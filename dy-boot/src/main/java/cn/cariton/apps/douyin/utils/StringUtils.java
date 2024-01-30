package cn.cariton.apps.douyin.utils;


import cn.cariton.apps.douyin.exception.AppRuntimeException;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author icnz
 * @date 2023-10-20 14:25
 */
public final class StringUtils {

    /**
     * 空字符串
     */
    private static final String NULLSTR = "";

    /**
     * 下划线
     */
    private static final char SEPARATOR = '_';

    /**
     * 星号
     */
    private static final String START = "*";

    /**
     * 是否空字符串
     */
    public static boolean isBlank(CharSequence cs) {
        if (cs != null) {
            int length = cs.length();
            for (int i = 0; i < length; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 是否非空字符串
     */
    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * 是否是NULL或空字符串
     */
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * 是否非NULL和非空字符串
     */
    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

    /**
     * 是否驼峰字符串
     */
    public static boolean isCamel(String str) {
        return Character.isLowerCase(str.charAt(0)) && !str.contains("_");
    }

    /**
     * 字符串驼峰模式转下划线模式
     */
    public static String camelToUnderline(String param) {
        if (isBlank(param)) {
            return "";
        } else {
            int len = param.length();
            StringBuilder sb = new StringBuilder(len);

            for (int i = 0; i < len; ++i) {
                char c = param.charAt(i);
                if (Character.isUpperCase(c) && i > 0) {
                    sb.append('_');
                }

                sb.append(Character.toLowerCase(c));
            }

            return sb.toString();
        }
    }

    /**
     * 字符串下划线模式转驼峰模式
     */
    public static String underlineToCamel(String param) {
        if (isBlank(param)) {
            return "";
        } else {
            String temp = param.toLowerCase();
            int len = temp.length();
            StringBuilder sb = new StringBuilder(len);

            for (int i = 0; i < len; ++i) {
                char c = temp.charAt(i);
                if (c == '_') {
                    ++i;
                    if (i < len) {
                        sb.append(Character.toUpperCase(temp.charAt(i)));
                    }
                } else {
                    sb.append(c);
                }
            }

            return sb.toString();
        }
    }

    /**
     * 字符串首字母大写
     */
    public static String firstToUpperCase(String param) {
        return isBlank(param) ? "" : param.substring(0, 1).toUpperCase() + param.substring(1);
    }

    /**
     * 字符串首字母小写
     */
    public static String firstToLowerCase(String param) {
        return isBlank(param) ? "" : param.substring(0, 1).toLowerCase() + param.substring(1);
    }

    /**
     * 字符串是否匹配正则表达式
     */
    public static boolean matches(String regex, String input) {
        return null != regex && null != input && Pattern.matches(regex, input);
    }

    /**
     * 字符串是否包含大写字母
     */
    public static boolean containsUpperCase(String word) {
        for (int i = 0; i < word.length(); ++i) {
            char c = word.charAt(i);
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 随机打乱字符串顺序
     *
     * @param str 原字符串
     * @return 乱序字符串
     */
    public static String randomSequence(String str) {
        char[] arr = str.toCharArray();
        Random r = new Random();
        for (int i = 0; i < arr.length; i++) {
            int index = r.nextInt(arr.length); // 形成随机索引
            char temp = arr[i]; // 中间变量
            arr[i] = arr[index]; // 随机索引的数组赋值给当前循环的数组
            arr[index] = temp; // 值互换
        }
        return new String(arr); // 把数组转换成字符串
    }

    /**
     * 百分数转BigDecimal
     *
     * @param str 百分数
     * @return BigDecimal
     */
    public static BigDecimal percentToBigDecimal(String str) {
        BigDecimal result = new BigDecimal(0);
        if (isEmpty(str)) {
            return result;
        }
        try {
            NumberFormat numberFormat = NumberFormat.getPercentInstance();
            result = new BigDecimal(numberFormat.parse(str).toString());
            return result;
        } catch (ParseException e) {
            throw new AppRuntimeException("百分号格式错误", e);
        }
    }

    /**
     * 两个字符串是否一致
     */
    public static boolean equals(final CharSequence cs1, final CharSequence cs2) {
        if (cs1 == cs2) {
            return true;
        } else if (cs1 != null && cs2 != null) {
            if (cs1.length() != cs2.length()) {
                return false;
            } else if (cs1 instanceof String && cs2 instanceof String) {
                return cs1.equals(cs2);
            } else {
                int length = cs1.length();

                for (int i = 0; i < length; ++i) {
                    if (cs1.charAt(i) != cs2.charAt(i)) {
                        return false;
                    }
                }

                return true;
            }
        } else {
            return false;
        }
    }
}
