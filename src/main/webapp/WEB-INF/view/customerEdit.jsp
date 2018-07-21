<%--
  User: tanlang
  Date: 2018/7/22
  Time: 18:28
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>编辑客户信息</title>
</head>
<body>

    <form action="/customers/edit" method="post">
        <input type="hidden" name="id" value="${customer.id}"/>
        <label>姓名</label>：<input type="text" name="name" value="${customer.name}"/><br/>
        <label>联系人</label>：<input type="text" name="contact" value="${customer.contact}"/><br/>
        <label>电话号码</label>：<input type="text" name="telephone" value="${customer.telephone}"/><br/>
        <label>邮箱地址</label>：<input type="text" name="email" value="${customer.email}"/><br/>
        <label>备注</label>：<input type="text" name="remark" value="${customer.remark}"/><br/>
        <input type="submit" value="保存"/>
    </form>
</body>
</html>
