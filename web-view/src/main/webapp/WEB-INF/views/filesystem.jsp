<%@ page language="java" contentType="text/html; charset=utf8"
         pageEncoding="utf8" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf8">
    <title><spring:message code="label.title"/></title>
</head>
<body>

<%--<a href="<c:url value="/logout" />">
    <spring:message code="label.logout"/>
</a>--%>

<h2><spring:message code="label.title"/></h2>
<c:if test="${! empty errorMessage}">
<h4><spring:message code="label.error.itemNotFind" arguments="${param.path}"/></h4>
<h4><a href="fs"><spring:message code="label.messages.goToRoot"/></a>
    <h4>
        </c:if>

        <c:if test="${item != null}">
        <h3>${itemLinkedPath}</h3>
        <table>
            <tr>
                <th><spring:message code="label.item.name"/></th>
                <th><spring:message code="label.item.creation_date"/></th>
                <th><spring:message code="label.item.size"/></th>
                <th>&nbsp;</th>
                <th>&nbsp;</th>
            </tr>
            <c:if test="${! empty item.path}">
                <tr>
                    <td><a href="?path=${item.path}">..</a></td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
            </c:if>
            <c:forEach items="${childrenList}" var="item">
                <tr>
                    <td><a href="?path=${item.fullPath}">${item.name}</a></td>
                    <td>${item.date}</td>
                    <td>&nbsp;</td>
                    <td><a href="fs/${item.id}/edit"><spring:message code="label.item.action.edit"/></a></td>
                    <td><a href="fs/${item.id}/delete"><spring:message code="label.item.action.delete"/></a></td>
                </tr>
            </c:forEach>
        </table>
        </c:if>

        <c:if test="${newFolder != null}">
        <form:form method="post" action="/addFolder" commandName="newFolder">
        <table>
            <tr>
                <td>
                    <spring:message code="label.item.name"/>
                </td>
                <td>
                    <form:input path="name"/>
                    <form:hidden path="path"/>
                </td>
                <td>
                    <input type="submit" value="<spring:message code="label.add.folder"/>"/>
                    <span><form:errors path="name"/></span>
                </td>
            </tr>
        </table>
        </form:form>
        </c:if>

        <c:choose>
        <c:when test="${not empty  errors}">
        <div class="error">
            <c:forEach items="${errors}" var="err">
                ${err.defaultMessage}
                <br/>
            </c:forEach>
        </div>
        </c:when>
        </c:choose>

        <c:if test="${newFile != null}">
        <form:form method="post" enctype="multipart/form-data" action="/addFile" commandName="newFile">
        <table>
            <tr>
                <td>
                    <input type="file" name="file"/>
                    <form:hidden path="name"/>
                    <form:hidden path="path"/>
                </td>
                <td>
                    <input type="submit" value="<spring:message code="label.add.file"/>"/>
                    <span><form:errors path="data"/></span>
                </td>
            </tr>
        </table>
        </form:form>
        </c:if>

        <c:if test="${editedItem != null}">
        <form:form method="post" commandName="editedItem">
        <h4><spring:message code="label.item.edit"/></h4>
        <table>
            <tr>
                <td>
                    <spring:message code="label.item.name"/>
                </td>
                <td>
                    <form:hidden path="id"/>
                    <form:hidden path="name"/>
                    <input type="text" value="${editedItem.name}" name="newName"/>
                    <form:hidden path="path"/>
                </td>
                <td>
                    <input type="submit" value="<spring:message code="label.item.action.edit"/>"/>
                    <span><form:errors path="name"/></span>
                </td>
            </tr>
        </table>
        </form:form>
        </c:if>
</body>
</html>
