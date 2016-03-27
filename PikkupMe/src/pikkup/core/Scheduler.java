package pikkup.core;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.bson.Document;

import pikkup.base.DataBaseManager;
import pikkup.model.Ride;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;


public class Scheduler extends TimerTask implements ServletContextListener {
	
	static Timer timer = new Timer();
	
	@Override
	public void run() {
//		Calendar calendar = Calendar.getInstance();
//		calendar.add(Calendar.DATE, 1);
//		
//		DataBaseManager manager = DataBaseManager.getInstance();
//		
//		MongoCollection<Document> rideCollection = manager.getDatabase().getCollection("rides");
//		MongoCursor<Document> allRides = rideCollection.find().iterator();
//		
//		MongoCollection<Document> requestCollection = manager.getDatabase().getCollection("requests");
//		MongoCursor<Document> allRequests = requestCollection.find().iterator();
//		
//		List<Ride> matchedRides = new ArrayList<Ride>();
//		while(allRides.hasNext()) {
//			Document rideDocument = allRides.next();
//			Ride ride = new Ride(rideDocument.get("origin").toString(), rideDocument.get("destination").toString(), rideDocument.get("date").toString(), new Integer(rideDocument.get("seats").toString()), rideDocument.get("drivername").toString());
//			
//			List<String> riderNames = new ArrayList<String>();
//			while(allRequests.hasNext() && ride.getAvailableSeats() > 0) {
//				Document requestDocument = allRequests.next();
//				
//				if(rideDocument.get("date").toString().equals(requestDocument.get("date").toString())
//				&& rideDocument.get("destination").toString().equals(requestDocument.get("destination").toString())) {
//					
//					riderNames.add(requestDocument.get("ridername").toString());
//					ride.setAvailableSeats(ride.getAvailableSeats() - 1);
//					
//					
//				}
//			}
//			ride.setRiderNames(riderNames);
//			matchedRides.add(ride);
//		}
//		
//		MongoCursor<Document> requests1 = requestCollection.find().iterator();
//		MongoCursor<Document> requests2 = requestCollection.find().iterator();
//		
//		List<Ride> uberRides = new ArrayList<Ride>();
//		while(requests1.hasNext()) {
//			Document request1 = requests1.next();
//			Ride ride = new Ride(request1.get("origin").toString(), request1.get("destination").toString(), request1.get("date").toString(), 4, request1.get("ridername").toString());
//			
//			List<String> riderNames = new ArrayList<String>();
//			riderNames.add(request1.get("ridername").toString());
//			while(requests2.hasNext()) {
//				Document request2 = requests2.next();
//				
//				if(!request1.get("ridername").toString().equals(request2.get("ridername").toString())
//						&& request1.get("date").toString().equals(request2.get("date").toString())
//						&& request1.get("destination").toString().equals(request2.get("destination").toString())) {
//					
//					riderNames.add(request2.get("ridername").toString());
//					ride.setAvailableSeats(ride.getAvailableSeats() - 1);
//					
//				}
//			}
//			
//			ride.setRiderNames(riderNames);
//			uberRides.add(ride);
//		}
//		
//		
//		
	}
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		Calendar calendar = Calendar.getInstance();
		
		calendar.set(Calendar.HOUR_OF_DAY, 18);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);
		
		timer.schedule(new Scheduler(), calendar.getTime(), 24*60*60*1000);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}
}