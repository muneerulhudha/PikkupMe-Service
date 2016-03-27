package pikkup.rest;

import static com.mongodb.client.model.Filters.eq;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import pikkup.base.DataBaseManager;
import pikkup.core.MatchMaking;
import pikkup.model.Ride;
import pikkup.util.Util;

@Path("ride")
public class RideService {
	
	@GET
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getRide(@PathParam("username") String username) {
		
		DataBaseManager manager = DataBaseManager.getInstance();
		MongoCollection<Document> collection = manager.getDatabase().getCollection("rides");
		
		MongoCursor<Document> documents = collection.find(eq("drivername", username)).iterator();
		
		List<Ride> rides = new ArrayList<Ride>();
		while(documents.hasNext()) {
			Document doc = documents.next();
			
			Date date = Util.stringToDate(doc.get("date").toString());
			SimpleDateFormat format = new SimpleDateFormat("E, dd MMM yyyy");
			String dateString = format.format(date);
			
			
			Ride ride = new Ride();
			
			ride.setOrigin(doc.get("origin").toString());
			ride.setDestination(doc.get("destination").toString());
			ride.setDesiredArrival(dateString);
			ride.setAvailableSeats(new Integer((doc.get("seats").toString())));
			ride.setDriverName(doc.get("drivername").toString());
			
			rides.add(ride);
		}
		
		return Util.toJson(rides);
	}
	
	@POST
	@Consumes("application/x-www-form-urlencoded")
	public Response postRide(@FormParam("username") String username, @FormParam("origin") String origin, @FormParam("destination") String destination, @FormParam("date") String date, @FormParam("seats") int seats) {
		DataBaseManager manager = DataBaseManager.getInstance();
		MongoCollection<Document> collection = manager.getDatabase().getCollection("rides");
		
		Document newDoc = new Document("drivername", username)
					.append("origin", origin)
					.append("destination", destination)
					.append("date", date)
					.append("seats", seats);
		
		collection.insertOne(newDoc);
		
		MatchMaking.process();

		return Response.status(202).entity("SUCCESS").build();
	}

}
