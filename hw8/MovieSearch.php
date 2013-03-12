<?php

echo '<rsp state="ok">';

// HOW DO I DO THIS PART NOW!?! GET RID OF IT?
$title = $_GET["title"]; // how do i do the escape character thing?
$type = $_GET["type"];
if ($_GET["type"] == "All Types") {
	$type = "feature,tv_series,game";
}else if ($_GET["type"] == "Film") {
	$type = "feature";
} else if ($_GET["type"] == "TV") {
	$type = "tv_series";
} else if ($_GET["type"] == "Video Games") {
	$type = "game";
}

// THIS SHOULD BE GOTTEN FROM THE JAVA SERVLET, HOW THE FUCK DO I DO.
$query_string = 'title=' . urlencode($title) . '&' . 'title_type=' . $type;										
//echo $query_string;
//echo '<br/>';								

$query_url = 'http://www.imdb.com/search/title?' . $query_string;
//echo $query_url;
////////////////////
	
$query_results = file_get_contents($query_url);
//echo $query_results;

// [1] - link, [2] - title, [3] - year, [4] - image
// /<tr class=\"even|odd detailed\">(.*)<\/tr>/
//<tr class=\"(even|odd) detailed\">(.*)<\/tr>
$tag = 'tr';
$pattern = "/<$tag class=\"(even|odd) detailed\">(.*?)<\/$tag>/s";

$matches = preg_match_all($pattern, $query_results, $results, PREG_SET_ORDER);

if (sizeof($results) < 5) {
	echo '<results total="' . sizeof($results) . '">';
} else {
	echo '<results total="5">';
}

if($matches) {
		

	for ($i = 0; $i < 5; $i++) {
		//echo $results[$i][2];
		if (!array_key_exists($i, $results)) {
			break;
		}
		// GET IMAGE
		$pattern = "/<img src=\"(.*?)\".*>/";
		preg_match($pattern, $results[$i][2], $image);
		// GET TITLE
		$pattern = "/<a href=\".title.[a-z,A-Z,0-9]+.\">(.+?)</";
		preg_match($pattern, $results[$i][2], $title);
		// GET YEAR
		$pattern = "/<span class=\"year_type\">.([0-9,?]{4})/";
		preg_match($pattern, $results[$i][2], $year);
		// GET DIRECTOR
		// director list
		$pattern = '/Dir:.*/';
		if (preg_match($pattern, $results[$i][2], $directors)) {
			$pattern = "/<a href=\".name.[a-z,A-Z,0-9]+.\">(.+?)<.a>,?/";
			preg_match_all($pattern, $directors[0], $director_names, PREG_PATTERN_ORDER);
			$director = implode(', ', $director_names[1]);
		} else {
			$director = 'N/A';
		}
		// GET RATING
		$pattern = '/<span class=\"rating-rating\"><span class=\"value\">([0-9,\.]+)/';
		if (preg_match($pattern, $results[$i][2], $rating)) {
			$rate = $rating[1];
		} else {
			//echo 'rating: N/A <br/>';
			$rate = 'N/A';
		}
		// Get Link
		$pattern = '/.title.[a-z,A-Z.0-9]+./';
		preg_match($pattern, $results[$i][2], $link);
		$full_link = 'http://www.imdb.com' . $link[0];
		// XML entry
		echo '<result cover="' . $image[1] . '" title="' . $title[1] . '" year="' . $year[1] . '" director="' . $director .
				'" rating="' . $rate . '" details="' . $full_link . '"/>';
	}

}
echo '</results>';
echo '</rsp>';