<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="000_head_meta.jsp" %>

    <title>Admin - Edit existing Group</title>

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
                <a href="${contextPath}/admin/groups">< back to Groups page</a>
                <div class="container">

                    <form:form method="POST" modelAttribute="groupForm" class="form-signin" action="/admin/editexistinggroup">
                        <h3 class="form-signin-heading">Edit existing Group</h3>
                        <input type="hidden" name="itemIDtoEdit" value="${itemIDtoEdit}">
                        <spring:bind path="groupName">
                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                <form:input type="text" path="groupName" class="form-control" placeholder="Group name"
                                            autofocus="true"></form:input>
                                <form:errors path="groupName"></form:errors>
                            </div>
                        </spring:bind>

                        <spring:bind path="groupDescription">
                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                <form:textarea path="groupDescription" class="form-control" placeholder="Group description"
                                               maxlength="1024" rows="9"></form:textarea>
                                <form:errors path="groupDescription"></form:errors>
                            </div>
                        </spring:bind>

                        <spring:bind path="users">
                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                <label class="margin_0_0_0_10 control-label">Users(ID&Name):</label><br>
                                <c:forEach items="${usersList}" var="userItem">
                                    <div class="checkbox">
                                        <form:checkbox path="users" value="${userItem}"/>
                                            ${userItem.getId()}&nbsp;${userItem.getUsername()}
                                    </div>
                                </c:forEach>
                                <form:errors path="users"></form:errors>
                            </div>
                        </spring:bind>

                        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
                        <br>
                        <h4 class="text-center"><a href="${contextPath}/admin/groups">Cancel and back to Groups page</a></h4>

                    </form:form>

                </div>

            </div>
        </div>

    </div><!-- id="content" -->
</div><!-- id="surface" -->

<%--<%@include file="099_script_at_end.jsp" %>--%>
</body>
</html>