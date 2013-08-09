<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
    <meta charset="utf-8">
    <title>Main page</title>

</head>
<body>
<h2><spring:message code="label.mainPageTitle" /></h2>
<br>
<p><a href="dataPage"><spring:message code="href.pageDataDB"/></a></p>
<p><a href="cmdPage"><spring:message code="href.pageCmd"/></a></p>
</body>
</html>
