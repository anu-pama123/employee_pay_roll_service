package com.bridgelabz;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeePayRollService {

    public enum IoService {CONSOLE_IO, FILE_IO, DB_IO, REST_IO}
    private List<EmployeePayRollData> employeePayRollList;

    public EmployeePayRollService() {}

    public EmployeePayRollService(List<EmployeePayRollData>employeePayRollList) {
        this.employeePayRollList = employeePayRollList;
    }

    public static void main(String[] args) {
        List<EmployeePayRollData> employeePayRollList = new ArrayList<>();
        EmployeePayRollService employeePayRoll = new EmployeePayRollService(employeePayRollList);
        Scanner consoleInputReader = new Scanner(System.in);
        employeePayRoll.readEmployeePayRollData(consoleInputReader);
        employeePayRoll.writeEmployeePayRollData(IoService.FILE_IO);
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

    public void writeEmployeePayRollData(IoService IoService) {
        if (IoService.equals(IoService.CONSOLE_IO))
            System.out.println("\nwriting employee payroll roaster to console\n" + employeePayRollList);
            else if (IoService.equals(IoService.FILE_IO))
                new EmployeePayRollFileIOService().writeData(employeePayRollList);
    }

//    public long readEmployeePayRollData(IoService ioService) {
//        if (ioService.equals(IoService.FILE_IO))
//            this.employeePayRollList = new EmployeePayRollService().readdata();
//            return employeePayRollList.size();
//    }

    public void printData(IoService ioService){
        if (ioService.equals(IoService.FILE_IO))
            new EmployeePayRollFileIOService().printData();
    }

    public long countEntries (IoService ioService) {
        if (ioService.equals(IoService.FILE_IO))
            return new EmployeePayRollFileIOService().countEntries();
            return 0;
    }
}
