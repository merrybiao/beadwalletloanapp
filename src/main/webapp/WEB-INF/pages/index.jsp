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
<meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
<link href="<%=basePath%>/css/main.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="<%=basePath%>/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/flex.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/main.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/common-index.js"></script>
<script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>/js/qq.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/index.js"></script>
<title>借款</title>
</head>
<body>
<div class="context" id="context">
	<div id="jiekuan" style="display:block">
  <div class="header">
    <div class="bannar">
        <img src="<%=basePath%>/images/banner1.png" class="block" />
    </div>
</div>
<div class="body">
    <img src="<%=basePath%>/images/guanggao.jpg" class="block" />
    <p >
          <button type="button" class="btn font-bai  btn1 bs "  id="loan" onclick="borrowMoney()">我要借款</button>
        <button type="button" class="btn font-bai  btn1 bs none"  id="progress" onclick="location='http://139.224.17.43/loanpage/review.html'">查看借款进度</button>
        <button type="button" class="btn font-bai  btn1 bs none"  id="repay" onclick="location='http://139.224.17.43/loanpage/repay.html'">我要还款/续贷</button>
    </p>  
</div></div>
</div>	
	<div class="nav">
		<ul>
			<li class="active" data-url='index1.html'>借款</li>
			<li onclick='window.location.href="http://139.224.17.43/loanpage/repay.html"'>还款</li>
			<li onclick='window.location.href="http://139.224.17.43/loanpage/account_info.html"'>个人</li>
		</ul>
	</div>
</body>
</html>