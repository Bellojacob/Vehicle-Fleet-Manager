import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
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

    // Assign drivers based on seniority check
    public void assignDriversWithSeniorityCheck() {
        // Sort drivers by seniority (hire date)
        Collections.sort(drivers);

        Driver lastAssignedDriver = null;  // Keep track of the last assigned driver for seniority comparison

        for (Vehicle vehicle : vehicles) {
            if (vehicle.isStreetLegal()) {
                boolean driverAssigned = false;

                // Loop through sorted drivers to find a match
                for (Driver driver : drivers) {
                    if (vehicle instanceof Truck) {
                        // Check if driver has CDL and license is valid for a truck
                        if (driver.isValidLicense(new Date()) && driver.isHasCDL() && vehicle.getDriver() == null) {
                            vehicle.setDriver(driver);

                            // Seniority check: Ensure the current driver is more senior than the last one
                            if (lastAssignedDriver != null && driver.compareTo(lastAssignedDriver) > 0) {
                                seniorityCheckPassed = false;
                            }
                            lastAssignedDriver = driver;
                            driverAssigned = true;
                            break;
                        }
                    } else {
                        // Check if driver has a valid license for a car
                        if (driver.isValidLicense(new Date()) && vehicle.getDriver() == null) {
                            vehicle.setDriver(driver);

                            // Seniority check: Ensure the current driver is more senior than the last one
                            if (lastAssignedDriver != null && driver.compareTo(lastAssignedDriver) > 0) {
                                seniorityCheckPassed = false;
                            }
                            lastAssignedDriver = driver;
                            driverAssigned = true;
                            break;
                        }
                    }
                }

                // If no driver was assigned, mark the vehicle as unfulfilled
                if (!driverAssigned) {
                    System.out.println("No suitable driver available for vehicle with license plate " + vehicle.getLicensePlate());
                }
            } else {
                // Vehicle exceeds emissions standards
                System.out.println(vehicle.getClass().getSimpleName() + " with license plate " + vehicle.getLicensePlate() + " exceeds the tailpipe emission standard.");
            }
        }

        // Check if seniority passed
        if (seniorityCheckPassed) {
            System.out.println("Seniority check passed.");
        } else {
            System.out.println("Seniority check failed.");
        }
    }

    // Generate the report for the current DailyPlan
    @Override
    public String toString() {
        StringBuilder report = new StringBuilder();

        report.append("REPORT \n")
                .append(dateOfWorkOrder).append("\n")
                .append("Cars requested: ").append(carNums)
                .append(", Trucks requested: ").append(truckNums).append("\n");

        boolean allVehiclesAssigned = true;

        report.append("DETAILS \n");

        for (Vehicle vehicle : vehicles) {
            if (vehicle.isStreetLegal()) {
                if (vehicle.getDriver() != null) {
                    // Print details of vehicle assignment
                    report.append(vehicle.getClass().getSimpleName())
                            .append(" with license plate ")
                            .append(vehicle.getLicensePlate())
                            .append(" assigned to ")
                            .append(vehicle.getDriver().getName())
                            .append(".\n");
                } else {
                    report.append(vehicle.getClass().getSimpleName())
                            .append(" with license plate ")
                            .append(vehicle.getLicensePlate())
                            .append(" could not be assigned to a driver.\n");
                    allVehiclesAssigned = false;
                }
            } else {
                report.append(vehicle.getClass().getSimpleName())
                        .append(" with license plate ")
                        .append(vehicle.getLicensePlate())
                        .append(" exceeds the tailpipe emission standard.\n");
                allVehiclesAssigned = false;
            }
        }

        // Check if the order was fulfilled
        if (allVehiclesAssigned) {
            report.append("Order fulfilled.\n");
        } else {
            report.append("Order not fulfilled.\n");
        }

        // Print seniority check result
        if (seniorityCheckPassed) {
            report.append("Seniority check passed.\n");
        } else {
            report.append("Seniority check failed.\n");
        }

        // Check if all drivers have valid licenses
        boolean allDriversValid = true;
        for (Driver driver : drivers) {
            if (!driver.isValidLicense(new Date())) {
                allDriversValid = false;
                break;
            }
        }

        if (allDriversValid) {
            report.append("All drivers have valid licenses.\n");
        } else {
            report.append("Some drivers have invalid licenses.\n");
        }

        return report.toString();
    }
}
