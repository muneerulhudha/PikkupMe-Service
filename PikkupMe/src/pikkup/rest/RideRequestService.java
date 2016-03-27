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
import pikkup.model.RideRequest;
import pikkup.util.SmsSender;
import pikkup.util.Uber;
import pikkup.util.Util;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.twilio.sdk.TwilioRestException;

@Path("request")
public class RideRequestService {
	
	@GET
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getRideRequest(@PathParam("username") String username) {
		
		DataBaseManager manager = DataBaseManager.getInstance();
		MongoCollection<Document> collection = manager.getDatabase().getCollection("requests");
		
		MongoCursor<Document> documents = collection.find(eq("ridername", username)).iterator();
		
		List<RideRequest> requests = new ArrayList<RideRequest>();
		while(documents.hasNext()) {
			Document doc = documents.next();
			
			Date date = Util.stringToDate(doc.get("date").toString());
			SimpleDateFormat format = new SimpleDateFormat("E, dd MMM yyyy");
			String dateString = format.format(date);
			
			RideRequest request = new RideRequest();
			
			request.setOrigin(doc.get("origin").toString());
			request.setDestination(doc.get("destination").toString());
			request.setDesiredArrival(dateString);
			request.setRiderName(doc.get("ridername").toString());
			
			requests.add(request);
		}
		
		return Util.toJson(requests);
	}
	
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public String postRideRequest(@FormParam("username") String username, @FormParam("origin") String origin, @FormParam("destination") String destination, @FormParam("date") String date) throws TwilioRestException {
		DataBaseManager manager = DataBaseManager.getInstance();
		MongoCollection<Document> collection = manager.getDatabase().getCollection("requests");
		String result = "";
		
		Document doc = collection.find(and(eq("ridername", username), eq("destination", destination), eq("date", date))).first();
		if(doc == null){
			Document newDoc = new Document("ridername", username)
					.append("origin", origin)
					.append("destination", destination)
					.append("date", date);
		
			collection.insertOne(newDoc);
			
			//MATCHMAKING
			MongoCollection<Document> rides = manager.getDatabase().getCollection("rides");
			Document ride = rides.find(and(eq("destination", destination), eq("date", date))).first();
			MongoCollection<Document> matchCollection = manager.getDatabase().getCollection("matches");
			
			if(ride == null) {
				MongoCollection<Document> requests = manager.getDatabase().getCollection("requests");
				long count = requests.count(and(eq("destination", destination), eq("date", date)));
				
				MongoCursor<Document> req = rides.find(and(eq("destination", destination), eq("date", date))).iterator();
				
				while(req.hasNext()) {
					Document request = req.next();
					
					if(count > 4) {
						SmsSender.SendSms(request.get("phoneno").toString(), "No rides available. Consider taking a uber with other riders. Estimated cost: "+ Uber.getCost()/4);
					} else {
						SmsSender.SendSms(request.get("phoneno").toString(), "No rides available. Consider taking a uber with other riders. Estimated cost: "+ Uber.getCost()/count);
					}
				}
			}
			
			Document matchDocument = new Document("ridername", username)
				.append("drivername", ride.get("drivername").toString())
				.append("status", 0)
				.append("destination", ride.get("destination").toString());

			matchCollection.insertOne(matchDocument);
			
			SmsSender.SendSms(ride.get("phoneno").toString(), "The rider "+username+" requested a trip to "+destination+" on "+date);
			//END MATCHMAKING
			
			result = "{\"success\": true}";	
			return result;
		}else{
			result = "{\"success\": false, \"message\": \"Request already exists\"}";
			return result;
		}
		
	}

}
