import java.awt.Color;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class ViewTournaments extends JFrame implements ActionListener {

	int userid;
	JList<String> tournaments;
	JLabel ttext;
	JButton back; 
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getActionCommand().equals("Back"))
		{
			new Frame(true, this.userid);
			dispose();
			return;
		}
	}
	
	ViewTournaments(int id)
	{
		this.userid = id;
		System.out.println(this.userid);
		this.setLayout(null);
		this.setTitle("Tournaments");
		
		ttext = new JLabel("Your Tournaments");
		ttext.setBounds(500, 20, 300, 50);
		ttext.setSize(300, 50);
		this.add(ttext);
		
		back = new JButton("Back");
		back.setBounds(1000, 50, 100, 30);
		this.add(back);
		back.addActionListener(this);
		
		this.setSize(1280, 720);
		this.setVisible(true);
		
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
				String query = "SELECT * FROM tournaments WHERE user_id='" + this.userid + "';";
				
				ResultSet total = stmt.executeQuery("SELECT COUNT(*) FROM tournaments WHERE user_id='" + this.userid + "';");
				total.next();
				int count = total.getInt(1);
				total.close();
				
				ResultSet rs = stmt.executeQuery(query);
				
				JLabel list[] = new JLabel[count];
				
				for(int i = 0; i < count && rs.next(); i++)
				{
					list[i] = new JLabel();
					list[i].setText(rs.getString(2));
					list[i].setOpaque(true);
					if(i % 2 == 0)
						list[i].setBackground(Color.LIGHT_GRAY);
					else
						list[i].setBackground(Color.WHITE);
					list[i].setBounds(50, 100 + (i * 50), 500, 50);
					this.add(list[i]);
					list[i].addMouseListener(new MouseAdapter() {
						 
			            @Override
			            public void mouseClicked(MouseEvent e) {
		                    try{new TournamentDetails(rs.getInt(1), rs.getInt(7));}
		                    catch(SQLException exc) {}
		                    dispose();
			            }
			 
			        });
					
				}
			}
			catch(SQLSyntaxErrorException exc)
			{
				System.out.println(exc.getLocalizedMessage());
			}
		}
		catch(SQLException exc)
		{
			System.out.println(exc.getLocalizedMessage());
		}
		
	}

}
