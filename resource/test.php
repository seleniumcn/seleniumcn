<?php
$type=$_GET["type"];

if($type =="1"){
	sleep(2);
	echo "<button>testImplicitlyWait. it displayed after 2 seconds</button>";
}elseif($type=="2"){
	$seconds = rand(1,5);
	sleep($seconds);
	echo "<button onclick=\"alert('show ajax btn')\">testExplicitlyWait. it displayed after $seconds seconds</button>"; 
}elseif($type=="class"){
	sleep(5);
	echo "new";

}else{


}


?>