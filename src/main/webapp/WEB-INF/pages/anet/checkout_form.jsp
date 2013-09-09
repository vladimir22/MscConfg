<%--

  User: Vladimir Pronkin
  Date: 02.09.13
  Time: 12:50

--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html lang='en'>
<head>
    <title>testing</title>
</head>
<body>
<%@ page import="net.authorize.sim.*"%>
<%
    String apiLoginId = "7C2r3bW235";
    String transactionKey = "7C4CcR7m464876ht";
    String relayResponseUrl = "http://localhost:8080/relay_response/";
    String amount = "1.99";
    String testCard= "4007000000027";
    String expDate= "1213";
    Fingerprint fingerprint = Fingerprint.createFingerprint(apiLoginId,
            transactionKey,
            1234567890, "1.99");
    long x_fp_sequence = fingerprint.getSequence();
    long x_fp_timestamp = fingerprint.getTimeStamp();
    String x_fp_hash = fingerprint.getFingerprintHash();
%>
<form id='secure_redirect_form_id' action='https://test.authorize.net/
gateway/transact.dll' method='POST'>
    <label for='x_card_num'>Credit Card Number</label>
    <input type='text' class='text' id='x_card_num' name='x_card_num' value='<%=testCard%>'
           size='20' maxlength='16' />
    <br />
    <label for='x_exp_date'>Expiration Date(Ex: 1213)</label>
    <input type='text' class='text' id='x_exp_date' value='<%=expDate%>' name='x_exp_date' size='6'
           maxlength='6'/>
    <br />
    <label for='x_amount'>Amount(Ex: 1.99)</label>
    <input type='text' class='text' id='x_amount' name='x_amount' size='10'
           maxlength='10' readonly='readonly' value='<%=amount%>' />
    <br />
    <input type='hidden' name='x_invoice_num'
           value='<%=System.currentTimeMillis()%>' />
    <input type='hidden' name='x_relay_url' value='<%=relayResponseUrl%>' />
    <input type='hidden' name='x_login' value='<%=apiLoginId%>' />
    <input type='hidden' name='x_fp_sequence' value='<%=x_fp_sequence%>' />
    <input type='hidden' name='x_fp_timestamp' value='<%=x_fp_timestamp%>' />
    <input type='hidden' name='x_fp_hash' value='<%=x_fp_hash%>' />
    <input type='hidden' name='x_version' value='3.1' />
    <input type='hidden' name='x_method' value='CC' />
    <input type='hidden' name='x_type' value='AUTH_CAPTURE' />
    <input type='hidden' name='x_amount' value='<%=amount%>' />
    <input type='hidden' name='x_test_request' value='TRUE' />
    <input type='hidden' name='notes' value='extra hot please' />
    <input type='submit' name='buy_button' value='BUY' />
</form>
</body>