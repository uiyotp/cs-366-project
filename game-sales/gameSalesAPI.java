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

	public gameSalesAPI() {
		super();
	}

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
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	{
		//If there is an error, then I know this method is running.  And there is no error :(
		response.sendError(HttpServletResponse.SC_NOT_FOUND, "The requested page [page] not found.");
		
        //Open the connection
		//gameSalesAPI gameObj=new gameSalesAPI();
		//gameObj.Connection();
		String queryResult = "[ { \"_id\": \"608db231ad0a0bf24df8c14e\", \"index\": 0, \"guid\": \"f91d6d52-194c-4ae4-a219-4a1d6f7a1cb7\", \"isActive\": false, \"balance\": \"$2,793.16\", \"picture\": \"http://placehold.it/32x32\", \"age\": 37, \"eyeColor\": \"green\", \"name\": { \"first\": \"Randall\", \"last\": \"Ford\" }, \"company\": \"EWAVES\", \"email\": \"randall.ford@ewaves.net\", \"phone\": \"+1 (948) 477-3768\", \"address\": \"108 Ira Court, Boykin, North Carolina, 8541\", \"about\": \"Incididunt anim sint anim esse. Proident cillum laborum cupidatat irure voluptate enim quis ut adipisicing exercitation. Eu id enim qui ad cillum ex pariatur ad aute et irure non. Nisi pariatur aliquip nisi anim ea Lorem mollit excepteur aliquip et ullamco velit. Incididunt enim cupidatat proident commodo culpa adipisicing ex minim consectetur cillum. Sunt pariatur eiusmod eiusmod ex.\", \"registered\": \"Tuesday, June 19, 2018 2:40 PM\", \"latitude\": \"-46.832858\", \"longitude\": \"-102.53401\", \"tags\": [ \"et\", \"quis\", \"eu\", \"aute\", \"nulla\" ], \"range\": [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ], \"friends\": [ { \"id\": 0, \"name\": \"Kelley Jacobson\" }, { \"id\": 1, \"name\": \"Reed Estes\" }, { \"id\": 2, \"name\": \"Deirdre Cantrell\" } ], \"greeting\": \"Hello, Randall! You have 5 unread messages.\", \"favoriteFruit\": \"apple\" }, { \"_id\": \"608db2311fec7d97e3c0bae3\", \"index\": 1, \"guid\": \"d1054479-8451-4f8c-9f99-a514025ff32b\", \"isActive\": false, \"balance\": \"$3,442.46\", \"picture\": \"http://placehold.it/32x32\", \"age\": 26, \"eyeColor\": \"brown\", \"name\": { \"first\": \"Blevins\", \"last\": \"Woodward\" }, \"company\": \"VERAQ\", \"email\": \"blevins.woodward@veraq.org\", \"phone\": \"+1 (945) 547-3232\", \"address\": \"537 Conduit Boulevard, Biddle, Washington, 1328\", \"about\": \"Enim officia in anim mollit sit commodo cupidatat aliqua cillum. Mollit laboris adipisicing amet aliqua. Occaecat dolore quis tempor voluptate anim aliqua fugiat ut quis officia sunt sit magna. Reprehenderit sunt nisi id voluptate commodo. Duis cupidatat dolor laborum ea minim mollit officia amet enim incididunt voluptate consequat anim.\", \"registered\": \"Friday, August 21, 2015 5:34 PM\", \"latitude\": \"21.03576\", \"longitude\": \"-23.466542\", \"tags\": [ \"pariatur\", \"sit\", \"quis\", \"velit\", \"ut\" ], \"range\": [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ], \"friends\": [ { \"id\": 0, \"name\": \"Yvonne Mason\" }, { \"id\": 1, \"name\": \"Yolanda Ramos\" }, { \"id\": 2, \"name\": \"Catherine Cardenas\" } ], \"greeting\": \"Hello, Blevins! You have 9 unread messages.\", \"favoriteFruit\": \"banana\" }, { \"_id\": \"608db231f674485ade582d3b\", \"index\": 2, \"guid\": \"d268cb95-3593-42f1-b3d0-493adaed41e3\", \"isActive\": true, \"balance\": \"$1,910.64\", \"picture\": \"http://placehold.it/32x32\", \"age\": 20, \"eyeColor\": \"brown\", \"name\": { \"first\": \"Concepcion\", \"last\": \"Love\" }, \"company\": \"COMTRAIL\", \"email\": \"concepcion.love@comtrail.name\", \"phone\": \"+1 (880) 413-2783\", \"address\": \"423 Colonial Court, Hachita, Tennessee, 8741\", \"about\": \"Id nostrud enim eiusmod id minim tempor consequat veniam commodo minim esse laboris Lorem. Mollit veniam laborum sit aute aute laborum aliqua tempor do ad. Id eiusmod cillum ullamco est veniam anim exercitation nulla exercitation consequat id.\", \"registered\": \"Friday, February 15, 2019 12:43 PM\", \"latitude\": \"83.569785\", \"longitude\": \"-153.819762\", \"tags\": [ \"mollit\", \"occaecat\", \"sint\", \"laboris\", \"sint\" ], \"range\": [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ], \"friends\": [ { \"id\": 0, \"name\": \"Hays Sweet\" }, { \"id\": 1, \"name\": \"Dale Hawkins\" }, { \"id\": 2, \"name\": \"Mejia Colon\" } ], \"greeting\": \"Hello, Concepcion! You have 6 unread messages.\", \"favoriteFruit\": \"strawberry\" }, { \"_id\": \"608db2315d21176453df6fde\", \"index\": 3, \"guid\": \"228247a7-7514-4988-9606-d4b42e30fead\", \"isActive\": true, \"balance\": \"$2,180.40\", \"picture\": \"http://placehold.it/32x32\", \"age\": 38, \"eyeColor\": \"green\", \"name\": { \"first\": \"Lidia\", \"last\": \"Chavez\" }, \"company\": \"FUTURIS\", \"email\": \"lidia.chavez@futuris.info\", \"phone\": \"+1 (987) 564-3626\", \"address\": \"138 Celeste Court, Tolu, Nebraska, 5889\", \"about\": \"Ullamco proident dolor voluptate proident commodo consectetur veniam mollit commodo consequat sit. Ad Lorem sit labore ullamco sit. Magna est id est culpa irure proident dolor occaecat culpa qui. Adipisicing ipsum ea sunt ullamco culpa non duis commodo in. Cupidatat commodo pariatur nostrud ipsum ea voluptate quis ipsum sit quis voluptate occaecat nulla.\", \"registered\": \"Monday, September 9, 2019 1:39 AM\", \"latitude\": \"-77.036069\", \"longitude\": \"-115.255247\", \"tags\": [ \"laborum\", \"velit\", \"cillum\", \"commodo\", \"elit\" ], \"range\": [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ], \"friends\": [ { \"id\": 0, \"name\": \"Blair Fernandez\" }, { \"id\": 1, \"name\": \"Juliette Hutchinson\" }, { \"id\": 2, \"name\": \"Erna Townsend\" } ], \"greeting\": \"Hello, Lidia! You have 7 unread messages.\", \"favoriteFruit\": \"banana\" }, { \"_id\": \"608db2311dbd175ee941baa8\", \"index\": 4, \"guid\": \"76d6458e-9fc1-4407-a056-fbccc2a8fcf9\", \"isActive\": false, \"balance\": \"$3,350.10\", \"picture\": \"http://placehold.it/32x32\", \"age\": 38, \"eyeColor\": \"blue\", \"name\": { \"first\": \"Keith\", \"last\": \"Johns\" }, \"company\": \"SENSATE\", \"email\": \"keith.johns@sensate.com\", \"phone\": \"+1 (935) 540-3936\", \"address\": \"865 Locust Street, Noblestown, Pennsylvania, 1364\", \"about\": \"Ad eu pariatur culpa exercitation ullamco cupidatat. Tempor laboris irure amet tempor ut ullamco laboris dolore cillum veniam elit aliqua. Ad esse nisi labore voluptate aliquip officia id et ut. Deserunt sunt nostrud enim proident incididunt aute. Incididunt ut ex commodo esse amet ad exercitation ipsum mollit officia adipisicing occaecat. Ipsum nisi voluptate id duis. In labore veniam velit minim duis esse irure commodo velit proident sint.\", \"registered\": \"Thursday, September 10, 2015 8:04 AM\", \"latitude\": \"3.60866\", \"longitude\": \"-92.476863\", \"tags\": [ \"proident\", \"cillum\", \"officia\", \"nulla\", \"et\" ], \"range\": [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ], \"friends\": [ { \"id\": 0, \"name\": \"June Miranda\" }, { \"id\": 1, \"name\": \"Ina Lynn\" }, { \"id\": 2, \"name\": \"Diane Wagner\" } ], \"greeting\": \"Hello, Keith! You have 10 unread messages.\", \"favoriteFruit\": \"apple\" } ]";

        // //For the topGameFilter form post
		// if (request.getParameter("formName") == "topGameFilter"){
        //     String filter = request.getParameter("topMetric");
        //     String sql = "";			
		// 	if (filter == "critic") {
		// 		sql = "SELECT g.name, p.name, d.name, g.year, g.genre, g.platform, g.critic_score, g.user_score, g.age_rating FROM game AS g" +
        //         "INNER JOIN publisher AS p" +
        //         "ON g.pub_ID = p.pub_ID" +
        //         "INNER JOIN developer AS d" +
        //         "ON g.dev_ID = d.dev_ID" +
        //         "WHERE g.critic_count > 10" +
        //         "ORDER BY g.critic_score desc" +
        //         "LIMIT 10;";	
		// 	}
		// 	if (filter == "user") {
		// 		sql = "SELECT g.name, p.name, d.name, g.year, g.genre, g.platform, g.critic_score, g.user_score, g.age_rating FROM game AS g" +
        //         "INNER JOIN publisher AS p" +
        //         "ON g.pub_ID = p.pub_ID" +
        //         "INNER JOIN developer AS d" +
        //         "ON g.dev_ID = d.dev_ID" +
        //         "WHERE g.user_count > 10" +
        //         "ORDER BY g.user_score desc" +
        //         "LIMIT 10;";
		// 	}
		// 	if (filter == "sales") {
		// 		sql = "SELECT g.name, p.name, d.name, g.year, g.genre, g.platform, g.critic_score, g.user_score, g.age_rating" +
        //         "FROM game g, publisher p, developer d, sales s," +
        //         "WHERE g.game_ID in(" +
        //         "SELECT game_ID" +
        //         "FROM game g" +
        //         "WHERE game_ID in(" +
        //         "SELECT g.game_ID, (s.na_sales + s.eu_sales + s.jp_sales + s.other_sales ) AS total_sales" +
        //         "FROM game g, sales s" +
        //         "WHERE s.game_ID=g.game_ID" +
        //         "ORDER BY total_sales desc" +
        //         "LIMIT 10));";
		// 	}
        //     queryResult = gameObj.simpleQuery(sql);
        // }
		
        // //Close the connection
        // try {
        //     connection.close();
        // }
        // catch (SQLException e) {
        //     e.printStackTrace();
        // }

		PrintWriter respwriter = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		respwriter.print(queryResult);
		respwriter.flush();
		
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