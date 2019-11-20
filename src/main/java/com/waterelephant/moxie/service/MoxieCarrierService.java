package com.waterelephant.moxie.service;

import com.beadwallet.service.entity.response.Response;

import java.util.Map;

/**
 * Created by song on 2017/3/10.
 *
 * @author GuoK
 * @version 1.0
 * @create_date 2017/3/10 10:39
 */
public interface MoxieCarrierService {


    // SDK-创建运营商采集任务
    public Response<Object> gatherTaskSDK(Map<String, String> reqMap);

    // SDK-获取运营商任务执行状态
    public Response<Object> getGatherTaskStatusSDK(Map<String, String> reqMap);

    // SDK-获取二次授权图片，短信码
    public Response<Object> getMsgCodeSDK(Map<String, String> reqMap);

    // SDK-输入图片、短信验证码
    public Response<Object> inputCodeSDK(Map<String, String> reqMap);


    // 创建运营商采集任务
    public Response<Object> updateMoxieTask(String mobile, String password, String real_name, String id_card, String user_id);

    // 获取二次授权图片，短信码
    public Response<Object> getMsgCode(String task_id, String type);

    // 输入图片、短信验证码
    public Response<Object> updateInputCode(String task_id, String input, String bw_id, String mobile);
}
