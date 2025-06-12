package com.example.webapp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
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

        log.log(Level.INFO, "GET request received.");
        resp.setContentType("text/plain");
        PrintWriter out = resp.getWriter();

        // Environment variable'lardan bağlantı bilgilerini al
        String user = System.getenv("PGUSER");
        String pass = System.getenv("PGPASS");
        String host = System.getenv("PGHOST");
        String db   = System.getenv("PGDB");

        String url = "jdbc:postgresql://" + host + ":5432/" + db;

        try {
            // PostgreSQL JDBC sürücüsünü yükle
            Class.forName("org.postgresql.Driver");

            // Bağlantı kur
            Connection conn = DriverManager.getConnection(url, user, pass);
            log.log(Level.INFO, "PostgreSQL connection established.");

            // Sorgu çalıştır
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM todo");

            // Sonuçları yaz
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
            log.log(Level.SEVERE, "Database connection failed: " + e.getMessage(), e);
            out.println("Error connecting to PostgreSQL: " + e.getMessage());
        }
    }
}
