package pikkup.model;

import java.util.Date;


public class Ride {
	
	String origin;
	String destination;
	
	Date desiredArrival;
	
	int availableSeats;
	
	boolean hasDriver;
	
	RideStatus rideStatus;

	public Ride(String origin, String destination, Date desiredArrival, int availableSeats, boolean hasDriver) {
		this.origin = origin;
		this.destination = destination;
		this.desiredArrival = desiredArrival;
		this.availableSeats = availableSeats;
		this.hasDriver = hasDriver;
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

	public Date getDesiredArrival() {
		return desiredArrival;
	}

	public void setDesiredArrival(Date desiredArrival) {
		this.desiredArrival = desiredArrival;
	}

	public int getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}

	public boolean isHasDriver() {
		return hasDriver;
	}

	public void setHasDriver(boolean hasDriver) {
		this.hasDriver = hasDriver;
	}
	
}