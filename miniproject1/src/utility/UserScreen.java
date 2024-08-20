package utility;

import java.util.List;
import java.util.Scanner;

import Loginhistory.LoginHistoryDAO;
import Loginhistory.LoginHistoryVO;
import board.BoardVO;
import user.UserDAO;
import user.UserVO;

public class UserScreen {
	// 콘솔 디자인
	private static String title = "";
	private static final int width = 250; // 구분선의 전체 길이
	private static int padding = (width - title.length()) / 2;

	// 스캐너 선언
	private static Scanner scan_input = new Scanner(System.in);
	private static String input;

	// 정확한 입력 값을 받기위한 변수
	private static boolean isValid = false;

	// 메세지 변수 선언
	private static String message = "";

	// 회원 정보
	private static UserVO userInfo = new UserVO();

	// 회원 목록 화면
	public static void printUserListScreen(UserVO currentUserInfo) {
		PrintScreen.printTitle("[회원 목록]");

		// 전체 테이블의 너비와 열의 너비를 정의합니다.
		int width = 250;
		int noWidth = 5;
		int idWidth = 15;
		int nameWidth = 20;
		int phoneWidth = 30;
		int addrWidth = 40;
		int sexWidth = 10;
		int roleWidth = 13;
		int deleteYNWidth = 16;
		int loginWidth = 30;
		int logoutWidth = 30;

		// 헤더 출력
		String header = String.format(
				"| %-" + noWidth + "s | %-" + idWidth + "s | %-" + nameWidth + "s | %-" + phoneWidth + "s | %-"
						+ addrWidth + "s | %-" + sexWidth + "s | %-" + roleWidth + "s | %-" + deleteYNWidth + "s | %-"
						+ loginWidth + "s | %-" + logoutWidth + "s |",
				"회원번호", "아이디", "이름", "전화번호", "주소", "성별", "권한", "탈퇴여부", "최근 로그인", "최근 로그아웃");
		System.out.println("-".repeat(width));
		System.out.println(header);
		System.out.println("-".repeat(width));

		// 사용자 목록 가져오기
		List<UserVO> userList = UserDAO.userList(new UserVO());

		// 각 사용자 정보 출력
		for (UserVO user : userList) {
			String row = String.format(
					"| %-" + noWidth + "s | %-" + idWidth + "s | %-" + nameWidth + "s | %-" + phoneWidth + "s | %-"
							+ addrWidth + "s | %-" + sexWidth + "s | %-" + roleWidth + "s | %-" + deleteYNWidth
							+ "s | %-" + loginWidth + "s | %-" + logoutWidth + "s |",
					etcMethod.truncateOrPad(String.valueOf(user.getUser_no()), noWidth),
					etcMethod.truncateOrPad(user.getUser_id(), idWidth),
					etcMethod.truncateOrPad(user.getUser_name(), nameWidth),
					etcMethod.truncateOrPad(user.getUser_phone(), phoneWidth),
					etcMethod.truncateOrPad(user.getUser_addr(), addrWidth),
					etcMethod.truncateOrPad(user.getUser_sex(), sexWidth),
					etcMethod.truncateOrPad(user.getUser_role(), roleWidth),
					etcMethod.truncateOrPad(user.getUser_delete_YN(), deleteYNWidth),
					etcMethod.truncateOrPad(
							user.getUser_login_recent() != null ? user.getUser_login_recent().toString() : "null",
							loginWidth),
					etcMethod.truncateOrPad(
							user.getUser_logout_recent() != null ? user.getUser_logout_recent().toString() : "null",
							logoutWidth));
			System.out.println(row);
			;
		}

		// 표 끝 부분 출력
		System.out.println("=".repeat(width));

		while (true) {
			System.out.println();
			System.out.println("상세하게 보고싶은 회원의 번호를 입력하십시오.");
			System.out.println("\'q\'를 입력하면 이전 화면으로 이동합니다.");
			System.out.print("회원 번호 : ");
			String user_no = scan_input.nextLine();

			if (user_no.equalsIgnoreCase("q")) {
				System.out.println("이전 화면으로 이동합니다.");
				PrintScreen.printListAfterLoginForAdmin(currentUserInfo);
				break;
			} else {
				UserVO userInfo = UserDAO.userView(user_no);
				if (userInfo != null) {
					PrintScreen.printTitle("[" + userInfo.getUser_id() + "의 회원 정보] ");
					System.out.println("");
					System.out.println("회원 번호 : " + userInfo.getUser_no());
					System.out.println("아이디 : " + userInfo.getUser_id());
					System.out.println("이름 : " + userInfo.getUser_name());
					System.out.println("전화번호  : " + userInfo.getUser_phone());
					System.out.println("주소 : " + userInfo.getUser_addr());
					System.out.println("성별 : " + userInfo.getUser_sex());
					System.out.println("권한 : " + userInfo.getUser_role());
					System.out.println("탈퇴 여부 : " + userInfo.getUser_delete_YN());
					System.out.println("최근 로그인 : " + userInfo.getUser_login_recent());
					System.out.println("최근 로그아웃  : " + userInfo.getUser_logout_recent());

					boolean validInput = false;

					while (!validInput) { // 사용자가 올바른 선택을 할 때까지 반복
						System.out.println();
						System.out.println("1. [" + userInfo.getUser_id() + "] 님의 로그인 이력 조회");
						System.out.println("2. [" + userInfo.getUser_id() + "] 님의 회원 탈퇴");
						System.out.println("3. 다른 회원 조회");
						System.out.println();
						System.out.print("원하는 기능을 선택해 주세요 : ");
						String choice = scan_input.nextLine();

						switch (choice) {
						case "1":
							// 로그인 이력 조회 로직 호출
							PrintScreen.printTitle("[" + userInfo.getUser_id() + "님의 로그인 이력 조회]");

							// 해당 사용자의 로그인 이력 가져오기
							List<LoginHistoryVO> loginHis = LoginHistoryDAO.hisView(userInfo.getUser_no());

							// 전체 테이블의 너비와 열의 너비를 정의합니다.
							int login_width = 250;
							int his_noWidth = 5;
							int login_idWidth = 15;
							int login_nameWidth = 20;
							int login_hisWidth = 30;
							int logout_hisWidth = 40;

							// 헤더 출력
							header = String.format(
									"| %-" + his_noWidth + "s | %-" + login_idWidth + "s | %-" + login_nameWidth
											+ "s | %-" + login_hisWidth + "s | %-" + logout_hisWidth + "s |",
									"이력 번호", "아이디", "이름", "로그인 이력", "로그아웃 이력");
							System.out.println("-".repeat(width));
							System.out.println(header);
							System.out.println("-".repeat(width));

							// 각 사용자 정보 출력
							for (LoginHistoryVO his : loginHis) {
								String row = String.format(
										"| %-" + his_noWidth + "s | %-" + login_idWidth + "s | %-" + login_nameWidth
												+ "s | %-" + login_hisWidth + "s | %-" + logout_hisWidth + "s |",
										etcMethod.truncateOrPad(String.valueOf(his.getHis_no()), his_noWidth),
										etcMethod.truncateOrPad(his.getUser_id(), login_idWidth),
										etcMethod.truncateOrPad(his.getUser_name(), login_nameWidth),
										etcMethod.truncateOrPad(
												his.getHis_login_date() != null ? his.getHis_login_date().toString()
														: "null",
												login_hisWidth),
										etcMethod.truncateOrPad(
												his.getHis_logout_date() != null ? his.getHis_logout_date().toString()
														: "null",
												logout_hisWidth));
								System.out.println(row);
								;
							}
							// 표 끝 부분 출력
							System.out.println("=".repeat(width));
							validInput = true;
							break;
						case "2":
							// 회원 삭제 로직 호출
							message = printUserDeleteScreen(userInfo.getUser_no());
							if (message.equals("성공")) {
								System.out.println("성공");
								validInput = true;
							} else {
								System.out.println("실패");
							}

						case "3":
							System.out.println("회원 선택으로 돌아갑니다.");
							validInput = true;
							break;
						default:
							System.out.println("잘못된 입력입니다. 다시 시도해 주세요.");
							break;
						}
					}
				} else {
					System.out.println("오류 : 사용자 정보를 조회할 수 없습니다.");
				}
			}
		}
	}

// 마이페이지 화면
	public static void printUserViewScreen(UserVO currentUserInfo) {
		PrintScreen.printTitle("[나의 정보 확인]");
		// 조회된 사용자 정보를 화면에 출력
		if (currentUserInfo != null) {
			System.out.println("아이디        : " + currentUserInfo.getUser_id());
			System.out.println("이름         : " + currentUserInfo.getUser_name());
			System.out.println("전화번호       : " + currentUserInfo.getUser_phone());
			System.out.println("주소          : " + currentUserInfo.getUser_addr());
			System.out.println("성별          : " + currentUserInfo.getUser_sex());
			System.out.println("최근 로그인     : " + currentUserInfo.getUser_login_recent());
			System.out.println("최근 로그아웃   : " + currentUserInfo.getUser_logout_recent());
			if (currentUserInfo.getUser_role().equalsIgnoreCase("admin")) {
				PrintScreen.printListAfterLoginForAdmin(currentUserInfo);
			} else {
				PrintScreen.printListAfterLogin(currentUserInfo);
			}

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

	// 회원 탈퇴 화면
	public static String printUserDeleteScreen(int user_no) {
		String message = UserDAO.userDelete(userInfo.getUser_no());
		return message;
	}

	// 회원 수정 화면
	public static void printUserUpdateScreen(UserVO user) {
		UserVO newUserInfo = new UserVO();
		PrintScreen.printTitle("[" + user.getUser_name() + " 님의 회원 정보 수정]");

		while (true) {
			System.out.println();
			System.out.println("비밀번호를 입력해 주세요.");
			System.out.println("비밀번호 : ");
			input = scan_input.nextLine();

			if (input.equals(user.getUser_pass())) {
				System.out.println("비밀번호 일치");
				System.out.println();
				System.out.print("아이디 : " + user.getUser_id());
				newUserInfo.setUser_id(user.getUser_id());

				System.out.print("비밀번호 : ");
				input = scan_input.nextLine();
				newUserInfo.setUser_pass(input);

				System.out.print("이름 : " + user.getUser_name());
				newUserInfo.setUser_name(user.getUser_name());

				System.out.println("전화번호 입력 시 \"-\"를 제외하고 숫자만 입력하십시오.");
				System.out.print("전화번호 : ");
				input = scan_input.nextLine();
				newUserInfo.setUser_phone(etcMethod.formatPhoneNumber(input));

				System.out.println("주소 입력 예시 :  \"서울특별시 강서구 화곡동\"");
				System.out.print("주소 : ");
				input = scan_input.nextLine();
				newUserInfo.setUser_addr(input);

				System.out.println("성별 입력 예시 :  \"남\", \"여\" 중 선택");
				System.out.print("성별 : " + user.getUser_sex());
				newUserInfo.setUser_sex(newUserInfo.getUser_sex());
				break;
			} else {
				System.out.println("비밀번호가 일치하지 않습니다.");
			}
		}

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
								PrintScreen.printListAfterLoginForAdmin(userInfo);
							} else if (userInfo.getUser_role().equals("user")) {
								PrintScreen.printListAfterLogin(userInfo);
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

	// 로그아웃
	public static void printLogoutScreen(UserVO userInfo) {
		String message = "";
		message = UserDAO.userRecentLogoutUpdate(userInfo);
		if (message.equals("성공")) {
			System.out.println("로그아웃 완료");
			PrintScreen.printMainSecreen();
		} else {
			System.out.println("로그아웃 도중 오류가 발생했습니다.");
			System.exit(0); // 프로그램 종료
		}

	}

	// 아이디 찾기
	public static void findId() {
		PrintScreen.printTitle("[아이디 찾기]");

		while (true) {
			System.out.println("아이디 찾기를 종료하고 싶으시다면, 'q'를 눌러주십시오. 이전 화면으로 돌아갑니다.");

			System.out.print("이름 : ");
			String input_name = scan_input.nextLine();

			System.out.print("전화번호 : ");
			String input_phone = scan_input.nextLine();
			input_phone = etcMethod.formatPhoneNumber(input_phone); // 전화번호 포맷 맞추기

			if ("q".equalsIgnoreCase(input_name) || "q".equalsIgnoreCase(input_phone)) {
				// 사용자가 'q'를 입력한 경우, 메인 화면으로 이동
				PrintScreen.printMainSecreen();
				return; // 메인 화면으로 이동 후 메서드 종료
			}

			String user_id = UserDAO.userFindId(input_name, input_phone);

			if (user_id != null) {
				// 아이디 찾기 성공
				System.out.println("[" + input_name + "]님의 아이디는 [" + user_id + "] 입니다.");
				PrintScreen.printMainSecreen(); // 아이디 찾기 후 메인 화면으로 이동
				return; // 메서드 종료
			} else {
				// 아이디 찾기 실패
				System.out.println("회원 정보를 찾을 수 없습니다. 다시 입력해 주십시오.");
				System.out.println();
				// while 루프가 반복되어 사용자에게 다시 입력을 받도록 함
			}
		}
	}

	// 비밀번호 초기화
	public static void resetPassword() {
		PrintScreen.printTitle("[비밀번호 초기화]");
		while (true) {
			System.out.print("아이디 : ");
			String input_id = scan_input.nextLine();

			System.out.print("이름 : ");
			String input_name = scan_input.nextLine();

			System.out.print("전화번호 : ");
			String input_phone = scan_input.nextLine();
			input_phone = etcMethod.formatPhoneNumber(input_phone);

			boolean check = UserDAO.checkUserForPasswordReset(input_id, input_name, input_phone);
			if (check) { // true 일경우 = 해당 아이디 존재한다.
				System.out.println();
				System.out.println("[" + input_id + "]님의 비밀번호 초기화");
				System.out.println();
				System.out.println("변경할 비밀번호를 입력해주십시오.");
				System.out.println("비밀번호 초기화를 종료하고 싶으시다면, q를 눌러주십시오. 이전화면으로 돌아갑니다.");
				System.out.print("새로운 비밀번호 :");
				String input_pass = scan_input.nextLine();
				if ("q".equalsIgnoreCase(input_pass)) {
					// 사용자가 'q'를 입력한 경우, 메인 화면으로 이동
					PrintScreen.printMainSecreen();
					return; // 메인 화면으로 이동 후 메서드 종료
				}
				// 'q'가 아닌 경우 비밀번호 초기화 시도
				String message = UserDAO.userResetPass(input_id, input_name, input_phone, input_pass);

				if (message.equals("성공")) {
					System.out.println();
					System.out.println("비밀번호가 초기화 되었습니다.");
					PrintScreen.printMainSecreen();
				} else {
					System.out.println("오류 발생");
				}
			} else {
				System.out.println("해당 정보의 회원을 찾을 수 없습니다.");
				System.out.println("1. 다시 입력");
				System.out.println("2. 이전 화면으로");

				System.out.println();
				System.out.println("원하는 기능을 선택하십시오.");
				System.out.print("기능 번호 : ");
				input = scan_input.nextLine();

				switch (input) {
				case "1":
					// 다시 입력 화면으로 돌아가기
					break;
				case "2":
					PrintScreen.printMainSecreen(); // 메인 화면으로 이동
					return; // 메인 화면으로 이동 후 종료
				default:
					System.out.println("잘못된 입력입니다.");
					// 기능 선택으로 돌아가기
					break;
				}
			}

		}
	}
}
