package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

import data.dataPeer;
import tags.DeCode;
import tags.EnCode;
import tags.Tags;

public class Client {
	public static ArrayList<dataPeer> client = null;
	private ClientServer server;
	private InetAddress ipServer;
	private int portServer = 7777;
	private String nameUser = "";
	private boolean isStop = false;
	private static int portclient = 10000;
	private Socket socketClient;
	private ObjectInputStream StreamIn;		
	private ObjectOutputStream StreamOut;	
	
	public Client(String arg, int arg1, String name, String dataUser) throws Exception {
		ipServer = InetAddress.getByName(arg);
		nameUser = name;
		portclient = arg1;
		client = DeCode.getAllUser(dataUser);
		
		new Thread(new Runnable(){
			@Override
			public void run() {
				updateFriend();
			}
		}).start();
		server = new ClientServer(nameUser);
		(new Request()).start();
		
	}
	public static int getPort() {
		return portclient;
	}
	public void request() throws Exception {
		socketClient = new Socket();
		SocketAddress addressServer = new InetSocketAddress(ipServer, portServer);
		socketClient.connect(addressServer);
		String msg = EnCode.sendRequest(nameUser);
		StreamOut = new ObjectOutputStream(socketClient.getOutputStream());
		StreamOut.writeObject(msg);
		StreamOut.flush();
		StreamIn = new ObjectInputStream(socketClient.getInputStream());
		msg = (String) StreamIn.readObject();
		System.out.print(msg);
		StreamIn.close();
		client = DeCode.getAllUser(msg);
		new Thread(new Runnable() {

			@Override
			public void run() {
				updateFriend();
			}
		}).start();
	}

	public class Request extends Thread {
		@Override
		public void run() {
			super.run();
			while (!isStop) {
				try {
					Thread.sleep(15000);
					request();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void requestChat(String IP, int port_guess, String guest) throws Exception {
		final Socket connclient = new Socket(InetAddress.getByName(IP), port_guess);
		ObjectOutputStream sendrequestChat = new ObjectOutputStream(connclient.getOutputStream());
		sendrequestChat.writeObject(EnCode.sendRequestChat(nameUser));
		sendrequestChat.flush();
		ObjectInputStream receivedChat = new ObjectInputStream(connclient.getInputStream());
		String msg = (String) receivedChat.readObject();
		if (msg.equals(Tags.CHAT_DENY_TAG)) {
			HomeFrame.request("May be your friend busy!", false);
			connclient.close();
			return;
		} else {
			new ChatFrame(nameUser, guest, connclient, portclient);
		}
	}

	public void exit() throws IOException, ClassNotFoundException {
		isStop = true;
		socketClient = new Socket();
		SocketAddress addressServer = new InetSocketAddress(ipServer, portServer);
		socketClient.connect(addressServer);
		String msg = EnCode.exit(nameUser);
		StreamOut = new ObjectOutputStream(socketClient.getOutputStream());
		StreamOut.writeObject(msg);
		StreamOut.flush();
		StreamOut.close();
		server.exit();
	}

	public void updateFriend() {
		int size = client.size();
		HomeFrame.clearAll();
		for (int i = 0; i < size; i++)
			if (!client.get(i).getName().equals(nameUser))
				HomeFrame.update_List_Fiend(client.get(i).getName());
	}
}
