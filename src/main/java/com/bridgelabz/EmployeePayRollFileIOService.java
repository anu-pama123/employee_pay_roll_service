package com.bridgelabz;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

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

    public void printData() {
        try {
            Files.lines(new File(PAY_ROLL_FILE_NAME).toPath()).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long countEntries() {
        long entries = 0;
        try {
            entries = Files.lines(new File(PAY_ROLL_FILE_NAME).toPath()).count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entries;
    }

    public List<EmployeePayRollData> readData() {
        List<EmployeePayRollData> employeePayRollList = new ArrayList<>();
        List<String> temp = new ArrayList<>();
        try {
            Files.lines(new File(PAY_ROLL_FILE_NAME).toPath()).map(line -> line.trim()).forEach(line -> temp.add(line));
            Iterator itr = temp.iterator();
            while (itr.hasNext()){
                String i = (String)itr.next();
                String[] keyValuePairs = i.substring(i.indexOf('{')+1, i.indexOf('}')).split(",");
                Map<String, String> map = new HashMap<>();
                for (String pair : keyValuePairs) {
                    String[] entry = pair.split("=");
                    map.put(entry[0].trim(), entry[1].trim());
                }
                EmployeePayRollData employeeData = new EmployeePayRollData(Integer.parseInt(map.get("id")), map.get("name"), Double.parseDouble(map.get("salary")));
                employeePayRollList.add(employeeData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employeePayRollList;
    }
}

