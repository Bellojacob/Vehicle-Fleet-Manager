// Title:   Person.java
// Author:  Jacob Bello
// Course:  CST 336
// Date:    10/7/2024
// Abstract: This Person class holds values for name and the person's hire date.

//- name: String
//- hireDate: Date

public class Person {
    private String name;
    private Date hireDate;

    public Person(String name, Date hireDate) {
        this.name = name;
        this.hireDate = hireDate;
    }

    public String getName() {
        return name;
    }

    public Date getHireDate() {
        return hireDate;
    }
}
