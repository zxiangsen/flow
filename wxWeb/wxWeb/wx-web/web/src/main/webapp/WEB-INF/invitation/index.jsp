<%@ page language="java" contentType="text/html; charset = UTF-8"
         pageEncoding="UTF-8"
%>

<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
    <meta charset="utf-8"/>
    <script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
    <script type="text/javascript" src="../../js/fontAuto.js"></script>

    <title>商家邀请</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            list-style: none;
        }

        html, body, .web {
            width: 100%;
            height: 100%;
        }

        img {
            width: 100%;
        }

        .title {
            box-sizing: border-box;
            padding: 5rem 4rem 3.5rem;
            color: rgb(55, 58, 65);
            font-size: 17px;
            font-weight: bold;
        }

        .name, .card {
            width: 29.5rem;
            height: 3rem;
            border-radius: 10px;
            border: 4px solid rgb(238, 246, 254);
            margin: 0 auto;
            display: block;
            padding-left: 4px;
            outline: none;
        }

        .name {
            margin-bottom: 2.5rem;
        }

        .together {
            display: flex;
            width: 29.5rem;
            margin: 2.5rem auto;
            justify-content: space-around;
        }

        .together .phone {
            border-radius: 10px;
            width: 20.5rem;
            height: 3rem;
            border: 4px solid rgb(238, 246, 254);
            margin-left: -4px;
            padding-left: 4px;
            outline: none;
        }

        .together #yzm {
            width: 8rem;
            border-radius: 10px;
            border: 4px solid rgb(51, 145, 243);
            color: rgb(51, 145, 243);
            display: flex;
            align-items: center;
            justify-content: center;
            margin-right: -4px;
            background: #fff;
            outline: none;
        }

        .code {
            width: 11.5rem;
            height: 3rem;
            border: 4px solid rgb(238, 246, 254);
            margin-left: 4rem;
            border-radius: 10px;
            padding-left: 4px;
            outline: none;
        }

        .tj {
            width: 29.5rem;
            height: 4rem;
            background-color: rgb(51, 145, 243);
            color: #fff;
            font-size: 13px;
            display: flex;
            align-items: center;
            text-align: center;
            margin: 2.5rem auto 0;
            display: block;
            outline: none;
            border: none;
            border-radius: 10px;
        }

    </style>
</head>

<body>
<img src="https://douyabucket.oss-cn-shenzhen.aliyuncs.com/appImage/bg_join.png"/>
<div class="title">
   ${nickName}向你发出加盟邀请
</div>
<input type="text" placeholder="姓名" class="name"/>
<input type="text" placeholder="身份证" class="card"/>
<div class="together">
    <input type="tel" placeholder="手机号码" class="phone"/>
    <button id="yzm">发送验证码</button>
</div>
<input type="number" placeholder="验证码" maxlength="6" class="code"/>
<button class="tj">提交</button>
<script>
    (function (doc, win) {
        var docEl = doc.documentElement,
            resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
            recalc = function () {
                var clientWidth = docEl.clientWidth;
                if (!clientWidth) return;
                docEl.style.fontSize = 10 * (clientWidth / 375) + 'px';
            };

        if (!doc.addEventListener) return;
        win.addEventListener(resizeEvt, recalc, false);
        doc.addEventListener('DOMContentLoaded', recalc, false);
    })(document, window);
</script>

<script type="text/javascript">
    var msgid = "";
    window.onload = function () {
        var button = document.getElementById("yzm");

        button.innerText = "发送验证码";
        var timer = null;
            button.onclick = function () {
                var phone = $(".phone").val()
                var code = $(".code").val()
                var yzm = $(".yzm").val()
                if(phone==""){
                    alert("手机号未输入")
                }
                else{
                    clearInterval(timer);//这句话至关重要
                    var time = 60;
                    timer = setInterval(function () {
                        //console.log(time);
                        if (time <= 0) {
                            button.innerText = "";
                            button.innerText = "点击重新发送";
                            button.disabled = false;
                        } else {
                            button.disabled = true;
                            button.innerText = "";
                            button.innerText = "剩余" + (time) + "秒";
                            time--;
                        }
                    }, 1000);

                    $.ajax({
                        type: "get",
                        url: "../../msg/getCode?phone=" + phone + "&type=1",
                        async: false,
                        dataType: 'json',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        success: function (data) {
                            console.log(data)
                            msgid = data.data.msg_Id
                        }
                    });
                }

        }

    }
    $(".tj").click(function () {
        var code = $(".code").val();
        $.ajax({
            type: "get",
            url: "../../msg/isValidCode?code=" + code + "&msg_id=" + msgid,
            async: false,
            dataType: 'json',
            header: {
                "Content-Type": "application/json"
            },
            success: function (data) {
                var card = $(".card").val();
                var name = $(".name").val();
                var phone = $(".phone").val();
                if (data.code == 200) {
                    var data = {
                        name: name,
                        phone: phone,
                        idCard: card,
                        userId:${userId}
                    };
                    console.log(data)
                    $.ajax({
                        type: "get",
                        url: "../../apply/add?name=" + name + "&phone=" + phone + "&idCard=" + card + "&userId=" + (${userId}),
                        async: false,
                        dataType: 'json',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        success: function (data) {
                            if (data.code == 200) {
                                alert("提交成功")
                            } else {
                                alert(data.msg)
                            }
                        }
                    });
                } else {
                    alert(data.msg)
                }
            }
        });
    })

</script>
</body>
</html>
