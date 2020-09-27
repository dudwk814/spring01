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

    <div class="uploadResult">
        <ul>

        </ul>
    </div>

    <div class="bigPictureWrapper">
        <div class="bigPicture">
        </div>
    </div>

    <style>
        .uploadResult {
            width: 100%;
            background-color: gray;
        }

        .uploadResult ul {
            display: flex;
            flex-flow: row;
            justify-content: center;
            align-items: center;
        }

        .uploadResult ul li {
            list-style: none;
            padding: 10px;
        }

        .uploadResult ul li img {
            width: 100px;
        }

        .uploadResult ul li span {
            color: white;
        }

        .bigPictureWrapper {
            position: absolute;
            display: none;
            justify-content: center;
            align-items: center;
            top: 0%;
            width: 100%;
            height: 100%;
            background-color: gray;
            z-index: 100;
            background: rgba(255, 255, 255, 0.5);
        }

        .bigPicture {
            position: relative;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .bigPicture img {
            width: 600px;
        }
    </style>


    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script>

        function showImage(fileCallPath) {
            //  alert(fileCallPath)
            $(".bigPictureWrapper").css("display", "flex").show();

            $(".bigPicture").html("<img src='/display?fileName=" + encodeURI(fileCallPath) + "'>").animate({
                width: '100%',
                height: '100%'
            }, 1000);
        }

        $(".bigPictureWrapper").on("click", function (e) {
           $(".bigPicture").animate({width: '0%', height: '0%'}, 1000);
           setTimeout(function (){
               $(".bigPictureWrapper").hide();
           }, 1000);
        });

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
                    return false;
                }
                return true;
            }

            var cloneObj = $(".uploadDiv").clone();

            $("#uploadBtn").on("click", function (e) {
                var formData = new FormData();

                var inputFile = $("input[name='uploadFile']");

                var files = inputFile[0].files;

                console.log(files);

                // add filedata to formdata
                for (var i = 0; i < files.length; i++) {
                    if (!checkExtension(files[i].name, files[i].size)) {
                        return false;
                    }
                    formData.append("uploadFile", files[i]);
                }

                $.ajax({
                    url: '/uploadAjaxAction',
                    processData: false,
                    contentType: false,
                    data: formData,
                    type: 'POST',
                    dataType: 'json',
                    success: function (result) {
                        alert(result);

                        showUploadedFile(result);
                        $(".uploadDiv").html(cloneObj.html());
                    }
                }); //end ajax
            });

            var uploadResult = $(".uploadResult ul");

            function showUploadedFile(uploadResultArr) {

                var str = "";

                $(uploadResultArr).each(function (i, obj) {
                    if (!obj.image) {

                        var fileCallPath = encodeURIComponent(obj.uploadPath+"/"+obj.uuid+"_"+obj.fileName);

                        var fileLink = fileCallPath.replace(new RegExp(/\\/g),"/");

                        str += "<li><div><a href='/download?fileName=" + fileCallPath + "'>"+"<img src='/resources/img/attach.png'>" + obj.fileName + "</a>" +
                                "<span data-file=\'"+fileCallPath+"\' data-type='file'> x </sapn>" + "<div></li>";
                    } else {
                      //  str += "<li>" + obj.fileName + "</li>";

                        var fileCallPath = encodeURIComponent(obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName);

                       var originalPath = obj.uploadPath + "\\" + obj.uuid + "_" + obj.fileName;

                        originalPath = originalPath.replace(new RegExp(/\\/g), "/");

                        str += "<li><a href=\"javascript:showImage(\'"+originalPath+"\')\"> <img src='/display?fileName="+fileCallPath+"'></a>" +
                                "<span data-file=\'"+fileCallPath+"\' data-type='image'> x </span>" + "</li>";
                    }
                });

                uploadResult.append(str);

            }

            $(".uploadResult").on("click", "span", function (e) {

                var targetFile = $(this).data("file");
                var type = $(this).data("type");
                console.log(targetFile);

                $.ajax({
                    url: '/deleteFile',
                    data: {fileName: targetFile, type:type},
                    dataType: 'text',
                    type: 'POST',
                    success: function (result) {
                        alert(result);
                    }
                }); //$.ajax
            })

        });
    </script>
</body>
</html>
