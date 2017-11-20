package ua.nure.usermanagement.web;

import ua.nure.usermanagement.User;
import ua.nure.usermanagement.database.DaoFactory;
import ua.nure.usermanagement.database.exception.DatabaseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;

@WebServlet(name = "addServlet",urlPatterns = "/add")
public class AddServlet extends EditServlet{

    @Override
    protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/add.jsp").forward(req, resp);
    }

    @Override
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
            DaoFactory.getInstance().getUserDao().create(user);
        } catch (DatabaseException e) {
            req.setAttribute("error", "So... Um, there is an error in the database: " + e.getMessage());
            showPage(req, resp);
        }
        req.getRequestDispatcher("/browse").forward(req, resp);
    }

    @Override
    public User getUser(HttpServletRequest req) throws ValidationException, ParseException {
        User user = new User();
        if (req.getParameter("firstName") == null) {
            throw new ValidationException("Oi, you forgot to enter the first name!");
        }
        user.setFirstName(req.getParameter("firstName"));
        if (req.getParameter("lastName") == null) {
            throw new ValidationException("Oi, you forgot to enter the last name!");
        }
        user.setLastName(req.getParameter("lastName"));
        user.setDateOfBirth(DateFormat.getDateInstance().parse(req.getParameter("dateOfBirth")));
        return user;
    }
}
