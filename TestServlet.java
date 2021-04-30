
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Types;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import MySQLDemo.TopGameProcedures;

public class TestServlet extends HttpServlet
{  
	//connection from gameSalesAPI.java
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
	}
	//  end of connection from gameSalesAPI.java
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		TestServlet gameObj=new TestServlet();
		gameObj.Connection(); //object for sql
		PrintWriter out=res.getWriter();//writes response
		String result="";//result will be posted to website/app
		
		if (req.getParameter("formName").compareToIgnoreCase("topGameFilter")==0){
			//if getting user, sales, or critic top games
			String s1 = req.getParameter("topMetric");
			String spName="";
			if (s1.compareToIgnoreCase("critic")==0) {
				spName = "getTopCriticGenre";
				result= gameObj.simpleStoreProcedure(spName);		
			}
			if (s1.compareToIgnoreCase("user")==0) {
				spName = "getTopUserGenre";
				result=gameObj.simpleStoreProcedure(spName);
			}
			if (s1.compareToIgnoreCase("sales")==0) {
				spName ="getTopSalesGenre";
				result=gameObj.simpleStoreProcedure(spName);
			}
		}//end top game procedure
		
		
		if(req.getParameter("formName").compareToIgnoreCase("searchGame") ==0 || req.getParameter("formName").compareToIgnoreCase("reviewGame")==0 || req.getParameter("formName").compareToIgnoreCase("editGame")==0) {
			//if search game, review game, or edit game
			
			String sqlQuery="";
			String gname=req.getParameter("gameName");
			String dname=req.getParameter("devName");
			String year=req.getParameter("year");
			String platform=req.getParameter("platform");
			//search for the game
			sqlQuery ="select g.name, p.name, d.name, g.year, g.genre, g.platform, g.critic_score, g.user_score, g.age_rating FROM game AS g, publisher as p, developer as d WHERE g.name="+gname+" and g.platform=" +platform +";"; 
			result=gameObj.simpleQuery(sqlQuery);
			//if query found a game
			if(result.compareToIgnoreCase("no game found")!=0) {
				
				//if user wants to edit game
				if(req.getParameter("formName").compareToIgnoreCase("editGame")==0) {
					
					//String pname=req.getParameter("pubName");
					//String ageRate=req.getParameter("ageRating");
					sqlQuery="INSERT INTO game( platform, name, developer_ID, year) VALUES ("+platform+", "+gname+", "+dname+", "+year+");";
					gameObj.simpleQuery(sqlQuery);
					result="Game edit complete.";
				}//end edit game
				
				//if user wants to review a game
				if(req.getParameter("formName").compareToIgnoreCase("reviewGame")==0) {					
					String reviewType=req.getParameter("ratingType");
					int reviewVal=Integer.parseInt(req.getParameter("score")); //gets score and converts from string to int
					
					//if review is critic
					if(reviewType.compareToIgnoreCase("critic")==0) {
						sqlQuery="UPDATE game SET critic_score = (critic_count * critic_score + "+reviewVal+")/++critic_count, critic_count = critic_count+1, WHERE game_ID = (SELECT game_ID FROM game WHERE name="+gname+" and g.platform=" +platform +");"; 
						gameObj.simpleQuery(sqlQuery);
						result="Game critic review complete.";
					}//end critic if
					
					//if review is user
					if(reviewType.compareToIgnoreCase("user")==0) {
						sqlQuery="UPDATE game SET user_score = (user_count * user_score + "+reviewVal+")/++user_count, user_count=user_count+1 WHERE game_ID = (SELECT game_ID FROM game WHERE name="+gname+" and g.platform=" +platform +");";
						gameObj.simpleQuery(sqlQuery);
						result="Game user review complete.";
					}//end user if
					
				}//end if review game
				
			}//end if query found game
			
		}//end if search game, review game, or edit game
		
		out.println(result);//posts result to website
		
	}//end doPost
		
				
	//from GameQueries.java		
	public String simpleQuery(String sqlQuery) {
		String games="no game found";
    	try {
    		statement = connection.createStatement();
    		resultSet = statement.executeQuery(sqlQuery);

    		ResultSetMetaData metaData = resultSet.getMetaData();
    		int columns = metaData.getColumnCount();
    		
    		

    		for (int i=1; i<= columns; i++) {
    			games+=(metaData.getColumnName(i)+"\t"); //add result to string?
    		}

    		System.out.println();

    		while (resultSet.next()) {
       
    			for (int i=1; i<= columns; i++) {
    				games+=(resultSet.getObject(i)+"\t\t"); //add result to string?
    			}
    			//System.out.println();
    		}
    		
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
		return games;  //return string
    } // end of simpleQuery method
		
	//from TopGameProcedures.java
		public String simpleStoreProcedure(String spName) {
			String listGame="";
			try {
				statement = connection.createStatement();
				
				CallableStatement myCallStmt = connection.prepareCall("{call "+spName+"(?)}");
				myCallStmt.setString(1,"");
		        myCallStmt.registerOutParameter(1,Types.VARCHAR);
		        myCallStmt.execute();
		        listGame = myCallStmt.getString(1); //add output to string?
		        
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		 
		 return(listGame);//return string
	}// end of simpleStoreProcedure method
}//end of Test Servlet
