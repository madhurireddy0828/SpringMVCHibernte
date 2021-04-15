<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>

<jsp:include page="header.jsp"></jsp:include>
<h2>Delete Employee Screen</h2>

<font color="red">${messageInfo }</font>

<form action="deleteEmp" method="post">
Enter user id to delete: <input type="text" name="id" id="" >
<br />
<input type="submit" name="Delete" value="Delete">

</form>

</body>
</html>