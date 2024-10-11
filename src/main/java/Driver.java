// Title:   Driver.java
// Author:  Jacob Bello
// Course:  CST 336
// Date:    10/3/2024
// Abstract: This Driver class is a child class of Person class, it has values for license expiration date, a trucking
//           license (CDL), and drivers license number. We also have a driver compareTo method to compare hiring dates.
//           We also have a method to check if a license is valid.

//- licenseExp : Date
//- hasCDL : boolean
//- dLicense : String
//Add additional attributes for the project
//+ compareTo(driver : Driver) : int

public class Driver extends Person implements Comparable<Driver>{
    private Date licenseExp;
    private boolean hasCDL;
    private String dLicense;

    public Driver(String name, Date hireDate, Date licenseExp, boolean hasCDL, String dLicense) {
        super(name, hireDate);
        this.licenseExp = licenseExp;
        this.hasCDL = hasCDL;
        this.dLicense = dLicense;
    }

    public int compareTo(Driver otherDriver) {
        return this.getHireDate().compareTo(otherDriver.getHireDate());
    }

    public boolean isValidLicense(Date currentDate){
        return currentDate.isBefore(licenseExp);

    }

    public Date getLicenseExp() {
        return licenseExp;
    }

    public boolean isHasCDL() {
        return hasCDL;
    }

    public String getdLicense() {
        return dLicense;
    }

    public Date getHireDate() {
        return super.getHireDate();
    }

    public String toString(){
        return getName() + ", HIRED: " + getHireDate() + ", DL #: " + getdLicense() + ", DL EXP: " + getLicenseExp() + ", HAS CDL: " + isHasCDL();
    }
}
