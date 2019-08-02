/**
 * 
 */
package com.provider.order.helper;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 项目名称: ldap-service <br>
 * 包: com.migu.tsg.microservice.atomicservice.common.helper <br>
 * 类名称: SendMailUtils.java <br>
 * 类描述: 邮件发送工具类<br>
 * 创建人: WangSheng <br>
 * 创建时间: 2017年9月15日上午9:41:20 <br>
 * 版本: v1.0
 */
@Component
public class SendMailHelper {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SendMailHelper.class);

    @Autowired
    private JavaMailSender javaMailSenderImpl;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.usable:false}")
    private Boolean usable = false;

    ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public void submit(final String mailTo, final List<Map<String, String>> usernameList) {
        if (usable) {
            threadPool.submit(new Runnable() {
                public void run() {
                    sendSimpleHtmlMail(mailTo, usernameList);
                }
            });
        }
    }

    /**
     * 发送简单的邮件
     * @param mailTo 邮件发送到目标邮箱
     * @param subject 邮件主题
     * @param text 邮件内容
     */
    public void sendSimpleMail(final String mailTo, final String subject, final String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        // 在setFrom处必须填写自己的邮箱地址，否则会报
        // Failed messages: com.sun.mail.smtp.SMTPSendFailedException:
        // 501 mail from address must be same as authorization user错误
        message.setFrom(username);
        message.setTo(mailTo);
        message.setSubject(subject);
        message.setText(text);
        try {
            javaMailSenderImpl.send(message);
        } catch (Exception e) { // MailException 邮件发送失败异常
            LOGGER.error(
                    "method[sendSimpleMail] Mail failed when new user was added, but successful users was created",
                    e);
            return;
        }
        LOGGER.info("Send mail {} successfully", mailTo);
    }

    /**
     * 批量发送简单的邮件
     * @param mailsTo 邮件发送到目标邮箱集合
     * @param usernameList 用户账号集合
     * @param password 用户密码
     */
    public void sendSimpleMailBatch(final List<String> mailsTo, final List<String> usernameList,
            final String password) {
        // 在setFrom处必须填写自己的邮箱地址，否则会报
        // Failed messages: com.sun.mail.smtp.SMTPSendFailedException:
        // 501 mail from address must be same as authorization user错误
        if (CollectionUtils.isNotEmpty(mailsTo) && CollectionUtils.isNotEmpty(usernameList)) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(username);
            message.setTo(mailsTo.toArray(new String[] {}));
            message.setSubject("Ldap Users");
            message.setText("Create user " + usernameList.toString() + " success, All user passwords are: "
                    + password);
            try {
                javaMailSenderImpl.send(message);
            } catch (Exception e) { // MailException 邮件发送失败异常
                LOGGER.error(
                        "method[sendSimpleMailBatch] Mail failed when new user was added, but successful users was created",
                        e);
                return;
            }
            LOGGER.info("Send mail {} successfully", mailsTo);
        }
    }

    /**
     * 发送HTML的邮件
     * @param mailTo 邮件发送到目标邮箱
     * @param usernameList 用户账号集合
     * @param password 用户密码
     */
    public void sendSimpleHtmlMail(final String mailTo, final List<String> usernameList,
            final String password) {
        if (StringUtils.isNotBlank(mailTo)) {
            // 使用JavaMail的MimeMessage，支付更加复杂的邮件格式和内容
            MimeMessage msg = javaMailSenderImpl.createMimeMessage();
            try {
                // 创建MimeMessageHelper对象，处理MimeMessage的辅助类
                MimeMessageHelper helper = new MimeMessageHelper(msg, true);
                // 使用辅助类MimeMessage设定参数
                helper.setFrom(username);
                helper.setTo(mailTo);
                helper.setSubject("RBAC Ldap Users");
                // 第二个参数true，表示text的内容为html
                helper.setText("<body><p>Hello " + mailTo + "</p> Create user " + usernameList.toString()
                        + " success, All user passwords are: " + password + "</body>", true);
                javaMailSenderImpl.send(msg);
            } catch (Exception e) { // MessagingException,MailException 邮件发送失败异常
                LOGGER.error(
                        "method[sendSimpleHtmlMail] Mail failed when new user was added, but successful users was created",
                        e);
                return;
            }
            LOGGER.info("Send mail {} successfully", mailTo);
        }
    }

    /**
     * 发送HTML的邮件
     * @param mailTo 邮件发送到目标邮箱
     * @param usernameList 用户账号集合
     * @param password 用户密码
     */
    public void sendSimpleHtmlMail(final String mailTo, final List<Map<String, String>> usernameList) {
        if (StringUtils.isNotBlank(mailTo)) {
            // 使用JavaMail的MimeMessage，支付更加复杂的邮件格式和内容
            MimeMessage msg = javaMailSenderImpl.createMimeMessage();
            try {
                // 创建MimeMessageHelper对象，处理MimeMessage的辅助类
                MimeMessageHelper helper = new MimeMessageHelper(msg, true);
                // 使用辅助类MimeMessage设定参数
                helper.setFrom(username);
                helper.setTo(mailTo);
                helper.setSubject("RBAC Ldap Users");
                // 第二个参数true，表示text的内容为html
                helper.setText("<body><p>Hello " + mailTo + "</p> Create users " + usernameList.toString()
                        + " success </body>", true);
                javaMailSenderImpl.send(msg);
            } catch (Exception e) { // MessagingException,MailException 邮件发送失败异常
                LOGGER.error(
                        "method[sendSimpleHtmlMail] Mail failed when new user was added, but successful users was created",
                        e);
                return;
            }
            LOGGER.info("Send mail {} successfully", mailTo);
        }
    }

    /**
     * 发送带附件的HTML的邮件
     * @param mailTo 邮件发送到目标邮箱
     * @param usernameList 用户账号集合
     * @param password 用户密码
     * @param url 图片附件路径
     */
    public void sendSimpleHtmlAttachmentMail(final String mailTo, final List<String> usernameList,
            final String password, final String url) {
        if (StringUtils.isNotBlank(mailTo)) {
            // 使用JavaMail的MimeMessage，支付更加复杂的邮件格式和内容
            MimeMessage msg = javaMailSenderImpl.createMimeMessage();
            try {
                // 创建MimeMessageHelper对象，处理MimeMessage的辅助类
                MimeMessageHelper helper = new MimeMessageHelper(msg, true);
                // 使用辅助类MimeMessage设定参数
                helper.setFrom(username);
                helper.setTo(mailTo);
                helper.setSubject("Ldap Users");
                // 第二个参数true，表示text的内容为html
                // 注意<img/>标签，src='cid:file'，'cid'是contentId的缩写，'file'是一个标记，
                // 需要在后面的代码中调用MimeMessageHelper的addInline方法替代成文件
                helper.setText("<body><h1>Hello " + mailTo + "</h1> Create user " + usernameList.toString()
                        + " success, All user passwords are: " + password + "<img src='cid:file'/></body>",
                        true);
                // 添加图片new FileSystemResource("F:\\图片\\4.gif");
                FileSystemResource file = new FileSystemResource(url);
                helper.addInline("file", file);
                javaMailSenderImpl.send(msg);
            } catch (Exception e) { // MessagingException,MailException 邮件发送失败异常
                LOGGER.error(
                        "method[sendSimpleHtmlMail] Mail failed when new user was added, but successful users was created",
                        e);
                return;
            }
            LOGGER.info("Send mail {} successfully", mailTo);
        }
    }
}
