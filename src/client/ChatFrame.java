package client;

import java.awt.EventQueue;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import tags.DeCode;
import tags.EnCode;
import tags.Tags;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;


public class ChatFrame {

	private static String URL_DIR = System.getProperty("user.dir");
	private static String TEMP = "/temp/";

	private ChatRoom chat;
	public Socket socketChat;
	private String nameUser = ""; 
	private String nameGuest = "";
	private int portServer = 0;
	
	private JFrame frmChat;

	public boolean isStop = false, isSendFile = false, isReceiveFile = false;
	public File file;
	
	private JPanel contentPane;
	private JTextField textChat;
	public JTextField textPath;
	public JButton btnOpenFile;
	public JButton btnSendFile;
	public JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatFrame home = new ChatFrame();
					home.frmChat.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public ChatFrame(String user, String guest, Socket socket, int port) {
		nameUser = user;
		nameGuest = guest;
		socketChat = socket;
		this.portServer = port;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatFrame home = new ChatFrame(nameUser, nameGuest, socketChat, portServer, 0);
					home.frmChat.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public ChatFrame (String user, String guest, Socket socket, int port, int a) throws Exception {
		nameUser = user;
		nameGuest = guest;
		socketChat = socket;
		this.portServer = port;
		initialize();
		chat = new ChatRoom(socketChat, nameUser, nameGuest, this);
		chat.start();
	}
	
	public ChatFrame() {
		initialize();
	}
	
	public void updateChat(String msg) {
		textArea.append(msg + "\n");
	}
	
	private void initialize() {
		frmChat = new JFrame();
		frmChat.setTitle(nameGuest);
		frmChat.setResizable(false);
		frmChat.setBounds(100, 100, 463, 474);
		//frmChat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frmChat.setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		
		textChat = new JTextField();
		textChat.setColumns(10);
		
		JButton btnSend = new JButton("Send");
		btnSend.setIcon(new ImageIcon(ChatFrame.class.getResource("/icon/send.png")));
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnSendActionPerformed(arg0);
			}
		});
		
		textPath = new JTextField();
		textPath.setColumns(10);
		
		btnOpenFile = new JButton("Open file");
		btnOpenFile.setIcon(new ImageIcon(ChatFrame.class.getResource("/icon/folder.png")));
		btnOpenFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnOpenFileActionPerformed(e);
			}
		});
		
		btnSendFile = new JButton("Send file");
		btnSendFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				try {
					btnSendFileActionPerformed(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnSendFile.setEnabled(false);
		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		
		JButton btnDisconnect = new JButton("Disconnect");
		btnDisconnect.setIcon(new ImageIcon(ChatFrame.class.getResource("/icon/logout.png")));
		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnDisconnectActionPerformed(arg0);
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 424, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addContainerGap()
								.addComponent(textChat, GroupLayout.PREFERRED_SIZE, 287, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnSend, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(textPath, GroupLayout.PREFERRED_SIZE, 232, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnOpenFile)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnSendFile, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE))))
					.addGap(26))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(152)
					.addComponent(btnDisconnect, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
					.addGap(141))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 253, GroupLayout.PREFERRED_SIZE)
					.addGap(26)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(textChat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSend, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(textPath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnOpenFile)
						.addComponent(btnSendFile))
					.addGap(18)
					.addComponent(btnDisconnect)
					.addContainerGap(17, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	private void btnSendActionPerformed(java.awt.event.ActionEvent evt) { 
		if (isStop) {
			updateChat("[" + nameUser +"] : " + textChat.getText().toString());
			textChat.setText("");
			return;
		}
		String msg = textChat.getText();
		if (msg.equals(""))
			return;
		try {
			chat.sendMessage(EnCode.sendMessage(msg));
			updateChat("["+ nameUser +"] : " + msg);
			textChat.setText("");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void btnDisconnectActionPerformed(java.awt.event.ActionEvent evt) {
		int result = Tags.show(frmChat, "Do you want close chat with "+ nameGuest, true);
		if (result == 0) {
			try {
				isStop = true;
				frmChat.dispose();
				chat.sendMessage(Tags.CHAT_CLOSE_TAG);
				chat.stopChat();
				System.gc();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	private void btnOpenFileActionPerformed(java.awt.event.ActionEvent evt) { 
		JFileChooser fileChooser = new JFileChooser();
        fileChooser.showDialog(frmChat, "Select File");
        file = fileChooser.getSelectedFile();
        
        if(file != null){
            if(!file.getName().isEmpty()){
            	btnSendFile.setEnabled(true);
            	String str;
                
                if(textPath.getText().length() > 30){
                    String t = file.getPath();
                    str = t.substring(0, 20) + " [...] " + t.substring(t.length() - 20, t.length());
                }
                else{
                    str = file.getPath();
                }
                textPath.setText(str);
            }
        }
	}
	
	private void btnSendFileActionPerformed(java.awt.event.ActionEvent evt) throws Exception{ 
		long size = file.length();
        if(size < 120 * 1024 * 1024){
        	ObjectOutputStream outPeer = new ObjectOutputStream(socketChat.getOutputStream());
        	String[] msg = new String[4];
        	msg[0] = "upload_req"; //type
        	msg[1] = nameUser; //sender
        	msg[2] = file.getPath(); // content
        	msg[3] = nameGuest; // receiver
        	outPeer.writeObject(msg);
        	outPeer.flush();
        }
        else{
        	textArea.append("[Application > Me] : File is size too large\n");
        }
	}
	
	// class chat room 
	public class ChatRoom extends Thread {

		private Socket connect;
		private ObjectOutputStream outPeer;
		private ObjectInputStream inPeer;
		private boolean finishReceive = false;
		private String nameFileReceive = "";
		public ChatFrame chatFrame;

		public ChatRoom(Socket connection, String name, String guest, ChatFrame frame)
				throws Exception {
			connect = new Socket();
			connect = connection;
			nameGuest = guest;
			this.chatFrame = frame;
		}

		@Override
		public void run() {
			super.run();
			while (!isStop) {
				try {
					inPeer = new ObjectInputStream(connect.getInputStream());
					Object obj = inPeer.readObject();
					if (obj instanceof String) {
						String msgObj = obj.toString();
						if (msgObj.equals(Tags.CHAT_CLOSE_TAG)) {
							isStop = true;
							Tags.show(frmChat, nameGuest
									+ " may be close chat with you!", false);
							connect.close();
							break;
						}
						else {
							String message = DeCode.getMessage(msgObj);
							updateChat("[" + nameGuest + "] : " + message);
						}
					}
					else if(obj instanceof String[]) {
						Object [] arrayObj = (Object[]) obj;
						String [] msg = new String[4];
						for (int i = 0; i< arrayObj.length; i++) {
							msg[i] = arrayObj[i].toString();
						}
						if (msg[0].equals("upload_req")) {
							ObjectOutputStream objOut = new ObjectOutputStream(connect.getOutputStream());
							String [] message = new String[4];
							message[0] = "upload_res";
                            message[1] = msg[3];
                            message[2] = "NO"; // when you say NO
                            message[3] = msg[1];
							if(JOptionPane.showConfirmDialog(frmChat, ("Accept '"+msg[2]+"' from "+msg[1]+" ?")) == 0){
		                        
		                        JFileChooser jf = new JFileChooser();
		                        jf.setSelectedFile(new File(msg[2]));
		                        int returnVal = jf.showSaveDialog(frmChat);
		                       
		                        String saveTo = jf.getSelectedFile().getPath();
		                        if(saveTo != null && returnVal == JFileChooser.APPROVE_OPTION){
		                            Download dwn = new Download(saveTo, chatFrame);
		                            Thread t = new Thread(dwn);
		                            t.start();
		                            //send(new Message("upload_res", (""+InetAddress.getLocalHost().getHostAddress()), (""+dwn.port), msg.sender));
		                            //send(new Message("upload_res", ui.username, (""+dwn.port), msg.sender));
		                            message[0] = "upload_res";
		                            //message[1] = connect.getInetAddress().getHostAddress();
		                            //System.out.println("Ten may: " + message[1]);
		                            message[1] = connect.getLocalAddress().getHostAddress();
		                            message[2] = Integer.toString(dwn.port);
		                            message[3] = msg[1];
		                            objOut.writeObject(message);
		                        }
		                        else{
		                            //send(new Message("upload_res", ui.username, "NO", msg.sender));
		                        	objOut.writeObject(message);
		                        }
		                    }
		                    else{
		                        //send(new Message("upload_res", ui.username, "NO", msg.sender));
		                    	objOut.writeObject(message);
		                    }
							objOut.flush();
                            //objOut.close();
						}
						else if (msg[0].equals("upload_res")) {
							if(!msg[2].equals("NO")){
		                        int port  = Integer.parseInt(msg[2]);
		                        String addr = msg[1];
		                        
		                        chatFrame.btnOpenFile.setEnabled(false); chatFrame.btnSendFile.setEnabled(false);
		                        Upload upl = new Upload(addr, port, chatFrame.file, chatFrame);
		                        Thread t = new Thread(upl);
		                        t.start();
		                    }
		                    else{
		                    	chatFrame.textArea.append("[FILE] : " +msg[1]+" rejected file request\n");
		                    }
						}
						//inData.close();
					}
				} catch (Exception e) {
					File fileTemp = new File(URL_DIR + TEMP + nameFileReceive);
					if (fileTemp.exists() && !finishReceive) {
						fileTemp.delete();
					}
				}
			}
		}

		public synchronized void sendMessage(Object obj) throws Exception {
			outPeer = new ObjectOutputStream(connect.getOutputStream());
			if (obj instanceof String) {
				String message = obj.toString();
				outPeer.writeObject(message);
				outPeer.flush();
				if (isReceiveFile)
					isReceiveFile = false;
			} 
		}

		public void stopChat() {
			try {
				connect.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
