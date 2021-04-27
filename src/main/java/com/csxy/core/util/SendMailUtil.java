package com.csxy.core.util;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 邮件发送工具类（可带附件发送）
 * @return
 * @date 2021/4/25
 * @author Yuwq
 */
public class SendMailUtil {
    private static JavaMailSenderImpl javaMailSender;

    //发件人
    private static final String SENDER = "1447600226@qq.com";

    static {
        javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("pop.qq.com");// 链接服务器
//        javaMailSender.setPort(25);// 默认使用25端口发送
        javaMailSender.setUsername("1447600226@qq.com");// 邮箱账号
        javaMailSender.setPassword("jeeewvpqnpmyjjda");// 授权码
//        javaMailSender.setDefaultEncoding("UTF-8");
//        javaMailSender.setProtocol("smtp");
    }

//    public static void main(String[] args) throws Exception {
//        //发送普通邮件
////        sendSimpleMail("1447600226@qq.com", "面试邀请", "邮件内容", false);
//
//        //发送带附件的邮件
//        HashMap<String, File> files = new HashMap<>();
//        File file = new File("E:\\szbg.docx");
//        files.put("szbg.docx", file);
//        sendAttachmentMail("1447600226@qq.com", "面试邀请", "邮件内容", files);
//    }

    /**
     * 发送普通邮件
     *  @param to 收件人
     * @param subject 主题
     * @param text 正文
     * @param isHtml 正文是否为html格式
     */
    public static void sendSimpleMail(String to, String subject, String text, boolean isHtml) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(SENDER, "通知");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, isHtml);
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送带附件邮件
     *
     * @param to 收件人
     * @param subject 主题
     * @param text 正文
     * @param files 附件
     */
    public static void sendAttachmentMail(String to, String subject, String text, Map<String, File> files) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(SENDER, "通知");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            Set<Map.Entry<String, File>> fileSet = files.entrySet();
            for (Map.Entry f : fileSet) {
                helper.addAttachment((String) f.getKey(), (File) f.getValue());
            }
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
