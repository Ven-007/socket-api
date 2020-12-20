package cn.clouds.utils;

import ch.qos.logback.classic.Level;
import cn.clouds.enums.ResponseEnums;
import cn.clouds.exception.Base5xxException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

/**
 * @author clouds
 * @version 1.0
 */
@Slf4j
@Component
public class MailUtils {
    private static String mailFrom;

    private static String mailFromPasswd;

    public static String host;

    private static String port;

    private static boolean smtpSsl;

    private static String protocal;

    private static String smtpAuthEnable;

    private static String mailDebug;

    public static String getMailFrom() {
        return mailFrom;
    }

    @Value("${spring.mail.username}")
    public void setMailFrom(String mailFrom) {
        MailUtils.mailFrom = mailFrom;
    }

    public static String getMailFromPasswd() {
        return mailFromPasswd;
    }

    @Value("${spring.mail.password}")
    public void setMailFromPasswd(String mailFromPasswd) {
        MailUtils.mailFromPasswd = mailFromPasswd;
    }

    public static String getHost() {
        return host;
    }

    @Value("${spring.mail.host}")
    public void setHost(String host) {
        MailUtils.host = host;
    }

    public static String getPort() {
        return port;
    }

    @Value("${spring.mail.port}")
    public void setPort(String port) {
        MailUtils.port = port;
    }

    public static String getSmtpSsl() {
        return port;
    }

    @Value("${spring.mail.smtp.ssl}")
    public void setSmtpSsl(boolean smtpSsl) {
        MailUtils.smtpSsl = smtpSsl;
    }

    public static String getProtocal() {
        return protocal;
    }

    @Value("${spring.mail.transport.protocol}")
    public void setProtocal(String protocal) {
        MailUtils.protocal = protocal;
    }

    public static String getSmtpAuthEnable() {
        return smtpAuthEnable;
    }

    @Value("${spring.mail.properties.mail.smtp.auth}")
    public void setSmtpEnable(String smtpEnable) {
        MailUtils.smtpAuthEnable = smtpEnable;
    }

    public static String getMailDebug() {
        return mailDebug;
    }

    @Value("${spring.mail.debug}")
    public void setMailDebug(String mailDebug) {
        MailUtils.mailDebug = mailDebug;
    }

    public static void sendMail(String mailTo, String subject, String body, String filename) {
        LogUtil.commonLog(Level.INFO, "", true, new Throwable());
        Properties properties = System.getProperties();
        Session session = prepareSession(properties);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(getMailFrom()));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
            message.setSubject(subject);
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(body);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            if (filename != null) {
                LogUtil.commonLog(Level.INFO, "邮件有附件：" + filename, null, new Throwable());
                messageBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(filename);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(filename);
                multipart.addBodyPart(messageBodyPart);
            }

            message.setContent(multipart);
            Transport.send(message);
            LogUtil.commonLog(Level.INFO, "邮件发送成功", null, new Throwable());
        } catch (Exception e) {
            LogUtil.commonLog(Level.ERROR, "邮件发送失败：" + e.getMessage(), null, new Throwable());
            throw new Base5xxException(HttpStatus.BAD_GATEWAY, ResponseEnums.ERROR, null, e);
        } finally {
            LogUtil.commonLog(Level.INFO, "", false, new Throwable());
        }
    }

    private static Session prepareSession(Properties properties) {
        properties.setProperty("spring.mail.smtp.host", getHost());
        properties.put("spring.mail.smtp.port", getPort());
        properties.put("spring.mail.smtp.auth", true);
        properties.setProperty("mail.transport.protocol", "smtp");
        if (true) {
            properties.put("spring.mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        }
        properties.put("spring.mail.debug", true);
        return Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(getMailFrom(), getMailFromPasswd());
            }
        });
    }

}
