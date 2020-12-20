package cn.clouds.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.util.StringUtils;

/**
 * @author clouds
 * @version 1.0
 */
public class CloudStringUtils {

    public static void main(String[] args) {
        /**
         * 正则表达式 判断文件类型
         */
        boolean b = matcher("mp4".toUpperCase(), "(JPEG|jpeg|JPG|jpg|png|PNG|MP4)");
        System.out.println("b:" + b);
        System.out.println(b);
        if (!b) {
            System.out.println("文件格式不正确");
        } else {
            System.out.println("正确");
        }
        /**
         * TODO 正则表达式教程
         */
        System.out.println("+的reg匹配");
        System.out.println(matcher("runoob", "runo+b"));
        System.out.println(matcher("runooob", "runo+b"));
        System.out.println(matcher("runorunob", "runo+b"));
        System.out.println(matcher("runorunobfefe", "runo+b"));

        System.out.println("*的reg匹配");
        System.out.println(matcher("syykiferij", "syy*hhh"));
        System.out.println(matcher("syyhhh", "syy*hhh"));
        System.out.println(matcher("syyyyhhh55", "syy*hhh"));
        System.out.println(matcher("syyyyyyhhh", "syy*hhh"));
        System.out.println("?的reg匹配");
        System.out.println(matcher("syykiferij", "syy?hhh"));
        System.out.println(matcher("syyhhh", "syy?hhh"));
        System.out.println(matcher("syyyyhhh55", "syy?hhh"));
        System.out.println(matcher("syyyyyyhhh", "syy?hhh"));
        System.out.println(matcher("syhhh", "syy?hhh"));

        System.out.println("替换空白行及换行");
        String value="sd  ujuks fe  fweew\n fjwiaihf\r" +
                "wi";
        System.out.println(value);
        System.out.println(value.replaceAll("[\\s]", ""));
        System.out.println(matcher("sd  ujuks fe  wi", "[\\s]"));;
        System.out.println("正则表达式修饰符");
        /**
         * /pattern/flags
         * eg: /\s/g 匹配全局字符
         *     /pattern/i 匹配时忽略大小写 (?i)
         *     /pattern/多行匹配
         */
        System.out.println(matcher("JPG", "(?i)(png|jpg)"));

        System.out.println("[]匹配");
        System.out.println(matcher("jpg", "[jpg|png|JPEG|JPG|PNG|MP4|mp4]"));
        System.out.println(matcher("abc", "(abc)"));

    }

    /**
     * 正则表达式工具方法
     *
     * @param value String
     * @param reg   String
     * @return boolean
     */
    public static boolean matcher(String value, String reg) {
        if (StringUtils.isEmpty(value) || StringUtils.isEmpty(reg)) {
            return false;
        }
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(value);
        if (matcher.find()) {
            return true;
        }
        return false;
    }
}
