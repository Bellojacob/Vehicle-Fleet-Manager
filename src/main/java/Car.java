// Title:   Car.java
// Author:  Jacob Bello
// Course:  CST 336
// Date:    10/3/2024
// Abstract: This Car class is a child class of Vehicle, it holds all the attributes that Vehicle does as well as
//           converting the emissions limit from grams to kg

//- numPassengers : int
//+ EMISSIONS_LIMIT : double
//+ isStreetLegal() : boolean

public class Car extends Vehicle {
    private int numPassengers;
    public final double EMISSIONS_LIMIT = 350; // grams

    public Car(String licensePlate, double emissions, int mileage, int numPassengers) {
        super(licensePlate, emissions, mileage);
        this.numPassengers = numPassengers;
    }

    @Override
    public boolean isStreetLegal() {
        // emission limit in kg should be .35 kg
        double emissionLimitInKg = (double)EMISSIONS_LIMIT / 1000;
//        System.out.println("The emission limit is " + emissionLimitInKg + " kilograms");
        // let's say 77 kg is passed in

        double thisEmission = this.getEmissions() / 1000;
//        System.out.println("This car ("+this.getLicensePlate()+") emits this much pollution: " + thisEmission);
//        System.out.println("Car " + this.getLicensePlate() + " Emissions: " + thisEmission + " kg, Limit: " + emissionLimitInKg + " kg");
//        if (thisEmission <= emissionLimitInKg){
//            System.out.println("This car should be good to go \n");
//        } else {
//            System.out.println("This car fails the emission test \n");
//        }
        return thisEmission <= emissionLimitInKg;

//        double emissionsInGrams = this.getEmissions() * 1000.0; // Convert emissions from kg to grams
//        System.out.println("Car " + this.getLicensePlate() + " Emissions: " + emissionsInGrams + " grams, Limit: " + EMISSIONS_LIMIT + " grams");
//        return emissionsInGrams <= EMISSIONS_LIMIT;
    }

    public int getNumPassengers() {
        return numPassengers;
    }

    public String toString(){
        return "CAR - License Plate #: " + getLicensePlate() + "passenger seating: " + getNumPassengers() +  " mileage: " + getMileage() + " emissions: " + getEmissions();
    }
}
