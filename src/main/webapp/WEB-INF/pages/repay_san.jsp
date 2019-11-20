<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
    <%
		String path = request.getContextPath();
		String basePath = path;
	%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta content="telephone=no" name="format-detection">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,height=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
    <script src="<%=basePath %>/js/flex.js" type="text/javascript"></script>
    <link rel="stylesheet" href="<%=basePath %>/css/base.css">
    <title>还款</title>
</head>

<body>
    <div class="repay-nav repay-san-nav">
        <div class="repay-lis repay-active">还款</div>
    </div>
    <div class="repay-content repay-san-content">
        <div class="repay-money">
            <p class="repay-info font-w">本次未还本金（元）</p>
            <p class="repay-num" id="whbj">${repay.realityRepayMoney}</p>
        </div>
        <div class="repay-detail">
            <p>申请时间：<span id="sqsj">
           		<fmt:formatDate value='${repay.createTime}'
							pattern='yyyy-MM-dd' />
            </span></p>
            <p>借款期限：<span id="jkqx">
            		<c:if test="${!empty repay.repayTerm}">
	            		${repay.repayTerm}个月
	            	</c:if>
            </span></p>
            <p>还款方式：<span id="hkfs">  
            	<c:if test="${!empty repay.repayType}">      
            		<c:if test="${repay.repayType==1}">
	            		先息后本
	            	</c:if>
	            	<c:if test="${repay.repayType==2}">
	            		等额本息
	            	</c:if>
	            </c:if> 	
            </span></p>
            <p>借款进度：<span>还款进行中</span></p>
            <p>下一还款时间：<span class="font-y" id="hksj">
            	<fmt:formatDate value='${repay.repayTime}'
							pattern='yyyy-MM-dd' />
            </span></p>
        </div>
        <div class="repay-kefu">
        	咨询客服：400-827-6999
        </div>
    </div>
</body>

</html>
