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

@Path("requestmatches")
public class RequestMatches {

	@GET
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getRides(@PathParam("username") String username) {
		
		DataBaseManager manager = DataBaseManager.getInstance();
		MongoCollection<Document> collection = manager.getDatabase().getCollection("matches");
		MongoCollection<Document> requests = manager.getDatabase().getCollection("requests");
		int flag = 0;
		StringBuilder result = new StringBuilder();
		
		result.append("[");
		
		MongoCursor<Document> matches = collection.find(eq("drivername", username)).iterator();
		
		while(matches.hasNext()) {
			flag = 1;
			Document match = matches.next();
			Document request = requests.find(and(eq("ridername", match.get("ridername").toString()), eq("destination", match.get("destination").toString()))).first();
			
			String date = request.get("date").toString();
			String ridername = match.get("ridername").toString();
			String destination = match.get("destination").toString();
			
			result.append("{ \"destination\" : \"").append(destination).append("\", ")
				.append("\"ridername\" : \"").append(ridername).append("\", ")
				.append("\"date\" : \"").append(date).append("\"},");

		}
		if(flag == 1)
			result = result.replace(result.length() - 1, result.length(), "");
		
		result.append("]");
		
		return result.toString();
	}
	
}
