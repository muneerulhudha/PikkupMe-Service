package pikkup.rest;

import static com.mongodb.client.model.Filters.eq;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

@Path("locations")
public class LocationService {
	
	@SuppressWarnings("resource")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getLocations() {
		
		String output = "[";
		MongoClientURI connectionString = new MongoClientURI("mongodb://muneer:1234567@ds023078.mlab.com:23078/pikkup");
		MongoClient mongoClient = new MongoClient(connectionString);

		MongoDatabase database = mongoClient.getDatabase("pikkup");
		
		MongoCollection<Document> collection = database.getCollection("locations");
		
		MongoCursor<Document> cursor = collection.find().iterator();
		try {
		    while (cursor.hasNext()) {
		        output += cursor.next().toJson();
		        output += ",";
		    }
		} finally {
		    cursor.close();
		}

		output = output.substring(0, output.length() - 1);
		output += "]";
		
		return output;
	}
	
	@SuppressWarnings("resource")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{name}")
	public String getLocation(@PathParam("name") String locationName) {
		
		MongoClientURI connectionString = new MongoClientURI("mongodb://muneer:1234567@ds023078.mlab.com:23078/pikkup");
		MongoClient mongoClient = new MongoClient(connectionString);
		String output = "";
		
		MongoDatabase database = mongoClient.getDatabase("pikkup");
		
		MongoCollection<Document> collection = database.getCollection("locations");
		
		Document doc = collection.find(eq("name", locationName)).first();

		output += doc.toJson();
		
		return output;
	}
	
}
