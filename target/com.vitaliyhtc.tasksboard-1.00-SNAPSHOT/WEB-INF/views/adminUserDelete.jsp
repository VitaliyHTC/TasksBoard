<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="000_head_meta.jsp" %>

    <title>Admin - Delete User</title>

    <link href="${contextPath}/resources/css/bootstrap.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/style-main.css" rel="stylesheet">
</head>
<body>

<div id="surface">
    <%@include file="001_div_header.jsp" %>
    <div id="content">

        <div class="all">
            <div class="all-wrap bg_eee09">
                <a href="${contextPath}/admin/users">< back to Users page</a>

                <c:if test="${forbidden}">
                    <div class="bs-callout bs-callout-warning" id="callout-help-text-accessibility">
                        <p>${errormsg}</p>
                    </div>
                </c:if>

                <c:choose>
                    <c:when test="${form_disabled == 'disabled'}">
                        <h3 class="text-center">Forbidden!</h3>
                    </c:when>
                    <c:otherwise>
                        <div class="container form-delete">
                            <h4>Would you like to delete this User?</h4>
                            <div class="bs-callout bs-callout-info" id="callout-help-text-accessibility">
                                <p>
                                        ${userToDelete.getUsername()}<br>
                                        ${userToDelete.getEmail()}<br>
                                        ${userToDelete.getFirstname()}&nbsp;${userToDelete.getLastname()}
                                </p>
                            </div>
                            <form:form method="POST" action="/admin/deleteuser">
                                <input type="hidden" name="itemIDtoDelete" value="${itemIDtoDelete}">
                                <div class="form-group">
                                    <input type="radio" name="radioconfirm" value="radio_no" checked>&nbsp;&nbsp;No, leave user in system.<br>
                                    <input type="radio" name="radioconfirm" value="radio_yes">&nbsp;&nbsp;Yes, delete user from system.<br>
                                    <input type="checkbox" name="checkboxconfirm" value="checkbox_yes">&nbsp;&nbsp;I confirm user deletion.<br>
                                </div>
                                <button class="btn btn-primary" type="submit">Delete</button>
                                <a href="${contextPath}/admin/users" class="btn btn-default" role="button">Cancel</a>
                            </form:form>
                        </div>
                    </c:otherwise>
                </c:choose>

            </div>
        </div>

    </div><!-- id="content" -->
</div><!-- id="surface" -->

<%--<%@include file="099_script_at_end.jsp" %>--%>
</body>
</html>