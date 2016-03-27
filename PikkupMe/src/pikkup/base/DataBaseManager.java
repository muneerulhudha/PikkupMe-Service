package pikkup.base;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;


public class DataBaseManager {

	private static DataBaseManager instance = null;
	
	MongoClient mongoClient;
	MongoDatabase database;

	protected DataBaseManager() {
		MongoClientURI connectionString = new MongoClientURI("mongodb://muneer:1234567@ds023078.mlab.com:23078/pikkup");
		mongoClient = new MongoClient(connectionString);
		database = mongoClient.getDatabase("pikkup");
	}

	public static DataBaseManager getInstance() {
		if (instance == null) {
			instance = new DataBaseManager();
		}
		return instance;
	}

	public MongoClient getMongoClient() {
		return mongoClient;
	}

	public MongoDatabase getDatabase() {
		return database;
	}

}