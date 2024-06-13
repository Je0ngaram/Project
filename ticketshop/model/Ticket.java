package ticketshop.model;
//티켓에 관한 정보임
public class Ticket {
	private int ticketId;
	private String title;
	private String author;
	private String publisher;
	private int price;
	
	public Ticket(int ticketId, String title, String author, String publisher, int price) {
		this.ticketId = ticketId;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.price = price;
	}
	
	public int getTicketId() {
		return ticketId;
	}
	public String getTitle() {
		return title;
	}
	public String getAuthor() {
		return author;
	}
	public String getPublisher() {
		return publisher;
	}
	public int getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return ticketId + ", " + title + ", " + author + ", " + publisher
				+ ", " + price + "원";
	}
}
