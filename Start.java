import java.awt.Color;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class Start
{
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Frame();
	}

}

class Account extends JFrame implements ActionListener
{
	JLabel username, user, email, mail, password, pass;
	JButton update, delete, back;
	int userid;
	
	Account(int id)
	{
		this.userid = id;
		this.setLayout(null);
		this.setTitle("Account");
		
		String usernamedb = "root";
		String passworddb = "root";
		Connection conn = null;
		Statement stmt = null;
		String dbURL = "jdbc:mysql://127.0.0.1:3306/crickstarz2";
		try
		{
			conn = DriverManager.getConnection(dbURL, usernamedb, passworddb);
			if(conn != null)
				System.out.println("Connected to database");
			stmt = conn.createStatement();
			try
			{
				String query = "SELECT * FROM user_table WHERE ID='" + id + "';";
				ResultSet rs = stmt.executeQuery(query);
				rs.next();
		
				username = new JLabel("Username: ");
				username.setOpaque(true);
				username.setBounds(50, 50, 100, 30);
				username.setBackground(Color.LIGHT_GRAY);
				this.add(username);
				
				user = new JLabel(rs.getString(1));
				user.setOpaque(true);
				user.setBounds(150, 50, 150, 30);
				user.setBackground(Color.LIGHT_GRAY);
				this.add(user);
				
				email = new JLabel("Email: ");
				email.setOpaque(true);
				email.setBounds(50, 100, 100, 30);
				email.setBackground(Color.WHITE);
				this.add(email);
				
				mail = new JLabel(rs.getString(2));
				mail.setOpaque(true);
				mail.setBounds(150, 100, 150, 30);
				mail.setBackground(Color.WHITE);
				this.add(mail);
				
				password = new JLabel("Password: ");
				password.setOpaque(true);
				password.setBounds(50, 150, 100, 30);
				password.setBackground(Color.LIGHT_GRAY);
				this.add(password);
				
				pass = new JLabel("********");
				pass.setOpaque(true);
				pass.setBounds(150, 150, 150, 30);
				pass.setBackground(Color.LIGHT_GRAY);
				this.add(pass);
				
				update = new JButton("Update");
				update.setBounds(75, 200, 100, 30);
				this.add(update);
				update.addActionListener(this);
				
				delete = new JButton("Delete");
				delete.setBounds(200, 200, 100, 30);
				delete.setBackground(Color.RED);
				delete.setForeground(Color.WHITE);
				this.add(delete);
				delete.addActionListener(this);
				
				back = new JButton("Back");
				back.setBounds(325, 200, 100, 30);
				this.add(back);
				back.addActionListener(this);
				
				this.setVisible(true);
				this.setSize(1280, 720);
			}
			catch(SQLException exc)
			{
				System.out.println(exc.getLocalizedMessage());
			}
		}
		catch(SQLException exc)
		{
			exc.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getActionCommand())
		{
		case "Update":
			new UpdateAccount(this.userid);
			dispose();
			break;
		case "Delete":
			new DeleteAccount(this.userid);
			dispose();
			break;
		case "Back":
			new Frame(true, this.userid);
			dispose();
			break;
		}
	}
}

class Frame extends JFrame implements ActionListener
{
	JButton register, login, createTournament, viewTournament, update;
	JLabel label;
	int userid;
	
	Frame()
	{
		this.setTitle("Welcome to Cricstarz");
		
		this.setLayout(null);
		
		register = new JButton("Register");
		register.setBounds(50, 50, 100, 30);
		this.add(register);
		register.addActionListener(this);
		
		login = new JButton("Login");
		login.setBounds(200, 50, 100, 30);
		this.add(login);
		login.addActionListener(this);
		
		this.setVisible(true);
		this.setSize(1280, 720);
	}
	
	Frame(boolean b, int id)
	{
		if(b)
		{
			this.setTitle("Welcome");
			this.setLayout(null);
			
			this.userid = id;
			createTournament = new JButton("Create Tournament");
			createTournament.setBounds(50, 50, 200, 30);
			this.add(createTournament);
			createTournament.addActionListener(this);
			
			viewTournament = new JButton("View Tournaments");
			viewTournament.setBounds(300, 50, 200, 30);
			this.add(viewTournament);
			viewTournament.addActionListener(this);
			
			update = new JButton("Account");
			update.setBounds(550, 50, 100, 30);
			this.add(update);
			update.addActionListener(this);
			
			this.setVisible(true);
			this.setSize(1280, 720);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getActionCommand().toString())
		{
			case "Register":
				new Register();
				dispose();
				break;
			case "Login":
				new Login();
				dispose();
				break;
			case "Create Tournament":
				new CreateTournament(this.userid);
				dispose();
				break;
			case "View Tournaments":
				new ViewTournaments(this.userid);
				dispose();
				break;
			case "Account":
				new Account(this.userid);
				dispose();
				break;
		}
	}

}