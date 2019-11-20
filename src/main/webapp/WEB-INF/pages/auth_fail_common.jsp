<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
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
<title>认证失败</title>
<link rel="stylesheet" href="<%=path%>/css/sign_callback.css">
<link rel="stylesheet" href="<%=path%>/css/main.css">
<link rel="stylesheet" href="<%=path%>/css/loading.css">
<script src="<%=path%>/js/flex.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/jquery-1.11.3.min.js"
	type="text/javascript"></script>
</head>
<body>
    <div class="chenggong">
        <div class="text-center">
            <img src="<%=path%>/images/shibai_icon.png" alt="失败" class="jingao">
            <!-- code0084 -->
            <p class="font-hei font-m m_t40">
	            <c:if test="${msg == null}">认证失败</c:if>
	            <c:if test="${msg != null}">${msg}</c:if>
             </p>
        </div>
    </div>
    <!-- <div class="cg-btn1 flex font-b">
        <button type="button" class="flex1 font-blue bg-hui"  id="fh">返回</button>
    </div> -->
    <script src="js/flex.js" type="text/javascript"></script>
    <script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js" type="text/javascript"></script>
    <script>
    $(window).load(function() {
        if ($(".head_top").size() > 0) {
            $(".chenggong").height($(window).height() - $(".head_top").height() - $(".cg-btn1").height())
        }
    });
   
	//$(function() {
		//$("#fh").click(function() {
		//	sign('fail');
		//});
	//});
	function sign(result) {
		
		myjs.sign(result);
	}
    </script>
</body>
</html>



















