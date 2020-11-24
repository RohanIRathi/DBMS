import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class DeleteAccount extends JFrame implements ActionListener {

	JLabel confirm;
	JButton delete, cancel;
	int userid;
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getActionCommand())
		{
		case "Cancel":
			new Account(this.userid);
			dispose();
			break;
		case "Yes, Delete":
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
					String query = "DELETE FROM `crickstarz2`.`user_table` WHERE (`ID` = '" + this.userid + "');";
					stmt.executeUpdate(query);
					
					new Frame();
					dispose();
				}
				catch(SQLSyntaxErrorException exc)
				{
					exc.printStackTrace();
				}
			}
			catch(SQLException exc)
			{
				exc.printStackTrace();
			}
			break;
		}
	}
	
	DeleteAccount(int id)
	{
		this.userid = id;
		this.setTitle("Delete Account");
		this.setLayout(null);
		
		confirm = new JLabel("Are You Sure You Want to DELETE your account?  Note: This action is irreversible");
		confirm.setBounds(50, 50, 750, 50);
		confirm.setVerticalAlignment(0);
		confirm.setOpaque(true);
		confirm.setForeground(Color.RED);
		this.add(confirm);
		
		delete = new JButton("Yes, Delete");
		delete.setBackground(Color.RED);
		delete.setForeground(Color.WHITE);
		delete.setBounds(75, 100, 100, 30);
		this.add(delete);
		delete.addActionListener(this);
		
		cancel = new JButton("Cancel");
		cancel.setBounds(200, 100, 100, 30);
		this.add(cancel);
		cancel.addActionListener(this);
		
		this.setVisible(true);
		this.setSize(1280, 720);
	}

}
