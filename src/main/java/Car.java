//- numPassengers : int
//+ EMISSIONS_LIMIT : double
//+ isStreetLegal() : boolean

public class Car extends Vehicle{
    private int numPassengers;
    public final double EMISSIONS_LIMIT = 350; // grams

    public Car(String licensePlate, double emissions, int mileage, int numPassengers) {
        super(licensePlate, emissions, mileage);
        this.numPassengers = numPassengers;
    }

    public boolean isStreetLegal(){
        return this.emissions <= EMISSIONS_LIMIT;
    }
}
