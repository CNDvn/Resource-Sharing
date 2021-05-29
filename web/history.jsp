<%-- 
    Document   : history
    Created on : May 18, 2021, 7:55:03 PM
    Author     : CND
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="hieubd.utils.MyConstants" %>
<!DOCTYPE html>
<c:set var="user" value="${sessionScope.user}" />
<c:if test="${empty user or (user.roleID != MyConstants.ROLE_EMPLOYEE)}">
    ${pageContext.response.sendRedirect("login.html")}
</c:if>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Request History Page</title>
    </head>
    <body>
        <c:if test="${not empty user.name}">
            Welcome: <font color="green">${user.name}</font>
            <button><a href="MainController?btnAction=Logout">Logout</a></button>
        </c:if>

        <!--Begin Search bar-->
        <div style="margin: 30px">
            <form action="MainController" style="display: inline; margin-right: 50%">
                <input type="date" name="searchByDate" value="${param.searchByDate}"/>
                <input type="text" name="searchByName" value="${param.searchByName}" placeholder="Search by name"/>
                <button type="submit" name="btnAction" value="History">Search</button>
            </form>
            <button><a href="MainController?btnAction=Search">Search Page</a></button>
        </div>
        <!--End Search bar-->

        <c:set var="listRequest" value="${requestScope.listRequest}" />
        <c:set var="listResource" value="${requestScope.listResource}" />
        <c:if test="${ not empty requestScope.searchNotValue}">
            <font color="red">${requestScope.searchNotValue}</font>
        </c:if>
        <c:if test="${not empty listRequest}">
            <table border="1">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Name</th>
                        <th>Status</th>
                        <th>Date Booking</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="request" items="${listRequest}" varStatus="counter">
                        <tr>
                            <td>${counter.count}</td>
                            <td>
                                <c:forEach var="item" items="${listResource}" >
                                    <c:if test="${item.resourceID == request.resourceID}">
                                        ${item.name}
                                    </c:if>
                                </c:forEach>
                            </td>
                            <td>
                                <c:if test="${request.statusID == MyConstants.STATUS_NEW}">
                                    Waiting
                                </c:if>
                                <c:if test="${request.statusID == MyConstants.STATUS_ACCEPT}">
                                    Accepted
                                </c:if>
                            </td>
                            <td>${request.date}</td>
                            <td>
                                <c:url var="deleteHistory" value="MainController">
                                    <c:param name="btnAction" value="DeleteHistory" />
                                    <c:param name="requestID" value="${request.requestID}" />
                                    <c:param name="page" value="${param.page}" />
                                    <c:param name="searchByDate" value="${param.searchByDate}" />
                                    <c:param name="searchByName" value="${param.searchByName}" />
                                </c:url>
                                <a href="${deleteHistory}">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <c:if test="${not empty requestScope.pageTotal}">
                <c:forEach begin="1" end="${requestScope.pageTotal}" var="i">
                    <c:url var="index" value="MainController">
                        <c:param name="btnAction" value="History" />
                        <c:param name="page" value="${i}" />
                        <c:param name="searchByDate" value="${param.searchByDate}" />
                        <c:param name="searchByName" value="${param.searchByName}" />
                    </c:url>
                    <a href="${index}">${i}</a>
                </c:forEach>
            </c:if>
        </c:if>

    </body>
</html>
