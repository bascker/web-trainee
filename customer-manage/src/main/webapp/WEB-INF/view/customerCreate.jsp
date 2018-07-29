<%--
  User: bascker
  Date: 2018/7/21
  Time: 20:20
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<html>
<head>
    <title>客户管理 - 创建客户</title>
</head>
<body>
    <form action="/customers/create" method="post">
        <label>姓名</label>：<input type="text" name="name" value=""/><br/>
        <label>联系人</label>：<input type="text" name="contact" value=""/><br/>
        <label>电话号码</label>：<input type="text" name="telephone" value=""/><br/>
        <label>邮箱地址</label>：<input type="text" name="email" value=""/><br/>
        <label>备注</label>：<input type="text" name="remark" value=""/><br/>
        <input type="submit" value="保存"/>
    </form>
</body>
</html>
