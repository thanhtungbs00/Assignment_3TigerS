package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


import tags.DeCode;
import tags.Tags;

public class ClientServer {
	private String userName = "";
	private ServerSocket serverPeer;
	private int port;
	private boolean isStop = false;

	public void stopServerPeer() {
		isStop = true;
	}

	public boolean getStop() {
		return isStop;
	}

	public ClientServer(String name) throws Exception {
		userName = name;
		port = Client.getPort();
		serverPeer = new ServerSocket(port);
		(new WaitPeerConnect()).start();
	}
	
	public void exit() throws IOException {
		isStop = true;
		serverPeer.close();
	}

	class WaitPeerConnect extends Thread {

		Socket connection;
		ObjectInputStream getRequest;

		@Override
		public void run() {
			super.run();
			while (!isStop) {
				try {
					connection = serverPeer.accept();
					getRequest = new ObjectInputStream(connection.getInputStream());
					String msg = (String) getRequest.readObject();
					String name = DeCode.getNameRequestChat(msg);
					int result = HomeFrame.request("Would you like chat with " + name, true);
					ObjectOutputStream send = new ObjectOutputStream(connection.getOutputStream());
					if (result == 0) {
						send.writeObject(Tags.CHAT_ACCEPT_TAG);
						new ChatFrame(userName, name, connection, port);
					} else if (result == 1) {
						send.writeObject(Tags.CHAT_DENY_TAG);
					}
					send.flush();
				} catch (Exception e) {
					break;
				}
			}
			try {
				serverPeer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
