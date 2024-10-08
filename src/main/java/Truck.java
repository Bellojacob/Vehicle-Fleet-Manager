//- loadCap : double
//- towingCap : int
//+ EMISSIONS_LIMIT : double
//+ isStreetLegal() : boolean

public class Truck {
    private double loadCap;
    private int towingCap;
    public final double EMISSIONS_LIMIT = 500;

    public boolean isStreetLegal(){
        return true;
    }
}
