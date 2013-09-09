<%--
  User: Vladimir
  Date: 02.09.13
  Time: 17:27
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <title></title>
    <link href="resources/styles/cmd.css" rel="stylesheet">
    <script src="resources/styles/jquery-1.9.1.js"></script>

    <script type="text/javascript">
        function sentAnetReq() {   //  show full command log
            $.ajax({
                type: "GET",
                url:  "<c:url value="/postanetAjax" />",
                data: "apiUrl="+$("#apiUrl").val()+
                        "&apiLoginId="+$("#apiLoginId").val()+
                        "&transactionKey="+$("#transactionKey").val()+
                        "&relayResponseUrl="+$("#relayResponseUrl").val()+

                        "&cardNum="+$("#cardNum").val()+
                        "&expDate="+$("#expDate").val()+
                        "&amount="+$("#amount").val()+
                        "&requestKindName="+$("#requestKindName").find("option:selected").val()+

                        "&name1="+$("#name1").val()+
                        "&value1="+$("#value1").val()+
                        "&name2="+$("#name2").val()+
                        "&value2="+$("#value2").val()+
                        "&name3="+$("#name3").val()+
                        "&value3="+$("#value3").val()
                        
                
                
                
                ,

                dataType: "html",    // <-- back from server
                beforeSend: function(){
                    $("#anetResponse").html('<img src="resources/styles/AjaxLoader.gif" /> Now loading...');
                },
                error: function(msg){
                    alert("fail");
                },
                success: function(result){
                    $("#anetResponse").html(result);
                }
            });
        };
    </script>

    <script type="text/javascript">
        function setBeginParams() {
            var  requestKindName = $("#requestKindName").find("option:selected").val() ;

              if(requestKindName.indexOf("UCHARGE")!=-1){
                  $('#apiUrlTitle').text('UniCharge gateway:');
                  $('#apiUrl').val("https://sandbox.unipaygateway.com/gates/httpform");

                  $('#apiLoginIdTitle').text('Merchant Account Code:');
                  $('#apiLoginId').val('10001');

                  $('#transactionKey').val('NOT NEEDED');
                  $('#relayResponseUrl').val('NOT NEEDED');

                  if (requestKindName.indexOf("UCHARGE:CARD_PRESENT:")!=-1){
                      $('#cardNumTitle').text('Card TrackData:');
                      $('#cardNum').val('%B5499740000000057^Smith/John^13121011000 1111A123456789012?');
                      $('#expDate').val('NOT NEEDED');
                  } else   {
                      $('#cardNumTitle').text('Card Number');
                      $('#cardNum').val('4111111111111111');
                      $('#expDate').val('1017');
                  }

                  $('#amountTitle').text('amount(UCHARGE:Cents):');
                  $('#amount').val('100');
              }
            if(requestKindName.indexOf("ANET")!=-1){
                $('#apiUrlTitle').text('AuthNet gateway:');
                $('#apiUrl').val("https://test.authorize.net/gateway/transact.dll");

                $('#apiLoginIdTitle').text('API Login ID:');
                $('#apiLoginId').val('7C2r3bW235');

                $('#transactionKey').val('7C4CcR7m464876ht');
                $('#relayResponseUrl').val('http://localhost:8080/relay_response_postanet');

                $('#cardNumTitle').text('Card Number');
                $('#cardNum').val('4007000000027');
                $('#expDate').val('1213');

                $('#amountTitle').text('amount(ANET:Dollars.Cents):');
                $('#amount').val('1.01');

            }

        }

    </script>



</head>
<body>
<div style="width:100%;">
<div style=" width:610px; float: left; display: inline-block;">
    <table>
        <tr>
            <td style="text-align: center; color:green">
                Parameter name
            </td>
            <td style="text-align: center;color:green">
                Parameter value
            </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: center; color: #8a2be2">
                Merchant (SECURE) data
            </td>
        </tr>
        <tr>
            <td>
                <p id="apiUrlTitle">apiUrl:</p>
            </td>
            <td>
                <input type='text' class='text' id='apiUrl' name='apiUrl' value='${apiUrl}'
                       size='65'  />
            </td>
        </tr>
        <tr>
            <td>
                <p id="apiLoginIdTitle">apiLoginId:</p>
            </td>
            <td>
                <input type='text' class='text' id='apiLoginId' name='apiLoginId' value='${apiLoginId}'
                       size='65'  />
            </td>
        </tr>
        <tr>
            <td>
                <p id="transactionKeyTitle">transactionKey: </p>
            </td>
            <td>
                <input type='text' class='text' id='transactionKey' name='transactionKey' value='${transactionKey}'
                       size='65'  />
            </td>
        </tr>
        <tr>
            <td>
                relayResponseUrl:
            </td>
            <td>
                <input type='text' class='text' id='relayResponseUrl' name='relayResponseUrl' value='${relayResponseUrl}'
                       size='65'  />
            </td>
        </tr>

        <tr>
            <td colspan="2" style="text-align: center; color: blue">
                Client data
            </td>
        </tr>

        <tr>
            <td>
            <p id="cardNumTitle">cardNum:</p>
            </td>
            <td>
                <input type='text' class='text' id='cardNum' name='cardNum' value='${cardNum}'
                       size='65'  />
            </td>
        </tr>
        <tr>
            <td>
                <p id="expDateTitle">   expDate:</p>
            </td>
            <td>
                <input type='text' class='text' id='expDate' name='expDate' value='${expDate}'
                       size='65'  />
            </td>
        </tr>
        <tr>
            <td>
                <p id="amountTitle"> amount(Dollars.Cents):</p>
            </td>
            <td>
                <input type='text' class='text' id='amount' name='amount' value='${amount}'
                       size='65'  />
            </td>
        </tr>
        <tr>
            <td>
                Transaction Kind:
            </td>
            <td>
                <%--<form:select path="requestKind" items="${requestKinds}" var="type">
                    <form:options/>
                </form:select>--%>
                    <select name="requestKindName" id="requestKindName" onchange="setBeginParams()">
                       <c:forEach items="${requestKinds}" var="requestKind">
                           <option value="${requestKind.kindName}" ${requestKind.kindName == selectedRequestKind.kindName ? 'selected="selected"' : ''}>${requestKind.kindName}</option>
                        </c:forEach>
                    </select>

            </td>
        </tr>


    </table>
    </div>
<div style='width:500px; float: left; display: inline-block;' >
    <p style="color: #a52a2a; padding-left: 50px"> Additional(Overriding) params:</p>
    <table>
        <tr>
            <td>
            <p style="color: #d59392">Name:</p>
            </td>
            <td>
                <p style="color: #d59392">Value</p>
            </td>
        </tr>
        <tr>
            <td>
              <input type='text' class='text' id='name1' name='name1' size='15'  />
            </td>
            <td>
                <input type='text' class='text' id='value1' name='value1' size='65'  />
            </td>
        </tr>
        <tr>
            <td>
                <input type='text' class='text' id='name2' name='name2' size='15'  />
            </td>
            <td>
                <input type='text' class='text' id='value2' name='value2' size='65'  />
            </td>
        </tr>
        <tr>
            <td>
                <input type='text' class='text' id='name3' name='name3' size='15'  />
            </td>
            <td>
                <input type='text' class='text' id='value3' name='value3' size='65'  />
            </td>
        </tr>
    </table>
    
</div>

</div>
    <div style="width:10%;clear:both; dispaly:inline-block;">
        <input type='button' onclick="sentAnetReq()" name="sendRequest-->" value="sendRequest" id="sendRequest"/>
    </div>

<div style="width:100%;display: inline-block; padding: 0 0 0 0;" >
    <div name="anetResponse" id="anetResponse"></div>
</div>
</div>
</body>
</html>