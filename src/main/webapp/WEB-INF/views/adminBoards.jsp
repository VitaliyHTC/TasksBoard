<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="000_head_meta.jsp" %>

    <title>Admin - Boards</title>

    <link href="${contextPath}/resources/css/bootstrap.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/style-main.css" rel="stylesheet">
</head>
<body>

<div id="surface">
    <%@include file="001_div_header.jsp" %>
    <div id="content">

        <div class="all">
            <div class="all-wrap bg_white">
                <h3>Boards list</h3>
                <a href="${contextPath}/admin/addnewboard">Add new Board</a>
                <br><br>

                <c:if test="${!empty boardsList}">
                    <table class="w100 Zebra">
                        <tr>
                            <th width="40">ID</th>
                            <th width="120">Name</th>
                            <th>Description</th>
                            <th>Visibility</th>
                            <th>Users(ID&Name)</th>
                            <th>TaskLists</th>
                            <th>Group(ID&Name)</th>
                            <th width="100">Edit?</th>
                        </tr>
                        <c:forEach items="${boardsList}" var="board">
                            <tr>
                                <td>${board.getId()}</td>
                                <td><a href="${contextPath}/admin/board?boardID=${board.getId()}" title="View this board">${board.getBoardName()}</a></td>
                                <td>${board.getBoardDescription()}</td>
                                <td>${board.getBoardVisibility().getVisName()}</td>
                                <td>
                                    <c:forEach items="${board.getUsers()}" var="userItem">
                                        ${userItem.getId()}&nbsp;${userItem.getUsername()}<br>
                                    </c:forEach>
                                </td>
                                <td>
                                    <c:forEach items="${board.getTaskListSet()}" var="taskListItem">
                                        ${taskListItem.getListName()}&nbsp;<a href="${contextPath}/admin/editexistingtasklist?itemIDtoEdit=${taskListItem.getId()}">Edit</a>
                                        / <a href="${contextPath}/admin/deletetasklist?itemIDtoDelete=${taskListItem.getId()}">Delete</a>
                                        <br>
                                    </c:forEach>
                                    <div class="clearfix"></div>
                                    <a href="${contextPath}/admin/addtasklist?boardID=${board.getId()}">Add TaskList</a>
                                </td>
                                <td>${board.getGroup().getId()}&nbsp;${board.getGroup().getGroupName()}</td>
                                <td>
                                    <a href="${contextPath}/admin/editexistingboard?itemIDtoEdit=${board.getId()}">Edit</a>
                                    /
                                    <a href="${contextPath}/admin/deleteboard?itemIDtoDelete=${board.getId()}">Delete</a>
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