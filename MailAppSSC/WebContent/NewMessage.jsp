<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>	
	<br><br>
	<form action="MessageServlet" method="post" id="form">
		To: <input type=text name="to">
		<br><br>
		Cc: <input type=text name="cc">
		<br><br>
		Subject: <input type=text name="subject">
		<br><br>
		<p>Body:</p> 
		<textarea name="body" form="form">...</textarea>
		<br><br>
		<input type="submit" value="Send"> 
		<br><br>
	</form>
	<form action="LogoutServlet" method="post">
		<input type="submit" value="Logout"> 	
	</form>
		
</body>
</html>