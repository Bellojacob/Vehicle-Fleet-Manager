// Title:   DailyPlan.java
// Author:  Jacob Bello
// Course:  CST 336
// Date:    10/7/2024
// Abstract: This DailyPlan class is the most crucial class in the program, we have several attributes such as,
//           dateOfWorkOrder, number of cars, number of trucks, and arraylist for drivers and vehicles.
//           We initially start by parsing all the data from the text file which is passed in, then we go line by line
//           and make sure to store each important segment of information as variables and objects. Then we
//           have a toString which prints how many vehicles to request of each type, and prints the results of
//           orderFulfilled and senorityCheck methods. These methods assign drivers to their vehicles accordingly.

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
    public void parseFile(String filepath) {
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


    // in this method we are going to check if the order was fulfilled correctly or not
    public String orderFulfilled() {
//        System.out.println("Drivers:");
//        for (Driver driver : drivers){
//            System.out.println(driver);
//        }
//        System.out.println("Vehicles:");
//        for (Vehicle vh : vehicles){
//            System.out.println(vh);
//        }
        // Sort drivers by hire date
        // print before sort
//        System.out.println("pre sort:");
//        for (Driver dr : drivers){
//            System.out.println(dr);
//        }
        Collections.sort(drivers);
        // print post sort
//        System.out.println("post sort");
//        for (Driver dr : drivers){
//            System.out.println(dr);
//        }
//        for (int i = 0; i < drivers.size() - 1; i++) {
//            drivers.get(i).compareTo(drivers.get(i + 1));
//        }



        // keep track of how many vehicles of each type are assigned
        int carsAssigned = 0;
        int trucksAssigned = 0;

        // for each vehicle in the arraylist of vehicles, make each vehicle have a assignedDriver value of null
        for (Vehicle vehicle : vehicles) {



            // then for each driver in driver arraylist, check if the driver has a valid license, and if the vehicle we
            // are currently on is a truck, if so then we need to check if the current driver has a CDL to drive the truck,
            // or if the the current vehicle is a car
            // then for the current vehicle, make the assignedDriver value equal to the current driver
            Driver assignedDriver = null;
            for (Driver driver : drivers) {
                if (driver.isValidLicense(new Date()) && ((vehicle instanceof Truck && driver.isHasCDL()))) {
                    assignedDriver = driver;
                    break;
                } else if (driver.isValidLicense(new Date()) && vehicle instanceof Car) {
                    assignedDriver = driver;
                    break;
                }
            }

            // if the current vehicle has an assignedDriver value, then set the driver for the current driver,
            // if the current vehicle is a car, then iterate carsAssigned, if it is a truck then iterate truckAssigned
            if (assignedDriver != null) {
                vehicle.setDriver(assignedDriver);
                if (vehicle instanceof Car) {
                    carsAssigned++;
                } else {
                    trucksAssigned++;
                }
            }
        }

        // for the toString, if we have enough cars assigned for the carNums variable we got from the txt file,
        // and we have enough trucks assigned for the truckNums variable we got from the txt file,
        // if both are true, then return that we fulfilled the order
        // other return we did not fulfill the order
        boolean orderFulfilled = (carsAssigned >= carNums) && (trucksAssigned >= truckNums);

        if (orderFulfilled) {
            return "Order fulfilled \n";
        } else if(carsAssigned < carNums){
            return "Order not fulfilled: \n- Not enough street legal cars.\n";
        } else if (trucksAssigned < truckNums) {
            return "Order not fulfilled: \n- Not enough street legal trucks.\n";
        } else {
            return "Order not fulfilled: \n- Unknown reason.\n";
        }
    }


        public String seniorityCheck() {
        // Sort drivers by hire date
        Collections.sort(drivers);

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
                drivers.remove(assignedDriver);
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

    // print out getDetails section
    public String getDetails() {
        StringBuilder details = new StringBuilder();

        // iterate through each vehicle, get license plate, and driver for each. Check to make sure the vehicle is
        // valid and doesn't exceed emissions. return info accordingly
        for (Vehicle vehicle : vehicles) {
            String licensePlate = vehicle.getLicensePlate();
            Driver driver = vehicle.getDriver();
            boolean exceedsEmissions = !vehicle.isStreetLegal();

            if (exceedsEmissions) {
                if (vehicle instanceof Truck) {
                    details.append("Truck with license plate ")
                            .append(licensePlate)
                            .append(" exceeds the tailpipe emission standard.\n");
                } else {
                    details.append("Car with license plate ")
                            .append(licensePlate)
                            .append(" exceeds the tailpipe emission standard.\n");
                }
            } else if (driver != null) {
                if (vehicle instanceof Truck) {
                    details.append("Truck with license plate ")
                            .append(licensePlate)
                            .append(" assigned to ")
                            .append(driver.getName())
                            .append(".\n");
                } else {
                    details.append("Car with license plate ")
                            .append(licensePlate)
                            .append(" assigned to ")
                            .append(driver.getName())
                            .append(".\n");
                }
            }
        }

        details.append(checkLicense());

        return details.toString();
    }

    public String checkLicense(){
        boolean allLicensesValid = true;
        StringBuilder sb = new StringBuilder();
        for (Driver driver : drivers) {
            if (!driver.isValidLicense(new Date())) {
                allLicensesValid = false;
                sb.append("Driver ").append(driver.getName()).append(" has an expired license.\n");
            }
        }
        if (allLicensesValid) {
            sb.append("\nAll drivers have valid licenses.\n");
        }
        return sb.toString();
    }
}

