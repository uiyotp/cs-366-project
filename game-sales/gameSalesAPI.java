import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class gameSalesAPI extends HttpServlet
{  
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
	
    //For all POST HTTP REQUESTS (Will not work for GETs)
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
        //Open the connection
		gameSalesAPI gameObj=new gameSalesAPI();
		gameObj.Connection();
		String queryResult = "";

        //For the topGameFilter form post
		if (request.getParameter("formName") == "topGameFilter"){
            String filter = request.getParameter("topMetric");
            String sql = "";			
			if (filter == "critic") {
				sql = "SELECT g.name, p.name, d.name, g.year, g.genre, g.platform, g.critic_score, g.user_score, g.age_rating FROM game AS g" +
                "INNER JOIN publisher AS p" +
                "ON g.pub_ID = p.pub_ID" +
                "INNER JOIN developer AS d" +
                "ON g.dev_ID = d.dev_ID" +
                "WHERE g.critic_count > 10" +
                "ORDER BY g.critic_score desc" +
                "LIMIT 10;";	
			}
			if (filter == "user") {
				sql = "SELECT g.name, p.name, d.name, g.year, g.genre, g.platform, g.critic_score, g.user_score, g.age_rating FROM game AS g" +
                "INNER JOIN publisher AS p" +
                "ON g.pub_ID = p.pub_ID" +
                "INNER JOIN developer AS d" +
                "ON g.dev_ID = d.dev_ID" +
                "WHERE g.user_count > 10" +
                "ORDER BY g.user_score desc" +
                "LIMIT 10;";
			}
			if (filter == "sales") {
				sql = "SELECT g.name, p.name, d.name, g.year, g.genre, g.platform, g.critic_score, g.user_score, g.age_rating" +
                "FROM game g, publisher p, developer d, sales s," +
                "WHERE g.game_ID in(" +
                "SELECT game_ID" +
                "FROM game g" +
                "WHERE game_ID in(" +
                "SELECT g.game_ID, (s.na_sales + s.eu_sales + s.jp_sales + s.other_sales ) AS total_sales" +
                "FROM game g, sales s" +
                "WHERE s.game_ID=g.game_ID" +
                "ORDER BY total_sales desc" +
                "LIMIT 10));";
			}
            queryResult = gameObj.simpleQuery(sql);
        }
		
        //Close the connection
        try {
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(queryResult);
		out.flush();
		
	}
		
	public String simpleQuery(String sqlQuery) {
		//Need to convert to JSON in order to output results
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
    }

    public void storeProcedure(String procedure) {
		try {
			statement = connection.createStatement();
			int total =0;
			CallableStatement myCallStmt = connection.prepareCall("{call " + procedure + " (?)}");
		    myCallStmt.registerOutParameter(1,Types.BIGINT);
		    myCallStmt.execute();
		    total = myCallStmt.getInt(1);
		    System.out.println("The procedure " + procedure + " returned: "+ total);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
	public void storeProcedure(String procedure, String input) {
		try {
			statement = connection.createStatement();
			CallableStatement myCallStmt;
			String result = "";
			if(input == "") {
				myCallStmt = connection.prepareCall("{call " + procedure + " (?)}");
			}else {
				myCallStmt = connection.prepareCall("{call " + procedure + " (?," + input + ")}");
			}
			myCallStmt.setString(1,"");
		    myCallStmt.registerOutParameter(1,Types.VARCHAR);
	        myCallStmt.execute();
	        result = myCallStmt.getString(1);
		    System.out.println("The procedure " + procedure + " returned: "+ result);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

}