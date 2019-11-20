<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
		String path = request.getContextPath();
		String basePath = path;
	 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <title>Document</title>
    <link rel="stylesheet" href="<%=basePath%>/css/move_base.css">
    <link rel="stylesheet" href="<%=basePath%>/css/store.css">
    <script src="<%=basePath%>/js/rem.js"></script>
</head>
<body>
<header>
        <div class="icon"><</div>
                  商城
    </header>
    <div class="content intro">
            <p>商城优惠券</p>
            <p><span>￥</span><span>300-1000</span></p>
        </div>
    <div class="content">
        <p>简介:</p>
        <section>
               <p> 1. 商城优惠券可在购买商品时直接使用</p>
                <p>2. 不同价格的商品匹配使用不同面额的商城优惠券（部分商品不可使用商城优惠券）</p>
                <p>3. 商城优惠券自购买日起90天内有效，请在有效期内使用，过期自动作废
                使用说明：不同价格的商品匹配使用不同面额的商城优惠券（部分商品不可使用商城优惠券）</p>
                
        </section>
    </div>
    <footer >
        <%--(code:xjbk001)--%>
    <%--<a href="<%=basePath%>/sxyDrainage/xjbk/saveOrderAuth.do?order_id=${orderId}&user_phone=${userPhone}&return_url=${returnUrl}">
             确认
    </a>--%>
     <a onclick="window.location.replace('<%=basePath%>/sxyDrainage/xjbk/saveOrderAuth.do?order_id=${orderId}&user_phone=${userPhone}&return_url=${returnUrl}')" href="javascript:void(0)" >
                确认
     </a>
    </footer>
   
</body>
</html>