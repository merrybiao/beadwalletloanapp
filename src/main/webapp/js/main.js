
'use strict';
function js (){
    var a = document.createElement("script");
    var  page_body = document.querySelector("body");
    a.src="js/jquery-1.11.3.min.js";
    a.type='text/javascript';
      page_body.appendChild(a);
}
//判断微信
function is_weixin() {
    var ua = navigator.userAgent.toLowerCase();
    if (ua.match(/MicroMessenger/i) == "micromessenger" && /iphone|ipad|ipod/.test(ua)) {
        return true;
    } else {
        return false;
    }
}
//头部
function head_top() {
    var page_body = document.querySelector("body");
    var header = document.createElement('div');
    var title=document.querySelector("head title");
    var title_ = title.innerHTML;
    if(title.getAttribute("bg")){
        var bg=title.getAttribute("bg");
        header.style.backgroundImage='url('+bg+')';
    }
    header.className = "head_top font-b";
    header.innerHTML = '<i onclick="history.go(-1)"></i>' + title_;
    if (title_ !== "借款" && title_ !== "还款" && title_ !== "个人中心")
        page_body.insertBefore(header, page_body.childNodes[0]);
}
//弹窗
function tanchuang(url) {
    var url = url;
    var tan = document.createElement('div');
    tan.setAttribute("id", "tanchuang");
    tan.innerHTML = '<i></i><iframe src=' + url + '></iframe>';
    document.querySelector("body").appendChild(tan);
    document.querySelector("#tanchuang i").onclick = function(e) {
        e.stopPropagation();
        var _this = document.querySelector("#tanchuang");
        _this.parentNode.removeChild(_this);
    }
}
function dialog(){
    $(".dialog").show();
}
function loader(obj, url,fn) {
    $(obj).load(url, function() {
        if(fn)fn();
         })
}
//ready
$(document).ready(function() {
    head_top();
    // loader("#jiekuan", "index1.html")
    // loader("#huankuan", "repay.html");
    // loader("#geren", "account_info.html");   
});
//load
window.onload = function() {
    //公共底部
};
