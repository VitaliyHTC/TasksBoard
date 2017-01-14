<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="000_head_meta.jsp" %>

    <title>Index</title>

    <link href="${contextPath}/resources/css/normalize.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/style-main.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/trello_core.css" rel="stylesheet">

</head>
<body ng-app="application">

<div id="surface">
    <%@include file="001_div_header.jsp" %>
    <div id="content">

        <div ng-view></div>

        <div class="clearfix"></div>
    </div><!-- id="content" -->
</div><!--  id="surface" -->


<%@include file="099_script_at_end.jsp" %>
<script src="${contextPath}/resources/ng/index.js"></script>
<script src="${contextPath}/resources/ng/member-boards.js"></script>
<script src="${contextPath}/resources/ng/board-main.js"></script>




</body>
</html>
