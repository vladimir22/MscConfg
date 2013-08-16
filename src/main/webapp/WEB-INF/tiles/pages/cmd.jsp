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

    <%--<link href="resources/styles/bootstrap.css" rel="stylesheet">--%>
    <%--<link href="resources/styles/bootstrap-responsive.css" rel="stylesheet">--%>

    <link href="resources/styles/cmd.css" rel="stylesheet">
    <script src="resources/styles/jquery-1.9.1.js"></script>

    <script type="text/javascript">
        function doCmdAjaxPost(cmd) {    // send POST request with AjaxObject
            $("#cmdAjaxResponse").empty();                   // $("#thebutton span").text("My NEW Text"); - chage via span
            $("#cmdFullText").empty();
            var num = $("#param").val();
            var cmdTest="";
            if($('input[name=TestMode]').is(':checked')){
                cmdTest="true"
            }   else {
                cmdTest="false"
            }

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
                    $(".cmdAjaxResponse").html('<img src="resources/styles/AjaxLoader.gif" /> Now loading...');
                },
                error: function(msg){
                    alert("fail");
                },
                success: function(result){
                    $(".cmdAjaxResponse").html(result);
                    $(".cmd-response").show();
                }
            });
        };
    </script>



   <%-- <script>
        $(document).ready(function(){         // When start up Hide Cmd Table (realization via css)
            $(".show-hide").hide();
            $(".show-hide").hide();
            $(".show-hide").hide();
        });
    </script>--%>

    <script type="text/javascript">
        function prepareComboCmd(){       // Prepare cmd view
            /*Clear old cmd out*/
           $("#cmdAjaxResponse").empty();                   // $("#thebutton span").text("My NEW Text"); - chage via span
           $("#cmdFullText").empty();
           $("#param").val("");
           $(".cmd-response").hide();

            $('.show-hide').show();

            var selval = $("#cmdSelect").find("option:selected").val();
            var selobj = jQuery.parseJSON(selval);              //var obj = jQuery.parseJSON('{"name":"John"}');

            $("#commandHeader").text(selobj.commandHeader);
            $("#paragraph").text(selobj.paramName);     /* $("#thebutton span").text("My NEW Text"); - chage via span*/
            $("#cmdBtn").prop('value', selobj.btnName);

            var func = "doCmdAjaxPost('"+selobj.cmdName.toString()+"')";
            $("#cmdBtn").attr('onclick', func );




        }
    </script>

    <script type="text/javascript">
        function showFullText() {   //  show full command log
           $.ajax({
              type: "GET",
                url:  "<c:url value="/cmdPage/getFullText" />",
               /* data:  cmdName.toString(),*/
                dataType: "html",    // <-- back from server
                beforeSend: function(){
                    $("#cmdFullText").html('<img src="resources/styles/AjaxLoader.gif" /> Now loading...');
                },
                error: function(msg){
                    alert("fail");
                },
                success: function(result){
                    $("#cmdFullText").html(result);
                }
            });
        };
    </script>
</head>
<body>

<div class="cmd-view">

    <div class="div-title" > <span> Выберете Команду:</span>  </div>
    <div class="div-body" >
    <select id="cmdSelect" onchange="prepareComboCmd()" name="cmdSelect" multiple="multiple">
        <%--Very carefull sintacsis : '{"11":"11","22":"22"}'--%>
        <option value='{"commandHeader":"Поиск абонента","paramName":"MSISDN:","btnName":"Найти","cmdName":"VsubCmd"}'>Найти Абонента</option>
        <option value='{"commandHeader":"Тестовая Команда","paramName":"AnyParam:","btnName":"Выполнить","cmdName":"TempCmd"}'>Тест Команда</option>
        <option value='{"commandHeader":"Неизвестная Команда","paramName":"","btnName":"ЁЖЫКinTheFr@g","cmdName":"UnknownCmd"}'>Неизвестная Команда</option>
    </select>
    </div>
</div>

<div  class="show-hide cmd-view ">
    <div class="div-title" > <span id="commandHeader" name ="commandHeader" ></span>  </div>

    <div class="div-body">
      <p id="paragraph" class="cmd-text" ></p>
      <input type="text" size="20" name="param" style="height: 25px; width: 120px"  id="param">
      <input type='button' onclick="doCmdAjaxPost('VsubCmd')" name="cmdBtn" id="cmdBtn" class="href-button red-color"/>

        <div class="clear-float test-div">
            <p>TestMode</p>
            <input type="checkbox"  name="TestMode" value="TestMode" checked/>
        </div>



    </div>
</div>

<div class="show-hide cmd-fulltext">
    <%-- <div class="div-title" ><p> Ответ :</p>  </div>--%>
    <div class="div-body">
        <div name="cmdFullText" id="cmdFullText"></div>
    </div>
</div>

<div class="cmd-response div-shadow-layer1">
    <div class="div-title" > <span> Ответ :</span>  </div>
    <div class="div-body">
        <div name="cmdAjaxResponse" class="cmdAjaxResponse"></div>
    </div>
</div>
<div class="cmd-response div-shadow-layer2">
    <div class="div-title" > <span> Ответ :</span>  </div>
    <div class="div-body">
        <div name="cmdAjaxResponse" class="cmdAjaxResponse"></div>
    </div>
</div>






</body>
</html>