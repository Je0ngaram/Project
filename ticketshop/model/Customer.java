package ticketshop.model;

public class Customer extends User{
	private String address;
	private String email;
	private String card;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setCard(String card) {
		this.card = card;
		
	}
}
