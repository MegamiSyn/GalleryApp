<?php
$page_number = $_GET['page_number'];
$item_count = $_GET['item_count'];

$from = $page_number*$item_count - ($item_count-1);
$to = $page_number*$item_count;
$response = array();
$stats = array();

if($to>70){
	array_push($response,array('status' => 'end'));
	echo json_encode($response);
}else{
	array_push($response, array('status' => 'ok'));
	$count = $from;
	$images = array();
}

while ($count <= $to) {
	if($count <10){
		$image_path = "http://192.168.0.100/gallery/images/pic0".$count.".jpg";
	}else{
		$image_path = "http://192.168.0.100/gallery/images/pic".$count.".jpg";
	}
	array_push($images, array('id' =>$count,'image_path'=>$image_path));
	$count=$count+1;
}

array_push($response, array('images'=>$images));
	sleep(2);
	echo json_encode($response);

?>