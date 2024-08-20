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
	
	// 문자열을 고정된 길이로 잘라내거나 빈 공간을 채우는 함수
	public static String truncateOrPad(String text, int length) {
	    int padding = correctWidth(text, length) - text.length();
	    if (text.length() > length) {
	        return text.substring(0, length); // 문자열을 잘라냄
	    } else {
	        return text + " ".repeat(padding); // 부족한 부분을 공백으로 채움
	    }
	}

	// 한글 등 넓은 문자를 고려하여 문자 폭을 보정하는 함수
	public static int correctWidth(String text, int originalWidth) {
	    int korCount = 0;
	    for (char c : text.toCharArray()) {
	        if (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HANGUL_SYLLABLES
	                || Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HANGUL_JAMO
	                || Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HANGUL_COMPATIBILITY_JAMO) {
	            korCount++;
	        }
	    }
	    return originalWidth - (korCount / 2); // 한글 문자는 두 글자로 계산
	}


}
