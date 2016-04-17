<?php
$con=mysqli_connect("mysql7.000webhost.com","a4341314_fresh","Group49","a4341314_avocado");
if (mysqli_connect_errno($con))
{
   echo '{"query_result":"ERROR"}';
}

$movie = $_GET['movie'];
$continue = true;
$first = true;

$result = mysqli_query($con,"SELECT ID, Review.Username, Major, Rating, Comment FROM Review, User WHERE Movie = '$movie' and Review.Username = User.Username");

if($result == true) {

	while ($continue) {
		$row = mysqli_fetch_array($result);
		if ($row == NULL) {
			 if ($first) {
				echo '{"query_result":"EMPTY"}';
				echo ",";
			}
			$continue = false;
		} else {
			$first = false;
    		echo json_encode($row);
    		echo ",";
    	}
	}
} else{
    echo '{"query_result":"FAILURE"}';
}
mysqli_close($con);
?>