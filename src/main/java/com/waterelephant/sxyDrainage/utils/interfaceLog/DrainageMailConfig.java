//package com.waterelephant.sxyDrainage.utils.interfaceLog;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.mail.MailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//
///**
// * @author 王亚楠
// * @version 1.0
// * @date 2018/6/13
// * @since JDK 1.8
// */
//@Configuration
//public class DrainageMailConfig {
//    /** SMTP服务器 */
//    private static final String HOST = "smtp.163.com";
//    /** 端口号 */
//    private static final int PORT = 25;
//    /** 帐号 */
//    private static final String USERNAME = "sxyDrainage@163.com";
//    /** SMTP授权码 */
//    private static final String PASSWORD = "Beadwallet2018";
//
//    /**
//     * 配置邮件发送器
//     *
//     * @return 消息发送器
//     */
//    @Bean
//    public MailSender mailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        // 指定用来发送Email的邮件服务器主机名
//        mailSender.setHost(HOST);
//        // 默认端口，标准的SMTP端口
//        mailSender.setPort(PORT);
//        // 用户名
//        mailSender.setUsername(USERNAME);
//        // 密码
//        mailSender.setPassword(PASSWORD);
//        return mailSender;
//    }
//}
