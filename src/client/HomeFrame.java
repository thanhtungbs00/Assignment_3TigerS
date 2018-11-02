package client;


import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import tags.Tags;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Choice;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class HomeFrame {

	private JPanel contentPane;
	private JFrame frmHome;
	private JTextField textUsername;
	private static Choice choiceFriends;
	
	private static String IPClient = ""; 
	private static String nameuser = "";
	private static String dataUser = "";
	private static int portClient = 0;
	private Client clientNode;
	private JButton btnExit;
	private JScrollPane scrollPane; 
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomeFrame home = new HomeFrame();
					home.frmHome.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public HomeFrame(String ip, int port, String name, String msg) throws Exception {
		IPClient = ip;
		portClient = port;
		nameuser = name;
		dataUser = msg;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomeFrame home = new HomeFrame();
					home.frmHome.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public HomeFrame() throws Exception {
		initialize();
		clientNode = new Client(IPClient, portClient, nameuser, dataUser);
	}
	
	public static void update_List_Fiend(String msg) {
		choiceFriends.add(msg);
	}
	
	public static void clearAll() {
		choiceFriends.removeAll();
		//textArea.setText("");
	}
	
	private void initialize() {
		frmHome = new JFrame();
		frmHome.setTitle("Home Client - 3TigerS");
		frmHome.setResizable(false);
		frmHome.setBounds(100, 100, 275, 198);
		frmHome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frmHome.setContentPane(contentPane);
		
		textUsername = new JTextField();
		textUsername.setFont(new Font("Arial", Font.BOLD, 12));
		textUsername.setText(nameuser);
		textUsername.setEditable(false);
		textUsername.setColumns(10);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.setIcon(new ImageIcon(HomeFrame.class.getResource("/icon/connect.png")));
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnConnectActionPerformed(arg0);
			}
		});
		
		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnExitActionPerformed(arg0);
			}
		});
		
		scrollPane = new JScrollPane();
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(HomeFrame.class.getResource("/icon/elf.png")));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(textUsername, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(btnExit, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE))
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 238, GroupLayout.PREFERRED_SIZE))
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnConnect, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
							.addComponent(label)
							.addGap(21))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(textUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(29)
							.addComponent(btnConnect, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(18)
							.addComponent(label)))
					.addContainerGap(20, Short.MAX_VALUE))
		);
		
		choiceFriends = new Choice();
		scrollPane.setViewportView(choiceFriends);
		contentPane.setLayout(gl_contentPane);
	}
	
	private void btnConnectActionPerformed(java.awt.event.ActionEvent evt) { 
		String name = choiceFriends.getSelectedItem().toString();
		System.out.println("Name: " + name);
		if (name.equals("") || Client.client == null) {
			Tags.show(frmHome, "Name 's friend mistake!", false);
			return;
		}
		int size = Client.client.size();
		for (int i = 0; i < size; i++) {
			System.out.println(Client.client.get(i).getName());
			if (name.equals(Client.client.get(i).getName())) {
				try {
					clientNode.requestChat(Client.client.get(i).getHost(),Client.client.get(i).getPort(), name);
					return;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		Tags.show(frmHome, "Friend not found", false);
	}
	
	private void btnExitActionPerformed(java.awt.event.ActionEvent evt) { 
		int check = Tags.show(frmHome, "Do you want exit now?", true);
		if (check == 0) {
			try {
				clientNode.exit();
				frmHome.dispose();
			} catch (Exception e) {
				frmHome.dispose();
			}
		}
	}
	
	public static int request(String msg, boolean type) {
		JFrame frameMessage = new JFrame();
		return Tags.show(frameMessage, msg, type);
	}
}

