package pikkup.core;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class Scheduler extends TimerTask implements ServletContextListener {
	
	static Timer timer = new Timer();
	
	@Override
	public void run() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 1);
		
//		DataBaseManager manager = DataBaseManager.getInstance();
//		MongoCollection<Document> collection = manager.getDatabase().getCollection("rides");
//		MongoCollection<Document> collection2 = manager.getDatabase().getCollection("requests");
//		
//		MongoCursor<Document> documents = collection.find(eq("BYDATE!!!", DAAAAAATE)).iterator();
//		MongoCursor<Document> documents2 = collection2.find(eq("BYDATE!!!", DAAAAAATE)).iterator();
		
		
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