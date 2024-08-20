package Loginhistory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LoginHistoryDAO {

	private static Connection conn = null;

	// 특정 회원의 로그인 이력 조회
	private static PreparedStatement loginHisPsmt = null;

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

			// 특정 회원의 로그인 이력 조회
			loginHisPsmt = conn.prepareStatement("""
					SELECT H.HIS_NO, U.USER_NO, U.USER_ID, U.USER_NAME, H.HIS_LOGIN_DATE, H.HIS_LOGOUT_DATE
					FROM TB_LOGIN_HIS H
					JOIN TB_USER U
					ON H.USER_NO = U.USER_NO
					WHERE H.user_no = ?
					""");

		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
	}

	public static List<LoginHistoryVO> hisView(int user_no) {
		List<LoginHistoryVO> history = new ArrayList<LoginHistoryVO>();
		
		try {
			loginHisPsmt.setInt(1, user_no);
			ResultSet rs = loginHisPsmt.executeQuery();
			while (rs.next()) {
				LoginHistoryVO his = new LoginHistoryVO(
						rs.getInt("his_no")
						,rs.getInt("user_no")
						,rs. getString("user_id")
						,rs. getString("user_name")
						,rs. getString("his_login_date")
						,rs. getString("his_logout_date"));
				history.add(his);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
		return history;
	}
}
