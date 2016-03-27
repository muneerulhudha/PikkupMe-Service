package pikkup.core;


public class Scheduler {

	private static Scheduler instance = null;

	protected Scheduler() {
		//create connection
		//and other stuff
	}

	public static Scheduler getInstance() {
		if (instance == null) {
			instance = new Scheduler();
		}
		return instance;
	}

	public void updateRides() {

	}
}