package board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import user.UserVO;

public class BoardDAO {

	// DB 연결
	private static Connection conn = null;
	// board list
	private static PreparedStatement boardListPsmt = null;
	// board delete
	private static PreparedStatement boardDeletePsmt = null;
	// board view
	private static PreparedStatement boardViewPsmt = null;
	private static PreparedStatement boardViewCntIncPsmt = null;

	// board insert
	private static PreparedStatement boardInsertPsmt = null;
	// board update
	private static PreparedStatement boardUpdatePsmt = null;

	// 연결 및 쿼리
	static {
		try {

			// JDBC Driver 등록
			Class.forName("oracle.jdbc.OracleDriver");

			// 연결하기
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/xe", "MSA07", // user
					"1004" // password
			);
			// 자동 commit 끄기
			conn.setAutoCommit(false);

			// board list - 게시물 목록 조회 + 24시간 이내면 시:분만 출력
			// 페이징 아직
			boardListPsmt = conn.prepareStatement("""
					SELECT B.BOARD_NO,
					U.USER_NO,
					U.USER_NAME AS BOARD_WRITER,
					B.BOARD_TITLE,
					B.BOARD_VIEW_CNT,
					CASE
					WHEN B.BOARD_REG_DATE >= (SYSDATE-1) THEN TO_CHAR(B.BOARD_REG_DATE, 'HH24:MI')
					ELSE TO_CHAR(B.BOARD_REG_DATE, 'YYYY-MM-DD')
					END AS BOARD_REG_DATE
					FROM TB_BOARD B
					JOIN TB_USER U ON B.USER_NO = U.USER_NO
					ORDER BY B.BOARD_NO ASC
					""");
			// board delete
			boardDeletePsmt = conn.prepareStatement("DELETE FROM TB_BOARD WHERE board_no = ?");
			// board view
			boardViewPsmt = conn.prepareStatement("""
					SELECT B.BOARD_NO,
					B.USER_NO,
					U.USER_NAME AS BOARD_WRITER,
					B.BOARD_TITLE, B.BOARD_CONTENT,
					B.BOARD_VIEW_CNT, B.BOARD_REG_DATE
					FROM TB_BOARD B
					JOIN TB_USER U ON B.USER_NO = U.USER_NO
					WHERE B.BOARD_NO = ?
					ORDER BY B.BOARD_NO ASC
										""");
			boardViewCntIncPsmt = conn.prepareStatement("""
					UPDATE TB_BOARD SET BOARD_VIEW_CNT = BOARD_VIEW_CNT+1
					WHERE BOARD_NO = ?
					""");
			// board insert
			boardInsertPsmt = conn.prepareStatement("""
					INSERT INTO TB_BOARD
					(user_no, board_title, board_content, board_pass)
					VALUES
					(?, ?, ?, ?)
					""");
			// board update
			boardUpdatePsmt = conn.prepareStatement("""
					UPDATE TB_BOARD SET
					BOARD_TITLE = ?,
					BOARD_CONTENT = ?
					WHERE BOARD_NO = ?
					""");

		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
	}

	// board list
	public static List<BoardVO> boardList() {
		List<BoardVO> boardList = new ArrayList<BoardVO>();

		try {
			ResultSet rs = boardListPsmt.executeQuery();
			while (rs.next()) { // 행이 여러개니까 다음행 있으면 리스트에 담고 없으면 끝내는 코드
				BoardVO board = new BoardVO(rs.getInt("board_no"), rs.getString("board_writer"),
						rs.getString("board_title"), rs.getInt("board_view_cnt"), rs.getString("board_reg_date"));
				boardList.add(board);
			}
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}

		return boardList;
	}

	// board delete
	public static String boardDelete(BoardVO boardVO) {
		int updated = 0;
		// 오류&성공 메세지 리턴을 위한 변수
		String message = "";

		try {
			boardDeletePsmt.setInt(1, boardVO.getBoard_no());
			updated = boardDeletePsmt.executeUpdate();

			if (updated == 1) {
				message = "성공";
				conn.commit();
			} else {
				message = "실패";
			}

		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}

		return message;
	}

	// board view
	public static BoardVO boardView(String board_no) {
		BoardVO board = null;
		int updated = 0;
		String message = "";
		try {

			boardViewCntIncPsmt.setString(1, board_no);
			boardViewPsmt.setString(1, board_no);
			updated = boardViewCntIncPsmt.executeUpdate();

			if (updated == 1) {
				conn.commit();
				message = "성공";

				ResultSet rs = boardViewPsmt.executeQuery();

				if (rs.next()) {
					board = new BoardVO(rs.getInt("board_no"), rs.getString("board_writer"),
							rs.getString("board_title"), rs.getString("board_content"), rs.getInt("board_view_cnt"),
							rs.getString("board_reg_date"));
				}

			} else {
				message = "실패";
			}
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
		return board;
	}

	// board insert
	public static String boardInsert(UserVO userVO, BoardVO boardVO) {
		String message = "";
		int updated = 0;

		try {
			boardInsertPsmt.setInt(1, userVO.getUser_no());
			boardInsertPsmt.setString(2, boardVO.getBoard_title());
			boardInsertPsmt.setString(3, boardVO.getBoard_content());
			boardInsertPsmt.setString(4, boardVO.getBoard_pass());

			updated = boardInsertPsmt.executeUpdate();

			if (updated == 1) {
				message = "성공";
				conn.commit();
			} else {
				message = "실패";
			}

		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}

		return message;
	}

	// board update
	public static String boardUpdate(BoardVO boardVO) {
		String message = "";
		int updated = 0;

		try {
			boardUpdatePsmt.setString(1, boardVO.getBoard_title());
			boardUpdatePsmt.setString(2, boardVO.getBoard_content());
			boardUpdatePsmt.setInt(3, boardVO.getBoard_no());
			updated = boardUpdatePsmt.executeUpdate();

			if (updated == 1) {
				message = "성공";
				conn.commit();
			} else {
				message = "실패";
			}

		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}

		return message;

	}

}
