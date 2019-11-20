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
<title>绑卡成功</title>
<link rel="stylesheet" href="<%=path%>/css/sign_callback.css">
<script src="<%=path%>/js/flex.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/jquery-1.11.3.min.js"
	type="text/javascript"></script>
</head>
<body>
	<!--  code0091  -->
	<div class="scontent_wrapper">
		<span class="scontent_icon"></span>
		<p class="fp1">您已绑卡成功${result}</p>
		<p class="fp3">${respDesc}</p>
	</div>
</body>
</html>