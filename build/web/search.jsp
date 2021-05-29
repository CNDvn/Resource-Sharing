<%-- 
    Document   : search
    Created on : May 12, 2021, 3:21:55 PM
    Author     : CND
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="hieubd.utils.MyConstants" %>
<!DOCTYPE html>
<c:set var="user" value="${sessionScope.user}" />
<c:if test="${empty user}">
    ${pageContext.response.sendRedirect("login.html")}
</c:if>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Page</title>
    </head>
    <body>
        <style>
            td, th {
                border: 1px solid black;
            }

            table {
                border: none;
                border-collapse: collapse;
            }
            a{
                text-decoration: none;
            }
        </style>

        <c:if test="${not empty user.name}">
            Welcome: <font color="green">${user.name}</font>
            <button><a href="MainController?btnAction=Logout">Logout</a></button>
        </c:if>

        <div style="margin: 30px">
            <!--Begin Search bar-->
            <form action="MainController" style="display: inline; margin-right: 50%">
                <input type="date" name="searchByDate" value="${param.searchByDate}"/>
                <select name="searchByCategory">
                    <option value="">Category</option>
                    <c:forEach var="category" items="${requestScope.listCategoies}">
                        <option 
                            <c:if test="${category.categoryID == param.searchByCategory}">
                                selected
                            </c:if>
                            value="${category.categoryID}">${category.name}</option>
                    </c:forEach>
                </select>
                <input type="text" name="searchByName" value="${param.searchByName}" placeholder="Search by name"/>
                <input type="submit" value="Search" name="btnAction"/>
            </form>
            <!--End Search bar-->

            <!--Begin menu bar-->
            <c:if test="${user.roleID == MyConstants.ROLE_EMPLOYEE}">
                <button><a href="MainController?btnAction=History">History Page</a></button>
            </c:if>
            <c:if test="${user.roleID == MyConstants.ROLE_MANAGE}">
                <button><a href="MainController?btnAction=SearchRequestProcess">Process Request Page</a></button>
            </c:if>
            <!--End menu bar-->
        </div>

        <!--Begin show list resource-->
        <c:set var="listResource" value="${requestScope.listResource}" />
        <c:if test="${ not empty requestScope.searchNotValue}">
            <font color="red">${requestScope.searchNotValue}</font>
        </c:if>
        <c:if test="${ not empty listResource}">
            <table>
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Name</th>
                        <th>Color</th>
                        <th>Category</th>
                        <th>Quantity</th>
                            <c:if test="${user.roleID == MyConstants.ROLE_EMPLOYEE }">
                            <th>Action</th>
                            </c:if>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${listResource}" varStatus="counter" >
                        <tr>
                            <td>${counter.count }</td>
                            <td>${item.name}</td>
                            <td>${item.color}</td>
                            <td>
                                <c:forEach var="category" items="${requestScope.listCategoies}">
                                    <c:if test="${item.categoryID == category.categoryID}">
                                        ${category.name}
                                    </c:if>
                                </c:forEach>
                            </td>
                            <td>${item.quantity}</td>
                            <c:if test="${user.roleID == MyConstants.ROLE_EMPLOYEE }">
                                <td>
                                    <c:url var="booking" value="MainController">
                                        <c:param name="btnAction" value="Booking" />
                                        <c:param name="resourceID" value="${item.resourceID}" />
                                        <c:param name="page" value="${param.page}" />
                                        <c:param name="searchByDate" value="${param.searchByDate}" />
                                        <c:param name="searchByCategory" value="${param.searchByCategory}" />
                                        <c:param name="searchByName" value="${param.searchByName}" />
                                    </c:url>
                                    <a href="${booking}">Booking</a>
                                </td>
                            </c:if>
                            <c:if test="${requestScope.success == item.resourceID}">
                                <td style="border: none; color: green">
                                    Success, please wait manager accept
                                </td>
                            </c:if>
                            <c:if test="${requestScope.fail == item.resourceID}">
                                <td style="border: none; color: red">
                                    Fail, Sorry this resource was out of  stock
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
                        <c:param name="btnAction" value="Search" />
                        <c:param name="page" value="${i}" />
                        <c:param name="searchByDate" value="${param.searchByDate}" />
                        <c:param name="searchByCategory" value="${param.searchByCategory}" />
                        <c:param name="searchByName" value="${param.searchByName}" />
                    </c:url>
                    <a href="${index}">${i}</a>
                </c:forEach>
            </c:if>
            <!--End paging-->
        </c:if>
        <!--End show list resource-->

    </body>
</html>
