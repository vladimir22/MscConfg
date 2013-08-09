<%--
  Created by IntelliJ IDEA.
  User: win7srv
  Date: 01.07.13
  Time: 12:20
  Commands page- in jquery make cmdName & params(object), send via ajax , print response
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<html>
<head>
    <meta charset="utf-8">
    <title><spring:message code="href.pageCmd"/></title>

    <link href="resources/styles/bootstrap.css" rel="stylesheet">
    <link href="resources/styles/bootstrap-responsive.css" rel="stylesheet">
    <script src="resources/styles/jquery-1.9.1.js"></script>

    <script type="text/javascript">
        function doCmdAjaxPost(cmd) {
            var num = $("#param").val();
            var cmdTest = $("#cmdTest").find("option:selected").val();
            var object = {cmdName: cmd, number: num, cmdTest: cmdTest};
            var htmlStr = JSON.stringify(object);     // serialize , in Spring Object implemets Serializable is a must !!! (also check Jakson lib version - serialization jar)
            /*alert("Send to server .ajax:" + htmlStr);*/

            $.ajax({
                type: "POST",
                url:  "<c:url value="/cmdRecieve" />",
                data:  htmlStr,        // data: object   --> not work (400-The request sent by the client was syntactically incorrect) !!!
                dataType: "html",    // <-- back from server
                contentType: "application/json; charset=utf-8",
                beforeSend: function(){
                    $("#cmdAjaxResponse").html('<img src="resources/styles/AjaxLoader.gif" /> Now loading...');
                },
                error: function(msg){
                    alert("fail");
                },
                success: function(result){
                    $("#cmdAjaxResponse").html(result);

                }
            });
        };
    </script>


    <script>
        function prepareCmd(commandHeader, paramName, btnName, cmdName) {
            $("#commandHeader").text(commandHeader);
            $("#paragraph").text(paramName);     /* $("#thebutton span").text("My NEW Text"); - chage via span*/
            $("#cmdBtn").prop('value', btnName);

            var func = "doCmdAjaxPost('"+cmdName.toString()+"')";
            $("#cmdBtn").attr('onclick', func );
           /* alert("prepareCmd  cmdBtn.onclick =" + func);*/
            $('.cmdTab td').show();
        }
    </script>

    <script>        // When start up Hide Cmd Table (realization via css)
        $(document).ready(function(){
            $('.cmdTab td').hide();});
    </script>

    <script>
        function prepareComboCmd(){

            /*var obj = jQuery.parseJSON('{"name":"John"}');
            alert( obj.name );*/
            var selval = $("#cmdSelect").find("option:selected").val();
            /*alert( selval );*/
            var selobj = jQuery.parseJSON(selval);
           /* alert( selobj.commandHeader );*/


            $("#commandHeader").text(selobj.commandHeader);
            $("#paragraph").text(selobj.paramName);     /* $("#thebutton span").text("My NEW Text"); - chage via span*/
            $("#cmdBtn").prop('value', selobj.btnName);

            var func = "doCmdAjaxPost('"+selobj.cmdName.toString()+"')";
            $("#cmdBtn").attr('onclick', func );
            /* alert("prepareCmd  cmdBtn.onclick =" + func);*/
            $('.cmdTab td').show();



           /* var selval = $("#cmdSelect").find("option:selected").val();
            var selvalObj = $.parseJSON("{'commandHeader':'QQQ'}");
            alert("prepareComboCmd!!! :"+selval);
            alert("selvalObj.commandHeader :"+ selvalObj.commandHeader );*/
        }

    </script>
</head>
<body>
<table>
    <tr style="vertical-align: top">
        <td>
            <b id="selectHeader" name ="selectHeader"  style="text-align: center;  vertical-align: middle;  color:#FFCC00 "> Выберете Команду:</b>
            <br>
            <%--<button name="cmdMaker" onclick="prepareCmd('Поиск абонента','MSISDN:','Найти','VsubCmd')" id="cmdMaker">Сформировать Команду</button>--%>

              <select id="cmdSelect" onchange="prepareComboCmd()" name="cmdSelect" multiple="multiple">
               <%--Very carefull sintacsis : '{"11":"11","22":"22"}'--%>
                <option value='{"commandHeader":"Поиск абонента","paramName":"MSISDN:","btnName":"Найти","cmdName":"VsubCmd"}'>Найти Абонента</option>
                <option value='{"commandHeader":"Тестовая Команда","paramName":"AnyParam:","btnName":"Выполнить","cmdName":"TempCmd"}'>Тест Команда</option>
                   <option value='{"commandHeader":"Неизвестная Команда","paramName":"","btnName":"ЁЖЫКinTheFr@g","cmdName":"UnknownCmd"}'>Неизвестная Команда</option>
              </select>
            <div name="comboDiv" id="comboDiv"></div>
        </td>
        <td>
            <table class="cmdTab" >
                <tr>
                    <td colspan="2"  style="text-align: center;  vertical-align: middle; color:#FFCC00 ">
                        <b id="commandHeader" name ="commandHeader" ></b>
                    </td>
                    <td>
                        <b id="responseHeader" name ="responseHeader"  style="text-align: center;  vertical-align: middle;  color:#FFCC00 "> Ответ :</b>
                    </td>
                </tr>
                <tr style="vertical-align: top">
                    <td style="text-align: center;">
                        <b id="paragraph" class="controls"></b>


                        <input type="text" size="20" name="param" style="height: 25px; width: 120px"  id="param">
                        <input type='button' onclick="doCmdAjaxPost('VsubCmd')" name="cmdBtn" id="cmdBtn" class="btn btn-danger btn-mini"  style="height: 25px; width: 80px"/>
                        <%--<button id="thebutton">
                            <span class="ui-button-text">My Text</span>
                        </button>--%>
                    </td>
                    <td>
                         <b id="testparagraph" class="controls">TestCmd:</b>

                        <select id="cmdTest" style="height: 27px; width: 70px">
                            <option>true</option>
                            <option>false</option>
                        </select>
                    </td>
                    <td>
                        <div name="cmdAjaxResponse" id="cmdAjaxResponse"></div>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>

</body>
</html>