import java.util.ArrayList;
import java.util.Scanner;

//  Car Class

class Car {
    private String brand;
    private String model;
    private String plateNumber;
    private double pricePerDay;
    private boolean isAvailable;

    public Car(String brand, String model, String plateNumber, double pricePerDay) {
        this.brand = brand;
        this.model = model;
        this.plateNumber = plateNumber;
        this.pricePerDay = pricePerDay;
        this.isAvailable = true;
    }

    public String getBrand()       { return brand; }
    public String getModel()       { return model; }
    public String getPlateNumber() { return plateNumber; }
    public double getPricePerDay() { return pricePerDay; }
    public boolean isAvailable()   { return isAvailable; }
    public void setAvailable(boolean available) { this.isAvailable = available; }

    public void displayInfo() {
        System.out.println("  Brand    : " + brand + " " + model);
        System.out.println("  Plate    : " + plateNumber);
        System.out.println("  Price    : $" + pricePerDay + " / day");
        System.out.println("  Available: " + (isAvailable ? "Yes" : "No"));
        System.out.println("--------------------------------");
    }
}

//  Customer Class
class Customer {
    private String name;
    private String phoneNumber;
    private String licenseNumber;

    public Customer(String name, String phoneNumber, String licenseNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.licenseNumber = licenseNumber;
    }

    public String getName()          { return name; }
    public String getPhoneNumber()   { return phoneNumber; }
    public String getLicenseNumber() { return licenseNumber; }

    public void displayInfo() {
        System.out.println("  Name   : " + name);
        System.out.println("  Phone  : " + phoneNumber);
        System.out.println("  License: " + licenseNumber);
        System.out.println("--------------------------------");
    }
}


//  Rental Class
class Rental {
    private Car car;
    private Customer customer;
    private int days;
    private double totalPrice;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
        this.totalPrice = car.getPricePerDay() * days;
        car.setAvailable(false);
    }

    public Car getCar() { return car; }

    public void returnCar() {
        car.setAvailable(true);
        System.out.println("  Car returned successfully!");
    }

    public void displayReceipt() {
        System.out.println("--------------------------------");
        System.out.println("             Receipt            ");
        System.out.println("--------------------------------");
        System.out.println("  Customer : " + customer.getName());
        System.out.println("  Phone    : " + customer.getPhoneNumber());
        System.out.println("  Car      : " + car.getBrand() + " " + car.getModel());
        System.out.println("  Plate    : " + car.getPlateNumber());
        System.out.println("  Days     : " + days);
        System.out.println("  Per Day  : $" + car.getPricePerDay());
        System.out.println("--------------------------------");
        System.out.println("  Total    : $" + totalPrice);
        System.out.println("--------------------------------");
    }
}


//  Main Class
public class Main {

    static ArrayList<Car> cars = new ArrayList<>();
    static ArrayList<Rental> rentals = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
public static void main(String[] args) {
        cars.add(new Car("Toyota",  "Camry",    "KRK-1234", 50.0));
        cars.add(new Car("BMW",     "X5",       "SLM-5678", 120.0));
        cars.add(new Car("Kia",     "Sportage", "EBL-9999", 70.0));
        cars.add(new Car("Hyundai", "Tucson",   "DHK-3333", 65.0));

        boolean running = true;
        while (running) {
            showMenu();
            int choice = getInt("  Choose an option: ");
            switch (choice) {
                case 1 -> showAvailableCars();
                case 2 -> rentCar();
                case 3 -> returnCar();
                case 4 -> addCar();
                case 5 -> showAllCars();
                case 0 -> { System.out.println("  Goodbye!"); running = false; }
                default -> System.out.println("  Invalid choice!");
            }
        }
    }

    static void showMenu() {
        System.out.println("       Car Rental System       ");
        System.out.println("--------------------------------");
        System.out.println("1 - Available Cars");
        System.out.println("2 - Rent a Car");
        System.out.println("3 - Return a Car");
        System.out.println("4 - Add New Car");
        System.out.println("5 - Show All Cars");
        System.out.println("0 - Exit");
        System.out.println("--------------------------------");
    }

    static void showAvailableCars() {
        System.out.println("\n  Available Cars:");
        System.out.println("--------------------------------");
        boolean found = false;
        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).isAvailable()) {
                System.out.println("  [" + i + "]");
                cars.get(i).displayInfo();
                found = true;
            }
        }
        if (!found) System.out.println("  No cars available!");
    }

    static void rentCar() {
        showAvailableCars();
        int index = getInt("\n  Select car number: ");
        if (index < 0 || index >= cars.size()) {
            System.out.println("  Invalid number!"); return;
        }
        if (!cars.get(index).isAvailable()) {
            System.out.println("  Car not available!"); return;
        }
        System.out.print("  Customer Name  : "); String name    = scanner.nextLine();
        System.out.print("  Phone Number   : "); String phone   = scanner.nextLine();
        System.out.print("  License Number : "); String license = scanner.nextLine();
        int days = getInt("  Number of Days : ");

        Customer customer = new Customer(name, phone, license);
        Rental rental = new Rental(cars.get(index), customer, days);
        rentals.add(rental);
        rental.displayReceipt();
        System.out.println("  Rental created successfully!");
    }

    static void returnCar() {
        System.out.println("\n  Active Rentals:");
        System.out.println("--------------------------------");
        boolean found = false;
        for (int i = 0; i < rentals.size(); i++) {
            if (!rentals.get(i).getCar().isAvailable()) {
                System.out.println("  [" + i + "] " +
                    rentals.get(i).getCar().getBrand() + " " +
                    rentals.get(i).getCar().getModel() + " - " +
                    rentals.get(i).getCar().getPlateNumber());
                found = true;
            }
        }
        if (!found) { System.out.println("  No active rentals!"); return; }

        int index = getInt("\n  Select rental number: ");
        if (index < 0 || index >= rentals.size()) {
            System.out.println("  Invalid number!"); return;
        }
        rentals.get(index).returnCar();
    }
static void addCar() {
        System.out.println("\n  Enter new car details:");
        System.out.print("  Brand         : "); String brand = scanner.nextLine();
        System.out.print("  Model         : "); String model = scanner.nextLine();
        System.out.print("  Plate Number  : "); String plate = scanner.nextLine();
        double price = getDouble("  Price per day : $");
        cars.add(new Car(brand, model, plate, price));
        System.out.println("  Car added successfully!");
    }

    static void showAllCars() {
        System.out.println("\n  All Cars:");
        System.out.println("--------------------------------");
        for (int i = 0; i < cars.size(); i++) {
            System.out.println("  [" + i + "]");
            cars.get(i).displayInfo();
        }
    }

    static int getInt(String msg) {
        System.out.print(msg);
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.print("  Enter a number: ");
        }
        int val = scanner.nextInt();
        scanner.nextLine();
        return val;
    }

    static double getDouble(String msg) {
        System.out.print(msg);
        while (!scanner.hasNextDouble()) {
            scanner.nextLine();
            System.out.print("  Enter a number: ");
        }
        double val = scanner.nextDouble();
        scanner.nextLine();
        return val;
    }
}