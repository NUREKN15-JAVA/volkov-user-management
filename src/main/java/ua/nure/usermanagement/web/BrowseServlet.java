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

@WebServlet(name = "browseServlet",urlPatterns = "/browse")
public class BrowseServlet extends HttpServlet {

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

    private void details(HttpServletRequest req, HttpServletResponse resp) {

    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) {
    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("id") == null) {
            req.setAttribute("error", "You fool! You should've selected a user first!");
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
        }
        try {
            User user = DaoFactory.getInstance().getUserDao().find(Long.valueOf(req.getParameter("id")));
            req.getSession().setAttribute("user", user);
        } catch (DatabaseException e) {
            req.setAttribute("error", "So... Um, there is an error in the database: " + e.getMessage());
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
        }
        req.getRequestDispatcher("/edit").forward(req, resp);
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/add").forward(req, resp);

    }

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
