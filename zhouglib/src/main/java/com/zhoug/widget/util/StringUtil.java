package com.zhoug.widget.util;

import java.util.regex.Pattern;

/**
 * 字符串工具包
 */
public class StringUtil {
    /**
     * 全角空格，长度等于一个中文字符
     */
    public static final String BLANK_SPACE_2="&#8195;";
    /**
     * 全角空格，长度等于半个中文字符
     */
    public static final String BLANK_SPACE_1="&#8194;";
    /**
     * 不会被合并的空格，长度与常规空格相同
     */
    public static final String BLANK_SPACE="&#160;";
    /**email的正则表达式*/
    private final static Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");


    /**
     * 断给定字符串是否空白串
     * null、"null"、" "
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        if(str==null || "".equals(str.trim())|| "null".equals(str))
            return true;
        return false;
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {

        return !isEmpty(email) && emailer.matcher(email).matches();
    }





}
