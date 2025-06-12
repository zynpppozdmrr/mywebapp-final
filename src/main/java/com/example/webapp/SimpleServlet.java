package com.example.webapp;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/hello")
public class SimpleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Azure App Service environment variables
        String dbHost = System.getenv("POSTGRESHOST");
        String dbUser = System.getenv("POSTGRESUSER");
        
        String dbName = System.getenv("POSTGRESDATABASE");

        resp.setContentType("text/plain");
        PrintWriter out = resp.getWriter();

        out.println("Database Host: " + dbHost);
        out.println("Database User: " + dbUser);
        out.println("Database Name: " + dbName);
        // Güvenlik açısından şifreyi yazdırmıyoruz
        // out.println("Database Password: " + dbPassword);
    }
}
