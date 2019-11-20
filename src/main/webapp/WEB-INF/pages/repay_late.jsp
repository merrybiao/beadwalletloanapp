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
	<script type="text/javascript" src="<%=basePath%>/js/jquery-1.11.3.min.js"></script>
    <script src="<%=basePath%>/js/flex.js" type="text/javascript"></script>
    <link rel="stylesheet" href="<%=basePath%>/css/base.css">
    <link rel="stylesheet" href="<%=basePath%>/css/loading.css">
    <script src="<%=basePath%>/js/main.js" type="text/javascript"></script>
   	<script src="<%=basePath%>/js/common-index.js"></script>
	<script src="<%=basePath%>/js/login.js"></script>
    <title bg="<%=basePath%>/images/yuqi.png">逾期还款</title>		
</head>

<input type="hidden" id="borrowAmonuntId"/>
<input type="hidden" id="applyPayStatus"/>

<div class="repay-content repay-late-content">
    <div class="repay-money">
        <p class="repay-info font-w">本次逾期未还本金（元）</p>
        <p class="repay-num" id="yqbj"></p>
    </div>
    <div class="repay-detail">
        <p>申请时间：<span id="sqsj"></span></p>
        <p>逾期时长：<span id="day"></span></p>
        <p>逾期罚息：<span class="clr-warn" id="faxi"></span></p>
        <p>还款总额：<span class="clr-warn" id="borrowAmonunt"></span></p>
    </div>
    <div>
        <a href="javascript:void(0);" class="agreement" id="hetong">查看合同</a>
    </div>
    <div class="late-warn">
        <p><strong>凡是未及时还款均视为逾期，将以借款金额1%/天进行罚息。</strong>请往扣款银行卡中充入足够金额以缴清还款，<strong>请您珍惜个人信用记录。</strong></p>
    </div>
     <div class="btn-wrap">
	    <form id="toMoneyForm" action="/beadwalletloanapp/loan/rechargePage.do" method="post">
				<input type="hidden" name="amt" id="amt"/>
				<input type="hidden" name="orderId" id="orderId"/>
				<input type="hidden" name="userId" id="userId"/>
				<input type="hidden" name="type" value="1"/>
				<input type="hidden" name="param" id="param"/>
			</form>
       <!--  <button class="repay-btn must" type="button" id="toMoney">确认还款</button>
        <button class="repay-btn must" type="button" id="xudai">我要续贷</button> -->
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
<script>
		var orderId=getParameter("orderId");
		var userId=getUUID();
		$(function(){
			$.post('/beadwalletloanapp/app/repay/appCheckLogin/queryOverdueRecord.do',{"orderId":orderId,"loginToken":getLoginToken()}, function(data){
				if(data.code=='000'){
					var bwOverdueRecordDto=data.result;
					if(bwOverdueRecordDto){
						$("#yqbj").html(toAmount(bwOverdueRecordDto.repayMoney));
						if(bwOverdueRecordDto.overdueDay!=null&&bwOverdueRecordDto.overdueDay!=''){
							$("#day").html(bwOverdueRecordDto.overdueDay+'天');
						}else{
							$("#day").html("暂无");
						}
						
						$("#faxi").html(toAmount(bwOverdueRecordDto.overdueAccrualMoney)+"元");//罚息
						$("#borrowAmonunt").html(toAmount(bwOverdueRecordDto.repayMoney+bwOverdueRecordDto.overdueAccrualMoney)+"元");
						$("#borrowAmonuntId").val(bwOverdueRecordDto.borrowAmonunt);
						
						//赋值
						$("#orderId").val(orderId);
						$("#amt").val(toAmount(bwOverdueRecordDto.repayMoney));
						$("#userId").val(userId);
						
					}
				}else{
					alert(data.msg);
					return false;
				}
			});
			$.post('/beadwalletloanapp/app/my/appCheckLogin/findBwOrderByOrderId.do',{"orderId":orderId,"loginToken":getLoginToken()}, function(data){
				if(data.code=='000'){
					var bwOrder=data.result;
					$("#sqsj").html(longToDate(bwOrder.createTime,'yyyy年MM月dd日'));//申请时间
					if(bwOrder){
						if(bwOrder.repayTerm!=null&&bwOrder.repayTerm!=''){
							//$(".late-countdown p").find("span").eq(0).html("借款期限："+bwOrder.repayTerm+"个月");
						}else{
							//$(".late-countdown p").find("span").eq(0).html("借款期限：暂无");
						}
						if(bwOrder.repayType!=null&&bwOrder.repayType!=''){
							if(bwOrder.repayType==1){
								$("#repayType").html("还款方式：先息后本");
							}else{
								$("#repayType").html("还款方式：等额本息");
							}
						}else{
							$("#repayType").html("还款方式：暂无");
						}
					}else{
						//$(".late-countdown p").find("span").eq(0).html("借款期限：暂无");
						$("#repayType").html("还款方式：暂无");
					}
				}else{
					alert(data.msg);
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