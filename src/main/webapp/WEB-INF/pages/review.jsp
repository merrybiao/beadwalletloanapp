<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
    <script src="<%=basePath%>/js/jquery-1.11.3.min.js"></script>
    <script src="<%=basePath%>/js/flex.js" type="text/javascript"></script>
    <link rel="stylesheet" href="<%=basePath%>/css/base.css">
    <link rel="stylesheet" href="<%=basePath%>/css/loading.css">
    <script src="<%=basePath%>/js/main.js" type="text/javascript"></script>
    <script src="<%=basePath%>/js/common-index.js"></script>
	<script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js" type="text/javascript"></script>
    <script src="<%=basePath%>/js/qq.js"></script>
    <title>水象金融</title>
</head>

<body>
         <input type="hidden" id="sign_status" /> <!-- 签约状态  去签约时候传递-->
		 <input type="hidden" id="orderId" /><!-- 工单id -->
		 <input type="hidden" id="borrowAmount" />  <!-- 借款金额    去签约时传递-->
    <div class="review-wrap">
    <!--暂未通过：review-failnow-state;永未通过：review-failforever-state; 已通过：review-pass-state； 放款中：review-loan-state； 审核中：review-cur-state-->
		<div class="review-state " id="show"></div>
		 <!-- 审核中 //////////////////-->
		<div class="review-cur" style="display: none;" id="shenghe">
            <p>请注意接听021客服号码来电<br>
            加速审核进程<br>
            <!-- <span onclick="location='RZshouye.html'">点击查看审核资料</span> -->
            </p>
        </div>
		
		
		 <!-- 审核已通过按钮 ////////////签约 -->
        <div class="loan-detail" style="display: none;" id="sign1">
            <p>借款金额：<span id="money"></span></p>
            <p>借款期限：<span id="repayTerm"></span><i class="qx-btn"></i></p>
            <p>还款方式：<span id="repayType"></span><i class="hkfs-btn"></i></p>
        </div>
        <div class="sign-btn" style="display: none;" id="sign2">
            <button onclick="sign()" id="signStatus"></button>
        </div>
       
		<!-- 放款中 //////////////-->
        <div class="loan-detail" style="display: none;" id="waitLoan">
            <p>借款金额：<span id="money2"></span></p>
            <p>借款期限：<span id="repayTerm2"></span><i class="qx-btn"></i></p>
            <p>还款方式：<span id="repayType2"></span><i class="hkfs-btn"></i></p>
        </div>
		
		
		<!-- 暂时未通过 -->
        <div class="review-fail" style="display: none;" id="zsNoPass">
            <dl>
                <dt>未通过原因：</dt>
                <dd id="rejectInfo"></dd>
            </dl>
            <dl>
                <dt>解决方法：</dt>
                <dd id="solve"></dd>
            </dl>
        </div>
		
		 <!-- 永久未通过 -->
        <div class="review-fail" style="display: none;" id="noPass">
            <dl>
                <dt>未通过原因：</dt>
                <dd id="rejectInfo2"></dd>
            </dl>
        </div>
		
		 <div class="kefu">
            咨询客服：400-810-8818
        </div>
        <!-- 已通过弹窗 -->
        <div class="popup popup-qx">
        	<div class="poup-cover"></div>
            <div class="popup-detail">
                <h3>说明</h3>
                <p>借款期限其他选择暂未开放</p>
               
                <div class="x-btn"></div>
            </div>
        </div>
        <div class="popup popup-hkfs">
        	<div class="poup-cover"></div>
            <div class="popup-detail">
                <h3>说明</h3>
                <p>还款方式其他选择暂未开放</p>
               
                <div class="x-btn"></div>
            </div>
        </div>
    </div>
    <script>
    $(function() {
        $(".review-wrap").height($(window).height() - $(".head_top").height());
        popupShow("qx-btn", "popup-qx", "hkfs-btn");
        popupShow("hkfs-btn", "popup-hkfs", "qx-btn");
    });
    function popupShow(cls1, cls2, cls3){
    	$("." + cls1).bind('touchend', function() {
    		$("." + cls3).css("pointer-events", "none");
            $("." + cls2).show(function(event) {
                $(this).addClass('popup-show').on("webkitTransitionEnd", function() {
                    $(this).show();
                }).on("transitionend", function() {
                    $(this).show();
                    
                });
            });
        });

        $(".x-btn").bind("touchend", function(event) {
        	event.stopPropagation();
            $("." + cls2).removeClass('popup-show').on("webkitTransitionEnd", function() {
                $(this).hide();
                $("." + cls3).css("pointer-events", "auto");
            }).on("transitionend", function() {
                $(this).hide();
                $("." + cls3).css("pointer-events", "auto");
            });
        })
    }
    </script>
	<script src="<%=basePath%>/js/progress.js"></script>
	<script src="<%=basePath%>/js/login.js"></script> 
</body>
</html>
