<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	//String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
	String basePath = path;
%>
<html lang="zh-cn">
<head>
<meta charset="UTF-8">
<meta content="telephone=no" name="format-detection">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<title>签约失败</title>
<link rel="stylesheet" href="<%=path%>/css/sign_callback.css">
<script src="<%=path%>/js/flex.js" type="text/javascript"></script>
<script src="<%=path%>/js/flexble.js" type="text/javascript"></script>
<script src="<%=path%>/images/sign_fail.png" type="text/javascript"></script>
<script src="<%=basePath%>/js/jquery-1.11.3.min.js"
	type="text/javascript"></script>

<script src="flexble.js"></script>
<style type="text/css">
body {
	overflow: hidden;
	width: 10rem;
	height: 100%;
}

* {
	padding: 0px;
	margin: 0px;
}

.content-img {
	width: 115px;
	height: 115px;
	/*  margin-left:-75px;
        margin-top:-75px; */
	margin: 50% auto 0px auto;
}

.content-img span {
	width: 100%;
	height: 100%;
}

h2 {
	margin-top: 1rem;
	text-align: center;
	color: darkgrey;
}

.btn {
	background: #3f8dff;
	color: #ffffff;
	border-radius: 10px;
	text-align: center;
	line-height: 1rem;
	width: 2rem;
	margin: 1rem auto;
}
</style>
</head>
<body>
	<div class="content-img">
		<span class="fcontent_icon"></span>
	</div>
	<h2>'${message}'</h2>
	<div class="btn" onclick="window.location.href='${redirectUrl}';">返回</div>
</body>
</html>