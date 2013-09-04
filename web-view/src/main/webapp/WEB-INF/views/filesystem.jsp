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


<h3>${item.path}</h3>
<c:if test="${!empty childrenList}">
    <table class="data">
        <tr>
            <th><spring:message code="label.item.name"/></th>
            <th><spring:message code="label.item.creation_date"/></th>
            <th><spring:message code="label.item.size"/></th>
            <th>&nbsp;</th>
            <th>&nbsp;</th>
        </tr>
        <c:if test="${item.parentPath != null}">
            <tr>
                <td><a href="?path=${item.parentPath}">..</a></td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
        </c:if>
        <c:forEach items="${childrenList}" var="item">
            <tr>
                <td><a href="?path=${item.path}">${item.name}</a></td>
                <td>${item.date}</td>
                <td>&nbsp;</td>
                <td><a href="fs/${item.id}/edit"><spring:message code="label.edit"/></a></td>
                <td><a href="fs/${item.id}/delete"><spring:message code="label.delete"/></a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<form:form method="post" action="fs/create" commandName="newFolder">
    <table>
        <tr>
            <td>
                <spring:message code="label.item.name"/>
            </td>
            <td>
                <form:input path="name"/>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="<spring:message code="label.add.folder"/>"/>
            </td>
        </tr>
    </table>
</form:form>

</body>
</html>
