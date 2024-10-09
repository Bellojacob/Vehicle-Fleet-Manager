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
