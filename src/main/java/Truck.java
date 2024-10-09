//- loadCap : double
//- towingCap : int
//+ EMISSIONS_LIMIT : double
//+ isStreetLegal() : boolean

public class Truck extends Vehicle{
    private double loadCap;
    private int towingCap;
    public final double EMISSIONS_LIMIT = 500; // grams per mile

    public Truck(String licensePlate, double emissions, int mileage, double loadCap, int towingCap) {
        super(licensePlate, emissions, mileage);
        this.loadCap = loadCap;
        this.towingCap = towingCap;
    }

    public boolean isStreetLegal(){
        return this.emissions <= EMISSIONS_LIMIT;
    }
}
