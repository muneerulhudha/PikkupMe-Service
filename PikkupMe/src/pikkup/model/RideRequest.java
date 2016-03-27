package pikkup.model;

public class RideRequest {
	
	String origin;
	String destination;
	
	String desiredArrival;
	
	String riderName;
	
	public RideRequest() {
		
	}

	public RideRequest(String origin, String destination, String desiredArrival,
			String riderName) {

		this.origin = origin;
		this.destination = destination;
		this.desiredArrival = desiredArrival;
		this.riderName = riderName;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDesiredArrival() {
		return desiredArrival;
	}

	public void setDesiredArrival(String desiredArrival) {
		this.desiredArrival = desiredArrival;
	}

	public String getRiderName() {
		return riderName;
	}

	public void setRiderName(String riderName) {
		this.riderName = riderName;
	}
	
}