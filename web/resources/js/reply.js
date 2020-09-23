console.log("Reply Module............");

var replyService = (function () {

    // 댓글 등록 함수
    function add(reply, callback, error) {
        console.log("add reply..........");

        $.ajax({
           type : 'post',
           url : '/replies/new',
           data : JSON.stringify(reply),
           contentType : "application/json; charset=utf-8",
           success : function (result, status, xhr) {
               if (callback) {
                   callback(result);
               }
           },
           error : function (xhr, status, er) {
               if (error) {
                   error(er);
               }
           }
        });
    }

    //댓글 목록 함수
    	function getList(param, callback, error) {

		var bno = param.bno;
		var page = param.page || 1;

		$.getJSON("/replies/pages/" + bno + "/" + page + ".json",
				function(data) {
					if (callback) {
						callback(data);
					}
				}).fail(function(xhr, status, err) {
			if (error) {
				error();
			}
		});
	}

	// 댓글 삭제 함수
    function remove(rno, callback, error) {
        $.ajax({
            type: 'delete',
            url: '/replies/' + rno,
            success: function (deleteResult, status, xhr) {
                if (callback) {
                    callback(deleteResult);
                }
            },
            error: function (xhr, status, er) {
                if (error) {
                    error(er);
                }
            }
        });
    }

    //댓글 수정 함수
    function update(reply, callback, error) {

        console.log("RNO : " + reply.rno);

        $.ajax({
            type: 'put',
            url: '/replies/' + reply.rno,
            data: JSON.stringify(reply),
            contentType: "application/json; charset=utf-8",
            success: function (result, status, xhr) {
                if (callback) {
                    callback(result);
                }
            },
            error: function (xhr, status, er) {
                if (error) {
                    error(er);
                }
            }
        });
    }

    // 댓글 조회 함수
    function get(rno, callback, error) {

        $.get("/replies/" + rno + ".json", function (result) {
            if (callback) {
                callback(result);
            }
        }).fail(function (xhr, status, err) {
            if (err) {
                error();
            }
        });
    }
    return {
        add : add,
        getList : getList,
        remove : remove,
        update : update,
        get: get
    };

})();