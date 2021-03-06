package com.bridgelabz;

import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class EmployeePayRollServiceTest {
    @Test
    public void given3EmployeeWhenWrittenToFileShouldMatchEmployeeEntries() {
        EmployeePayRollData[] arrayOfEmps = {
                new EmployeePayRollData(1, "Jeff Bezos", 10000.0),
                new EmployeePayRollData(2, "Bill Gates", 20000.0),
                new EmployeePayRollData(3, "Mark Zuckerberg", 30000.0)
        };
        EmployeePayRollService employeePayRollService;
        employeePayRollService = new EmployeePayRollService(Arrays.asList(arrayOfEmps));
        employeePayRollService.writeEmployeePayRollData(EmployeePayRollService.IoService.FILE_IO);
        employeePayRollService.printData(EmployeePayRollService.IoService.FILE_IO);
        long entries = employeePayRollService.countEntries(EmployeePayRollService.IoService.FILE_IO);
        Assert.assertEquals(3, entries);
    }

    @Test
    public void givenFileOnReadingFromFileShouldMatchEmployeeCount() {
        EmployeePayRollService employeePayRollService = new EmployeePayRollService();
        long entries = employeePayRollService.readEmployeePayRollData(EmployeePayRollService.IoService.FILE_IO);
        Assert.assertEquals(3, entries);
    }

    @Test
    public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount() {
        EmployeePayRollService employeePayRollService = new EmployeePayRollService();
        List<EmployeePayRollData>employeePayRollData = employeePayRollService.readEmployeePayRollDatas
                                                       (EmployeePayRollService.IoService.DB_IO);
        Assert.assertEquals(3,employeePayRollData.size());
    }

    @Test
    public void givenNewSalaryForEmployee_WhenUpdated_ShouldSyncWithDB() {
        EmployeePayRollService employeePayRollService = new EmployeePayRollService();
        List<EmployeePayRollData>employeePayRollData = employeePayRollService.readEmployeePayRollDatas
                                                       (EmployeePayRollService.IoService.DB_IO);
        employeePayRollService.updateEmployeeSalary("Terisa", 400000);
        boolean result = employeePayRollService.checkEmployeePayRollInSyncWithDB("Terisa");
        Assert.assertTrue(result);
    }

    @Test
    public void givenDateRangeWhenRetrievedShouldMatchEmployeeCount() {
        EmployeePayRollService employeePayRollService = new EmployeePayRollService();
        employeePayRollService.readEmployeePayRollDatas(EmployeePayRollService.IoService.DB_IO);
        LocalDate startDate = LocalDate.of(2018, 01, 03);
        LocalDate endDate = LocalDate.now();
        List<EmployeePayRollData>employeePayRollData = employeePayRollService.readEmployeePayrollForDateRange
                                                       (EmployeePayRollService.IoService.DB_IO, startDate, endDate);
        Assert.assertEquals(3, employeePayRollData.size());
    }
    @Test
    public void givenPayRollData_WhenAverageSalaryRetrievedByGender_ShouldReturnProperValue() {
        EmployeePayRollService employeePayRollService = new EmployeePayRollService();
        employeePayRollService.readEmployeePayRollDatas(EmployeePayRollService.IoService.DB_IO);
        Map<String, Double> averageSalaryByGender = employeePayRollService.readAverageSalaryByGender(EmployeePayRollService.IoService.DB_IO);
        Assert.assertTrue(averageSalaryByGender.get("M").equals(200000.00) && averageSalaryByGender.get("F").equals(400000.00));
    }

    @Test
    public void givenNewEmployee_WhenAdded_ShouldSyncWithDB() {
        EmployeePayRollService employeePayRollService = new EmployeePayRollService();
        employeePayRollService.readEmployeePayRollDatas(EmployeePayRollService.IoService.DB_IO);
        employeePayRollService.addEmployeeToPayRoll("Mark", 500000.00, LocalDate.now(),"M");
        boolean result = employeePayRollService.checkEmployeePayRollInSyncWithDB("Mark");
        Assert.assertTrue(result);
    }

    @Test
    public void givenEmployees_WhenAddedToDB_ShouldMatchEmployeeEntries() {
        EmployeePayRollData[] arrayOfEmps = {
                new EmployeePayRollData(0, "Jeff Bezos", "M", 10000.0,LocalDate.now()),
                new EmployeePayRollData(0, "Bill Gates", "M", 20000.0, LocalDate.now()),
                new EmployeePayRollData(0, "Mark Zuckerberg","M", 30000.0, LocalDate.now()),
                new EmployeePayRollData(0, "Sunder", "M", 10000.0, LocalDate.now()),
                new EmployeePayRollData(0, "Mukesh", "M", 20000.0, LocalDate.now()),
                new EmployeePayRollData(0, "Anil", "M", 30000.0, LocalDate.now())
        };
        EmployeePayRollService employeePayRollService = new EmployeePayRollService();
        employeePayRollService.readEmployeePayRollDatas(EmployeePayRollService.IoService.DB_IO);
        Instant start = Instant.now();
        employeePayRollService.addEmployeeToPayRoll(Arrays.asList(arrayOfEmps));
        Instant end = Instant.now();
        System.out.println("Duration without Thread: "+ Duration.between(start, end));
        Assert.assertEquals(6, employeePayRollService.countEntries(EmployeePayRollService.IoService.DB_IO));
    }
}
