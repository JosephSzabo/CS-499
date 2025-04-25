package rescueanimals;

public class Dog extends RescueAnimal {

    // Instance variable specific for dog class
    private String breed;

    // Constructor for creating dog object
    public Dog(String name, String breed, String gender, String age,
               String weight, String acquisitionDate, String acquisitionCountry,
               String trainingStatus, boolean reserved, String inServiceCountry) {
    	
    	// Set variables inherited from the RescueAnimal class
        setName(name);  
        setBreed(breed);  //specific to dog class
        setGender(gender);
        setAge(age);
        setWeight(weight);
        setAcquisitionDate(acquisitionDate);
        setAcquisitionLocation(acquisitionCountry);
        setTrainingStatus(trainingStatus);
        setReserved(reserved);
        setInServiceCountry(inServiceCountry);
    }

    // Accessor Method for breed
    public String getBreed() {
        return breed;
    }

    // Mutator Method for breed
    public void setBreed(String dogBreed) {
        breed = dogBreed;
    }
}
