package com.waterelephant.moxie.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.entity.response.Response;
import com.beadwallet.service.moXie.entity.MobileBasic;
import com.beadwallet.service.moXie.entity.TaskStatusRsp;
import com.beadwallet.service.moXie.entity.VoiceCallList;
import com.beadwallet.service.utils.CommUtils;
import com.beadwallet.service.utils.ConfigReader;
import com.beadwallet.service.utils.HttpClientHelper;
import com.beadwallet.service.utils.HttpRequest;
import com.waterelephant.moxie.service.impl.MoxieCarrierServiceImpl;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * Created by song on 2017/3/20.
 *
 * @author GuoK
 * @version 1.0
 * @create_date 2017/3/20 15:39
 */
public class MoxieCarrierUtil {

    private static Logger logger = Logger.getLogger(MoxieCarrierUtil.class);
    /**
     * 魔蝎 - 运营商 - 任务创建 - 1.创建运营商采集任务
     *
     * @param paramMap
     * @return
     */
    public static Response<Object> createTask(Map<String, String> paramMap) {
        logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~  进入MoxieCarrierUtil");
        Response<Object> response = new Response<>();
        try {
            // 验证参数
            if (CommUtils.isNull(paramMap)) {
                response.setRequestCode("110");
                response.setRequestMsg("SDK创建采集任务请求参数为空");
                return response;
            }

            // 请求
            String url = ConfigReader.getConfig("request_path") + "/moxie/createTask.do"; // 路径
            String json = HttpClientHelper.post(url, "utf-8", paramMap);

            // 请求处理
            if (CommUtils.isNull(json) == true) {
                response.setRequestCode("101");
                response.setRequestMsg("创建采集任务 - SDK调用server返回为空");
                return response;
            }

            JSONObject jsonObject = JSONObject.parseObject(json);
            response.setRequestCode(jsonObject.getString("requestCode"));
            response.setRequestMsg(jsonObject.getString("requestMsg"));
            response.setObj(jsonObject.getString("obj"));
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setRequestCode("900");
            response.setRequestMsg("1.SDK-创建运营商采集任务异常：" + e.getMessage());
            return response;
        }
    }

    /**
     * 魔蝎 - 运营商 - 任务创建 - 2.获取运营商采集任务执行状态
     *
     * @param task_id 任务ID
     * @return
     */
    public static Response<Object> getTaskStatus(Map<String, String> paramMap) {
        Response<Object> response = new Response<>();
        try {
            // 取参
            String task_id = paramMap.get("task_id");

            // 验证参数
            if (CommUtils.isNull(task_id)) {
                response.setRequestCode("110");
                response.setRequestMsg("SDK获取运营商采集任务执行状态 - 参数【taskId】为空");
                return response;
            }

            // 请求
            String url = ConfigReader.getConfig("request_path") + "/moxie/getTaskStatus.do?task_id=" + task_id;
            String json = HttpRequest.doGet(url);

            // 请求处理
            if (CommUtils.isNull(json) == true) {
                response.setRequestCode("101");
                response.setRequestMsg("获取运营商采集任务执行状态 - SDK调用server返回为空");
                return response;
            }

            JSONObject jsonObject = JSONObject.parseObject(json);
            response.setRequestCode(jsonObject.getString("requestCode"));
            response.setRequestMsg(jsonObject.getString("requestMsg"));

            String obj = jsonObject.getString("obj");
            if (obj != null && ("".equals(obj) == false)) {
                TaskStatusRsp taskStatusRsp = JSON.parseObject(jsonObject.getString("obj"), TaskStatusRsp.class);
                response.setObj(taskStatusRsp);
            }
        } catch (Exception e) {
            response.setRequestCode("900");
            response.setRequestMsg("2.SDK-获取运营商采集任务执行状态异常：" + e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 魔蝎 - 运营商 - 任务创建 - 3.输入短信或图片验证码
     *
     * @param task_id 任务ID
     * @param input   验证码
     * @return
     */
    public static Response<Object> inputCode(Map<String, String> paramMap) {
        Response<Object> response = new Response<>();
        try {
            // 取参
            String task_id = paramMap.get("task_id");
            String input = paramMap.get("input");

            // 验证
            if (CommUtils.isNull(task_id)) {
                response.setRequestCode("110");
                response.setRequestMsg("SDK获取请求参数【任务ID】为空");
                return response;
            }
            if (CommUtils.isNull(input)) {
                response.setRequestCode("111");
                response.setRequestMsg("SDK获取请求参数【验证码】为空");
                return response;
            }

            // 请求
            String url = ConfigReader.getConfig("request_path") + "/moxie/inputCode.do?task_id=" + task_id + "&input=" + input;
            String json = HttpRequest.doGet(url);

            // 请求处理
            if (CommUtils.isNull(json) == true) {
                response.setRequestCode("101");
                response.setRequestMsg("输入短信或图片验证码 - SDK调用server返回为空");
                return response;
            }

            JSONObject jsonObject = JSONObject.parseObject(json);
            response.setRequestCode(jsonObject.getString("requestCode"));
            response.setRequestMsg(jsonObject.getString("requestMsg"));
        } catch (Exception e) {
            e.printStackTrace();
            response.setRequestCode("900");
            response.setRequestMsg("3.SDK-输入短信或图片验证码异常：" + e.getMessage());
        }
        return response;
    }

    /**
     * 魔蝎 - 运营商 - 任务创建 - 3-1.获取图片验证码/二次授权短信验证码
     *
     * @param task_id 任务ID
     * @param type    类型（取值sms/img）（短信验证码/图片验证码）
     * @return
     */
    public static Response<Object> verifyCode(Map<String, String> paramMap) {
        Response<Object> response = new Response<>();
        try {
            // 取参
            String task_id = paramMap.get("task_id");
            String type = paramMap.get("type");

            // 验证
            if (CommUtils.isNull(task_id)) {
                response.setRequestCode("110");
                response.setRequestMsg("SDK获取请求参数【任务ID】为空");
                return response;
            }
            if (CommUtils.isNull(type)) {
                response.setRequestCode("111");
                response.setRequestMsg("SDK获取请求参数【类型】为空");
                return response;
            }

            // 请求
            String url = ConfigReader.getConfig("request_path") + "/moxie/verifyCode.do?task_id=" + task_id + "&type=" + type;
            System.out.println("verifyCode-url:" + url);
            String json = HttpRequest.doGet(url);

            // 请求处理
            if (CommUtils.isNull(json) == true) {
                response.setRequestCode("101");
                response.setRequestMsg("获取图片验证码/二次授权短信验证码 - SDK调用server返回为空");
                return response;
            }

            JSONObject jsonObject = JSONObject.parseObject(json);
            response.setRequestCode(jsonObject.getString("requestCode"));
            response.setRequestMsg(jsonObject.getString("requestMsg"));

            JSONObject jsonObject2 = jsonObject.getJSONObject("obj");
            if (jsonObject2 != null) {
                TaskStatusRsp taskStatusRsp = JSONObject.toJavaObject(jsonObject2, TaskStatusRsp.class);
                response.setObj(taskStatusRsp); // 返回体
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setRequestCode("900");
            response.setRequestMsg("3-1.SDK-获取图片验证码/二次授权短信验证码异常：" + e.getMessage());
        }
        return response;
    }

    /**
     * 魔蝎 - 运营商 - 账单查询 - 1.获取账号基本信息
     *
     * @param task_id 任务ID
     * @param type    类型（取值sms/img）（短信验证码/图片验证码）
     * @return
     */
    public static Response<Object> getBasicInfo(Map<String, String> paramMap) {
        Response<Object> response = new Response<>();
        try {
            // 取参
            String task_id = paramMap.get("task_id");
            String mobile = paramMap.get("mobile");

            // 验证
            if (CommUtils.isNull(task_id)) {
                response.setRequestCode("110");
                response.setRequestMsg("SDK获取请求参数【任务ID】为空");
                return response;
            }
            if (CommUtils.isNull(mobile)) {
                response.setRequestCode("111");
                response.setRequestMsg("SDK获取请求参数【手机号】为空");
                return response;
            }

            // 请求
            String url = ConfigReader.getConfig("request_path") + "/moxie/getBasicInfo.do?task_id=" + task_id + "&mobile=" + mobile;
            String json = HttpRequest.doGet(url);

            // 请求处理
            if (CommUtils.isNull(json) == true) {
                response.setRequestCode("101");
                response.setRequestMsg("获取账号基本信息 - SDK调用server返回为空");
                return response;
            }

            JSONObject jsonObject = JSONObject.parseObject(json);
            if ("200".equals(jsonObject.getString("requestCode")) == true) {
                response.setRequestCode(jsonObject.getString("requestCode"));
                response.setRequestMsg(jsonObject.getString("requestMsg"));

                JSONObject jsonObject2 = jsonObject.getJSONObject("obj");
                MobileBasic mobileBasic = JSONObject.toJavaObject(jsonObject2, MobileBasic.class);
                response.setObj(mobileBasic); // 返回体
            } else {
                response.setRequestCode(jsonObject.getString("requestCode"));
                response.setRequestMsg(jsonObject.getString("requestMsg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setRequestCode("900");
            response.setRequestMsg("1.SDK-获取账号基本信息异常：" + e.getMessage());
        }
        return response;
    }

    /**
     * 魔蝎 - 运营商 - 账单查询 - 3.获取账号通话详情
     *
     * @param task_id 任务ID
     * @param mobile  手机号码
     * @param month   查询月份（不传默认查询前6个月）
     * @return
     */
    public static Response<Object> getCallInfo(Map<String, String> paramMap) {
        Response<Object> response = new Response<>();
        try {
            // 取参
            String task_id = paramMap.get("task_id");
            String mobile = paramMap.get("mobile");
            String month = paramMap.get("month");

            // 验证
            if (CommUtils.isNull(task_id)) {
                response.setRequestCode("110");
                response.setRequestMsg("SDK获取请求参数【任务ID】为空");
                return response;
            }
            if (CommUtils.isNull(mobile)) {
                response.setRequestCode("111");
                response.setRequestMsg("SDK获取请求参数【手机号】为空");
                return response;
            }

            // 请求
            String json = HttpRequest.doGet(ConfigReader.getConfig("request_path") + "/moxie/getCallInfo.do?task_id=" + task_id + "&mobile=" + mobile + "&month=" + month);

            // 请求处理
            if (CommUtils.isNull(json) == true) {
                response.setRequestCode("101");
                response.setRequestMsg("获取账号通话详情 - SDK调用server返回为空");
                return response;
            }

            JSONObject jsonObject = JSONObject.parseObject(json);
            if ("200".equals(jsonObject.getString("requestCode")) == true) {
                response.setRequestCode(jsonObject.getString("requestCode"));
                response.setRequestMsg(jsonObject.getString("requestMsg"));

                JSONObject jsonObject2 = jsonObject.getJSONObject("obj");
                VoiceCallList voiceCallList = JSONObject.toJavaObject(jsonObject2, VoiceCallList.class);
                response.setObj(voiceCallList); // 返回体
            } else {
                response.setRequestCode(jsonObject.getString("requestCode"));
                response.setRequestMsg(jsonObject.getString("requestMsg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setRequestCode("900");
            response.setRequestMsg("3.SDK-获取账号通话详情异常：" + e.getMessage());
        }
        return response;
    }

    public static void main(String[] args) {
        //String json = "{\"description\":\"等待用户验证码输入超时\",\"finished\":true,\"phase\":\"LOGIN\",\"progress\":0,\"phase_status\":\"DONE_TIMEOUT\",\"input\":null,\"can_leave\":true,\"error_code\":\"CALO-22211-10\"}";
        String json = "{\"description\":\"请输入您手机收到的短信验证码，在5分钟内都可以继续使用。\",\"finished\":false,\"phase\":\"LOGIN\",\"progress\":0,\"phase_status\":\"WAIT_CODE\",\"input\":{\"type\":\"sms\",\"value\":null,\"wait_seconds\":\"60\"},\"can_leave\":false,\"error_code\":\"0\"}";
        try {
            //JSONObject jsonObject = JSONObject.parseObject(json);
            //net.sf.json.JSONObject jsonObject2 = net.sf.json.JSONObject.fromObject(json);
            //			TaskStatusRsp taskStatusRsp1 = JSONObject.parseObject(json, TaskStatusRsp.class);
            //			Map map = JSONObject.parseObject(json);

            //TaskStatusRsp taskStatusRsp2 = JSONObject.toJavaObject(jsonObject,  TaskStatusRsp.class);

            TaskStatusRsp taskStatusRsp = JSON.parseObject(json, TaskStatusRsp.class);

            System.out.println(taskStatusRsp.getInput().getType());
            //askStatusRsp taskStatusRsp = (TaskStatusRsp)net.sf.json.JSONObject.toBean(jsonObject2, TaskStatusRsp.class);
            //TaskStatusRsp taskStatusRsp = (TaskStatusRsp)net.sf.json.JSONObject.toBean(jsonObject2, TaskStatusRsp.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

