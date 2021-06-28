package com.bridgelabz;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeePayRollDBService {
    private PreparedStatement employeePayRollDataStatement;
    private static EmployeePayRollDBService employeePayRollDBService;
    private EmployeePayRollDBService() { }

    public static EmployeePayRollDBService getInstance() {
        if (employeePayRollDBService == null)
            employeePayRollDBService = new EmployeePayRollDBService();
        return employeePayRollDBService;
    }

    public List<EmployeePayRollData> readData() {
        String sql = "SELECT * FROM employee_pay_roll";
        return this.getEmployeePayRollDataUsingDB(sql);
    }

    public List<EmployeePayRollData> getEmployeePayRollForDateRange(LocalDate startDate, LocalDate endDate) {
        String sql = String.format("SELECT * FR0M employee_pay_roll WHERE START BETWEEN '%s' AND '%s';", Date.valueOf(startDate), Date.valueOf(endDate));
        return this.getEmployeePayRollDataUsingDB(sql);
    }

    public Map<String, Double> getAverageSalaryByGender() {
        String sql = "SELECT gender, AVG(salary) as avg_salary FROM employee_pay_roll GROUP by gender;";
        Map<String, Double> genderToAverageSalaryMap = new HashMap<>();
        try(Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String gender = resultSet.getString("gender");
                double salary = resultSet.getDouble("avg_salary");
                genderToAverageSalaryMap.put(gender, salary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genderToAverageSalaryMap;
    }

    public List<EmployeePayRollData> getEmployeePayRollDataUsingDB(String sql) {
        List<EmployeePayRollData> employeePayRollList = new ArrayList<>();
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet =  statement.executeQuery(sql);
            employeePayRollList = this.getEmployeePayRollData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
 //       System.out.println(employeePayRollList);
        return employeePayRollList;
    }

    public List<EmployeePayRollData> getEmployeePayRollData(String name) {
        List<EmployeePayRollData> employeePayRollList = null;
        if (this.employeePayRollDataStatement == null)
            this.prepareStatementForEmployeeData();
        try {
  //          employeePayRollDataStatement.setString(1, name);
            System.out.println(employeePayRollDataStatement);
            ResultSet resultSet = employeePayRollDataStatement.executeQuery();
            employeePayRollList = this.getEmployeePayRollData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayRollList;
    }

    private List<EmployeePayRollData> getEmployeePayRollData(ResultSet resultSet) {
        List<EmployeePayRollData> employeePayRollList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");
                LocalDate start = resultSet.getDate("start").toLocalDate();
            employeePayRollList.add(new EmployeePayRollData(id, name, salary, start));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayRollList;
    }

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

    private void prepareStatementForEmployeeData() {
        try {
            Connection connection = this.getConnection();
            String sql = "SELECT * FR0M employee_pay_roll WHERE name = 'Terisa' ";
            employeePayRollDataStatement =connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int updateEmployeeData(String name, double salary) {
        return this.updateEmployeeDataUsingStatement(name, salary);
    }

    private int updateEmployeeDataUsingStatement(String name, double salary) {
        String sql = String.format("update employee_pay_roll set salary = %.2f where name = '%s';", salary, name);
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
