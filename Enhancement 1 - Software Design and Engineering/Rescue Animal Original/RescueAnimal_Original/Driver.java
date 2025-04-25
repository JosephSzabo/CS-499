// Author Name: Joseph Szabo

// Date: April 21, 2024

// Course ID: IT-145

// Brief Description: This is the Driver class for module 5 

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Driver {
	
    private static ArrayList<Dog> dogList = new ArrayList<Dog>(); //Array list for dogs
    private static ArrayList<Monkey> monkeyList = new ArrayList<Monkey>(); //Array list for monkeys
    private static ArrayList<String> monkeySpecies = new ArrayList<String>();  //Array List for monkey species
    
    

    public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);  //Scanner object


        initializeDogList();
        initializeMonkeyList();
        initializeMonkeySpecies();
        
        Scanner scnr = new Scanner(System.in);
        displayMenu();

        // Add a loop that displays the menu, accepts the users input
        // and takes the appropriate action.
	    // For the project submission you must also include input validation
        // and appropriate feedback to the user.
        // Hint: create a Scanner and pass it to the necessary
        // methods 
	    // Hint: Menu options 4, 5, and 6 should all connect to the printAnimals() method.

    
char menuChoice = 'Z'; //any random value
while (menuChoice!= 'Q' && menuChoice!= 'q') {
	displayMenu();   // Display's menu to the user
	menuChoice = scanner.nextLine().charAt(0);  // Get user input
    switch(menuChoice) { //switch based on users choice
	case '1':
		intakeNewDog(scanner);  //call method to intake a new dog
		break;
	case '2':
		intakeNewMonkey(scanner); //call method to intake a new monkey
		break;
	case '3':
		reserveAnimal(scanner); //call method to reserve animal
		break;
	case '4':
		printAnimals('4'); //call method to print dog list
		break;
	case '5':
		printAnimals('5'); //call method to print monkey list
		break;
	case '6':
		printAnimals('6'); //call method to print all animals available list
		break;
	case 'Q': //quit program
	case 'q':
		System.out.println("Exiting the program. Goodbye.");
		break;
	default:		
			while (menuChoice < '1' || menuChoice > '6') {
                System.out.println("Please enter a valid choice (1-6): ");
                menuChoice = scanner.nextLine().charAt(0); // Get user input
            }
            break; // Break out of the inner loop
		} //end switch
	}//end loop
    }

	// This method prints the menu options
    public static void displayMenu() {
        System.out.println("\n\n");
        System.out.println("\t\t\t\tRescue Animal System Menu");
        System.out.println("[1] Intake a new dog");
        System.out.println("[2] Intake a new monkey");
        System.out.println("[3] Reserve an animal");
        System.out.println("[4] Print a list of all dogs");
        System.out.println("[5] Print a list of all monkeys");
        System.out.println("[6] Print a list of all animals that are not reserved");
        System.out.println("[q] Quit application");
        System.out.println();
        System.out.println("Enter a menu selection");
    }


    // Adds dogs to a list for testing
    public static void initializeDogList() {
        Dog dog1 = new Dog("Spot", "German Shepherd", "male", "1", "25.6", "05-12-2019", "United States", "intake", false, "United States");
        Dog dog2 = new Dog("Rex", "Great Dane", "male", "3", "35.2", "02-03-2020", "United States", "Phase I", false, "United States");
        Dog dog3 = new Dog("Bella", "Chihuahua", "female", "4", "25.6", "12-12-2019", "Canada", "in service", true, "Canada");

        dogList.add(dog1);
        dogList.add(dog2);
        dogList.add(dog3);
    }


    // Adds monkeys to a list for testing
    //Optional for testing
    public static void initializeMonkeyList() { //Method to initialize the list of monkeys
    	monkeyList = new ArrayList<Monkey>(); //Initialize monkeyList as a new array list of monkey objects
    }
    
    public static void initializeMonkeySpecies() { //Method to initialize the list of monkey species
    	
    	String species1 = new String("capuchin");
    	String species2 = new String("guenon");
    	String species3 = new String("macaque");
    	String species4 = new String("squirrel Monkey");
    	String species5 = new String("tamarin");
    	String species6 = new String("marmoset");
    	
    	monkeySpecies.add(species1);
    	monkeySpecies.add(species2);
    	monkeySpecies.add(species3);
    	monkeySpecies.add(species4);
    	monkeySpecies.add(species5);
    	monkeySpecies.add(species6);   	
	} 


    // Complete the intakeNewDog method
    // The input validation to check that the dog is not already in the list
    // is done for you
    public static void intakeNewDog(Scanner scanner) { //method to intake new dog
        System.out.println("What is the dog's name?");
        String dogName = scanner.next(); //Declare and initialize dogName
        
        //Iterate through the doglist to find the dog by it's name
        for(Dog dog: dogList) {
            if(dog.getName().equalsIgnoreCase(dogName)) { //check if dog name already exists
                System.out.println("\nThis dog is already in our system\n");
                return; //returns to menu
            }
        }    
            	try {
            		
            		
            		//Set its properties
            		String inServiceCountry = "";
            		String name = scanner.nextLine(); //initialize the name variable
            		System.out.println("What is the dog's breed?");
            		String breed = scanner.nextLine(); //get dog breed from user input
            		System.out.println("What is the dog's gender?");
            		String gender = scanner.nextLine(); //get dog gender from user input
            		System.out.println("What is the dog's age?");
            		String age = scanner.nextLine(); //get dog age from user input
            		System.out.println("what is the dog's weight?");
            		String weight = scanner.nextLine(); // get dog weight from user input
            		System.out.println("When was the date of aquiring the dog?");
            		String aquisitionDate = scanner.nextLine(); //get acquisition date from user input
            		System.out.println("Where is the location of aquiring dog?");
            		String aquisitionLocation = scanner.nextLine(); //get acquisition country from user
            		System.out.println("What is the dog's training status?");
            		String trainingStatus = scanner.nextLine(); // get training status from user input
            		System.out.println("Is the dog reserved? yes or no");           		
            		String reservedInput = scanner.nextLine().toLowerCase(); //get reserved info from user input
            		boolean reserved;
            		while (true) { // asking user input as (yes or no) instead of asking for true or false
            		    if (reservedInput.equals("yes")) {
            		        reserved = true;
            		        break;
            		    } else if (reservedInput.equals("no")) {
            		        reserved = false;
            		        break;
            		    } else {
            		        System.out.println("Invalid input. Please enter 'yes' or 'no':"); //validating input
            		        reservedInput = scanner.nextLine().toLowerCase();
            		    }
            		}
            		if (reserved == true) {
            			System.out.println("Which country is the dog in service?");
            			inServiceCountry = scanner.nextLine(); }
            		
            		//Instantiate a new Dog object           		
            		Dog newDog = new Dog(name, breed, gender, age, weight, aquisitionDate, aquisitionLocation, trainingStatus, reserved, inServiceCountry);
            		
            		System.out.println("Dog has been created."); 
            		dogList.add(newDog); // Add new dog to dog array list            		
            		return;           		
            	}
            	catch (InputMismatchException e) { // catch the InputMismatchException
					System.out.println("Invalid input. Please try again");
					scanner.next(); // Clear input buffer
					displayMenu();
					return;}
            	}            	
        // Complete intakeNewMonkey
        // For the project submission you must also  validate the input
	    // to make sure the monkey doesn't already exist and the species type is allowed
        public static void intakeNewMonkey(Scanner scanner) {
            System.out.println("What is the new monkey's name?");
            String name = scanner.nextLine();
            for(Monkey monkey : monkeyList) {
            	if(monkey.getName().equalsIgnoreCase(name)) { //check if monkey already exists
            		System.out.println("\nThis monkey already exists in our system.");
            		return;
            	}
            }
            //Instantiate and add the new monkey to the appropriate list
            String species;
            while (true) {
            	System.out.println("What species does the monkey belong to?");
            	species = scanner.nextLine().toLowerCase();
            	if (monkeySpecies.contains(species)) { // Check to see if species is a valid name
            		break; //Exit loop if the species is valid
            	}
            	else {
            		System.out.println("species not found, please enter a valid species."); //asks user for valid input
            	}
            }
                                 		            
            System.out.println("What is the new monkey's tail length?"); // Get user's input for monkey tail length
            Float tailLength = Float.parseFloat(scanner.nextLine());
            
            System.out.println("What is the new monkey's gender?\n"); // Get user input for monkey's gender
            String gender = scanner.nextLine();
            
            System.out.println("What is the new monkey's age?\n"); // Get user input for monkey's age
            String age = scanner.nextLine();
            
            System.out.println("What is the new monkey's weight?\n"); // Get user input for monkey's weight
            String weight = scanner.nextLine();
            
            System.out.println("What is the new monkey's acquisition date?"); // Get user input for monkey's aquisition date
            String acquisitionDate = scanner.nextLine();
            
            System.out.println("What is the new monkey's acquisition country?"); // Get user input for monkey's aquisition country
            String acquisitionCountry = scanner.nextLine();
            
            System.out.println("What is the new monkey's training status?"); // Get user input for monkey's training status
            String trainingStatus = scanner.nextLine();
            
            System.out.println("Is the new monkey reserved? Yes or No"); // Get user input for whether the monkey is reserved or not
            String reservedInput = scanner.nextLine().toLowerCase(); // Get user input for whether the monkey is reserved or not
            Boolean reserved;
            if (reservedInput.equals("yes")) {
                reserved = true;
            } else if (reservedInput.equals("no")) {
                reserved = false;
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                reserved = null; 
            }
            
            System.out.println("What is the new monkey's in service country?"); // Get user input for monkey's in service country
            String inServiceCountry = scanner.nextLine();
            
            System.out.println("What is the new monkey's height?\n"); // Get user input for monkey's height
            Float height = Float.parseFloat(scanner.nextLine());
            
            System.out.println("What is the new monkey's body length?\n"); // Get user input for monkey's body length
            Float bodyLength = Float.parseFloat(scanner.nextLine());
            
            Monkey newMonkey = new Monkey(name, species, gender, age, weight, acquisitionDate, acquisitionCountry, trainingStatus, reserved, inServiceCountry, tailLength, height, bodyLength);
            monkeyList.add(newMonkey);
        	}	
		// Complete reserveAnimal
        // You will need to find the animal by animal type and in service country
        public static void reserveAnimal(Scanner scanner) {
        	while (true) {
                System.out.println("What type of animal, dog or monkey?");
                String animalType = scanner.nextLine();
            
                // If the animal type is dog
                if (animalType.equalsIgnoreCase("dog")) {
                    System.out.println("Enter the name of the dog:");
                    String name = scanner.nextLine();
                    System.out.println("Enter the in-service country:");
                    String inServiceCountry = scanner.nextLine();
                
                    // Iterate through the dog list
                    for (Dog dog : dogList) {
                        // Check if the dog's name and in-service country match the input
                        if (dog.getName().equalsIgnoreCase(name) && dog.getInServiceCountry().equalsIgnoreCase(inServiceCountry)) {
                    	    boolean validInput = false; //flag to track if user input is valid
                            while (!validInput) {
                                System.out.println("Do you want to set the dog as reserved? (yes or no):");                      
                                String reservedInput = scanner.nextLine().toLowerCase(); // Convert input to lowercase                            

                                // Check if the input is "yes" or "no" and set the reserved status accordingly
                                if (reservedInput.equals("yes")) {
                                    dog.setReserved(true);
                                    validInput = true;  //flag to exit loop
                                } else if (reservedInput.equals("no")) {
                                    dog.setReserved(false);
                                    validInput = true;  //flag to exit loop
                                } else {
                                    System.out.println("Invalid input. Please enter 'yes' or 'no'.");                       
                                } 
                            }
                            System.out.println("Reserve Animal Complete. Thank You.");
                            return;
                        }
                    }
                    // If no matching dog is found
                    System.out.println("Dog doesn't exist in the specified in-service country.");
                }
         
                // If the animal type is monkey
                else if (animalType.equalsIgnoreCase("monkey")) {
                    System.out.println("Enter the name of the monkey:");
                    String name = scanner.nextLine();
                    System.out.println("Enter the in-service country:");
                    String inServiceCountry = scanner.nextLine();
                
                    // Iterate through the monkey list
                    for (Monkey monkey : monkeyList) {
                        // Check if the monkey's name and in-service country match the input
                        if (monkey.getName().equalsIgnoreCase(name) && monkey.getInServiceCountry().equalsIgnoreCase(inServiceCountry)) {
                    	    boolean validInput = false; //flag to track if user input is valid
                            while (!validInput) {
                    	        System.out.println("Do you want to set the monkey to reserved? (yes or no):");                  
                    	        String reservedInput = scanner.nextLine().toLowerCase(); // Convert input to lowercase
                    	    
                    	         // Check if the input is "yes" or "no" and set the reserved status accordingly
                                if (reservedInput.equals("yes")) {
                                    monkey.setReserved(true);
                                    validInput = true;  //flag to exit loop
                                } else if (reservedInput.equals("no")) {
                                    monkey.setReserved(false);
                                    validInput = true;  //flag to exit loop
                                } else {
                                    System.out.println("Invalid input. Please enter 'yes' or 'no'.");                                                  
                                } 
                            }
                            System.out.println("Reserve Animal Complete. Thank You.");
                            return;
                        }
                    }
                    // If no matching monkey is found
                    System.out.println("Monkey doesn't exist in the specified in-service country.");
                }
                // If the animal type is not dog or monkey
                else {
                System.out.println("Invalid animal type. Please enter 'dog' or 'monkey'.");                
                }
            }
        }
        
        // Complete printAnimals
        // Include the animal name, status, acquisition country and if the animal is reserved.
	// Remember that this method connects to three different menu items.
        // The printAnimals() method has three different outputs
        // based on the listType parameter
        // dog - prints the list of dogs
        // monkey - prints the list of monkeys
        // available - prints a combined list of all animals that are
        // fully trained ("in service") but not reserved 
	// Remember that you only have to fully implement ONE of these lists. 
	// The other lists can have a print statement saying "This option needs to be implemented".
	// To score "exemplary" you must correctly implement the "available" list.
        public static void printAnimals(char choice) {
            if (choice == '4') {
            	for (Dog dog : dogList) {
            		System.out.println("Name: " + dog.getName() + ", Status: " + dog.getTrainingStatus() + ", Acquisition Country: " + dog.getAcquisitionLocation() + ", Reserved: " + dog.getReserved());
            	}
            }
            else if (choice == '5') {
            	for (Monkey monkey : monkeyList) {
            		 System.out.println("Name: " + monkey.getName() + ", Status: " + monkey.getTrainingStatus() + ", Acquisition Country: " + monkey.getAcquisitionLocation() + ", Reserved: " + monkey.getReserved());           	
                }
            } 
            //Gets all animals from their lists. checks whether animals are reserved or not.
            else if(choice == '6') {
            	for (Dog dog: dogList) 
            		if (dog.getTrainingStatus().equalsIgnoreCase("in service") && !dog.getReserved()) {
                        System.out.println("Name: " + dog.getName() + ", Status: " + dog.getTrainingStatus() + ", Acquisition Country: " + dog.getAcquisitionLocation() + ", Reserved: " + dog.getReserved());
                    }
                }	            	
                for (Monkey monkey : monkeyList) {
                    if (monkey.getTrainingStatus().equalsIgnoreCase("in service") && !monkey.getReserved()) {
                        System.out.println("Name: " + monkey.getName() + ", Status: " + monkey.getTrainingStatus() + ", Acquisition Country: " + monkey.getAcquisitionLocation() + ", Reserved: " + monkey.getReserved());
                    }
                }
            }  
        {
                System.out.println("Invalid choice.");
            }
        
      
}

        

