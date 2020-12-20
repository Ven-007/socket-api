package cn.clouds.utils;


import java.io.File;


/**
 * @author clouds
 * @version 1.0
 */
public class MailUtilsTest {
    static final MailUtils mailUtils = new MailUtils();

    public static void setUp() {
        mailUtils.setMailFrom("");
        mailUtils.setMailFromPasswd("");
        mailUtils.setPort("465");
        mailUtils.setHost("smtp.qq.com");
    }

    public static void main(String[] args) {
        setUp();
        setMail();
    }

    public static void setMail() {
        MailUtils.sendMail("", "test", "test", null);
        MailUtils.sendMail("", "hello", "hello",
                new File("/opt/win.jpg").getAbsolutePath());
    }
}
