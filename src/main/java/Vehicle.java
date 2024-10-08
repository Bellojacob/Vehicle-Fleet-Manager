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
    private int milage;

    public boolean isStreetLegal(){
        return true;
    }
}
