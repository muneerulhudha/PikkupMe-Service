package pikkup.base;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;


public class DBManager {

	private static DBManager instance = null;
	
	MongoClient mongoClient;
	MongoDatabase database;

	protected DBManager() {
		MongoClientURI connectionString = new MongoClientURI("mongodb://muneer:1234567@ds023078.mlab.com:23078/pikkup");
		mongoClient = new MongoClient(connectionString);
		database = mongoClient.getDatabase("pikkup");
	}

	public static DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
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