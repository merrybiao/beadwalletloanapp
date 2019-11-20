<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>支付方式</title>
	<script src="${ctx}/js/jquery-3.1.1.min.js1"></script>
	<style>
	    body {
	      margin: 0;
	    }
	    .body {
	      margin-top: 20px;
	    }
	    .img-box {
	      width: 150px;
	      margin: 20px 0 0 15px;
	      text-align: center;
	    }
	    img {
	      margin: 0 auto;
	      width: 100%;
	      height: auto;
	      vertical-align: middle;
	      text-align: center;
	    }
	</style>
</head>
<body>
	<div style="margin:20px 10px 0px 10px">
		<p>请选择支付方式：</p>
		<hr/>
		<div class="body">
		    
		    <div class="img-box" style="padding-left:5px"><a href="${ctx}/payTest/payCompactAction.action?merid=${param.merid}&key=${param.key}&test=${param.test}"><img src="${ctx}/images/wx.png1"></a></div>
		    <div class="img-box"><a href="${ctx}/payTest/alipayAction.action?merid=${param.merid}&key=${param.key}&test=${param.test}"><img src="${ctx}/images/zfb.png1"></a></div>
		    <div class="img-box"><a href="${ctx}/payTest/alijspayAction.action?merid=${param.merid}&key=${param.key}&test=${param.test}"><img src="${ctx}/images/zfb.png1"></a></div>
		    
		    <div style=" margin: 40px 0 0 20px">
			    <p>
			    	微信支付请在微信中操作；
			    </p>
			    <p>
			    	支付宝支付请在外部浏览器打开然后操作。
			    </p>
		    </div>
		</div>
		
	</div>
	<script>
	/*
	$(document).ready(function(){
		$.ajax({ url: "${ctx}/payTest/aliPayCompactAction.action?merid=${param.merid}&key=${param.key}&test=${param.test}",
			data: {},
			dataType: "html",
			method: "post",
			success: doResult
		});
	});
	*/
	/**
	 * 调用结果
	 */
	function doResult(data){
		alert(data);
	}
	</script>
</body>
</html>