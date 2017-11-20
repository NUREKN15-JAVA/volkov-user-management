package ua.nure.usermanagement.web;

import ua.nure.usermanagement.User;
import ua.nure.usermanagement.database.DaoFactory;
import ua.nure.usermanagement.database.exception.DatabaseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * An implementation of {@link HttpServlet} for processing the requests
 * of visualising and changing data in table "users" of the database
 */
@WebServlet(name = "browseServlet", urlPatterns = "/browse")
public class BrowseServlet extends HttpServlet {

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
        if (req.getParameter("add") != null) {
            add(req, resp);
        } else if (req.getParameter("edit") != null) {
            edit(req, resp);
        } else if (req.getParameter("delete") != null) {
            delete(req, resp);
        } else if (req.getParameter("details") != null) {
            details(req, resp);
        } else {
            browse(req, resp);
        }
    }

    /**
     * Processes a request for a detailed visualisation of a selected entry
     *
     * @param req  Request scope of the web app
     * @param resp Response scope of the web app
     * @throws ServletException
     * @throws IOException
     */
    private void details(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("id") == null) {
            req.setAttribute("error", "Please, select a user via a radio button before proceeding");
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
            return;
        }
        try {
            User user = DaoFactory.getInstance().getUserDao().find(Long.valueOf(req.getParameter("id")));
            req.getSession().setAttribute("user", user);
        } catch (DatabaseException e) {
            req.setAttribute("error", "So... Um, there is an error in the database: " + e.getMessage());
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("/details").forward(req, resp);
    }

    /**
     * Processes a request for a deleting of a selected entry
     *
     * @param req  Request scope of the web app
     * @param resp Response scope of the web app
     * @throws ServletException
     * @throws IOException
     */
    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("id") == null) {
            req.setAttribute("error", "Please, select a user via a radio button before proceeding");
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
        }
        try {
            User user = DaoFactory.getInstance().getUserDao().find(Long.valueOf(req.getParameter("id")));
            req.getSession().setAttribute("user", user);
        } catch (DatabaseException e) {
            req.setAttribute("error", "So... Um, there is an error in the database: " + e.getMessage());
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("/delete").forward(req, resp);
    }

    /**
     * Processes a request for an editing of a selected entry
     *
     * @param req  Request scope of the web app
     * @param resp Response scope of the web app
     * @throws ServletException
     * @throws IOException
     */
    private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("id") == null) {
            req.setAttribute("error", "Please, select a user via a radio button before proceeding");
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
            return;
        }
        try {
            User user = DaoFactory.getInstance().getUserDao().find(Long.valueOf(req.getParameter("id")));
            req.getSession().setAttribute("user", user);
        } catch (DatabaseException e) {
            req.setAttribute("error", "So... Um, there is an error in the database: " + e.getMessage());
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("/edit").forward(req, resp);
    }

    /**
     * Processes a request for an adding of an entry
     *
     * @param req  Request scope of the web app
     * @param resp Response scope of the web app
     * @throws ServletException
     * @throws IOException
     */
    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/add").forward(req, resp);
    }

    /**
     * Processes a request for a visualisation of data
     * contained in a table "users" in database
     *
     * @param req  Request scope of the web app
     * @param resp Response scope of the web app
     * @throws ServletException
     * @throws IOException
     */
    private void browse(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Collection<User> users;
        try {
            users = DaoFactory.getInstance().getUserDao().findAll();
        } catch (DatabaseException e) {
            throw new ServletException(e);
        }
        req.getSession().setAttribute("users", users);
        req.getRequestDispatcher("/browse.jsp").forward(req, resp);
    }
}
