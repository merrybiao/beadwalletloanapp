$(function () {
    var bwId = getUUID();
    var loginToken = getLoginToken();
    if (isExistLogin()) {
        // 成功登录
        $.ajax({// 最近一期工单查询 (过滤掉了1 草稿状态工单)
            type: "post",
            url: "/beadwalletloanapp/bewadwallet/loan/findBwOrderByBwId.do",
            dataType: "json",
            data: {
                "bwId": bwId,
                "loginToken": loginToken
            },
            success: function (data) {
                if (data.result != null) {// ****************************有工单信息
                    var statusId = data.result.statusId;// 工单状态
                    var orderId = data.result.id;// 工单ID	
                    var creditLimit=data.result.creditLimit;//借款额度
                    $("#orderId").val(orderId);
                    // ~~~~~~~~~审核中~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                    if (statusId == '2'||statusId == '3' ) {// 初审  终审-- 审核中
                        $("#show").addClass("review-cur-state");
						$("#shenghe").show();
                    }
                    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                    if (statusId == '4') {// 签约
                       $("#borrowAmount").val(data.result.borrowAmount);// 借款金额
                       var  repayTerm = data.result.repayTerm;// 借款期限
                       var  repayType = data.result.repayType;// 还款方式
					   $("#money").html(data.result.borrowAmount+'元');
					   if(repayTerm==null){
						   $("#repayTerm").html('1个月');
					   }else{
						   $("#repayTerm").html(repayTerm+'个月');
					   }
					   if(repayType==null){
						   $("#repayType").html('先息后本');	
					   }else{
						    if(repayType==1){
						    $("#repayType").html('先息后本');	
						    }else{
							 $("#repayType").html('等额本息');
						}
					   }
					   
                        // （1:先息后本 2:等额本息）
                    	// ////////////
                         $.ajax({
                             type: "post",
                             url: "/beadwalletloanapp/app/my/appCheckLogin/checkBankCardSignByBwId.do",
                             dataType: "json",
                             data: {
                                 "loginToken": loginToken,
                                 "bwId": bwId
                             },
                             success: function (data) {
                                 if (data.code == '000') {
                                   var  signStatus = data.result.signStatus;// 签约状态
                                     $("#sign_status").val(signStatus);//隐藏域 // 0// 未签约// 1已签约
                                     if (signStatus == '0') {
                                         $("#signStatus").html('签约马上拿钱');
                                     } else {
                                         $("#signStatus").html('签约马上拿钱');
                                     }
                                 }
                             }
                         });
                         // ////////////	          
                        $("#show").addClass("review-pass-state");
						$("#sign1").show();
                        $("#sign2").show();
                    }
					 //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                    if (statusId == '5' || statusId == '11' || statusId == '12' || statusId == '14') {// 待放款
                        var borrowAmount = data.result.borrowAmount;
                        var repayTerm = data.result.repayTerm;
                        var repayType = data.result.repayType;
                        // 填充页面
                        $("#money2").html(data.result.borrowAmount+'元');
					    $("#repayTerm2").html(repayTerm+'个月');
					    if(repayType==1){
						 $("#repayType2").html('先息后本');	
						}else{
						$("#repayType2").html('等额本息');
						}
						
					    $("#show").addClass("review-loan-state");
                        $("#waitLoan").show();
                    }
					 //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                    if (statusId == '6') {// 结束
                    	//$("#creditLimit").html("0.0");
                       // $("#index1").show(); 
                    }
                    if (statusId == '7') {// 拒绝(审核未通过)
                        // ////////////
                        $.ajax({
                            type: "post",
                            url: "/beadwalletloanapp/app/reject/record/findBwRejectRecord.do",
                            dataType: "json",
                            data: {
                                "orderId": orderId
                            },
                            success: function (data) {
                                if (data != null) {
                                     var rejectInfo=data.result.rejectInfo;//被拒信息
                                    var rejectType = data.result.rejectType;// 被拒类型
                                    // 0.永久被拒 1.非永久被拒
                                    var createTime = data.result.createTime+7862400000;// 再次申请借款的限制时间(文档以createTime为准)
                                    // 填充页面
                                    var date =new Date(createTime);
                                    if (rejectType == '1') {//暂时未通过
										 $("#zsNoPass").show();
										 $("#show").addClass("review-failnow-state");
										 $("#rejectInfo").html("系统评分不足！");
										 $("#solve").html("请于"+ date.format("yyyy-MM-dd")+ "之后再次申请！")
                                    } else {//永久未通过
										$("#noPass").show();
										$("#show").addClass("review-failforever-state");
										$("#rejectInfo2").html("系统评分不足！");
                                    }
                                }
                            }
                        });
                        // ////////////
                    }
                    if (statusId == '8') {// 撤回
                       
                    }
                    if (statusId == '9') {// 还款中
					   window.location.href = "/beadwalletloanapp/bewadwallet/loan/repayNormal.do?orderId="+orderId;
                    }

                    if (statusId == '13') {// 逾期
					  window.location.href = "/beadwalletloanapp/bewadwallet/loan/repayLate.do?orderId="+orderId;
                    }
                    // ////////////////////////////
                } else {// 无工单信息
                    window.location.href = "/beadwalletloanapp/bewadwallet/loan/haoDaiController.do";
                }

            }
        });
    } else {
        // 失败
        window.location.href = "/beadwalletloanapp/bewadwallet/loan/haoDaiController.do";
    }
});

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

// 签约
function sign() {
	 if(isExistLogin()){//登录成功
	     //是否勾选同意条款
	   // var agree=$("input[type='checkbox']").is(':checked');
        // if(!agree){
		//   alert("请勾选借款协议");
        //  return false; 
       //  }
		 var orderId = $("#orderId").val();
		$.ajax({//修改还款方式和借款时间
			type: "post",
			url: "/beadwalletloanapp/app/my/appCheckLogin/updateBwOrderAttrByOrderId.do",
			dataType: "json",
			data: {
				"orderId":orderId,
				"loginToken": getLoginToken(),
				"orderTerm": "1",
				"repayType": "1"
			},
			success: function (data) {
				if (data.code == '000') {
				////////////////////////////////////////////
					var signStatus=$("#sign_status").val();//获取隐藏
					if (signStatus == '0') {//未签约
					// ////////////借款人基本信息
						$.ajax({
							type: "post",
							url: "/beadwalletloanapp/app/my/appCheckLogin/findBwBorrowerByBwId.do",
							dataType: "json",
							data: {
								"loginToken": getLoginToken(),
								"bwId": getUUID()
							},
							async: false,
							success: function (data) {
								if (data.code == '000') {
									var phone = data.result.phone;
									location.href = "sign_agreemment.html?orderId=" + orderId+ "&signStatus=0&phone=" + phone + "&borrowAmount="+  $("#borrowAmount").val() + "&repayTerm=1&repayType=1";
								}
							}
						});             
					} else {//已经签约
						alert("签约成功");
						location.href="/beadwalletloanapp/bewadwallet/loan/haoDaiController.do"
					}
					////////////////////////////////////////////
				}else{
					alert("系统异常")
				}
			}
		}); 
		   
		 
	 }else{// 2登录失败
	     window.location.href = "/beadwalletloanapp/bewadwallet/loan/haoDaiController.do"; 
	 }
    
}