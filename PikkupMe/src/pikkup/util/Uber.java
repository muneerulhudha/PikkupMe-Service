package pikkup.util;

import static com.mongodb.client.model.Filters.eq;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bson.Document;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import pikkup.model.PriceEstimateCollectionModel;

public class Uber {

	public static int getCost(){
		return 8;
	}
	
	public static Future<PriceEstimateCollectionModel> getCostAgain(String destination) throws Exception {
		// TODO Auto-generated method stub	
		String baseUri = "https://api.uber.com/v1";
		
		MongoClientURI connectionString = new MongoClientURI("mongodb://muneer:1234567@ds023078.mlab.com:23078/pikkup");
		MongoClient mongoClient = new MongoClient(connectionString);

		MongoDatabase database = mongoClient.getDatabase("pikkup");
		
		MongoCollection<Document> collection = database.getCollection("locations");
		Document doc = collection.find(eq("name", destination)).first();
		
		final double startLat = 32.984572;
		final double startLong = -96.7502117;
		
		String endLatS = doc.get("lat").toString();
		String endLongS = doc.getString("long").toString();
		
		final Double endLat = Double.parseDouble(endLatS);
		final Double endLong = Double.parseDouble(endLongS);

//		HttpResponse<JsonNode> response = Unirest.get("https://api.uber.com/v1/estimates/price")
//		  .routeParam("method", "get")
//		  .routeParam("Authentication", "Token " + "3z1RatO5tPNN8TvJ28qV6Im0VSW4ehh4VG3FRYjA")
//		  .queryString("Authentication", )
//		  
//		  .asJson();
//		
//		System.out.println(response.getStatus());
		
		StringBuilder queryBuilder = new StringBuilder(baseUri);
        queryBuilder.append("/v1/estimates/price");
        
        appendUrlWithQueryParameters(queryBuilder, new HashMap<String, Object>() {
            private static final long serialVersionUID = 5272266287936440457L;
            {
                    put( "start_latitude", startLat );
                    put( "start_longitude", startLong );
                    put( "end_latitude", endLat.doubleValue() );
                    put( "end_longitude", endLong.doubleValue() );
            }});
		

        //validate and preprocess url
        String queryUrl = cleanUrl(queryBuilder);

        //prepare and invoke the API call request to fetch the response
        HttpRequest request = Unirest.get(queryUrl);
        
        request.header("accept", "application/json")
        .header("Authorization", String.format("Bearer %1$s", "Token " + "3z1RatO5tPNN8TvJ28qV6Im0VSW4ehh4VG3FRYjA"));
        
        
        
        final Future<HttpResponse<String>> responseAsync = request.asStringAsync();
//        return new FutureTask<PriceEstimateCollectionModel>(new Callable<PriceEstimateCollectionModel>() {
//            public PriceEstimateCollectionModel call() throws Exception {
//                //execute and get response from async task
//                HttpResponse<String> response = responseAsync.get();
//
//               
//                //return Util.jsonDeserialize(response.getBody(),
//                 //new TypeReference<PriceEstimateCollectionModel>(){});
//        }});
		return null;
	}
	
	public static String cleanUrl(StringBuilder url)
    {
        //ensure that the urls are absolute
        Pattern pattern = Pattern.compile("^(https?://[^/]+)");
        Matcher matcher = pattern.matcher(url);
        if (!matcher.find())
            throw new IllegalArgumentException("Invalid Url format.");

        //get the http protocol match
        String protocol = matcher.group(1);

        //remove redundant forward slashes
        String query = url.substring(protocol.length());
        query = query.replaceAll("//+", "/");

        //return process url
        return protocol.concat(query);
    }
	
	public static void appendUrlWithQueryParameters(StringBuilder queryBuilder, Map<String, Object> parameters) {
        //perform parameter validation
        if (null == queryBuilder)
            throw new IllegalArgumentException("Given value for parameter \"queryBuilder\" is invalid." );

        if (null == parameters)
            return;

        //does the query string already has parameters
        boolean hasParams = (queryBuilder.indexOf("?") > 0);

        //iterate and append parameters
       for (Map.Entry<String, Object> pair : parameters.entrySet()) {
            //ignore null values
            if (null == pair.getValue())
                continue;
            //if already has parameters, use the &amp; to append new parameters
            char separator = (hasParams) ? '&' : '?';

            queryBuilder.append(separator + pair.getKey() + "=" + pair.getValue());

            //indicate that now the query has some params
            hasParams = true;
        }
    }

}
