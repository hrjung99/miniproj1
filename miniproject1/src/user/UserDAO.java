package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import user.UserVO;

public class UserDAO {
	private static Connection conn = null;
	// user list
	private static PreparedStatement userListPsmt = null;
	// user delete
	private static PreparedStatement userDeletePsmt = null;
	// user view
	private static PreparedStatement userViewPsmt = null;
	private static PreparedStatement userViewCntIncPsmt = null;

	// user insert
	private static PreparedStatement userInsertPsmt = null;
	// user update
	private static PreparedStatement userUpdatePsmt = null;

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

			// user list - 게시물 목록 조회 + 24시간 이내면 시:분만 출력
			// 페이징 아직
			userListPsmt = conn.prepareStatement("""

					""");
			// user delete
			userDeletePsmt = conn.prepareStatement("");
			// user view
			userViewPsmt = conn.prepareStatement("""

					""");
			userViewCntIncPsmt = conn.prepareStatement("""

					""");
			// user insert
			userInsertPsmt = conn.prepareStatement("""
					INSERT INTO TB_USER 
					(user_id, user_pass, user_name, user_phone, user_addr, user_sex)
					VALUES
					(?, ?, ?, ?, ?, ?)
					""");
			// user update
			userUpdatePsmt = conn.prepareStatement("""

					""");

		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
	}

//	// user list
//	public List<UserVO> userList(UserVO userVO) {
//		List userList = new ArrayList<UserVO>();
//
//		try {
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			e.getMessage();
//		}
//
//		return userList;
//	}
//
//	// user delete
//	public String userDelete(UserVO userVO) {
//		int updated = 0;
//		// 오류&성공 메세지 리턴을 위한 변수
//		String message = "";
//
//		try {
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			e.getMessage();
//		}
//
//		return message;
//	}
//
//	// user view
//	public UserVO userView(UserVO userVO) {
//		UserVO user = null;
//		int updated = 0;
//		int user_no = user.getUser_no();
//		String message = "";
//		try {
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			e.getMessage();
//		}
//		return user;
//	}

	// user insert
	public String userInsert(UserVO userVO) {
		String message = "";
		int updated = 0;

		try {
			userInsertPsmt.setString(1,userVO.getUser_id());
			userInsertPsmt.setString(2, userVO.getUser_pass());
			userInsertPsmt.setString(3, userVO.getUser_name());
			userInsertPsmt.setString(4, userVO.getUser_phone());
			userInsertPsmt.setString(5, userVO.getUser_addr());
			userInsertPsmt.setString(6, userVO.getUser_sex());
			
			updated = userInsertPsmt.executeUpdate();
			
			if (updated == 1) {
				message = "회원 가입 성공";
				conn.commit();
			} else {
				message = "오류 : 회원 가입 실패";
			}
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
		
		return message;
	}

//	// user update
//	public String userUpdate(UserVO userVO) {
//		String message = "";
//		int updated = 0;
//
//		try {
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			e.getMessage();
//		}
//
//		return message;
//
//	}

}
