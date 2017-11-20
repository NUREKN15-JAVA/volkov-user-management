package ua.nure.usermanagement.web;

import ua.nure.usermanagement.User;
import ua.nure.usermanagement.database.DaoFactory;
import ua.nure.usermanagement.database.exception.DatabaseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * An implementation of {@link EditServlet} for processing the requests
 * of deleting an entry from "users" table in database
 */
@WebServlet(name = "deleteServlet", urlPatterns = "/delete")
public class DeleteServlet extends EditServlet {

    /**
     * Deletes an entry from the database and returns user to the browse page.
     * @param req Request scope of the web app
     * @param resp Response scope of the web app
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doOk(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            DaoFactory.getInstance().getUserDao().delete((User) req.getSession().getAttribute("user"));
        } catch (DatabaseException e) {
            req.setAttribute("error", "So... Um, there is an error in the database: " + e.getMessage());
            req.getRequestDispatcher("/delete.jsp").forward(req, resp);
        }
        req.getRequestDispatcher("/browse").forward(req, resp);
    }

    /**
     * Calls a jsp page for the confirmation of deletion of a specific entry
     * @param req Request scope of the web app
     * @param resp Response scope of the web app
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/delete.jsp").forward(req, resp);

    }
}
