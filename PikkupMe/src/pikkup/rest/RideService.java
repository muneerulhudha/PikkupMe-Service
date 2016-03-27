package pikkup.rest;

import static com.mongodb.client.model.Filters.and;
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

import org.bson.Document;

import pikkup.base.DataBaseManager;
import pikkup.model.Ride;
import pikkup.util.SmsSender;
import pikkup.util.Util;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.twilio.sdk.TwilioRestException;

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
	@Produces(MediaType.APPLICATION_JSON)
	public String postRide(@FormParam("username") String username, @FormParam("origin") String origin, @FormParam("destination") String destination, @FormParam("date") String date, @FormParam("seats") int seats) throws TwilioRestException {
		DataBaseManager manager = DataBaseManager.getInstance();
		MongoCollection<Document> collection = manager.getDatabase().getCollection("rides");
		String result = "";
		
		Document doc = collection.find(and(eq("drivername", username), eq("destination", destination), eq("date", date))).first();
		if(doc == null){
			Document newDoc = new Document("drivername", username)
					.append("origin", origin)
					.append("destination", destination)
					.append("date", date)
					.append("seats", seats);
		
			collection.insertOne(newDoc);
			
			//MATCHMAKING
			MongoCollection<Document> requests = manager.getDatabase().getCollection("requests");
			MongoCursor<Document> requestMatches = requests.find(and(eq("destination", destination), eq("date", date))).iterator();
			MongoCollection<Document> matchCollection = manager.getDatabase().getCollection("matches");
			
			while(requestMatches.hasNext()) {
				Document request = requestMatches.next();
				
				Document matchDocument = new Document("drivername", username)
				.append("ridername", request.get("ridername").toString())
				.append("status", 0)
				.append("destination", request.get("destination").toString());
	
				matchCollection.insertOne(matchDocument);
				
				SmsSender.SendSms(request.get("phoneno").toString(), "The driver "+username+" registered a trip to "+destination+" on "+date);
			}
			//END MATCHMAKING
			
			
			result = "{\"success\": true}";	
			return result;
		}else{
			result = "{\"success\": false, \"message\": \"Ride already exists\"}";
			return result;
		}
	}

}
