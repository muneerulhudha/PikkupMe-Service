package pikkup.model;

import java.util.Date;


public class Ride {
	
	String origin;
	String destination;
	
	String desiredArrival;
	
	int availableSeats;
	
	String driverName;
	
	RideStatus rideStatus;
	
	public Ride() {
		
	}

	public Ride(String origin, String destination, String desiredArrival, int availableSeats, String driverName) {
		this.origin = origin;
		this.destination = destination;
		this.desiredArrival = desiredArrival;
		this.availableSeats = availableSeats;
		this.driverName = driverName;
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

	public int getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public RideStatus getRideStatus() {
		return rideStatus;
	}

	public void setRideStatus(RideStatus rideStatus) {
		this.rideStatus = rideStatus;
	}
	
}