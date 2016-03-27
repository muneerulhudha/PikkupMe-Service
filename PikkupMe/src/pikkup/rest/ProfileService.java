package pikkup.rest;

import static com.mongodb.client.model.Filters.eq;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.bson.Document;

import pikkup.base.DBManager;
import pikkup.model.Profile;
import pikkup.util.Util;

import com.mongodb.client.MongoCollection;

@Path("profile")
public class ProfileService {
	
	@GET
	@Path("/{username}")
	public String getProfile(@PathParam("username") String username) {
		
		DBManager manager = DBManager.getInstance();
		MongoCollection<Document> collection = manager.getDatabase().getCollection("users");
		
		Document doc = collection.find(eq("username", username)).first();
		
		Profile profile = new Profile(doc.get("username").toString(), doc.get("email").toString(), doc.get("college").toString(), doc.get("address").toString(), doc.get("phoneno").toString());
		
		return Util.toJson(profile);
	}
	
	@POST
	@Consumes("application/x-www-form-urlencoded")
	public void putProfile(@FormParam("username") String username, @FormParam("email") String email, @FormParam("college") String college, @FormParam("address") String address, @FormParam("phoneNumber") String phoneNumber) {
		
		DBManager manager = DBManager.getInstance();
		MongoCollection<Document> collection = manager.getDatabase().getCollection("users");
		
		collection.updateOne(new Document("username", username),
		        new Document("email", email)
		                .append("college", college)
		                .append("address", address)
		                .append("phoneno", phoneNumber)
				);
		
	}
	
}