<?php

	define("SITE_MYSQL_SERVER",     "mysql03.totaalholding.nl");
	define("SITE_MYSQL_USERNAME",   "endran_client_sp");
	define("SITE_MYSQL_PASSWORD",   "a)Ma.@m7iz*I");
	define("SITE_MYSQL_DATABASE",   "endran_scrumpoker");
	define("SITE_MYSQL_TABLE",   "ads");

	function getAddLevel(){
		$pdo = new PDO('mysql:host=' . SITE_MYSQL_SERVER . ';dbname=' . SITE_MYSQL_DATABASE, SITE_MYSQL_USERNAME, SITE_MYSQL_PASSWORD);
		$stmt = $pdo->prepare('SELECT * FROM ' . SITE_MYSQL_TABLE);
		$stmt->execute();
		return $stmt->fetchAll();
	}		
		
	$rows = getAddLevel();

	if($userId != '') {
		echo json_encode($rows);
	}
	else {
		echo '<table width="1500">';
		echo '<tr>';
		echo '<td><b>AdLevel</b></td>';
		echo '</tr>';
	
		foreach ($rows as $row) {
			echo '<tr>';
			echo '<td>' . $row['adLevel'] . '</td>';
			echo '</tr>';
		}
		
		echo '</table>';
	}
	
	$row = null;
	$stmt = null;
	$pdo = null;
?>
