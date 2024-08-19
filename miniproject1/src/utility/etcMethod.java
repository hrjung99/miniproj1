package utility;

public class etcMethod {
	
	public static String formatPhoneNumber(String phoneNumber) {
	    // 전화번호에서 숫자만 추출
	    String digits = phoneNumber.replaceAll("\\D", "");
	    
	    // 전화번호가 11자리인지 확인
	    if (digits.length() == 11) {
	        // 010-1234-5678 형식으로 변환
	        return digits.substring(0, 3) + "-" + digits.substring(3, 7) + "-" + digits.substring(7);
	    } else {
	        // 유효하지 않은 전화번호 형식
	        return phoneNumber; // 원본 전화번호 반환 (혹은 예외 처리)
	    }
	}


}
