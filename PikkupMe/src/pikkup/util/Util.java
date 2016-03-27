package pikkup.util;

import static com.mongodb.client.model.Filters.eq;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.bson.Document;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;

import pikkup.base.DataBaseManager;
import pikkup.model.PriceEstimateCollectionModel;

public class Util {
	
	static Gson gson = new Gson();
	private static ObjectMapper mapper = new ObjectMapper();
	
	public static String toJson(Object o) {
		return gson.toJson(o);
	}
	
	public static Class<?> fromJson(String json, Class<?> toClass) {
		return (Class<?>) gson.fromJson(json, toClass);
	}

	public static String getPhoneNo(String username) {
		// TODO Auto-generated method stub
		
		DataBaseManager manager = DataBaseManager.getInstance();
		MongoCollection<Document> collection = manager.getDatabase().getCollection("users");
		
		Document doc =collection.find(eq("username", username)).first();
		
		return doc.get("phoneno").toString();
	}
	
	public static Date stringToDate(String string) {
		String[] date = string.split("/");
		
		Calendar calendar = Calendar.getInstance();
		
		calendar.set(Calendar.MONTH, new Integer(date[0])-1);
		calendar.set(Calendar.DATE, new Integer(date[1]));
		calendar.set(Calendar.YEAR, new Integer(date[2]));
		
		return calendar.getTime();
	}

	public static PriceEstimateCollectionModel jsonDeserialize(String root, TypeReference typeReference)
            throws IOException {
        if (isNullOrWhiteSpace(root))
            return null;

        return mapper.readValue(root, typeReference);
    }

	public static boolean isNullOrWhiteSpace(String s) {
        if(s == null)
            return  true;

        int length = s.length();
        if (length > 0) {
            for (int start = 0, middle = length / 2, end = length - 1; start <= middle; start++, end--) {
                if (s.charAt(start) > ' ' || s.charAt(end) > ' ') {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
	
}