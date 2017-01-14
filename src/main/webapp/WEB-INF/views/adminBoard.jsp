<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="000_head_meta.jsp" %>

    <title>${board.getBoardName()} - Board - Admin</title>

    <%--<link href="${contextPath}/resources/css/bootstrap.css" rel="stylesheet">--%>
    <link href="${contextPath}/resources/css/normalize.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/style-main.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/trello_core.css" rel="stylesheet">

    <style>
        .list-header-extras-item .icon-remove { right: 3px; top: 3px; }
        .list-header-extras-item .icon-edit { right: 32px; top: 3px; }
    </style>

</head>
<body>

<div id="surface">
    <%@include file="001_div_header.jsp" %>
    <div id="content">



        <div class="board-wrapper">
            <div class="board-main-content">

                <div class="board-header u-clearfix">
                    <a class="board-header-btn board-header-btn-name" href=""><span class="board-header-btn-text" dir="auto">${board.getBoardName()}</span></a>
                    <div class="board-header-btns mod-left">
                        <a class="board-header-btn" href="${contextPath}/admin/editexistingboard?itemIDtoEdit=${board.getId()}&redirectPage=board" title="Click to edit board name, description and visibility."><span class="icon-sm icon-edit board-header-btn-icon"></span></a>
                        <a class="board-header-btn" href="" title="${board.getBoardDescription()}"><span class="icon-sm icon-description board-header-btn-icon"></span></a>
                        <a id="permission-level" class="board-header-btn perms-btn" href="" title="${board.getBoardVisibility().getVisDescription()}"><span class="board-header-btn-icon icon-sm icon-private"></span><span class="board-header-btn-text">${board.getBoardVisibility().getVisName()}</span></a>
                        <div class="board-header-btn">
                            <span class="icon-sm icon-organization board-header-btn-icon"></span>
                            <c:forEach items="${board.getUsers()}" var="user">
                                &nbsp;${user.getUsername()}
                            </c:forEach>
                        </div>
                    </div><!-- board-header-btns mod-left -->
                    <%--
                    <div class="board-header-btns mod-right">
                        <a class="board-header-btn board-header-btn-filter-indicator" href=""><span class="icon-sm icon-filter board-header-btn-icon"></span><span class="board-header-btn-text u-text-underline">Filtering is on.</span><span class="icon-sm icon-close board-header-btn-icon-close"></span></a>
                        <a class="board-header-btn sub-btn" href=""><span class="icon-sm icon-subscribe board-header-btn-icon"></span><span class="board-header-btn-text u-text-underline">Subscribed</span></a>
                        <a class="board-header-btn calendar-btn" href=""><span class="icon-sm icon-calendar board-header-btn-icon"></span><span class="board-header-btn-text u-text-underline">Calendar</span></a>
                        <span class=""><span class="board-header-plugin-btns hide"></span></span>
                        <a class="board-header-btn mod-show-menu" href=""><span class="icon-sm icon-overflow-menu-horizontal board-header-btn-icon"></span><span class="board-header-btn-text u-text-underline">Show Menu</span></a>
                    </div><!-- board-header-btns mod-right -->
                    --%>
                </div><!-- board-header -->
                <div class="board-canvas">
                    <div id="board" class="u-fancy-scrollbar">



                        <c:forEach items="${board.getTaskListSet()}" var="taskList">
                            <div class="list-wrapper">
                                <div class="list">

                                    <div class="list-header u-clearfix is-menu-shown">
                                        <div class="list-header-target"></div>
                                        <h2 class="list-header-name-assist" dir="auto">${taskList.getListName()}</h2>
                                        <textarea class="list-header-name mod-list-name" spellcheck="false" dir="auto" maxlength="512" style="overflow: hidden; word-wrap: break-word; height: 24px;">${taskList.getListName()}</textarea>
                                        <p class="list-header-num-cards">${taskList.getTaskItemSet().size()}&nbsp;cards</p>
                                        <div class="list-header-extras">
                                            <span class="list-header-extras-subscribe hide"><span class="icon-sm icon-subscribe"></span></span>
                                            <a class="list-header-extras-item dark-hover" href="${contextPath}/admin/editexistingtasklist?itemIDtoEdit=${taskList.getId()}&redirectPage=board" title="Click to edit taskList."><span class="icon-sm icon-edit"></span></a>
                                            <a class="list-header-extras-item dark-hover" href="${contextPath}/admin/deletetasklist?itemIDtoDelete=${taskList.getId()}&redirectPage=board" title="Click to delete taskList."><span class="icon-sm icon-remove"></span></a>
                                            <a class="list-header-extras-menu dark-hover hide" href=""><span class="icon-sm icon-overflow-menu-horizontal"></span></a>
                                        </div>
                                    </div><!-- .list-header -->

                                    <div class="list-cards u-fancy-scrollbar u-clearfix ui-sortable">

                                        <c:forEach items="${taskList.getTaskItemSet()}" var="taskItem">
                                            <div class="list-card ui-droppable">
                                                <div class="list-card-cover"></div>
                                                <a class="list-header-extras-item" href="${contextPath}/admin/editexistingtaskitem?itemIDtoEdit=${taskItem.getId()}&boardID=${board.getId()}" title="Click to edit taskItem."><span class="icon-sm icon-edit list-card-operation dark-hover"></span></a>
                                                <a class="list-header-extras-item" href="${contextPath}/admin/deletetaskitem?itemIDtoDelete=${taskItem.getId()}&boardID=${board.getId()}" title="Click to delete taskItem."><span class="icon-sm icon-remove list-card-operation dark-hover"></span></a>
                                                <div class="list-card-stickers-area hide"><div class="stickers"></div></div>
                                                <div class="list-card-details">
                                                    <div class="list-card-labels"></div>
                                                    <a class="list-card-title" dir="auto" href=""><span class="card-short-id hide">#1</span><b>${taskItem.getTaskName()}</b> ${taskItem.getTaskDescription()}</a>
                                                    <div class="badges"><span class="js-badges"></span><span class="js-plugin-badges"><span></span></span></div>
                                                    <div class="list-card-members"></div>
                                                </div>
                                            </div><!-- .list-card -->
                                        </c:forEach>

                                        <%--
                                        <div class="list-card ui-droppable">
                                            <div class="list-card-cover"></div>
                                            <span class="icon-sm icon-edit list-card-operation dark-hover"></span>
                                            <div class="list-card-stickers-area hide"><div class="stickers"></div></div>
                                            <div class="list-card-details">
                                                <div class="list-card-labels"></div>
                                                <a class="list-card-title" dir="auto" href="/c/zHuRhFAd/1-first"><span class="card-short-id hide">#1</span>first!</a>
                                                <div class="badges"><span class="js-badges"></span><span class="js-plugin-badges"><span></span></span></div>
                                                <div class="list-card-members"></div>
                                            </div>
                                        </div><!-- .list-card -->
                                        --%>

                                    </div><!-- .list-cards -->

                                    <a class="open-card-composer" href="${contextPath}/admin/addtaskitem?taskListID=${taskList.getId()}&boardID=${board.getId()}">Add a card…</a>

                                </div><!-- .list -->
                            </div><!-- .list-wrapper -->
                        </c:forEach>



                        <div class="list-wrapper mod-add is-idle">
                            <a href="${contextPath}/admin/addtasklist?boardID=${board.getId()}&redirectPage=board"><form><span class="placeholder">Add a list…</span></form></a>
                        </div><!-- list-wrapper -->

                    </div><!-- #board -->
                </div><!-- .board-canvas -->

            </div><!-- board-main-content -->
        </div><!-- board-wrapper -->






        <%--
        <div class="all">
            <div class="all-wrap bg_white">
                <a href="${contextPath}/admin/boards">< back to Boards page</a>
                <br>
                <a href="${contextPath}/admin/editexistingboard?itemIDtoEdit=${board.getId()}">Edit this board</a>
                <br>
                <table class="Zebra">
                    <tr>
                        <th>Name:</th>
                        <th>Value:</th>
                    </tr>
                    <tr><td>ID:</td><td>${board.getId()}</td></tr>
                    <tr><td>Name:</td><td>${board.getBoardName()}</td></tr>
                    <tr><td>Description:</td><td>${board.getBoardDescription()}</td></tr>
                    <tr><td>Visibility:</td><td>${board.getBoardVisibility().getVisName()}</td></tr>
                    <tr><td>Group:</td><td>${board.getGroup()}</td></tr>
                    <tr><td>Users:</td>
                        <td>
                            <c:forEach items="${board.getUsers()}" var="user">
                                ${user.getUsername()}<br>
                            </c:forEach>
                        </td>
                    </tr>
                    <tr><td>TaskLists:</td>
                        <td>
                            <c:forEach items="${board.getTaskListSet()}" var="taskList">
                                ${taskList.getListName()}&nbsp;<a href="${contextPath}/admin/editexistingtasklist?itemIDtoEdit=${taskList.getId()}&redirectPage=board">Edit</a>
                                / <a href="${contextPath}/admin/deletetasklist?itemIDtoDelete=${taskList.getId()}&redirectPage=board">Delete</a>
                                <br>
                            </c:forEach>
                            <div class="clearfix"></div>
                            <a href="${contextPath}/admin/addtasklist?boardID=${board.getId()}&redirectPage=board">Add TaskList</a>
                        </td>
                    </tr>
                </table>

            </div>
        </div>
        --%>

    </div><!-- id="content" -->
</div><!-- id="surface" -->

<%--<%@include file="099_script_at_end.jsp" %>--%>
</body>
</html>