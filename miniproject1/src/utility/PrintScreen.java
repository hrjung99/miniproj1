package utility;

import java.util.Scanner;

import board.BoardVO;
import user.UserDAO;
import user.UserVO;

public class PrintScreen {

	// 콘솔 디자인
	private static String title = "";
	private static final int width = 250; // 구분선의 전체 길이
	private static int padding = (width - title.length()) / 2;
//
//	System.out.println("=".repeat(width));
//	System.out.println(" ".repeat(padding) + title);
//	System.out.println("=".repeat(width));

	// 콘솔 디자인 변수
	private static final int WIDTH = 80;

	// 스캐너 선언
	private static Scanner scan_input = new Scanner(System.in);
	private static String input;

	// 정확한 입력 값을 받기위한 변수
	private static boolean isValid = false;

	// 프로젝트 명 출력 메소드
	public static void printProjectName(String title) {
		int padding = (WIDTH - title.length()) / 2;
		System.out.println("=".repeat(WIDTH));
		System.out.println(" ".repeat(padding) + title);
	}

	// 각 제목 출력 메소드
	public static void printTitle(String title) {
		int padding = (WIDTH - title.length()) / 2;
		System.out.println("=".repeat(WIDTH));
		System.out.println(" ".repeat(padding) + title);
		System.out.println("-".repeat(WIDTH));
	}

	// 메인 화면
	public static void printMainSecreen() {
		printTitle("[메인 메뉴]");
		while (!isValid) { // 유효한 입력이 들어올 때까지 반복 할 수 있도록 while 루프 돌리기
			System.out.println("1. 회원 가입");
			System.out.println("2. 로그인");
			System.out.println("3. 아이디 찾기");
			System.out.println("4. 비밀번호 초기화");
			System.out.println("5. 프로그램 종료");
			System.out.println();
			System.out.println("원하는 기능을 선택하십시오.");
			System.out.print("기능 번호 : ");
			input = scan_input.nextLine();
			switch (input) {
			case "1":
				System.out.println("회원 가입으로 이동합니다.");
				isValid = true;
				// 회원가입 화면 출력
				UserScreen.printUserInsertScreen();
				break;
			case "2":
				System.out.println("로그인 화면으로 이동합니다.");
				// 로그인 화면 출력
				UserScreen.printLoginScreen();
				isValid = true;
				break;
			case "3":
				System.out.println("아이디 찾기 화면으로 이동합니다.");
				UserScreen.findId();
				isValid = true;
				break;
			case "4":
				System.out.println("비밀번호 초기화 화면으로 이동합니다.");
				UserScreen.resetPassword();
				isValid = true;
			case "5":
				System.out.println("프로그램을 종료합니다. 이용해주셔서 감사합니다.");
				scan_input.close();
				System.exit(0);
				break;
			default:
				System.out.println();
				System.out.println("잘못된 입력입니다. 다시 시도하십시오.");
				break;
			}
		}
		isValid = false;

	}

	// 로그인 성공 후 화면
	public static void printListAfterLogin(UserVO currentUserInfo) {
		PrintScreen.printTitle("[메뉴]");
		System.out.println("1. 나의 정보 확인");
		System.out.println("2. 게시물 목록");
		System.out.println("3. 로그아웃");
		System.out.println("4. 프로그램 종료");

		System.out.println();
		System.out.println("원하는 기능을 선택하십시오.");
		System.out.print("기능 번호 : ");
		String choice = scan_input.nextLine();
		switch (choice) {
		case "1":
			// 나의 정보 확인
			UserScreen.printUserViewScreen(currentUserInfo);
			break;

		case "2":
			// 게시물 목록
			BoardScreen.printBoardListScreen(currentUserInfo);
			break;

		case "3":
			// 로그아웃
			UserScreen.printLogoutScreen(currentUserInfo);
			break;

		case "4":
			// 프로그램 종료
			System.out.println("프로그램을 종료합니다.");
			System.exit(0); // 프로그램 종료

		default:
			// 잘못된 선택
			System.out.println("잘못된 선택입니다. 다시 선택해 주세요.");
			printListAfterLogin(currentUserInfo); // 잘못된 선택 시 다시 화면 표시
			break;
		}

	}

	// 로그인 성공 후 화면 for admin
	public static void printListAfterLoginForAdmin(UserVO currentUserInfo) {
		PrintScreen.printTitle("[메뉴 - 관리자]");
		System.out.println("1. 나의 정보 확인");
		System.out.println("2. 게시물 목록");
		System.out.println("3. 회원 목록");
		System.out.println("4. 로그아웃");
		System.out.println("5. 프로그램 종료");

		System.out.println();
		System.out.println("원하는 기능을 선택하십시오.");
		System.out.print("기능 번호 : ");
		String choice = scan_input.nextLine();
		switch (choice) {
		case "1":
			// 나의 정보 확인
			UserScreen.printUserViewScreen(currentUserInfo);
			break;

		case "2":
			// 게시물 목록
			BoardScreen.printBoardListScreen(currentUserInfo);
			break;

		case "3":
			// 회원 목록
			UserScreen.printUserListScreen(currentUserInfo);
			break;

		case "4":
			// 로그아웃
			UserScreen.printLogoutScreen(currentUserInfo);
			break;

		case "5":
			// 프로그램 종료
			System.out.println("프로그램을 종료합니다.");
			System.exit(0); // 프로그램 종료

		default:
			// 잘못된 선택
			System.out.println("잘못된 선택입니다. 다시 선택해 주세요.");
			printListAfterLoginForAdmin(currentUserInfo); // 잘못된 선택 시 다시 화면 표시
			break;
		}
	}

	public static void isMyBoard(UserVO currentUserInfo, BoardVO boardView) {
        System.out.println();
        System.out.println();

        // 내 게시물일 경우
        if (boardView.getBoard_no() == currentUserInfo.getUser_no()) {
            while (true) {
                System.out.println("1. [" + boardView.getBoard_title() + "] 게시물 수정");
                System.out.println("2. [" + boardView.getBoard_title() + "] 게시물 삭제");
                System.out.println("3. 다른 게시물 선택");
                System.out.println();
                System.out.print("기능 번호 : ");
                String choice = scan_input.nextLine();
                switch (choice) {
                    case "1":
                        while (true) {
                            // 게시물 비밀번호 확인
                            System.out.println("비밀번호를 입력해주세요.");
                            System.out.println("이전화면으로 돌아가고 싶다면 \'q\'를 입력하세요.");
                            System.out.print("비밀번호 : ");
                            String password_up = scan_input.nextLine();
                            if ("q".equalsIgnoreCase(password_up)) {
                                break; // 이전 화면으로 돌아가기
                            }
                            if (boardView.getBoard_pass().equals(password_up)) {
                                // 게시물 수정 화면 출력
                                String message = BoardScreen.printBoardUpdateScreen(boardView);
                                if (message.equals("성공")) {
                                	System.out.println("게시물이 수정되었습니다.");
                                }else {
                                	System.out.println("게시물 수정에 실패했습니다.");
                                }
                                
                                break;
                            } else {
                                System.out.println("비밀번호가 올바르지 않습니다.");
                            }
                        }
                        break;
                    case "2":
                        while (true) {
                            // 게시물 비밀번호 확인
                            System.out.println("비밀번호를 입력해주세요.");
                            System.out.print("비밀번호 : ");
                            String password_del = scan_input.nextLine();
                            if ("q".equalsIgnoreCase(password_del)) {
                                break; // 이전 화면으로 돌아가기
                            }
                            if (boardView.getBoard_pass().equals(password_del)) {
                                // 게시물 삭제 호출
                                String message = BoardScreen.printBoardDeleteScreen(boardView);
                                
                                if (message.equals("성공")) {
                                	System.out.println("게시물이 삭제되었습니다.");
                                }else {
                                	System.out.println("게시물 삭제에 실패했습니다.");
                                }
                                break;
                            } else {
                                System.out.println("비밀번호가 올바르지 않습니다.");
                            }
                        }
                        break;
                    case "3":
                        System.out.println("기능 선택으로 돌아갑니다.");
                        return; // 기능 선택으로 돌아감
                    default:
                        System.out.println("잘못된 입력입니다. 다시 시도해 주세요.");
                        break;
                }
            }
        }
    }
}