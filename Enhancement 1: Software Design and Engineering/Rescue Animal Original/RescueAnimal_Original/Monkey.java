// Author Name: Joseph Szabo

// Date: April 11, 2024

// Course ID: IT-145

// Brief Description: This is the monkey class for module 5


//  monkey class, inheriting from RescueAnimal
public class Monkey extends RescueAnimal {

    // Instance variable 
    private float tailLength; 
    private float height;
    private float bodyLength;
	private static String species;
    

    // constructor to initialize monkey information
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
        double tailLength, 
        double height, 
        double bodyLength) {
	}
   
        
    public static String getSpecies()  {            //Accessor method for species attribute
        return species;
}

    public void setSpecies(String species)  {     //Mutator method for species attribute
        this.species = species;
}
    public float getTailLength()  {     //Accessor method for tail length attribute
        return tailLength;
}

    public void setTailLength(int tailLength) {   // Mutator method for tail length 
        this.tailLength = tailLength;
}

    public float getHeight()  {          //Accessor method for height attribute
        return height;
}

    public void setHeight(int height)  {     //mutator method for height attribute
        this.height = height;
}

    public float getBodyLength()  {       //Accessor method for body length attribute
        return bodyLength;
}
 
    public void setBodyLength(int bodyLength)  {       //Mutator method or body length attribute
        this.bodyLength = bodyLength;
}
}
