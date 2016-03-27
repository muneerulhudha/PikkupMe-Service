package pikkup.rest;

import static com.mongodb.client.model.Filters.eq;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import pikkup.util.Password;

@Path("login")
public class LoginService {

	@SuppressWarnings("resource")
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public String login(@FormParam("username") String username, @FormParam("password") String password) throws Exception {
		
		MongoClientURI connectionString = new MongoClientURI("mongodb://muneer:1234567@ds023078.mlab.com:23078/pikkup");
		MongoClient mongoClient = new MongoClient(connectionString);
		String result = "";
		
		MongoDatabase database = mongoClient.getDatabase("pikkup");
		MongoCollection<Document> collection = database.getCollection("users");
		
		Document doc = collection.find(eq("username", username)).first();
		
		if(doc != null){
			String actualPass = doc.get("password").toString();			
			if(Password.check(password, actualPass)){
				result = "{\"success\": true}";	
				return result;
			}else{
				result = "{\"success\": false, \"message\": \"Invalid Password\"}";
				return result;
			}
		}else{
			result = "{\"success\": false, \"message\": \"Invalid Username\"}";
			return result;			
		}

	}

}
