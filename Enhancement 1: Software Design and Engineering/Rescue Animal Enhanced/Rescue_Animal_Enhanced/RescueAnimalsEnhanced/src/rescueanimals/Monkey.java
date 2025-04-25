package rescueanimals;

public class Monkey extends RescueAnimal {

    // Instance variables specific to monkey class
    private float tailLength; 
    private float height;
    private float bodyLength;
    private String species;

    // Constructor to initialize monkey information
    public Monkey(
        String name, 
        String species, 
        String gender, 
        String age, 
        String weight,
        String acquisitionDate, 
        String acquisitionCountry, 
        String trainingStatus,
        boolean reserved,  
        String inServiceCountry, 
        float tailLength, 
        float height, 
        float bodyLength) {

    	// Set inherited attributes using mutator methods from the RescueAnimal class
        setName(name);
        setSpecies(species);  
        setGender(gender);
        setAge(age);
        setWeight(weight);
        setAcquisitionDate(acquisitionDate);
        setAcquisitionLocation(acquisitionCountry);
        setTrainingStatus(trainingStatus);
        setReserved(reserved);
        setInServiceCountry(inServiceCountry);
        
        // Set specific monkey attributes using provided values
        this.tailLength = tailLength;  // Set tail length
        this.height = height;          // Set height
        this.bodyLength = bodyLength;  // Set body length  
    }

    // Accessor method for species
    public String getSpecies() {
        return species;
    }

    // Mutator method for species
    @Override
    public void setSpecies(String species) {
        this.species = species;
    }

    // Accessor method for tail length
    public float getTailLength() {
        return tailLength;
    }

    // Mutator method for tail length
    public void setTailLength(float tailLength) {
        this.tailLength = tailLength;
    }

    // Accessor method for height
    public float getHeight() {
        return height;
    }

    // Mutator method for height
    public void setHeight(float height) {
        this.height = height;
    }

    // Accessor method for body length
    public float getBodyLength() {
        return bodyLength;
    }

    // Mutator method for body length
    public void setBodyLength(float bodyLength) {
        this.bodyLength = bodyLength;
    }
}
