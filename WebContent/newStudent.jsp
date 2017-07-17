<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="styles.css" rel="stylesheet">
<title>Student Form</title>
</head>
<body>
  

	<h3>Students</h3>
	<form action="NewStudent.do" method="POST">
		First Name: <input type="text" name="firstName" value="First Name" /><br />
		Last Name: <input type="text" name="lastName" value="Last Name" /><br />
		Other Information: <input type="text" name="otherInformation"
			value="Other Information" /><br /> <input type="submit"
			value="Add Student" />
	</form>
	<form action="RemoveStudent.do" method="POST"></form>

	<c:choose>
		<c:when test="${! empty studentList}">
			<form action="GenerateRandomPair.do" method="GET">
				<input type="text" name="GenerateRandomPair" value="Group Size" />
				<input type="submit" value="Get Randomized Pairings!" />
			</form>
			${error}
			<ol>
				<c:forEach items="${studentList}" var="s">
					<li>${s.firstName}${s.lastName}${s.otherInformation}</li>
					<form action="RemoveStudent.do" method="POST">
						<input type="hidden" name="firstName" value="${s.firstName}" /> <input
							type="hidden" name="lastName" value="${s.lastName}" /><input
							type="hidden" name="otherInformation"
							value="${s.otherInformation}" /> <input type="submit"
							value="RemoveStudent" />
					</form>
				</c:forEach>
			</ol>
		</c:when>
		<c:otherwise>
		<c:choose>
		<c:when test="${! empty groupMap}">
			<form action="RandomizeAgain.do" method="GET">
				<input type="text" name="RandomizeAgain" value="Group Size" /> <input
					type="submit" value="Randomize Again" />
				<c:forEach items="${groupMap }" var="map">
					<c:forEach items="${map.value }" var="student">
				${student.lastName}, ${student.firstName}</br>
					</c:forEach>
					</br>
					</br>
				</c:forEach>
			</form>
		</c:when>
		</c:choose>
		</c:otherwise>
	</c:choose>

</body>
</html>