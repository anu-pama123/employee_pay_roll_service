package com.bridgelabz;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class EmployeePayRollService {
    public enum IoService {CONSOLE_IO, FILE_IO, DB_IO, REST_IO}
    private List<EmployeePayRollData> employeePayRollList;
    private EmployeePayRollDBService employeePayRollDBService;

    public EmployeePayRollService() {
        employeePayRollDBService = EmployeePayRollDBService.getInstance();
    }

    public EmployeePayRollService(List<EmployeePayRollData>employeePayRollList) {
        this();
        this.employeePayRollList = employeePayRollList;
    }

    public static void main(String[] args) {
        List<EmployeePayRollData> employeePayRollList = new ArrayList<>();
        EmployeePayRollService employeePayRoll = new EmployeePayRollService(employeePayRollList);
        Scanner consoleInputReader = new Scanner(System.in);
        employeePayRoll.readEmployeePayRollData(consoleInputReader);
        employeePayRoll.writeEmployeePayRollData(IoService.FILE_IO);
    }

    private void readEmployeePayRollData (Scanner consoleInputReader) {
        System.out.println("Enter employee ID: ");
        int id = consoleInputReader.nextInt();
        System.out.println("Enter employee name: ");
        String name = consoleInputReader.next();
        System.out.println("Enter employee salary: ");
        double salary = consoleInputReader.nextDouble();
        EmployeePayRollData empObj = new EmployeePayRollData(id, name, salary);
        employeePayRollList.add(empObj);
    }

    public List<EmployeePayRollData> readEmployeePayRollDatas(IoService ioService) {
        if (ioService.equals(IoService.DB_IO))
            this.employeePayRollList = employeePayRollDBService.readData();
        return employeePayRollList;
    }

    public boolean checkEmployeePayRollInSyncWithDB(String name) {
        List<EmployeePayRollData> employeePayrolldataList = employeePayRollDBService.getEmployeePayRollData(name);
        return employeePayrolldataList.get(0).equals(getEmployeePayRollData(name));
    }

    public void updateEmployeeSalary(String name, double salary) {
        int result = employeePayRollDBService.updateEmployeeData(name, salary);
        if (result == 0) return;
        EmployeePayRollData employeePayRollData = this.getEmployeePayRollData(name);
        if(employeePayRollData != null) employeePayRollData.salary = salary;
    }

    private EmployeePayRollData getEmployeePayRollData(String name) {
        return this.employeePayRollList.stream()
                   .filter(employeePayRollDataItem -> employeePayRollDataItem.name.equals(name))
                   .findFirst()
                   .orElse(null);
    }

    public List<EmployeePayRollData> readEmployeePayrollForDateRange(IoService ioService, LocalDate startDate, LocalDate endDate) {
        if (ioService.equals(IoService.DB_IO))
        return employeePayRollDBService.getEmployeePayRollForDateRange(startDate, endDate);
        return null;
    }

    public Map<String, Double> readAverageSalaryByGender(IoService ioService) {
        if (ioService.equals(IoService.DB_IO))
            return employeePayRollDBService.getAverageSalaryByGender();
        return null;
    }

    public void addEmployeeToPayRoll(String name, double salary, LocalDate start, String gender) {
        employeePayRollList.add(employeePayRollDBService.addEmployeeToPayRoll(name, salary, start, gender));
    }

    public void writeEmployeePayRollData(IoService IoService) {
        if (IoService.equals(IoService.CONSOLE_IO))
            System.out.println("\nwriting employee payroll roaster to console\n" + employeePayRollList);
            else if (IoService.equals(IoService.FILE_IO))
                new EmployeePayRollFileIOService().writeData(employeePayRollList);
        System.out.println(employeePayRollList);
    }

    public void printData(IoService ioService){
        if (ioService.equals(IoService.FILE_IO))
            new EmployeePayRollFileIOService().printData();
    }

    public long countEntries (IoService ioService) {
        if (ioService.equals(IoService.FILE_IO))
            return new EmployeePayRollFileIOService().countEntries();
            return 0;
    }

    public long readEmployeePayRollData (IoService ioService) {
        List<EmployeePayRollData> employeePayRollList = new EmployeePayRollFileIOService().readData();
        return employeePayRollList.size();
    }
}
