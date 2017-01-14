<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="000_head_meta.jsp" %>

    <title>Admin - Users</title>

    <link href="${contextPath}/resources/css/bootstrap.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/style-main.css" rel="stylesheet">
</head>
<body>

<div id="surface">
    <%@include file="001_div_header.jsp" %>
    <div id="content">

        <div class="all">
            <div class="all-wrap bg_white">
                <h3>Users list</h3>
                <a href="${contextPath}/admin/addnewuser">Add new User</a>
                <br><br>

                <h4>Users with ROLE_ADMIN :</h4>
                <c:if test="${!empty usersAdminsList}">
                    <table class="w100 Zebra">
                        <tr>
                            <th width="40">ID</th>
                            <th>Username</th>
                            <th>Email</th>
                            <th>Firstname&nbsp;Lastname</th>
                            <th>Enabled?&nbsp;/&nbsp;Non Locked?</th>
                            <th>Boards(ID&Name)</th>
                            <th>Groups(ID&Name)</th>
                            <th width="100">Edit?</th>
                        </tr>
                        <c:forEach items="${usersAdminsList}" var="user">
                            <tr>
                                <td>${user.getId()}</td>
                                <td>${user.getUsername()}</td>
                                <td>${user.getEmail()}</td>
                                <td>${user.getFirstname()}&nbsp;${user.getLastname()}</td>
                                <td>${user.isEnabled()}&nbsp;/&nbsp;${user.isAccountNonLocked()}</td>
                                <td>
                                    <c:forEach items="${user.getBoards()}" var="boardItem">
                                        ${boardItem.getId()}&nbsp;${boardItem.getBoardName()}<br>
                                    </c:forEach>
                                </td>
                                <td>
                                    <c:forEach items="${user.getGroups()}" var="groupItem">
                                        ${groupItem.getId()}&nbsp;${groupItem.getGroupName()}<br>
                                    </c:forEach>
                                </td>
                                <td>
                                    ---
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:if>

                <br>
                <h4>Users with only ROLE_USER :</h4>
                <c:if test="${!empty usersUsersList}">
                    <table class="w100 Zebra">
                        <tr>
                            <th width="40">ID</th>
                            <th>Username</th>
                            <th>Email</th>
                            <th>Firstname&nbsp;Lastname</th>
                            <th>Enabled?&nbsp;/&nbsp;Non Locked?</th>
                            <th>Boards(ID&Name)</th>
                            <th>Groups(ID&Name)</th>
                            <th width="100">Edit?</th>
                        </tr>
                        <c:forEach items="${usersUsersList}" var="user">
                            <tr>
                                <td>${user.getId()}</td>
                                <td>${user.getUsername()}</td>
                                <td>${user.getEmail()}</td>
                                <td>${user.getFirstname()}&nbsp;${user.getLastname()}</td>
                                <td>${user.isEnabled()}&nbsp;/&nbsp;${user.isAccountNonLocked()}</td>
                                <td>
                                    <c:forEach items="${user.getBoards()}" var="boardItem">
                                        ${boardItem.getId()}&nbsp;${boardItem.getBoardName()}<br>
                                    </c:forEach>
                                </td>
                                <td>
                                    <c:forEach items="${user.getGroups()}" var="groupItem">
                                        ${groupItem.getId()}&nbsp;${groupItem.getGroupName()}<br>
                                    </c:forEach>
                                </td>
                                <td>
                                    <a href="${contextPath}/admin/editexistinguser?itemIDtoEdit=${user.getId()}">Edit</a>
                                    /
                                    <a href="${contextPath}/admin/deleteuser?itemIDtoDelete=${user.getId()}">Delete</a>
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