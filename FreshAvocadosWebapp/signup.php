<?php
$con=mysqli_connect("mysql7.000webhost.com","a4341314_fresh","Group49","a4341314_avocado");
if (mysqli_connect_errno($con))
{
   echo '{"query_result":"ERROR"}';
}

$username = $_GET['username'];
$password = $_GET['password'];
$Email = $_GET['Email'];
$Major = $_GET['Major'];
$FirstName = $_GET['FirstName'];
$LastName = $_GET['LastName'];
$bio = $_GET['bio'];
$checkEmail = mysqli_query($con,"SELECT * FROM User WHERE Email = '$Email'");
$checkUser = mysqli_query($con,"SELECT * FROM User WHERE Username = '$username'");
if ($checkUser == true) { # checks for no duplicate emails
		$checkExistingUser = mysqli_fetch_array($checkUser);
   	 if (!empty($checkExistingUser)) {
   	 	echo '{"query_result":"EXISTING"}';
   	 } elseif ($checkEmail == true) { #checks for no duplicate usernames
			$checkExistingEmail = mysqli_fetch_array($checkEmail);
   	 		if (!empty($checkExistingEmail)) {
   	 			echo '{"query_result":"EMAIL"}';
   	 		} else {
				$result = mysqli_query($con,"INSERT INTO User(Username, Password, Email, Major, FirstName, LastName, Bio) 
          		VALUES ('$username', '$password', '$Email', '$Major', '$FirstName', '$LastName', '$bio')");
				if($result == true) {
    				echo '{"query_result":"SUCCESS"}';
				} else {
    				echo '{"query_result":"FAILURE"}';
				}
			}
	} else {
		echo '{"query_result":"FAILURE"}';
	}
} else {
	echo '{"query_result":"FAILURE"}';
}

mysqli_close($con);
?>