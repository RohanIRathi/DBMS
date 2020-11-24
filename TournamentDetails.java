import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.sql.*;

public class TournamentDetails extends JFrame implements ActionListener {

	int userid, tourid;
	JLabel tname, name, tplayers, players, tovers, overs, tteams, teams, tvenue, venue, tuser, user;
	JButton back;
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getActionCommand().equals("Back"))
		{
			new ViewTournaments(this.userid);
			dispose();
			return;
		}
	}
	
	TournamentDetails(int id, int user_id) {
		
		this.setTitle("");
		this.setLayout(null);
		
		String username = "root";
		String password = "root";
		Connection conn = null;
		Statement stmt = null;
		String dbURL = "jdbc:mysql://127.0.0.1:3306/crickstarz2";
		try
		{
			conn = DriverManager.getConnection(dbURL, username, password);
			if(conn != null)
				System.out.println("Connected to database");
			stmt = conn.createStatement();
			try
			{
				String query = "SELECT * FROM tournaments WHERE id='" + id + "';";
				ResultSet rs = stmt.executeQuery(query);
				rs.next();
				
				tname = new JLabel("Name: ");
				tname.setBounds(50, 50, 100, 30);
				tname.setOpaque(true);
				tname.setBackground(Color.LIGHT_GRAY);
				this.add(tname);
				
				name = new JLabel(rs.getString(2));
				name.setBounds(150, 50, 200, 30);
				name.setOpaque(true);
				name.setBackground(Color.LIGHT_GRAY);
				this.add(name);
				
				tteams = new JLabel("No of teams: ");
				tteams.setBounds(50, 100, 100, 30);
				tteams.setOpaque(true);
				tteams.setBackground(Color.WHITE);
				this.add(tteams);
				
				teams = new JLabel(rs.getString(4));
				teams.setBounds(150, 100, 200, 30);
				teams.setOpaque(true);
				teams.setBackground(Color.WHITE);
				this.add(teams);
				
				tovers = new JLabel("No of overs: ");
				tovers.setBounds(50, 150, 100, 30);
				tovers.setOpaque(true);
				tovers.setBackground(Color.LIGHT_GRAY);
				this.add(tovers);
				
				overs = new JLabel(rs.getString(5));
				overs.setBounds(150, 150, 200, 30);
				overs.setOpaque(true);
				overs.setBackground(Color.LIGHT_GRAY);
				this.add(overs);
				
				tplayers = new JLabel("No of players: ");
				tplayers.setBounds(50, 200, 100, 30);
				tplayers.setOpaque(true);
				tplayers.setBackground(Color.WHITE);
				this.add(tplayers);
				
				players = new JLabel(rs.getString(3));
				players.setBounds(150, 200, 200, 30);
				players.setOpaque(true);
				players.setBackground(Color.WHITE);
				this.add(players);
				
				tvenue = new JLabel("Venue: ");
				tvenue.setBounds(50, 250, 100, 30);
				tvenue.setOpaque(true);
				tvenue.setBackground(Color.LIGHT_GRAY);
				this.add(tvenue);
				
				venue = new JLabel(rs.getString(6));
				venue.setBounds(150, 250, 200, 30);
				venue.setOpaque(true);
				venue.setBackground(Color.LIGHT_GRAY);
				this.add(venue);
				
				rs.close();
				
				ResultSet userId = stmt.executeQuery("SELECT name FROM user_table WHERE ID='" + user_id +"';");
				userId.next();
				
				tuser = new JLabel("Created By: ");
				tuser.setBounds(50, 300, 100, 30);
				tuser.setOpaque(true);
				tuser.setBackground(Color.WHITE);
				this.add(tuser);
				
				user = new JLabel(userId.getString(1));
				user.setBounds(150, 300, 200, 30);
				user.setOpaque(true);
				user.setBackground(Color.WHITE);
				this.add(user);
				
				back = new JButton("Back");
				back.setBounds(150, 350, 100, 30);
				this.add(back);
				back.addActionListener(this);
				
				this.setVisible(true);
				this.setSize(1280, 720);
				
			}
			catch(SQLException exc)
			{
				System.out.println("Internal: " + exc.getErrorCode() + exc.getMessage());
			}
		}
		catch(SQLException exc)
		{
			System.out.println("External: " + exc.getErrorCode() + exc.getLocalizedMessage());
		}
	}

}
