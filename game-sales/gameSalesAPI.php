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
                    $topGenre = " WHERE g.genre = " . $topGenre;
                }
                if ($topMetric == "critic") {
                    $sql = "SELECT g.name, p.name, d.name, g.year, g.genre, g.platform, g.critic_score, g.user_score, g.age_rating FROM game AS g
                    INNER JOIN publisher AS p
                    ON g.pub_ID = p.pub_ID
                    INNER JOIN developer AS d
                    ON g.dev_ID = d.dev_ID
                    WHERE g.critic_count > 10" . $topGenre . "
                    ORDER BY g.critic_score desc
                    LIMIT 10;";	
                }
                if ($topMetric == "user") {
                    $sql = "SELECT g.name, p.name, d.name, g.year, g.genre, g.platform, g.critic_score, g.user_score, g.age_rating FROM game AS g
                    INNER JOIN publisher AS p
                    ON g.pub_ID = p.pub_ID
                    INNER JOIN developer AS d
                    ON g.dev_ID = d.dev_ID
                    WHERE g.user_count > 10" . $topGenre . "
                    ORDER BY g.user_score desc
                    LIMIT 10;";
                }
                if ($topMetric == "sales") {
                    $sql = "SELECT g.name, p.name, d.name, g.year, g.genre, g.platform, g.critic_score, g.user_score, g.age_rating
                    FROM game g, publisher p, developer d, sales s,
                    WHERE g.game_ID in(
                    SELECT game_ID
                    FROM game g
                    WHERE game_ID in(
                    SELECT g.game_ID, (s.na_sales + s.eu_sales + s.jp_sales + s.other_sales ) AS total_sales
                    FROM game g, sales s
                    WHERE s.game_ID=g.game_ID" . $topGenre . "
                    ORDER BY total_sales desc
                    LIMIT 10));";
                }
                $resultSet = simpleQuery($sql, $db);
                echo json_encode($resultSet);
                break;
    
            default:
                break; 
        }
                
    }else if($_SERVER['REQUEST_METHOD'] === 'GET'){
        //The only GET request in our app is to get a list of genres to populate the genre dropdown
        $sql = "SELECT UNIQUE genre FROM game";
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