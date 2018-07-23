<%--
  User: bascker
  Date: 2018/7/22
  Time: 17:50
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="BASE" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>客户管理</title>
</head>
<body>
    <div>
        <a href="/customers/create" name="btn-create">新增</a>
    </div>
    <table>
        <thead>
            <tr>
                <th>客户名称</th>
                <th>联系人</th>
                <th>电话号码</th>
                <th>邮箱</th>
                <th>备注</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="customer" items="${customers}">
                <tr>
                    <td>${customer.name}</td>
                    <td>${customer.contact}</td>
                    <td>${customer.telephone}</td>
                    <td>${customer.email}</td>
                    <td>${customer.remark}</td>
                    <td>
                        <a href="${BASE}/customers/edit?id=${customer.id}">编辑</a>&nbsp;&nbsp;
                        <a href="${BASE}/customers/delete?id=${customer.id}">删除</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
