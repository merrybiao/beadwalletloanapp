// 获取请求参数
function getParameter(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r !== null) {
        return decodeURI(r[2]);
    }
    return null;
}

// 对Date的扩展，将 Date 转化为指定格式的String   
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，   
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)   
// 例子：   
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423   
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18   
Date.prototype.format = function(fmt){ //author: meizz   
  var o = {   
    "M+" : this.getMonth()+1,                 //月份   
    "d+" : this.getDate(),                    //日   
    "h+" : this.getHours(),                   //小时   
    "m+" : this.getMinutes(),                 //分   
    "s+" : this.getSeconds(),                 //秒   
    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
    "S"  : this.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
}

// 常用验证
// val-要验证的值
// rule-验证规则，如果不支持当前规则，则返回失败。
function validate(val, rule) {
	var reg;
	if (rule == "digits") {// 数字
		reg = /^\d+$/;
	} else if (rule == "letters"){// 字母
		reg = /^[a-z]+$/i;
	} else if (rule == "date"){// 日期，格式:yyyy-mm-dd
		reg = /^\d{4}-\d{2}-\d{2}$/;
	} else if (rule == "time"){// 时间，00:00到23:59之间
		reg = /^([01]\d|2[0-3])(:[0-5]\d){1,2}$/;
	} else if (rule == "email"){// 邮箱
		reg = /^[\w\+\-]+(\.[\w\+\-]+)*@[a-z\d\-]+(\.[a-z\d\-]+)*\.([a-z]{2,4})$/i;
	} else if (rule == "url"){// 网址
		reg = /^(https?|s?ftp):\/\/\S+$/i;
	} else if (rule == "qq"){// QQ号码
		reg = /^[1-9]\d{4,}$/;
	} else if (rule == "IDcard"){// 身份证号码
		reg = /^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/;
	} else if (rule == "tel"){// 电话号码
		reg = /^(?:(?:0\d{2,3}[\- ]?[1-9]\d{6,7})|(?:[48]00[\- ]?[1-9]\d{6}))$/;
	} else if (rule == "mobile"){// 手机号
		reg = /^1[3-9]\d{9}$/;
	} else if (rule == "zipcode"){// 邮编
		reg = /^\d{6}$/;
	} else if (rule == "chinese"){// 汉字
		reg = /^[\u0391-\uFFE5]+$/;
	} else if (rule == "chineseName"){// 姓名
		reg = /^[\u0391-\uFFE5]{2,6}$/;
	} else if (rule == "password"){// 密码，6-16位字符，不能包含空格
		reg =  /(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/;
	}else if (rule == "bankcard"){ //银行卡号
		reg = /^(\d{16}|\d{19})$/;	
	}else {
		return false;
	}
	return reg.test(val);
}
// String去除空格
String.prototype.trim = function(){   
	return this.replace(/(^\s*)|(\s*$)/g, "");   
}
//关闭当前窗口
function closeWindow(){
	var userAgent = navigator.userAgent;
	if (userAgent.indexOf("Firefox") != -1 || userAgent.indexOf("Chrome") !=-1) {
	   window.close();
	} else {

	   window.opener = null;

	   window.open("", "_self");

	   window.close();

	}
}