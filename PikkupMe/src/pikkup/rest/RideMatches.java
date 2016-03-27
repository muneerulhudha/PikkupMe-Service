package pikkup.rest;

import static com.mongodb.client.model.Filters.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import pikkup.base.DataBaseManager;

@Path("ridematches")
public class RideMatches {

	@GET
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getRides(@PathParam("username") String username) {
		
		DataBaseManager manager = DataBaseManager.getInstance();
		MongoCollection<Document> collection = manager.getDatabase().getCollection("matches");
		MongoCollection<Document> rides = manager.getDatabase().getCollection("rides");
		int flag = 0;
		StringBuilder result = new StringBuilder();
		
		result.append("[");
		
		MongoCursor<Document> matches = collection.find(eq("ridername", username)).iterator();
		
		while(matches.hasNext()) {
			flag =1;
			Document match = matches.next();
			Document ride = rides.find(and(eq("drivername", match.get("drivername").toString()), eq("destination", match.get("destination").toString()))).first();
			
			String date = ride.get("date").toString();
			String drivername = match.get("drivername").toString();
			String destination = match.get("destination").toString();
			
			result.append("{ \"destination\" : \"").append(destination).append("\", ")
				.append("\"drivername\" : \"").append(drivername).append("\", ")
				.append("\"date\" : \"").append(date).append("\"},");

		}
		
		if(flag == 1)
			result = result.replace(result.length() - 1, result.length(), "");
		result.append("]");
		
		return result.toString();
	}
	
}
