package Loginhistory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginHistoryVO {
	
	private int his_no;
	private int user_no;
	private String user_id;
	private String user_name;
	private String his_login_date;
	private String his_logout_date;
	
}
