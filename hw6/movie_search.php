<?php

echo '<html><head><meta http-equiv="Content-type" content="text/html;charset=UTF-8"></head>';

$title = $_POST["title"]; // how do i do the escape character thing?
$type = $_POST["type"];
if ($_POST["type"] == "All Types") {
	$type = "feature,tv_series,game";
}else if ($_POST["type"] == "Film") {
	$type = "feature";
} else if ($_POST["type"] == "TV") {
	$type = "tv_series";
} else if ($_POST["type"] == "Video Games") {
	$type = "game";
}

$query_string = 'title=' . urlencode($title) . '&' . 'title_type=' . $type;										
//echo $query_string;
//echo '<br/>';								

$query_url = 'http://www.imdb.com/search/title?' . $query_string;
//echo $query_url;
	
$query_results = file_get_contents($query_url);
//echo $query_results;
echo '<h1 align="center" style="margin: 0px auto;">Search Results</h1>';
echo '<h3 align="center" style="margin: 0px auto">"' . $_POST["title"] . '" of type "' . $_POST["type"] . "</h3>"; 
echo '</br>';
// [1] - link, [2] - title, [3] - year, [4] - image
// /<tr class=\"even|odd detailed\">(.*)<\/tr>/
//<tr class=\"(even|odd) detailed\">(.*)<\/tr>
$tag = 'tr';
$pattern = "/<$tag class=\"(even|odd) detailed\">(.*?)<\/$tag>/s";
if(preg_match_all($pattern, $query_results, $results, PREG_SET_ORDER)) {
		
	echo '<br/>';

	echo '<table border=1 style="margin: 0px auto">';
	echo '<tr><th>Image</th><th>Title</th><th style="padding-left:10px;padding-right:10px">Year</th><th>Director</th><th style="padding-left:10px;padding-right:10px">Rating(10)</th><th style="padding-left:10px;padding-right:10px">Link</th></tr>';
	for ($i = 0; $i < 5; $i++) {
		//echo $results[$i][2];
		if (!array_key_exists($i, $results)) {
			break;
		}
		echo '<tr>';
		// get image
		// TODO: check if $results[$i][2] is null, break if it is, for results nubering less than 5
		$pattern = "/<img src=\"(.*?)\".*>/";
		preg_match($pattern, $results[$i][2], $image);
		//echo 'image: ' . $image[1] . '<br/>';
		echo '<td><img src="' . $image[1] .'"/></td>';
		// get title
		$pattern = "/<a href=\".title.[a-z,A-Z,0-9]+.\">(.+?)</";
		preg_match($pattern, $results[$i][2], $title);
		//echo 'title: ' . $title[1] . '<br/>';
		echo '<td align="center" style="padding-left:10px;padding-right:10px">' . $title[1] . '</td>';
		// get year
		$pattern = "/<span class=\"year_type\">.([0-9,?]{4})/";
		preg_match($pattern, $results[$i][2], $year);
		//echo 'year: ' . $year[1] . '<br/>';
		echo '<td align="center">' . $year[1] . '</td>';
		// get director
		// director list
		$pattern = '/Dir:.*/';
		if (preg_match($pattern, $results[$i][2], $directors)) {
			$pattern = "/<a href=\".name.[a-z,A-Z,0-9]+.\">(.+?)<.a>,?/";
			preg_match_all($pattern, $directors[0], $director_names, PREG_PATTERN_ORDER);
			$director = implode(', ', $director_names[1]);
		} else {
			$director = 'N/A';
		}
		$director = utf8_encode($director);
		//echo 'director: ' . $director . '<br/>';
		echo '<td align="center" style="padding-left:10px;padding-right:10px">' . $director . '</td>';
		// Get Rating
		$pattern = '/<span class=\"rating-rating\"><span class=\"value\">([0-9,\.]+)/';
		if (preg_match($pattern, $results[$i][2], $rating)) {
			//echo 'rating: ' . $rating[1] . '<br/>';
			echo '<td align="center">' . $rating[1] . '</td>';
		} else {
			//echo 'rating: N/A <br/>';
			echo '<td align="center">N/A</td>';
		}
		// Get Link
		$pattern = '/.title.[a-z,A-Z.0-9]+./';
		preg_match($pattern, $results[$i][2], $link);
		//echo 'http://www.imdb.com' . $link[0] . '<br/>';
		$full_link = 'http://www.imdb.com' . $link[0];
		echo "<td align='center'><a href='" . $full_link . "'>Link</a></td>";

		echo '</tr>';
	}
	echo '</table>';
} else {
	echo '<p align="center"><b>No results found!</b></p>';
}