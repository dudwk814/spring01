<%--
  Created by IntelliJ IDEA.
  User: PCY
  Date: 2020-09-26
  Time: 오후 2:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <div class="uploadDiv">
        <input type="file" name="uploadFile" multiple>
    </div>

    <button id="uploadBtn">Upload</button>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script>
        $(document).ready(function () {

            var regex = new RegExp("(.*?)\.(exe|sh|zip|zla)$");
            var maxSize = 1085660;

            function checkExtension(fileName, fileSize) {
                if (fileSize >= maxSize) {
                    alert("파일 사이즈 초과!");
                    return false;
                }

                if (regex.test(fileName)) {
                    alert("해당 종류의 파일은 업로드할 수 없습니다.");
                }
            }

            $("#uploadBtn").on("click", function (e) {
                var formData = new FormData();

                var inputFile = $("input[name='uploadFile']");

                var files = inputFile[0].files;

                console.log(files);

                // add filedata to formdata
                for (var i = 0; i < files.length; i++) {
                    formData.append("uploadFile", files[i]);
                }

                $.ajax({
                    url: '/uploadAjaxAction',
                    processData: false,
                    contentType: false,
                    data: formData,
                    type: 'POST',
                    success: function (result) {
                        alert("Uploaded");
                    }
                }); //end ajax
            });
        });
    </script>
</body>
</html>
