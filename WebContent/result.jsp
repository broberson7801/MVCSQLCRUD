<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Dummy Result Page</title>
</head>
<body>
<form action="studentAdded.do" method="GET">
	<c:choose>
		<c:when test="${! empty studentList}">
				<<c:forEach items="${studentList}" var="s">
					<li>${s.firstName} ${student.lastName} ${s.otherInformation}</li>
					</c:forEach>
		</c:when>
		<c:otherwise>
			<p>No Students</p>
		</c:otherwise>
	</c:choose>
	</form>
</body>
</html> 