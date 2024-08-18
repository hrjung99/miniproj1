package board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardVO {
	
	private int board_no;
	private String board_writer;
	private String board_title;
	private String board_content;
	private int board_view_cnt;
	private String board_reg_date;
	private int user_no;

	//board list 생성자
	public BoardVO(int board_no, String board_writer, String board_title, int board_view_cnt, String board_reg_date) {
        this.board_no = board_no;
        this.board_writer = board_writer;
        this.board_title = board_title;
        this.board_view_cnt = board_view_cnt;
        this.board_reg_date = board_reg_date;
    }
	
	public BoardVO(int board_no, String board_writer, String board_title, String board_content, int board_view_cnt, String board_reg_date) {
        this.board_no = board_no;
        this.board_writer = board_writer;
        this.board_title = board_title;
        this.board_content = board_content;
        this.board_view_cnt = board_view_cnt;
        this.board_reg_date = board_reg_date;
    }
	

	
}
