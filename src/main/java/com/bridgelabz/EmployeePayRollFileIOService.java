package com.bridgelabz;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class EmployeePayRollFileIOService {

    public static String PAY_ROLL_FILE_NAME = "/home/aswin/payroll_file.txt";

    public void writeData(List<EmployeePayRollData> employeePayRollDataList) {
        StringBuffer empBuffer = new StringBuffer();
        employeePayRollDataList.forEach(employee -> {
            String employeeDataString = employee.toString().concat("\n");
            empBuffer.append(employeeDataString);
        });

        try {
            Files.write(Paths.get(PAY_ROLL_FILE_NAME), empBuffer.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
