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
    <script src="/resources/jquery-1.9.1.js"></script>

    <script type="text/javascript">
        function helloAjax(cmdname) {     // Ajax Get request example (send params)
            $.ajax({
                url : "<c:url value="/helloajax" />",
                data : {cmdname : cmdname, istest :$("#istest").find("option:selected").val()},
                beforeSend: function(){
                    $("#ajaxResponse").html('<img src="/resources/AjaxLoader.gif" /> Now loading...');
                },
                success : function(result) {
                    $("#ajaxResponse").html(result);	//result - response from server (Spring method return)
                },
                error : function(result) {
                    $('#ajaxResponse').set("Ajax response error:" + result.responseText);

                }
            });

        }
    </script>

    <script type="text/javascript">
        function doAjaxPost(cmd) {       // Ajax Post request example (Send new Object)
            var num = $("#msisdn").val();
            var object = {cmdName: cmd,number: num};
            var htmlStr = JSON.stringify(object);     // serialize , in Spring Object implemets Serializable is a must !!! (also check Jakson lib version - serialization jar)
            alert(".ajax:" + htmlStr);

            $.ajax({
                type: "POST",
                url:  "<c:url value="/AddUser" />",
                data:  htmlStr,        // data: object   --> not work (400-The request sent by the client was syntactically incorrect) !!!
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                error: function(msg){
                    alert("fail");
                },
                success: function(msg){
                    alert(JSON.stringify(msg));
                }
            });
        };
    </script>

</head>

<body>

<div class="container">
    <form action="testBtn/false" method="post"><input type="submit" class="btn btn-danger btn-mini" value="CmdToSSH"/></form>
    <form action="testBtn/true" method="post"><input type="submit" class="btn btn-danger btn-mini" value="CmdTest"/></form>

    <table >
        <tr style="vertical-align: top">
            <td>
                <button onclick="helloAjax('tempcmd')" class="btn btn-danger btn-mini"  style="height: 25px; width: 80px">TempCmd</button>
                <button onclick="helloAjax('vsubcmd')" class="btn btn-danger btn-mini"  style="height: 25px; width: 80px">VsubCmd</button>
                <!-- <a onclick="helloAjax()">Ajax Say Hello</a>  -->
           </td>
            <td>
                isTest =
                <select id="istest" style="height: 25px; width: 80px">
                    <option>true</option>
                    <option>false</option>
                </select>
            </td>
            <td>
                <div name="ajaxResponse" id="ajaxResponse"></div>
            </td>
        </tr>
        <tr>
            <td>



                <b>Номер:</b>
                <input type="text" size="20" name="msisdn"  id="msisdn">

                <button onclick="doAjaxPost('VsubCmd')" class="btn btn-danger btn-mini"  style="height: 25px; width: 80px">Search</button>

            </td>
            <td>
                TestCmd =
                <select id="testvsub" style="height: 25px; width: 80px">
                    <option>true</option>
                    <option>false</option>
                </select>
            </td>

        </tr>
        <tr>

        </tr>

        city
    </table>



    <div class="row">
        <div class="span8 offset2">
            <h1>Users</h1>
            <form:form method="post" action="add" commandName="user" class="form-horizontal">
            <div class="control-group">
                <form:label cssClass="control-label" path="firstName">First Name:</form:label>
                <div class="controls">
                    <form:input path="firstName"/>
                </div>
            </div>
            <div class="control-group">
                <form:label cssClass="control-label" path="lastName">Last Name:</form:label>
                <div class="controls">
                    <form:input path="lastName"/>
                </div>
            </div>
            <div class="control-group">
                <form:label cssClass="control-label" path="email">Email:</form:label>
                <div class="controls">
                    <form:input path="email"/>
                </div>
            </div>
            <div class="control-group">
                <div class="controls">
                    <input type="submit" value="Add User" class="btn"/>
                    </form:form>
                </div>
            </div>

            <c:if test="${!empty users}">
                <h3>Users</h3>
                <table class="table table-bordered table-striped">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Email</th>
                        <th>&nbsp;</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${users}" var="user">
                        <tr>
                            <td>${user.lastName}, ${user.firstName}</td>
                            <td>${user.email}</td>
                            <td>
                                <form action="delete/${user.id}" method="post"><input type="submit" class="btn btn-danger btn-mini" value="Delete"/></form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </div>

      <!--  MgwData -->

    <div class="row">
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