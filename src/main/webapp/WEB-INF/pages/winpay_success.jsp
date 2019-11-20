<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>

<script type="text/javascript">
<%
 request.setCharacterEncoding("utf-8");
 response.setCharacterEncoding("utf-8");
 String getBrandWCPayRequest=String.valueOf(request.getAttribute("getBrandWCPayRequest"));
 String money = String.valueOf(request.getAttribute("money"));
 String payType = String.valueOf(request.getAttribute("payType"));
 String productType =  String.valueOf(request.getAttribute("productType"));
 String term =  null;
 String xuDaiRepayTime =  null;
 String totalXudaiAmount =  null;
 String borrowAmount =  null;
 
 if(getBrandWCPayRequest==null){
	 getBrandWCPayRequest =  request.getParameter("getBrandWCPayRequest");
 }
 if("2".equals(payType)){
	 term =  String.valueOf(request.getAttribute("term"));
	 xuDaiRepayTime = String.valueOf(request.getAttribute("xuDaiRepayTime"));
	 totalXudaiAmount =String.valueOf(request.getAttribute("totalXudaiAmount"));
	 borrowAmount = String.valueOf(request.getAttribute("borrowAmount"));
 }
 
  System.out.println("后台传值 :　"+ getBrandWCPayRequest);
   %>
   
   var payType = <%=payType%>;
   var term = "<%=term%>";
   var xuDaiRepayTime = "<%=xuDaiRepayTime%>";
   var totalXudaiAmount = "<%=totalXudaiAmount%>";
   var borrowAmount = "<%=borrowAmount%>";
   var productType = "<%=productType%>";
  
function onBridgeReady(){
  
   WeixinJSBridge.invoke(
       'getBrandWCPayRequest', <%=getBrandWCPayRequest%>,
       function(res){
    	   //alert(res.err_msg);
    	   if(payType == 2){
    		   if(res.err_msg == "get_brand_wcpay_request:ok" ) {
            	   window.location.href = "http://106.14.238.126/weixinApp/html/Repayment/renewalSuccess.html?term="+term+"&xuDaiRepayTime="+xuDaiRepayTime+"&totalXudaiAmount="+totalXudaiAmount+"&borrowAmount="+borrowAmount;
               }else if(res.err_msg == "get_brand_wcpay_request:cancel"){
            	   window.location.href = "http://106.14.238.126/weixinApp/html/Repayment/renewalFail.html?isWeixin=0";
               } else{
            	   window.location.href = "http://106.14.238.126/weixinApp/html/Repayment/renewalFail.html?isWeixin=0";  
               }    // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。 
    	   }else {
    		   if(res.err_msg == "get_brand_wcpay_request:ok" ) {
            	   window.location.href = "http://106.14.238.126/weixinApp/html/Repayment/weixinRepaySuccess.html?money="+<%=money%>;
               }else if(res.err_msg == "get_brand_wcpay_request:cancel"){
            	   window.location.href = "http://106.14.238.126/weixinApp/html/Repayment/weixinRepayFail.html?productType="+productType;  
               } else{
            	   window.location.href = "http://106.14.238.126/weixinApp/html/Repayment/weixinRepayFail.html?productType="+productType;  
               }    // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。  
    	   }
          
       }
   ); 
}


if (typeof WeixinJSBridge == "undefined"){
	
   if( document.addEventListener ){
       document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
   }else if (document.attachEvent){
       document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
       document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
   }
}else{
   onBridgeReady();
}

function callpay(){
	if (typeof WeixinJSBridge == "undefined"){
	   if( document.addEventListener ){
	       document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
	   }else if (document.attachEvent){
	       document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
	       document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
	   }
	}else{
	   onBridgeReady();
	}
}
</script>

</head>
<body>

<div align="center">
微信支付处理中，请不要离开......
        <button style="width:210px; height:30px; background-color:#FE6714; border:0px #FE6714 solid; cursor: pointer;  color:white;  font-size:16px;" type="button" onclick="callpay()" >微信支付</button>
</div>

</body>
</html>