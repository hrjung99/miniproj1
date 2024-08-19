package utility;

import board.BoardVO;
import user.UserVO;

public class BoardScreen {
	//콘솔 디자인
	private static String title = "";
	private static final int  width = 80; // 구분선의 전체 길이
	private static int padding = (width - title.length()) / 2;
	
	//게시물 목록 화면
	public static void printBoardListScreen(UserVO currentUserInfo) {
		title = "[게시물 목록]";
		System.out.println("=".repeat(width));
		System.out.println(" ".repeat(padding) + title);
		System.out.println("-".repeat(width));
		System.out.printf("%-8s%-15s%-30s%-10s%-20s\n", "번호", "작성자", "제목", "조회수", "작성일");
		System.out.println("-".repeat(width));


		
		
		
		System.out.println("=".repeat(width));	
	}
	
	//게시물 상세 화면
	public static void printBoardViewScreen() {
		
	}
	
	//게시물 등록 화면
	public static void printBoardInsertScreen() {
			
		}
	
	//게시물 삭제 화면
	public static void printBoardDeleteScreen() {
			
		}
	
	//게시물 수정 화면
	public static void printBoardUpdateScreen() {
			
		}
	

}
