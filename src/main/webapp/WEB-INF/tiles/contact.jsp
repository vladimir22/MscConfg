<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
	<title>Spring 3 MVC Series - Contact Manager</title>
</head>
<body>
<h2>Contact Manager</h2>

<form:form method="post" action="addmgw.html"  class="form-horizontal">
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

</body>
</html>
