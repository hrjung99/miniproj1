package utility;

import java.util.List;
import java.util.Scanner;

import board.BoardVO;
import user.UserDAO;
import user.UserVO;



public class UserScreen {
	//콘솔 디자인
	private static String title = "";
	private static final int  width = 120; // 구분선의 전체 길이
	private static int padding = (width - title.length()) / 2;

	// 스캐너 선언
    private static Scanner scan_input = new Scanner(System.in);
    private static String input;
	
	//정확한 입력 값을 받기위한 변수
	private static boolean isValid = false;
	
	//메세지 변수 선언
	private static String message="";
	
	//회원 정보
	private static UserVO userInfo = new UserVO();
	
	
	//회원 목록 화면
	public static void printUserListScreen() {
		PrintScreen.printTitle("[회원 목록]");
		// 헤더 출력
        String header = String.format("| %-8s | %-10s | %-10s | %-15s | %-20s | %-4s | %-6s | %-8s | %-19s | %-19s |",
                                      "회원번호", "아이디", "이름", "전화번호", "주소", "성별", "권한", "탈퇴여부", "최근 로그인", "최근 로그아웃");
        System.out.println(header);
        System.out.println("-".repeat(width));

        // 사용자 목록 가져오기
        List<UserVO> userList = UserDAO.userList(new UserVO());

        // 각 사용자 정보 출력
        for (UserVO user : userList) {
            String row = String.format("| %-8d | %-10s | %-10s | %-15s | %-20s | %-4s | %-6s | %-8s | %-19s | %-19s |",
                                       user.getUser_no(),
                                       user.getUser_id(),
                                       user.getUser_name(),
                                       user.getUser_phone(),
                                       user.getUser_addr(),
                                       user.getUser_sex(),
                                       user.getUser_role(),
                                       user.getUser_deleteYN(),
                                       user.getUser_login_recent(),
                                       user.getUser_logout_recent());
            System.out.println(row);
        }

        // 표 끝 부분 출력
        System.out.println("=".repeat(width));
	}
	
	
	
	
	// 마이페이지 화면
	public static void printUserViewScreen(UserVO userVO) {
		PrintScreen.printTitle("[나의 정보 확인]");
		UserVO currentUserInfo = new UserVO();
		currentUserInfo = UserDAO.userView(userVO);
		// 조회된 사용자 정보를 화면에 출력
		if (currentUserInfo != null) {
			System.out.println("아이디        : " + currentUserInfo.getUser_id());
			System.out.println("이름          : " + currentUserInfo.getUser_name());
			System.out.println("전화번호      : " + currentUserInfo.getUser_phone());
			System.out.println("주소          : " + currentUserInfo.getUser_addr());
			System.out.println("성별          : " + currentUserInfo.getUser_sex());
			System.out.println("최근 로그인   : " + currentUserInfo.getUser_login_recent());
			System.out.println("최근 로그아웃 : " + currentUserInfo.getUser_logout_recent());
		} else {
			System.out.println("오류 : 사용자 정보를 조회할 수 없습니다.");
		}
	}

	// 회원 가입 화면
	public static void printUserInsertScreen() {
		UserVO userVO = new UserVO();
		PrintScreen.printTitle("[회원 가입]");
		boolean isIdAvailable;
		
		// 아이디 중복 체크
        do {
            System.out.print("아이디 : ");
    		input = scan_input.nextLine();
            userVO.setUser_id(input);
            
            isIdAvailable = UserDAO.isIdAvailable(input); // 아이디 중복 체크 함수 호출
            
            if (isIdAvailable) {
            	System.out.println();
                System.out.println("이미 존재하는 아이디 입니다.\n 다른 아이디를 입력해 주세요.");
            }
        } while (isIdAvailable); // 아이디가 유효할 때까지 반복
		
		System.out.print("비밀번호 : ");
		input = scan_input.nextLine();
		userVO.setUser_pass(input);
		
		System.out.print("이름 : ");
		input = scan_input.nextLine();
		userVO.setUser_name(input);
		
		System.out.println("전화번호 입력 시 \"-\"를 제외하고 숫자만 입력하십시오.");
		System.out.print("전화번호 : ");
		input = scan_input.nextLine();
	    userVO.setUser_phone(etcMethod.formatPhoneNumber(input));
		
		System.out.println("주소 입력 예시 :  \"서울특별시 강서구 화곡동\"");
		System.out.print("주소 : ");
		input = scan_input.nextLine();
		userVO.setUser_addr(input);
		
		System.out.println("성별 입력 예시 :  \"남\", \"여\" 중 선택");
		System.out.print("성별 : ");
		input = scan_input.nextLine();
		userVO.setUser_sex(input);
				
		while (true) {
	        System.out.println();
	        System.out.println("1. 가입");
	        System.out.println("2. 다시 입력");
	        System.out.println("3. 이전 화면으로");
	        System.out.println();
			System.out.println("원하는 기능을 선택하십시오.");
			System.out.print("기능 번호 : ");
			String choice = scan_input.nextLine();
	        
	        switch (choice) {
	            case "1":
	                // 1. 가입
	                String message = UserDAO.userInsert(userVO);
	                if (message.equals("성공")) {
	                    System.out.println("가입을 축하합니다.");
	                    PrintScreen.printMainSecreen();
	                } else {
	                    System.out.println("오류: 회원 가입이 실패했습니다.");
	                }
	                return; // 메서드 종료
	                
	            case "2":
	                // 2. 다시 입력
	                printUserInsertScreen(); // 재귀 호출
	                return; // 메서드 종료
	                
	            case "3":
	                // 3. 이전 화면으로
	                PrintScreen.printMainSecreen();
	                return; // 메서드 종료
	                
	            default:
	                System.out.println("잘못된 선택입니다. 다시 선택해 주세요.");
	                break; // 올바른 선택이 아닐 경우 반복
	        }
	    }
	}
	
	//회원 탈퇴 화면
	public static void printUserDeleteScreen() {
		
	}
	
	//회원 수정 화면
	public static void printUserUpdateScreen() {
		
	}
	
	// 로그인
	public static void printLoginScreen() {
		PrintScreen.printTitle("[로그인]");
		UserVO userInfo = new UserVO();
		String message = "";

		while (true) {
			System.out.print("아이디 : ");
			String inputId = scan_input.nextLine();
			System.out.print("비밀번호 : ");
			String inputPass = scan_input.nextLine();

			System.out.println("1. 로그인");
			System.out.println("2. 다시 입력");
			System.out.println("3. 이전화면으로");
			System.out.println();
			System.out.println("원하는 기능을 선택하십시오.");
			System.out.print("기능 번호 : ");
			String choice = scan_input.nextLine();

			switch (choice) {
			// 1 일 경우 로그인 시도
			case "1":
				userInfo = UserDAO.userLogin(inputId);
				String user_id = userInfo.getUser_id();
				String user_pass = userInfo.getUser_pass();

				if (userInfo != null) {
					if (user_pass.equals(inputPass)) {
						// 로그인 성공
						message = UserDAO.userRecentLoginUpdate(userInfo.getUser_id());
						if (message.equals("성공")) {
							UserDAO.userRecentLoginUpdate(user_id);
							System.out.println("로그인에 성공했습니다.");
							if (userInfo.getUser_role().equals("admin")) {
								printListAfterLoginForAdmin(userInfo);
							} else if (userInfo.getUser_role().equals("user")) {
								printListAfterLogin(userInfo);
							}
							return;
						} else { // 아이디 비번 틀림 : 로그인 실패
							System.out.println("아이디 혹은 비밀번호가 유효하지 않습니다.");
							System.out.println("로그인 정보를 다시 입력해 주세요.");
							System.out.println();
							break;
						}
					} else { // 로그인 실패
						System.out.println("아이디 혹은 비밀번호가 유효하지 않습니다.");
						System.out.println("로그인 정보를 다시 입력해 주세요.");
						System.out.println();
					}
				}

			// 2일 경우 로그인 정보 다시 입력
			case "2":
				break;

			// 3일 경우 이전 화면으로
			case "3":
				PrintScreen.printMainSecreen();
				return;

			default:
				System.out.println();
				System.out.println("잘못된 입력입니다. 다시 시도하십시오.");
				break;
			}
		}
	}

	
	
	
	// 로그 아웃
	public static void printLogoutScreen() {

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
			printUserViewScreen(currentUserInfo);
			break;

		case "2":
			// 게시물 목록
			BoardScreen.printBoardListScreen(currentUserInfo);
			break;

		case "3":
			// 로그아웃
			printLogoutScreen();
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
			printUserViewScreen(currentUserInfo);
			break;

		// case 3
		// 회원 목록
		// printUserListScreen() 함수 호출

		case "2":
			// 게시물 목록
			BoardScreen.printBoardListScreen(currentUserInfo);
			break;
			
		case "3":
            // 회원 목록
            printUserListScreen();
            break;

		case "4":
			// 로그아웃
			printLogoutScreen();
			break;

		case "5":
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
	public static void findId() {
		PrintScreen.printTitle("[아이디 찾기]");
		System.out.print("이름 : ");
		String input_name = scan_input.nextLine();
		
		System.out.print("전화번호 : ");
		String input_phone = scan_input.nextLine();
		
		
	}

}
