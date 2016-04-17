<?php
$con=mysqli_connect("mysql7.000webhost.com","a4341314_fresh","Group49","a4341314_avocado");
if (mysqli_connect_errno($con))
{
   echo '{"query_result":"ERROR"}';
}


$username = $_GET['username'];
$password = $_GET['password'];

$result = mysqli_query($con,"SELECT * FROM User WHERE Username = '$username' AND Password = '$password'");
$attempt = mysqli_query($con,"SELECT * FROM User WHERE Username = '$username'");

if($result == true) {
	$row = mysqli_fetch_array($result);
	 if (!empty($row)) { #successfully logged in
         //echo json_encode($row);
         $fp = fopen('data.json', 'w');
         fwrite($fp, json_encode($row));
         fclose($fp);
         $handle = fopen('data.json', 'r');
         $contents = fread($handle, filesize('data.json'));
         if ($contents) echo 'ayyyy';
         fclose($handle);
	 } else { #check if username exists
   		if($attempt == true) {
   			$rowAttempt = mysqli_fetch_array($attempt);
   	 			if (!empty($rowAttempt)) { #increase login Attempts
   	 				$numTry = mysqli_query($con,"UPDATE User SET loginAttempts = loginAttempts + 1 WHERE Username = '$username'");
   	 			}
 				echo '{"query_result":"DENIED"}';
	      }
   }
} else {
    echo '{"query_result":"FAILURE"}';
}

$updateLocked = mysqli_query($con,"UPDATE User SET isLocked = CASE WHEN (loginAttempts >= 3) THEN '1' END");
if (!$updateLocked) {
    echo '{"query_result":"FAILURE"}';
}

mysqli_close($con);
?>