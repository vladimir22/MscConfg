<%--

  User: win7srv
  Date: 06.08.13
  Time: 8:56
  Шаблон страницы кот. используется в tiles.xml
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="resources/styles/template.css" rel="stylesheet">
    <%--<title> Подхватит из боди.jsp </title>--%>
</head>
<body>
<div class="header">
    <tiles:insertAttribute name="header" />
</div>

<div class="middle">
    <div class="menu">
        <tiles:insertAttribute name="menu" />
    </div>
    <div class="body">
        <tiles:insertAttribute name="body" />
    </div>
</div>

<div class="footer">
    <tiles:insertAttribute name="footer" />
</div>


</body>
</html>