package user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {
	
	private int user_no;
	private String user_id;
	private String user_pass;
	private String user_name;
	private String user_phone;
	private String user_addr;
	private String user_sex;
	private String user_role;
	private String user_delete_YN;
	private String user_login_recent;
	private String user_logout_recent;
	
}
