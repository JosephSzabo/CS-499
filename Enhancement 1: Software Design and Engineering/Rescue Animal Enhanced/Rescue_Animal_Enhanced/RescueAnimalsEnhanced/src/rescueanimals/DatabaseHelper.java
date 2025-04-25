package rescueanimals;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    // JDBC URL for SQLite
    private static final String DATABASE_URL = "jdbc:sqlite:animal_rescue.db";

    // Create a connection to the database
    public static Connection connect() {
        try {
            return DriverManager.getConnection(DATABASE_URL); // Establishing connection to the database
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage()); // Handle any connection failure
            return null;
        }
    }

 // Create table for animals: Dogs and Monkeys
    public static void createTable() {
    	// SQL to create the animals table
        String createTableSQL = "CREATE TABLE IF NOT EXISTS animals (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                "name TEXT NOT NULL," +
                                "species TEXT," +
                                "type TEXT," +  // dog or monkey
                                "breed TEXT," +  // Specific to dogs
                                "tailLength REAL," +
                                "height REAL," +
                                "bodyLength REAL," +
                                "gender TEXT," +
                                "age TEXT," +
                                "weight TEXT," +
                                "acquisitionDate TEXT," +  // Ensure column name matches here
                                "acquisitionCountry TEXT," +
                                "trainingStatus TEXT," +
                                "reserved BOOLEAN," +
                                "inServiceCountry TEXT" +
                                ");";
        
        // Create the table
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Table 'animals' created successfully."); // table successfully created
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage()); // Trouble creating the table
        }
    }

    // Insert a new animal (Monkey or Dog)
    public static void insertAnimal(RescueAnimal animal) {
    	// SQL to instert a new animal into the database
        String sql = "INSERT INTO animals (name, breed, gender, age, weight, acquisitionDate, acquisitionCountry, trainingStatus, reserved, inServiceCountry, tailLength, height, bodyLength, species, type) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        // Insert animal data into the database
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Insert values depending on the type of animal
            pstmt.setString(1, animal.getName());  // name
            pstmt.setString(2, animal instanceof Dog ? ((Dog) animal).getBreed() : ((Monkey) animal).getSpecies());  // breed or species
            pstmt.setString(3, animal.getGender());  // gender
            pstmt.setString(4, animal.getAge());  // age
            pstmt.setString(5, animal.getWeight());  // weight
            pstmt.setString(6, animal.getAcquisitionDate());  // acquisitionDate
            pstmt.setString(7, animal.getAcquisitionLocation());  // acquisitionCountry
            pstmt.setString(8, animal.getTrainingStatus());  // trainingStatus
            pstmt.setBoolean(9, animal.getReserved());  // reserved
            pstmt.setString(10, animal.getInServiceCountry());  // inServiceCountry

            // Insert monkey-specific values if available
            if (animal instanceof Monkey) {
                pstmt.setFloat(11, ((Monkey) animal).getTailLength());  // tailLength
                pstmt.setFloat(12, ((Monkey) animal).getHeight());  // height
                pstmt.setFloat(13, ((Monkey) animal).getBodyLength());  // bodyLength
                pstmt.setString(14, ((Monkey) animal).getSpecies());  // species
                pstmt.setString(15, "monkey");  // type, for monkey
            } else {
                pstmt.setNull(11, Types.FLOAT);  // If not a monkey, set null for monkey-specific columns
                pstmt.setNull(12, Types.FLOAT);
                pstmt.setNull(13, Types.FLOAT);
                pstmt.setNull(14, Types.VARCHAR);
                pstmt.setString(15, "dog");  // type, for dog
            }

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error inserting animal: " + e.getMessage()); // Error handling output
        }
    }


    // Fetch all dogs from database
    public static List<RescueAnimal> fetchDogs() {
        List<RescueAnimal> dogs = new ArrayList<>();
        String selectSQL = "SELECT * FROM animals WHERE type = 'dog'"; // SQL to select only dog type
        
        // Fetch dogs and add them to the list
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(selectSQL)) {
            while (rs.next()) {
            	
            	// Create dog object from the results
                Dog dog = new Dog(rs.getString("name"), rs.getString("breed"), rs.getString("gender"), rs.getString("age"),
                                  rs.getString("weight"), rs.getString("acquisitionDate"), rs.getString("acquisitionCountry"),
                                  rs.getString("trainingStatus"), rs.getBoolean("reserved"), rs.getString("inServiceCountry"));
                dog.setId(rs.getInt("id")); // Set the ID
                dogs.add(dog); // Add to the list of dogs
            }
        } catch (SQLException e) {
            System.out.println("Error fetching dogs: " + e.getMessage()); // Error handling output
        }
        return dogs;
    }

    // Fetch all monkeys from database
    public static List<RescueAnimal> fetchMonkeys() {
        List<RescueAnimal> monkeys = new ArrayList<>();
        String selectSQL = "SELECT * FROM animals WHERE type = 'monkey'"; // SQL to select only monkey type
        
        // Fetch monkeys and add them to the list
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(selectSQL)) {
            while (rs.next()) {
            	// Create a Monkey object from the results
                Monkey monkey = new Monkey(rs.getString("name"), rs.getString("species"), rs.getString("gender"), rs.getString("age"),
                                           rs.getString("weight"), rs.getString("acquisitionDate"), rs.getString("acquisitionCountry"),
                                           rs.getString("trainingStatus"), rs.getBoolean("reserved"), rs.getString("inServiceCountry"),
                                           rs.getFloat("tailLength"), rs.getFloat("height"), rs.getFloat("bodyLength"));
                monkey.setId(rs.getInt("id")); // Set the ID
                monkeys.add(monkey); // Add to list of monkeys
            }
        } catch (SQLException e) {
            System.out.println("Error fetching monkeys: " + e.getMessage()); // Error handling output
        }
        return monkeys;
    }

 // Fetch all non-reserved animals from the database
    public static List<RescueAnimal> fetchNonReservedAnimals() {
        List<RescueAnimal> animals = new ArrayList<>();
        String selectSQL = "SELECT * FROM animals WHERE reserved = 0"; // SQL to select non-reserved animals
        
        // Fetch non-reserved animals and add them to the list
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(selectSQL)) {
            while (rs.next()) {
                String type = rs.getString("type"); // Get the type of animal
                RescueAnimal animal;
                
                // Check if 'type' is not null and then check if it's dog or monkey
                if (type != null && type.equals("dog")) {
                    animal = new Dog(rs.getString("name"), rs.getString("breed"), rs.getString("gender"), rs.getString("age"),
                                     rs.getString("weight"), rs.getString("acquisitionDate"), rs.getString("acquisitionCountry"),
                                     rs.getString("trainingStatus"), rs.getBoolean("reserved"), rs.getString("inServiceCountry"));
                } else if (type != null && type.equals("monkey")) { // Assuming you have a "monkey" type as well
                    animal = new Monkey(rs.getString("name"), rs.getString("species"), rs.getString("gender"), rs.getString("age"),
                                        rs.getString("weight"), rs.getString("acquisitionDate"), rs.getString("acquisitionCountry"),
                                        rs.getString("trainingStatus"), rs.getBoolean("reserved"), rs.getString("inServiceCountry"),
                                        rs.getFloat("tailLength"), rs.getFloat("height"), rs.getFloat("bodyLength"));
                } else {
            
                    continue; // Skip if type is null or unknown
                }
                
                animal.setId(rs.getInt("id")); // Set the ID
                animals.add(animal); // Add to the list of non-reserved animals
            }
        } catch (SQLException e) {
            System.out.println("Error fetching non-reserved animals: " + e.getMessage());
        }
        return animals;
    }

    // Reserve an animal by its ID
    public static void reserveAnimal(int animalId) {
        String updateSQL = "UPDATE animals SET reserved = 1 WHERE id = ?"; // SQL to set animal as reserved
        
        // Update reserve status of the animal
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
            pstmt.setInt(1, animalId); // Set the animal ID
            pstmt.executeUpdate(); 
            System.out.println("Animal reserved successfully.");
        } catch (SQLException e) {
            System.out.println("Error reserving animal: " + e.getMessage()); // Error handling output
        }
    }

    // Update an animal's information 
    public static void updateAnimal(RescueAnimal animal) {
    	// SQL to update animals data
        String updateSQL = "UPDATE animals SET name = ?, species = ?, breed = ?, tailLength = ?, height = ?, " +
                           "bodyLength = ?, gender = ?, age = ?, weight = ?, acquisitionDate = ?, acquisitionCountry = ?, " +
                           "trainingStatus = ?, reserved = ?, inServiceCountry = ? WHERE id = ?";
        
        // Update the animals details in the database
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
            pstmt.setString(1, animal.getName());
            pstmt.setString(2, animal.getSpecies());
            pstmt.setString(3, animal instanceof Dog ? ((Dog) animal).getBreed() : null);
            pstmt.setFloat(4, animal instanceof Monkey ? ((Monkey) animal).getTailLength() : 0);
            pstmt.setFloat(5, animal instanceof Monkey ? ((Monkey) animal).getHeight() : 0);
            pstmt.setFloat(6, animal instanceof Monkey ? ((Monkey) animal).getBodyLength() : 0);
            pstmt.setString(7, animal.getGender());
            pstmt.setString(8, animal.getAge());
            pstmt.setString(9, animal.getWeight());
            pstmt.setString(10, animal.getAcquisitionDate());
            pstmt.setString(11, animal.getAcquisitionLocation());
            pstmt.setString(12, animal.getTrainingStatus());
            pstmt.setBoolean(13, animal.getReserved());
            pstmt.setString(14, animal.getInServiceCountry());
            pstmt.setInt(15, animal.getId()); // Set the ID to update the specific animal
            
            pstmt.executeUpdate();
            System.out.println("Animal updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating animal: " + e.getMessage());
        }
    }

    // Delete an animal by ID
    public static void deleteAnimal(int animalId) {
        String deleteSQL = "DELETE FROM animals WHERE id = ?"; // SQL to delete animal by ID
        
        // Execute the deletion of animal
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
            pstmt.setInt(1, animalId); // Set animal ID to be deleted
            pstmt.executeUpdate();
            System.out.println("Animal deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting animal: " + e.getMessage());
        }
    }

    // Search for animals by name 
    public static List<RescueAnimal> searchAnimalsByName(String name) {
        List<RescueAnimal> animals = new ArrayList<>();
        String selectSQL = "SELECT * FROM animals WHERE name = ?";  // SQL to search by name

        // Search animals by name and add them to the list
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(selectSQL)) {
            stmt.setString(1, name); // Set the name parameter
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String type = rs.getString("type"); // Get the type of animal
                
                // Check for null type before using it in comparison checking dog and monkey types
                if (type != null && type.equals("dog")) {
                    RescueAnimal animal = new Dog(
                        rs.getString("name"), rs.getString("breed"), rs.getString("gender"), 
                        rs.getString("age"), rs.getString("weight"), rs.getString("acquisitionDate"),
                        rs.getString("acquisitionCountry"), rs.getString("trainingStatus"),
                        rs.getBoolean("reserved"), rs.getString("inServiceCountry")
                    );
                    animal.setId(rs.getInt("id"));
                    animals.add(animal);
                } else if (type != null && type.equals("monkey")) {
                    RescueAnimal animal = new Monkey(
                        rs.getString("name"), rs.getString("species"), rs.getString("gender"),
                        rs.getString("age"), rs.getString("weight"), rs.getString("acquisitionDate"),
                        rs.getString("acquisitionCountry"), rs.getString("trainingStatus"),
                        rs.getBoolean("reserved"), rs.getString("inServiceCountry"),
                        rs.getFloat("tailLength"), rs.getFloat("height"), rs.getFloat("bodyLength")
                    );
                    animal.setId(rs.getInt("id"));
                    animals.add(animal);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching animals by name: " + e.getMessage());
        }
        return animals;
    }

    // Fetch animal by ID
    public static RescueAnimal fetchAnimalById(int animalId) {
        String selectSQL = "SELECT * FROM animals WHERE id = ?"; // SQL to fetch an animal by ID
        
        // Fetch an animal from the database by ID
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
            pstmt.setInt(1, animalId); // set animal ID parameter
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String type = rs.getString("type"); // Get the type of animal
                RescueAnimal animal;
                
                // create animal object based on its type
                if (type.equals("dog")) {
                    animal = new Dog(rs.getString("name"), rs.getString("breed"), rs.getString("gender"), rs.getString("age"),
                                     rs.getString("weight"), rs.getString("acquisitionDate"), rs.getString("acquisitionCountry"),
                                     rs.getString("trainingStatus"), rs.getBoolean("reserved"), rs.getString("inServiceCountry"));
                } else {
                    animal = new Monkey(rs.getString("name"), rs.getString("species"), rs.getString("gender"), rs.getString("age"),
                                        rs.getString("weight"), rs.getString("acquisitionDate"), rs.getString("acquisitionCountry"),
                                        rs.getString("trainingStatus"), rs.getBoolean("reserved"), rs.getString("inServiceCountry"),
                                        rs.getFloat("tailLength"), rs.getFloat("height"), rs.getFloat("bodyLength"));
                }
                animal.setId(rs.getInt("id")); // Set the ID
                return animal; // Return animal object
            }
        } catch (SQLException e) {
            System.out.println("Error fetching animal by ID: " + e.getMessage());
        }
        return null; // Animal not found
    }
}
