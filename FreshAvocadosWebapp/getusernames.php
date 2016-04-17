<?php
$con=mysqli_connect("mysql7.000webhost.com","a4341314_fresh","Group49","a4341314_avocado");
if (mysqli_connect_errno($con))
{
   echo '{"query_result":"ERROR"}';
}


$username = $_GET['username']
;$password = $_GET['password'];
$continue = true;

$result = mysqli_query($con,"SELECT Username FROM User WHERE isAdmin = 0 ORDER BY Username");
 
if($result == true) {
	while ($continue) {
		$row = mysqli_fetch_array($result);
		if ($row == NULL) {
			$continue = false;
		} else {
    	echo json_encode($row);
        echo ",";
    	}
	}
} else{
    echo '{"query_result":"FAILURE"}';
}
mysqli_close($con);
?>	