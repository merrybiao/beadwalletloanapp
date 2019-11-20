<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = path;
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta content="telephone=no" name="format-detection">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,height=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
    <script src="<%=basePath%>/js/flex.js" type="text/javascript"></script>
    <link rel="stylesheet" href="<%=basePath%>/css/base.css">
    <title>逾期还款</title>
</head>

<body>
	<div class="repay-nav repay-late-san-nav">
        <div class="repay-lis repay-active">还款</div>
    </div>
    <div class="repay-content repay-late-san-content">
        <div class="repay-money">
            <p class="repay-info font-w">本次逾期未还本金（元）</p>
            <p class="repay-num" id="yqbj">111</p>
            <div class="late-warn font-w">
	            <p><strong>凡是未及时还款均视为逾期，将以借款金额1%/天进行罚息。</strong>请往扣款银行卡中充入足够金额以缴清还款，<strong>请您珍惜个人信用记录。</strong></p>
	        </div>
        </div>
        <div class="repay-detail">
            <p>申请时间：<span id="sqsj"></span></p>
            <p>逾期时长：<span id="day"></span></p>
            <p>逾期罚息：<span class="clr-warn font-y" id="faxi">111</span></p>
            <p>还款总额：<span class="clr-warn font-y" id="borrowAmonunt">111</span></p>
        </div>
        <div class="repay-kefu">
        	咨询客服：400-827-6999
        </div>
    </div>
</body>

</html>
    