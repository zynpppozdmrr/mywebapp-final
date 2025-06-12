package com.mywebapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Azure App Service'den gelen environment değişkenleri
        String host = System.getenv("POSTGRESHOST");
        String database = System.getenv("POSTGRESDATABASE");
        String user = System.getenv("POSTGRESUSER");
        String password = System.getenv("POSTGRESPASSWORD");

        String jdbcUrl = "jdbc:postgresql://" + host + ":5432/" + database;

        resp.setContentType("text/plain");
        PrintWriter out = resp.getWriter();

        try {
            // PostgreSQL JDBC driver'ını yükle
            Class.forName("org.postgresql.Driver");

            // Bağlantıyı kur
            Connection conn = DriverManager.getConnection(jdbcUrl, user, password);
            out.println("✅ Connection to PostgreSQL successful!");
            conn.close();

        } catch (ClassNotFoundException e) {
            out.println("❌ JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            out.println("❌ Database connection error: " + e.getMessage());
        }
    }
}
