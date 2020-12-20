package cn.clouds.utils;


import cn.clouds.enums.ResponseEnums;
import cn.clouds.exception.Base5xxException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.CharBuffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

/**
 * @author clouds
 * @version 1.0
 */
public class Utils {

    public static boolean isJsonString(String json) {
        if (StringUtils.isEmpty(json)) return false;
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str)) {
            return true;
        }
        return false;
    }

    public static Map<String, Object> objectToMap(Object object) {
        final ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> returnResult = objectMapper.convertValue(object, Map.class);
        return returnResult;
    }

    /**
     * 处理json字符串value中的非法字符
     *
     * @param str
     * @return
     */
    public static String EncodeTextareaChar(String str) {
        if (str == null) return null;
        return str.replace("\"", "'")
                .replace("\r\n", " ")
                .replace("\n", " ")
                .replace("\r", " ");
    }

    /**
     * 将日期中的时分秒毫秒置为0
     *
     * @param date
     * @return
     * @throws ParseException
     */
    private static Date removeTime(Date date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date);
        return sdf.parse(dateStr);
    }

    /**
     * 计算两个日期之间相差多少天
     *
     * @param date1
     * @param date2
     * @return
     * @throws ParseException
     */
    public static int getDaysDifference(Date date1, Date date2) throws ParseException {
        return (int) ((removeTime(date2).getTime() - removeTime(date1).getTime()) / (1000 * 3600 * 24));
    }

    public static String generatePassword() {
        int length = 10;
        String[] characters = new String[]{
                "0123456789",
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz",
                "!@#$%^&*"
        };

        StringBuilder allChars = new StringBuilder();
        CharBuffer buffer = CharBuffer.allocate(length);

        for (String chars : characters) {
            fillRandomCharacters(chars, 1, buffer);
            allChars.append(chars);
        }

        fillRandomCharacters(allChars, length - buffer.position(), buffer);
        buffer.flip();
        randomize(buffer);

        return buffer.toString();
    }

    private static void fillRandomCharacters(CharSequence source, int count, Appendable target) {
        Random random = new Random();
        for (int i = 0; i < count; ++i) {
            try {
                target.append(source.charAt(random.nextInt(source.length())));
            } catch (IOException e) {
                throw new Base5xxException(HttpStatus.INTERNAL_SERVER_ERROR, ResponseEnums.ERROR, null, null);
            }
        }

    }

    private static void randomize(CharBuffer buffer) {
        Random random = new Random();
        for (int i = buffer.position(); i < buffer.limit(); ++i) {
            int n = random.nextInt(buffer.length());
            char c = buffer.get(n);
            buffer.put(n, buffer.get(i));
            buffer.put(i, c);
        }

    }

    /**
     * 将逗号分隔的字符串解析成字符串数组
     *
     * @param commaSeparatedStr
     * @return
     */
    public static String[] parsecommaSeparatedStr(String commaSeparatedStr) {
        if (commaSeparatedStr == null) {
            return null;
        }
        return commaSeparatedStr.split("\\s*,\\s*");
    }

    /**
     * Integer数组到String-用于前台的Integer数组id保存到数据库
     *
     * @param array
     * @return
     */
    public static String intArrayToString(int[] array) {
        if (array.length == 0) {
            return null;
        }
        String s = Arrays.toString(array);
        return s.substring(1, s.length() - 1).replace(" ", "");
    }

    /**
     * 解析字符串到Integer数组
     *
     * @param s
     * @return
     */
    public static int[] stringToIntArray(String s) {
        if (StringUtils.isEmpty(s)) {
            return null;
        }
        String[] s1 = s.split(",");
        return Stream.of(s1).mapToInt(Integer::parseInt).toArray();
    }
}
