import java.awt.Color;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class CreateTournament extends JFrame implements ActionListener {
	
	JTextField name, players, overs, teams, venue;
	JLabel tname, tplayers, tovers, tteams, tvenue, error;
	JButton create, retry, back;
	int userid;
	
	CreateTournament(int id)
	{
		this.userid = id;
		this.setTitle("Create Tournament");
		this.setLayout(null);
		
		tname = new JLabel("Name: ");
		tname.setBounds(50, 50, 100, 30);
		this.add(tname);
		
		name = new JTextField();
		name.setName("Name");
		name.setBounds(150, 50, 100, 30);
		this.add(name);
		
		tplayers = new JLabel("No of Players: ");
		tplayers.setBounds(50, 100, 100, 30);
		this.add(tplayers);
		
		players = new JTextField();
		players.setName("Players");
		players.setBounds(150, 100, 100, 30);
		this.add(players);
		
		tovers = new JLabel("No of overs: ");
		tovers.setBounds(50, 150, 100, 30);
		this.add(tovers);
		
		overs = new JTextField();
		overs.setName("Overs");
		overs.setBounds(150, 150, 100, 30);
		this.add(overs);
		
		tteams = new JLabel("No of teams: ");
		tteams.setBounds(50, 200, 100, 30);
		this.add(tteams);
		
		teams = new JTextField();
		teams.setName("Teams");
		teams.setBounds(150, 200, 100, 30);
		this.add(teams);
		
		tvenue = new JLabel("Venue: ");
		tvenue.setBounds(50, 250, 100, 30);
		this.add(tvenue);
		
		venue = new JTextField();
		venue.setName("Venue");
		venue.setBounds(150, 250, 100, 30);
		this.add(venue);
		
		create = new JButton("Create");
		create.setBounds(125, 300, 100, 30);
		this.add(create);
		create.addActionListener(this);
		
		back = new JButton("Back");
		back.setBounds(125, 200, 100, 30);
		this.add(back);
		back.addActionListener(this);
		
		error = new JLabel("");
		this.add(error);
		
		this.setSize(1280, 720);
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand().equals("Back"))
		{
			new Frame(true, this.userid);
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
				String query = "INSERT INTO `crickstarz2`.`tournaments` (`name`, `no_of_players`, `no_of_teams`, `no_of_overs`, `venue`, `user_id`)" + 
				"VALUES ('" + name.getText() + "', '" + players.getText() + "', '" + teams.getText() + "', '" + overs.getText() + "', '" +
				venue.getText() + "', '" + this.userid + "');";
				
				stmt.execute(query);
				
				error.setOpaque(true);
				error.setBackground(Color.GREEN);
				error.setBounds(10, 10, 200, 30);
				error.setText("Tournament Created Successfully!");
				
				try
				{
					Thread.sleep(2000);
				}
				catch(InterruptedException exc)
				{
					
				}
				finally
				{
					new Frame(true, this.userid);
					dispose();
				}
			}
			catch(SQLSyntaxErrorException exc)
			{
				if(exc.getErrorCode() == 1146)
				{
					String creation = "CREATE TABLE `crickstarz2`.`tournaments` (\r\n" + 
							"  `id` INT NOT NULL AUTO_INCREMENT,\r\n" + 
							"  `name` VARCHAR(128) NOT NULL,\r\n" + 
							"  `no_of_players` INT NOT NULL,\r\n" + 
							"  `no_of_teams` INT NOT NULL,\r\n" + 
							"  `no_of_overs` INT NOT NULL,\r\n" + 
							"  `venue` VARCHAR(45) NOT NULL,\r\n" + 
							"  `user_id` INT NULL,\r\n" + 
							"  PRIMARY KEY (`id`),\r\n" + 
							"  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,\r\n" + 
							"  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,\r\n" + 
							"  CONSTRAINT `user_id`\r\n" + 
							"    FOREIGN KEY (`user_id`)\r\n" + 
							"    REFERENCES `crickstarz2`.`user_table` (`ID`)\r\n" + 
							"    ON DELETE CASCADE\r\n" + 
							"    ON UPDATE CASCADE);";
					stmt.executeUpdate(creation);
					
					System.out.println("Table Created!");
				}
				System.out.println(exc.getErrorCode() + " : " + exc.getLocalizedMessage());
			}
			catch(SQLIntegrityConstraintViolationException exc)
			{
				if(exc.getErrorCode() == 1062)
				{
					error.setOpaque(true);
					error.setBackground(Color.RED);
					error.setBounds(10, 10, 200, 30);
					error.setForeground(Color.WHITE);
					error.setText("Tournament already exists! Try again");
				}
				System.out.println(exc.getErrorCode() + " : " + exc.getLocalizedMessage());
			}
			catch(SQLException exc)
			{
				if(exc.getErrorCode() == 1366)
				{
					error.setOpaque(true);
					error.setBackground(Color.RED);
					error.setBounds(10, 10, 200, 30);
					error.setText("Please Check The Data you have entered!");
				}
			}
		}
		catch(SQLException ex)
		{
			System.out.println(ex.getErrorCode() + " : " + ex.getLocalizedMessage());
		}
	}

}
