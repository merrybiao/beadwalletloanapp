package com.waterelephant.moxie.controller;

import com.beadwallet.service.entity.response.Response;
import com.waterelephant.dto.MoxieTaskDto;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwOrderAuth;
import com.waterelephant.moxie.service.MoxieCarrierService;
import com.waterelephant.service.BwOrderAuthService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.utils.*;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 魔蝎数据运营商认证
 *
 * @author GuoK
 * @version 1.0
 * @create_date 2017/3/10 10:39
 */
@Controller
@RequestMapping("/app/moxie")
public class MoxieCarrierController {
    private Logger logger = Logger.getLogger(MoxieCarrierController.class);

    @Autowired
    private MoxieCarrierService moxieCarrierService;

    @Autowired
    private IBwBorrowerService bwBorrowerService;

    @Autowired
    private BwOrderAuthService bwOrderAuthService;

    /**
     * 创建任务
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/carrier/createGatherTask.do")
    public AppResponseResult createTask(HttpServletRequest request, HttpServletResponse response) {
        AppResponseResult result = new AppResponseResult();
        try {
            // 获取请求参数
            String mobile = request.getParameter("phone");
            String password = request.getParameter("password");
            String real_name = request.getParameter("real_name");
            String id_card = request.getParameter("id_card");
            String user_id = request.getParameter("bw_id");
            String auth_channel = request.getParameter("auth_channel");
            String order_id = request.getParameter("order_id");
            if (CommUtils.isNull(mobile)) {
                result.setCode("1102");
                result.setMsg("手机号为空");
                return result;
            }
            if (CommUtils.isNull(password)) {
                result.setCode("1103");
                result.setMsg("服务密码为空");
                return result;
            }
            if (CommUtils.isNull(real_name)) {
                result.setCode("1104");
                result.setMsg("姓名为空");
                return result;
            }
            if (CommUtils.isNull(id_card)) {
                result.setCode("1105");
                result.setMsg("身份证号码为空");
                return result;
            }
            if (CommUtils.isNull(user_id)) {
                result.setCode("1106");
                result.setMsg("借款人ID为空");
                return result;
            }
            if (CommUtils.isNull(order_id)) {
                result.setCode("1107");
                result.setMsg("订单ID为空");
                return result;
            }
            if (CommUtils.isNull(auth_channel)) {
                result.setCode("1108");
                result.setMsg("渠道来源为空");
                return result;
            }

            logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 魔蝎运营商：请求MoxieCarrierService.updateMoxieTask（）  入参：" + "借款人id："
                    + user_id + "，手机号：" + mobile + "，服务密码：" + password + "，姓名：" + real_name + "，身份证号：" + id_card
                    + "，订单ID：" + order_id + "，渠道来源：" + auth_channel);
            Response<Object> res = moxieCarrierService.updateMoxieTask(mobile, password, real_name, id_card, user_id);
            if (!CommUtils.isNull(res) && !CommUtils.isNull(res.getRequestCode())
                    && !CommUtils.isNull(res.getRequestMsg())) {
                logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 魔蝎运营商：MoxieCarrierService.updateMoxieTask（）  出参  " + "code： "
                        + res.getRequestCode() + "，Msg: " + res.getRequestMsg());
            }

            // 处理运营商采集任务返回结果
            if (res.getRequestCode().equals("201")) {
                JSONObject taskJson = JSONObject.fromObject(res.getObj());
                String task_id = taskJson.getString("task_id");

                // 开始存入Redis
                MoxieTaskDto moxieTaskDto = new MoxieTaskDto();
                moxieTaskDto.setMobile(mobile);
                moxieTaskDto.setOrder_id(order_id);
                moxieTaskDto.setTask_id(task_id);
                moxieTaskDto.setUser_id(user_id);
                moxieTaskDto.setAccount(3);
                // RedisUtils.rpush(SystemConstant.MOXIE_CARRIER_KEY, JsonUtils.toJson(moxieTaskDto));
                RedisUtils.hset(SystemConstant.MOXIE_CARRIER_TEM, task_id, JsonUtils.toJson(moxieTaskDto));
                logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 不需要二次验证，调用Redis存入数据：" + res);

                // 修改认证状态
                BwBorrower borrower = new BwBorrower();
                borrower.setId(Long.parseLong(user_id));
                borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
                logger.info(
                        " ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 修改认证状态：根据借款人User_ID为：" + user_id + "的查询结果：" + borrower.getId());
                if (!CommUtils.isNull(borrower)) {
                    borrower.setAuthStep(5);
                    borrower.setUpdateTime(new Date());
                    int bNum = bwBorrowerService.updateBwBorrower(borrower);
                    logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 修改结果：" + bNum);
                    if (bNum < 0) {
                        result.setCode("1109");
                        result.setMsg("修改借款人认证状态信息操作失败");
                        return result;
                    } else {
                        result.setCode("201");
                        result.setMsg("成功");
                    }

                    // 添加运营商认证记录
                    BwOrderAuth bwOrderAuth = bwOrderAuthService.findBwOrderAuth(Long.valueOf(order_id), 1);
                    if (CommUtils.isNull(bwOrderAuth)) {
                        logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 添加认证运营商认证记录：order_id：" + order_id);
                        bwOrderAuth = new BwOrderAuth();
                        bwOrderAuth.setCreateTime(new Date());
                        bwOrderAuth.setAuth_channel(Integer.parseInt(auth_channel));
                        bwOrderAuth.setOrderId(Long.parseLong(order_id));
                        bwOrderAuth.setAuth_type(1);
                        bwOrderAuth.setUpdateTime(new Date());
                        bwOrderAuthService.saveBwOrderAuth(bwOrderAuth);
                    } else {
                        logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 修改认证运营商认证记录：order_id：" + order_id);
                        bwOrderAuth.setAuth_channel(Integer.parseInt(auth_channel));
                        bwOrderAuth.setUpdateTime(new Date());
                        bwOrderAuthService.updateBwOrderAuth(bwOrderAuth);
                    }

                    return result;
                } else {
                    logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 根据User_ID为：" + user_id + "的查询借款人信息为空");
                    result.setCode("1110");
                    result.setMsg("借款人信息查询为空");
                }
            }
            if (res.getRequestCode().equals("200")) {
                result.setCode("000");
                result.setMsg(res.getRequestMsg());
                result.setResult(res.getObj());
                return result;
            } else {
                result.setCode(res.getRequestCode());
                result.setMsg(res.getRequestMsg());
                return result;
            }
        } catch (Exception e) {
            result.setCode("111");
            result.setMsg("请求失败，请稍候再试");
            result.setResult(false);// 处理失败
            logger.error(e.getMessage());
            e.printStackTrace();
            return result;
        }
    }

    /**
     * 获取二次授权图片，短信码
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/carrier/getMsgCode.do")
    public AppResponseResult getMsgCode(HttpServletRequest request, HttpServletResponse response) {
        AppResponseResult result = new AppResponseResult();
        try {
            // 获取请求参数
            String type = request.getParameter("type");
            String task_id = request.getParameter("task_id");

            if (CommUtils.isNull(task_id)) {
                result.setCode("1102");
                result.setMsg("task_id 为空");
                return result;
            }
            if (CommUtils.isNull(type)) {
                result.setCode("1103");
                result.setMsg("type 为空");
                return result;
            }

            logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 魔蝎运营商：请求MoxieCarrierService.getMsgCode（） 入参：task_id：" + task_id
                    + ",type:" + type);
            // 获取二次验证信息
            Response<Object> res = moxieCarrierService.getMsgCode(task_id, type);
            if (!CommUtils.isNull(res) && !CommUtils.isNull(res.getRequestCode())
                    && !CommUtils.isNull(res.getRequestMsg())) {
                logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 魔蝎运营商：MoxieCarrierService.getMsgCode（） 出参： " + "code： "
                        + res.getRequestCode() + "，Msg: " + res.getRequestMsg());
            }
            // 处理运营商采集任务返回结果
            if (res.getRequestCode().equals("200")) {
                result.setCode("000");
                result.setMsg(res.getRequestMsg());
                result.setResult(res.getObj());
                return result;
            } else {
                result.setCode(res.getRequestCode());
                result.setMsg(res.getRequestMsg());
                return result;
            }
        } catch (Exception e) {
            result.setCode("111");
            result.setMsg("请求失败，请稍候再试");
            result.setResult(false);// 处理失败
            logger.error(e.getMessage());
            return result;
        }
    }

    /**
     * 提交短信或则图片验证码
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/carrier/inputCode.do")
    public AppResponseResult subCode(HttpServletRequest request, HttpServletResponse response) {

        AppResponseResult result = new AppResponseResult();
        try {
            String task_id = request.getParameter("task_id");
            String input = request.getParameter("code");
            String user_id = request.getParameter("bw_id");
            String phone = request.getParameter("phone");
            String auth_channel = request.getParameter("auth_channel");
            String order_id = request.getParameter("order_id");
            if (CommUtils.isNull(task_id)) {
                result.setCode("1101");
                result.setMsg("task_id 为空");
                return result;
            }
            if (CommUtils.isNull(input)) {
                result.setCode("1102");
                result.setMsg("验证码为空");
                return result;
            }
            if (CommUtils.isNull(user_id)) {
                result.setCode("1103");
                result.setMsg("借款人id为空");
                return result;
            }
            if (CommUtils.isNull(order_id)) {
                result.setCode("1104");
                result.setMsg("订单ID为空");
                return result;
            }
            if (CommUtils.isNull(phone)) {
                result.setCode("1105");
                result.setMsg("手机号码为空");
                return result;
            }
            if (CommUtils.isNull(auth_channel)) {
                result.setCode("1106");
                result.setMsg("渠道来源为空");
                return result;
            }

            logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 魔蝎运营商：请求MoxieCarrierService.updateInputCode（）  入参：" + "Task_id："
                    + task_id + "，验证码：" + input + "，借款人id：" + user_id + "，订单id：" + order_id + "，手机号码：" + phone
                    + "，渠道来源：" + auth_channel);
            Response<Object> res = moxieCarrierService.updateInputCode(task_id, input, user_id, phone);
            if (!CommUtils.isNull(res) && !CommUtils.isNull(res.getRequestCode())
                    && !CommUtils.isNull(res.getRequestMsg())) {
                logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 魔蝎运营商：MoxieCarrierService.updateInputCode（）  出参  " + "code： "
                        + res.getRequestCode() + "，Msg: " + res.getRequestMsg());
            } else {
                result.setCode("111");
                result.setMsg("请求失败，请稍候再试");
                return result;
            }
            if ("200".equals(res.getRequestCode())) {
                BwBorrower borrower = new BwBorrower();
                borrower.setId(Long.parseLong(user_id));
                borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
                logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 根据借款人：" + user_id + "查询结果：" + borrower.getId());

                // 调用redis存入数据
                MoxieTaskDto moxieTaskDto = new MoxieTaskDto();
                moxieTaskDto.setMobile(phone);
                moxieTaskDto.setOrder_id(order_id);
                moxieTaskDto.setTask_id(task_id);
                moxieTaskDto.setUser_id(user_id);
                moxieTaskDto.setAccount(3);
                // RedisUtils.rpush(SystemConstant.MOXIE_CARRIER_KEY, JsonUtils.toJson(moxieTaskDto));
                RedisUtils.hset(SystemConstant.MOXIE_CARRIER_TEM, task_id, JsonUtils.toJson(moxieTaskDto));
                logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 调用Redis存入数据：" + res);

                if (!CommUtils.isNull(borrower)) {
                    // 修改认证状态
                    borrower.setAuthStep(5);
                    borrower.setUpdateTime(new Date());
                    int bNum = bwBorrowerService.updateBwBorrower(borrower);
                    logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 修改结果：" + bNum);
                    if (bNum < 0) {
                        result.setCode("1109");
                        result.setMsg("修改借款人认证状态操作失败");
                        return result;
                    } else {
                        result.setCode("000");
                        result.setMsg("成功");
                    }

                    // 添加认证记录
                    BwOrderAuth bwOrderAuth = bwOrderAuthService.findBwOrderAuth(Long.valueOf(order_id), 1);
                    if (CommUtils.isNull(bwOrderAuth)) {
                        bwOrderAuth = new BwOrderAuth();
                        bwOrderAuth.setCreateTime(new Date());
                        bwOrderAuth.setAuth_channel(Integer.parseInt(auth_channel));
                        bwOrderAuth.setOrderId(Long.parseLong(order_id));
                        bwOrderAuth.setAuth_type(1);
                        bwOrderAuth.setUpdateTime(new Date());
                        bwOrderAuthService.saveBwOrderAuth(bwOrderAuth);
                    } else {
                        bwOrderAuth.setAuth_channel(Integer.parseInt(auth_channel));
                        bwOrderAuth.setUpdateTime(new Date());
                        bwOrderAuthService.updateBwOrderAuth(bwOrderAuth);
                    }

                } else {
                    logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 根据" + user_id + "查询借款人信息为空");
                    result.setCode("1110");
                    result.setMsg("借款人信息查询为空");
                }

            } else if ("201".equals(res.getRequestCode())) {
                result.setCode("201");
                result.setMsg(res.getRequestMsg());
                result.setResult(res.getObj());
            } else {
                result.setCode("1107");
                result.setMsg(res.getRequestMsg());
            }
            return result;
        } catch (Exception e) {
            result.setCode("111");
            result.setMsg("请求失败，请稍候再试");
            result.setResult(false);// 处理失败
            logger.error(e.getMessage());
            e.printStackTrace();
            return result;
        }

    }

}