<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%
		String path = request.getContextPath();
		String basePath = path;
	%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta content="telephone=no" name="format-detection">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
    <link rel="stylesheet" href="<%=basePath%>/css/main.css">
    <link rel="stylesheet" href="<%=basePath%>/css/loading.css">
    <script src="<%=basePath%>/js/flex.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/jquery-1.11.3.min.js" type="text/javascript"></script>
	<%-- <script src="<%=basePath%>/js/main.js" type="text/javascript"></script> --%>
	<script type="text/javascript" src="<%=basePath%>/js/IDValidator.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>/js/GB2260.js" charset="utf-8"></script>
	<script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/common.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/login.js" type="text/javascript"></script> 
    <title>运营商认证</title>
</head>

<body>
    <div class="xxx">
		<img src="../../images/yunyingshang2.png" width="100%"/>
    </div>
    <div class="yunying2">
        <div id="part1" class="part1">
            <title>请填您的运营商信息（必填）</title>
            <form id="carrieroperatorForm" method="post">
                <p>
                    <input type="text" placeholder="开户人" class="noNull" notNull="开户人不能为空" id="name" name="name" />
                </p>
                <p>
                    <input type="text" placeholder="身份证" class="noNull" notNull="身份证不能为空" id="idCard" name="idCard" />
                </p>
                <p>
                    <input type="text" placeholder="运营商电话" id="mobile" name="mobile" class="noNull" notNull="运营商电话不能为空" />
                </p>
                <p>
                    <input type="text" placeholder="运营商密码" class="noNull" notNull="运营商密码不能为空" id="password" name="password" />
                </p>
                <p>
                    <input type="hidden" placeholder="开户人" name="bwId" id="bwId" class="noNull" notNull="请重新登录后在进行操作" />
                </p>
                <p class="clearfix"><a id="forgetPass" class="fr" style="cursor: pointer;">忘记密码？</a></p>
                <button type="button" class="btn" id="buttonSub">下一步</button>
                <!--loginToken-->
                <input type="hidden" name="loginToken" id="loginToken" />
				<!--dataType-->
                <input type="hidden"  id="dataType" />
            </form>
            <!-- 跳转的类别 -->
            <input type="hidden" id="vailteType" value="0"/>
            <!-- 跳转的接口方法  -->
            <input type="hidden" id="vailteMethod" />
            <!-- 短信验证码  -->
            <input type="hidden" id="vailteMsg" />
            <!-- 图片验证码  -->
            <input type="hidden" id="vailteImg" />
        </div>
        <div id="part2" class="orget-valid part2">
            <p id="messAgeText" class="">
                <input type="text" placeholder="短信验证码" id="msgCode" name="msgCode" />
                <span><a id="phoneMsg" href="javascript:void(0);" onclick="getPhoneCode(this,60)" >发送短信</a></span>
            </p>
            <p id="img" class="font-hei  passcode" style="display: none">
                <input type="text" placeholder="请输入图片显示的验证码" id="picCode" name="picCode" />
                <em><img id="picCodeImg" onclick="getImgCode()" width="100%" height="100%" /></em>
            </p>
            <button type="button" class="btn" id="valiateButton">下一步</button>
        </div>
    </div>
	
<script type="text/javascript">
	var wait=0;
	$(function() {
	
		/* 	if (hasInProgressOrder()) {
				$("#password").hide();
			} */
			
			var win_h = document.body.clientHeight;

			$(".yunying2").height(win_h - $(".xxx").height());

			var bwIdval = getUUID();

			$("#bwId").val(bwIdval);

			var loginToken = getLoginToken();

			//赋值
			$("#loginToken").val(loginToken);
			
			var orderId = "${orderId}";

			//借款人姓名

			var realName = getParameter("realName");

			//初始化赋值
			initData();
		
			//提交

			$("#buttonSub").click(function(){
					$('#buttonSub').attr('disabled',true).addClass('disable');
					setTimeout("$('#buttonSub').removeAttr('disabled').removeClass('disable');", 1000)

					if (!hasInProgressOrder(orderId)) {
						var status='true';			  

						$(".noNull").each(function(){

								if($(this).val()==""){

										alert($(this).attr('notNull'));

										status='false';

										return false;

								}

						 })

					

						if(status=='true'){

								var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 

								if(!myreg.test($("#mobile").val())) { 

									alert('请输入有效的手机号码！'); 

									status='false';

									return false;

								}

								

								if (!validate($("#idCard").val(), "IDcard")) {

									alert("请输入正确的身份证号");

									status='false';

									return false;

								}

								

								//发送ajax 运营商是否跳转

								$.ajax({
									type : "post",
									url : "/beadwalletloanapp/app/operate/appCheckLogin/login.do",
									dataType : "json",
									data : {'phone':$("#mobile").val(),'pwd':$("#password").val(),'userId':getUUID(),'loginToken':getLoginToken()},
									success : function(obj) {
										var status = obj.code;
										if(status == "000"){
											if(obj.result == "" || obj.result==null ||  obj.result== undefined){
												//直接跳转
												$("#vailteType").val("1");
												window.location.href =  "/beadwalletloanapp/bewadwallet/loan/yunYingShangResult.do?code="+obj.code+"&orderId="+orderId+"&userId="+getUUID();
											}else{
												//vailteMethod
												var method = obj.result.method;
												$("#vailteMethod").val(method);
												//图片验证码
												var imgMsg = obj.result.param2;
												if(imgMsg == "picCode"){
													$("#vailteImg").val("1");
													$("#picCodeImg").attr("src","data:image/jpeg;base64,"+obj.result.picCode);
												}
												//短信验证码
												var textMsg = obj.result.param1;
												if(textMsg == "msgCode"){
													$("#vailteMsg").val("1");
												}
												
												//dataType
												var piccodeType = obj.result.piccodeType;
												if(piccodeType != "" && piccodeType != null && piccodeType!=undefined){
													$("#dataType").val(piccodeType);
												}
												
												var messagecodeType = obj.result.messagecodeType;
												if(messagecodeType != "" && messagecodeType != null && messagecodeType!=undefined){
													$("#dataType").val(messagecodeType);
												}
												
											}
											
																												  
											//直接跳转
											var vailteType = $("#vailteType").val();
											
											//判断是否有短信验证码
											if($("#vailteMsg").val()=="1"){
												$("#msgCode").val("");
												//倒计时
												time_end(60);
												$("#img").hide();
												$("#messAgeText").show();
											}
											
											//判断是否有图片验证码
											if($("#vailteImg").val()=="1"){
												$("#picCode").val("");
												$("#messAgeText").hide();
												$("#img").show();
											}
											
											//直接跳转
											if($("#vailteType").val() == "0"){
												$("#part1").hide();
												$("#part2").show();	
											}else if($("#vailteType").val() == "1"){
												window.location.href =  "/beadwalletloanapp/bewadwallet/loan/yunYingShangResult.do?code="+obj.code+"&orderId="+orderId+"&userId="+getUUID();
											}
									
										}else{
											alert(obj.msg);
										}

									}

								});
						}
					} else {
						window.location.href =  "/beadwalletloanapp/bewadwallet/loan/yunYingShangResult.do?code="+obj.code+"&orderId="+orderId+"&userId="+getUUID();
					}

			});



			

			//验证码

			$("#valiateButton").click(function(){
			
				 $('#valiateButton').attr('disabled',true).addClass('disable');
				 setTimeout("$('#valiateButton').removeAttr('disabled').removeClass('disable');", 1000)

				 var status = "true";

				 //方法

				 var method = $("#vailteMethod").val();

				 //图片

				 var picCode = $("#picCode").val();

				 //验证码

				 var vailteText = $("#msgCode").val();

				 var url='';

				 var data='';

				 if($("#vailteImg").val()=="1"){
						
						if($.trim(picCode) == "" || $.trim(picCode) == null || $.trim(picCode)==undefined){

							alert("图片验证码不能为空");

							status = "false";

						}

				}
				
				if($("#vailteMsg").val()=="1"){

					if($.trim(vailteText) == "" || $.trim(vailteText) == null || $.trim(vailteText)==undefined){

							alert("短信验证码不能为空");

							status = "false";

					}

				}	
				
				if(method == "login"){

				
					url = "/beadwalletloanapp/app/operate/appCheckLogin/login.do";

					data = "phone="+$("#mobile").val()+"&idCard="+$("#idCard").val()+"&pwd="+$("#password").val()+"&userId="+getUUID()+"&loginToken="+getLoginToken()+"&name="+$("#name").val();

					//判断是否有短信验证码
					if($("#vailteMsg").val()=="1"){
						data +="&msgCode="+$("#msgCode").val();
					}
					
					//判断是否有短信验证码
					if($("#vailteImg").val()=="1"){
						data +="&picCode="+$("#picCode").val();
					}

				}else if(method == "submitMessageCode"){

					url = "/beadwalletloanapp/app/operate/appCheckLogin/submitMsgCode.do";

					data = "phone="+$("#mobile").val()+"&msgCode="+$("#msgCode").val()+"&userId="+getUUID()+"&picCode="+$("#picCode").val()+"&name="+$("#name").val()+"&idCard="+$("#idCard").val()+"&loginToken="+getLoginToken();

				 }else if(method == "getMsgCode"){
					url = "/beadwalletloanapp/app/operate/appCheckLogin/getMsgCode.do";

					data = "phone="+$("#mobile").val()+"&idCard="+$("#idCard").val()+"&pwd="+$("#password").val()+"&userId="+getUUID()+"&loginToken="+getLoginToken()+"&name="+$("#name").val();

				 }else if(method == "getPicCode"){
					url = "/beadwalletloanapp/app/operate/appCheckLogin/getPicCode.do";

					data = "phone="+$("#mobile").val()+"&idCard="+$("#idCard").val()+"&pwd="+$("#password").val()+"&userId="+getUUID()+"&loginToken="+getLoginToken()+"&name="+$("#name").val();

				 }
				if(status == "true"){
					
					//发送ajax

					$.ajax({

						type : "post",

						url : url,

						dataType : "json",

						data : data,
							
						success : function(obj) {	
							if(obj.code=="000"){
									if($("#vailteMethod").val() == "login" && obj.result ==null){
										alert(obj.msg); 
										window.location.href =  "/beadwalletloanapp/bewadwallet/loan/yunYingShangResult.do?code="+obj.code+"&orderId="+orderId+"&userId="+getUUID();
										return false;
									}
																	
									if($("#vailteMethod").val() == "submitMessageCode" && obj.result !=null){
										alert(obj.msg); 
										window.location.href =  "/beadwalletloanapp/bewadwallet/loan/yunYingShangResult.do?code="+obj.code+"&orderId="+orderId+"&userId="+getUUID();
										return false;
									}
									
								
									var piccodeType = obj.result.piccodeType;
									if(piccodeType != "" && piccodeType != null && piccodeType!=undefined){
										$("#dataType").val(piccodeType);
									}
										
									
									var messagecodeType = obj.result.messagecodeType;
									if(messagecodeType != "" && messagecodeType != null && messagecodeType!=undefined){
										$("#dataType").val(messagecodeType);
									}
									
									if($("#vailteMethod").val() == "login" || $("#vailteMethod").val() == "submitMessageCode"){
											//vailteMethod
											var method = obj.result.method;
											$("#vailteMethod").val(method);
											
											//图片验证码
											var imgMsg = obj.result.param2;
											if(imgMsg == "picCode"){
												$("#vailteImg").val("1");
											}else{
												$("#vailteImg").val("0");
											}
											//短信验证码
											var textMsg = obj.result.param1;
											if(textMsg == "msgCode"){
												$("#vailteMsg").val("1");
											}else{
												$("#vailteMsg").val("0");
											}
											
											
											//短信验证码 
											if($("#vailteImg").val()=="1"){
												//是否为图片验证码
												var imgMsg = obj.result.param2;
												if(imgMsg == "picCode"){
													$("#vailteImg").val("1");
													//隐藏
													$("#messAgeText").hide();
													//清空
													$("#picCode").val("");
													$("#picCodeImg").attr("src","data:image/jpeg;base64,"+obj.result.picCode);
													$("#img").show();
												}
											}
											
											
											//图片验证码
											if($("#vailteMsg").val()=="1"){
												var textMsg = obj.result.param1;
												if(textMsg == "msgCode"){
													//清空
													$("#msgCode").val("");
													//倒计时
													time_end(60);
													$("#vailteMsg").val("1");
													//隐藏
													$("#img").hide();
													$("#messAgeText").show();
												}
											}
												$("#part1").hide();
												$("#part2").show();	
								
									}else{
										//getMsgCode
										if($("#vailteMethod").val() == "getMsgCode"){
											//倒计时
											time_end(60);
											//清空
											$("#msgCode").val("");
										}
										//getPicCode
										if($("#vailteMethod").val() == "getPicCode"){
											$("#picCodeImg").attr("src","data:image/jpeg;base64,"+obj.result.picCode);
										}
									}
									
							}else{
								//回退
								alert(obj.msg); 
								if(obj.msg=="手机号与服务密码不匹配，请重新输入服务密码"){
									window.location.reload();
								}
								//刷图片
								getImgCode();
							}

						}

					});

				}
			});

			

			//忘记密码

			$("#forgetPass").click(function(){

				window.location.href =  "http://139.196.253.158/loanpage/findpw.html";

			});

	});		
	
	
	
		//初始化
		function initData(){
				$.ajax({
						type : "post",
						url : "/beadwalletloanapp/app/my/appCheckLogin/findBwBorrowerByBwId.do",
						dataType : "json",
						data : {"loginToken":getLoginToken(),"bwId":getUUID()},
						success : function(obj) {	
							var status = obj.code;
							if(status == "000"){
								if(obj.result!=null){
									//手机号
									var mobileVal = obj.result.phone;
									$("#mobile").val(mobileVal);
									if(mobileVal!=null && mobileVal!='' && mobileVal!=undefined){
										setReadOnly("mobile");
									}
									
									//身份证
									var idCard = obj.result.idCard;
									$("#idCard").val(idCard);
									if(idCard!=null && idCard!='' && idCard!=undefined){
										setReadOnly("idCard");
									}
									//name
									var name = obj.result.name;
									$("#name").val(name);
									if(name!=null && name!='' && name!=undefined){
										setReadOnly("name");
									}
								}
							}else{
								alert(obj.msg);
							}
						}

					});
		
		}

		
		
		//发送短信验证码
		function getPhoneCode(obj,waitTime){
			if(wait==0){
				var phone=$("#mobile").val();
				$.post('/beadwalletloanapp/app/operate/appCheckLogin/refreshMsgCode.do',{"phone":phone,"userId":getUUID(),"loginToken":getLoginToken(),"messagecodeType":$("#dataType").val()},function(data){
						wait=waitTime;
						time(obj,waitTime);
						alert(data.msg);
				});
			}
		}
		
		//倒计时
		function time(o,waitTime) {
			if (wait == 0) {           
				o.innerText="获取验证码";    
			} else { 
				o.innerText="重新发送(" + wait + ")"; 
				wait--;  
				setTimeout(function() {  
					time(o,wait)  
				},  
				1000)  
			}  
		} 
		//倒计时
		function time_end(waitTime) {
			wait = waitTime;
			if (wait == 0) {           
				$("#phoneMsg").html("获取验证码");    
			} else { 
				$("#phoneMsg").html("重新发送(" + wait + ")");    
				wait--;  
				setTimeout(function() {  
					time_end(wait)  
				},  
				1000)  
			}  
		} 
		
	//图片验证码
	function getImgCode(){
		$.post('/beadwalletloanapp/app/operate/appCheckLogin/refreshPicCode.do',{"phone":$("#mobile").val(),"userId":getUUID(),"loginToken":getLoginToken(),"piccodeType":$("#dataType").val()},function(data){
				if(data.code!='000'){
					alert("验证码发送失败");
				}else{
					$("#picCodeImg").attr("src","data:image/jpeg;base64,"+data.result);
				}
		});
	}
	//特殊只读属性
	function setReadOnly(id){
	   $("#"+id).attr("readonly","readonly");
	}
</script>
</body>
</html>