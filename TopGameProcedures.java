package MySQLDemo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.CallableStatement;
import java.sql.Types;

public class TopGameProcedures {

	static final String databasePrefix ="cs366-2211_olhausenmm13";
    static final String netID ="olhausenmm13"; // Please enter your netId
    static final String hostName ="washington.uww.edu"; //140.146.23.39 or washington.uww.edu
    static final String databaseURL ="jdbc:mariadb://"+hostName+"/"+databasePrefix;
    static final String password="mo0643"; // please enter your own password
     		    
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

	public void simpleStoreProcedure(String spName) {

	try {
		statement = connection.createStatement();
		String listGame;
		CallableStatement myCallStmt = connection.prepareCall("{call "+spName+"(?)}");
		myCallStmt.setString(1,"");
        myCallStmt.registerOutParameter(1,Types.VARCHAR);
        myCallStmt.execute();
        listGame = myCallStmt.getString(1);
        System.out.println("The top games are" + listGame);
	}
	catch (SQLException e) {
		e.printStackTrace();
	}
} // end of simpleQuery method

/*
 
delimiter $$
 drop procedure if exists getTopSalesGenre; 
 create procedure getTopSalesGenre(IN genreChoice VARCHAR(50))
 begin SELECT g.name, g.platform FROM game g, publisher p, developer d, sales s, 
 WHERE g.game_ID in
 ( SELECT game_ID FROM game g WHERE game_ID in
 ( SELECT g.game_ID, (s.na_sales + s.eu_sales + s.jp_sales + s.other_sales ) 
 AS total_sales FROM game g, sales s WHERE s.game_ID=g.game_ID 
 ORDER BY total_sales desc LIMIT 10)); 
 end $$ delimiter;
 
 delimiter $$
drop procedure if exists getTopUserGenre;
create procedure getTopUserGenre(IN genreChoice VARCHAR(50))
begin
SELECT g.name, p.name, d.name, g.year, g.genre, g.platform, g.critic_score, g.user_score, g.age_rating
FROM game AS g
INNER JOIN publisher AS p
ON g.pub_ID = p.pub_ID
INNER JOIN developer AS d
ON g.dev_ID = d.dev_ID
WHERE g.user_count > 10 AND genre = genreChoice
ORDER BY g.user_score desc
LIMIT 10;
end $$
delimiter;

delimiter $$
drop procedure if exists getTopCriticGenre;
create procedure getTopCriticGenre(IN genreChoice VARCHAR(50))
begin
SELECT g.name, p.name, d.name, g.year, g.genre, g.platform, g.critic_score, g.user_score, g.age_rating FROM game AS g
INNER JOIN publisher AS p
ON g.pub_ID = p.pub_ID
INNER JOIN developer AS d
ON g.dev_ID = d.dev_ID
WHERE g.critic_count > 10 AND genre = genreChoice ORDER BY g.critic_score desc
LIMIT 10; end $$
delimiter;

*/
	
	
public static void main(String args[]) {

	TopGameProcedures gameObj = new TopGameProcedures();
	gameObj.Connection(); 
	String spName ="getTopSalesGenre";
	gameObj.simpleStoreProcedure(spName);
	spName = "getTopCriticGenre";
	gameObj.simpleStoreProcedure(spName);
	spName = "getTopUserGenre";
	gameObj.simpleStoreProcedure(spName);
	
}



}
