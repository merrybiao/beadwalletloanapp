package com.waterelephant.utils;


import java.util.Properties;
import java.util.ResourceBundle;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * 带内容样式和附件的邮件
 * 
 * @author：Ellrien
 * @date: 2013-10-9上午10:14:05
 * @version: 1.0
 */
public class MailUtil {
	private static final JavaMailSenderImpl senderMail = new JavaMailSenderImpl();
	private static String fromEmail = "";
	static {
		ResourceBundle bundle = ResourceBundle.getBundle("email");
		if (bundle == null) {
			throw new IllegalArgumentException("[email.properties] is not found!");
		}
		senderMail.setHost(bundle.getString("host"));
		senderMail.setPort(25);
		Properties prop = new Properties();
		prop.setProperty("mail.smtp.auth", "true");
		fromEmail = bundle.getString("sender");
		// SMTP验证时，需要用户名和密码
		senderMail.setUsername(bundle.getString("userName"));
		senderMail.setPassword(bundle.getString("userPwd"));
		senderMail.setJavaMailProperties(prop); // 如果要密码验证,这里必须加,不然会报553错误
	}

	public static boolean send(String toEmail, String subject, String msg) {
		// 建立简单的邮件信息
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		// 设定收件人、寄件人、主题与内文
		mailMessage.setTo(toEmail);
		mailMessage.setFrom(fromEmail);// 这里必须和用户名一样,不然会报553错误
		mailMessage.setSubject(subject);
		mailMessage.setText(msg);
		try {
			senderMail.send(mailMessage);
		} catch (Exception e) {
			return false;
		}
		// 传送邮件

		/*
		 * // 发送HTML格式的邮件 // 建立邮件信息，可发送HTML格式 MimeMessage mimeMessage =
		 * senderMail.createMimeMessage(); // MimeMessage-->java的
		 * MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,
		 * true, "GBK"); // MimeMessageHelper-->spring的 // 不加后面两个参数会乱码
		 * 
		 * // 设置收件人，主题，内容 messageHelper.setSubject("Hello! ");
		 * messageHelper.setFrom("Goldcane@163.com");
		 * messageHelper.setTo("admin@126.com");
		 * 
		 * StringBuffer str = new StringBuffer(); str.append(
		 * "<html><head></head><body><h1>Hello! 中文! </h1></body></html>");
		 * messageHelper.setText(str.toString(), true); // 为true-->发送转义HTML
		 * 
		 * // senderMail.send(mimeMessage); //这个是不带附件的
		 * 
		 * // 发送带附件的 FileSystemResource file = new FileSystemResource( new File(
		 * "E:\\DevelopmentSoft\\spring-framework-3.0.5.RELEASE\\docs\\javadoc-api\\index.html"
		 * )); messageHelper.addAttachment("index.html", file);
		 * 
		 * senderMail.send(mimeMessage); // 这个是发送带附件的
		 */
		return true;
	}

	public static void main(String[] args) throws Exception {
		MailUtil.send("786736195@qq.com","aaaa"," bbbbbbbbbbbb");
	}

}