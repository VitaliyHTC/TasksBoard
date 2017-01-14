<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="000_head_meta.jsp" %>

    <title>Admin - Edit existing TaskList</title>

    <link href="${contextPath}/resources/css/bootstrap.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/style_login_register.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/style-main.css" rel="stylesheet">
</head>
<body>

<div id="surface">
    <%@include file="001_div_header.jsp" %>
    <div id="content">

        <div class="all">
            <div class="all-wrap bg_eee09">
                <c:choose>
                    <c:when test="${redirectPage =='board'}">
                        <a href="${contextPath}/admin/board?boardID=${boardID}">< back to board page</a>
                    </c:when>
                    <c:otherwise>
                        <a href="${contextPath}/admin/boards">< back to Boards page</a>
                    </c:otherwise>
                </c:choose>
                <div class="container">

                    <form:form method="POST" modelAttribute="taskListForm" class="form-signin" action="/admin/editexistingtasklist">
                        <h3 class="form-signin-heading">Edit existing TaskList</h3>
                        <input type="hidden" name="boardID" value="${boardID}">
                        <input type="hidden" name="itemIDtoEdit" value="${itemIDtoEdit}">
                        <input type="hidden" name="redirectPage" value="${redirectPage}">
                        <spring:bind path="listName">
                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                <form:input type="text" path="listName" class="form-control" placeholder="TaskList name"
                                            autofocus="true"></form:input>
                                <form:errors path="listName"></form:errors>
                            </div>
                        </spring:bind>
                        <spring:bind path="listDescription">
                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                <form:textarea path="listDescription" class="form-control" placeholder="TaskList description"
                                               maxlength="1024" rows="9"></form:textarea>
                                <form:errors path="listDescription"></form:errors>
                            </div>
                        </spring:bind>
                        <spring:bind path="itemsLimit">
                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                <h4>Enter items limit:</h4>
                                <form:input type="number" name="quantity" min="0" max="512" path="itemsLimit" class="form-control"
                                            placeholder="TaskList items limit"></form:input>
                                <form:errors path="itemsLimit"></form:errors>
                            </div>
                        </spring:bind>

                        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
                        <br>
                        <h4 class="text-center">
                            <c:choose>
                                <c:when test="${redirectPage =='board'}">
                                    <a href="${contextPath}/admin/board?boardID=${boardID}">Cancel and back to board page</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${contextPath}/admin/boards">Cancel and back to Boards page</a>
                                </c:otherwise>
                            </c:choose>
                        </h4>

                    </form:form>

                </div>

            </div>
        </div>

    </div><!-- id="content" -->
</div><!-- id="surface" -->

<%--<%@include file="099_script_at_end.jsp" %>--%>
</body>
</html>