<%--
  User: Vladimir
  Date: 04.09.13
  Time: 10:36
  SIM QuickStart Methods
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>


<%@ page import="net.authorize.sim.*" %>
<%
    String apiUrl = "https://test.authorize.net/gateway/transact.dll";
    String apiLoginId = "7C2r3bW235";
    String transactionKey = "7C4CcR7m464876ht";
    String relayResponseUrl = "http://localhost:8080/relay_response_postanet";

    String cardNum = "4007000000027";
    String expDate = "1213";
    String amount = "1.99";

    Fingerprint fingerprint = Fingerprint.createFingerprint(
            apiLoginId,
            transactionKey,
            1234567890, // random sequence used for creating the finger print

            amount);
    long x_fp_sequence = fingerprint.getSequence();
    long x_fp_timestamp = fingerprint.getTimeStamp();
    String x_fp_hash = fingerprint.getFingerprintHash();
%>

<FORM NAME='formName' ID='formID' ACTION='<%=apiUrl%>'
      METHOD='POST'>
    <INPUT TYPE='HIDDEN' NAME='x_login' VALUE='<%=apiLoginId%>'>
    <INPUT TYPE='HIDDEN' NAME='x_fp_sequence' VALUE='<%=x_fp_sequence%>'>
    <INPUT TYPE='HIDDEN' NAME='x_fp_timestamp' VALUE='<%=x_fp_timestamp%>'>
    <INPUT TYPE='HIDDEN' NAME='x_fp_hash' VALUE='<%=x_fp_hash%>'>
    <INPUT TYPE='HIDDEN' NAME='x_version' VALUE='3.1'>
    <INPUT TYPE='HIDDEN' NAME='x_method' VALUE='CC'>
    <INPUT TYPE='HIDDEN' NAME='x_type' VALUE='AUTH_CAPTURE'>
    <INPUT TYPE='TEXT' NAME='x_amount' VALUE='<%=amount%>'>
    <INPUT TYPE='HIDDEN' NAME='x_show_form' VALUE='payment_form'>
    <INPUT TYPE='HIDDEN' NAME='x_test_request' VALUE='FALSE'>
    <INPUT TYPE='SUBMIT' NAME='submit_button' VALUE='Submit' CLASS='null'>
</FORM>


</body>
</html>