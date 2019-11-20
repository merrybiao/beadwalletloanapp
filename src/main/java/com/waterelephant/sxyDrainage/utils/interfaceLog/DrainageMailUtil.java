//package com.waterelephant.sxyDrainage.utils.interfaceLog;
//
//import com.waterelephant.utils.CommUtils;
//import org.apache.log4j.Logger;
//import org.springframework.context.ApplicationContext;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.web.context.ContextLoaderListener;
//import java.io.PrintWriter;
//import java.io.StringWriter;
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
///**
// * @author 王亚楠
// * @version 1.0
// * @date 2018/6/13
// * @since JDK 1.8
// */
//
//public class DrainageMailUtil {
//
//    private static Logger logger = Logger.getLogger(DrainageMailUtil.class);
//    /** 发件人 */
//    private static final String FROM = "sxyDrainage@163.com";
//    /** 收件人 */
//    private static final String TO = "yinliu@beadwallet.com";
//
//
//    /**
//     * 发送邮件
//     *
//     * @param theme 邮件主题
//     * @param throwable 异常
//     */
//    public static void sendSimpleEmail(String theme, Throwable throwable) {
//        try {
//            // 1.获取mail的bean
//            ApplicationContext ac = ContextLoaderListener.getCurrentWebApplicationContext();
//            JavaMailSender mailSender = ac.getBean("mailSender", JavaMailSender.class);
//            // 时间
//            String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date());
//            // IP
//            String hostAddress = InetAddress.getLocalHost().getHostAddress();
//            // 消息构造器
//            SimpleMailMessage message = new SimpleMailMessage();
//            // 发件人
//            message.setFrom(FROM);
//            // 收件人
//            message.setTo(TO);
//            // 主题
//            message.setSubject(theme);
//            // 正文
//            message.setText("服务器：[" + getHostAddress(hostAddress) + "], 时间：[" + nowTime + "]\n\r内容：\n\r" + getStackTrace(throwable));
//            // 发送
//            mailSender.send(message);
//
//            logger.info("邮件发送成功：" + TO);
//
//        } catch (UnknownHostException e) {
//            logger.info("邮件发送失败，自己找...");
//        }
//    }
//
//    /** 转换内-外网地址 */
//    private static String getHostAddress(String hostAddress) {
//        if (CommUtils.isNull(hostAddress)) {
//            return "为空";
//        }
//        if (hostAddress.equals("10.27.144.173")) {
//            return "106.14.45.32";
//        }
//        if (hostAddress.equals("10.31.21.188")) {
//            return "106.15.50.148";
//        }
//        if (hostAddress.equals("10.28.99.88")) {
//            return "106.14.237.235";
//        }
//        if (hostAddress.equals("10.25.35.1")) {
//            return "106.14.63.234";
//        }
//        if (hostAddress.equals("10.27.251.107")) {
//            return "106.14.62.233";
//        }
//        return "未能转换原地址" + hostAddress;
//    }
//
//    /** 异常堆栈信息 */
//    public static String getStackTrace(Throwable throwable) {
//        StringWriter sw = new StringWriter();
//        PrintWriter pw = new PrintWriter(sw);
//        try {
//            throwable.printStackTrace(pw);
//            return sw.toString();
//        } finally {
//            pw.close();
//        }
//    }
//}
