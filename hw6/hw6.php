<!DOCTYPE html PUBLIC �@-//W3C//DTD XHTML 1.0 Strict//EN�
   �http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict-dtd�>
<html>
<head>
	<meta http-equiv="Content-type" content="text/html;charset=UTF-8">
	<title>Homework #6</title>
	<script>
		function titleCheck() {
			var x = document.forms["imdbSearch"]["title"].value;
			if (x == null || x == "") {
				alert("Please Enter a Title");
				return false;
			}
		}
	</script>
</head>
<body>
<h1 align="center" >Movie Search</h1>
<div align="center" style="margin:0px auto">
	<form id="imdbSearch" method="POST" action="movie_search.php" onSubmit="return titleCheck()" >
		<label for="title">Title:</label>
		<input type="text" name="title" />
		<br/>
		<label for="type">Media Type:</label>
		<select name="type">
			<option selected>All Types</option>
			<option>Film</option>
			<option>TV</option>
			<option>Video Games</option>
		</select>
		<br/>
		<input type="submit" value="Search" />
	</form>
</div>
<div>
</div>
</body>
</html>