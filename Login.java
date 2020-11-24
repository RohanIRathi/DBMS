import java.awt.Color;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class Login extends JFrame implements ActionListener {
	
	JLabel username, password, error;
	JTextField user, pass;
	JButton login, back;

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand().equals("Back"));
		if(e.getActionCommand().equals("Back"))
		{
			new Frame();
			dispose();
			return;
		}
		// TODO Auto-generated method stub
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
				String query = "SELECT * FROM user_table WHERE name='" + user.getText() + "'";
				
				ResultSet rs = stmt.executeQuery(query);
				rs.next();
				int id = rs.getInt(3);
				String passworddb = rs.getString(4);
				if(pass.getText().equals(passworddb))
				{
					new Frame(true, id);
					dispose();
				}
				else
				{
					SQLException exc = new SQLException("Incorrect Password!", "", 403);
					throw exc;
				}
				error.setOpaque(true);
				error.setBackground(Color.GREEN);
				error.setBounds(10, 10, 200, 30);
				error.setText("No errors: "+ id);
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
	
	Login()
	{
		this.setTitle("Login");
		
		this.setLayout(null);
		
		username = new JLabel("Username: ");
		username.setBounds(50, 50, 100, 30);
		this.add(username);
		
		password = new JLabel("Password: ");
		password.setBounds(50, 100, 100, 30);
		this.add(password);
		
		user = new JTextField();
		user.setName("Username");
		user.setBounds(150, 50, 100, 30);
		this.add(user);
		
		pass = new JTextField();
		pass.setName("Password");
		pass.setBounds(150, 100, 100, 30);
		this.add(pass);
		
		login = new JButton("Login");
		login.setBounds(125, 150, 100, 30);
		this.add(login);
		login.addActionListener(this);
		
		back = new JButton("Back");
		back.setBounds(125, 200, 100, 30);
		this.add(back);
		back.addActionListener(this);
		
		error = new JLabel("");
		this.add(error);
		
		this.setVisible(true);
		this.setSize(1280, 720);
		
	}

}
