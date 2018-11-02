package tags;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnCode {

	private static Pattern checkMessage = Pattern.compile("[^<>]*(<|>)");

	public static String signupAccount(String name , String pass , String port) {
		return Tags.SIGNUP_OPEN_TAG 
				+ Tags.PEER_NAME_OPEN_TAG + name + Tags.PEER_NAME_CLOSE_TAG 
				+ Tags.PASS_OPEN_TAG + pass + Tags.PASS_CLOSE_TAG
				+ Tags.PORT_OPEN_TAG + port + Tags.PORT_CLOSE_TAG;
	}
	
	public static String loginAccount(String name , String pass , String port) {
		return Tags.LOGIN_OPEN_TAG 
				+ Tags.PEER_NAME_OPEN_TAG + name + Tags.PEER_NAME_CLOSE_TAG 
				+ Tags.PASS_OPEN_TAG + pass + Tags.PASS_CLOSE_TAG
				+ Tags.PORT_OPEN_TAG + port + Tags.PORT_CLOSE_TAG;
	}
	
	public static String sendRequest(String name) {
		return Tags.SESSION_KEEP_ALIVE_OPEN_TAG 
				+ Tags.PEER_NAME_OPEN_TAG + name + Tags.PEER_NAME_CLOSE_TAG 
				+ Tags.STATUS_OPEN_TAG + Tags.SERVER_ONLINE + Tags.STATUS_CLOSE_TAG
				+ Tags.SESSION_KEEP_ALIVE_CLOSE_TAG;
	}

	public static String sendMessage(String message) {
		Matcher findMessage = checkMessage.matcher(message);
		String result = "";
		while (findMessage.find()) {
			String subMessage = findMessage.group(0);
			int begin = subMessage.length();
			char nextChar = message.charAt(subMessage.length() - 1);
			System.out.println(result);
			result += subMessage + nextChar;
			subMessage = message.substring(begin, message.length());
			message = subMessage;
			findMessage = checkMessage.matcher(message);
		}
		result += message;
		return Tags.CHAT_MSG_OPEN_TAG + result + Tags.CHAT_MSG_CLOSE_TAG;
	}

	public static String sendRequestChat(String name) {
		return Tags.CHAT_REQ_OPEN_TAG 
				+ Tags.PEER_NAME_OPEN_TAG + name + Tags.PEER_NAME_CLOSE_TAG 
				+ Tags.CHAT_REQ_CLOSE_TAG;
	}

	public static String exit(String name) {
		return Tags.SESSION_KEEP_ALIVE_OPEN_TAG 
				+ Tags.PEER_NAME_OPEN_TAG + name + Tags.PEER_NAME_CLOSE_TAG 
				+ Tags.STATUS_OPEN_TAG + Tags.SERVER_OFFLINE + Tags.STATUS_CLOSE_TAG
				+ Tags.SESSION_KEEP_ALIVE_CLOSE_TAG;
	}
}

