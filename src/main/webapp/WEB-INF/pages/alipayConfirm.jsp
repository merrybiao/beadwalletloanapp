<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>


<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>支付结果</title>
	<script src="${ctx}/js/jquery-3.1.1.min.js1"></script>
	<script src="${ctx}/js/callalipayna_v4.js"></script>
	<!-- <script src="http://pay.ebjfinance.com/js/callalipayna_v4.js"></script> -->
		
	<script type="text/javascript">
		// 商户订单号
		var merchantOrderNo = '${merchantOutOrderNo}';
				
		// 调用10次
		var callTimes = 0;
		// 定时器句柄
		var intervalObj = null;
		
		$(document).ready(function(){
			var payResult = "${payResult}";
			// 如果不是支付成功，不停地向服务器发起请求
			if(payResult != 'success') {
				intervalObj = setInterval(runAjax, 2000);  
			}
			else {
				// $('#payResult').html("支付成功");
				$('#payResult').css("display", 'none');
				$('#main').css("display", 'block');
			}
		});
		
		/**
		 * 远程请求数据
		 */
		function runAjax() {
			callTimes++;
			// 如果调用大于10次，则清除定时器
			if(callTimes >= 10){
				clearInterval(intervalObj);
				return;
			}
			// alert(callTimes);
			
			$.ajax({ url: "${ctx}/ybjAlipay/alipayQuery.do",
				data: {merchantOutOrderNo: merchantOrderNo},
				dataType: "json",
				method: "post",
				success: doResult
			});
		}
		
		/**
		 * 调用结果
		 */
		function doResult(json){
			var payResult = json.payResult;
			if(payResult == true) {
				$('#payResult').css("display", 'none');
				$('#main').css("display", 'block');
				clearInterval(intervalObj);
			}
			else {
				// alert(payResult);
			}
		}
	</script>
	  <style>
    html {
      font-family: sans-serif; /* 1 */
      line-height: 1.15; /* 2 */
      -ms-text-size-adjust: 100%; /* 3 */
      -webkit-text-size-adjust: 100%; /* 3 */
    }
    body {
      margin: 0;
    }
    .img-box img {
      display: block;
      width: 100%;
      height: auto;
    }
  </style>
</head>

<body>
	
	<div id="payResult" style="margin:20px 10px 0px 10px; display:block">请在支付宝应用中完成支付......</div>
	<div class="main" id="main" style="display:none">
	    <div class="img-box">
	      <img src="${ctx}/images/f3.png1">
	    </div>
	</div>
	
	<script type="text/javascript">
		var returnURL = "${returnURL}";
		callappna.callAlipay(returnURL);
		
	</script>
	
</body>
</html>