package ticketshop.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TicketStorage {
	ArrayList<Ticket> TicketList = new ArrayList<>();
	final int MAX_QUANTITY = 10;
	private String ticketFilename = "ticketlist.txt";
	private int lastId;
	private boolean isSaved;
	
	public TicketStorage() throws IOException {
		loadTicketListFromFile();
		generateLastId();
		isSaved = true;
	}
	
	private void generateLastId() {
		lastId = 0;
		for (Ticket ticket : TicketList) {
			int id = ticket.getTicketId();
			if (id > lastId) lastId = id;
		}
	}

	private void loadTicketListFromFile() throws IOException {
		FileReader fr;
		try {
			fr = new FileReader(ticketFilename);
			BufferedReader br = new BufferedReader(fr);
			String idStr;
			while ((idStr = br.readLine()) != null && !idStr.equals("")) {
				int id = Integer.parseInt(idStr);
				String title = br.readLine();
				String author = br.readLine();
				String publisher = br.readLine();
				int price = Integer.parseInt(br.readLine());
				TicketList.add(new Ticket(id, title, author, publisher, price));
			}
			fr.close();
			br.close();

		} catch (FileNotFoundException |  NumberFormatException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public int getNumTickets() {
		return TicketList.size();
	}

	public String getTicketInfo(int i) {
		return TicketList.get(i).toString();
	}

	public boolean isValidTicket(int ticketId) {
		for (Ticket ticket : TicketList) {
			if (ticket.getTicketId() == ticketId) return true;
		}
		return false;
	}

	public Ticket getTicketById(int ticketId) {
		for (Ticket ticket : TicketList) {
			if (ticket.getTicketId() == ticketId)
				return ticket;
		}
		return null;
	}

	public int getMaxQuantity() {
		return MAX_QUANTITY;
	}

	public boolean isEmpty() {
		return TicketList.size() == 0;
	}

	public void deleteItem(int TicketId) {
		TicketList.remove(getTicketById(TicketId));
		isSaved = false;
	}

	public void addTicket(String title, String author, String publisher, int price) {
		Ticket ticket = new Ticket(++lastId, title, author, publisher, price);
		TicketList.add(ticket);
		isSaved = false;
	}

	public boolean isSaved() {
		return isSaved;
	}

	public void saveTicketList2File() {

		try {
			FileWriter fw = new FileWriter(ticketFilename);
			for (Ticket ticket : TicketList) {
				fw.write(ticket.getTicketId() + "\n");
				fw.write(ticket.getTitle() + "\n");
				fw.write(ticket.getAuthor() + "\n");
				fw.write(ticket.getPublisher() + "\n");
				fw.write(ticket.getPrice() + "\n");
			}
			fw.close();
			isSaved = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
