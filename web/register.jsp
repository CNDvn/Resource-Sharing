<%-- 
    Document   : register
    Created on : May 13, 2021, 11:26:40 AM
    Author     : CND
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register Page</title>
    </head>
    <body>
        <style>
            body{
                font-family: sans-serif;
                font-weight: 500;
            }
            .parameter{
                width: 400px;
                height: 20px;
            }
            .phone{
                width: 150px;
            }
            .address{
                height: 50px;
            }
        </style>
        <h1>Register</h1>

        <form action="MainController" method="POST">
            <table>
                <tbody>
                    <tr>
                        <td>email: </td>
                        <td>
                            <input class="parameter" type="email" name="email" value="" minlength="6" maxlength="40" />*<br/>
                            <font color="green">6 -> 40 character</font>
                        </td>
                        <td>
                            <font color="red">${requestScope.error.emailErr}</font>
                        </td>
                    </tr>
                    <tr>
                        <td>Password: </td>
                        <td>
                            <input class="parameter" type="password" name="password" value="" minlength="6" maxlength="50" />*<br/>
                            <font color="green">6 -> 50 character</font></td>
                        <td>
                            <font color="red">${requestScope.error.passwordErr}</font>
                        </td>
                    </tr>
                    <tr>
                        <td>name:</td>
                        <td>
                            <input class="parameter" type="text" name="name" value="" maxlength="50" />*<br/>
                            <font color="green">limit 50 character</font>
                        </td>
                        </td>
                        <td>
                            <font color="red">${requestScope.error.nameErr}</font>
                        </td>
                    </tr>
                    <tr>
                        <td>phone:</td>
                        <td>
                            <input class="parameter phone" type="tel" name="phone" value="" minlength="10" maxlength="13" />
                        </td>
                        <td>
                            <font color="red">${requestScope.error.phoneErr}</font>
                        </td>
                    </tr>
                    <tr>
                        <td>address:</td>
                        <td>
                            <textarea class="parameter address"  type="text" name="address" maxlength="100" value="" ></textarea><br/>
                            <font color="green">limit 100 character</font>

                        </td>
                    </tr>
                    <tr>
                        <td><input type="submit" name="btnAction" value="Register" /></td>
                        <td><input type="reset" value="Reset" /></td>
                    </tr>
                </tbody>
            </table>
            <a href="login.html">Login</a>
        </form>

    </body>
</html>
