package ua.nure.usermanagement.web;

import ua.nure.usermanagement.User;
import ua.nure.usermanagement.database.DaoFactory;
import ua.nure.usermanagement.database.exception.DatabaseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;

/**
 * An implementation of {@link HttpServlet} for processing the requests
 * of editing an entry from "users" table in database
 */
@WebServlet(name = "editServlet",urlPatterns = "/edit")
public class EditServlet extends HttpServlet {

    /**
     * This method processes all requests that come through
     * him and divides them based on the users command
     *
     * @param req  Request scope of the web app
     * @param resp Response scope of the web app
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("ok") != null) {
            doOk(req, resp);
        } else if (req.getParameter("cancel") != null) {
            doCancel(req, resp);
        } else {
            showPage(req, resp);
        }
    }

    /**
     * Calls a jsp page for the editing of a specific entry
     * @param req Request scope of the web app
     * @param resp Response scope of the web app
     * @throws ServletException
     * @throws IOException
     */
    protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/edit.jsp").forward(req, resp);
    }

    /**
     * Nullifies all changes of an entry and returns user to the browse page.
     * @param req Request scope of the web app
     * @param resp Response scope of the web app
     * @throws ServletException
     * @throws IOException
     */
    private void doCancel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/browse").forward(req, resp);
    }

    /**
     * Applies all changes of an entry and returns user to the browse page.
     * @param req Request scope of the web app
     * @param resp Response scope of the web app
     * @throws ServletException
     * @throws IOException
     */
    protected void doOk(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = null;
        try {
            user = getUser(req);
        } catch (ValidationException e) {
            req.setAttribute("error", e.getMessage());
            showPage(req, resp);
            return;
        } catch (ParseException e) {
            req.setAttribute("error", "Oi, your date format is a bit off! " + e.getMessage());
            showPage(req, resp);
            return;
        }
        try {
            DaoFactory.getInstance().getUserDao().update(user);
        } catch (DatabaseException e) {
            req.setAttribute("error", "So... Um, there is an error in the database: " + e.getMessage());
            showPage(req, resp);
            return;
        }
        req.getRequestDispatcher("/browse").forward(req, resp);
    }

    /**
     * A utility method for getting user data from the request
     * @param req Request scope of the web app
     * @return Returns a User object filled with data from the request
     * @throws ValidationException If not all of the required parameters are found
     * @throws ParseException If given date information isn't in a parsable date-format
     */
    protected User getUser(HttpServletRequest req) throws ValidationException, ParseException {
        User user = new User();
        user.setId(Long.valueOf(req.getParameter("id")));
        if (req.getParameter("firstName") == null) {
            throw new ValidationException("Oi, you forgot to enter the first name!");
        }
        user.setFirstName(req.getParameter("firstName"));
        if (req.getParameter("lastName") == null) {
            throw new ValidationException("Oi, you forgot to enter the last name!");
        }
        user.setLastName(req.getParameter("lastName"));
        if(req.getParameter("dateOfBirth")== null){
            throw new ValidationException("Oi, you forgot to enter the date of birth!");
        }
        user.setDateOfBirth(DateFormat.getDateInstance().parse(req.getParameter("dateOfBirth")));
        return user;
    }
}
