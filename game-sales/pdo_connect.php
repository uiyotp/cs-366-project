<?php

$user = 'paprockisj04';
$pass = 'sp3619'; 
$db_info='mysql:host=jdbc:mariadb://washington.uww.edu/cs366-2211_paprockisj04;dbname=cs366-2211_paprockisj04';
try {
    $db = new PDO($db_info, $user, $pass);

} catch (PDOException $e) {
    print "Error!: " . $e->getMessage() . "<br/>";
    die();
}

?>