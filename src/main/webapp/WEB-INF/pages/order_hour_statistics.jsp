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
    <%-- <link rel="stylesheet" href="<%=basePath%>/css/base.css"> --%>
    <link rel="stylesheet" href="<%=basePath%>/css/loading.css">
    <script src="<%=basePath%>/js/main.js" type="text/javascript"></script>
   	<script src="<%=basePath%>/js/common-index.js"></script>
    <title bg="<%=basePath%>/images/yuqi.png" style="width:50px;">工单小时统计</title>		
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
    </style>
</head>
<body>
<div id="order_apply_data">
<!-- 工单申请数据 -->
</div>
<div id="order_audit_data">
<!-- 工单进件数据 -->
</div>
<div id="order_loans_data">
<!-- 工单放款数据 -->
</div>
</body>
<script>
$(function(){
	
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
				
				var data_title = "<h2>工单申请数统计：</h2><br/>"
				var table_title = "<thead><tr><th>分类</th><th>渠道名称</th>";
				$.each(date_title,function(i,time){
					table_title = table_title + "<th>"+time+"</th>";
				});
				table_title = table_title +"</tr></thead>";
				
				var apply_html = data_title +"<table>" +table_title+own_html+api_html+other_html+"</table><br/>";
				$("#order_apply_data").html(apply_html);
			}else{
				alert(data.msg);
				return false;
			}
		}
	});
	
	/* $.ajax({
		type : "post",
		url : "/beadwalletloanapp/order/statistics/hour/apply/order_count.do",
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		data : '{}',
		success : function(data) {
			if(data.code=='0000'){
				var result=data.result;
				var own_channel = result.own_channel;
				var api_channel = result.api_channel;
				var other_channel = result.other_channel;
				
				var own_html = "";
				$.each(own_channel,function(i,item){
					if(i==0){
						var rowspan = "";
						if(own_channel.length >0){
							rowspan = "rowspan="+own_channel.length;
						}
						own_html = own_html + "<tr><td "+rowspan+">自有渠道</td><td>"+item.channel_name+"</td><td>"+item.order_count+"</td><td>"+item.report_time+"</td></tr>";
					}else{
						own_html = own_html + "<tr><td>"+item.channel_name+"</td><td>"+item.order_count+"</td><td>"+item.report_time+"</td></tr>";
					}
				});
				
				var api_html = "";
				$.each(api_channel,function(i,item){
					if(i==0){
						var rowspan = "";
						if(api_channel.length >0){
							rowspan = "rowspan="+api_channel.length;
						}
						api_html = api_html + "<tr><td "+rowspan+">API渠道</td><td>"+item.channel_name+"</td><td>"+item.order_count+"</td><td>"+item.report_time+"</td></tr>";
					}else{
						api_html = api_html + "<tr><td>"+item.channel_name+"</td><td>"+item.order_count+"</td><td>"+item.report_time+"</td></tr>";
					}
				});
				
				var other_html = "";
				$.each(other_channel,function(i,item){
					var channel_name = "";
					if(item.channel_name == ''){
						channel_name = 'H5渠道';
					}
					other_html = other_html + "<tr><td>其他渠道</td><td>"+channel_name+"</td><td>"+item.order_count+"</td><td>"+item.report_time+"</td></tr>";
				});
				
				var data_title = "<h2>工单申请数统计：</h2><br/>"
				var table_title = "<thead><tr><th>分类</th><th>渠道名称</th><th>工单数</th><th>统计时间</th></tr></thead>";
				var apply_html = data_title +"<table>" +table_title+own_html+api_html+other_html+"</table><br/>";
				$("#order_apply_data").html(apply_html);
			}else{
				alert(data.msg);
				return false;
			}
		}
	}); */
	
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
				
				var data_title = "<h2>工单进件数统计：</h2><br/>"
				var table_title = "<thead><tr><th>分类</th><th>渠道名称</th>";
				$.each(date_title,function(i,time){
					table_title = table_title + "<th>"+time+"</th>";
				});
				table_title = table_title +"</tr></thead>";
				
				var audit_html = data_title +"<table>" +table_title+own_html+api_html+other_html+"</table><br/>";
				$("#order_audit_data").html(audit_html);
			}else{
				alert(data.msg);
				return false;
			}
		}
	});
	
	/* $.ajax({
		type : "post",
		url : "/beadwalletloanapp/order/statistics/hour/audit/order_count.do",
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		data : '{}',
		success : function(data) {
			if(data.code=='0000'){
				var result=data.result;
				var own_channel = result.own_channel;
				var api_channel = result.api_channel;
				var other_channel = result.other_channel;
				
				var own_html = "";
				$.each(own_channel,function(i,item){
					if(i==0){
						var rowspan = "";
						if(own_channel.length >0){
							rowspan = "rowspan="+own_channel.length;
						}
						own_html = own_html + "<tr><td "+rowspan+">自有渠道</td><td>"+item.channel_name+"</td><td>"+item.order_count+"</td><td>"+item.report_time+"</td></tr>";
					}else{
						own_html = own_html + "<tr><td>"+item.channel_name+"</td><td>"+item.order_count+"</td><td>"+item.report_time+"</td></tr>";
					}
				});
				
				var api_html = "";
				$.each(api_channel,function(i,item){
					if(i==0){
						var rowspan = "";
						if(api_channel.length >0){
							rowspan = "rowspan="+api_channel.length;
						}
						api_html = api_html + "<tr><td "+rowspan+">API渠道</td><td>"+item.channel_name+"</td><td>"+item.order_count+"</td><td>"+item.report_time+"</td></tr>";
					}else{
						api_html = api_html + "<tr><td>"+item.channel_name+"</td><td>"+item.order_count+"</td><td>"+item.report_time+"</td></tr>";
					}
				});
				
				var other_html = "";
				$.each(other_channel,function(i,item){
					var channel_name = "";
					if(item.channel_name == ''){
						channel_name = 'H5渠道';
					}
					other_html = other_html + "<tr><td>其他渠道</td><td>"+channel_name+"</td><td>"+item.order_count+"</td><td>"+item.report_time+"</td></tr>";
				});
				
				var data_title = "<h2>工单进件数统计：</h2><br/>"
				var table_title = "<thead><tr><th>分类</th><th>渠道名称</th><th>工单数</th><th>统计时间</th></tr></thead>";
				var audit_html = data_title +"<table>" +table_title+own_html+api_html+other_html+"</table><br/>";
				$("#order_audit_data").html(audit_html);
			}else{
				alert(data.msg);
				return false;
			}
		}
	}); */
	
	
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
				
				var data_title = "<h2>工单放款数统计：</h2><br/>"
				var table_title = "<thead><tr><th>分类</th><th>渠道名称</th>";
				$.each(date_title,function(i,time){
					table_title = table_title + "<th>"+time+"</th>";
				});
				table_title = table_title +"</tr></thead>";
				
				var loans_html = data_title +"<table>" +table_title+own_html+api_html+other_html+"</table><br/>";
				$("#order_loans_data").html(loans_html);
			}else{
				alert(data.msg);
				return false;
			}
		}
	});
	
	/* $.ajax({
		type : "post",
		url : "/beadwalletloanapp/order/statistics/hour/loans/order_count.do",
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		data : '{}',
		success : function(data) {
			if(data.code=='0000'){
				var result=data.result;
				var own_channel = result.own_channel;
				var api_channel = result.api_channel;
				var other_channel = result.other_channel;
				
				var own_html = "";
				$.each(own_channel,function(i,item){
					if(i==0){
						var rowspan = "";
						if(own_channel.length >0){
							rowspan = "rowspan="+own_channel.length;
						}
						own_html = own_html + "<tr><td "+rowspan+">自有渠道</td><td>"+item.channel_name+"</td><td>"+item.order_count+"</td><td>"+item.report_time+"</td></tr>";
					}else{
						own_html = own_html + "<tr><td>"+item.channel_name+"</td><td>"+item.order_count+"</td><td>"+item.report_time+"</td></tr>";
					}
				});
				
				var api_html = "";
				$.each(api_channel,function(i,item){
					if(i==0){
						var rowspan = "";
						if(api_channel.length >0){
							rowspan = "rowspan="+api_channel.length;
						}
						api_html = api_html + "<tr><td "+rowspan+">API渠道</td><td>"+item.channel_name+"</td><td>"+item.order_count+"</td><td>"+item.report_time+"</td></tr>";
					}else{
						api_html = api_html + "<tr><td>"+item.channel_name+"</td><td>"+item.order_count+"</td><td>"+item.report_time+"</td></tr>";
					}
				});
				
				var other_html = "";
				$.each(other_channel,function(i,item){
					var channel_name = "";
					if(item.channel_name == ''){
						channel_name = 'H5渠道';
					}
					other_html = other_html + "<tr><td>其他渠道</td><td>"+channel_name+"</td><td>"+item.order_count+"</td><td>"+item.report_time+"</td></tr>";
				});
				
				var data_title = "<h2>工单放款数统计：</h2><br/>"
				var table_title = "<thead><tr><th>分类</th><th>渠道名称</th><th>工单数</th><th>统计时间</th></tr></thead>";
				var loans_html = data_title +"<table>" +table_title+own_html+api_html+other_html+"</table><br/>";
				$("#order_loans_data").html(loans_html);
			}else{
				alert(data.msg);
				return false;
			}
		}
	}); */
	
});
</script>
</html>