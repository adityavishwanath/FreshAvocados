<?php
$con=mysqli_connect("mysql7.000webhost.com","a4341314_fresh","Group49","a4341314_avocado");
if (mysqli_connect_errno($con))
{
   echo '{"query_result":"ERROR"}';
}

$username = $_GET['username'];
$movie = $_GET['movie'];
$comment = $_GET['comment'];
$rating = $_GET['rating'];

$result = mysqli_query($con,"SELECT MAX(ID) FROM Review");
$checkExist = mysqli_query($con,"SELECT * FROM Review WHERE Username = '$username' AND Movie = '$movie'");
#delete existing comment
#$delExist = mysqli_query($con,"DELETE FROM Review WHERE Username = '$username' AND Movie = '$movie'"); 


if($result == true) {
   	$row = mysqli_fetch_array($result);
   	#gets next ID
   	if ($row['MAX(ID)'] != NULL) {
   		$id = $row['MAX(ID)'] + 1;
	} else {
		$id = 1000;
	}
   	#insert new comment	
   	$rowExist = mysqli_fetch_array($checkExist);
   	if ($rowExist == NULL) {
		$ins = mysqli_query($con,"INSERT INTO Review(ID, Username, Movie, Comment, Rating)
			VALUES ('$id', '$username', '$movie', '$comment', '$rating')");
		if($ins == true) {
			echo '{"query_result":"SUCCESS"}';
		} else {
			echo '{"query_result":"FAILURE"}';	
		}
	} else {
		echo '{"query_result":"SUCCESS"}';
	}
#sql did not work
} else {
    echo '{"query_result":"FAILURE"}';
}

mysqli_close($con);
?> 