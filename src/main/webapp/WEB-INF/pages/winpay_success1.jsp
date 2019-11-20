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
 
   %>
   var payType = <%=payType%>;
   var getBrandWCPayRequest = "<%=getBrandWCPayRequest%>";
   var money = "<%=money%>";
   var term = "<%=term%>";
   var xuDaiRepayTime = "<%=xuDaiRepayTime%>";
   var totalXudaiAmount = "<%=totalXudaiAmount%>";
   var borrowAmount = "<%=borrowAmount%>";
   alert(payType+","+getBrandWCPayRequest+","+money+","+term+","+xuDaiRepayTime+","+totalXudaiAmount+","+borrowAmount);
   
   var str =  "http://106.14.238.126/weixinApp/html/Repayment/renewalSuccess.html?term="+term+"&xuDaiRepayTime="+xuDaiRepayTime+"&totalXudaiAmount="+totalXudaiAmount+"&borrowAmount="+borrowAmount;
  	alert(str);
   </script>

</head>
<body>

<div align="center">
微信支付处理中，请不要离开......
        <button style="width:210px; height:30px; background-color:#FE6714; border:0px #FE6714 solid; cursor: pointer;  color:white;  font-size:16px;" type="button" onclick="callpay()" >微信支付</button>
</div>

</body>
</html>