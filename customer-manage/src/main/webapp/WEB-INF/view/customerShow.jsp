<%--
  User: bascker
  Date: 2018/9/2
  Time: 22:24
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>客户详情</title>
</head>
<body>
    <label>ID：</label>${customer.id}<br/>
    <label>用户名：</label>${customer.name}<br/>
    <label>联系人：</label>${customer.contact}<br/>
    <label>手机号：</label>${customer.telephone}<br/>
    <label>Email：</label>${customer.email}<br/>
    <label>备注：</label>${customer.remark}<br/>

    <a href="/customers">返回</a>
</body>
</html>
