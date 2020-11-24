import java.awt.Color;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

public class UpdateAccount extends JFrame implements ActionListener {
	
	JLabel password, newpassword, confirmpassword, error;
	JTextField pass, newpass, confpass;
	JButton update, back;
	int userid;

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getActionCommand().equals("Back"))
		{
			new Account(this.userid);
			dispose();
			return;
		}
		String username = "root";
		String password = "root";
		Connection conn = null;
		Statement stmt = null;
		String dbURL = "jdbc:mysql://127.0.0.1:3306/crickstarz2";
		try
		{
			conn = DriverManager.getConnection(dbURL, username, password);
			if(conn != null)
			stmt = conn.createStatement();
			try
			{
				String query = "SELECT * FROM user_table WHERE ID='" + this.userid + "'";
				
				ResultSet rs = stmt.executeQuery(query);
				rs.next();
				int id = rs.getInt(3);
				String passworddb = rs.getString(4);
				if(pass.getText().equals(passworddb))
				{
					if(newpass.getText().equals(confpass.getText()))
					{
						String updateQuery = "UPDATE `crickstarz2`.`user_table` SET `Password` = '" + newpass.getText() +"' WHERE (`ID` = '" + this.userid + "');";
						stmt.executeUpdate(updateQuery);
						
						new Account(this.userid);
						dispose();
					}
					else
					{
						SQLException exc = new SQLException("Passwords Do Not Match!", "", 403);
						throw exc;
					}
				}
				else
				{
					SQLException exc = new SQLException("Incorrect Password!", "", 403);
					throw exc;
				}
			}
			catch(SQLException ex)
			{
				if(ex.getErrorCode() == 0)
				{
					error.setOpaque(true);
					error.setBackground(Color.RED);
					error.setBounds(10, 10, 200, 30);
					error.setText("No such user!");
				}
				else if(ex.getErrorCode() == 403)
				{
					error.setOpaque(true);
					error.setBackground(Color.RED);
					error.setBounds(10, 10, 200, 30);
					error.setText(ex.getLocalizedMessage());
				}
				System.out.println("Select exception: " + ex.getErrorCode() + " : " + ex.getLocalizedMessage());
			}
		}
		catch(SQLException ex)
		{
			
		}
	}
	
	UpdateAccount(int id)
	{
		this.userid = id;
		this.setTitle("Update Password");
		this.setLayout(null);
		
		password = new JLabel("Enter Password: ");
		password.setBounds(50, 50, 100, 30);
		this.add(password);
		
		newpassword = new JLabel("Enter New Password: ");
		newpassword.setBounds(50, 100, 100, 30);
		this.add(newpassword);
		
		confirmpassword = new JLabel("Confirm Password: ");
		confirmpassword.setBounds(50, 150, 100, 30);
		this.add(confirmpassword);
		
		pass = new JTextField();
		pass.setName("pass");
		pass.setBounds(150, 50, 150, 30);
		this.add(pass);
		
		newpass = new JTextField();
		newpass.setName("newpass");
		newpass.setBounds(150, 100, 150, 30);
		this.add(newpass);
		
		confpass = new JTextField();
		confpass.setName("newpass");
		confpass.setBounds(150, 150, 150, 30);
		this.add(confpass);
		
		update = new JButton("Update");
		update.setBounds(100, 200, 100, 30);
		this.add(update);
		update.addActionListener(this);
		
		back = new JButton("Back");
		back.setBounds(225, 200, 100, 30);
		this.add(back);
		back.addActionListener(this);
		
		error = new JLabel("");
		error.setBounds(10, 10, 200, 30);
		this.add(error);
		
		this.setVisible(true);
		this.setSize(1280, 720);
	}

}
