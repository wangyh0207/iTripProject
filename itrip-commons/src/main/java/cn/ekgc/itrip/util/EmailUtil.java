package cn.ekgc.itrip.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

/**
 * <b>邮件发送工具类</b>
 * @author wyh
 * @version 1.0.0
 * @since 1.0.0
 */
@Component("emailUtil")
public class EmailUtil {
	@Autowired
	private JavaMailSender mailSender;

	/**
	 * <b>发送邮件</b>
	 * @param to
	 * @param subject
	 * @param context
	 * @throws Exception
	 */
	@Async
	public void sendMail(String to, String subject, String context) throws Exception {
		// 创建邮件信息对象
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		// 创建 MimeMessageHelper, 当 Mulitipart 为 true 时，可以发送含有 HTML 代码的邮件
		MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
		// 设定收件人信息
		messageHelper.setTo(to);
		// 设定发件人信息
		messageHelper.setFrom(ConstantUtils.MAIL_FROM);
		// 设定抄送人
		messageHelper.setCc(ConstantUtils.MAIL_FROM);
		// 设置邮件主题
		messageHelper.setSubject(subject);
		// 设置邮件发送内容
		messageHelper.setText(context, true);
		// 进行邮件发送
		mailSender.send(mimeMessage);
	}
}