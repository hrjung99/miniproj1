package utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Loginhistory.LoginHistoryDAO;
import Loginhistory.LoginHistoryVO;
import board.BoardDAO;
import board.BoardVO;
import user.UserDAO;
import user.UserVO;

public class BoardScreen {
	// 콘솔 디자인
	private static String title = "";
	private static final int width = 250; // 구분선의 전체 길이
	private static int padding = (width - title.length()) / 2;

	// 스캐너 선언
	private static Scanner scan_input = new Scanner(System.in);
	private static String input;
	
	//게시물 목록 화면
	public static void printBoardListScreen(UserVO currentUserInfo) {
		title = "[게시물 목록]";
		
		 // 전체 테이블의 너비와 열의 너비를 정의합니다.
		int width = 250;
		int noWidth = 5;
		int writerWidth = 15;
		int titleWidth = 40;
		int viewCntWidth = 5;
		int dateWidth = 30;

	    
		// 헤더 출력
		    String header = String.format("| %-" + noWidth + "s | %-" + writerWidth + "s | %-" + titleWidth + "s | %-" + viewCntWidth + "s | %-" + dateWidth + "s |",
		            "게시물 번호", "작성자", "제목", "조회수", "작성일");
		    System.out.println("-".repeat(width));
		    System.out.println(header);
		    System.out.println("-".repeat(width));

		// 게시물 목록 가져오기
		List<BoardVO> boardList = new ArrayList<BoardVO>();
		boardList = BoardDAO.boardList();
		// 각 게시물 정보 출력
		for (BoardVO board : boardList) {
			String row = String.format(
					"| %-" + noWidth + "s | %-" + writerWidth + "s | %-" + titleWidth + "s | %-" + viewCntWidth
							+ "s | %-" + dateWidth + "s |",
					etcMethod.truncateOrPad(String.valueOf(board.getBoard_no()), noWidth),
					etcMethod.truncateOrPad(board.getBoard_writer(), writerWidth),
					etcMethod.truncateOrPad(board.getBoard_title(), titleWidth),
					etcMethod.truncateOrPad(String.valueOf(board.getBoard_view_cnt()), viewCntWidth),
					etcMethod.truncateOrPad(board.getBoard_reg_date(), dateWidth));
			System.out.println(row);
			;
		}

		// 표 끝 부분 출력
		System.out.println("=".repeat(width));

		while (true) {
		    System.out.println();
		    System.out.println("게시물의 번호를 입력하십시오.");
		    System.out.println("\'q\'를 입력하면 이전 화면으로 이동합니다.");
		    System.out.println("\'i\'를 입력하면 게시물 등록 화면으로 이동합니다.");
		    
		    System.out.print("게시물 번호 : ");
		    String board_no = scan_input.nextLine();

		    if (board_no.equalsIgnoreCase("q")) {
		        // 이전 화면으로 이동
		        System.out.println("이전 화면으로 이동합니다.");
			    System.out.println("=".repeat(width));
		        if (currentUserInfo.getUser_role().equals("admin")) {
		            PrintScreen.printListAfterLoginForAdmin(currentUserInfo);
		        } else if (currentUserInfo.getUser_role().equals("user")) {
		            PrintScreen.printListAfterLogin(currentUserInfo);
		        }
		        break;
		    } else if (board_no.equalsIgnoreCase("i")) {
		        // 게시물 등록 화면으로 이동
		        System.out.println("게시물 등록 화면으로 이동합니다.");
				System.out.println("=".repeat(width));
		        String message = BoardScreen.printBoardInsertScreen(currentUserInfo);
		        if (message.equals("성공")) {
		        	System.out.println("게시물 등록에 성공했습니다.");
				    System.out.println("=".repeat(width));
		        	printBoardListScreen(currentUserInfo);
		        	break;
		        } else {
		        	System.out.println("게시물 등록을 실패했습니다.");
		        }
		        break; // 게시물 등록 후에는 루프를 빠져나가도록 설정
		    } else {
		        // 게시물 상세 조회
		        BoardVO boardView = printBoardViewScreen(board_no);
		        if (boardView != null) {
		        	PrintScreen.isMyBoard(currentUserInfo, boardView);
		        } else {
		            System.out.println("게시물을 조회할 수 없습니다.");
		        }
		    }
		}
	}

	
	
// 게시물 상세 화면
	public static BoardVO printBoardViewScreen(String board_no) {
		BoardVO boardView = BoardDAO.boardView(board_no);

		if (boardView != null) {
			PrintScreen.printTitle("[" + boardView.getBoard_title() + "] ");
			System.out.println("");
			System.out.println("게시물 번호 : " + boardView.getBoard_no());
			System.out.println("작성자 : " + boardView.getBoard_writer());
			System.out.println("내용 : " + boardView.getBoard_content());
			System.out.println("조회수  : " + boardView.getBoard_view_cnt());
			System.out.println("작성일 : " + boardView.getBoard_reg_date());
		} else {
			boardView = null;
		}
		return boardView;
	}

	// 게시물 등록 화면
	public static String printBoardInsertScreen(UserVO currentUserInfo) {
		BoardVO newBoard = new BoardVO();
		
	    // 게시물 제목 입력받기
	    System.out.print("게시물 제목을 입력하세요: ");
	    String boardTitle = scan_input.nextLine();
	    newBoard.setBoard_title(boardTitle);

	    // 게시물 내용 입력받기
	    System.out.print("게시물 내용을 입력하세요: ");
	    String boardContent = scan_input.nextLine();
	    newBoard.setBoard_content(boardContent);

	    // 게시물 비밀번호 입력받기
	    System.out.print("게시물 비밀번호를 입력하세요: ");
	    String boardPass = scan_input.nextLine();
	    newBoard.setBoard_pass(boardPass);

	    // 입력받은 정보로 게시물 등록
	    String message = BoardDAO.boardInsert(currentUserInfo, newBoard);
	    System.out.println(message);
	    
	    return message;

	}

	// 게시물 삭제 화면
	public static String printBoardDeleteScreen(BoardVO boardView) {
		
		String message = BoardDAO.boardDelete(boardView);
        return message;
	}

	// 게시물 수정 화면
	public static String printBoardUpdateScreen(BoardVO boardInfo) {
		PrintScreen.printTitle("[게시물 수정]");

        // 기존 게시물 정보를 보여줌
        System.out.println("기존 작성자: " + boardInfo.getBoard_writer());
        System.out.println("기존 제목: " + boardInfo.getBoard_title());
        System.out.println("기존 내용: " + boardInfo.getBoard_content());
        System.out.println();

        System.out.print("새로운 제목 (변경하지 않으려면 Enter): ");
        String newTitle = scan_input.nextLine();
        if (newTitle.isEmpty()) {
            newTitle = boardInfo.getBoard_title();
        }

        System.out.print("새로운 내용 (변경하지 않으려면 Enter): ");
        String newContent = scan_input.nextLine();
        if (newContent.isEmpty()) {
            newContent = boardInfo.getBoard_content();
        }

        // 수정된 정보를 담을 새로운 BoardVO 객체 생성
        BoardVO updatedBoard = new BoardVO();
        updatedBoard.setBoard_no(boardInfo.getBoard_no());
        updatedBoard.setBoard_title(newTitle);
        updatedBoard.setBoard_content(newContent);
        
        String message = BoardDAO.boardUpdate(updatedBoard);
        return message;
			
		}

}
