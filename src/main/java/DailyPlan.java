import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DailyPlan {
    private Date dateOfWorkOrder;
    private int carNums;
    private int truckNums;
    private ArrayList<Driver> drivers;
    private ArrayList<Vehicle> vehicles;

    public DailyPlan(String filePath){
        drivers = new ArrayList<>();
        vehicles = new ArrayList<>();
        parseFile(filePath);
    }

    private void parseFile(String filepath) {
        File file = new File(filepath);
        try {
            Scanner scanner = new Scanner(file);

            // read date of work order
            if (scanner.hasNextLine()){
                String[] dateParts = scanner.nextLine().split("/");
                int month = Integer.parseInt(dateParts[0]);
                int day = Integer.parseInt(dateParts[1]);
                int year = Integer.parseInt(dateParts[2]);
                dateOfWorkOrder = new Date(month, day, year);
            }
            // read request for cars and trucks
            if (scanner.hasNextLine()){
                String[] vehicleRequests = scanner.nextLine().split("/");
                carNums = Integer.parseInt(vehicleRequests[0]);
                truckNums = Integer.parseInt(vehicleRequests[1]);
            }

            // read number of drivers
            int numDrivers = 0;
            if (scanner.hasNextLine()){
                numDrivers = Integer.parseInt(scanner.nextLine());
            }

            // read driver information
            for (int i = 0; i < numDrivers; i++){
                if (scanner.hasNextLine()){
                    String[] driverInfo = scanner.nextLine().split(",");
                    String name = driverInfo[0];
                    String[] hireDateParts = driverInfo[1].split("/");
                    Date hireDate = new Date(Integer.parseInt(hireDateParts[0]), Integer.parseInt(hireDateParts[1]),Integer.parseInt(hireDateParts[2]));
                    String dLicense = driverInfo[2];
                    String [] licenseExpParts = driverInfo[3].split("/");
                    Date licenseExp = new Date(Integer.parseInt(licenseExpParts[0]), Integer.parseInt(licenseExpParts[1]), Integer.parseInt(licenseExpParts[2]));
                    boolean hasCDL = Boolean.parseBoolean(driverInfo[4]);

                    Driver driver = new Driver(name, hireDate, licenseExp, hasCDL, dLicense);
                    drivers.add(driver);
                }
            }

            // read vehicle information
            int numVehicles = 0;
            if (scanner.hasNextLine()){
                numVehicles = Integer.parseInt(scanner.nextLine());
            }

            for (int i = 0; i < numVehicles; i++){
                if (scanner.hasNextLine()) {
                    String [] vehicleInfo = scanner.nextLine().split(",");
                    String licensePlate = vehicleInfo[0];

                    if (vehicleInfo.length == 5) {  // this is a truck
                        double loadCap = Double.parseDouble(vehicleInfo[1]);
                        int towingCap = Integer.parseInt(vehicleInfo[2]);
                        int mileage = Integer.parseInt(vehicleInfo[3]);
                        double emissions = Double.parseDouble(vehicleInfo[4]);

                        Truck truck = new Truck(licensePlate, emissions, mileage, loadCap, towingCap);
                        vehicles.add(truck);
                } else {
                        int numPassengers = Integer.parseInt(vehicleInfo[1]);
                        int mileage = Integer.parseInt(vehicleInfo[2]);
                        double emissions = Double.parseDouble(vehicleInfo[3]);

                        Car car = new Car(licensePlate, emissions, mileage, numPassengers);
                        vehicles.add(car);
                    }
            }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filepath);
            e.printStackTrace();
        }
    }

    public static Scanner fileReader(String filePath) {
        Scanner input = null;
        try {
            input = new Scanner(new FileInputStream(filePath));
        } catch (FileNotFoundException e) {
            System.out.println("File not found or could not be opened.");
            System.exit(0);
        }
        System.out.println(input);
        return input;
    }

    public String toString(){
        return "Empty for now";
    }
}