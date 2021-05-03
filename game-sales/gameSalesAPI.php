<?php

include("pdo_connect.php");

// Make sure that connection to databse is successful
if (!$db) {
    echo "Could not connect to the database";
    exit();
}

// Define variables and assign their default values
$formName = ""; // default value for the switch statement
$parameterValues = null; // default values named parameters
$pageTitle = ""; // define a title for each output
$columns = array(); // define an array of column labels for a table header

try {
    if($_SERVER['REQUEST_METHOD'] === 'POST'){
        
        if (isset($_POST['formName'])) {
            $formName = $_POST['formName'];
        }
        
        switch ($formName) {
            //If the form name is "topGameFilter", then we are going to display the top 10 rated games based on the parameter
            case "topGameFilter":
                $topMetric = $_POST['topMetric'];
                $topGenre = $_POST['topGenre'];
                if($topGenre == "none"){
                    $topGenre = "";
                } else {
                    $topGenre = " AND g.genre = '" . $topGenre . "' ";
                }
                if ($topMetric == "critic") {
                    $sql = "SELECT g.name, p.name as publisher, d.name as developer, g.year, g.genre, g.platform, g.critic_score, g.user_score, (s.na_sales + s.eu_sales + s.jp_sales + s.other_sales ) AS total_sales
                    FROM game AS g
                    INNER JOIN publisher AS p
                    ON g.publisher_ID = p.publisher_ID
                    INNER JOIN developer AS d
                    ON g.developer_ID = d.developer_ID
                    INNER JOIN sales AS s
                    ON g.game_ID = s.game_ID
                    WHERE g.critic_count > 10" . $topGenre . "
                    ORDER BY g.critic_score desc
                    LIMIT 10;";
                }
                if ($topMetric == "user") {
                    $sql = "SELECT g.name, p.name as publisher, d.name as developer, g.year, g.genre, g.platform, g.critic_score, g.user_score, (s.na_sales + s.eu_sales + s.jp_sales + s.other_sales ) AS total_sales
                    FROM game AS g
                    INNER JOIN publisher AS p
                    ON g.publisher_ID = p.publisher_ID
                    INNER JOIN developer AS d
                    ON g.developer_ID = d.developer_ID
                    INNER JOIN sales AS s
                    ON g.game_ID = s.game_ID
                    WHERE g.user_count > 10" . $topGenre . "
                    ORDER BY g.user_score desc
                    LIMIT 10;";
                }
                if ($topMetric == "sales") {
                    $sql = "SELECT g.name, p.name as publisher, d.name as developer, g.year, g.genre, g.platform, g.critic_score, g.user_score, (s.na_sales + s.eu_sales + s.jp_sales + s.other_sales ) AS total_sales
                    FROM game as g
                    INNER JOIN publisher AS p
                    ON g.publisher_ID = p.publisher_ID
                    INNER JOIN developer AS d
                    ON g.developer_ID = d.developer_ID
                    INNER JOIN sales AS s
                    ON g.game_ID = s.game_ID
                    WHERE g.user_count > 10" . $topGenre . "
                    ORDER BY total_sales desc
                    LIMIT 10;";
                }
                $resultSet = simpleQuery($sql, $db);
                echo json_encode($resultSet);
                break;
                
            case "editGameSearch":
                $gameName = $_POST['gameName'];
                $devName = $_POST['devName'];
                $year = $_POST['year'];
                $platform = $_POST['platform'];
                //if fields are not empty, execute sql
                if ($gameName!="" && $devName!="" && $year!="" && $platform!=""){
                     $sql="SELECT g.name, p.name, d.name, g.year, g.genre, g.platform, g.critic_score, g.user_score, g.age_rating
                            FROM game AS g, publisher as p, developer as d 
                            WHERE g.name=". $gameName ." and year=" . $year ." and g.platform=". $platform ." and g.publisher_ID=p.publisher_ID and g.developer_ID=d.developer_ID and d.name=". $devName .";"; 
                }
                $resultSet = simpleQuery($sql, $db);
                echo json_encode($resultSet);
                break;
           
            case "editGame":
                $gameName = $_POST['gameName'];
                $devName = $_POST['devName'];
                $year = $_POST['year'];
                $platform = $_POST['platform'];
                //if fields are not empty, execute sql
                if ($gameName!="" && $devName!="" && $year!="" && $platform!=""){
                   $sql= "INSERT INTO game( platform, name, developer_ID, year)
                    VALUES (". $platform .", ". $gameName .", (SELECT developer_ID FROM developer WHERE name=".$devName ."), ". $year .");";
                    
                }
                
                $resultSet = simpleQuery($sql, $db);
                
                break;
                
            case "reviewGame":
                $gameName = $_POST['gameName'];
                $devName = $_POST['devName'];
                $year = $_POST['year'];
                $platform = $_POST['platform'];
                $ratingType=$_POST['ratingType'];
                $score=$_POST['score'];
                
                if ($ratingType=="critic"){
                    $sql="UPDATE game SET critic_score = 
                          (critic_count * critic_score + ". $score .")/++critic_count, critic_count = critic_count+1,
                          WHERE game_ID = 
                          (SELECT game_ID 
                          FROM game AS g, publisher as p, developer as d
                          WHERE g.name=". $gameName ." and year=" . $year ." and g.platform=". $platform ." and g.publisher_ID=p.publisher_ID and g.developer_ID=d.developer_ID and d.name=". $devName .";";  
                }
                
                if ($ratingType=="user"){
                    $sql="UPDATE game SET user_score =
                          (user_count * user_score + ". $score .")/++user_count, user_count = user_count+1,
                          WHERE game_ID =
                          (SELECT game_ID
                          FROM game AS g, publisher as p, developer as d
                          WHERE g.name=". $gameName ." and year=" . $year ." and g.platform=". $platform ." and g.publisher_ID=p.publisher_ID and g.developer_ID=d.developer_ID and d.name=". $devName .";";  
                }
                
                simpleQuery($sql, $db);
               
                break;
                
            case "searchGame":
                $gameName = $_POST['gameName'];
                $devName = $_POST['devName'];
                $year = $_POST['year'];
                $platform = $_POST['platform'];
                //if fields are not empty, execute sql
                if ($gameName!="" && $devName!="" && $year!="" && $platform!=""){
                    $sql="SELECT g.name, p.name, d.name, g.year, g.genre, g.platform, g.critic_score, g.user_score, g.age_rating
                            FROM game AS g, publisher as p, developer as d
                            WHERE g.name=". $gameName ." and year=" . $year ." and g.platform=". $platform ." and g.publisher_ID=p.publisher_ID and g.developer_ID=d.developer_ID and d.name=". $devName .";";
                }
                $resultSet = simpleQuery($sql, $db);
                echo json_encode($resultSet);
                break;
                //Add more cases here for each POST, remember to check and figure out what fields it is expecting you to return in the SELECT queries
                //Let me know if you have any questions
                
            default:
                break;
        }
        
    }else if($_SERVER['REQUEST_METHOD'] === 'GET'){
        //The only GET request in our app is to get a list of genres to populate the genre dropdown
        $sql = "SELECT DISTINCT genre FROM game;";
        $resultSet = simpleQuery($sql, $db);
        echo json_encode($resultSet);
    }
}catch (PDOException $e) {
    echo "Error!: ". $e->getMessage() . "<br/>";
    die();
}

function simpleQuery($sql, $db, $parameterValues = null){
    $statement = $db->prepare($sql);
    $statement->execute($parameterValues);
    $result = $statement->fetchAll(PDO::FETCH_ASSOC);
    return $result;
}

?>
