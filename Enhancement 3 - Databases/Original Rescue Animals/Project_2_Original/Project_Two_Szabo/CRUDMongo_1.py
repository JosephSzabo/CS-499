
from pymongo import MongoClient
from bson.objectid import ObjectId

class AnimalShelter(object):
    """ CRUD operatoins for Animal collection in MongoDB """
    
    def __init__(self, username, password, host='127.0.0.1', port=27017, db_name='AAC', collection_name='animals'):
        #Initializing the MongoClient. This helps to
        #access the MongoDB databases and collections.
        # This is hard-wired to use the aac database, the
        # animals collection, and the aac user.
        # Definitions of the connection string variables are 
        # unique to the individual Apporto environment.
        #
        # You must edit the connection variables below to reflect
        # your own instance of MongoDB!
        #
        # Connection Variables
        # 
        
        USER = 'aacuser'
        PASS = 'SNHU1234'
        HOST = 'localhost'
        PORT = 27017
        DB = 'AAC'
        COL = 'animals'

         # MongoDB Atlas URI
        uri = f"mongodb+srv://{USER}:{PASS}@project2.klpd3by.mongodb.net/{DB}?retryWrites=true&w=majority&appName=Project2"
        
        # Test Connection
        try:
            client = MongoClient(uri)
            print("Connected successfully to MongoDB!")
        except Exception as e:
            # Catch and print any connection errors
            print(f"Error: {e}")
        
        # Initialize Connection
        # mongodb+srv://aacuser:SNHU1234@project2.klpd3by.mongodb.net/?retryWrites=true&w=majority&appName=Project2
  # Initialize the MongoDB client and select the database and collection
        self.client = MongoClient(uri)
        self.database = self.client[DB]
        self.collection = self.database[COL]
        
    #Complete this create method to implement the C in CRUD
    """
    Input: data (dictionary format)
    Output: Returns True if the insertion was successful, else false.
    """
    def create(self, animal_data):
        if animal_data is not None:
            # Insert the privided input into the 'animals' collection
            self.database.animals.insert_one(animal_data)
            return True
        else:
            # Raise an exception if the data is empty or none
            raise Exception("Nothing to save, because data parameter is empty")
            return False

    #Create to implement the R in CRUD
    """
    Read animal documents for the collection
    Input: animal_data (dictionary format) The criteria to find specific documents
    Output: Returns a list of documents matching the input
    If no filter is provided, returns all documents
    """
    def read(self, animal_data): 
        if animal_data is not None:
            # Find documents matching provided input
            results = self.collection.find(animal_data)
        else:
            # If no filter is provided, finds all documents in collection
            results = self.collection.find({})
            
        # Convert cursor to list
        result_list = list(results)  
        
        if result_list:
            # Return the list of found documents
            return result_list
        else:
            # Return an empty list if no documents are found.
            return [] 
        
    # Create to implement the U in CRUD
    """
    Update an animal document in the collection
    Input: key-value pair for finding documents and antoher key-value pair to update     the document.
    Output: Return the number of objects modified
    animal_data: filter to find the document to update
    update_data: The data that will get updated in the found document
    return: The number of objects modified
    """
    def update(self, animal_data, new_data):
        if animal_data and new_data:
            # Use update_many to update multiple documents matching the filter with                 the new data
            result = self.collection.update_many(animal_data, {"$set": new_data})
            # Return the count of modified documents
            return result.modified_count
        else:
            # raise exception is filter_data or update_data is missing
            raise Exception("Animal data and update data cannot be empty.")
            
            
    #Create to implement the D in CRUD
    """
    Delete an animal document in the collection
    Input: key-value pair for finding the right document to delete.
    output: Return the number of objects removed from the collection
    filter_data: key-value pair to find the document to delete.
    Return: the number of objects removed from the collection
    """
    def delete(self, animal_data):
        if animal_data:
            # Use delete_many to delete documents matching the input
            result = self.collection.delete_many(animal_data)
            # Return the count of documents deleted
            return result.deleted_count
        else:
            # Raise an exception if the filter criteria data is missing
            raise Exception("Filter data cannot be empty.")
            