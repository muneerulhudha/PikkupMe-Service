package pikkup.rest;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.bson.Document;

import pikkup.base.DataBaseManager;
import pikkup.core.MatchMaking;
import pikkup.model.RideRequest;
import pikkup.util.Util;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

@Path("request")
public class RideRequestService {
	
	@GET
	@Path("/{username}")
	public String getRideRequest(@PathParam("username") String username) {
		
		DataBaseManager manager = DataBaseManager.getInstance();
		MongoCollection<Document> collection = manager.getDatabase().getCollection("requests");
		
		MongoCursor<Document> documents = collection.find(eq("ridername", username)).iterator();
		
		List<RideRequest> requests = new ArrayList<RideRequest>();
		while(documents.hasNext()) {
			Document doc = documents.next();
			
			RideRequest request = new RideRequest();
			
			request.setOrigin(doc.get("origin").toString());
			request.setDestination(doc.get("destination").toString());
			request.setDesiredArrival(Util.stringToDate(doc.get("date").toString()));
			request.setRiderName(doc.get("ridername").toString());
			
			requests.add(request);
		}
		
		return Util.toJson(requests);
	}
	
	@POST
	@Consumes("application/x-www-form-urlencoded")
	public Response postRideRequest(@FormParam("username") String username, @FormParam("origin") String origin, @FormParam("destination") String destination, @FormParam("date") String date) {
		DataBaseManager manager = DataBaseManager.getInstance();
		MongoCollection<Document> collection = manager.getDatabase().getCollection("requests");
		
		Document newDoc = new Document("ridername", username)
					.append("origin", origin)
					.append("destination", destination)
					.append("date", date);
		
		collection.insertOne(newDoc);
		
		MatchMaking.process();

		return Response.status(202).entity("SUCCESS").build();
	}

}
