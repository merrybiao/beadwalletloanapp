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
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
    <link rel="stylesheet" href="<%=basePath%>/css/main.css">
	<script src="<%=basePath%>/js/flex.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/jquery-1.11.3.min.js"></script>
    <title>续贷成功</title>
</head>

<body>
    <div class="chenggong">
    <div class="text-center">
        <img src="<%=basePath%>/images/ok.png" alt="成功" class="jingao">
        <p class="font-hei font-b m_t40">您的续贷已成功</p>
    </div>
    </div>
        <div class="cg-btn flex font-b">
            <button type="button" class="flex1" onclick="location='index.html'" id="index">返回首页</button>
            <button type="button" class="flex1" id="query">查看借款</button>
        </div>
    <script>
        $(window).load(function(){
            if($(".head_top").size()>0){
            $(".chenggong").height($(window).height()-$(".head_top").height()- $(".cg-btn").height())
            }
        });
      
        $(function() {
			$("#index").click(function() {
				index('success');
			});
			$("#query").click(function() {
				progress('success');
			});
			
		});
		function index(result) {
			myjs.index(result);
		}
		function progress(result) {
			myjs.progress(result);
		}
        
        
    </script>
</body>
</html>