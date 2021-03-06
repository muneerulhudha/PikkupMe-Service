package pikkup.rest;

import static com.mongodb.client.model.Filters.eq;

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

import pikkup.base.DataBaseManager;
import pikkup.model.Profile;
import pikkup.util.Util;

import com.mongodb.client.MongoCollection;

@Path("profile")
public class ProfileService {
	
	@GET
	@Path("/{username}")
	public String getProfile(@PathParam("username") String username) {
		
		DataBaseManager manager = DataBaseManager.getInstance();
		MongoCollection<Document> collection = manager.getDatabase().getCollection("users");
		
		Document doc = collection.find(eq("username", username)).first();
		
		Profile profile = new Profile(doc.get("username").toString(), doc.get("email").toString(), doc.get("college").toString(), doc.get("address").toString(), doc.get("phoneno").toString());
		
		return Util.toJson(profile);
	}
	
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public String putProfile(@FormParam("username") String username, @FormParam("email") String email, @FormParam("college") String college, @FormParam("address") String address, @FormParam("phoneno") String phoneNumber) {
		
		DataBaseManager manager = DataBaseManager.getInstance();
		MongoCollection<Document> collection = manager.getDatabase().getCollection("users");
		String result="";
		
		Document existingDoc = collection.find(eq("username", username)).first();
		
		Document doc = new Document("username", username)
					.append("password", existingDoc.get("password"))
					.append("email", email)
					.append("college", college)
	                .append("address", address)
	                .append("phoneno", phoneNumber);
		
		collection.updateOne(eq("username", username), new Document("$set", doc));
		
		result = "{\"success\": true}";	
		return result;
		
	}
	
}