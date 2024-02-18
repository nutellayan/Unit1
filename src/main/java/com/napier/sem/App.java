package com.napier.sem;

import java.sql.*;

public class App {
    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect() {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/employees?useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            } catch (SQLException | InterruptedException ex) {
                System.out.println("Failed to connect to database attempt " + i);
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * Generate and display salary report for all employees.
     */
    public void generateSalaryReport() {
        try {
            // Create SQL statement
            Statement stmt = con.createStatement();

            // SQL query to retrieve salary information for all employees
            String query = "SELECT e.emp_no, e.first_name, e.last_name, s.salary " +
                    "FROM employees e " +
                    "JOIN salaries s ON e.emp_no = s.emp_no " +
                    "WHERE s.to_date = '9999-01-01'";

            // Execute SQL query
            ResultSet rs = stmt.executeQuery(query);

            // Display salary report
            System.out.println("Employee Salary Report:");
            System.out.println("==================================================");
            System.out.printf("%-10s %-15s %-15s %-10s\n", "EmployeeID", "First Name", "Last Name", "Salary");
            System.out.println("--------------------------------------------------");
            while (rs.next()) {
                int empNo = rs.getInt("emp_no");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                int salary = rs.getInt("salary");
                System.out.printf("%-10d %-15s %-15s %-10d\n", empNo, firstName, lastName, salary);
            }
        } catch (SQLException e) {
            System.out.println("Error executing SQL query: " + e.getMessage());
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect() {
        if (con != null) {
            try {
                // Close connection
                con.close();
                System.out.println("Disconnected from database");
            } catch (SQLException e) {
                System.out.println("Error closing connection to database");
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        // Create new Application
        App app = new App();

        // Connect to database
        app.connect();

        // Generate and display salary report
        app.generateSalaryReport();

        // Disconnect from database
        app.disconnect();
    }
}
