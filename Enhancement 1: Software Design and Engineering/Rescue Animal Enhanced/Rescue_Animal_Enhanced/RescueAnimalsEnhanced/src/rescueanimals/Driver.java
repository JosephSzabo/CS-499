package rescueanimals;

import java.util.List;
import java.util.Scanner;

public class Driver {

    public static void main(String[] args) {
        // Initialize the database and create the table if it doesn't exist
        DatabaseHelper.createTable();

        // Initialize test dogs and monkeys
        initializeDogList();
        initializeMonkeyList();

        // Create a Scanner for user input
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            // Display menu
            System.out.println("\nPlease choose an option:");
            System.out.println("1. Intake new dog");
            System.out.println("2. Intake new monkey");
            System.out.println("3. Reserve an animal");
            System.out.println("4. Print a list of all dogs");
            System.out.println("5. Print a list of all monkeys");
            System.out.println("6. Print a list of all non-reserved animals");
            System.out.println("7. Update animal details");
            System.out.println("8. Delete an animal");
            System.out.println("9. Search animals by name");
            System.out.println("q. Quit");
            System.out.print("Your choice: ");
            
            String choice = scanner.nextLine();

            // Handle user input using a switch-case structure
            switch (choice) {
                case "1":
                    intakeNewDog(scanner);
                    break;
                case "2":
                    intakeNewMonkey(scanner);
                    break;
                case "3":
                    reserveAnimal(scanner);
                    break;
                case "4":
                    printAllDogs();
                    break;
                case "5":
                    printAllMonkeys();
                    break;
                case "6":
                    printNonReservedAnimals();
                    break;
                case "7":
                    updateAnimal(scanner);
                    break;
                case "8":
                    deleteAnimal(scanner);
                    break;
                case "9":
                    searchAnimals(scanner);
                    break;
                case "q":
                    running = false;
                    System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again."); // user input validation
            }
        }

        scanner.close(); // close the scanner
    }

    // Initialize test dogs
    public static void initializeDogList() {
        Dog dog1 = new Dog("Spot", "German Shepherd", "male", "1", "25.6", "05-12-2019", "United States", "intake", false, "United States");
        Dog dog2 = new Dog("Rex", "Great Dane", "male", "3", "35.2", "02-03-2020", "United States", "Phase I", false, "United States");
        Dog dog3 = new Dog("Bella", "Chihuahua", "female", "4", "25.6", "12-12-2019", "Canada", "in service", true, "Canada");

        // Insert test dogs into database
        DatabaseHelper.insertAnimal(dog1);
        DatabaseHelper.insertAnimal(dog2);
        DatabaseHelper.insertAnimal(dog3);
    }

    // Initialize test monkeys with species
    public static void initializeMonkeyList() {
        String[] speciesList = {"capuchin", "guenon", "macaque", "squirrel monkey", "tamarin", "marmoset"};
        
        Monkey monkey1 = new Monkey("Charlie", speciesList[0], "male", "3 years", "12 kg", "2022-06-01", "Brazil", "Trained", false, "USA", 0.5f, 0.7f, 1.0f);
        Monkey monkey2 = new Monkey("Max", speciesList[1], "female", "2 years", "10 kg", "2021-03-12", "Kenya", "Phase I", false, "USA", 0.4f, 0.8f, 0.9f);
        Monkey monkey3 = new Monkey("Lola", speciesList[2], "male", "4 years", "14 kg", "2020-09-20", "India", "In Service", true, "India", 0.3f, 0.6f, 1.1f);

        // Insert test monkeys into database
        DatabaseHelper.insertAnimal(monkey1);
        DatabaseHelper.insertAnimal(monkey2);
        DatabaseHelper.insertAnimal(monkey3);
    }

    // Intake a new dog by asking user for information
    private static void intakeNewDog(Scanner scanner) {
        System.out.println("Enter dog details:");

        System.out.print("Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Breed: ");
        String breed = scanner.nextLine();
        
        System.out.print("Gender: ");
        String gender = scanner.nextLine();
        
        System.out.print("Age: ");
        String age = scanner.nextLine();
        
        System.out.print("Weight: ");
        String weight = scanner.nextLine();
        
        System.out.print("Acquisition Date: ");
        String acquisitionDate = scanner.nextLine();
        
        System.out.print("Acquisition Country: ");
        String acquisitionCountry = scanner.nextLine();
        
        System.out.print("Training Status: ");
        String trainingStatus = scanner.nextLine();
        
        System.out.print("Reserved (true/false): ");
        boolean reserved = Boolean.parseBoolean(scanner.nextLine());
        
        System.out.print("In Service Country: ");
        String inServiceCountry = scanner.nextLine();
        
        // Create the Dog object
        Dog dog = new Dog(name, breed, gender, age, weight, acquisitionDate, acquisitionCountry, trainingStatus, reserved, inServiceCountry);
        
        // Insert the new dog into the database
        DatabaseHelper.insertAnimal(dog);
    }

    // Insert a new monkey by asking user for information
    private static void intakeNewMonkey(Scanner scanner) {
        System.out.println("Enter monkey details:");

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Species: ");
        String species = scanner.nextLine();
        
        System.out.print("Gender: ");
        String gender = scanner.nextLine();
        
        System.out.print("Age: ");
        String age = scanner.nextLine();
        
        System.out.print("Weight: ");
        String weight = scanner.nextLine();
        
        System.out.print("Acquisition Date: ");
        String acquisitionDate = scanner.nextLine();
        
        System.out.print("Acquisition Country: ");
        String acquisitionCountry = scanner.nextLine();
        
        System.out.print("Training Status: ");
        String trainingStatus = scanner.nextLine();
        
        System.out.print("Reserved (true/false): ");
        boolean reserved = Boolean.parseBoolean(scanner.nextLine());
        
        System.out.print("In Service Country: ");
        String inServiceCountry = scanner.nextLine();
        
        System.out.print("Tail Length (in cm): ");
        float tailLength = Float.parseFloat(scanner.nextLine());
        
        System.out.print("Height (in cm): ");
        float height = Float.parseFloat(scanner.nextLine());
        
        System.out.print("Body Length (in cm): ");
        float bodyLength = Float.parseFloat(scanner.nextLine());
        
        // Create the Monkey object
        Monkey monkey = new Monkey(name, species, gender, age, weight, acquisitionDate, acquisitionCountry, trainingStatus, reserved, inServiceCountry, tailLength, height, bodyLength);
        
        // Insert the new monkey into the database
        DatabaseHelper.insertAnimal(monkey);
    }

    // Reserve an animal by ID
    private static void reserveAnimal(Scanner scanner) {
        System.out.print("Enter the animal ID to reserve: ");
        int animalId = Integer.parseInt(scanner.nextLine());
        
        // Reserve the animal in the database
        DatabaseHelper.reserveAnimal(animalId);
    }

    // Update details of existing animal in database
    private static void updateAnimal(Scanner scanner) {
        System.out.print("Enter the animal ID to update: ");
        int animalId = Integer.parseInt(scanner.nextLine());

        // Get the existing animal from the database using animal ID
        RescueAnimal animal = DatabaseHelper.fetchAnimalById(animalId);

        if (animal == null) {
            System.out.println("Animal not found.");
            return;
        }

        // Update animal's details and apply
        System.out.println("Enter new details for the animal:");

        System.out.print("Name (" + animal.getName() + "): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) animal.setName(name);

        System.out.print("Gender (" + animal.getGender() + "): ");
        String gender = scanner.nextLine();
        if (!gender.isEmpty()) animal.setGender(gender);

        System.out.print("Age (" + animal.getAge() + "): ");
        String age = scanner.nextLine();
        if (!age.isEmpty()) animal.setAge(age);

        System.out.print("Weight (" + animal.getWeight() + "): ");
        String weight = scanner.nextLine();
        if (!weight.isEmpty()) animal.setWeight(weight);

        System.out.print("Acquisition Date (" + animal.getAcquisitionDate() + "): ");
        String acquisitionDate = scanner.nextLine();
        if (!acquisitionDate.isEmpty()) animal.setAcquisitionDate(acquisitionDate);

        System.out.print("Acquisition Country (" + animal.getAcquisitionLocation() + "): ");
        String acquisitionCountry = scanner.nextLine();
        if (!acquisitionCountry.isEmpty()) animal.setAcquisitionLocation(acquisitionCountry);

        System.out.print("Training Status (" + animal.getTrainingStatus() + "): ");
        String trainingStatus = scanner.nextLine();
        if (!trainingStatus.isEmpty()) animal.setTrainingStatus(trainingStatus);

        System.out.print("Reserved (" + animal.getReserved() + "): ");
        boolean reserved = Boolean.parseBoolean(scanner.nextLine());
        animal.setReserved(reserved);

        System.out.print("In Service Country (" + animal.getInServiceCountry() + "): ");
        String inServiceCountry = scanner.nextLine();
        if (!inServiceCountry.isEmpty()) animal.setInServiceCountry(inServiceCountry);

        // Update the animal in the database
        DatabaseHelper.updateAnimal(animal);
    }

    // Delete an animal from databsae using animal ID
    private static void deleteAnimal(Scanner scanner) {
        System.out.print("Enter the animal ID to delete: ");
        int animalId = Integer.parseInt(scanner.nextLine());

        // Delete the animal from the database
        DatabaseHelper.deleteAnimal(animalId);
    }

    // Search for animals by name adn display results
    private static void searchAnimals(Scanner scanner) {
        System.out.print("Enter animal name to search: ");
        String name = scanner.nextLine();

        // fetch and display animals that match the search query
        List<RescueAnimal> animals = DatabaseHelper.searchAnimalsByName(name);
        if (animals.isEmpty()) {
            System.out.println("No animals found with that name.");
        } else {
            System.out.println("\nSearch Results:");
            for (RescueAnimal animal : animals) {
            	System.out.println("ID: " + animal.getId() + 
                        ", Name: " + animal.getName() + 
                        ", Gender: " + animal.getGender() + 
                        ", Age: " + animal.getAge() + 
                        ", Weight: " + animal.getWeight() + 
                        ", Acquisition Date: " + animal.getAcquisitionDate() + 
                        ", Acquisition Country: " + animal.getAcquisitionLocation() + 
                        ", Training Status: " + animal.getTrainingStatus() + 
                        ", Reserved: " + (animal.getReserved() ? "Yes" : "No") + 
                        ", In Service Country: " + animal.getInServiceCountry() + 
                        ", Type: " + animal.getClass().getSimpleName() +
                        // If it's a dog, you can print the breed
                        (animal instanceof Dog ? ", Breed: " + ((Dog) animal).getBreed() : "") +
                        // If it's a monkey, you can print the species, tail length, height, and body length
                        (animal instanceof Monkey ? ", Species: " + ((Monkey) animal).getSpecies() +
                                                   ", Tail Length: " + ((Monkey) animal).getTailLength() +
                                                   ", Height: " + ((Monkey) animal).getHeight() +
                                                   ", Body Length: " + ((Monkey) animal).getBodyLength() : ""));
            }
        }
    }

    // Print all dogs in database
    private static void printAllDogs() {
        List<RescueAnimal> dogs = DatabaseHelper.fetchDogs();
        if (dogs.isEmpty()) {
            System.out.println("No dogs found.");
        } else {
            System.out.println("\nList of all dogs:");
            for (RescueAnimal dog : dogs) {
                Dog d = (Dog) dog;
                System.out.println("ID: " + d.getId() + ", Name: " + d.getName() + ", Breed: " + d.getBreed() + 
                        ", Gender: " + d.getGender() + ", Age: " + d.getAge() + ", Weight: " + d.getWeight() +
                        ", Acquisition Date: " + d.getAcquisitionDate() + ", Acquisition Country: " + d.getAcquisitionLocation() +
                        ", Training Status: " + d.getTrainingStatus() + ", Reserved: " + d.getReserved() +
                        ", In Service Country: " + d.getInServiceCountry());
            }
        }
    }

    // Print all monkeys in database
    private static void printAllMonkeys() {
        List<RescueAnimal> monkeys = DatabaseHelper.fetchMonkeys();
        if (monkeys.isEmpty()) {
            System.out.println("No monkeys found.");
        } else {
            System.out.println("\nList of all monkeys:");
            for (RescueAnimal monkey : monkeys) {
                Monkey m = (Monkey) monkey;
                System.out.println("ID: " + m.getId() + ", Name: " + m.getName() + ", Species: " + m.getSpecies() +
                        ", Gender: " + m.getGender() + ", Age: " + m.getAge() + ", Weight: " + m.getWeight() +
                        ", Acquisition Date: " + m.getAcquisitionDate() + ", Acquisition Country: " + m.getAcquisitionLocation() +
                        ", Training Status: " + m.getTrainingStatus() + ", Reserved: " + m.getReserved() +
                        ", In Service Country: " + m.getInServiceCountry() + ", Tail Length: " + m.getTailLength() +
                        ", Height: " + m.getHeight() + ", Body Length: " + m.getBodyLength());
            }
        }
    }

    // Print all non-reserved animals in database
    private static void printNonReservedAnimals() {
        List<RescueAnimal> nonReservedAnimals = DatabaseHelper.fetchNonReservedAnimals();
        if (nonReservedAnimals.isEmpty()) {
            System.out.println("No non-reserved animals found.");
        } else {
            System.out.println("\nList of all non-reserved animals:");
            for (RescueAnimal animal : nonReservedAnimals) {
            	System.out.println("ID: " + animal.getId() + 
                        ", Name: " + animal.getName() + 
                        ", Gender: " + animal.getGender() + 
                        ", Age: " + animal.getAge() + 
                        ", Weight: " + animal.getWeight() + 
                        ", Acquisition Date: " + animal.getAcquisitionDate() + 
                        ", Acquisition Country: " + animal.getAcquisitionLocation() + 
                        ", Training Status: " + animal.getTrainingStatus() + 
                        ", Reserved: " + (animal.getReserved() ? "Yes" : "No") + 
                        ", In Service Country: " + animal.getInServiceCountry() + 
                        ", Type: " + animal.getClass().getSimpleName() +
                        // If it's a dog, you can print the breed
                        (animal instanceof Dog ? ", Breed: " + ((Dog) animal).getBreed() : "") +
                        // If it's a monkey, you can print the species, tail length, height, and body length
                        (animal instanceof Monkey ? ", Species: " + ((Monkey) animal).getSpecies() +
                                                   ", Tail Length: " + ((Monkey) animal).getTailLength() +
                                                   ", Height: " + ((Monkey) animal).getHeight() +
                                                   ", Body Length: " + ((Monkey) animal).getBodyLength() : ""));
 }
}
}
}
