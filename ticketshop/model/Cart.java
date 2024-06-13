package ticketshop.model;

import java.util.ArrayList;

public class Cart {

		private ArrayList<CartItem> itemList = new ArrayList<>();

		public boolean isEmpty() {
			return itemList.isEmpty();
		}

		public ArrayList<CartItem> getItemList() {
			return itemList;
		}

		public int getNumItems() {
			return itemList.size();
		}

		public String getItemInfo(int index) {
			return itemList.get(index).toString();
		}

		public void addItem(Ticket ticket) {
			
			CartItem item = getCartItem(ticket);
			if (item == null) {
				itemList.add(new CartItem(ticket));
			}
			else {
				item.addQuantity(1);
			}
		}
		
		private CartItem getCartItem(Ticket ticket) {
			
			for (CartItem item : itemList) {
				if (item.getTicket() == ticket) return item;
			}
			
			return null;
		}
		
		private CartItem getCartItem(int ticketId) {
			for (CartItem item : itemList) {
				if (item.ticketId == ticketId) return item;
			}
			return null;
		}
		
		public void resetCart() {
			itemList.clear();
		}

		public boolean isValidItem(int ticketId) {
			for (CartItem item : itemList) {
				if (item.ticketId == ticketId) return true;
			}
			return false;
		}

		public void deleteItem(int ticketId) {
			CartItem item = getCartItem(ticketId);
			itemList.remove(item);
		}

		public void updateQuantity(int ticketId, int quantity) {
			
			if (quantity == 0)
				deleteItem(ticketId);
			else {
				CartItem item = getCartItem(ticketId);
				item.setQuantity(quantity);
			}
			
		}

		public int getTotalPrice() {
			int totalPrice = 0;
			for (CartItem item : itemList) {
				totalPrice += item.getPrice();
			}
			return totalPrice;
		}

}
