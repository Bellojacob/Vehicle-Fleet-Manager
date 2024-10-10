//- loadCap : double
//- towingCap : int
//+ EMISSIONS_LIMIT : double
//+ isStreetLegal() : boolean

public class Truck extends Vehicle {
    private double loadCap;
    private int towingCap;
    public final double EMISSIONS_LIMIT = 500; // grams

    public Truck(String licensePlate, double emissions, int mileage, double loadCap, int towingCap) {
        super(licensePlate, emissions, mileage);
        this.loadCap = loadCap;
        this.towingCap = towingCap;
    }

    @Override
    public boolean isStreetLegal() {
        // emission limit in kg should be .5 kg
        double emissionLimitInKg = (double) EMISSIONS_LIMIT / 1000;
//        System.out.println("The emission limit for trucks is " + emissionLimitInKg);
        // let's say 77 kg is passed in

        double thisEmission = this.getEmissions() / 1000;
//        System.out.println("This truck ("+this.getLicensePlate()+") has this much emissions: " + thisEmission);
//
//        if (thisEmission <= emissionLimitInKg){
//            System.out.println("This truck passes the emission limit \n");
//        } else {
//            System.out.println("This truck fails the emission test \n");
//        }
//        System.out.println("Truck " + this.getLicensePlate() + " Emissions: " + thisEmissionLimit + " kg, Limit: " + emissionLimitInKg + " kg");
        return thisEmission <= emissionLimitInKg;

    }


    public double getLoadCap() {
        return loadCap;
    }

    public int getTowingCap() {
        return towingCap;
    }
}