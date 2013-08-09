<?xml version="1.0" encoding="UTF-8" ?>
<!doctype html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8"%>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="resources/styles/header.css" rel="stylesheet">
</head>


<body>

<div class="logged-username">
    <p>
        <span class="user-name-style"><spring:message code="label.userFirstName"/> :</span>
        <span class="user-name"> <c:out value="${sessionScope.loggedUserMap['firstName']}" /></span>

    </p>

    <p>
        <span class="user-name-style"><spring:message code="label.userSecondName"/> :</span>
        <span class="user-name"> <c:out value="${sessionScope.loggedUserMap['lastName']}" /></span>
    </p>
</div>
</body>
</html>
