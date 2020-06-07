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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EmployeeInfo extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JComboBox comboBoxName;
	private JComboBox comboBoxSelect;

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
	private JTextField textFieldSearch;
	
	// --> Refresh table after manipulating it
	public void refreshTable() {
		try {
			String query = "select EID, Name, Surname, Age from EmployeeInfo";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			pst.close();
			rs.close();
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public void fillComboBox() {
		try {
			String query = "select * from EmployeeInfo";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) 
			{
				comboBoxName.addItem(rs.getString("Name"));
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

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
					
					pst.execute();
					pst.close();
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnLoadTable.setBounds(10, 320, 168, 30);
		contentPane.add(btnLoadTable);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(188, 10, 386, 340);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try	{
					int row = table.getSelectedRow();
					String EID_ = (table.getModel().getValueAt(row, 0)).toString();
					
					String query = "select * from EmployeeInfo where EID='"+EID_+"'";
					PreparedStatement pst = connection.prepareStatement(query);
				
					ResultSet rs = pst.executeQuery();
					
					while(rs.next()) 
					{
						textFieldEID.setText(rs.getString("EID"));
						textFieldName.setText(rs.getString("Name"));
						textFieldSurname.setText(rs.getString("Surname"));
						textFieldAge.setText(rs.getString("Age"));
					}
					
					pst.close();
										
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		scrollPane.setViewportView(table);
		
		JLabel lblEID = new JLabel("EID");
		lblEID.setHorizontalAlignment(SwingConstants.LEFT);
		lblEID.setBounds(10, 115, 42, 14);
		contentPane.add(lblEID);
		
		JLabel lblName = new JLabel("Name");
		lblName.setHorizontalAlignment(SwingConstants.LEFT);
		lblName.setBounds(10, 140, 42, 14);
		contentPane.add(lblName);
		
		JLabel lblSurname = new JLabel("Surname");
		lblSurname.setHorizontalAlignment(SwingConstants.LEFT);
		lblSurname.setBounds(10, 165, 52, 14);
		contentPane.add(lblSurname);
		
		JLabel lblAge = new JLabel("Age");
		lblAge.setHorizontalAlignment(SwingConstants.LEFT);
		lblAge.setBounds(10, 190, 42, 14);
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
				
				refreshTable();
			}
		});
		btnSave.setBounds(10, 218, 168, 23);
		contentPane.add(btnSave);
		
		textFieldEID = new JTextField();
		textFieldEID.setBounds(66, 112, 112, 20);
		contentPane.add(textFieldEID);
		textFieldEID.setColumns(10);
		
		textFieldName = new JTextField();
		textFieldName.setBounds(66, 137, 112, 20);
		contentPane.add(textFieldName);
		textFieldName.setColumns(10);
		
		textFieldSurname = new JTextField();
		textFieldSurname.setBounds(66, 162, 112, 20);
		contentPane.add(textFieldSurname);
		textFieldSurname.setColumns(10);
		
		textFieldAge = new JTextField();
		textFieldAge.setBounds(66, 187, 112, 20);
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
				
				refreshTable();
			}
		});
		btnUpdate.setBounds(10, 252, 168, 23);
		contentPane.add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int action = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this?",
						"Delete",JOptionPane.YES_NO_OPTION);
				if (action == 0) {
				
				try {
					String query = "delete from EmployeeInfo where EID = '"+ textFieldEID.getText()+"' ";
					PreparedStatement pst = connection.prepareStatement(query);
					
					pst.execute();
					
					JOptionPane.showMessageDialog(null, "Data Deleted!");
					
					pst.close();
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				refreshTable();
				}
			}
		});
		btnDelete.setBounds(10, 286, 168, 23);
		contentPane.add(btnDelete);
		
		comboBoxName = new JComboBox();
		comboBoxName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try	{
					String query = "select * from EmployeeInfo where name=?";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, (String) comboBoxName.getSelectedItem());
					ResultSet rs = pst.executeQuery();
					
					while(rs.next()) 
					{
						textFieldEID.setText(rs.getString("EID"));
						textFieldName.setText(rs.getString("Name"));
						textFieldSurname.setText(rs.getString("Surname"));
						textFieldAge.setText(rs.getString("Age"));
						
					}
					
					pst.close();
										
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		comboBoxName.setToolTipText("View Data");
		comboBoxName.setBounds(10, 82, 168, 22);
		contentPane.add(comboBoxName);
		
		textFieldSearch = new JTextField();
		textFieldSearch.setToolTipText("Type here");
		textFieldSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try	{
					String selection = (String)comboBoxSelect.getSelectedItem();
					String query = "select EID, Name, Surname, Age from EmployeeInfo where "+selection+"=?";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, textFieldSearch.getText());
					ResultSet rs = pst.executeQuery();
					
					table.setModel(DbUtils.resultSetToTableModel(rs));
					/*while(rs.next()) 
					{
						
					}*/
					
					pst.close();
										
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		textFieldSearch.setBounds(10, 43, 168, 22);
		contentPane.add(textFieldSearch);
		textFieldSearch.setColumns(10);
		
		comboBoxSelect = new JComboBox();
		comboBoxSelect.setModel(new DefaultComboBoxModel(new String[] {"EID", "Name", "Surname", "Age"}));
		comboBoxSelect.setBounds(72, 10, 106, 22);
		contentPane.add(comboBoxSelect);
		
		JLabel lblNewLabel = new JLabel("Filter by:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 8, 59, 22);
		contentPane.add(lblNewLabel);
		
		refreshTable();
		fillComboBox();
	}
}
