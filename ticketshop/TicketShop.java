package ticketshop;

import java.io.IOException;

import ticketshop.controller.TicketShopController;
import ticketshop.model.Cart;
import ticketshop.model.TicketStorage;
import ticketshop.view.ConsoleView;

public class TicketShop {

	private static final ConsoleView ConsoleView = null;

	public static void main(String[] args) throws IOException {
		// model 생성
		TicketStorage ticketstorage = new TicketStorage();
		Cart cart = new Cart();

		// view 생성
		ConsoleView view = new ConsoleView();

//		 controller 생성 (model, view)
		TicketShopController controller = new TicketShopController(ticketstorage, cart, view);
		controller.start();

	}

}
