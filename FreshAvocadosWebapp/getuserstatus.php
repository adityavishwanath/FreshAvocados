<?php
$con=mysqli_connect("mysql7.000webhost.com","a4341314_fresh","Group49","a4341314_avocado");
if (mysqli_connect_errno($con))
{
   echo '{"query_result":"ERROR"}';
}


$username = $_GET['username'];

$result = mysqli_query($con,"SELECT Username, isLocked, isBanned FROM User WHERE Username = '$username'");
 
if($result == true) {
   	$row = mysqli_fetch_array($result);
   	 if (!empty($row)) {
        echo json_encode($row);           
   	 } else {
   	 	echo '{"query_result":"DENIED"}';
   	 }
}
else{
    echo '{"query_result":"FAILURE"}';
}
mysqli_close($con);
?>		