package tags;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Tags {
	// -----------login / sign up-------------------
	public static String SIGNUP_OPEN_TAG = "<SIGN_UP>";				//sign up
	public static String SIGNUP_CLOSE_TAG = "</SIGN_UP>";
	//-------------------------------------------------
	public static String LOGIN_OPEN_TAG = "<LOGIN>";				//login
	public static String LOGIN_CLOSE_TAG = "</LOGIN>";
	//-------------------------------------------------
	public static String PEER_NAME_OPEN_TAG = "<PEER_NAME>";		// name
	public static String PEER_NAME_CLOSE_TAG = "</PEER_NAME>";
	//-------------------------------------------------
	public static String PASS_OPEN_TAG = "<PASS>";					// pass
	public static String PASS_CLOSE_TAG = "</PASS>";
	//-------------------------------------------------
	public static String PORT_OPEN_TAG = "<PORT>";					// port
	public static String PORT_CLOSE_TAG = "</PORT>";
	//-------------------------------------------------
	public static String CHAT_REQ_OPEN_TAG = "<CHAT_REQ>";			// request chat
	public static String CHAT_REQ_CLOSE_TAG = "</CHAT_REQ>";
	//-------------------------------------------------
	public static String SESSION_OPEN_TAG = "<SESSION>";			// session
	public static String SESSION_CLOSE_TAG = "</SESSION>";
	//-------------------------------------------------
	public static String CHAT_MSG_OPEN_TAG = "<CHAT_MSG>";			// message
	public static String CHAT_MSG_CLOSE_TAG = "</CHAT_MSG>";
	//-------------------------------------------------
	public static String SESSION_KEEP_ALIVE_OPEN_TAG = "<ALIVE>";	// update user is online
	public static String SESSION_KEEP_ALIVE_CLOSE_TAG = "</ALIVE>";
	//-------------------------------------------------
	public static String STATUS_OPEN_TAG = "<STATUS>";				// status of client
	public static String SERVER_ONLINE = "ALIVE";
	public static String SERVER_OFFLINE = "OOPS >>>WILL BE KILLED<<< ";
	public static String STATUS_CLOSE_TAG = "</STATUS>";
	// ------------------------------------------------
	public static String IP_OPEN_TAG = "<IP>";						// ip
	public static String IP_CLOSE_TAG = "</IP>";	
	//-------------------------------------------------
	public static String SESSION_DENY_TAG = "<SESSION_DENY />";		// server not start
	//-------------------------------------------------
	public static String SESSION_ACCEPT_OPEN_TAG = "<SESSION_ACCEPT>";
	public static String SESSION_ACCEPT_CLOSE_TAG = "</SESSION_ACCEPT>";
	//-------------------------------------------------
	public static String CHAT_DENY_TAG = "<CHAT_DENY />";
	public static String CHAT_ACCEPT_TAG = "<CHAT_ACCEPT />";
	public static String CHAT_CLOSE_TAG = "<CHAT_CLOSE />";
	//-------------------------------------------------
	public static String PEER_OPEN_TAG = "<PEER>";
	public static String PEER_CLOSE_TAG = "</PEER>";
	

	public static int show(JFrame frame, String msg, boolean type) {
		if (type)
			return JOptionPane.showConfirmDialog(frame, msg, null, JOptionPane.YES_NO_OPTION);
		JOptionPane.showMessageDialog(frame, msg);
		return -1;
	}
}