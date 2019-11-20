//
//package com.waterelephant.sxyDrainage.controller;
//
//
//import java.util.HashMap;
//import java.util.Map;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.beadwallet.service.utils.HttpClientHelper;
//import com.waterelephant.sxyDrainage.entity.xianjinbaika.XianJinBaiKaCommonRequest;
//import com.waterelephant.sxyDrainage.entity.xianjinbaika.XianJinBaiKaRequest;
//import com.waterelephant.sxyDrainage.utils.xianJinBaiKa.XianJinBaiKaConstant;
//import com.waterelephant.utils.MD5Util;
//
///**
// * Module:
// * <p>
// * XianJinBaiKaTest.java
// *
// * @author zhangchong
// * @version 1.0
// * @description: <描述>
// * @since JDK 1.8
// */
//public class XianJinBaiKaTest {
//    public static void main(String[] args) {
//        //testCheckUser();
//        // testValidBankList();
//        // testApplyBindCard();
//        // testPushOrder();
//        // testCalculate();
//        // testBindBankCardList();
//        // testOrderStatus();
//        // testauthStatus();
//        // testRepayplan();
//        // testContracts();
//        // testApplyRepay();
//        testSavePushUserBaseInfo();
//        System.out.println("2018-07-04".replace("-", "."));
//
//    }
//
//    private static String makeSign(String call, String param) {
//        String string = null;
//        try {
//            String ua = "SH-XJ360";
//            String signkey = "844a04a3ce024f5a2050ccd2099cab53";
//            String key = ua + signkey + ua;
//            string = MD5Util.md5(key + call + key + param + key);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return string;
//    }
//
//    private static String url = "http://localhost:9080/beadwalletloanapp/sxyDrainage/xjbk.do";
//
//    public static void testCheckUser() {
//        String call = "User.isUserAccept";
//
//        Map<String, String> xRequest = new HashMap<>(16);
//        xRequest.put("ua", "SH-XJ360");
//        xRequest.put("call", call);
//
//        XianJinBaiKaCommonRequest xCommonRequest = new XianJinBaiKaCommonRequest();
//        xCommonRequest.setUser_name("宋千书");
//        xCommonRequest.setUser_phone("15352221***");
//        xCommonRequest.setUser_idcard("62052219950601****");
//        String param = JSON.toJSONString(xCommonRequest);
//
//        xRequest.put("args", param);
//        xRequest.put("sign", makeSign(call, param));
//        xRequest.put("timestamp", "1500693926");
//
//        String json = HttpClientHelper.post(url, "utf-8", xRequest);
//        System.out.println(json);
//    }
//
//    public static void testSavePushUserBaseInfo() {
//        // String call = "Order.pushUserBaseInfo";
//        String call = "Order.pushUserAdditionalInfo";
//        Map<String, String> xRequest = new HashMap<>(16);
//        xRequest.put("ua", "SH-XJ360");
//        xRequest.put("call", call);
//        //String args="{\"order_info\":{\"loan_amount\":\"100000\",\"loan_term\":\"15\",
//        // \"order_sn\":\"20180704000001\",\"term_type\":\"1\"},\"user_info\":{\"degree\":\"4\",
//        // \"family_info\":{\"home_address\":\"尚浦中心8号楼\",\"home_areas\":\"北京市,市辖区,东城区\",\"house_type\":\"2\",
//        // \"live_time\":\"4\",\"permanent_address\":\"新江湾城路99号\",\"permanent_areas\":\"江西省,南昌市,市辖区\"},
//        // \"loan_use\":\"1\",\"user_idcard\":\"421022199307063953\",\"user_name\":\"张冲\",
//        // \"user_phone\":\"15972182935\"},
//        // \"user_verify\":{\"idcard_info\":{\"idcard_info\":{\"face_recognition_picture\":\"https://res.xianjincard
//        // .com/ygb/face_recognition/1410677/6C73257AFC88D.png\",\"id_number_f_picture\":\"http://res.koudailc
//        // .com/ygb/idcard/1410677/F0CD24D6F7222.jpg\",\"id_number_z_picture\":\"http://res.koudailc
//        // .com/ygb/idcard/1410677/8461BAFB3491C.jpg\",\"ocr_address\":\" 省 市 街道\",\"ocr_birthday\":\"1987-11-25\",
//        // \"ocr_end_time\":\"2050-06-10\",\"ocr_id_number\":\"128945178604278936\",\"ocr_issued_by\":\"某公安局\",
//        // \"ocr_name\":\"宋先生\",\"ocr_race\":\"汉\",\"ocr_sex\":\"男\",\"ocr_start_time\":\"2000-06-10\"}},
//        // \"operator_report_verify\":{\"application_check\":[{\"app_point\":\"user_name\",
//        // \"check_points\":{\"key_value\":\"宋先生\"}},{\"app_point\":\"id_card\",\"check_points\":{\"age\":30,
//        // \"city\":\"某市\",\"court_blacklist\":{\"arised\":false,\"black_type\":[]},
//        // \"financial_blacklist\":{\"arised\":false,\"black_type\":[]},\"gender\":\"男\",
//        // \"key_value\":\"124578177546327865\",\"province\":\"某省\",\"region\":\"某县\"}},
//        // {\"app_point\":\"cell_phone\",\"check_points\":{\"check_ebusiness\":\"未提供电商数据\",
//        // \"check_idcard\":\"与运营商提供的身份证号码匹配成功\",\"check_name\":\"与运营商提供的姓名匹配成功\",
//        // \"financial_blacklist\":{\"arised\":false,\"black_type\":[]},\"key_value\":\"18812345678\",
//        // \"reg_time\":\"2010-03-25 00:00:00\",\"reliability\":\"实名认证\",\"website\":\"上海移动\"}},
//        // {\"app_point\":\"home_addr\",\"check_points\":{\"check_addr\":\"未提供居住地址\",\"check_ebusiness\":\"未提供居住地址\",
//        // \"key_value\":\"无数据\"}},{\"app_point\":\"home_phone\",\"check_points\":{\"check_mobile\":\"未提供住所电话\",
//        // \"key_value\":\"无数据\"}},{\"app_point\":\"taobao\",\"check_points\":{\"key_value\":\"\",\"reg_time\":\"\",
//        // \"reliability\":\"\"}},{\"app_point\":\"jingdong\",\"check_points\":{\"key_value\":\"\",\"reg_time\":\"\",
//        // \"reliability\":\"\"}},{\"app_point\":\"contact\",\"check_points\":{\"check_mobile\":\"未提供联系人信息\",
//        // \"check_xiaohao\":\"未知\",\"contact_name\":\"\",\"key_value\":0,\"relationship\":\"\"}}],
//        // \"behavior_check\":[{\"check_point\":\"regular_circle\",\"check_point_cn\":\"朋友圈在哪里\",
//        // \"evidence\":\"未提供居住地址\",\"result\":\"无数据\",\"score\":0}],
//        // \"cell_behavior\":[{\"behavior\":[{\"call_cnt\":18,\"call_in_cnt\":11,\"call_in_time\":35.866666666667,
//        // \"call_out_cnt\":7,\"call_out_time\":28.916666666667,\"cell_loc\":\"上海\",\"cell_mth\":\"2018-04\",
//        // \"cell_operator\":\"chinamobilesh\",\"cell_operator_zh\":\"上海移动\",\"cell_phone_num\":\"18812345678\",
//        // \"net_flow\":0,\"sms_cnt\":78,\"total_amount\":-1}],\"phone_num\":\"18812345678\"}],
//        // \"contact_list\":[{\"call_cnt\":1,\"call_in_cnt\":1,\"call_in_len\":1.0166666666667,
//        // \"call_len\":1.0166666666667,\"call_out_cnt\":0,\"call_out_len\":0,\"contact_1m\":1,\"contact_1w\":0,
//        // \"contact_3m\":0,\"contact_3m_plus\":0,\"contact_afternoon\":0,\"contact_all_day\":false,
//        // \"contact_early_morning\":0,\"contact_holiday\":0,\"contact_morning\":0,\"contact_name\":\"未知\",
//        // \"contact_night\":0,\"contact_noon\":1,\"contact_weekday\":1,\"contact_weekend\":0,\"needs_type\":\"未知\",
//        // \"p_relation\":\"\",\"phone_num\":\"00862110016\",\"phone_num_loc\":\"未知\"}],
//        // \"contact_region\":[{\"region_avg_call_in_time\":1.6251243781095,
//        // \"region_avg_call_out_time\":1.6505050505051,\"region_call_in_cnt\":67,
//        // \"region_call_in_cnt_pct\":0.22635135135135,\"region_call_in_time\":108.88333333333,
//        // \"region_call_in_time_pct\":0.1641416044823,\"region_call_out_cnt\":33,
//        // \"region_call_out_cnt_pct\":0.14537444933921,\"region_call_out_time\":54.466666666667,
//        // \"region_call_out_time_pct\":0.058044119214238,\"region_loc\":\"上海\",\"region_uniq_num_cnt\":51}],
//        // \"ebusiness_expense\":[],\"main_service\":[{\"company_name\":\"中国招商银行客服电话\",\"company_type\":\"银行\",
//        // \"service_details\":[{\"interact_cnt\":8,\"interact_mth\":\"2017-11\"}],\"total_service_cnt\":29}],
//        // \"report\":{\"update_time\":\"2018-04-10T15:31:47+0800\"},\"trip_info\":[{\"trip_data_source\":[\"通信记录\"],
//        // \"trip_dest\":\"陕西\",\"trip_end_time\":\"2018-01-01\",\"trip_leave\":\"上海\",\"trip_person\":[\"未知\"],
//        // \"trip_start_time\":\"2017-12-23\",\"trip_transportation\":[\"未知\"],\"trip_type\":\"双休日\"}]},
//        // \"operator_verify\":{\"basic\":{\"cell_phone\":\"18812345678\",\"idcard\":\"610426********241*\",
//        // \"real_name\":\"*兵利\",\"reg_time\":\"2010-03-25 00:00:00\",\"update_time\":\"2018-04-10 15:32:09\"},
//        // \"calls\":[{\"call_type\":\"国内被叫\",\"cell_phone\":\"18812345678\",\"init_type\":\"被叫\",
//        // \"other_cell_phone\":\"008613009486795\",\"place\":\"上海\",\"start_time\":\"2017-11-30 17:49:50\",
//        // \"subtotal\":0,\"update_time\":\"2018-04-10 15:31:41\",\"use_time\":12}],\"datasource\":0}}}";
//        String args = "{\"order_info\":{\"order_sn\":\"20180704000001\",\"loan_amount\":\"100000\"," +
//            "\"loan_term\":\"15\",\"term_type\":\"1\"}," +
//            "\"user_additional\":{\"device_info\":{\"device_id\":\"867463036537217\",\"device_info\":\"OPPO " +
//            "R11s\",\"os_type\":\"Android\",\"os_version\":\"7.1.1\",\"ip\":\"101.81.15.143\",\"memory\":\"3.66 " +
//            "GB\",\"storage\":\"53.02 GB\",\"unuse_storage\":\"43.47 GB\",\"gps_longitude\":\"121.510596\"," +
//            "\"gps_latitude\":\"31.317884\",\"gps_address\":\"上海市杨浦区江湾城路靠近江湾68公馆\",\"wifi\":\"1\"," +
//            "\"wifi_name\":\"XJK_AD\",\"bettary\":\"39\",\"carrier\":\"中国移动\",\"tele_num\":\"46002\"," +
//            "\"app_market\":\"\",\"is_root\":\"0\",\"dns\":\"192.168.39.88,192.168.39.77\",\"is_simulator\":\"\"," +
//            "\"last_login_time\":\"1512627343\",\"pic_count\":\"\",\"android_id\":\"c112febbb8f4c25c\"," +
//            "\"udid\":\"c112febbb8f4c25c\",\"imsi\":\"\",\"mac\":\"02:00:00:00:00:00\",\"sdcard\":\"52.83 GB\"," +
//            "\"unuse_sdcard\":\"43.27 GB\",\"idfv\":\"867463036537217\",\"uuid\":\"\",\"idfa\":\"\"," +
//            "\"tongdun_device_id\":\"\",\"baiqishi_device_id\":\"\"}," +
//            "\"address_book\":{\"phone_list\":[\"中国00000000000000000000000000000000000000000000000" +
//            "联通_10000000000000000000000000000000000000000000000000010\",\"中国移动_10086\",\"中国电信_10001\"," +
//            "\"陵辛_18812345678\"]}," +
//            "\"contact_info\":{\"name\":\"爱屋吉屋\",\"mobile\":\"18221501320\",\"relation\":\"1\"," +
//            "\"name_spare\":\"cherry\",\"mobile_spare\":\"13671618954\",\"relation_spare\":\"5\",\"name_other_one\":\"爱屋00000000000000吉屋\"," +
//            "\"mobile_other_one\":\"18221501320000000000000000000000000000000000000000000\",\"name_other_two\":\"爱屋吉屋\",\"mobile_other_two\":\"18221501320\",\"name_other_three\":\"爱屋吉屋\"," +
//            "\"mobile_other_three\":\"18221501320\"}," +
//            "\"work_office_info\":{\"company_name\":\"\",\"areas\":\"\",\"address\":\"\",\"type\":\"\"," +
//            "\"work_age\":\"\",\"pay_type\":\"\",\"revenue\":\"\",\"tel_area\":\"\",\"tel_no\":\"\"}," +
//            "\"work_enterprise_info\":{\"user_position\":\"\",\"share\":\"\",\"is_market\":\"\"," +
//            "\"true_operation_address\":\"\",\"industry\":\"\",\"manage_type\":\"\",\"is_license\":\"\"," +
//            "\"manage_life_time\":\"\",\"total_revenue\":\"\",\"public_revenue\":\"\",\"private_revenue\":\"\"," +
//            "\"settle_revenue\":\"\",\"company_name\":\"\"},\"work_student_info\":{\"school_name\":\"\"," +
//            "\"location\":\"\",\"entrance\":\"\"},\"work_sole_info\":{\"true_operation_address\":\"\"," +
//            "\"industry\":\"\",\"manage_type\":\"\",\"is_license\":\"\",\"manage_life_time\":\"\"," +
//            "\"total_revenue\":\"\"},\"work_free_info\":{\"free_income\":\"\"},\"car_info\":{\"car_status\":\"\"," +
//            "\"car_price\":\"\",\"car_life_time\":\"\"},\"house_info\":{\"house_status\":\"\",\"location\":\"\"," +
//            "\"house_price\":\"\",\"house_loan_status\":\"\",\"house_pledge_status\":\"\"}," +
//            "\"profession_type\":{\"profession_type\":\"1\"},\"business_type\":{\"business_type\":\"2\"}," +
//            "\"credit_info\":{\"credit_status\":\"\"},\"social_security\":{\"social_ins_month\":\"\"}," +
//            "\"housing_security\":{\"housing_fund_month\":\"\"},\"marriage\":{\"marriage\":\"\"}," +
//            "\"revenue_source\":{\"revenue_source\":\"3\"},\"is_loan\":{\"is_loan\":\"2\",\"loan_type\":\"2\"}," +
//            "\"wechat\":{\"wechat\":\"2\"},\"email\":{\"email\":\"81021157@qq.com\"}," +
//            "\"qq\":{\"qq\":\"81021157\"}}}";
//        // String args=null;
//        xRequest.put("args", args);
//        xRequest.put("sign", makeSign(call, args));
//        xRequest.put("timestamp", "1500693926");
//
//        String json = HttpClientHelper.post(url, "utf-8", xRequest);
//        System.out.println(json);
//    }
//
//    public static void testValidBankList() {
//        String call = "BindCard.getValidBankList";
//
//        Map<String, String> xRequest = new HashMap<>(16);
//        xRequest.put("ua", "SH-XJ360");
//        xRequest.put("call", call);
//        xRequest.put("args", "");
//        xRequest.put("sign", makeSign(call, ""));
//        xRequest.put("timestamp", "1500693926");
//
//        String json = HttpClientHelper.post(url, "utf-8", xRequest);
//        System.out.println(json);
//    }
//
//    public static void testApplyBindCard() {
//        String call = "BindCard.applyBindCard";
//
//        Map<String, String> xRequest = new HashMap<>(16);
//        xRequest.put("ua", "SH-XJ360");
//        xRequest.put("call", call);
//
//        XianJinBaiKaCommonRequest xCommonRequest = new XianJinBaiKaCommonRequest();
//        xCommonRequest.setOrder_sn("20180704000001");
//        xCommonRequest.setBank_code("CCB");
//        xCommonRequest.setUser_name("张冲");
//        xCommonRequest.setUser_idcard("421022199307063953");
//        xCommonRequest.setCard_number("6217002870001944972");
//        xCommonRequest.setCard_phone("15972182935");
//        xCommonRequest.setUser_phone("15972182935");
//        xCommonRequest.setVerify_code("123456");
//        String param = JSON.toJSONString(xCommonRequest);
//
//        xRequest.put("args", param);
//        xRequest.put("sign", makeSign(call, param));
//        xRequest.put("timestamp", "1500693926");
//
//        String json = HttpClientHelper.post(url, "utf-8", xRequest);
//        System.out.println(json);
//    }
//
//    public static void testPushOrder() {
//        String call = "Order.pushOrderInfo";
//
//        XianJinBaiKaRequest xRequest = new XianJinBaiKaRequest();
//        xRequest.setUa(XianJinBaiKaConstant.UA_REQUEST);
//        xRequest.setCall(call);
//
//        String param = "{\"user_info\":{\"user_name\":\"张冲\",\"user_phone\":\"15972182935\"," +
//            "\"id_number\":\"421022199307063953\",\"profession_type\":\"1\"," +
//            "\"face_recognition_picture\":\"https://res.xianjincard.com/ygb/face_recognition/1.jpg\"," +
//            "\"id_number_picture\":\"\",\"id_number_z_picture\":\"https://res.xianjincard.com/ygb/idcard/1.jpg\"," +
//            "\"id_number_f_picture\":\"https://res.xianjincard.com/ygb/idcard/1.jpg\",\"ocr_name\":\"张冲\"," +
//            "\"ocr_race\":\"汉\",\"ocr_sex\":\"男\",\"ocr_birthday\":\"1993-07-06\"," +
//            "\"ocr_id_number\":\"421022199307063953\",\"ocr_address\":\"中国 省 市 街道\"," +
//            "\"ocr_issued_by\":\"上海市公安局某某分局\",\"ocr_start_time\":\"2016-05-25\",\"ocr_end_time\":\"2026-05-25\"}," +
//            "\"order_info\":{\"order_sn\":\"170819000003\",\"platform\":\"1\",\"loan_amount\":\"100000\"," +
//            "\"loan_term\":\"28\",\"term_type\":\"1\"}," +
//            "\"user_additional\":{\"work_office_info\":{\"company_name\":\"武汉水象\",\"areas\":\"\"," +
//            "\"address\":\"\",\"company_type\":\"3\",\"work_age\":\"2\",\"pay_type\":\"\",\"revenue\":\"10000\"," +
//            "\"tel_area\":\"\",\"tel_no\":\"\"},\"work_enterprise_info\":{\"user_position\":\"\",\"share\":\"\"," +
//            "\"is_market\":\"\",\"true_operation_address\":\"\",\"own_industry\":\"金融\",\"manage_type\":\"\"," +
//            "\"is_license\":\"\",\"own_manage_life_time\":\"2\",\"own_total_revenue\":\"100000\"," +
//            "\"public_revenue\":\"\",\"private_revenue\":\"\",\"settle_revenue\":\"\"," +
//            "\"own_company_name\":\"武汉水象\"},\"work_sole_info\":{\"true_operation_address\":\"\"," +
//            "\"sole_industry\":\"金融\",\"manage_type\":\"\",\"is_license\":\"\",\"sole_manage_life_time\":\"2\"," +
//            "\"sole_total_revenue\":\"100000\"},\"car_info\":{\"car_status\":\"1\",\"car_price\":\"200000\"," +
//            "\"car_life_time\":\"\"},\"house_info\":{\"house_status\":\"3\",\"location\":\"\"," +
//            "\"house_price\":\"2000000\",\"house_loan_status\":\"\",\"house_pledge_status\":\"\"}," +
//            "\"family_info\":{\"home_areas\":\"省,市,区\",\"home_address\":\"居住街道与门牌号\",\"live_time\":\"\"," +
//            "\"house_type\":\"\",\"permanent_areas\":\"\",\"permanent_address\":\"\"}," +
//            "\"work_free_info\":{\"free_income\":\"100000\"},\"marriage\":{\"marriage\":\"2\"}," +
//            "\"qq\":{\"qq\":\"7984000000\"},\"email\":{\"email\":\"email@example.com\"}}," +
//            "\"user_verify\":{\"address_book\":{\"phone_list\":[\"新世界网教_31216885\",\"新世界客服_31216729\"," +
//            "\"新世界总机_31216700\",\"医院耳鼻 85_81818135\",\"新世界中融商城_22817145\",\"浅橙_02869514351\",\"浅橙_02869514352\"," +
//            "\"浅橙_02869514353\",\"浅橙_02869514354\",\"浅橙_02869514355\",\"1测试_13918152966\",\"2测试_13918152968\"]}," +
//            "\"contact_info\":{\"name\":\"张三\",\"mobile\":\"13037103333\",\"relation\":\"父亲\"," +
//            "\"name_spare\":\"小芳\",\"mobile_spare\":\"同学\",\"relation_spare\":\"17621231111\"}," +
//            "\"zm_verify\":{\"zmxy_score\":601},\"operator_verify\":{\"basic\":{\"update_time\":\"2017-08-05 " +
//            "17:34:39\",\"idcard\":\"610426198711111111\",\"reg_time\":\"2010-03-25 00:00:00\"," +
//            "\"real_name\":\"张冲\",\"cell_phone\":\"15972182935\"},\"calls\":[{\"update_time\":\"2017-08-05 " +
//            "17:34:37\",\"start_time\":\"2017-06-01 11:32:06\",\"init_type\":\"被叫\",\"use_time\":22," +
//            "\"place\":\"上海\",\"cell_phone\":\"18717996666\",\"other_cell_phone\":\"18910550521\",\"subtotal\":0," +
//            "\"call_type\":\"本地通话\"},{\"update_time\":\"2017-08-05 17:34:37\",\"start_time\":\"2017-06-01 " +
//            "17:53:03\",\"init_type\":\"被叫\",\"use_time\":1,\"place\":\"上海\",\"cell_phone\":\"18717996666\"," +
//            "\"other_cell_phone\":\"17710120683\",\"subtotal\":0,\"call_type\":\"本地通话\"}]," +
//            "\"datasource\":\"chinamobilesh\"}}}";
//
//        xRequest.setArgs(param);
//        xRequest.setSign(makeSign(call, param));
//        xRequest.setTimestamp("1500693926");
//
//        String json = HttpClientHelper.post(url, "utf-8", JSON.toJSONString(xRequest));
//        System.out.println(json);
//    }
//
//    public static void testCalculate() {
//        String call = "Order.loanCalculate";
//
//        Map<String, String> xRequest = new HashMap<>(16);
//        xRequest.put("ua", "SH-XJ360");
//        xRequest.put("call", call);
//
//        XianJinBaiKaCommonRequest xCommonRequest = new XianJinBaiKaCommonRequest();
//        xCommonRequest.setLoan_amount(200000);
//        String param = JSON.toJSONString(xCommonRequest);
//
//        xRequest.put("args", param);
//        xRequest.put("sign", makeSign(call, param));
//        xRequest.put("timestamp", "1500693926");
//
//        String json = HttpClientHelper.post(url, "utf-8", xRequest);
//        System.out.println(json);
//    }
//
//    public static void testBindBankCardList() {
//        String call = "BindCard.getUserBindBankCardList";
//
//        Map<String, String> xRequest = new HashMap<>(16);
//        xRequest.put("ua", "SH-XJ360");
//        xRequest.put("call", call);
//
//        XianJinBaiKaCommonRequest xCommonRequest = new XianJinBaiKaCommonRequest();
//        xCommonRequest.setUser_phone("15972182935");
//        xCommonRequest.setUser_name("张冲");
//        xCommonRequest.setUser_idcard("421022199307063953");
//
//        String param = JSON.toJSONString(xCommonRequest);
//
//        xRequest.put("args", param);
//        xRequest.put("sign", makeSign(call, param));
//        xRequest.put("timestamp", "1500693926");
//
//        String json = HttpClientHelper.post(url, "utf-8", xRequest);
//        System.out.println(json);
//    }
//
//    public static void testOrderStatus() {
//        String call = "Order.getOrderStatus";
//
//        XianJinBaiKaRequest xRequest = new XianJinBaiKaRequest();
//        xRequest.setUa("SH-XJ360");
//        xRequest.setCall(call);
//
//        XianJinBaiKaCommonRequest xCommonRequest = new XianJinBaiKaCommonRequest();
//        xCommonRequest.setOrder_sn("170819000003");
//        xCommonRequest.setAct_type(1);
//
//        String param = JSON.toJSONString(xCommonRequest);
//
//        xRequest.setArgs(param);
//        xRequest.setSign(makeSign(call, param));
//        xRequest.setTimestamp("1500693926");
//
//        String json = HttpClientHelper.post(url, "utf-8", JSON.toJSONString(xRequest));
//        System.out.println(json);
//    }
//
//    public static void testauthStatus() {
//        String call = "User.authStatus";
//
//        Map<String, String> xRequest = new HashMap<>(16);
//        xRequest.put("ua", "SH-XJ360");
//        xRequest.put("call", call);
//
//        XianJinBaiKaCommonRequest xCommonRequest = new XianJinBaiKaCommonRequest();
//        xCommonRequest.setUser_phone("15972182935");
//        xCommonRequest.setUser_name("张冲");
//        xCommonRequest.setUser_idcard("421022199307063953");
//        xCommonRequest.setAuth_type("8");
//
//        String param = JSON.toJSONString(xCommonRequest);
//
//        xRequest.put("args", param);
//        xRequest.put("sign", makeSign(call, param));
//        xRequest.put("timestamp", "1500693926");
//
//        String json = HttpClientHelper.post(url, "utf-8", xRequest);
//        System.out.println(json);
//    }
//
//    public static void testRepayplan() {
//        String call = "Order.getRepayplan";
//
//        Map<String, String> xRequest = new HashMap<>();
//        xRequest.put("ua", "SH-XJ360");
//        xRequest.put("call", call);
//
//        XianJinBaiKaCommonRequest xCommonRequest = new XianJinBaiKaCommonRequest();
//        xCommonRequest.setOrder_sn("180416000001");
//
//        String param = JSON.toJSONString(xCommonRequest);
//
//        xRequest.put("args", param);
//        xRequest.put("sign", makeSign(call, param));
//        xRequest.put("timestamp", "1500693926");
//
//        String json = HttpClientHelper.post(url, "utf-8", xRequest);
//        System.out.println(JSONObject.parseObject(json));
//    }
//
//    public static void testContracts() {
//        String call = "Order.getContracts";
//
//        Map<String, String> xRequest = new HashMap<>(16);
//        xRequest.put("ua", "SH-XJ360");
//        xRequest.put("call", call);
//
//        XianJinBaiKaCommonRequest xCommonRequest = new XianJinBaiKaCommonRequest();
//        xCommonRequest.setOrder_sn("");
//        String param = JSON.toJSONString(xCommonRequest);
//
//        xRequest.put("args", param);
//        xRequest.put("sign", makeSign(call, param));
//        xRequest.put("timestamp", "1500693926");
//
//        String json = HttpClientHelper.post(url, "utf-8", xRequest);
//        System.out.println(JSONObject.parseObject(json));
//    }
//
//    public static void testApplyRepay() {
//        String call = "Order.applyRepay";
//
//        Map<String, String> xRequest = new HashMap<>(16);
//        xRequest.put("ua", "SH-XJ360");
//        xRequest.put("call", call);
//
//        XianJinBaiKaCommonRequest xCommonRequest = new XianJinBaiKaCommonRequest();
//        xCommonRequest.setOrder_sn("180417000009");
//        String param = JSON.toJSONString(xCommonRequest);
//
//        xRequest.put("args", param);
//        xRequest.put("sign", makeSign(call, param));
//        xRequest.put("timestamp", "1500693926");
//
//        String json = HttpClientHelper.post(url, "utf-8", xRequest);
//        System.out.println(JSONObject.parseObject(json));
//    }
//}
