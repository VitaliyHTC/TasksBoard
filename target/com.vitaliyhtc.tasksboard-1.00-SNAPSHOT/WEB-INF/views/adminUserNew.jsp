<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="000_head_meta.jsp" %>

    <title>Admin - Add new User</title>

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
                <a href="${contextPath}/admin/users">< back to Users page</a>
                <div class="container">

                    <form:form method="POST" modelAttribute="userForm" class="form-signin" action="/admin/addnewuser">
                        <h3 class="form-signin-heading">Create new User account</h3>
                        <spring:bind path="username">
                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                <form:input type="text" path="username" class="form-control" placeholder="Username"
                                            autofocus="true"></form:input>
                                <form:errors path="username"></form:errors>
                            </div>
                        </spring:bind>

                        <spring:bind path="password">
                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                <form:input type="password" path="password" class="form-control" placeholder="Password"></form:input>
                                <form:errors path="password"></form:errors>
                            </div>
                        </spring:bind>

                        <spring:bind path="confirmPassword">
                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                <form:input type="password" path="confirmPassword" class="form-control"
                                            placeholder="Confirm your password"></form:input>
                                <form:errors path="confirmPassword"></form:errors>
                            </div>
                        </spring:bind>

                        <spring:bind path="email">
                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                <form:input type="email" path="email" class="form-control" placeholder="Email"></form:input>
                                <form:errors path="email"></form:errors>
                            </div>
                        </spring:bind>

                        <spring:bind path="firstname">
                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                <form:input type="text" path="firstname" class="form-control" placeholder="Firstname"></form:input>
                                <form:errors path="firstname"></form:errors>
                            </div>
                        </spring:bind>

                        <spring:bind path="lastname">
                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                <form:input type="text" path="lastname" class="form-control" placeholder="Lastname"></form:input>
                                <form:errors path="lastname"></form:errors>
                            </div>
                        </spring:bind>

                        <spring:bind path="enabled">
                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                <label class="margin_0_0_0_10 control-label">Enabled?</label><br>
                                <div class="radio">
                                    <form:radiobutton path="enabled" name="enabled" value="true" />
                                    User account is enabled.
                                </div>
                                <div class="radio">
                                    <form:radiobutton path="enabled" name="enabled" value="false" />
                                    User account is disabled.
                                </div>
                                <form:errors path="enabled"></form:errors>
                            </div>
                        </spring:bind>

                        <spring:bind path="accountNonLocked">
                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                <label class="margin_0_0_0_10 control-label">Account Non Locked?</label><br>
                                <div class="radio">
                                    <form:radiobutton path="accountNonLocked" name="accountNonLocked" value="true" />
                                    User account is non locked.
                                </div>
                                <div class="radio">
                                    <form:radiobutton path="accountNonLocked" name="accountNonLocked" value="false" />
                                    User account is locked.
                                </div>
                                <form:errors path="accountNonLocked"></form:errors>
                            </div>
                        </spring:bind>

                        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
                        <br>
                        <h4 class="text-center"><a href="${contextPath}/admin/users">Cancel and back to Users page</a></h4>

                    </form:form>

                </div>

            </div>
        </div>

    </div><!-- id="content" -->
</div><!-- id="surface" -->

<%--<%@include file="099_script_at_end.jsp" %>--%>
</body>
</html>