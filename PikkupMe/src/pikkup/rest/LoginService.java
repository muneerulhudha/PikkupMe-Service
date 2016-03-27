package pikkup.rest;

import static com.mongodb.client.model.Filters.eq;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
	public Response login(@FormParam("username") String username, @FormParam("password") String password) throws Exception {
		
		MongoClientURI connectionString = new MongoClientURI("mongodb://muneer:1234567@ds023078.mlab.com:23078/pikkup");
		MongoClient mongoClient = new MongoClient(connectionString);
		String result = "";
		
		MongoDatabase database = mongoClient.getDatabase("pikkup");
		MongoCollection<Document> collection = database.getCollection("users");
		
		Document doc = collection.find(eq("username", username)).first();
		
		String actualPass = doc.get("password").toString();
		
		if(Password.check(password, actualPass)){
			result = "SUCCESS";	
			return Response.status(202).entity(result).build();
		}else{
			result = "FAILED";
			return Response.status(401).entity(result).build();
		}

	}

}
