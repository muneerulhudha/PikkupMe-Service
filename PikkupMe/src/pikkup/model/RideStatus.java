package pikkup.model;

import java.util.Map;

public class RideStatus {
	Boolean driverApproval;
	Map<String, Boolean> ridersApprovals;
	
	public RideStatus(Boolean driverApproval,
			Map<String, Boolean> ridersApprovals) {
		this.driverApproval = driverApproval;
		this.ridersApprovals = ridersApprovals;
	}

	public Boolean getDriverApproval() {
		return driverApproval;
	}

	public void setDriverApproval(Boolean driverApproval) {
		this.driverApproval = driverApproval;
	}

	public Map<String, Boolean> getRidersApprovals() {
		return ridersApprovals;
	}

	public void setRidersApprovals(Map<String, Boolean> ridersApprovals) {
		this.ridersApprovals = ridersApprovals;
	}
}