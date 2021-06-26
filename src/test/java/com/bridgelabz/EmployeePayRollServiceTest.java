package com.bridgelabz;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

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
        List<EmployeePayRollData>employeePayRollData = employeePayRollService.read2EmployeePayRollData(EmployeePayRollService.IoService.DB_IO);
        Assert.assertEquals(3,employeePayRollData.size());
    }
}
