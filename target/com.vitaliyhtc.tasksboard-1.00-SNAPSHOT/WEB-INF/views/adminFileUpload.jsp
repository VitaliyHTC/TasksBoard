<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="000_head_meta.jsp" %>

    <title>Admin - File Upload</title>

    <link href="${contextPath}/resources/css/bootstrap.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/style-main.css" rel="stylesheet">
</head>
<body>

<div id="surface">
    <%@include file="001_div_header.jsp" %>
    <div id="content">

        <div class="all">
            <div class="all-wrap bg_white">
                <h3>File Upload</h3>
                <!--<a href="${contextPath}/admin/addnewboard">Add new Board</a>-->
                <br><br>



                <form method="POST" action="/admin/uploadFile" enctype="multipart/form-data">
                    File to upload: <input type="file" name="file" multiple="true" >
                    <input type="submit" value="Upload"> Press here to upload the file!
                    <%--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>--%>
                </form>

                <c:if test="${!empty file}">
                    <h2>Submitted File</h2>
                    <table>
                        <tr>
                            <td>OriginalFileName:</td>
                            <td>${file.originalFilename}</td>
                        </tr>
                        <tr>
                            <td>Type:</td>
                            <td>${file.contentType}</td>
                        </tr>
                    </table>
                </c:if>



                <br><br>



                <form method="POST" action="/admin/uploadMultiFile" enctype="multipart/form-data">
                    File to upload: <input type="file" name="files">
                    File to upload: <input type="file" name="files">
                    File to upload: <input type="file" name="files">
                    File to upload: <input type="file" name="files">
                    <input type="submit" value="Upload"> Press here to upload the file!
                </form>

                <c:if test="${!empty file}">
                    <table>
                        <c:forEach items="${files}" var="file">
                            <tr>
                                <td>OriginalFileName:</td>
                                <td>${file.originalFilename}</td>
                            </tr>
                            <tr>
                                <td>Type:</td>
                                <td>${file.contentType}</td>
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