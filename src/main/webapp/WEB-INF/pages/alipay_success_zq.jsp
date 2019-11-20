<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	//String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
	String basePath = path;
	String channel = request.getParameter("channel");
	String money = request.getParameter("money");
%>
<html lang="zh-cn">
<head>
<meta charset="UTF-8">
<meta content="telephone=no" name="format-detection">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<title>展期成功</title>
<link rel="stylesheet" href="<%=path%>/css/sign_callback.css">
<link rel="stylesheet" href="<%=path%>/css/main.css">
<link rel="stylesheet" href="<%=path%>/css/loading.css">
<script src="<%=path%>/js/flex.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/jquery-1.11.3.min.js"
	type="text/javascript"></script>
</head>
<style>
.wrap{
	width:100%;
	height:350px;
	text-align:center;
	padding-top:30px;
	box-sizing:border-box;
	padding-left:0.34rem;
	padding-right:0.34rem;
}

.top-word{
	font-size:18px;
}
.bottom-line1{
	margin-top:25px;
	line-height:30px;
	display:-webkit-box;
	display:-webkit-flex;
	display:-ms-flexbox;
	display:flex;
	font-size:16px;
}
.bottom-line2{
	line-height:30px;
	display:-webkit-box;
	display:-webkit-flex;
	display:-ms-flexbox;
	display:flex;
	margin-bottom:25px;
	font-size:16px;
}
.bottom-line1 .bottom-line1-left,.bottom-line2 .bottom-line2-left{
	-webkit-box-flex:6;
	-webkit-flex:6;
	-ms-flex:6;
	flex:6;
	text-align:left;
}
.bottom-line1 .bottom-line1-right,.bottom-line2 .bottom-line2-right{
	-webkit-box-flex:6;
	-webkit-flex:6;
	-ms-flex:6;
	flex:6;
	text-align:right;
	color:#aaaaaa;
}
.btn-click{
	width:90%;
	height:40px;
	background:#3f8dff;
	color:#fff;
	margin-left:5%;
	font-size:16px;
	border:none;
	outline:none;
	border-radius:4px;
	
}
</style>
<body>
   <!--  <div class="chenggong">
        <div class=" chenggong-success">
            <img src="<%=path%>/images/sign_succ.png" alt="成功" class="jingao">
            <p class="font-hei font-m m_t40">还款成功</p>
        </div>
    </div>
	    <div class="cg-btn1 flex font-b">
	        <button type="button" class="flex1 font-blue bg-hui"  id="fh">完成</button>
	    </div>  -->
	    <div class="wrap">
	    	<div class="top">
	    		<img src="<%=path%>/images/zq2.png" alt="成功" class="jingao"/>
	    		<p class="top-word">展期成功</p>
	    	</div>
	    </div>
	    <button class="btn-click" id="fh">完成</button>
    <script>
    $(window).load(function() {
        if ($(".head_top").size() > 0) {
            $(".wrap").height($(window).height() - $(".top").height() - $(".btn-click").height())
        }
    });
    var channel = <%=channel %>;
	$(function() {
		if(1 == channel ){
			$("#fh").click(function() {
				sign('success');
			});
		}else if(2 == channel){
			$("#fh").click(function() {
				signIos();
			});
		}
	});
	function sign(result) {
		myjs.sign(result);
	}
	function signIos() {
		window.self.location = "waterelephantloanhome://";  
	}
    </script>
</body>
</html>



















