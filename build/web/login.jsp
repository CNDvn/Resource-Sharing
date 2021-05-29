<%-- 
    Document   : login
    Created on : May 12, 2021, 2:47:25 PM
    Author     : CND
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Login</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <h1>Login</h1>
        <form action="MainController" method="POST">
            <table>
                <tbody>
                    <tr>
                        <td>Email: </td>
                        <td><input type="text" name="userID" value="" placeholder="email"/></td>
                    </tr>
                    <tr>
                        <td>Password: </td>
                        <td><input type="password" name="password" value="" placeholder="password"/></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <div class="g-recaptcha" data-sitekey="6Lcdm9EaAAAAANh8CbwBuEUfA2OHoZTjfOtdGiwq"></div>
                        </td>
                    </tr>
                    <tr>
                        <td><input type="submit" value="Login" name="btnAction" /></td>
                        <td><input type="reset" value="Reset" /></td>
                    </tr>
                    <tr>
                        <td>
                            <a href="https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8084/ResourceSharing/MainController?btnAction=LoginGoogle&response_type=code
                               &client_id=225507771210-07p34499hvol7l70ojv69di12fd1tja7.apps.googleusercontent.com&approval_prompt=force">Login With Google</a> 
                        </td>
                        <td> <a href="register.jsp">Register</a></td>
                    </tr>
                </tbody>
            </table>
            <!-- reCAPTCHA -->
        </form>
        <c:if test="${not empty requestScope.errorString}">
            <font color="red">${requestScope.errorString}</font>
        </c:if>
        <!-- reCAPTCHA with Auto language -->
        <script src="https://www.google.com/recaptcha/api.js"></script>
    </body>
</html>
