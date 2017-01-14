<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="000_head_meta.jsp" %>

    <title>Admin - Groups</title>

    <link href="${contextPath}/resources/css/bootstrap.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/style-main.css" rel="stylesheet">
</head>
<body>

<div id="surface">
    <%@include file="001_div_header.jsp" %>
    <div id="content">

        <div class="all">
            <div class="all-wrap bg_white">
                <h3>Groups list</h3>
                <a href="${contextPath}/admin/addnewgroup">Add new Group</a>
                <br><br>

                <c:if test="${!empty groupsList}">
                    <table class="w100 Zebra">
                        <tr>
                            <th width="40">ID</th>
                            <th width="120">Name</th>
                            <th>Description</th>
                            <th>Users(ID&Name)</th>
                            <th>Boards(ID&Name)</th>
                            <th width="100">Edit?</th>
                        </tr>
                        <c:forEach items="${groupsList}" var="group">
                            <tr>
                                <td>${group.getId()}</td>
                                <td>${group.getGroupName()}</td>
                                <td>${group.getGroupDescription()}</td>
                                <td>
                                    <c:forEach items="${group.getUsers()}" var="userItem">
                                        ${userItem.getId()}&nbsp;${userItem.getUsername()}<br>
                                    </c:forEach>
                                </td>
                                <td>
                                    <c:forEach items="${group.getBoards()}" var="boardItem">
                                        ${boardItem.getId()}&nbsp;${boardItem.getBoardName()}<br>
                                    </c:forEach>
                                </td>
                                <td>
                                    <a href="${contextPath}/admin/editexistinggroup?itemIDtoEdit=${group.getId()}">Edit</a>
                                    /
                                    <a href="${contextPath}/admin/deletegroup?itemIDtoDelete=${group.getId()}">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:if>

            </div>
        </div>

    </div><!-- id="content" -->
</div><!-- id="surface" -->

<%--<%@include file="099_script_at_end.jsp" %>--%>
</body>
</html>