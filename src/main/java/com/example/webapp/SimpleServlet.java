package com.example.webapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

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

        resp.setContentType("text/plain");
        PrintWriter out = resp.getWriter();

        // 🔐 Secret bilgileri doğrudan yazıldı (Sadece test ortamı için)
        String host = "final1-pg.postgres.database.azure.com";
        String db = "finaldb";
        String user = "flaskAdmin@final1-pg";
        String password = "EsenZeynep.35";

        String url = "jdbc:postgresql://" + host + ":5432/" + db;

        try {
            Class.forName("org.postgresql.Driver");

            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM todo");

            while (rs.next()) {
                int id = rs.getInt("id");
                String desc = rs.getString("description");
                String details = rs.getString("details");
                boolean done = rs.getBoolean("done");

                out.println("[" + id + "] " + desc + " - " + details + " (done: " + done + ")");
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            out.println("Error connecting to PostgreSQL: " + e.getMessage());
            e.printStackTrace(out);
        }
    }
}
