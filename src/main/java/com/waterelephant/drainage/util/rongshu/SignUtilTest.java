package com.waterelephant.drainage.util.rongshu;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuxiaoixng
 */
public class SignUtilTest {

    private static final String DEFAULT_PUBLIC_KEY=
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCgW2dDqDpGbE6t7MaVWw7z35ha" + "\r" +
                    "0geRDSEgJkn8W2HHWzgf2qTW0VylviYlY+R7+TM9w85V1JjPGO22zw6WI8bDQ0K2" + "\r" +
                    "3dQxVa3HxUrPSSwec5Q+tnyCyrko2VfPTioHcIOxhqqfL3DWRLhILvQC7k1jQjUD" + "\r" +
                    "A0+FvDkLww+S2k60GQIDAQAB" + "\r";

    private static final String DEFAULT_PRIVATE_KEY=
            "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKBbZ0OoOkZsTq3s" + "\r" +
                    "xpVbDvPfmFrSB5ENISAmSfxbYcdbOB/apNbRXKW+JiVj5Hv5Mz3DzlXUmM8Y7bbP" + "\r" +
                    "DpYjxsNDQrbd1DFVrcfFSs9JLB5zlD62fILKuSjZV89OKgdwg7GGqp8vcNZEuEgu" + "\r" +
                    "9ALuTWNCNQMDT4W8OQvDD5LaTrQZAgMBAAECgYAWLE1dF5fnQPaoKgNTh6HLqvFA" + "\r" +
                    "LaaKMgyQi3rTgDdG/6AFF5CPe6eZ628O4H8pfU3OjpKrX5g5mrLUAlF8BTpocYLY" + "\r" +
                    "Kpy9Oy2eGBI9ca9zaTup1aItGMiw9o4KnEzVb+KSy1lHsXY6SW1VigysotZunxYU" + "\r" +
                    "ZvC2KCCBnwcdXEUh2QJBANLXpycddBCY415mpgUqUy7txkGeMrjp8/FOLP1KbRkE" + "\r" +
                    "C8WjI54EX4AjXc2cSclIShAezMK8Na6F8jlTrGW7T7MCQQDCs6wtOXvm7d8ZiKU6" + "\r" +
                    "YHTcYMa6ecd7lTBLctwpc88XmOI1+z/TszVoVBVH6WqftP9GogGtwgHHHN/O+1af" + "\r" +
                    "5acDAkEAifbbRdkcDZA9l5QLpu2fKOImDOH7xswv+AJzpfqBkRD4swahU9EAvNRn" + "\r" +
                    "mRdfoPpQnGPLENIfPmgfrCt4b8k1yQJAGZjVgfyUtX+AXTMBxfL4aiCu/8US3MR4" + "\r" +
                    "XPL0zt5S059d3gryETr2QokLYzDku6poBTk3T0i6QxsgsW2JrevbUQJBAMAk32Z2" + "\r" +
                    "RfmVIeMl73fY0JRzkVv0uWqPShfP0qrIKNdkDXmUrImN2G4klkF8oD/4Aza+AGe2" + "\r" +
                    "ERnMnyFZOLfhqQU=" + "\r";

    public static void main(String[] args) {
    	String  data="{\"applyDate\":1508463883843,\"bankCityId\":0,\"bankDistrictId\":0,\"bankProvinceId\":0,\"basicInfo\":{\"careerType\":\"工薪族\",\"companyCity\":\"北京市\",\"companyCityId\":110100,\"companyDistrict\":\"海淀区\",\"companyDistrictId\":110108,\"companyName\":\"百融\",\"companyProvince\":\"北京\",\"companyProvinceId\":110000,\"degree\":\"30002\",\"houseAddress\":\"中关村东南小区\",\"houseCity\":\"北京市\",\"houseCityId\":110100,\"houseDistrict\":\"海淀区\",\"houseDistrictId\":110108,\"houseProvince\":\"北京\",\"houseProvinceId\":110000,\"houseState\":\"60003\",\"marriage\":\"50001\",\"unitAddress\":\"融科资讯中心\",\"unitPhone\":\"010-88888888\"},\"cid\":\"41272419821130333X\",\"contact\":{\"firstName\":\"雪花\",\"firstPhone\":\"13666666666\",\"firstRelation\":\"80003\",\"secondName\":\"陈宋氏\",\"secondPhone\":\"13888888888\",\"secondRelation\":\"90006\"},\"desc\":\"70010\",\"deviceDataUrl\":\"https://dymapi.xiaqiu.cn/hermes/api/getDeviceData.do?uid=1060&pid=1009&appId=1009\",\"extendInfo\":{},\"idInfo\":{\"address\":\"北京市海淀区上地东里五区9号楼6O4号\",\"backFile\":\"https://dymapi.xiaqiu.cn/hermes/api/getResourceFile.do?uid=1060&pid=1009&type=2&appId=1009\",\"cid\":\"41272419821130333X\",\"frontFile\":\"https://dymapi.xiaqiu.cn/hermes/api/getResourceFile.do?uid=1060&pid=1009&type=1&appId=1009\",\"gender\":1,\"issuedBy\":\"北京市公安局海淀分局\",\"name\":\"陈永涛\",\"natureFile\":\"https://dymapi.xiaqiu.cn/hermes/api/getResourceFile.do?uid=1060&pid=1009&type=3&appId=1009\",\"validDate\":\"2016.02.27-2036.02.27\"},\"isInstalment\":0,\"loanAmount\":5000,\"name\":\"陈永涛\",\"openid\":\"LaBSGPQw0ba3bGmo+9nY1BDCxcm8kv/aux6VXQljoZ8=\",\"operator\":{\"createTime\":1507883112000,\"operatorCity\":\"北京\",\"operatorName\":\"中国移动\",\"operatorProvince\":\"北京\",\"operatorType\":1,\"phone\":\"13601297635\",\"resourceUrl\":\"https://dymapi.xiaqiu.cn/hermes/api/getOperatorData.do?uid=1060&appId=1009&pid=1009\"},\"orderId\":\"120522\",\"period\":30,\"registerPhone\":\"13601297635\",\"sesameScore\":\"780\",\"uid\":1060}";
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("channelId", "551");
        paramMap.put("data",data );

        String sign = SignUtil.rsaSign(paramMap, RongShuConstant.PRI_KEY, "UTF-8");// 加签
        System.out.println(sign);

    }


}
