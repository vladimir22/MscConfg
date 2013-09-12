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
    <link href="resources/styles/payrequest.css" rel="stylesheet">
    <script src="resources/styles/jquery-1.9.1.js"></script>

    <script type="text/javascript">
        function sentAnetReq() {   //  show full command log
            $.ajax({
                type: "GET",
                url:  "<c:url value="/postanetAjax" />",
                data:   "anetApiUrl="+$("#anetApiUrl").val()+
                        "&anetApiLoginId="+$("#anetApiLoginId").val()+
                        "&anetTransactionKey="+$("#anetTransactionKey").val()+

                        "&ucharApiUrl="+$("#ucharApiUrl").val()+
                        "&ucharMerchantAccountCode="+$("#ucharMerchantAccountCode").val()+
                        "&ucharUserName="+$("#ucharUserName").val()+
                        "&ucharPassword="+$("#ucharPassword").val()+

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
                        "&value3="+$("#value3").val()+
                        "&name4="+$("#name4").val()+
                        "&value4="+$("#value4").val()+
                        "&name5="+$("#name5").val()+
                        "&value5="+$("#value5").val()+
                        "&name6="+$("#name6").val()+
                        "&value6="+$("#value6").val()+
                        "&name7="+$("#name7").val()+
                        "&value7="+$("#value7").val()+
                        "&name8="+$("#name8").val()+
                        "&value8="+$("#value8").val()+
                        "&name9="+$("#name9").val()+
                        "&value9="+$("#value9").val()+
                        "&name10="+$("#name10").val()+
                        "&value10="+$("#value10").val()+

                        "&makeRequestConvertation="+$("#makeRequestConvertation").prop('checked') +  // true or false
                        "&makeResponseConvertation="+$("#makeResponseConvertation").prop('checked')
                ,

                dataType: "html",    // <-- back from server
                beforeSend: function(){
                    $("#httpResponse").html('<img src="resources/styles/AjaxLoader.gif" /> Now loading...');
                },
                error: function(msg){
                    alert("fail");
                },
                success: function(result){
                    $("#httpResponse").html(result);
                }
            });
        };
    </script>

    <script type="text/javascript">
        function setBeginParams() {   // While NOT USED !!!
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
/*                $('#cardNum').val('4007000000027');
                $('#expDate').val('1213');*/
                $('#cardNum').val('4111111111111111');
                $('#expDate').val('1017');

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
            <td style="color:green">
                Parameter name
            </td>
            <td style="color:green">
                Parameter value
            </td>
        </tr>
        <tr>
            <td colspan="2" style=" color: #5bc0de">
                Merchant (SECURE) data
            </td>
        </tr>

        <%--ANET Data--%>
        <tr>
            <td>
                <p id="anetApiUrlTitle">anetApiUrl:</p>
            </td>
            <td>
                <input type='text' class='text' id='anetApiUrl' name='anetApiUrl' value='${anetApiUrl}'
                       size='65'  />
            </td>
        </tr>
        <tr>
            <td>
                <p id="anetApiLoginIdTitle">anetApiLoginId:</p>
            </td>
            <td>
                <input type='text' class='text' id='anetApiLoginId' name='anetApiLoginId' value='${anetApiLoginId}'
                       size='65'  />
            </td>
        </tr>
        <tr>
            <td>
                <p id="anetTransactionKeyTitle">anetTransactionKey:</p>
            </td>
            <td>
                <input type='text' class='text' id='anetTransactionKey' name='anetTransactionKey' value='${anetTransactionKey}'
                       size='65'  />
            </td>
        </tr>

        <%--UCHARGE Data--%>
        <tr>
            <td>
                <p id="ucharApiUrlTitle">ucharApiUrl:</p>
            </td>
            <td>
                <input type='text' class='text' id='ucharApiUrl' name='ucharApiUrl' value='${ucharApiUrl}'
                       size='65'  />
            </td>
        </tr>
        <tr>
            <td>
                <p id="ucharMerchantAccountCodeTitle">ucharMerchantAccountCode:</p>
            </td>
            <td>
                <input type='text' class='text' id='ucharMerchantAccountCode' name='ucharMerchantAccountCode' value='${ucharMerchantAccountCode}'
                       size='65'  />
            </td>
        </tr>
        <tr>
            <td>
                <p id="ucharUserNameTitle">ucharUserName:</p>
            </td>
            <td>
                <input type='text' class='text' id='ucharUserName' name='ucharUserName' value='${ucharUserName}'
                       size='65'  />
            </td>
        </tr>
        <tr>
            <td>
                <p id="ucharPasswordTitle">ucharPassword:</p>
            </td>
            <td>
                <input type='text' class='text' id='ucharPassword' name='ucharPassword' value='${ucharPassword}'
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
            <td colspan="2" style=" color: #5bc0de">
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
            <td colspan="2">
                <br>
            </td>
        </tr>
        <tr>
            <td colspan="2">

                <input type="checkbox" name="makeRequestConvertation" id="makeRequestConvertation"   value="makeRequestConvertation">Make Request Convertation
                <input type="checkbox" name="makeResponseConvertation" id="makeResponseConvertation"   value="makeResponseConvertation">Make Response Convertation

            </td>
        </tr>
        <tr>
            <td style=" color: #8a2be2">
                Transaction Kind:
            </td>
             <td>
                <%--<form:select path="requestKind" items="${requestKinds}" var="type">
                    <form:options/>
                </form:select>--%>
                    <select name="requestKindName" id="requestKindName" <%--onchange="setBeginParams()"--%>>
                       <c:forEach items="${requestKinds}" var="requestKind">
                           <option value="${requestKind.kindName}" ${requestKind.kindName == selectedRequestKind.kindName ? 'selected="selected"' : ''}>${requestKind.kindName}</option>
                        </c:forEach>
                    </select>
            </td>
        </tr>


    </table>
    </div>
<div style='width:500px; float: left; display: inline-block;' >
    <table>
        <tr>
            <td>
            <p style="color: #d59392">Name:</p>
            </td>
            <td>
                <p style="color: #d59392">Value       Additional(Overriding) params:</p>
            </td>
        </tr>
        <tr>
            <td>
              <input type='text' class='text' id='name1' name='name1' size='15' />
            </td>
            <td>
                <input type='text' class='text' id='value1' name='value1' size='65'   />
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
        <tr>
            <td>
                <input type='text' class='text' id='name4' name='name4' size='15'  />
            </td>
            <td>
                <input type='text' class='text' id='value4' name='value4' size='65'  />
            </td>
        </tr>
        <tr>
            <td>
                <input type='text' class='text' id='name5' name='name5' size='15'  />
            </td>
            <td>
                <input type='text' class='text' id='value5' name='value5' size='65'  />
            </td>
        </tr>
        <tr>
            <td>
                <input type='text' class='text' id='name6' name='name6' size='15'  />
            </td>
            <td>
                <input type='text' class='text' id='value6' name='value6' size='65'  />
            </td>
        </tr>
        <tr>
            <td>
                <input type='text' class='text' id='name7' name='name7' size='15'  />
            </td>
            <td>
                <input type='text' class='text' id='value7' name='value7' size='65'  />
            </td>
        </tr>
        <tr>
            <td>
                <input type='text' class='text' id='name8' name='name8' size='15'  />
            </td>
            <td>
                <input type='text' class='text' id='value8' name='value8' size='65'  />
            </td>
        </tr>
        <tr>
        <td>
            <input type='text' class='text' id='name9' name='name9' size='15'  />
        </td>
        <td>
            <input type='text' class='text' id='value9' name='value9' size='65'  />
        </td>
    </tr>
        <tr>
            <td>
                <input type='text' class='text' id='name10' name='name10' size='15'  />
            </td>
            <td>
                <input type='text' class='text' id='value10' name='value10' size='65'  />
            </td>
        </tr>
    </table>
    
</div>

</div>
    <div style="width:10%;clear:both; dispaly:inline-block;">
        <input type='button' onclick="sentAnetReq()" name="sendRequest" value="sendRequest" id="sendRequest"/>
    </div>

<div style="width:100%;display: inline-block; padding: 0 0 0 0;" >
    <div name="httpResponse" id="httpResponse"></div>
</div>
</div>
</body>
</html>