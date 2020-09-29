<%--
  Created by IntelliJ IDEA.
  User: PCY
  Date: 2020-09-28
  Time: 오전 9:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>/sample/admin page</h1>

    <p>principal : <sec:authentication property="principal"/></p>
    <p>MemberVO : <sec:authentication property="principal.member"/></p>
    <p>사용자이름 : <sec:authentication property="principal.member.username"/></p>
    <p>사용자아이디 : <sec:authentication property="principal.member.userid"/></p>
    <p>사용자 권한 리스트 : <sec:authentication property="principal.member.authList"/></p>

    <a href="/customLogout">Logout</a>
</body>
</html>
