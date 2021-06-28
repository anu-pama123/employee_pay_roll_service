package com.bridgelabz;

import java.time.LocalDate;
import java.util.Objects;

public class EmployeePayRollData {
    public static int size;
    public int id;
    public String name;
    public double salary;
    public LocalDate start;

    public EmployeePayRollData(Integer id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public EmployeePayRollData(Integer id, String name, double salary, LocalDate start) {
        this(id, name, salary);
        this.start = start;
    }

    @Override
    public String toString() {
        return "EmployeePayRollData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", start=" + start +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeePayRollData that = (EmployeePayRollData) o;
        return id == that.id && Double.compare(that.salary, salary) == 0 && Objects.equals(name, that.name) && Objects.equals(start, that.start);
    }
}
