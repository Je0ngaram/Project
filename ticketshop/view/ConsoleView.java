package ticketshop.view;

import java.util.Scanner;

import ticketshop.model.Cart;
import ticketshop.model.Customer;
import ticketshop.model.TicketStorage;

public class ConsoleView {

	// 환영 인사 출력
	public void displayWelcome() {
		String welcome = "*****************************************\n"
				+ "*      Welcome to DREAM Ticket Shop     *\n"
				+ "*****************************************";
		System.out.println(welcome);

	}

	// 메뉴 번호 입력 받기
	public int selectMenu(String[] menuList) {
		displayMenu(menuList);
		int menu;
		do {
			menu = readNumber(">> 메뉴 선택 : ");
			if (menu < 0 || menu >= menuList.length)
				System.out.println("0부터 " + (menuList.length - 1) + "까지의 숫자를 입력하세요.");
		} while (menu < 0 || menu >= menuList.length);
		return menu;
	}

	// 메뉴 출력
	private void displayMenu(String[] menuList) {
		System.out.println("=========================================");

		for (int i = 1; i < menuList.length; i++) {
			System.out.println(menuList[i]);
		}
		System.out.println(menuList[0]);
		System.out.println("=========================================");

	}

	//           콘서트 목록 보여주기    콘서트 리스트에서
	public void displayTicketInfo(TicketStorage ticketstorage) {
		for (int i = 0; i < ticketstorage.getNumTickets(); i++) {
			String bookInfo = ticketstorage.getTicketInfo(i);
			System.out.println(bookInfo);
		}

	}

	// 예매 확인하기
	public void displayCart(Cart mCart) {

		if (mCart.isEmpty()) {
			System.out.println(">> 예매 내역이 없습니다.");
			return;
		}

		for (int i = 0; i < mCart.getNumItems(); i++) {
			System.out.println(mCart.getItemInfo(i));
		}

		System.out.println("총 금액 : " + mCart.getTotalPrice() + "원");

	}

	// TicketStorage 에 있는 콘서트를 ID로 선택하기
	public int selectTicketId(TicketStorage TicketStorage) {
		
		int ticketId;
		boolean result;
		do {
			ticketId = readNumber("추가할 티켓의 ID를 입력하세요 : ");
			result = TicketStorage.isValidTicket(ticketId);
			if (!result)
				System.out.print("잘못된 티켓의 ID입니다.");
		} while (!result);
		return ticketId;

	}

	// cart 에 있는 티켓을 ID로 선택하기
	public int selectTicketId(Cart cart) {
		int ticketId;
		boolean result;
		do {

			ticketId = readNumber("티켓 ID 입력 : ");
			result = cart.isValidItem(ticketId);

			if (!result)
				System.out.print("잘못된 티켓의 ID입니다.");
		} while (!result);

		return ticketId;

	}

	
//	// 도서 수량 입력 받기        <-- 내가 뺀 기능
//	public int inputQuantity(int min, int max) {
//		int number;
//		do {
//
//			number = readNumber(">> 수량 입력 (" + min + " ~ " + max + "): ");
//
//			if (number < min || number > max)
//				System.out.println("잘못된 수량입니다.");
//		} while (number < min || number > max);
//
//		return number;
//
//	}

	
	// 사용자 정보 입력 받기 -이름, 전화번호, 결제 카드
	public void inputCustomerInfo(Customer customer) {
		Scanner input = new Scanner(System.in);
		System.out.println("드림티켓을 이용하시려면 개인 정보를 입력하세요.");
		System.out.print(">> 이름 : ");
		customer.setName(input.nextLine());
		System.out.print(">> 전화번호 : ");
		customer.setPhone(input.nextLine());
		System.out.print(">> 결제 카드 : ");
		customer.setCard(input.nextLine());

	}

	// 배송 정보 입력 받기 - 주소와 이메일주소를 입력하지 않은 경우
	public void inputDeliveryInfo(Customer customer) {
		if (customer.getEmail() == null) {
			Scanner input = new Scanner(System.in);
			System.out.println("티켓 배송을 위해 이메일 주소와 배송받을 주소를 입력하세요.");
			System.out.print(">> 이메일 : ");
			customer.setEmail(input.nextLine());
			System.out.print(">> 주소 : ");
			customer.setAddress(input.nextLine());

		}

	}

	public void displayOrder(Cart cart, Customer customer) {
		System.out.println();

		// 예매 확인하기
		System.out.println("***** 예매한 티켓 ******");
		displayCart(cart);

		// 배송 정보 보여주기 - 사용자 이름, 전화번호, 이메일주소, 주소
		System.out.println("***** 배송 정보 ******");
		System.out.println(">> 이름 : " + customer.getName());
		System.out.println(">> 전화번호 : " + customer.getPhone());
		System.out.println(">> 이메일 : " + customer.getEmail());
		System.out.println(">> 주소 : " + customer.getAddress());
		System.out.println();

	}

	
	///////////////////// 공용 모듈 ////////////////////////

	
	// 입력된 문자열을 입력했을 때만 true 를 반환하는 confirm 용
	public boolean askConfirm(String message, String yes) {
		System.out.print(message);
		Scanner input = new Scanner(System.in);
		if (input.nextLine().equals(yes))
			return true;
		return false;

	}

	// 숫자 입력 받기 (숫자가 아닌 문자를 넣으면 예외 처리하고 다시 입력받기)
	public int readNumber(String message) {
		if (message != null || !message.equals(""))
			System.out.print(message);
		
		Scanner input = new Scanner(System.in);
		try {
			int number = input.nextInt();
			return number;
		} catch (Exception e) {
			System.out.print("숫자를 입력하세요 :");
			return readNumber(message);
		}

	}

	// 입력된 문자열 출력
	public void showMessage(String message) {
		System.out.println(message);

	}

	// 문자열 입력 받기
	public String inputString(String msg) {
		Scanner in = new Scanner(System.in);
		System.out.print(msg);
		return in.nextLine();

	}

}