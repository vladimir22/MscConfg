<?xml version="1.0" encoding="UTF-8" ?>
<!doctype html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8"%>

<html>
<head>
    <meta charset="utf-8">
    <title>MscConfig Application</title>

    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link href="/resources/bootstrap.css" rel="stylesheet">
    <link href="/resources/bootstrap-responsive.css" rel="stylesheet">
    <link href="/resources/customStyle.css" rel="stylesheet">
    <script src="/resources/jquery-1.9.1.js"></script>

</head>

<body>

<div class="container">

      <!--  MgwData -->

    <div class="row">
        <div class="href-main-page" style=" position:absolute; left:10px; top:10px; text-decoration-color:grey; ">
            <p><a href="/"><spring:message code="label.mainPage" /></a></p>
        </div>
        <div class="span8 offset2">
            <h1>Данные MGW</h1>
            <form:form method="post" action="addMgwData" commandName="mgwData" class="form-horizontal">
            <div class="control-group">
                <form:label cssClass="control-label" path="mgw_id">mgw_id:</form:label>
                <div class="controls">
                    <form:input path="mgw_id"/>
                </div>
            </div>
            <div class="control-group">
                <form:label cssClass="control-label" path="name">name:</form:label>
                <div class="controls">
                    <form:input path="name"/>
                </div>
            </div>
            <div class="control-group">
                <form:label cssClass="control-label" path="ip">ip:</form:label>
                <div class="controls">
                    <form:input path="ip"/>
                </div>
            </div>
            <div class="control-group">
                <div class="controls">
                    <input type="submit" value="Add MgwData" class="btn"/>
                    </form:form>
                </div>
            </div>

            <c:if test="${!empty mgwDatas}">
                <h3>MgwDatas</h3>
                <table class="table table-bordered table-striped">
                    <thead>
                    <tr>
                        <th>mgw_id</th>
                        <th>name</th>
                        <th>ip</th>
                        <th>&nbsp;</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${mgwDatas}" var="mgwData">
                        <tr>
                            <td>${mgwData.mgw_id}, ${mgwData.name}</td>
                            <td>${mgwData.ip}</td>
                            <td>
                                <form action="deleteMgwData/${mgwData.mgw_id}" method="post"><input type="submit" class="btn btn-danger btn-mini" value="Delete"/></form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </div>

</div>

</body>
</html>