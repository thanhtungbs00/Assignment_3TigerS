package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import data.Database;
import data.dataPeer;
import tags.DeCode;
import tags.Tags;



public class Server {
	private ArrayList<dataPeer> dataPeer = null;	
	private ServerSocket server = null;						
	private Socket socket = null;						// connection
	private ObjectInputStream streamIn = null;			//obInputStream
	private ObjectOutputStream streamOut = null ;		//obOutputClient
	public boolean isStop = false, isExit = false;	
	public static ServerFrame ui;
	public Database db;
	
	// create server
	public Server(String filePath ,int port) throws Exception {
		db = new Database(filePath);
		try{  
		    server = new ServerSocket(port);
	        port = server.getLocalPort();
	        ServerFrame.updateMessage("Server startet. IP : " + InetAddress.getLocalHost().getHostAddress() + ", Port : " + server.getLocalPort());
	        }
		catch(IOException ioe){  
			ServerFrame.updateMessage("\nCan not bind to port " + port + ": " + ioe.getMessage()); 
		}
		dataPeer = new ArrayList<dataPeer>();
		(new WaitForConnect()).start();			
	}

	// wait connection
	public class WaitForConnect extends Thread {
		@Override
		public void run() {
			super.run();
			try {
				while (!isStop) {
					if (acceptConnection()) {
						if (isExit) {
							isExit = false;
						} else {
							streamOut = new ObjectOutputStream(socket.getOutputStream());
							streamOut.writeObject(sendSessionAccept());
							streamOut.flush();
							streamOut.close();
						}
					} else {
						streamOut = new ObjectOutputStream(socket.getOutputStream());
						streamOut.writeObject(Tags.SESSION_DENY_TAG);
						streamOut.flush();
						streamOut.close();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	// accept connection from client
	private boolean acceptConnection() throws Exception {
		socket = server.accept();	
		// server get message from client
		streamIn = new ObjectInputStream(socket.getInputStream());		
		String msg = (String) streamIn.readObject();
		// show message
		System.out.print(msg);
		ServerFrame.updateMessage(msg);
		ArrayList<String> getData = DeCode.getUser(msg);	// getData { login/signup , username , pass , portclient }
		if (getData != null) {
			String type_msg = getData.get(0);
			String user = getData.get(1);
			String pass = getData.get(2);
			ServerFrame.updateMessage("Message : LOGIN - user : " + user);
			if (type_msg == "login") {
				if(loginCLient(user,pass,socket.getInetAddress().toString(),Integer.parseInt(getData.get(3))))
					return true;
				else {
					ServerFrame.updateMessage("User :" + user +"Login failed");
					return false;
				}
			}
			else{
				if(signupClient(user,pass,socket.getInetAddress().toString(),Integer.parseInt(getData.get(3))))
					return true;
				else
					return false;
			}	
		} else {
			ServerFrame.updateMessage("Message : Update state user ");
			int size = dataPeer.size();					
			DeCode.updatePeerOnline(dataPeer, msg);			
			if (size != dataPeer.size()) {					
				isExit = true;								
			}
		}
		return true;
		
	}
	
	private boolean loginCLient(String name , String pass ,String ip , int port) throws Exception {
		if(db.checkLogin(name, pass)) {
			if (!isExsistName(name)) {
				// saveNewPeer (user , ip , port )
				saveNewPeer(name, ip , port);			
				ServerFrame.updateMessage("User :" + name +" is Online-- IP user:"+ip+ "-- port:"+port);
				return true;
			}else {
				return false;
			}
		}
		ServerFrame.updateMessage("User :" + name +" sai mk");
		return false;
	}
	
	private boolean signupClient(String name , String pass ,String ip , int port) throws Exception {
		if (!db.userExists(name)) {	
			if (!isExsistName(name)) {
				db.addUser(name,pass);
				// saveNewPeer (user , ip , port )
				saveNewPeer(name, ip , port);			
				ServerFrame.updateMessage("User :" + name +" is Online-- IP user:"+ip+ "-- port:"+port);
				return true;
			}else
				return false;
		}
		else
			return false;
	}
	
	////////////Kiem tra trung user name
	private boolean isExsistName(String name) throws Exception {
		if (dataPeer == null)
			return false;
		int size = dataPeer.size();
		for (int i = 0; i < size; i++) {
			dataPeer peer = dataPeer.get(i);
			if (peer.getName().equals(name))
				return true;
		}
		return false;
	}

	//show status server
	private String sendSessionAccept() throws Exception {
		String msg = Tags.SESSION_ACCEPT_OPEN_TAG;
		int size = dataPeer.size();				
		for (int i = 0; i < size; i++) {		
			dataPeer peer = dataPeer.get(i);	
			msg += Tags.PEER_OPEN_TAG;			
			msg += Tags.PEER_NAME_OPEN_TAG;
			msg += peer.getName();
			msg += Tags.PEER_NAME_CLOSE_TAG;
			msg += Tags.IP_OPEN_TAG;
			msg += peer.getHost();
			msg += Tags.IP_CLOSE_TAG;
			msg += Tags.PORT_OPEN_TAG;
			msg += peer.getPort();
			msg += Tags.PORT_CLOSE_TAG;
			msg += Tags.PEER_CLOSE_TAG;			
		}
		msg += Tags.SESSION_ACCEPT_CLOSE_TAG;	
		return msg;
	}
	
	//stop server
	public void stopServer() throws Exception {
		if (isStop == false) {
			isStop = true;
			server.close();
			socket.close();	
		}
	}
	
	//save name user
	private void saveNewPeer(String user, String ip, int port) throws Exception {
		dataPeer newPeer = new dataPeer();		
		if (dataPeer.size() == 0)				
			dataPeer = new ArrayList<dataPeer>();
		newPeer.setPeer(user, ip, port);		
		dataPeer.add(newPeer);					
	}
}
