package ua.nure.usermanagement.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * An implementation of {@link EditServlet} for processing the requests
 * of visualising data of an entry from "users" table in database
 */
@WebServlet(name = "detailsServlet", urlPatterns = "/details")
public class DetailsServlet extends EditServlet {

    /**
     * This methods processes all commands, that come to the servlet from
     * {@link BrowseServlet} or user
     * @param req Request scope of the web app
     * @param resp Response scope of the web app
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("back") != null) {
            req.getRequestDispatcher("/browse").forward(req, resp);
        } else {
            showPage(req, resp);
        }
    }

    /**
     * Calls a jsp page for the visualisation of a specific entry
     * @param req Request scope of the web app
     * @param resp Response scope of the web app
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/details.jsp").forward(req, resp);

    }
}
