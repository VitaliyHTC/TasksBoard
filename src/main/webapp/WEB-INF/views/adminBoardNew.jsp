<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="000_head_meta.jsp" %>

    <title>Admin - Add new Board</title>

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
                <a href="${contextPath}/admin/boards">< back to Boards page</a>
                <div class="container">

                    <form:form method="POST" modelAttribute="boardForm" class="form-signin" action="/admin/addnewboard">
                        <h3 class="form-signin-heading">Create new Board</h3>
                        <spring:bind path="boardName">
                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                <form:input type="text" path="boardName" class="form-control" placeholder="Board name"
                                            autofocus="true"></form:input>
                                <form:errors path="boardName"></form:errors>
                            </div>
                        </spring:bind>
                        <spring:bind path="boardDescription">
                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                <form:textarea path="boardDescription" class="form-control" placeholder="Board description"
                                               maxlength="1024" rows="9"></form:textarea>
                                <form:errors path="boardDescription"></form:errors>
                            </div>
                        </spring:bind>

                        <spring:bind path="boardVisibility">
                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                <label class="margin_0_0_0_10 control-label">Visibility:</label><br>
                                <c:forEach items="${visibilityList}" var="visibilityItem">
                                    <div class="radio">
                                        <c:set var="isCheched" value="" />
                                        <c:if test="${visibilitySelectedID != null}">
                                            <c:if test="${visibilitySelectedID == visibilityItem.getId()}">
                                                <c:set var="isCheched" value="checked" />
                                            </c:if>
                                        </c:if>
                                        <form:radiobutton path="boardVisibility" name="visibilityID" value="${visibilityItem.getId()}"
                                                          checked="${isCheched}" />
                                            ${visibilityItem.getVisName()}
                                    </div>
                                </c:forEach>
                                <form:errors path="boardVisibility"></form:errors>
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

                        <spring:bind path="group">
                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                <label class="margin_0_0_0_10 control-label">Group:</label><br>
                                <c:forEach items="${groupsList}" var="groupItem">
                                    <div class="radio">
                                        <c:set var="isCheched" value="" />
                                        <c:if test="${groupSelectedID != null}">
                                            <c:if test="${groupSelectedID == groupItem.getId()}">
                                                <c:set var="isCheched" value="checked" />
                                            </c:if>
                                        </c:if>
                                        <form:radiobutton path="group" name="groupID" value="${groupItem.getId()}"
                                                          checked="${isCheched}" />
                                            ${groupItem.getGroupName()}
                                    </div>
                                </c:forEach>
                                <form:errors path="group"></form:errors>
                            </div>
                        </spring:bind>
                        <%-- See:
                        http://www.mkyong.com/spring-mvc/spring-mvc-form-handling-example/
                        https://www.mkyong.com/spring-mvc/spring-mvc-form-check-if-a-field-has-an-error/
                        --%>

                        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
                        <br>
                        <h4 class="text-center"><a href="${contextPath}/admin/boards">Cancel and back to Boards page</a></h4>

                    </form:form>

                </div>

            </div>
        </div>

    </div><!-- id="content" -->
</div><!-- id="surface" -->

<%--<%@include file="099_script_at_end.jsp" %>--%>
</body>
</html>