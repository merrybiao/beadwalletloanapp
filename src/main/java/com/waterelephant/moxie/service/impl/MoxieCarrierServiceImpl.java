package com.waterelephant.moxie.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.entity.response.Response;
import com.beadwallet.service.moXie.entity.*;
import com.beadwallet.service.utils.ConfigReader;
import com.beadwallet.service.utils.HttpClientHelper;
import com.beadwallet.service.utils.HttpRequest;
import com.waterelephant.dto.OperateCheck;
import com.waterelephant.moxie.service.MoxieCarrierService;
import com.waterelephant.service.BaseService;
import com.waterelephant.utils.CommUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 魔蝎运营商查询service层
 *
 * @author GuoK
 * @version 1.0
 * @create_date 2017/3/10 10:39
 */
@Service
public class MoxieCarrierServiceImpl extends BaseService<OperateCheck, Long> implements MoxieCarrierService {
    private Logger logger = Logger.getLogger(MoxieCarrierServiceImpl.class);

    // SDK-创建运营商采集任务
    @Override
    public Response<Object> gatherTaskSDK(Map<String, String> reqMap) {
        return createTask(reqMap);
    }

    // SDK-获取运营商任务执行状态
    @Override
    public Response<Object> getGatherTaskStatusSDK(Map<String, String> reqMap) {
        return getTaskStatus(reqMap);
    }

    // SDK-获取二次授权图片，短信码
    @Override
    public Response<Object> getMsgCodeSDK(Map<String, String> reqMap) {
        return verifyCode(reqMap);
    }

    // SDK-输入图片、短信验证码
    @Override
    public Response<Object> inputCodeSDK(Map<String, String> reqMap) {
        return inputCode(reqMap);
    }

    // 运营商认证
    @Override
    public Response<Object> updateMoxieTask(String mobile, String password, String real_name, String id_card,
                                            String user_id) {
        Response<Object> result = new Response<>();
        HashMap<String, Object> map = new HashMap<>();
        try {
            // 创建运营商采集任务开始
            Map<String, String> reqMap = new HashMap<>();
            reqMap.put("password", password);
            reqMap.put("real_name", real_name);
            reqMap.put("id_card", id_card);
            reqMap.put("user_id", user_id);
            reqMap.put("account", mobile);

            // 调用SDK获取创建运营商采集任务结果
            Response<Object> gatherTaskRes = new Response<>();
            try {
                // 创建运营商采集任务
                logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 开始调用魔蝎SDK创建任务接口  入参：" + "借款人id：" + user_id + "，手机号：" + mobile
                        + "，服务密码：" + password + "，姓名：" + real_name + "，身份证号：" + id_card);
                gatherTaskRes = gatherTaskSDK(reqMap);
                if (gatherTaskRes != null && gatherTaskRes.getRequestCode() != null) {
                    logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 魔蝎SDK创建任务接口  出参：" + " 姓名：" + real_name + "  " + gatherTaskRes.getRequestCode() + ":"
                            + gatherTaskRes.getRequestMsg());
                } else {
                    logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 调用魔蝎SDK创建任务接口返回参数为空" + " 姓名：" + real_name + "  ");
                    result.setRequestCode("1201");
                    result.setRequestMsg("网络繁忙，请稍后再试");
                    return result;
                }
            } catch (Exception e) {
                logger.error(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 调用魔蝎SDK创建任务接口异常 " + " 姓名：" + real_name + "  " + e.toString());
                e.printStackTrace();
                result.setRequestCode("1313");
                result.setRequestMsg("网络繁忙，请稍后再试");
                return result;
            }

            // 解析创建运营商采集任务结果
            if ("200".equals(gatherTaskRes.getRequestCode()) && !CommUtils.isNull(gatherTaskRes.getObj())) {
                // 获取taskid
                JSONObject taskJson = JSONObject.parseObject(String.valueOf(gatherTaskRes.getObj()));
                String task_id = taskJson.getString("task_id");
                if (CommUtils.isNull(task_id)) {
                    result.setRequestCode("1312");
                    result.setRequestMsg("请求繁忙，请稍后再试");
                    return result;
                }
                reqMap.clear();
                reqMap.put("task_id", task_id);

                logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 创建任务接口进入轮询" + " 姓名：" + real_name + "  ");
                // 轮询状态
                int lunXun = 0;
                while (lunXun < 28) {
                    ++lunXun;
                    // 线程Sleep
                    long sleep = 2000l;
                    try {
                        Thread.sleep(sleep);
                    } catch (InterruptedException e) {
                        logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ updateMoxieTask()线程睡眠: 异常 " + " 姓名：" + real_name + "  " + e.toString());
                        e.printStackTrace();
                    }
                    // 调用SDK获取任务状态
                    Response<Object> statusRes = this.getGatherTaskStatusSDK(reqMap);
                    if (!CommUtils.isNull(statusRes) && !CommUtils.isNull(statusRes.getObj())) {
//                         logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 调用魔蝎SDK查询任务状态接口返回参数：" + gatherTaskRes);
//						 logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 调用魔蝎SDK查询任务状态接口返回参数：" +
//						 gatherTaskRes.getRequestCode());
//						 logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 调用魔蝎SDK查询任务状态接口返回参数：" +
//						 gatherTaskRes.getRequestMsg());
//						 logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 调用魔蝎SDK查询任务状态接口返回参数：" + gatherTaskRes.getObj());
                    } else {
                        logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 调用魔蝎SDK查询任务状态接口返回参数为空" + " 姓名：" + real_name + "  ");
                        result.setRequestCode("1201");
                        result.setRequestMsg("网络繁忙，请稍后再试");
                        return result;
                    }

                    // 解析任务状态结果
                    TaskStatusRsp taskStatusRsp = (TaskStatusRsp) statusRes.getObj();
                    if (CommUtils.isNull(taskStatusRsp)) {
                        logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 调用魔蝎SDK查询任务状态接口返回参数为空" + " 姓名：" + real_name + "  ");
                        result.setRequestCode("1201");
                        result.setRequestMsg("网络繁忙，请稍后再试");
                        return result;
                    }
                    String phase_status = taskStatusRsp.getPhaseStatus();
                    String errorCode = taskStatusRsp.getErrorCode();
                    String description = taskStatusRsp.getDescription();
                    if (CommUtils.isNull(phase_status) || CommUtils.isNull(errorCode) || CommUtils.isNull(description)) {
                        logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 调用魔蝎SDK查询任务状态接口返回参数为空" + " 姓名：" + real_name + "  ");
                        result.setRequestCode("1201");
                        result.setRequestMsg("网络繁忙，请稍后再试");
                        return result;
                    }
                    // 状态接口正常
                    if ("200".equals(statusRes.getRequestCode())) {
                        // 等待输入验证码
                        if (StringUtils.equalsIgnoreCase("WAIT_CODE", phase_status)) {
                            result.setRequestCode("200");
                            result.setRequestMsg(description);
                            map.put("task_id", task_id);
                            map.put("status", "WAIT_CODE");
                            if (!CommUtils.isNull(taskStatusRsp.getInput())) {
                                TaskInput taskInput = taskStatusRsp.getInput();
                                map.put("wait_seconds", taskInput.getWaitSeconds());
                                if (!CommUtils.isNull(taskInput.getType())) {
                                    map.put("type", taskInput.getType());
                                }
                                if ("img".equals(taskInput.getType()) && !CommUtils.isNull(taskInput.getValue())) {
                                        map.put("pic_code", taskStatusRsp.getInput().getValue());
                                        //Base64转换为图片
//                                GenerateImage(taskStatusRsp.getInput().getValue());
                                }
                            }
                            result.setObj(map);
                            break;
                        } else if (StringUtils.equalsIgnoreCase("DONE_FAIL", phase_status) || StringUtils.equalsIgnoreCase("DONE_TIMEOUT", phase_status)) {
                            result.setRequestCode(errorCode);
                            result.setRequestMsg(description);
                            break;
                        } else if (taskStatusRsp.isCan_leave()) {
                            result.setRequestCode("201");
                            result.setRequestMsg(description);
                            result.setObj(gatherTaskRes.getObj());
                            break;
                        }

                    } else {
                        // 轮询创建任务状态异常信息
                        result.setRequestCode("1201");
                        result.setRequestMsg(description);
                        break;
                    }

                    if (lunXun >= 28) {
                        result.setRequestCode("1302");
                        result.setRequestMsg("请求超时，请稍后再试");
                        logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 轮询次数大于28次，退出轮询" + " 姓名：" + real_name + "  ");
                        return result;
                    }
                }
                logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 创建任务接口退出轮询" + " 姓名：" + real_name + "  ");
            } else {
                logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 创建运营商任务请求结果非200" + " 姓名：" + real_name + "  ");
                if (!CommUtils.isNull(gatherTaskRes.getObj())) {
                    try {
                        JSONObject taskJson = JSONObject.parseObject(String.valueOf(gatherTaskRes.getObj()));
                        logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 创建运营商任务请求结果非空" + " 姓名：" + real_name + "  " + taskJson);

                        if (!CommUtils.isNull(taskJson.get("detail"))) {
                            String detail = String.valueOf(taskJson.get("detail"));
                            result.setRequestMsg(detail);
                        } else {
                            result.setRequestMsg("请求繁忙，请稍候再试");
                        }
                        if (!CommUtils.isNull(taskJson.get("status"))) {
                            String code = String.valueOf(taskJson.get("status"));
                            result.setRequestCode(code);
                        } else {
                            result.setRequestCode("1317");
                        }

                    } catch (Exception e) {
                        result.setRequestCode("1313");
                        result.setRequestMsg("请求错误，请稍候再试");
                        logger.error(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 获取任务创建接口返回JSON转换异常 " + " 姓名：" + real_name + "  " +
                                e.toString());
                        return result;
                    }

                } else {
                    logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 创建运营商任务请求结果为空 " + " 姓名：" + real_name + "  ");
                    result.setRequestCode("1301");
                    result.setRequestMsg("请求错误，请稍候再试");
                }
            }
            return result;
        } catch (Exception e) {
            result.setRequestCode("1313");
            result.setRequestMsg("请求错误，请稍候再试");
            logger.error(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ " + " 姓名：" + real_name + "  " + e);
            e.printStackTrace();
            return result;
        }
    }

    //base64字符串转化成图片
    public static boolean GenerateImage(String imgStr) {   //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) //图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            String imgFilePath = "d://222.jpg";//新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 获取二次授权图片，短信码
    @Override
    public Response<Object> getMsgCode(String task_id, String type) {
        Response<Object> result = new Response<>();
        HashMap<String, String> reqMap = new HashMap<>();
        HashMap<String, Object> map = new HashMap<>();
        reqMap.put("task_id", task_id);
        reqMap.put("type", type);
        try {
            // 获取运营商采集任务执行状态 开始
            int lunXun = 0;
            while (lunXun < 28) {
                ++lunXun;
                // 线程Sleep
                long sleep = 2000l;
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    logger.error(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ getMsgCode()线程睡眠: 异常" + e.toString() + " task_id " + task_id);
                    e.printStackTrace();
                }
                // 调用SDK 获取运营商采集任务执行状态
                Response<Object> gatherMsgCode = this.getMsgCodeSDK(reqMap);
                if (gatherMsgCode != null && gatherMsgCode.getObj() != null) {
                    // logger.info("调用魔蝎SDK二次授权接口返回参数：" + gatherMsgCode.getRequestCode() + ":"
                    // + gatherMsgCode.getRequestMsg() + "," + gatherMsgCode.getObj());
                    // logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 调用魔蝎SDK二次授权接口返回参数：" + gatherMsgCode.getObj());
                } else {
                    logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~ 调用魔蝎SDK二次授权接口返回参数为空" + " task_id " + task_id);
                    result.setRequestCode("1201");
                    result.setRequestMsg("网络繁忙，请稍后再试");
                    return result;
                }

                TaskStatusRsp taskStatusRsp = (TaskStatusRsp) gatherMsgCode.getObj();
                if (CommUtils.isNull(taskStatusRsp)) {
                    logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 调用魔蝎SDK查询任务状态接口返回参数为空" + " task_id " + task_id);
                    result.setRequestCode("1201");
                    result.setRequestMsg("网络繁忙，请稍后再试");
                    return result;
                }
                String phase_status = taskStatusRsp.getPhaseStatus();
                String errorCode = taskStatusRsp.getErrorCode();
                String description = taskStatusRsp.getDescription();
                if (CommUtils.isNull(phase_status) || CommUtils.isNull(errorCode) || CommUtils.isNull(description)) {
                    logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 调用魔蝎SDK查询任务状态接口返回参数为空" + " task_id " + task_id);
                    result.setRequestCode("1201");
                    result.setRequestMsg("网络繁忙，请稍后再试");
                    return result;
                }

                // 解析获取二次授权接口
                if ("200".equals(gatherMsgCode.getRequestCode())) {
                    // 等待输入验证码
                    if (StringUtils.equalsIgnoreCase("WAIT_CODE", phase_status)) {
                        result.setRequestCode("200");
                        result.setRequestMsg(description);
                        if ("img".equals(taskStatusRsp.getInput().getType())) {
                            map.put("pic_code", taskStatusRsp.getInput().getValue());
//                            GenerateImage(taskStatusRsp.getInput().getValue());
                        }
                        map.put("wait_seconds", taskStatusRsp.getInput().getWaitSeconds());
                        map.put("task_id", task_id);
                        map.put("status", "WAIT_CODE");
                        map.put("type", taskStatusRsp.getInput().getType());
                        result.setObj(map);
                        break;
                    } else if (StringUtils.equalsIgnoreCase("DONE_FAIL", phase_status) || StringUtils.equalsIgnoreCase("DONE_TIMEOUT", phase_status)) {
                        result.setRequestCode(errorCode);
                        result.setRequestMsg(description);
                        break;
                    } else if (taskStatusRsp.isCan_leave()) {
                        result.setRequestCode("201");
                        result.setRequestMsg(description);
                        if (CommUtils.isNull(gatherMsgCode.getObj())) {
                            result.setObj(gatherMsgCode.getObj());
                        }
                        break;
                    }

                } else {
                    logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 魔蝎查询任务状态接口返回：" + description + " task_id " + task_id);
                    result.setRequestCode("1201");
                    result.setRequestMsg(description);
                }

                if (lunXun >= 28) {
                    result.setRequestCode("1302");
                    result.setRequestMsg("请求超时，请稍后再试");
                    logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 轮询次数大于28次，退出轮询" + " task_id " + task_id);
                    return result;
                }
            }
            return result;
        } catch (Exception e) {
            logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 魔蝎再次获取验证码异常:" + " task_id " + task_id + "  " + e.toString());
            result.setRequestCode("1313");
            result.setRequestMsg("请求错误，请稍候再试");
            return result;
        }
    }

    // 提交验证码
    @Override
    public Response<Object> updateInputCode(String task_id, String input, String bw_id, String mobile) {
        Response<Object> result = new Response<>();
        HashMap<String, Object> map = new HashMap<>();

        // 提交验证码开始
        Map<String, String> reqMap = new HashMap<>();
        reqMap.put("task_id", task_id);
        reqMap.put("input", input);
        try {
            // 调用SDK提交短信验证码
            logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 开始调用魔蝎SDK提交接口入参  task_id：" + task_id + ",input:" + input);
            Response<Object> inputCodeRes = inputCodeSDK(reqMap);
            if (inputCodeRes != null && inputCodeRes.getRequestCode() != null && inputCodeRes.getRequestMsg() != null) {
                logger.info("调用魔蝎SDK提交接口返回参数：" + inputCodeRes.getRequestCode() + ":" + inputCodeRes.getRequestMsg());
            } else {
                logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 调用魔蝎SDK提交接口返回参数为空" + " task_id " + task_id + "  ");
                result.setRequestCode("1201");
                result.setRequestMsg("网络繁忙，请稍后再试");
                return result;
            }

            // 解析创建运营商采集任务结果
            if ("200".equals(inputCodeRes.getRequestCode()) || "201".equals(inputCodeRes.getRequestCode())) {
                reqMap.clear();
                reqMap.put("task_id", task_id);
                // 获取运营商采集任务执行状态 开始
                int lunXun = 0;
                while (lunXun < 28) {
                    ++lunXun;
                    long sleep = 2000l;
                    try {
                        Thread.sleep(sleep);
                    } catch (InterruptedException e) {
                        logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ updateInputCode()睡眠: 异常" + e.toString() + " task_id " + task_id);
                        e.printStackTrace();
                    }

                    Response<Object> gatherTaskRes = getGatherTaskStatusSDK(reqMap);
                    if (!CommUtils.isNull(gatherTaskRes) && !CommUtils.isNull(gatherTaskRes.getObj()) && !CommUtils.isNull(gatherTaskRes.getRequestCode())) {
//                         logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 调用魔蝎SDK查询任务状态接口返回参数：" + gatherTaskRes);
//						 logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 调用魔蝎SDK查询任务状态接口返回参数：" +
//						 gatherTaskRes.getRequestCode());
//						 logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 调用魔蝎SDK查询任务状态接口返回参数：" +
//						 gatherTaskRes.getRequestMsg());
//						 logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 调用魔蝎SDK查询任务状态接口返回参数：" + gatherTaskRes.getObj());
                    } else {
                        logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 调用魔蝎SDK查询任务状态接口返回参数为空" + " task_id " + task_id + "  ");
                        result.setRequestCode("1201");
                        result.setRequestMsg("网络繁忙，请稍后再试");
                        return result;
                    }

                    // 调用SDK 获取运营商采集任务执行状态
                    TaskStatusRsp taskStatusRsp = (TaskStatusRsp) gatherTaskRes.getObj();
                    String phase_status = taskStatusRsp.getPhaseStatus();
                    String description = taskStatusRsp.getDescription();
                    String errorCode = taskStatusRsp.getErrorCode();
                    if (CommUtils.isNull(phase_status) || CommUtils.isNull(errorCode) || CommUtils.isNull(description)) {
                        logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 调用魔蝎SDK查询任务状态接口返回参数为空" + " task_id " + task_id + "  ");
                        result.setRequestCode("1201");
                        result.setRequestMsg("网络繁忙，请稍后再试");
                        return result;
                    }

                    if ("200".equals(gatherTaskRes.getRequestCode())) {
                        if (StringUtils.equalsIgnoreCase("WAIT_CODE", phase_status)) {
                            // 输入验证码
                            result.setRequestCode("201");
                            result.setRequestMsg(description);
                            map.put("task_id", task_id);
                            map.put("status", "WAIT_CODE");
                            if (!CommUtils.isNull(taskStatusRsp.getInput())) {
                                TaskInput taskInput = taskStatusRsp.getInput();
                                map.put("wait_seconds", taskInput.getWaitSeconds());
                                if (!CommUtils.isNull(taskInput.getType())) {
                                    map.put("type", taskInput.getType());
                                }
                                if ("img".equals(taskInput.getType()) && !CommUtils.isNull(taskInput.getValue())) {
                                    map.put("pic_code", taskStatusRsp.getInput().getValue());
                                    //Base64转换为图片
//                                GenerateImage(taskStatusRsp.getInput().getValue());
                                }
                            }
                            result.setObj(map);
                            logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 魔蝎验证码认证成功 task_id：" + task_id + " task_id " + task_id + "  ");
                            break;
                        } else if (StringUtils.equalsIgnoreCase("DONE_FAIL", phase_status) || StringUtils.equalsIgnoreCase("DONE_TIMEOUT", phase_status)) {
                            result.setRequestCode(errorCode);
                            result.setRequestMsg(description);
                            break;
                        } else if (taskStatusRsp.isCan_leave()) {
                            result.setRequestCode("200");
                            result.setRequestMsg(description);
                            if (CommUtils.isNull(gatherTaskRes.getObj())) {
                                result.setObj(gatherTaskRes.getObj());
                            }
                            break;
                        }

                    } else {
                        logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 魔蝎查询任务状态接口返回：" + description + " task_id " + task_id + "  ");
                        result.setRequestCode("1201");
                        result.setRequestMsg(description);
                    }
                    if (lunXun >= 28) {
                        result.setRequestCode("1302");
                        result.setRequestMsg("请求超时，请稍后再试");
                        logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 轮询次数大于28次，退出轮询" + " task_id " + task_id + "  ");
                        return result;
                    }
                }
            } else {
                // 提交短信异常信息
                try {
                    logger.info("开始调用魔蝎SDK状态查询接口入参  task_id：" + task_id);
//                    Response<Object> gatherTaskRes = getGatherTaskStatusSDK(reqMap);
                    logger.info("  调用魔蝎SDK提交接口返回参数：" + inputCodeRes.getRequestCode() + ":"
                            + inputCodeRes.getRequestMsg());
                    InputRsp inputRsp = (InputRsp) inputCodeRes.getObj();
                    // String phase_status = taskStatusRsp.getPhaseStatus();
                    if (!CommUtils.isNull(inputRsp)) {
                        result.setRequestCode(inputRsp.getStatus());
                        result.setRequestMsg(inputRsp.getDetail());
                    } else {
                        result.setRequestCode("1201");
                        result.setRequestMsg("系统繁忙，请稍候再试");
                    }
                } catch (Exception e) {
                    logger.error(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 魔蝎轮询接口异常：" + " task_id " + task_id + "  " + e.toString());
                    result.setRequestCode("1201");
                    result.setRequestMsg("系统繁忙，请稍候再试");
                }

            }
            return result;
        } catch (Exception e) {
            logger.error(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 魔蝎提交二次验证接口异常：" + " task_id " + task_id + "  " + e.toString());
            result.setRequestCode("1313");
            result.setRequestMsg("请求错误，请稍候再试");
            e.printStackTrace();
            return result;
        }
    }

    /**
     * 魔蝎 - 运营商 - 任务创建 - 1.创建运营商采集任务
     *
     * @param paramMap
     * @return
     */
    public static Response<Object> createTask(Map<String, String> paramMap) {
        Response<Object> response = new Response<>();
        try {
            // 验证参数
            if (com.beadwallet.service.utils.CommUtils.isNull(paramMap)) {
                response.setRequestCode("110");
                response.setRequestMsg("SDK创建采集任务请求参数为空");
                return response;
            }

            // 请求
            String url = ConfigReader.getConfig("request_path") + "/moxie/createTask.do"; // 路径
            String json = HttpClientHelper.post(url, "utf-8", paramMap);

            // 请求处理
            if (com.beadwallet.service.utils.CommUtils.isNull(json) == true) {
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
            response.setRequestMsg("1.SDK-创建运营商采集任务异常：" + e.toString());
            return response;
        }
    }

    /**
     * 魔蝎 - 运营商 - 任务创建 - 2.获取运营商采集任务执行状态
     *
     * @return
     */
    public static Response<Object> getTaskStatus(Map<String, String> paramMap) {
        Response<Object> response = new Response<>();
        try {
            // 取参
            String task_id = paramMap.get("task_id");

            // 验证参数
            if (com.beadwallet.service.utils.CommUtils.isNull(task_id)) {
                response.setRequestCode("110");
                response.setRequestMsg("SDK获取运营商采集任务执行状态 - 参数【taskId】为空");
                return response;
            }

            // 请求
            String url = ConfigReader.getConfig("request_path") + "/moxie/getTaskStatus.do?task_id=" + task_id;
            String json = HttpRequest.doGet(url);

            // 请求处理
            if (com.beadwallet.service.utils.CommUtils.isNull(json) == true) {
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
     * @return
     */
    public static Response<Object> inputCode(Map<String, String> paramMap) {
        Response<Object> response = new Response<>();
        try {
            // 取参
            String task_id = paramMap.get("task_id");
            String input = paramMap.get("input");

            // 验证
            if (com.beadwallet.service.utils.CommUtils.isNull(task_id)) {
                response.setRequestCode("110");
                response.setRequestMsg("SDK获取请求参数【任务ID】为空");
                return response;
            }
            if (com.beadwallet.service.utils.CommUtils.isNull(input)) {
                response.setRequestCode("111");
                response.setRequestMsg("SDK获取请求参数【验证码】为空");
                return response;
            }

            // 请求
            String url = ConfigReader.getConfig("request_path") + "/moxie/inputCode.do?task_id=" + task_id + "&input=" + input;
            String json = HttpRequest.doGet(url);

            // 请求处理
            if (com.beadwallet.service.utils.CommUtils.isNull(json) == true) {
                response.setRequestCode("101");
                response.setRequestMsg("输入短信或图片验证码 - SDK调用server返回为空");
                return response;
            }

            JSONObject jsonObject = JSONObject.parseObject(json);
            response.setRequestCode(jsonObject.getString("requestCode"));
            response.setRequestMsg(jsonObject.getString("requestMsg"));

            JSONObject jsonObject2 = jsonObject.getJSONObject("obj");
            if (jsonObject2 != null) {
                InputRsp inputRsp = JSONObject.toJavaObject(jsonObject2, InputRsp.class);
                response.setObj(inputRsp); // 返回体
            }
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
     * @return
     */
    public static Response<Object> verifyCode(Map<String, String> paramMap) {
        Response<Object> response = new Response<>();
        try {
            // 取参
            String task_id = paramMap.get("task_id");
            String type = paramMap.get("type");

            // 验证
            if (com.beadwallet.service.utils.CommUtils.isNull(task_id)) {
                response.setRequestCode("110");
                response.setRequestMsg("SDK获取请求参数【任务ID】为空");
                return response;
            }
            if (com.beadwallet.service.utils.CommUtils.isNull(type)) {
                response.setRequestCode("111");
                response.setRequestMsg("SDK获取请求参数【类型】为空");
                return response;
            }

            // 请求
            String url = ConfigReader.getConfig("request_path") + "/moxie/verifyCode.do?task_id=" + task_id + "&type=" + type;
            System.out.println("verifyCode-url:" + url);
            String json = HttpRequest.doGet(url);

            // 请求处理
            if (com.beadwallet.service.utils.CommUtils.isNull(json) == true) {
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
     * @return
     */
    public static Response<Object> getBasicInfo(Map<String, String> paramMap) {
        Response<Object> response = new Response<>();
        try {
            // 取参
            String task_id = paramMap.get("task_id");
            String mobile = paramMap.get("mobile");

            // 验证
            if (com.beadwallet.service.utils.CommUtils.isNull(task_id)) {
                response.setRequestCode("110");
                response.setRequestMsg("SDK获取请求参数【任务ID】为空");
                return response;
            }
            if (com.beadwallet.service.utils.CommUtils.isNull(mobile)) {
                response.setRequestCode("111");
                response.setRequestMsg("SDK获取请求参数【手机号】为空");
                return response;
            }

            // 请求
            String url = ConfigReader.getConfig("request_path") + "/moxie/getBasicInfo.do?task_id=" + task_id + "&mobile=" + mobile;
            String json = HttpRequest.doGet(url);

            // 请求处理
            if (com.beadwallet.service.utils.CommUtils.isNull(json) == true) {
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
            if (com.beadwallet.service.utils.CommUtils.isNull(task_id)) {
                response.setRequestCode("110");
                response.setRequestMsg("SDK获取请求参数【任务ID】为空");
                return response;
            }
            if (com.beadwallet.service.utils.CommUtils.isNull(mobile)) {
                response.setRequestCode("111");
                response.setRequestMsg("SDK获取请求参数【手机号】为空");
                return response;
            }

            // 请求
            String json = HttpRequest.doGet(ConfigReader.getConfig("request_path") + "/moxie/getCallInfo.do?task_id=" + task_id + "&mobile=" + mobile + "&month=" + month);

            // 请求处理
            if (com.beadwallet.service.utils.CommUtils.isNull(json) == true) {
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


}
