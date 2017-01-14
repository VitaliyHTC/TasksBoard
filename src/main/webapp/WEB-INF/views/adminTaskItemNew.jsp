<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="000_head_meta.jsp" %>

    <title>Admin - Add new TaskItem</title>

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
                <a href="${contextPath}/admin/board?boardID=${boardID}">< back to board page</a>

                <div class="container">

                    <form:form method="POST" modelAttribute="taskItemForm" class="form-signin" action="/admin/addnewtaskitem">
                        <h3 class="form-signin-heading">Create new TaskItem</h3>
                        <input type="hidden" name="boardID" value="${boardID}">
                        <input type="hidden" name="taskListID" value="${taskListID}">
                        <%--<input type="hidden" name="itemIDtoEdit" value="${itemIDtoEdit}">--%>
                        <spring:bind path="taskName">
                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                <form:input type="text" path="taskName" class="form-control" placeholder="TaskItem name"
                                            autofocus="true"></form:input>
                                <form:errors path="taskName"></form:errors>
                            </div>
                        </spring:bind>
                        <spring:bind path="taskDescription">
                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                <form:textarea path="taskDescription" class="form-control" placeholder="TaskItem description"
                                               maxlength="1024" rows="9"></form:textarea>
                                <form:errors path="taskDescription"></form:errors>
                            </div>
                        </spring:bind>

                        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
                        <br>
                        <h4 class="text-center"><a href="${contextPath}/admin/board?boardID=${boardID}">Cancel and back to board page</a></h4>

                    </form:form>

                </div>

            </div>
        </div>

    </div><!-- id="content" -->
</div><!-- id="surface" -->

<%--<%@include file="099_script_at_end.jsp" %>--%>
</body>
</html>