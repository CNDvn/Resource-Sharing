<%-- 
    Document   : Manage
    Created on : May 18, 2021, 10:39:31 PM
    Author     : CND
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="hieubd.utils.MyConstants" %>
<!DOCTYPE html>
<c:set var="user" value="${sessionScope.user}" />
<c:if test="${empty user or (user.roleID != MyConstants.ROLE_MANAGE)}">
    ${pageContext.response.sendRedirect("login.html")}
</c:if>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Page</title>
    </head>
    <body>
        <style>
            td, th {
                border: 1px solid black;
            }

            table {
                border-collapse: collapse;
                border: none;
            }
            a{
                text-decoration: none;
            }
        </style>

        <c:if test="${not empty user.name}">
            Welcome: <font color="green">${user.name}</font>
            <button><a href="MainController?btnAction=Logout">Logout</a></button>
        </c:if>

        <!--Begin Search bar-->
        <div style="margin: 30px">
            <form action="MainController" style="display: inline; margin-right: 50%">
                <input type="text" name="searchContent" value="${param.searchContent}" placeholder="Search"/>
                <button name="btnAction" value="SearchRequestProcess">Search</button>
            </form>
            <button><a href="MainController?btnAction=Search">Search Page</a></button>
        </div>
        <!--End Search bar-->

        <c:set var="listRequest" value="${requestScope.listRequest}" />
        <c:set var="listResource" value="${requestScope.listResource}" />
        <c:set var="listUser" value="${requestScope.listUser}" />
        <c:set var="ListStatus" value="${requestScope.ListStatus}" />
        <c:if test="${ not empty requestScope.searchNotValue}">
            <font color="red">${requestScope.searchNotValue}</font>
        </c:if>
        <c:if test="${not empty listRequest}">
            <table border="1">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Resource Name</th>
                        <th>User Request</th>
                        <th>Date Booking</th>
                        <th>Status</th>
                        <th colspan="2">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="request" items="${listRequest}" varStatus="counter">
                        <tr>
                            <td>${counter.count}</td>
                            <td>
                                <c:forEach var="resource" items="${requestScope.listResource}">
                                    <c:if test="${resource.resourceID == request.resourceID}">
                                        ${resource.name}
                                    </c:if>
                                </c:forEach>
                            </td>
                            <td>
                                <c:forEach var="user" items="${requestScope.listUser}">
                                    <c:if test="${user.email eq request.email}">
                                        ${user.name}
                                    </c:if>
                                </c:forEach>
                            </td>
                            <td>${request.date}</td>

                            <c:if test="${request.statusID == MyConstants.STATUS_NEW}">
                                <td>
                                    New
                                </td>
                            </c:if>
                            <c:if test="${request.statusID == MyConstants.STATUS_ACCEPT}">
                                <td>
                                    Accepted
                                </td>
                            </c:if>

                            <td>
                                <c:url var="AcceptRequest" value="MainController">
                                    <c:param name="btnAction" value="AcceptRequest" />
                                    <c:param name="requestID" value="${request.requestID}" />
                                    <c:param name="resourceID" value="${request.resourceID}" />
                                    <c:param name="page" value="${param.page}" />
                                    <c:param name="searchContent" value="${param.searchContent}" />
                                </c:url>
                                <button style="width: 100%" 
                                        <c:if test="${request.statusID == MyConstants.STATUS_ACCEPT}">
                                            disabled
                                        </c:if>
                                        >
                                    <c:if test="${request.statusID == MyConstants.STATUS_ACCEPT}">
                                        Accept
                                    </c:if>
                                    <c:if test="${request.statusID == MyConstants.STATUS_NEW}">
                                        <a href="${AcceptRequest}">Accept</a>
                                    </c:if>
                                </button>
                            </td>
                            <td>
                                <c:url var="DeleteRequest" value="MainController">
                                    <c:param name="btnAction" value="DeleteRequest" />
                                    <c:param name="requestID" value="${request.requestID}" />
                                    <c:param name="page" value="${param.page}" />
                                    <c:param name="searchContent" value="${param.searchContent}" />
                                </c:url>
                                <button style="width: 100%"><a href="${DeleteRequest}">Delete</a></button>
                            </td>
                            <c:if test="${requestScope.errorAccept == request.requestID}">
                                <td style="border: none">
                                    This resource was out of stock
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <!--Begin paging-->
            <c:if test="${not empty requestScope.pageTotal}">
                <c:forEach begin="1" end="${requestScope.pageTotal}" var="i">
                    <c:url var="index" value="MainController">
                        <c:param name="btnAction" value="SearchRequestProcess" />
                        <c:param name="page" value="${i}" />
                        <c:param name="searchContent" value="${param.searchContent}" />
                    </c:url>
                    <a href="${index}">${i}</a>
                </c:forEach>
            </c:if>
            <!--End paging-->
        </c:if>
    </body>
</html>
