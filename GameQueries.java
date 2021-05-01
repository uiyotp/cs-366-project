package MySQLDemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class GameQueries {


	 	static final String databasePrefix ="cs366-2211_paprockisj04";
	    static final String netID ="paprockisj04";
	    static final String hostName ="washington.uww.edu";
	    static final String databaseURL ="jdbc:mariadb://"+hostName+"/"+databasePrefix;
	    static final String password="sp3619";

	    private Connection connection = null;
	    private Statement statement = null;
	    private ResultSet resultSet = null;
	    
	    public void Connection(){
	  
	      try {
	            Class.forName("org.mariadb.jdbc.Driver");
	              System.out.println("databaseURL"+ databaseURL);
	            connection = DriverManager.getConnection(databaseURL, netID, password);
	            System.out.println("Successfully connected to the database");
	         }
	        catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	        catch (SQLException e) {
	            e.printStackTrace();
	        }
	    } // end of Connection
	    
	    public void simpleQuery(String sqlQuery) {
	    
	    	try {
	    		statement = connection.createStatement();
	    		resultSet = statement.executeQuery(sqlQuery);

	    		ResultSetMetaData metaData = resultSet.getMetaData();
	    		int columns = metaData.getColumnCount();

	    		for (int i=1; i<= columns; i++) {
	    			System.out.print(metaData.getColumnName(i)+"\t");
	    		}

	    		System.out.println();

	    		while (resultSet.next()) {
	       
	    			for (int i=1; i<= columns; i++) {
	    				System.out.print(resultSet.getObject(i)+"\t\t");
	    			}
	    			System.out.println();
	    		}
	    	}
	    	catch (SQLException e) {
	    		e.printStackTrace();
	    	}
	    } // end of simpleQuery method
	    
	    public static void main(String args[]) {
	
	    	GameQueries gameObj = new GameQueries();
	    	gameObj.Connection();
	    	String sqlQuery ="select g.name, p.name, d.name, g.year, g.genre, g.platform, g.critic_score, g.user_score, g.age_rating FROM game AS g, publisher as p, developer as d WHERE g.name='Actua Tennis' and g.platform='PS'"; //search for a game
	    	gameObj.simpleQuery(sqlQuery);
			//sqlQuery="UPDATE game SET game_ID = Breath of the Wild WHERE critic_score = (critic_count * critic_score + (Java input new critic score))/++critic_count;";   //add critic review
	    	sqlQuery="UPDATE game SET critic_score = (critic_count * critic_score + (Java input new user score))/++critic_count, critic_count = critic_count+1, WHERE game_ID = (SELECT game_ID FROM game WHERE name = 'Breath of the Wild');"; 
			gameObj.simpleQuery(sqlQuery);
			//sqlQuery="UPDATE game SET game_ID = Breath of the Wild WHERE user_score = (user_count * user_score + (Java input new user score))/++user_count;";  //add user review
			sqlQuery="UPDATE game SET user_score = (user_count * user_score + (Java input new user score))/++user_count, user_count=user_count+1 WHERE game_ID = (SELECT game_ID FROM game WHERE name = 'Breath of the Wild');"; 
			gameObj.simpleQuery(sqlQuery);
			sqlQuery="INSERT INTO game(age_rating, year, platform, name, publisher_ID, developer_ID, genre, critic_score, critic_count, user_score, user_count) VALUES ('E10+', 2017, 'Switch', 'Breath of the Wild', (SELECT publisher_ID from publisher WHERE name ='Nintendo'), (SELECT developer_ID FROM developer WHERE name='Nintendo'), 'Adventure', 97, 109, 8.7, 16687) ;";  //add a new game
			//sqlQuery="INSERT INTO game SET age_rating = 'E10+', year = 2017, platform= 'Switch', name = 'Breath of the Wild', genre = 'Adventure', critic_score=97, critic_count=109, user_score=8.7, user_count= 16687, publisher_id= (SELECT publisher_ID from publisher2 WHERE name ='Nintendo'), developer_ID=(SELECT developer_ID FROM developer WHERE name='Nintendo');";
			gameObj.simpleQuery(sqlQuery);
	    }
	    
	
	
}

