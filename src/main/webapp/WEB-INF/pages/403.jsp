
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>
<body>
<spring:message code="label.pageNoAccess" />
<p><a href="/"><spring:message code="label.mainPage" /></a></p>
<p><a href="/login"><spring:message code="href.pageLogin" /></a></p>
</body>
</html>