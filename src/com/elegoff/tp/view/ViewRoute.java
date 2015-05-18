package com.elegoff.tp.view;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.elegoff.tp.bean.Processed;
import com.mongodb.MongoClient;

@WebServlet("/display")
public class ViewRoute extends HttpServlet

{

    /**
     *
     */
    private static final long serialVersionUID = -5060194653857582613L;

    /**
     * Logging object
     */
    private final static org.apache.log4j.Logger LOG = Logger.getLogger(ViewRoute.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        MongoClient mongo = (MongoClient) req.getServletContext().getAttribute("MONGO_CLIENT");
        ViewService service = new ViewService(mongo);
        List<Processed> ps = service.run();

        req.setAttribute("data", ps);

        req.getRequestDispatcher("trades.jsp").forward(req, res);
    }

}
