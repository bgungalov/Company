import java.awt.EventQueue;
import java.awt.Image;
import java.sql.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Login {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	Connection connection = null;
	private JTextField textFieldUN;
	private JPasswordField passwordField;

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
		connection = sqliteConnection.dbConnector();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(211, 211, 211));
		frame.setBounds(100, 100, 500, 330);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("UserName");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(215, 83, 75, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(215, 139, 75, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		textFieldUN = new JTextField();
		textFieldUN.setBounds(205, 109, 100, 20);
		frame.getContentPane().add(textFieldUN);
		textFieldUN.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try 
				{
					String query = "select * from EmployeeInfo where username =? and password =?";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, textFieldUN.getText());
					pst.setString(2, passwordField.getText());
					
					ResultSet rs = pst.executeQuery();
					int count = 0;
					while(rs.next()) 
					{
						count++;
					}
					if (count == 1) 
					{
						JOptionPane.showMessageDialog(null, "Login Successful");
						frame.dispose();
						EmployeeInfo emplInfo = new EmployeeInfo();
						emplInfo.setVisible(true);
					}
					else if(count > 1)
					{
						JOptionPane.showMessageDialog(null, "Duplicate Username and Password");
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Username or Password is not correct. Please try again!");
					}
					
					rs.close();
					pst.close();
					
				} catch (Exception e1)
				{
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		btnLogin.setBounds(205, 233, 100, 30);
		frame.getContentPane().add(btnLogin);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(205, 165, 100, 20);
		frame.getContentPane().add(passwordField);
		
		JLabel iconLabel = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("login.png")).getImage();
		iconLabel.setIcon(new ImageIcon(img));
		iconLabel.setBounds(33, 83, 160, 180);
		frame.getContentPane().add(iconLabel);
	}
}
