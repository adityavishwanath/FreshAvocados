<?php
$con=mysqli_connect("mysql7.000webhost.com","a4341314_fresh","Group49","a4341314_avocado");
if (mysqli_connect_errno($con))
{
   echo '{"query_result":"ERROR"}';
}


$username = $_GET['username'];
$isLocked = $_GET['isLocked'];
$isBanned = $_GET['isBanned'];

$result = mysqli_query($con,"UPDATE User SET isLocked = '$isLocked', isBanned = '$isBanned' WHERE Username = '$username'");
 
if($result == true) {
    echo '{"query_result":"SUCCESS"}';
}
else{
    echo '{"query_result":"FAILURE"}';
}

$updateLocked = mysqli_query($con,"UPDATE User SET loginAttempts = CASE WHEN (isLocked = 0 AND Username = '$username') THEN '0' 
									WHEN (isLocked = 1 AND Username = '$username') THEN '3' END");
if (!$updateLocked) {
    echo '{"query_result":"FAILURE"}';
}

mysqli_close($con);
?>