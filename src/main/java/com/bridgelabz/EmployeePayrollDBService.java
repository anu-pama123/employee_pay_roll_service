package com.bridgelabz;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollDBService {
    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service?usessl=false";
        String userName = "admin";
        String password = "password";
        Connection connection;
        System.out.println("Connecting to database" + jdbcURL);
        connection = DriverManager.getConnection(jdbcURL, userName, password);
        System.out.println("Connection is successful.." + connection);
        return connection;
    }

    public List<EmployeePayRollData> readData() {
        String sql = "SELECT * FROM employee_pay_roll";
        List<EmployeePayRollData> employeePayRollList = new ArrayList<>();
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet =  statement.executeQuery(sql);
            while (resultSet.next())  {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");
                LocalDate start = resultSet.getDate("start").toLocalDate();
                employeePayRollList.add(new EmployeePayRollData(id, name, salary, start));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(employeePayRollList);
        return employeePayRollList;
    }
}
