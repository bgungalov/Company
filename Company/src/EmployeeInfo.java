import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.*;
import javax.swing.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EmployeeInfo extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeInfo frame = new EmployeeInfo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	Connection connection = null;
	private JTextField textFieldEID;
	private JTextField textFieldName;
	private JTextField textFieldSurname;
	private JTextField textFieldAge;

	/**
	 * Create the frame.
	 */
	public EmployeeInfo() {
		connection = sqliteConnection.dbConnector();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnLoadTable = new JButton("Load Employee Data");
		btnLoadTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "select EID, Name, Surname, Age from EmployeeInfo";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnLoadTable.setBounds(10, 11, 168, 30);
		contentPane.add(btnLoadTable);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(188, 11, 386, 339);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JLabel lblEID = new JLabel("EID");
		lblEID.setHorizontalAlignment(SwingConstants.LEFT);
		lblEID.setBounds(10, 55, 42, 14);
		contentPane.add(lblEID);
		
		JLabel lblName = new JLabel("Name");
		lblName.setHorizontalAlignment(SwingConstants.LEFT);
		lblName.setBounds(10, 80, 42, 14);
		contentPane.add(lblName);
		
		JLabel lblSurname = new JLabel("Surname");
		lblSurname.setHorizontalAlignment(SwingConstants.LEFT);
		lblSurname.setBounds(10, 105, 52, 14);
		contentPane.add(lblSurname);
		
		JLabel lblAge = new JLabel("Age");
		lblAge.setHorizontalAlignment(SwingConstants.LEFT);
		lblAge.setBounds(10, 130, 42, 14);
		contentPane.add(lblAge);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "insert into EmployeeInfo (EID, Name, Surname, Age) values(?,?,?,?)";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, textFieldEID.getText());
					pst.setString(2, textFieldName.getText());
					pst.setString(3, textFieldSurname.getText());
					pst.setString(4, textFieldAge.getText());
					
					pst.execute();
					
					JOptionPane.showMessageDialog(null, "Data Saved!");
					
					pst.close();
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnSave.setBounds(10, 155, 80, 23);
		contentPane.add(btnSave);
		
		textFieldEID = new JTextField();
		textFieldEID.setBounds(66, 52, 112, 20);
		contentPane.add(textFieldEID);
		textFieldEID.setColumns(10);
		
		textFieldName = new JTextField();
		textFieldName.setBounds(66, 77, 112, 20);
		contentPane.add(textFieldName);
		textFieldName.setColumns(10);
		
		textFieldSurname = new JTextField();
		textFieldSurname.setBounds(66, 102, 112, 20);
		contentPane.add(textFieldSurname);
		textFieldSurname.setColumns(10);
		
		textFieldAge = new JTextField();
		textFieldAge.setBounds(66, 127, 112, 20);
		contentPane.add(textFieldAge);
		textFieldAge.setColumns(10);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "Update EmployeeInfo set "
							+ "EID = '"+textFieldEID.getText()+"',"
							+ "name = '"+textFieldName.getText()+"', "
							+ "surname = '"+textFieldSurname.getText()+"', "
							+ "age = '"+textFieldAge.getText()+"'"
							+ "where EID = '"+ textFieldEID.getText()+"' ";
					
					PreparedStatement pst = connection.prepareStatement(query);
					
					pst.execute();
					
					JOptionPane.showMessageDialog(null, "Data Updated!");
					
					pst.close();
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnUpdate.setBounds(98, 155, 80, 23);
		contentPane.add(btnUpdate);
	}
}
