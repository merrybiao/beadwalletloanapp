$(function () {
    $(".flex button").on("click", function () {
        // $(this).siblings("button").removeClass("on");
        // $(this).removeClass("on").addClass("on");
    });
    var code = getParameter("code");
    var state = getParameter("state");
    // 判断请求是否是从公众号跳转过来
    if (code != null && state == "beadwalletloanapp" && isWeixin()) {
        getOpenIdIndex(code);
    }
	// 判断是否为微信浏览器
	if (isWeixin()) {
		share();
	}
	getChannel();
	getLocation();
    
    var bwId = getUUID();
    var loginToken = getLoginToken();
    if (isExistLogin()) {
        // 成功登录
        $.ajax({// 最近一期工单查询 (过滤掉了1 草稿状态工单)
            type: "post",
            url: "/beadwalletloanapp/app/my/appCheckLogin/findBwOrderByBwId.do",
            dataType: "json",
            data: {
                "bwId": bwId,
                "loginToken": loginToken
            },
            success: function (data) {
				 if (data.result != null) {//
				    var result = data.result;
                    var statusId = data.result.statusId;// 工单状态
                    var orderId = data.result.id;// 工单ID	
                    $("#orderId").val(orderId);
					  //还款或续贷
					 if(statusId==9||statusId==13){
						 $("#loan").attr("class","btn font-bai  btn1 bs none");//我要借款
						 $("#progress").attr("class","btn font-bai  btn1 bs none");//查看进度
						 $("#repay").attr("class","btn font-bai  btn1 bs");//我要还款或续贷*********
					 }
					 //查看进度
					  if(statusId == '2'||statusId == '3' ||statusId == '4'||statusId == '5' || statusId == '11' || statusId == '12' || statusId == '14'||statusId == '7'){
						 $("#loan").attr("class","btn font-bai  btn1 bs none");//我要借款
						 $("#progress").attr("class","btn font-bai  btn1 bs ");//查看进度**************
						 $("#repay").attr("class","btn font-bai  btn1 bs none");//我要还款或续贷
					  }

					 if(statusId==6||statusId==8||statusId==1){//我要借款
						 $("#loan").attr("class","btn font-bai  btn1 bs ");//我要借款***********
						 $("#progress").attr("class","btn font-bai  btn1 bs none");//查看进度
						 $("#repay").attr("class","btn font-bai  btn1 bs none");//我要还款或续贷
					 }
	 
				 }
            }
        });
    } 
});

// 获取openID并自动登录
function getOpenIdIndex(code) {
	$.ajax({
        url: "/beadwalletloanapp/weixin/getOpenIdIndex.do",
        type: "post",
        dataType: "json",
        async: false,
        data: {"code" : code},
        success: function () {
            $.ajax({url: "/beadwalletloanapp/app/borrower/weiXinIndex.do", type: "post", async: false});
        }
    });
	/*$.post("/beadwalletloanapp/weixin/getOpenIdIndex.do", {"code": code}, function(res){
		if (res.code == "000") {
			$.post("/beadwalletloanapp/app/borrower/weiXinIndex.do");
		}
	});*/
}

// 分享
function share() {
	var url = location.href;
	if (url.indexOf("#") != -1) {
		url = url.substring(0, url.indexOf("#"));
	}
	$.post(
		"/beadwalletloanapp/weixin/getJssdkSignature.do", 
		{"url": url}, 
		function(data) {
			wx.config({
				debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
				appId: data.result.appId, // 必填，公众号的唯一标识
				timestamp: data.result.timestamp, // 必填，生成签名的时间戳
				nonceStr: data.result.nonceStr, // 必填，生成签名的随机串
				signature: data.result.signature,// 必填，签名
				jsApiList: ["getLocation", "hideMenuItems", "onMenuShareTimeline", "onMenuShareAppMessage"] // 必填，需要使用的JS接口列表
			});
			
			wx.ready(function(){
				/*var code = getParameter("code");
				var state = getParameter("state");
				// 判断请求是否是从公众号跳转过来
				if (code != null && state == "beadwalletloanapp") {
					// config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
					wx.getLocation({
						type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
						success: function (res) {
							var geocoder = new qq.maps.Geocoder(
									{
										complete: function (
												result) {
											var location = result.detail.addressComponents.province
													+ result.detail.addressComponents.city;
											$.post(
															"/beadwalletloanapp/weixin/getLocationIndex.do",
															{
																"location": location
															},
															function (
																	data) {
																if (data.code != "000") {
																	alert("定位失败");
																}
															},
															"json");
										}
									});
							var latLng = new qq.maps.LatLng(
									res.latitude,
									res.longitude);
							geocoder.getAddress(latLng);
						},
						fail: function (res) {
							alert("定位失败");
						},
						cancel: function (res) {
							alert("定位失败，请允许应用进行定位");
						}
					});
					
				} */
				// config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
				wx.hideMenuItems({
					// 要隐藏的菜单项，只能隐藏“传播类”和“保护类”按钮
					menuList: ["menuItem:share:qq", "menuItem:share:weiboApp", "menuItem:favorite", "menuItem:share:facebook", "menuItem:share:QZone",
								"menuItem:editTag", "menuItem:delete", "menuItem:copyUrl", "menuItem:originPage", "menuItem:openWithQQBrowser", "menuItem:openWithSafari", "menuItem:share:email", "menuItem:share:brand"]
				});
				
				wx.onMenuShareTimeline({
					title: '水象借点花', // 分享标题
					link: 'https://www.beadwallet.com/loanpage/fenxiang.html', // 分享链接
					imgUrl: 'https://www.beadwallet.com/app/loanh5/images/fenqi_wxfx_logo.png' // 分享图标
				});
				
				wx.onMenuShareAppMessage({
					title: '水象借点花', // 分享标题
					desc: '嗨翻双11，没钱怎么行？水象借点花让您放肆买，轻松贷！马上申请!', // 分享描述
					link: 'https://www.beadwallet.com/loanpage/fenxiang.html', // 分享链接
					imgUrl: 'https://www.beadwallet.com/app/loanh5/images/fenqi_wxfx_logo.png', // 分享图标
					type: '', // 分享类型,music、video或link，不填默认为link
					dataUrl: '' // 如果type是music或video，则要提供数据链接，默认为空
				});
			});
		},
		"json"
	);
}

// 获得渠道号
function getChannel() {
	var cid = getParameter("cid");
	if (cid != null) {
		$.post("/beadwalletloanapp/app/borrower/getChannel.do", {channelCode : cid});
	}
}

// 定位
function getLocation() {
	new qq.maps.Geolocation("QGUBZ-QLYKK-H6YJQ-A4ZK7-R2OC7-6EFUT", "H5").getLocation(function(pos) {
		var location = pos.province + "-" + pos.city;
		$.post("/beadwalletloanapp/weixin/getLocationIndex.do", {"location": location}, function(data) {
			if (data.code != "000") {
				//alert("定位失败");
			}
		},"json");
	},function(err){
		//alert("定位失败");
	});
}

// 是否存在登录
function isExistLogin() {
    var loginToken = getLoginToken();
    if (loginToken == null || loginToken == "undefined" || loginToken == "") {
        return false;
    }
    var isExist = false;
    $.ajax({
        url: "/beadwalletloanapp/app/borrower/loginCheck.do",
        type: 'post',
        dataType: 'json',
        async: false,
        data: {
            'loginToken': loginToken
        },
        success: function (obj) {
            var status = obj.code;
            if (status == "000") {
                isExist = true;
            }
        }
    });
    return isExist;
}

// 我要借款
function borrowMoney() {
    var bwId = getUUID();// 用户id
    var loginToken = getLoginToken();// 登录token
    if (isExistLogin()) {
    	//**********1登录成功************************************
    	/////////当前城市是否支持借款////////////
    	new qq.maps.Geolocation("QGUBZ-QLYKK-H6YJQ-A4ZK7-R2OC7-6EFUT", "H5").getLocation(function(pos) {
    		 $.ajax({
                 type: "post",
                 url: "http://139.224.17.43/beadwalletloanapp/app/cityCheck/appCheckLogin/cityCheck.do",
                 dataType: "json",
                 data: {
                     "loginToken": loginToken,
                     "cityName": pos.city
                 },
                 success: function (data) {
                     if (data.code!= "000") {//不支持借款
                        alert(data.msg);
                     }else{//支持借款
                    	 //----------------------------
                    	 // //////////////////生成空白订单
                         $.ajax({
                             type: "post",
                             url: "http://139.224.17.43/beadwalletloanapp/app/my/appCheckLogin/generateBwOrderForAuthThird.do",
                             dataType: "json",
                             data: {
                                 "loginToken": loginToken,
                                 "bwId": bwId,
                                 "channel": "4"
                             },
                             success: function (data) {
                                 if (data.code == "000") {
									 /**
                                     var orderId = data.result.orderId;
                 					$.post("/beadwalletloanapp/app/my/appCheckLogin/findBwBorrowerByBwId.do",
                                         {
                                             "loginToken": loginToken,
                                             "bwId": bwId
                                         }, function (data) {
                 						    if(data.result){
                 								var authStep = data.result.authStep;
                 								if (authStep == '1') {// 个人认证
                 									location.href = "gerenxinxi.html?orderId="+ orderId;
                 								} else if (authStep == '2') {// 工作认证
                 									location.href = "gongzuoxinxi.html?orderId="+ orderId;
                 								} else if (authStep == '3') {// 运营商               
                 									location.href = "yunyingshang.html?orderId="+ orderId;
                 								} else if (authStep == '5') {// 社保            
                 									location.href = "renzheng.html?orderId="+ orderId;
                 								}
                 							 }	  
                 					}, "json");
									**/
									 window.location.href = "RZshouye.html";
                                 }
                             }
                         });
                    	 //----------------------------
                     }
                 }
             });	
    	},function(err){
    		alert("定位失败,请尝试开启应用定位权限并清理缓存后重新进入!");
    	});	
    //**********************************************
    } else {
        // 2未登录
        window.location.href = "http://139.224.17.43/loanpage/login.html";
    }
}
