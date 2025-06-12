package com.example.webapp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/hello")
public class SimpleServlet extends HttpServlet {

    private Logger log;

    @Override
    public void init() throws ServletException {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-7s] %5$s %n");
        this.log = Logger.getLogger(SimpleServlet.class.getName());
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        log.log(Level.INFO, "GET Request received.");

        String dbHost = System.getenv("POSTGRES_HOST");
        String dbUser = System.getenv("POSTGRES_USER");
        String dbPass = System.getenv("POSTGRES_PASSWORD");
        String dbName = System.getenv("POSTGRES_DATABASE");

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("text/plain");
        resp.getWriter().println("Database Host: " + dbHost);
        resp.getWriter().println("Database User: " + dbUser);
        resp.getWriter().println("Database Name: " + dbName);
    }
}
