<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = path;
%>
<html lang="zh-cn">
<head>
<meta charset="UTF-8">
<meta content="telephone=no" name="format-detection">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<title>模拟测试</title>
<link rel="stylesheet" href="<%=path%>/css/sign_callback.css">
<link rel="stylesheet" href="<%=path%>/css/main.css">
<link rel="stylesheet" href="<%=path%>/css/loading.css">
<script src="<%=path%>/js/flex.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/jquery-1.11.3.min.js"
	type="text/javascript"></script>
<script language="javascript" type="text/javascript" src="<%=basePath%>/js/My97DatePicker/WdatePicker.js"></script>
<style type="text/css">
.bodyDiv {
	margin-left: 100px;
	margin-top: 50px;
}

.ulDiv {
	float: left;
}

.contentMainDiv {
	margin-left: 100px;
	float: left;
}

.contentDiv {
	width: 450px;
	margin-left: 100px;
	float: left;
	display: none;
}

ul li {
	cursor: pointer;
	color: #F52692;
	display: block;
	font-size: 18px;
	line-height: 30px;
	display: block;
	text-align: center;
	padding: 5px;
	font-family: helvetica, arial, sans-serif;
	
	text-shadow: 1px 1px white, -1px -1px #333;
}

.white-pink {
	margin-left: auto;
	margin-right: auto;
	max-width: 500px;
	background: #FFF;
	padding: 30px 30px 20px 30px;
	box-shadow: rgba(187, 187, 187, 1) 0 0px 20px -1px;
	-webkit-box-shadow: rgba(187, 187, 187, 1) 0 0px 20px -1px;
	font: 12px Arial, Helvetica, sans-serif;
	color: #666;
	border-radius: 10px;
	-webkit-border-radius: 10px;
}

.white-pink h1 {
	font: 24px "Trebuchet MS", Arial, Helvetica, sans-serif;
	padding: 0px 0px 10px 40px;
	display: block;
	border-bottom: 1px solid #F5F5F5;
	margin: -10px -30px 10px -30px;
	color: #969696;
}

.white-pink h1>span {
	display: block;
	font-size: 11px;
	color: #C4C2C2;
}

.white-pink label {
	display: block;
	margin: 0px 0px 5px;
}

.white-pink label>span {
	float: left;
	width: 20%;
	text-align: right;
	padding-right: 10px;
	margin-top: 10px;
	color: #969696;
}

.white-pink input[type="text"], .white-pink input[type="email"],
	.white-pink textarea, .white-pink select {
	color: #555;
	width: 70%;
	padding: 3px 0px 3px 5px;
	margin-top: 2px;
	margin-right: 6px;
	margin-bottom: 16px;
	border: 1px solid #e5e5e5;
	background: #fbfbfb;
	height: 25px;
	line-height: 15px;
	outline: 0;
	-webkit-box-shadow: inset 1px 1px 2px rgba(200, 200, 200, 0.2);
	box-shadow: inset 1px 1px 2px rgba(200, 200, 200, 0.2);
}

.white-pink textarea {
	height: 100px;
	padding: 5px 0px 0px 5px;
	width: 70%;
}

.white-pink .button {
	-moz-box-shadow: inset 0px 1px 0px 0px #fbafe3;
	-webkit-box-shadow: inset 0px 1px 0px 0px #fbafe3;
	box-shadow: inset 0px 1px 0px 0px #fbafe3;
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0.05, #ff5bb0
		), color-stop(1, #ef027d));
	background: -moz-linear-gradient(center top, #ff5bb0 5%, #ef027d 100%);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ff5bb0',
		endColorstr='#ef027d');
	background-color: #ff5bb0;
	border-radius: 9px;
	-webkit-border-radius: 9px;
	-moz-border-border-radius: 9px;
	border: 1px solid #ee1eb5;
	display: inline-block;
	color: #ffffff;
	font-family: Arial;
	font-size: 15px;
	font-weight: bold;
	font-style: normal;
	height: 40px;
	line-height: 30px;
	width: 100px;
	text-decoration: none;
	text-align: center;
	text-shadow: 1px 1px 0px #c70067;
}

.white-pink .button:hover {
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0.05, #ef027d
		), color-stop(1, #ff5bb0));
	background: -moz-linear-gradient(center top, #ef027d 5%, #ff5bb0 100%);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ef027d',
		endColorstr='#ff5bb0');
	background-color: #ef027d;
}

.white-pink .button:active {
	position: relative;
	top: 1px;
}

.white-pink select {
	background: url('down-arrow.png') no-repeat right,
		-moz-linear-gradient(top, #FBFBFB 0%, #E9E9E9 100%);
	background: url('down-arrow.png') no-repeat right,
		-webkit-gradient(linear, left top, left bottom, color-stop(0%, #FBFBFB),
		color-stop(100%, #E9E9E9));
	appearance: none;
	-webkit-appearance: none;
	-moz-appearance: none;
	text-indent: 0.01px;
	text-overflow: '';
	width: 70%;
	line-height: 15px;
	height: 30px;
}

.tu {
	text-shadow: -1px -1px white, 1px 1px #333;
}
</style>

<script>
	$(function() {
		
		function callback(data) {
			if ("OK" == data) {
				$(":text").val("");
			}
			alert(data);
		}
		
		var src = "../images/huli1.jpg";  
		var week = new Date().getDay();  
		switch (week) {  
				case 0 :  
						src="../images/huli1.jpg";  
						break;  
				case 1 :  
						src="../images/huli1.jpg";  
						break;  
				case 2 :  
						src="../images/huli2.jpg";  
						break;  
				case 3 :  
						src="../images/huli3.jpg";  
						break;  
				case 4 :  
						src="../images/huli4.jpg";  
						break;  
				case 5 :  
						src="../images/huli5.jpg";  
						break;  
				case 6 :  
						src="../images/huli1.jpg";   
						break;  
		}  
		$("#img").attr("src",src); 

		$("ul li").each(function(i, e) {
			$(this).click(function() {
				$("ul li").each(function() {
					$(this).css("font-size","18px");
					$(this).removeClass("tu");
				});
				$(this).addClass("tu");
				$(this).css("font-size","30px");
				$(".contentMainDiv").hide();
				$(".contentDiv").each(function(ii, ee) {
					if (i == ii) {
						$(this).show();
					} else {
						$(this).hide();
					}
				});
			});
		});

		// 删除用户
		$("#b1").click(function() {
			$.post("/beadwalletloanapp/testAnalog/testDeleteBorrower.do", {
				"phone" : $("#f1 #phone").val()
			}, function(data) {
				callback(data);
			});
		});

		// 绑定银行卡
		$("#b2").click(function() {
			$.post("/beadwalletloanapp/testAnalog/testSignBank.do", {
				"name" : $("#f2 #name").val(),
				"phone" : $("#f2 #phone").val(),
				"idCard" : $("#f2 #idCard").val(),
				"cardNo" : $("#f2 #cardNo").val()
			}, function(data) {
				callback(data);
			});
		});

		// 认证信息
		$("#b3").click(function() {
			$.post("/beadwalletloanapp/testAnalog/testOrderAuth.do", {
				"orderId" : $("#f3 #orderId").val(),
				"authType" : $("#f3 #authType").val()
			}, function(data) {
				callback(data);
			});
		});

		// 审核通过
		$("#b4").click(function() {
			$.post("/beadwalletloanapp/testAnalog/testAuditSuccess.do", {
				"orderId" : $("#f4 #orderId").val(),
				"phone" : $("#f4 #phone").val()
			}, function(data) {
				callback(data);
			});
		});

		// 放款
		$("#b5").click(function() {
			$.post("/beadwalletloanapp/testAnalog/analogMultiTermLoan.do", {
				"orderId" : $("#f5 #orderId").val(),
				"repayDate" : $("#f5 #repayDate").val()
			}, function(data) {
				callback(data);
			});
		});

		// 生成逾期记录
		$("#b6").click(function() {
			$.post("/beadwalletloanapp/testAnalog/analogMultiTermOverDue.do", {
				"orderId" : $("#f6 #orderId").val()
			}, function(data) {
				callback(data);
			});
		});

		// 清除还款数据
		$("#b7").click(function() {
			$.post("/beadwalletloanapp/testAnalog/clearMultiOrder.do", {
				"orderId" : $("#f7 #orderId").val()
			}, function(data) {
				callback(data);
			});
		});

		// 删除工单
		$("#b8").click(function() {
			$.post("/beadwalletloanapp/testAnalog/deleteOrderInfo.do", {
				"orderId" : $("#f8 #orderId").val(),
				"phone" : $("#f8 #phone").val()
			}, function(data) {
				callback(data);
			});
		});
	});
</script>
</head>
<body>
	<div class="bodyDiv">
		<div class="ulDiv">
			<ul>
				<li>删除用户</li>
				<li>绑定银行卡</li>
				<li>认证信息</li>
				<li>审核通过</li>
				<li>放款</li>
				<li>生成逾期记录</li>
				<li>清除还款数据</li>
				<li>删除工单</li>
			</ul>
		</div>
		<div class="contentMainDiv">
			<img id="img" alt="" src="../images/huli1.jpg" width="300px;" height="300px;">
		</div>
		<!-- 删除用户 -->
		<div class="contentDiv">
			<form id="f1" action="" method="post" class="white-pink">
				<h1>
					<span>删除用户</span>
				</h1>
				<label> <span>手机号 :</span> <input id="phone" type="text" name="phone"  />
				</label> 
				<label> <span>&nbsp;</span> <input id="b1" type="button"
					class="button" value="提交" />
				</label>
			</form>
		</div>
		<!-- 绑定银行卡 -->
		<div class="contentDiv">
			<form id="f2" action="" method="post" class="white-pink">
				<h1>
					<span>绑定银行卡</span>
				</h1>
				<label> <span>姓名 :</span> <input id="name" type="text" name="name"  />
				</label> 
				<label> <span>手机号 :</span> <input id="phone" type="text" name="phone"  />
				</label> 
				<label> <span>身份证号 :</span> <input id="idCard" type="text" name="idCard"  />
				</label> 
				<label> <span>银行卡号 :</span> <input id="cardNo" type="text" name="cardNo"  />
				</label> 
				<label> <span>&nbsp;</span> <input id="b2" type="button"
					class="button" value="提交" />
				</label>
			</form>
		</div>
		<!-- 认证信息 -->
		<div class="contentDiv">
			<form id="f3" action="" method="post" class="white-pink">
				<h1>
					<span>认证信息（认证类型，1：运营商 2：个人信息 3：拍照 4：芝麻信用 5：社保 6：公积金 7：邮箱 8：淘宝 9：京东）</span>
				</h1>
				<label> <span>工单Id :</span> <input id="orderId" type="text" name="orderId"  />
				</label> 
				<label> <span>认证类型:</span> <input id="authType" type="text" name="authType"  />
				</label> 
				<label> <span>&nbsp;</span> <input id="b3" type="button"
					class="button" value="提交" />
				</label>
			</form>
		</div>
		<!-- 审核通过 -->
		<div class="contentDiv">
			<form id="f4" action="" method="post" class="white-pink">
				<h1>
					<span>审核通过</span>
				</h1>
				<label> <span>工单Id :</span> <input id="orderId" type="text" name="orderId"  />
				</label> 
				<label> <span>手机号:</span> <input id="phone" type="text" name="phone"  />
				</label> 
				<label> <span>&nbsp;</span> <input id="b4" type="button"
					class="button" value="提交" />
				</label>
			</form>
		</div>
		<!-- 放款 -->
		<div class="contentDiv">
			<form id="f5" action="" method="post" class="white-pink">
				<h1>
					<span>放款 </span>
				</h1>
				<label> <span>工单Id :</span> <input id="orderId" type="text" name="orderId"  />
				</label> 
				<label> <span>放款时间:</span> <input id="repayDate" class="Wdate" type="text" 
					name="repayDate" onClick="WdatePicker()">
				</label> 
				<label> <span>&nbsp;</span> <input id="b5" type="button"
					class="button" value="提交" />
				</label>
			</form>
		</div>
		<!-- 生成逾期记录 -->
		<div class="contentDiv">
			<form id="f6" action="" method="post" class="white-pink">
				<h1>
					<span>生成逾期记录</span>
				</h1>
				<label> <span>工单Id :</span> <input id="orderId" type="text" name="orderId"  />
				</label> 
				<label> <span>&nbsp;</span> <input id="b6" type="button"
					class="button" value="提交" />
				</label>
			</form>
		</div>
		<!-- 清除还款数据 -->
		<div class="contentDiv">
			<form id="f7" action="" method="post" class="white-pink">
				<h1>
					<span>清除还款数据</span>
				</h1>
				<label> <span>工单Id :</span> <input id="orderId" type="text" name="orderId"  />
				</label> 
				<label> <span>&nbsp;</span> <input id="b7" type="button"
					class="button" value="提交" />
				</label>
			</form>
		</div>
		<!-- 删除工单 -->
		<div class="contentDiv">
			<form id="f8" action="" method="post" class="white-pink">
				<h1>
					<span>删除工单</span>
				</h1>
				<label> <span>工单Id :</span> <input id="orderId" type="text" name="orderId"  />
				</label> 
				<label> <span>手机号 :</span> <input id="phone" type="text" name="phone"  />
				</label> 
				<label> <span>&nbsp;</span> <input id="b8" type="button"
					class="button" value="提交" />
				</label>
			</form>
		</div>
	</div>
</body>
</html>



















