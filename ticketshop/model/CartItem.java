package ticketshop.model;

public class CartItem {
	
	Ticket ticket;
	int ticketId;
	int quantity;
	
	public CartItem(Ticket ticket) {
		this.ticket = ticket;
		this.ticketId = ticket.getTicketId();
		this.quantity = 1;
	}
	
	public Ticket getTicket() {
		return ticket;
	}
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void addQuantity(int quantity) {
		this.quantity += quantity;
		
	}

	@Override
	public String toString() {
		return ticket.getTicketId() + ", " + ticket.getTitle() + ", " + quantity + "장, " + getPrice() + "원";
	}

	public int getPrice() {
		return quantity * ticket.getPrice();
	}

}
