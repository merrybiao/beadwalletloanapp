<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = path;
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta content="telephone=no" name="format-detection">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,height=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
	<script type="text/javascript" src="<%=basePath%>/js/jquery-1.11.3.min.js"></script>
    <script src="<%=basePath%>/js/flex.js" type="text/javascript"></script>
    <link rel="stylesheet" href="<%=basePath%>/css/loading.css">
    <script src="<%=basePath%>/js/main.js" type="text/javascript"></script>
   	<script src="<%=basePath%>/js/common-index.js"></script>
    <title bg="<%=basePath%>/images/yuqi.png" style="width:50px;">工单统计</title>		
	<style type="text/css">
		.head_top {
		  height: 1.0666666667rem;
		  width: 100%;
		  background-color: #3f8dff;
		  text-align: center;
		  color: #fff;
		  font-size: 0.4533333333rem;
		  line-height: 1.0666666667rem;
		  position: relative; 
		}
        table
        {
            border-collapse: collapse;
            margin: 0 auto;
            text-align: center;
			width: 100%;
        }
        table td, table th
        {
            border: 1px solid #cad9ea;
            color: #666;
            height: 30px;
        }
        table thead th
        {
            background-color: #CCE8EB;
            width: 100px;
			font-size: 14px;
        }
        table tr:nth-child(odd)
        {
            background: #fff;
        }
        table tr:nth-child(even)
        {
            background: #F5FAFA;
        }
		body {
			padding: 0;
			margin: 0;
			width: 100%;
			height: 978px;
		}
		.container {
			width: 100%;
			height: 100%;
			float: left;
			background-color: #fdfdfd;
		}
		.menu_list {
			float: left;
			width: 10%;
			height: 100%;
			font-size: 16px;
			background-color: #CCE8EB;
		}
		ul {
			list-style: none;
			padding: 0;
			margin: 0;
			overflow: hidden;
		}
		li {
			float: left;
			width: 100%;
			padding: 10px;
		}
		li:hover {
			background-color: blanchedalmond;
			border-right: 2px solid red;
		}
		li:visited {
			background-color: antiquewhite;
		}
		.data_center {
			float: left;
			width: 90%;
			height: 100%;
			/* background-color: antiquewhite; */
		}
	</style>
</head>
<body>
<div class="container">
	<div class="menu_list">
		<ul>
			<li id ="order_apply"><a><span>工单申请数统计</span></a></li>
			<li id ="order_audit"><a><span>工单进件数统计</span></a></li>
			<li id ="order_loans"><a><span>工单放款数统计</span></a></li>
		</ul>
	</div>
	<div class="data_center" id="order_data"></div>
</div>
</body>
<script type="text/javascript">
$(function(){
	$("#order_apply").click(function(){
		$.ajax({
			type : "post",
			url : "/beadwalletloanapp/order/statistics/hour/apply/order12hCount.do",
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			data : '{}',
			success : function(data) {
				if(data.code=='0000'){
					var result=data.result;
					var own_channel = result.own_channel;
					var api_channel = result.api_channel;
					var other_channel = result.other_channel;
					var date_title = result.date_title;
					
					var own_html = "";
					$.each(own_channel,function(i,item){
						if(i==0){
							var rowspan = "";
							if(own_channel.length >1){
								rowspan = "rowspan="+own_channel.length;
							}
							own_html = own_html + "<tr><td "+rowspan+">自有渠道</td><td>"+item.channel_name+"</td>";
						}else{
							own_html = own_html + "<tr><td>"+item.channel_name+"</td>";
						}
						
						var order_data = item.order_data;
						$.each(date_title,function(a,time){
							var value = typeof(order_data[time]) == "undefined" ? "" : order_data[time];
							own_html = own_html + "<td>"+value+"</td>";
						});
						own_html = own_html + "</tr>";
					});
					
					var api_html = "";
					$.each(api_channel,function(i,item){
						channel_name = item.channel_name == "" ? item.channel_id : item.channel_name;
						if(i==0){
							var rowspan = "";
							if(api_channel.length >1){
								rowspan = "rowspan="+api_channel.length;
							}
							api_html = api_html + "<tr><td "+rowspan+">API渠道</td><td>"+channel_name+"</td>";
						}else{
							api_html = api_html + "<tr><td>"+channel_name+"</td>";
							
						}
						
						var order_data = item.order_data;
						$.each(date_title,function(a,time){
							var value = typeof(order_data[time]) == "undefined" ? "" : order_data[time];
							api_html = api_html + "<td>"+value+"</td>";
						});
						api_html = api_html + "</tr>";
					});
					
					var other_html = "";
					$.each(other_channel,function(i,item){
						var channel_name = "";
						if(item.channel_name == ''){
							channel_name = 'H5渠道';
						}
						other_html = other_html + "<tr><td>其他渠道</td><td>"+channel_name+"</td>";
						
						var order_data = item.order_data;
						$.each(date_title,function(a,time){
							var value = typeof(order_data[time]) == "undefined" ? "" : order_data[time];
							other_html = other_html + "<td>"+value+"</td>";
						});
						other_html = other_html + "</tr>";
					});
					
					var table_title = "<thead><tr><th>分类</th><th>渠道名称</th>";
					$.each(date_title,function(i,time){
						table_title = table_title + "<th>"+time+"</th>";
					});
					table_title = table_title +"</tr></thead>";
					
					var apply_html = "<table>" +table_title+own_html+api_html+other_html+"</table><br/>";
					$("#order_data").html(apply_html);
				}else{
					alert(data.msg);
					return false;
				}
			}
		});
	});
	
	$("#order_audit").click(function(){
		$.ajax({
			type : "post",
			url : "/beadwalletloanapp/order/statistics/hour/audit/order12hCount.do",
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			data : '{}',
			success : function(data) {
				if(data.code=='0000'){
					var result=data.result;
					var own_channel = result.own_channel;
					var api_channel = result.api_channel;
					var other_channel = result.other_channel;
					var date_title = result.date_title;
					
					var own_html = "";
					$.each(own_channel,function(i,item){
						if(i==0){
							var rowspan = "";
							if(own_channel.length >1){
								rowspan = "rowspan="+own_channel.length;
							}
							own_html = own_html + "<tr><td "+rowspan+">自有渠道</td><td>"+item.channel_name+"</td>";
						}else{
							own_html = own_html + "<tr><td>"+item.channel_name+"</td>";
						}
						
						var order_data = item.order_data;
						$.each(date_title,function(a,time){
							var value = typeof(order_data[time]) == "undefined" ? "" : order_data[time];
							own_html = own_html + "<td>"+value+"</td>";
						});
						own_html = own_html + "</tr>";
					});
					
					var api_html = "";
					$.each(api_channel,function(i,item){
						channel_name = item.channel_name == "" ? item.channel_id : item.channel_name;
						if(i==0){
							var rowspan = "";
							if(api_channel.length >1){
								rowspan = "rowspan="+api_channel.length;
							}
							api_html = api_html + "<tr><td "+rowspan+">API渠道</td><td>"+channel_name+"</td>";
						}else{
							api_html = api_html + "<tr><td>"+channel_name+"</td>";
							
						}
						
						var order_data = item.order_data;
						$.each(date_title,function(a,time){
							var value = typeof(order_data[time]) == "undefined" ? "" : order_data[time];
							api_html = api_html + "<td>"+value+"</td>";
						});
						api_html = api_html + "</tr>";
					});
					
					var other_html = "";
					$.each(other_channel,function(i,item){
						var channel_name = "";
						if(item.channel_name == ''){
							channel_name = 'H5渠道';
						}
						other_html = other_html + "<tr><td>其他渠道</td><td>"+channel_name+"</td>";
						
						var order_data = item.order_data;
						$.each(date_title,function(a,time){
							var value = typeof(order_data[time]) == "undefined" ? "" : order_data[time];
							other_html = other_html + "<td>"+value+"</td>";
						});
						other_html = other_html + "</tr>";
					});
					
					var table_title = "<thead><tr><th>分类</th><th>渠道名称</th>";
					$.each(date_title,function(i,time){
						table_title = table_title + "<th>"+time+"</th>";
					});
					table_title = table_title +"</tr></thead>";
					
					var audit_html = "<table>" +table_title+own_html+api_html+other_html+"</table><br/>";
					$("#order_data").html(audit_html);
				}else{
					alert(data.msg);
					return false;
				}
			}
		});
	});
	
	$("#order_loans").click(function(){
		$.ajax({
			type : "post",
			url : "/beadwalletloanapp/order/statistics/hour/loans/order12hCount.do",
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			data : '{}',
			success : function(data) {
				if(data.code=='0000'){
					var result=data.result;
					var own_channel = result.own_channel;
					var api_channel = result.api_channel;
					var other_channel = result.other_channel;
					var date_title = result.date_title;
					
					var own_html = "";
					$.each(own_channel,function(i,item){
						if(i==0){
							var rowspan = "";
							if(own_channel.length >1){
								rowspan = "rowspan="+own_channel.length;
							}
							own_html = own_html + "<tr><td "+rowspan+">自有渠道</td><td>"+item.channel_name+"</td>";
						}else{
							own_html = own_html + "<tr><td>"+item.channel_name+"</td>";
						}
						
						var order_data = item.order_data;
						$.each(date_title,function(a,time){
							var value = typeof(order_data[time]) == "undefined" ? "" : order_data[time];
							own_html = own_html + "<td>"+value+"</td>";
						});
						own_html = own_html + "</tr>";
					});
					
					var api_html = "";
					$.each(api_channel,function(i,item){
						channel_name = item.channel_name == "" ? item.channel_id : item.channel_name;
						if(i==0){
							var rowspan = "";
							if(api_channel.length >1){
								rowspan = "rowspan="+api_channel.length;
							}
							api_html = api_html + "<tr><td "+rowspan+">API渠道</td><td>"+channel_name+"</td>";
						}else{
							api_html = api_html + "<tr><td>"+channel_name+"</td>";
							
						}
						
						var order_data = item.order_data;
						$.each(date_title,function(a,time){
							var value = typeof(order_data[time]) == "undefined" ? "" : order_data[time];
							api_html = api_html + "<td>"+value+"</td>";
						});
						api_html = api_html + "</tr>";
					});
					
					var other_html = "";
					$.each(other_channel,function(i,item){
						var channel_name = "";
						if(item.channel_name == ''){
							channel_name = 'H5渠道';
						}
						other_html = other_html + "<tr><td>其他渠道</td><td>"+channel_name+"</td>";
						
						var order_data = item.order_data;
						$.each(date_title,function(a,time){
							var value = typeof(order_data[time]) == "undefined" ? "" : order_data[time];
							other_html = other_html + "<td>"+value+"</td>";
						});
						other_html = other_html + "</tr>";
					});
					
					var table_title = "<thead><tr><th>分类</th><th>渠道名称</th>";
					$.each(date_title,function(i,time){
						table_title = table_title + "<th>"+time+"</th>";
					});
					table_title = table_title +"</tr></thead>";
					
					var loans_html = "<table>" +table_title+own_html+api_html+other_html+"</table><br/>";
					$("#order_data").html(loans_html);
				}else{
					alert(data.msg);
					return false;
				}
			}
		});
	});
});
</script>
</html>