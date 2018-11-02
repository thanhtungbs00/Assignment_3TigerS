package client;

import java.awt.EventQueue;
import java.util.Random;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import tags.EnCode;
import tags.Tags;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;

public class LoginFrame {

	private JPanel contentPane;
	
	private JFrame frmLogin;
	private JTextField textField;	//username
	private JTextField textField_1;	//ip address
	private JTextField textField_2; //port
	private JPasswordField textField_3; // pass
	private JLabel lblStatus;
	
	

	private Pattern checkName = Pattern.compile("[a-zA-Z][^<>]*");

	private String name = "", IP = "",pass="";
	public static int portServer = 7777;
	private JButton btnSignUp;
	
	
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame home = new LoginFrame();
					home.frmLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginFrame() {
		initialize();
		lblStatus.setVisible(false);
	}
	
	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setTitle("Login");
		frmLogin.setResizable(false);
		frmLogin.setBounds(100, 100, 518, 231);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frmLogin.setContentPane(contentPane);
		
		JLabel lblUsername = new JLabel("Username:");
		
		JLabel lblIpAddress = new JLabel("IP Address:");
		
		JLabel lblPort = new JLabel("Port:");
		lblStatus = new JLabel("status");
		textField = new JTextField();
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		try {
			textField_1.setText(Inet4Address.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		textField_2 = new JTextField();
		textField_2.setText("7777");
		textField_2.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setIcon(new ImageIcon(LoginFrame.class.getResource("/icon/login.png")));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnLoginActionPerformed(e);
			}
		});
		
		JLabel lblPassword = new JLabel("Password:");
		
		btnSignUp = new JButton("Sign up");
		btnSignUp.setIcon(new ImageIcon(LoginFrame.class.getResource("/icon/signup.png")));
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnSignUpActionPerformed(arg0);
			}
		});
		
		textField_3 = new JPasswordField();
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(LoginFrame.class.getResource("/icon/friendship.png")));
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(label)
					.addGap(48)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblUsername, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
						.addComponent(lblPassword)
						.addComponent(lblIpAddress, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
						.addComponent(lblPort, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
					.addGap(1)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblStatus, GroupLayout.PREFERRED_SIZE, 189, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(textField)
								.addComponent(textField_2, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
								.addComponent(textField_1, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
								.addComponent(textField_3))
							.addGap(19)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnLogin, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnSignUp, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
					.addGap(25))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnSignUp, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblIpAddress)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblPort)
									.addGap(85))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblUsername)
									.addGap(15)
									.addComponent(lblPassword))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(30)
									.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(15)
									.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblStatus)))
					.addGap(30))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	@SuppressWarnings("deprecation")
	private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {      
		name = textField.getText();
		pass = textField_3.getText();
		lblStatus.setVisible(false);
		IP = textField_1.getText();
		if (checkName.matcher(name).matches() && !IP.equals("")) {
			try {
				// create random port of client
				Random rd = new Random();
				int portPeer = 10000 + rd.nextInt() % 1000;
				
				// get IP Address and port of server
				InetAddress ipServer = InetAddress.getByName(IP);
				portServer = Integer.parseInt(textField_2.getText());
				Socket socketClient = new Socket(ipServer, portServer);
				
				// send message (name,pass,port) login to server
				String msg = EnCode.loginAccount(name,pass,Integer.toString(portPeer));//(name,pass,port)
				ObjectOutputStream StreamOut = new ObjectOutputStream(socketClient.getOutputStream());
				StreamOut.writeObject(msg);
				StreamOut.flush();
				// get message from server
				ObjectInputStream StreamIn = new ObjectInputStream(socketClient.getInputStream());
				//get message
				msg = (String) StreamIn.readObject();
				socketClient.close();
				if (msg.equals(Tags.SESSION_DENY_TAG)) {
					lblStatus.setText(NAME_EXSIST);
					lblStatus.setVisible(true);
					return;
				}
				new HomeFrame(IP, portPeer, name, msg);
				frmLogin.dispose();
			} catch (Exception e) {
				lblStatus.setText(SERVER_NOT_START);
				lblStatus.setVisible(true);
				e.printStackTrace();
			}
		} else {
			lblStatus.setText(NAME_FAILED);
			lblStatus.setVisible(true);
			lblStatus.setText(NAME_FAILED);
		}
	}  
	
	@SuppressWarnings("deprecation")
	private void btnSignUpActionPerformed(java.awt.event.ActionEvent evt) {  
		name = textField.getText();
		pass = textField_3.getText();
		lblStatus.setVisible(false);
		IP = textField_1.getText();
		if (checkName.matcher(name).matches() && !IP.equals("")) {
			try {
				// create random port of client
				Random rd = new Random();
				int portPeer = 10000 + rd.nextInt() % 1000;
				
				// get IP Address and port of server
				InetAddress ipServer = InetAddress.getByName(IP);
				portServer = Integer.parseInt(textField_2.getText());
				Socket socketClient = new Socket(ipServer, portServer);
				
				// send message (name,pass,port) sign up to server
				String msg = EnCode.signupAccount(name,pass,Integer.toString(portPeer));//(name,pass,port)
				ObjectOutputStream StreamOut = new ObjectOutputStream(socketClient.getOutputStream());
				StreamOut.writeObject(msg);
				StreamOut.flush();
				// get message from server
				ObjectInputStream StreamIn = new ObjectInputStream(socketClient.getInputStream());
				//get message
				msg = (String) StreamIn.readObject();
				socketClient.close();
				if (msg.equals(Tags.SESSION_DENY_TAG)) {
					lblStatus.setText(NAME_EXSIST);
					lblStatus.setVisible(true);
					return;
				}
				new HomeFrame(IP, portPeer, name, msg);
				frmLogin.dispose();
			} catch (Exception e) {
				lblStatus.setText(SERVER_NOT_START);
				lblStatus.setVisible(true);
				e.printStackTrace();
			}
		} else {
			lblStatus.setText(NAME_FAILED);
			lblStatus.setVisible(true);
			lblStatus.setText(NAME_FAILED);
		}
	}
	
	private static String NAME_FAILED = "Connect with other name";
	private static String NAME_EXSIST = "Name is exsised or mis password";
	private static String SERVER_NOT_START = "Server not start";
} 

