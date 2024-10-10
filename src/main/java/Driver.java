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
}
