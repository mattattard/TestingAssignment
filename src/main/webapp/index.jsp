<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
</head>
    <body>
        <form action = "${pageContext.request.contextPath}/index" method = "POST">
            Name: <input type = "text" name = "name">
            <br>
            Address: <input type = "text" name = "address">
            <br>
            Card Number: <input type = "text" name = "card">
            <br>
            Card Type: <select name="cardType">
                <option value="Visa">Visa</option>
                <option value="American Express">American Express</option>
                <option value="MasterCard">MasterCard</option>
            </select>
            <br>
            Expiry Date: <input type = "text" name = "expiry">
            <br>
            CVV Code: <input type = "text" name = "cvv">
            <br>
            Amount: <input type = "text" name = "amount">
            <br>
            <input type = "submit" value = "Submit" name="submit"/>
        </form>
    </body>
</html>
