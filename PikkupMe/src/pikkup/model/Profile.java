package pikkup.model;

public class Profile {
	
	String username;
	String password;
	String email;
	String college;
	String address;
	String phoneno;
	
	public Profile(String email, String college, String address, String phoneNumber) {
		this.email = email;
		this.college = college;
		this.address = address;
		this.phoneno = phoneNumber;
	}
	
	public Profile(String username, String email,
			String college, String address, String phoneNumber) {
		
		this(email, college, address, phoneNumber);
		this.username = username;
	}
	
	public Profile(String username, String password, String email,
			String college, String address, String phoneNumber) {

		this(username, email, college, address, phoneNumber);
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneno;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneno = phoneNumber;
	}
	
}