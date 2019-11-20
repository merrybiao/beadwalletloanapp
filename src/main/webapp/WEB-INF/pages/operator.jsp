<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
		String path = request.getContextPath();
		//String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
		String basePath = path;
	%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta content="telephone=no" name="format-detection">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
	<link href="<%=basePath%>/css/main.css" rel="stylesheet" type="text/css"/>
	<script type="text/javascript" src="<%=basePath%>/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/js/flex.js">
	</script>
    <title>运营商信息</title>
</head>

<body>
<div class="tan" style="display:none">
		<div class="neirong">
			<div class="tan_head font-blue">运营商验证码</div>
			<div class="tan_body">
				<p class="shuru">输入您收到的验证码</p>
				<div id="messAgeText" style="display: none;">
					<p class="yanzhengma clearfix"><input type="" id="msgCode" name="msgCode" class="fl"><span id="timeCode" class="fasong fr" onclick="getPhoneCode(this,60)">发送验证码</span></p>
					<p class="err">验证码输入错误</p>
				</div>
				<div id="img" style="display: none;">
					<p class="yanzhengma clearfix"><input type="" id="picCode" name="picCode" class="fl"><span class="tuxing fr"><img src="" id="picCodeImg" onclick="getImgCode()" width="100%" height="100%"/></span></p>
					<p class="err">图形验证码错误</p>
				</div>
			</div>
			<div class="tan_foot">
					<button type="button">取消</button>
					<button type="button" id="valiateButton">确定</button>
			</div>
			<div class="loading2 text-center">
				<img src="<%=basePath%>/images/loading_2.gif" >
                   <p>正在处理中，请稍等...</p>
           </div>
		</div>
</div>
    <div class="yunyingshang2">
		<div><img src="<%=basePath%>/images/yunyingshang2.png" width="100%" class="block"></div>
		<div class="form2">
			<div class="fonm_group font-hei">
					<span class="font-w-b">真实姓名</span>
					<span><input type="text" name="" class="text-right" placeholder="请填写真实姓名" class="noNull" notNull="真实姓名不能为空" id="name" name="name" value="${name}" readonly="readonly"></span>
			</div>
			<div class="fonm_group font-hei">
					<span class="font-w-b">身份证号码</span>
					<span><input type="text" name="" class="text-right" placeholder="请填写您的身份证号码" class="noNull" notNull="身份证号码不能为空" id="idCard" name="idCard" value="${idCard}" readonly="readonly"></span>
			</div>
			<div class="fonm_group font-hei">
					<span class="font-w-b">电话号码</span>
					<span><input type="text" name="" class="text-right" placeholder="请填写当前手机号码" id="mobile" name="mobile" class="noNull" notNull="手机号码不能为空" value="${phone}" readonly="readonly"></span>
			</div>
			<div class="fonm_group font-hei">
					<span class="font-w-b">运营商密码</span>
					<span><input type="password" name="" class="text-right" placeholder="请填写您的运营商密码" class="noNull" notNull="运营商密码不能为空" id="password" name="password" ></span>
			</div>
			<a href="" class="fr font-blue font-m m_t30">忘记运营商密码？</a>
		</div>
		<div class="next_ relative"><button type="button" class="btn font-bai" id="buttonSub">运营商认证</button><div class="load" style="display:none"><img src="<%=basePath%>/images/load.gif" /></div></div>
		 <!-- 跳转的类别 -->
		<input type="hidden" id="vailteType" value="0" />
		<!-- 跳转的接口方法  -->
		<input type="hidden" id="vailteMethod" />
		<!-- 短信验证码  -->
		<input type="hidden" id="vailteMsg" />
		<!-- 图片验证码  -->
		<input type="hidden" id="vailteImg" />
		 <!--dataType-->
         <input type="hidden" id="dataType" />
    </div>
	<script src="<%=basePath%>/js/operator.js" type="text/javascript"></script>
<script type="text/javascript">
var wait = 0;
/* var bwId = getParameter("userId");
var orderId = getParameter("orderId"); */
var bwId = "${userId}"
var orderId = "${orderId}"
$(function() {

	 $(".yunyingshang2").height($(window).height());
	
    //提交
    $("#buttonSub").click(function() {
		$(".tan .tan_foot button").eq(0).one('click',function(){
			$(".tan").hide();
		});
		//$(".yanzhengma input").on("blur",function(){
		//	$(this).parent().next(".err").addClass("on");
		//	$(this).one("focus",function(){
			//	$(this).parent().next(".err").removeClass("on");
			//});
		//})
        var status = 'true';
        $(".noNull").each(function() {
            if ($(this).val() == "") {
                alert($(this).attr('notNull'));
                status = 'false';
                return ;
            }
        })
        if (status == 'true') {
			operator();
        }
    });

    //验证码
    $("#valiateButton").click(function() {
		var status="true";
        //方法
        var method = $("#vailteMethod").val();
        //图片
        var picCode = $("#picCode").val();
        //验证码
        var vailteText = $("#msgCode").val();
		var name=$("#name").val();
		var idCard=$("#idCard").val();
		var mobile=$("#mobile").val();
		var password=$("#password").val();
        var url = '';
        var data = '';
        if ($("#vailteImg").val() == "1") {
            if ($.trim(picCode) == "" || $.trim(picCode) == null || $.trim(picCode) == undefined) {
                alert("图片验证码不能为空");
				status="false";
                return;
            }
        }
        if ($("#vailteMsg").val() == "1") {
            if ($.trim(vailteText) == "" || $.trim(vailteText) == null || $.trim(vailteText) == undefined) {
                alert("短信验证码不能为空");
				status="false";
                return;
            }
        }
        if (method == "login") {
            url = "/beadwalletloanapp/bewadwallet/loan/operator.do";
            data = "phone=" + mobile + "&idCard=" + idCard + "&pwd=" + password + "&userId=" + bwId  + "&name=" + name;
            //判断是否有短信验证码
            if ($("#vailteMsg").val() == "1") {
                data += "&msgCode=" + $("#msgCode").val();
            }

            //判断是否有短信验证码
            if ($("#vailteImg").val() == "1") {
                data += "&picCode=" + $("#picCode").val();
            }
        } else if (method == "submitMessageCode") {
            url = "/beadwalletloanapp/bewadwallet/loan/submitMsgCode.do";
            data = "phone=" + mobile + "&msgCode=" + $("#msgCode").val() + "&userId=" + bwId + "&picCode=" + $("#picCode").val() + "&name=" + name + "&idCard=" + idCard;
        } else if (method == "getMsgCode") {
            url = "/beadwalletloanapp/bewadwallet/loan/getMsgCode.do";
            data = "phone=" + mobile + "&idCard=" + idCard + "&pwd=" + password + "&userId=" + bwId  + "&name=" + name;
        } else if (method == "getPicCode") {
            url = "/beadwalletloanapp/bewadwallet/loan/getPicCode.do";
            data = "phone=" + mobile + "&idCard=" + idCard + "&pwd=" + password + "&userId=" + bwId  + "&name=" + name;
        }
        $(this).parents(".tan").find(".tan_body,.tan_foot").hide().end().find(".loading2").show();
        var o=this;
        if (status == "true") {
            //发送ajax
            $.ajax({
                type: "post",
                url: url,
                dataType: "json",
                data: data,
                success: function(obj) {
						if (obj.code == "000") {
							if ($("#vailteMethod").val() == "login" && obj.result == null) {
								$("#messAgeText").hide();
								$("#img").hide();
								$(o).parents(".tan").find(".tan_body,.tan_foot").hide().end().find(".loading2").hide();
								getData(bwId);
								return ;
							}
							if ($("#vailteMethod").val() == "submitMessageCode" && obj.result != null) {
								$(".neirong").hide();							
								$(o).parents(".tan").find(".tan_body,.tan_foot").hide().end().find(".loading2").hide();
								getData(bwId);
								return ;
							}
							$(o).parents(".tan").find(".tan_body,.tan_foot").show().end().find(".loading2").hide();
							var piccodeType = obj.result.piccodeType;
							if (piccodeType != "" && piccodeType != null && piccodeType != undefined) {
								$("#dataType").val(piccodeType);
							}
							var messagecodeType = obj.result.messagecodeType;
							if (messagecodeType != "" && messagecodeType != null && messagecodeType != undefined) {
								$("#dataType").val(messagecodeType);
							}
							if ($("#vailteMethod").val() == "login" || $("#vailteMethod").val() == "submitMessageCode") {
								//vailteMethod
								var method = obj.result.method;
								$("#vailteMethod").val(method);
								//图片验证码
								var imgMsg = obj.result.param2;
								if (imgMsg == "picCode") {
									$("#vailteImg").val("1");
								} else {
									$("#vailteImg").val("0");
								}
								//短信验证码
								var textMsg = obj.result.param1;
								if (textMsg == "msgCode") {
									$("#vailteMsg").val("1");
								} else {
									$("#vailteMsg").val("0");
								}
								//短信验证码 
								if ($("#vailteImg").val() == "1") {
									//是否为图片验证码
									var imgMsg = obj.result.param2;
									if (imgMsg == "picCode") {
										$("#vailteImg").val("1");
										//隐藏
										$("#messAgeText").hide();
										//清空
										$("#picCode").val("");
										$("#picCodeImg").attr("src", "data:image/jpeg;base64," + obj.result.picCode);
										$("#img").show();
									}
								}


								//图片验证码
								if ($("#vailteMsg").val() == "1") {
									var textMsg = obj.result.param1;
									if (textMsg == "msgCode") {
										//清空
										$("#msgCode").val("");
										//倒计时
										time_end(60);
										$("#vailteMsg").val("1");
										//隐藏
										$("#img").hide();
										$("#messAgeText").show();
									}
								}

							} else {
								//getMsgCode
								if ($("#vailteMethod").val() == "getMsgCode") {
									//倒计时
									time_end(60);
									//清空
									$("#msgCode").val("");
								}
								//getPicCode
								if ($("#vailteMethod").val() == "getPicCode") {
									$("#picCodeImg").attr("src", "data:image/jpeg;base64," + obj.result.picCode);
								}
							}
						} else {
							alert(obj.msg);
							$(o).parents(".tan").find(".tan_body,.tan_foot").hide().end().find(".loading2").hide();
							//回退
							getImgCode();
							
							if (obj.msg == "手机号与服务密码不匹配，请重新输入服务密码") {
								window.location.reload();
							}
						}
                    } 

                });
			}
		
    });



    //忘记密码

    $("#forgetPass").click(function() {
        window.location.href = "http://139.196.253.158/loanpage/findpw.html";
    });

});




//发送短信验证码
function getPhoneCode(obj, waitTime) {
    if (wait == 0) {
		var phone=$("#mobile").val();
        $.post('/beadwalletloanapp/bewadwallet/loan/refreshMsgCode.do', {
            "phone": phone,
            "userId": bwId,
            "messagecodeType": $("#dataType").val()
        }, function(data) {
			
			if(data.code=="101"){
				alert(data.msg);
			}else{
				wait = waitTime;
				time(obj, waitTime);
			}
        });
    }
}

//倒计时
function time(o, waitTime) {
	
    if (wait == 0) {
		$(o).removeClass("on");
        o.innerText = "获取验证码";
    } else {
		$(o).addClass("on");
        o.innerText = "重新发送(" + wait + ")";
        wait--;
        setTimeout(function() {
                time(o, wait)
            },
            1000)
    }
}
//倒计时
function time_end(waitTime) {
    wait = waitTime;
    if (wait == 0) {
        $("#timeCode").removeClass("on").html("获取验证码");
    } else {
        $("#timeCode").addClass("on").html("重新发送(" + wait + ")");
        wait--;
        setTimeout(function() {
                time_end(wait)
            },
            1000)
    }
}

//图片验证码
function getImgCode() {
	var phone=$("#mobile").val();
    $.post('/beadwalletloanapp/bewadwallet/loan/refreshPicCode.do', {
        "phone": phone,
        "userId": bwId,
        "piccodeType": $("#dataType").val()
    }, function(data) {
        if (data.code != '000') {
            alert("验证码发送失败");
        } else {
            $("#picCodeImg").attr("src", "data:image/jpeg;base64," + data.result);
        }
    });
}
//特殊只读属性
function setReadOnly(id) {
    $("#" + id).attr("readonly", "readonly");
}
//拉取数据
function getData(userId){
	var mobile=$("#mobile").val();
	$.post('/beadwalletloanapp/bewadwallet/loan/getData.do', {"phone":mobile},function(data){
		$(".load").hide();
		$(".tan").hide();
		if(data.code == '102'){
			//留在当前页
			window.location.reload();
		}
		if (data.code != '000') {
            alert("运营商认证失败");
        }else{
			alert("运营商认证成功");
		}
		window.location.href="/beadwalletloanapp/bewadwallet/loan/yunYingShangResult.do?code="+data.code+"&userId="+bwId+"&orderId="+orderId; 
	});
}
//运营商信息
function operator(){
	var idCard=$("#idCard").val();
	var mobile=$("#mobile").val();
	var password=$("#password").val();
	if (!validate(mobile, "mobile")) {
			alert('请输入有效的手机号码！');
			return ;
	}
	if (!validate(idCard, "IDcard")) {
		alert("请输入正确的身份证号");
		return ;
	}
	$(".load").show();
	 //发送ajax 运营商是否跳转
	$.ajax({
		type: "post",
		url: "/beadwalletloanapp/bewadwallet/loan/operator.do",
		dataType: "json",
		data: {
			'phone': mobile,
			'pwd': password,
			'userId': bwId,
			'orderId':orderId
		},
		success: function(obj) {
			var status = obj.code;
			if (status == "000") {
				if (obj.result == "" || obj.result == null || obj.result == undefined) {
					//直接跳转
					$("#vailteType").val("1");
					$(".tan").hide();
					//alert("认证成功！！！");
				} else {
					var method = obj.result.method;
					$("#vailteMethod").val(method);
					//图片验证码
					var imgMsg = obj.result.param2;
					if (imgMsg == "picCode") {
						$("#vailteImg").val("1");
						$("#picCodeImg").attr("src", "data:image/jpeg;base64," + obj.result.picCode);
					}
					//短信验证码
					var textMsg = obj.result.param1;
					if (textMsg == "msgCode") {
						$("#vailteMsg").val("1");
					}
					//dataType
					var piccodeType = obj.result.piccodeType;
					if (piccodeType != "" && piccodeType != null && piccodeType != undefined) {
						$("#dataType").val(piccodeType);
					}
					var messagecodeType = obj.result.messagecodeType;
					if (messagecodeType != "" && messagecodeType != null && messagecodeType != undefined) {
						$("#dataType").val(messagecodeType);
					}
				}
				//直接跳转
				var vailteType = $("#vailteType").val();
				//判断是否有短信验证码
				if ($("#vailteMsg").val() == "1") {
					$("#msgCode").val("");
					//倒计时
					time_end(60);
					$("#img").hide();
					$("#messAgeText").show();
				}
				
				//判断是否有图片验证码
				if ($("#vailteImg").val() == "1") {
					$("#picCode").val("");
					$("#messAgeText").hide();
					$("#img").show();
				}
				 //直接跳转
				if ($("#vailteType").val() == "1") {
					$(".tan").hide();
					//alert("认证成功！！！");
				}
				$(".load").hide();
				getData(bwId);
			/* 	$(".tan").show(); */
			} else {
				alert(obj.msg);
				$(".load").hide();
				$(".tan").hide();
			}
		}
	});
}
</script>
</body>
</html>

    