package pikkup.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("ride")
public class RideService {
	
	@GET
	public String getRides() {
		String allRides = "";
		//TODO
		return allRides;
	}
	
	@GET
	@Path("/{ride}")
	public String getRide(@PathParam("ride") String name) {
		String ride = name;
		//TODO
		return ride;
	}
	
	@POST
	@Consumes("text/plain")
	public void postRide(String ride) {
		//TODO
	}

}
