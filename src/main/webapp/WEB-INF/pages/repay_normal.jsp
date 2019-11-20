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
	<script src="<%=basePath%>/js/common.js"></script>
	<script src="<%=basePath%>/js/login.js"></script>
    <title>还款</title>
</head>
<body>
<div class="repay-content repay-normal-content">
    <div class="repay-money">
        <p class="repay-info">本次未还本金（元）</p>
        <p class="repay-num" id="whbj"></p>
    </div>
    <div class="repay-detail">
        <p>申请时间：<span id="sqsj"></span></p>
        <p>借款期限：<span id="jkqx"></span></p>
        <p>还款方式：<span id="hkfs"></span></p>
        <p>借款进度：<span>还款进行中</span></p>
        <!-- <p>续贷期数：<span id="xdqs">第 3 期</span></p> -->
        <p>下一还款时间：<span id="hksj"></span></p>
    </div>
    <div>
        <a href="javascript:void(0);" class="agreement">查看合同</a>
    </div>
    <div class="gap-250"></div>
    <div class="btn-wrap">
	
	   <form id="toMoneyForm" action="/beadwalletloanapp/loan/rechargePage.do" method="post">
			<input type="hidden" name="amt" id="amt"/>
			<input type="hidden" name="orderId" id="orderId"/>
			<input type="hidden" name="userId" id="userId"/>
			<input type="hidden" name="type" value="1"/>
			<input type="hidden" name="param" id="param"/>
		</form>
        <button class="repay-btn" id="toMoney">确认还款</button>
        <button class="repay-btn" id="xudai">我要续贷</button> 
    </div>

    <div class="popup popup-hetong">
            <div class="poup-cover"></div>
            <div class="popup-detail">
                <ul>
                    <li><a href="javascript:void(0);" id="hetong">合同一</a></li>
                  <!--  <li><a href="javascript:void(0);">合同二</a></li>-->
                </ul>
                <button class="hetong-btn" type="button">取消</button>
            </div>
        </div>
</div>
    <div class="history-content" style="display: none;">
        <div class="none-info">
            您还没有借款记录
        </div>
    </div>
    <script>
        $(function(){
            switchNav();
            popupShow("agreement", "popup-hetong");
        });
        function switchNav(){
            $('.repay-lis').on('click', function(){
                $(this).addClass('repay-active').siblings().removeClass('repay-active');
                if($(this).index() == 0){
                    $('.repay-content').show();
                    $('.history-content').hide();
                }else{
                    $('.repay-content').hide();
                    $('.history-content').show();
                }
            });
        }

        function popupShow(cls1, cls2){
        $("." + cls1).on('touchend', function() {
            $("." + cls2).show(function(event) {
                $(this).addClass('popup-show').on("webkitTransitionEnd", function() {
                    $(this).show();
                }).on("transitionend", function() {
                    $(this).show();
                });
            });
        });

        $(".hetong-btn").on("touchend", function(event) {
            event.stopPropagation();
            $("." + cls2).removeClass('popup-show').on("webkitTransitionEnd", function() {
                $(this).hide();
            }).on("transitionend", function() {
                $(this).hide();
            });
        })
    }
	
	//////////////////
	var orderId="${orderId}";
		var userId=getUUID();
		$(function(){
			$.post('/beadwalletloanapp/app/my/appCheckLogin/findBwRepaymentPlanByOrderId.do',{"orderId":orderId,"loginToken":getLoginToken()}, function(object){
				if(object.code=='000'){
					 object=object.result;
					$("#hksj").html(longToDate(object.repay_time,'yyyy年MM月dd日')+"之前");
					$("#xdqs").html("第"+object.xudai_term+"期");
					$.post('/beadwalletloanapp/app/my/appCheckLogin/findBwOrderByOrderId.do',{"orderId":orderId,"loginToken":getLoginToken()}, function(data){
						if(data.code=='000'){
							var bwOrder=data.result;
							if(bwOrder){
								//赋值
								$("#orderId").val(orderId);
								$("#amt").val(toAmount(bwOrder.borrowAmount));
								$("#userId").val(userId);
								
								
								//借款明细
								//$(".use-num").html(toAmount(bwOrder.borrowAmount));
								$("#whbj").html(toAmount(bwOrder.borrowAmount)+"元");
								if(bwOrder.repayTerm!=null&&bwOrder.repayTerm!=''){
									$("#jkqx").html(bwOrder.repayTerm+"个月");//借款期限
								}
								if(bwOrder.repayType!=null&&bwOrder.repayType!=''){
									$("#hkfs").html(bwOrder.repayType==1?'先息后本':'等额本息');//还款方式
								}
								$("#sqsj").html(longToDate(bwOrder.createTime,'yyyy年MM月dd日'));
								
							}else{
								alert(object.msg);
								return false;
							}
						}
					});
				}else{
					alert(object.msg);
					return false;
				}
			});
				
		//续贷
		$("#xudai").click(function(){
			window.location.href = "xudai.html?orderId=" + orderId;
		});
		//充值
		$("#toMoney").click(function(){
			//发送ajax 
			$.post('/beadwalletloanapp/app/repay/appCheckLogin/queryOrderParam.do',{"userId":userId,"amt":$("#amt").val(),"loginToken":getLoginToken(),"orderId":orderId}, function(data){
						var  dataCode = data.code;
						if(dataCode=="000"){
							    window.location.href="huankuanchenggong.html?orderId="+orderId;		
							}else if(dataCode='104'){
								$("#amt").val(data.result);
								var param=userId+"_"+orderId;
								$("#param").val(param);
								$("#toMoneyForm").submit();
							}else{
								alert(data.msg);
							}
			});
		});
	});
	        //查看合同
	      $("#hetong").click(function(){
	        $.post('/beadwalletloanapp/app/my/appCheckLogin/findAdjunctByOrderId.do',{"bwId":getUUID(),"loginToken":getLoginToken(),"orderId":orderId}, function(data){
						var  dataCode = data.code;
						if(dataCode=="000"){
						        var adjunctPath=data.result.adjunctPath;
							    window.location.href=adjunctPath;		
							}else{
								alert(data.msg);
							}
			});
		});
    </script>
	<script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js" type="text/javascript"></script>
</body>
</html>