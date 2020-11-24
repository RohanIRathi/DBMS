import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.sql.*;

public class Register extends JFrame implements ActionListener {

	JLabel username, email, password, error;
	JTextField user, mail, pass;
	JButton register, back;
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
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
			try {
				stmt.executeQuery("select * from user_table");
				System.out.println("Selected successfully");
				try {
					String usernamedb = user.getText();
					String emaildb = mail.getText();
					String passdb = pass.getText();

					String query = "INSERT INTO `crickstarz2`.`user_table` (`Name`, `Email`, `Password`, `Admin`) VALUES ('"+ usernamedb +"','"+ emaildb +"','"+ passdb +"',1);";
					stmt.executeUpdate(query);
					System.out.println("Inserted successfully");
					
					new Frame();
					dispose();
				}
				catch(SQLIntegrityConstraintViolationException exc) {
					if(exc.getErrorCode() == 1062) {
						error.setBounds(10, 10, 200, 20);
						error.setOpaque(true);
						error.setBackground(Color.RED);
						error.setText("User Already Exists!");
						new Register();
						dispose();
					}
				}
				
			}
			catch (SQLSyntaxErrorException exception)
			{
				if(exception.getErrorCode() == 1146) {
				String createT = "CREATE TABLE `crickstarz2`.`user_table` (\r\n" + 
						"  `Name` VARCHAR(45) NOT NULL,\r\n" + 
						"  `Email` VARCHAR(45) NOT NULL,\r\n" + 
						"  `ID` INT NOT NULL AUTO_INCREMENT,\r\n" + 
						"  `Password` VARCHAR(128) NOT NULL,\r\n" + 
						"  `Admin` TINYINT NULL,\r\n" + 
						"  PRIMARY KEY (`ID`),\r\n" + 
						"  UNIQUE INDEX `Email_UNIQUE` (`Email` ASC) VISIBLE,\r\n" + 
						"  UNIQUE INDEX `Name_UNIQUE` (`Name` ASC) VISIBLE);\r\n";
			
				stmt.executeUpdate(createT);
			    System.out.println("Created table in given database...");
				}
				System.out.print(exception.getErrorCode());
				
			}
			
		}
		catch(SQLException se)
		{
			System.out.println(se.getErrorCode() + " : " + se.getLocalizedMessage());
		}
	}
	
	Register()
	{
		this.setTitle("Register");
		
		this.setLayout(null);
		
		username = new JLabel("Username: ");
		username.setBounds(50, 50, 100, 30);
		this.add(username);
		
		email = new JLabel("Email: ");
		email.setBounds(50, 100, 100, 30);
		this.add(email);
		
		password = new JLabel("Password: ");
		password.setBounds(50, 150, 100, 30);
		this.add(password);
		
		user = new JTextField();
		user.setName("Username");
		user.setBounds(150, 50, 100, 30);
		this.add(user);
		
		mail = new JTextField();
		mail.setName("Email");
		mail.setBounds(150, 100, 100, 30);
		this.add(mail);
		
		pass = new JTextField();
		pass.setName("Password");
		pass.setBounds(150, 150, 100, 30);
		this.add(pass);
		
		register = new JButton("Register");
		register.setBounds(125, 200, 100, 30);
		this.add(register);
		register.addActionListener(this);
		
		back = new JButton("Back");
		back.setBounds(250, 200, 100, 30);
		this.add(back);
		back.addActionListener(this);
		
		error = new JLabel("");
		this.add(error);
		
		this.setVisible(true);
		this.setSize(1280, 720);
		
	}

}
