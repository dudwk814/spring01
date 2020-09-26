<%--
  Created by IntelliJ IDEA.
  User: PCY
  Date: 2020-09-26
  Time: 오후 1:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

    <form action="/uploadFormAction" method="post" enctype="multipart/form-data">
        <input type="file" name="uploadFile" multiple>
        <button>Submit</button>
    </form>

</body>
</html>
