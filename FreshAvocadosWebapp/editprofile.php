<?php
$con=mysqli_connect("mysql7.000webhost.com","a4341314_fresh","Group49","a4341314_avocado");
if (mysqli_connect_errno($con))
{
   echo '{"query_result":"ERROR"}';
}

#SELECT * FROM `user` WHERE Username = 'vk'


$username = $_GET['username'];
$Major = $_GET['Major'];
$bio = $_GET['bio'];

 
$result = mysqli_query($con,"UPDATE User SET Major = '$Major', Bio = '$bio' WHERE Username = '$username'");
 
if($result == true) {
    echo '{"query_result":"SUCCESS"}';
}
else{
    echo '{"query_result":"FAILURE"}';
}
mysqli_close($con);
?>