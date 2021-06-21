package com.bridgelabz;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeePayRoll {
    private List<EmployeePayRollData> employeePayRollList;

    public EmployeePayRoll() {}

    public EmployeePayRoll(List<EmployeePayRollData>employeePayRollList) {
        this.employeePayRollList = employeePayRollList;
    }

    public static void main(String[] args) {
        List<EmployeePayRollData> employeePayRollList = new ArrayList<>();
        EmployeePayRoll employeePayRoll = new EmployeePayRoll(employeePayRollList);
        Scanner consoleInputReader = new Scanner(System.in);
        employeePayRoll.readEmployeePayRollData(consoleInputReader);
        employeePayRoll.writeEmployeePayRollData();
    }

    private void readEmployeePayRollData(Scanner consoleInputReader) {
        System.out.println("Enter employee ID: ");
        int id = consoleInputReader.nextInt();
        System.out.println("Enter employee name: ");
        String name = consoleInputReader.next();
        System.out.println("Enter employee salary: ");
        double salary = consoleInputReader.nextDouble();
        EmployeePayRollData empObj = new EmployeePayRollData(id, name, salary);
        employeePayRollList.add(empObj);
    }

    private void writeEmployeePayRollData() {
        System.out.println("\nWriting Employee Payroll Roaster to Console\n" + employeePayRollList);
    }
}
