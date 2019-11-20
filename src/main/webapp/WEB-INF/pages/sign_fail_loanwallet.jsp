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
<script src="<%=basePath%>/js/jquery-1.11.3.min.js"
	type="text/javascript"></script>
</head>
<body>
	<div class="fcontent_wrapper">
		<span class="fcontent_icon"></span>
		<p class="fp1">亲,您的签约未成功,${result}</p>
		<p class="fp2">非常抱歉给您带来不便</p>
		<p class="fp3">${respDesc}</p>
	</div>
	<!-- <script>
		$(function() {
			$(".fbtn_close").click(function() {
				history.back(-2);
			});
		});
	</script> -->
</body>
</html>