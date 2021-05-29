<%-- 
    Document   : confirmEmail
    Created on : May 15, 2021, 9:18:29 PM
    Author     : CND
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="hieubd.utils.MyConstants" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Confirm Email</h1>
        <p>Please, check your email to get the code, after ${MyConstants.TIME_OUT_CONFIRM_CODE} minutes, the code confirmation will expire</p>
        <a href="https://www.google.com/intl/vi/gmail/about/" target="_blank" >Go to Gmail</a>
        <form action="MainController" method="POST" style="margin-top: 20px">
            <table>
                <tbody>
                    <tr>
                        <td>
                            Code: 
                        </td>
                        <td>
                            <input type="hidden" name="email" value="${requestScope.email}" />
                            <input placeholder="Input confirm code" type="text" name="confirmCode" value="" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="submit" value="Confirm" name="btnAction" />
                        </td>
                        <td>
                            <input type="submit" value="send code again" name="btnAction" />
                        </td>
                    </tr>
                </tbody>
            </table>
            ${requestScope.error}
        </form>
    </body>
</html>
