<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<div id="header" class="header">
    <div class="linksblock">
        <a href="${contextPath}/index#/">Home</a>

        <sec:authorize access="hasRole('ROLE_ADMIN')">
            &nbsp;|&nbsp;
            <a href="${contextPath}/admin">Admin</a>
            &nbsp;>&nbsp;
            <a href="${contextPath}/admin/users">Users</a>&nbsp;
            <a href="${contextPath}/admin/groups">Groups</a>&nbsp;
            <a href="${contextPath}/admin/boards">Boards</a>&nbsp;
            <a href="${contextPath}/admin/uploadFileView">uploadFiles</a>&nbsp;
        </sec:authorize>

    </div>
    <div class="userinfo">
        <c:if test="${pageContext.request.userPrincipal.name != null}">
            <form id="logoutForm" method="POST" action="${contextPath}/logout">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
            ${pageContext.request.userPrincipal.name}&nbsp;|
            <a onclick="document.forms['logoutForm'].submit()">Logout</a>
        </c:if>
    </div>
</div>