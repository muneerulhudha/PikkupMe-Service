package pikkup.rest;

import static com.mongodb.client.model.Filters.*;

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

@Path("signup")
public class SignUpService {

	@SuppressWarnings("resource")
	@POST
	@Consumes("application/x-www-form-urlencoded")
	public Response signup(@FormParam("username") String username, @FormParam("password") String password, @FormParam("email") String email) throws Exception {
		
		MongoClientURI connectionString = new MongoClientURI("mongodb://muneer:1234567@ds023078.mlab.com:23078/pikkup");
		MongoClient mongoClient = new MongoClient(connectionString);
		String result = "";
		
		MongoDatabase database = mongoClient.getDatabase("pikkup");
		MongoCollection<Document> collection = database.getCollection("users");
	
		Document doc = collection.find(or(eq("username", username), eq("email", email))).first();
		if(doc == null){
			Document newDoc = new Document("username", username)
		               .append("password", Password.getSaltedHash(password))
		               .append("email", email)
		               .append("college", "")
		               .append("address", "")
		               .append("phoneno", "");
			
			collection.insertOne(newDoc);
			result = "SUCCESS";
			return Response.status(202).entity(result).build();
		}else{
			result = "USERNAME or EMAIL ALREADY EXISTS";
			return Response.status(401).entity(result).build();
		}

	}

}
