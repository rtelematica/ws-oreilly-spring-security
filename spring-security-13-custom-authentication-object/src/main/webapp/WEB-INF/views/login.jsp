<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Auto Service Center</title>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">

<!-- Latest Jquery -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"
	type="text/javascript"></script>
<!-- Latest compiled and minified JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<script
	src="<spring:url value="/resources/js/global.js"/>"></script>

</head>
<body>
	<jsp:include page="header.jsp" />
	<div class="container">
		<div class="row">
			<h1>Services</h1>
		</div>
		<c:url value="/login" var="loginUrl" />
		<form id="appointment-form" action="${loginUrl}" method="POST">
			<div class="form-group">
				<label for="make">Username</label> <input name="custom_username"
					class="form-control" />
			</div>
			<div class="form-group">
				<label for="model">Password</label> <input type="password"
					name="custom_password" class="form-control" />
			</div>
			<div class="form-group">
				<label for="make">Automobile Make</label> <input type="text"
					name="make" class="form-control" />
			</div>
			<sec:csrfInput />
			<c:if test="${param.error != null}">
				<p>Invalid username or password</p>
			</c:if>
			<c:if test="${param.logout != null}">
				<p>You have succesfully been logged out</p>
			</c:if>
			<button type="submit" id="btn-save" class="btn btn-primary">Login</button>
		</form>
	</div>
</body>
</html>