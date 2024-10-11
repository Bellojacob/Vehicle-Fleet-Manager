// Title:   Vehicle.java
// Author:  Jacob Bello
// Course:  CST 336
// Date:    10/3/2024
// Abstract: This is an abstract class which will be used to create car and truck objects. It should hold values such as
//           licensePlate, emissions, driver, if streetLegal, and mileage.

//- licensePlate: String
//- emissions: double
//- driver: Driver
//- streetLegal : boolean
//- mileage : int

//+ isStreetLegal() : boolean
//Add additional methods for the project


public abstract class Vehicle {
    private String licensePlate;
    private double emissions;
    private Driver driver;
    private boolean streetLegal;
    private int mileage;

    public Vehicle(String licensePlate, double emissions, int mileage) {
        this.licensePlate = licensePlate;
        this.emissions = emissions;
        this.mileage = mileage;
        this.streetLegal = isStreetLegal();
    }

    public abstract boolean isStreetLegal();

    public double getEmissions() {
        return emissions;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public int getMileage() {
        return mileage;
    }
}


