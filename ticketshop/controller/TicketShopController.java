package ticketshop.controller;

import ticketshop.model.Admin;
import ticketshop.model.Cart;
import ticketshop.model.Customer;
import ticketshop.model.TicketStorage;
import ticketshop.view.ConsoleView;

public class TicketShopController {

	ConsoleView view;
	TicketStorage mTicketStorage;
	Cart mCart;
	Customer mCustomer;
	Admin mAdmin;

//	String[] menuList = { //여유되면 2, 3, 4번 / 6, 7번 순서 바꾸기(편의상)
//			"0. 종료", //시스템 종료
//			"1. 콘서트 정보 보기",
//			"2. 콘서트 선택하기", //내가 새로 추가한 기능
//			"3. 예매 기록 확인하기", //예매한 티켓이 없습니다 or 예매한 티켓이 있으면 티켓 보여주기
//			"4. 예매하기", //티켓 예매(=티켓팅) 아직 결재는 안 한 상태
//			"5. 예매한 티켓 삭제", //예매한 티켓 삭제하기(=티켓팅 취소)
//			"6. 결제 취소하기", //결제 취소(티켓 구매 취소)
//			"7. 티켓 결제", //티켓 결제(구매)
//			"8. 관리자 모드"
//	};

	String[] menuList = { "0. 종료", "1. 콘서트 정보 보기", "2. 예매 기록 확인하기", // 예매한 티켓이 없습니다 or 예매한 티켓이 있으면 티켓 보여주기
			"3. 콘서트 티켓 선택하기", "4. 선택한 티켓 삭제", // 장바구니 비우기
			"5. 결제 취소하기", // 결제 취소(티켓 구매 취소) = 결제 했다가 결제 취소한 거
			"6. 티켓 결제", // 주문
			"7. 관리자 모드" };

	String[] adminMenuList = { "0. 종료", "1. 콘서트 정보 추가", "2. 콘서트 정보 삭제", "3. 콘서트 정보 보기", "4. 콘서트 정보 파일 저장"

	};

	public TicketShopController(TicketStorage ticketstorage, Cart cart, ConsoleView view) {
		this.view = view;
		mTicketStorage = ticketstorage;
		mCart = cart;
		mAdmin = new Admin();
	}

	public void start() {
		welcome(); // 환영 인사
		registerCustomerInfo(); // 고객 정보 등록

		int menu;

		do {
			menu = view.selectMenu(menuList);

			switch (menu) {
			case 1 -> viewConcertInfo(); // 콘서트 정보 보기
			case 2 -> viewCart(); // 예매 기록 확인하기
			case 3 -> addTicketCart(); // 콘서트 티켓 선택하기
			case 4 -> deleteTicketInCart(); // 선택한 티켓 삭제
			case 5 -> resetCart(); // 결제 취소하기 = 결제 후 결제 취소
			case 6 -> order(); // 티켓 결제 = 주문
			case 7 -> adminMode(); // 관리자 모드
			case 0 -> end(); // 종료
			default -> view.showMessage("잘못된 메뉴 번호입니다.");
			}
		} while (menu != 0);

	}

//------------------------------------------------------------------------------------

	// 환영 인사
	private void welcome() {
		view.displayWelcome();
	}

	// 사용자 정보 등록
	private void registerCustomerInfo() {
		mCustomer = new Customer();
		view.inputCustomerInfo(mCustomer);
	}

	// 콘서트 정보 보기
	private void viewConcertInfo() {
		view.displayTicketInfo(mTicketStorage);
	}

	// 예매 기록 확인하기
	private void viewCart() {
		view.displayCart(mCart);
	}

	// 콘서트 티켓 선택하기
	private void addTicketCart() {
		view.displayTicketInfo(mTicketStorage);
		int ticketId = view.selectTicketId(mTicketStorage);
		mCart.addItem(mTicketStorage.getTicketById(ticketId));
		view.showMessage(">>장바구니에 도서를 추가하였습니다.");
	}

	// 선택한 티켓 삭제
	private void deleteTicketInCart() {
		// 예매 기록 보여주기
		view.displayCart(mCart);
		if (!mCart.isEmpty()) {
			// 티켓 ID 입력 받기
			int ticketId = view.selectTicketId(mCart);
			if (view.askConfirm(">> 해당 티켓을 삭제하려면 yes를 입력하세요 : ", "yes")) {
				// 해당 티켓 ID의 cartItem 삭제
				mCart.deleteItem(ticketId);
				view.showMessage(">> 해당 티켓을 삭제했습니다.");
			}
		}
	}

	// 결제 취소하기
	private void resetCart() {
		;
	}

	// 티켓 결제하기 = 주문
	private void order() {
		// 배송 정보 추가
		getDeliveryInfo();
		// 구매 정보 보기 : 장바구니 내역, 배송 정보
		viewOrderInfo();
		// 정말 주문하시겠습니까?
		if (view.askConfirm("진짜 주문하려면 yes를 입력하세요 : ", "yes")) {
			// 주문 처리 -> 장바구니 초기화
			mCart.resetCart();
		}
	}

	private void getDeliveryInfo() {
		view.inputDeliveryInfo(mCustomer);
	}

	private void viewOrderInfo() {
		view.displayOrder(mCart, mCustomer);
	}

//-----------------------------------------------------------------------

	// 관리자 모드
	private void adminMode() {

		if (!authenticateAdmin()) {
			view.showMessage("관리자 모드로 전환할 수 없습니다.");
			return;
		}

		// 관리자 모드 진입 -> 도서 추가, 도서 삭제, 도서 정보 파일 저장
		// 관리자 모드일 때의 메뉴 출력
		// 메뉴 선택하면 해당 기능 실행
		int menu;
		do {
			menu = view.selectMenu(adminMenuList);

			switch (menu) {
			case 1 -> addTicket2Storage();
			case 2 -> deleteTicketInStorage();
			case 3 -> viewTicketInfo();
			case 4 -> saveTicketList2File();
			case 0 -> adminEnd();
			default -> view.showMessage("잘못된 메뉴 번호입니다.");
			}
		} while (menu != 0);

	}

//-------------------------------------------------------------------------

	//관리자 인증 (id, password 확인)
	private boolean authenticateAdmin() {
		view.showMessage("관리자 모드 진입을 위한 관리장 인증");
		String id = view.inputString("관리자 ID : ");
		String password = view.inputString("관리자 password : ");
		return mAdmin.login(id, password);
	}

	//Ticket Storage 에 콘서트 티켓 추가
	private void addTicket2Storage() {
		view.showMessage("새로운 콘서트 티켓을 추가합니다.");

		// 티켓 정보로 Ticket 인스턴스 만들어서 mTicketStorage에 추가
		mTicketStorage.addTicket(view.inputString("책 제목 : "), view.inputString("저자 : "), view.inputString("출판사 : "),
				view.readNumber("가격 : "));
	}

	//Ticket Storage 에서 티켓 삭제
	private void deleteTicketInStorage() {
		if (mTicketStorage.isEmpty()) {
			view.showMessage("티켓 리스트에 티켓이 없습니다.");
			return;
		}
		// 티켓 리스트 보여주기
		viewTicketInfo();
		// 티켓 ID 입력 받기
		int ticketId = view.selectTicketId(mTicketStorage);
		if (view.askConfirm(">> 해당 콘서트를 삭제하려면 yes를 입력하세요 : ", "yes")) {
			// 해당 도서 ID의 cartItem 삭제
			mTicketStorage.deleteItem(ticketId);
			view.showMessage(">> 해당 티켓를 삭제했습니다.");
		}

	}

	private void viewTicketInfo() {
		System.out.println("죄송합니다........");
		
	}

	//티켓 정보를 파일에 저장
	private void saveTicketList2File() {
		if (mTicketStorage.isSaved()) {
			view.showMessage("티켓의 정보가 파일과 동일합니다.");
		} else {
			mTicketStorage.saveTicketList2File();
			view.showMessage("티켓 정보를 저장하였습니다.");
		}
	}

	// 관리자 모드 종료 : 티켓 정보 수정 후 파일에 반영되지 않은 경우, 저장 여부 확인
	private void adminEnd() {
		if (!mTicketStorage.isSaved()) {
			view.showMessage("수정한 티켓 정보가 파일로 저장되지 않았습니다.");
			if (view.askConfirm("티켓 정보를 저장하려면 yes를 입력하세요 : ", "yes")) {
				mTicketStorage.saveTicketList2File();
			}
		}
		view.showMessage("관리자 모드가 종료되었습니다.\n");
	}

//-----------------------------------------------------------------------------
	
	//종료
	private void end() {
		view.showMessage(">> DREAM Ticket Shop을 종료합니다.");
	}

}
