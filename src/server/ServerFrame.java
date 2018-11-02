package server;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;

public class ServerFrame  {

	public static int port = 7777;
	
	private static JLabel lblNumber;
	Server server;
	private JFrame frmServertigers;
	private JPanel contentPane;
	private JTextField textField;//port
	private JTextField textField_1;//IP address
	private JTextField textField_2;// database
	public static JTextArea textArea;
	// file data
	public String filePath = "C:/Users/Thanh Tung/Documents/Assignment_Java/Assignment_3TigerS/Data.xml";
    public JFileChooser fileChooser;
    //button
    private JButton btnStartServer;
    private JButton btnStop;
    
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerFrame home = new ServerFrame();
					home.frmServertigers.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void updateMessage(String msg) {
		textArea.append(msg + "\n");
	}

	public static void updateNumberClient() {
		int number = Integer.parseInt(lblNumber.getText());
		lblNumber.setText(Integer.toString(number + 1));
	}
	
	/**
	 * Create the frame.
	 */
	public ServerFrame() {
		initialize();
		btnStartServer.setEnabled(false);
        btnStop.setEnabled(false);
		fileChooser = new JFileChooser();
		textArea.setEditable(false);
	}
	
	public boolean isWin32(){
        return System.getProperty("os.name").startsWith("Windows");
    }
	
	private void initialize() {
		frmServertigers = new JFrame();
		frmServertigers.setTitle("Server_3TigerS");
		frmServertigers.setResizable(false);
		frmServertigers.setBounds(100, 100, 541, 425);
		frmServertigers.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frmServertigers.setContentPane(contentPane);
		
		textField = new JTextField();
		textField.setText("7777");
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);						// show ip currently
		try {
			textField_1.setText(Inet4Address.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		
		JLabel lblPort = new JLabel("Port:");
		
		JLabel lblIpAddress = new JLabel("IP Address:");
		
		JLabel lblDatabase = new JLabel("Database:");
		
		btnStartServer = new JButton("Start Server");
		btnStartServer.setIcon(new ImageIcon(ServerFrame.class.getResource("/icon/power.png")));
		btnStartServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnStartServerActionPerformed(arg0);
			}
		});
		
		btnStop = new JButton("Stop");
		btnStop.setIcon(new ImageIcon(ServerFrame.class.getResource("/icon/stop.png")));
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnStopActionPerformed(e);
			}
		});
		
		JLabel lblStatusServer = new JLabel("Status Server:");
		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton btnOpenfile = new JButton("Open file");
		btnOpenfile.setIcon(new ImageIcon(ServerFrame.class.getResource("/icon/folder.png")));
		btnOpenfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnOpenfileActionPerformed(arg0);
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblPort, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
										.addGap(35))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblIpAddress, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)))
								.addComponent(lblDatabase))
							.addGap(36)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(textField_1)
										.addComponent(textField, GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE))
									.addGap(29)
									.addComponent(btnStartServer)
									.addGap(18)
									.addComponent(btnStop, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(btnOpenfile))))
						.addComponent(lblStatusServer, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 494, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(15, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnStop, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblPort))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblIpAddress)
								.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addComponent(btnStartServer, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDatabase)
						.addComponent(btnOpenfile))
					.addGap(24)
					.addComponent(lblStatusServer)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 206, GroupLayout.PREFERRED_SIZE)
					.addGap(19))
		);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		contentPane.setLayout(gl_contentPane);
		frmServertigers.pack();
		frmServertigers.setLocationRelativeTo(null);
	}
	
	private void btnStartServerActionPerformed(java.awt.event.ActionEvent evt) {                                         
		try {
			textArea.setText("");
			port = Integer.parseInt(textField.getText());
			server = new Server(filePath,port);
			btnStop.setEnabled(true);
			btnStartServer.setEnabled(false);
		} catch (Exception e) {
			ServerFrame.updateMessage("Start ERROR");
			e.printStackTrace();
		}
    }  
	
	private void btnStopActionPerformed(java.awt.event.ActionEvent evt) {
		btnStartServer.setEnabled(true);
			try {
				server.stopServer();
				ServerFrame.updateMessage("Stop Server");
			} catch (Exception e) {
				ServerFrame.updateMessage("Stop Server");
				e.printStackTrace();
			}	
	}
	
	private void btnOpenfileActionPerformed(java.awt.event.ActionEvent evt) {
		fileChooser.showOpenDialog(frmServertigers);
        File file = fileChooser.getSelectedFile();
        
        if(file != null){
            filePath = file.getPath();
            if(this.isWin32()){ filePath = filePath.replace("\\", "/"); }
            textField_2.setText(filePath);
            btnStartServer.setEnabled(true);
            //btnStop.setEnabled(true);
        }
	}
	
}
