import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class DailyPlan {
    private Date dateOfWorkOrder;
    private int carNums;
    private int truckNums;
    private ArrayList<Driver> drivers;
    private ArrayList<Vehicle> vehicles;

    private boolean seniorityCheckPassed = true;

    public DailyPlan(String filePath) {
        drivers = new ArrayList<>();
        vehicles = new ArrayList<>();
        parseFile(filePath);
    }

    // Parse the file to get work order information, drivers, and vehicles
    private void parseFile(String filepath) {
        File file = new File(filepath);
        try {
            Scanner scanner = new Scanner(file);

            // Read date of work order
            if (scanner.hasNextLine()) {
                String[] dateParts = scanner.nextLine().split("/");
                int month = Integer.parseInt(dateParts[0]);
                int day = Integer.parseInt(dateParts[1]);
                int year = Integer.parseInt(dateParts[2]);
                dateOfWorkOrder = new Date(month, day, year);
            }

            // Read request for cars and trucks
            if (scanner.hasNextLine()) {
                String[] vehicleRequests = scanner.nextLine().split(",");
                carNums = Integer.parseInt(vehicleRequests[0]);
                truckNums = Integer.parseInt(vehicleRequests[1]);
            }

            // Read number of drivers
            int numDrivers = 0;
            if (scanner.hasNextLine()) {
                numDrivers = Integer.parseInt(scanner.nextLine());
            }

            // Read driver information
            for (int i = 0; i < numDrivers; i++) {
                if (scanner.hasNextLine()) {
                    String[] driverInfo = scanner.nextLine().split(",");
                    String name = driverInfo[0];
                    String[] hireDateParts = driverInfo[1].split("/");
                    Date hireDate = new Date(Integer.parseInt(hireDateParts[0]), Integer.parseInt(hireDateParts[1]), Integer.parseInt(hireDateParts[2]));
                    String dLicense = driverInfo[2];
                    String[] licenseExpParts = driverInfo[3].split("/");
                    Date licenseExp = new Date(Integer.parseInt(licenseExpParts[0]), Integer.parseInt(licenseExpParts[1]), Integer.parseInt(licenseExpParts[2]));
                    boolean hasCDL = Boolean.parseBoolean(driverInfo[4]);

                    Driver driver = new Driver(name, hireDate, licenseExp, hasCDL, dLicense);
                    drivers.add(driver);
                }
            }

            // Read vehicle information
            int numVehicles = 0;
            if (scanner.hasNextLine()) {
                numVehicles = Integer.parseInt(scanner.nextLine());
            }

            for (int i = 0; i < numVehicles; i++) {
                if (scanner.hasNextLine()) {
                    String[] vehicleInfo = scanner.nextLine().split(",");
                    String licensePlate = vehicleInfo[0];

                    if (vehicleInfo.length == 5) {  // this is a truck
                        double loadCap = Double.parseDouble(vehicleInfo[1]);
                        int towingCap = Integer.parseInt(vehicleInfo[2]);
                        int mileage = Integer.parseInt(vehicleInfo[3]);
                        double emissions = Double.parseDouble(vehicleInfo[4]);

                        Truck truck = new Truck(licensePlate, emissions, mileage, loadCap, towingCap);
                        vehicles.add(truck);
                    } else {  // this is a car
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

    public String toString(){
        return dateOfWorkOrder + "\n"
                + "Cars requested: " + carNums + ", Trucks requested: " + truckNums + "\n"
                + orderFulfilled() +
                seniorityCheck();
    }

    public String orderFulfilled() {
        // Sort drivers by hire date
        Collections.sort(drivers, (d1, d2) -> d1.getHireDate().compareTo(d2.getHireDate()));


        int carsAssigned = 0;
        int trucksAssigned = 0;

        for (Vehicle vehicle : vehicles) {
            Driver assignedDriver = null;

            for (Driver driver : drivers) {
                if (driver.isValidLicense(new Date()) &&
                        ((vehicle instanceof Truck && driver.isHasCDL()) || vehicle instanceof Car)) {
                    assignedDriver = driver;
                    break;
                }
            }

            if (assignedDriver != null) {
                vehicle.setDriver(assignedDriver);
                if (vehicle instanceof Car) {
                    carsAssigned++;
                } else if (vehicle instanceof Truck) {
                    trucksAssigned++;
                }
            }
        }

        boolean orderFulfilled = (carsAssigned >= carNums) && (trucksAssigned >= truckNums);
        if (orderFulfilled) {
            return "Order fulfilled \n";
        } else {
            return "Order not fulfilled \n";
        }    }




    public String seniorityCheck() {
        // Sort drivers by hire date
        Collections.sort(drivers, (d1, d2) -> d1.getHireDate().compareTo(d2.getHireDate()));

        int carsAssigned = 0;
        int trucksAssigned = 0;
        boolean seniorityCheckPassed = true;

        for (Vehicle vehicle : vehicles) {
            Driver assignedDriver = null;

            for (Driver driver : drivers) {
                if (driver.isValidLicense(new Date()) &&
                        ((vehicle instanceof Truck && driver.isHasCDL()) || vehicle instanceof Car)) {
                    assignedDriver = driver;
                    break;
                }
            }

            if (assignedDriver != null) {
                vehicle.setDriver(assignedDriver);
                if (vehicle instanceof Car) {
                    carsAssigned++;
                } else if (vehicle instanceof Truck) {
                    trucksAssigned++;
                }
            } else {
                seniorityCheckPassed = false;
                break;
            }
        }

        if (seniorityCheckPassed) {
            return "Seniority check passed";
        } else {
            return "Seniority check failed";
        }
    }

    public String getDetails() {
        StringBuilder details = new StringBuilder();

        for (Vehicle vehicle : vehicles) {
            String licensePlate = vehicle.getLicensePlate();
            Driver driver = vehicle.getDriver();
            boolean exceedsEmissions = !vehicle.isStreetLegal();

            if (exceedsEmissions) {
                details.append(vehicle instanceof Truck ? "Truck" : "Car")
                        .append(" with license plate ")
                        .append(licensePlate)
                        .append(" exceeds the tailpipe emission standard.\n");
            } else if (driver != null) {
                details.append(vehicle instanceof Truck ? "Truck" : "Car")
                        .append(" with license plate ")
                        .append(licensePlate)
                        .append(" assigned to ")
                        .append(driver.getName())
                        .append(".\n");
            }
        }

        boolean allLicensesValid = true;
        for (Driver driver : drivers) {
            if (!driver.isValidLicense(new Date())) {
                details.append("Driver ")
                        .append(driver.getName())
                        .append(" has an expired license.\n");
                allLicensesValid = false;
            }
        }

        if (allLicensesValid) {
            details.append("All drivers have valid licenses.");
        }

        return details.toString();
    }
}
