package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.xdevapi.Result;

import user.UserVO;

public class UserDAO {
	private static Connection conn = null;
	// user list
	private static PreparedStatement userListPsmt = null;
	// user delete
	private static PreparedStatement userDeletePsmt = null;
	// user view
	private static PreparedStatement userViewPsmt = null;
	// user insert
	private static PreparedStatement userInsertPsmt = null;
	// user update
	private static PreparedStatement userUpdatePsmt = null;
	
	//아이디 존재하는지 체크
	private static PreparedStatement userIdValidPsmt = null;
	
	//비밀번호 검증
	private static PreparedStatement userPassAuthPsmt = null;
	
	//최근 로그인/로그아웃 업데이트
	private static PreparedStatement userRecentLoginPsmt = null;
	private static PreparedStatement userRecentLogoutPsmt = null;
	

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

			
			// 페이징 아직
			userListPsmt = conn.prepareStatement("""
					SELECT * FROM TB_USER
					""");
			// user delete
//			userDeletePsmt = conn.prepareStatement("");
			
			
			// user view
			userViewPsmt = conn.prepareStatement("""

					""");
			
			
			// user insert
			userInsertPsmt = conn.prepareStatement("""
					INSERT INTO TB_USER 
					(user_id, user_pass, user_name, user_phone, user_addr, user_sex)
					VALUES
					(?, ?, ?, ?, ?, ?)
					""");
			
			
			// user update
//			userUpdatePsmt = conn.prepareStatement("""
//
//					""");
			
			
			//아이디 존재하는지 체크
			userIdValidPsmt = conn.prepareStatement("""
					SELECT * FROM TB_USER WHERE TB_USER.USER_ID = ?
					""");
			
			
			//비밀번호 검증
			userPassAuthPsmt = conn.prepareStatement("""
					SELECT * FROM TB_USER WHERE USER_ID = ? AND USER_PASS = ?
					""");
			
			
			//최근 로그인/로그아웃 업데이트
			userRecentLoginPsmt = conn.prepareStatement("""
					UPDATE TB_USER
					SET user_login_recent = SYSDATE
					WHERE user_id = ?
					""");
			userRecentLogoutPsmt = conn.prepareStatement("""
					UPDATE TB_USER
					SET user_logout_recent = SYSDATE 
					WHERE user_id = ?
					""");


		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
	}

	// user list
	public static List<UserVO> userList(UserVO userVO) {
		List userList = new ArrayList<UserVO>();

		try {
//			Result rs = userListPsmt.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}

		return userList;
	}

//
//	// user delete
//	public static String userDelete(UserVO userVO) {
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
	// user view
	public static UserVO userView(UserVO userVO) {
		UserVO user = null;
		try {
			userViewPsmt.setInt(1, userVO.getUser_no());
			
			ResultSet rs = userViewPsmt.executeQuery();
			if(rs.next()) {
				user = new UserVO(
						rs.getInt("user_no")
						,rs.getString("user_id")
						,rs.getString("user_pass")
						,rs.getString("user_name")
						,rs.getString("user_phone")
						,rs.getString("user_addr")
						,rs.getString("user_sex")
						,rs.getString("user_role")
						,rs.getString("user_deleteYN")
						,rs.getString("user_login_recent")
						,rs.getString("user_logout_recent")
						);
			}
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
		return user;
	}

	// user insert
	public static String userInsert(UserVO userVO) {
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
				message = "성공";
				conn.commit();
			} else {
				message = "실패";
			}
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
		
		return message;
	}

//	// user update
//	public static String userUpdate(UserVO userVO) {
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
	
	//user id 존재하는지 확인
	public static boolean isIdAvailable(String user_id) {
		boolean isAvailable = false;
		
		try {
			userIdValidPsmt.setString(1, user_id);
			ResultSet rs = userIdValidPsmt.executeQuery();
			if (rs.next()) { // 결과가 있는지 확인
	            isAvailable = true; // 아이디가 존재함
	        } else {
	        	isAvailable = false;
	        } 
			rs.close();
		} catch(Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
		
		return isAvailable;
	}
	
	//use password정확한지 확인
	public static boolean passwordAuthenticate (String user_id, String user_pass) {
		boolean isAuth = false;
		return isAuth;
	}
	
	//로그인시 최근 로그인 update
	public static String userRecentLoginUpdate (String user_id) {
		String message = "";
		int updated = 0;
		try {
			userRecentLoginPsmt.setString(1,user_id);
			updated = userRecentLoginPsmt.executeUpdate();
			
			if (updated == 1) {
				message = "성공";
			} else {
				message = "실패";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}

		return message;
	}
	
	//로그인시 최근 로그아웃 update
	public static String userRecentLogoutUpdate(String user_id) {
		String message = "";
		int updated = 0;
		try {
			userRecentLogoutPsmt.setString(1,user_id);
			updated = userRecentLogoutPsmt.executeUpdate();
			
			if (updated == 1) {
				message = "성공";
			} else {
				message = "실패";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}

		return message;
	}
	
	
	
	
	
	//아이디 찾기
	public static void FindId() {
			
		}
	
	
	
	
	
	
	//비밀번호 초기화
	public static void ResetPass() {
			
		}
}
