package com.huweiv.util;

import java.util.regex.Pattern;

/**
 * @author HUWEIV
 * @version 1.0.0
 * @ClassName StringUtil
 * @Description TODO
 * @CreateTime 2022/4/23 10:21
 */
public class StringUtil {

    private static Pattern phoneNumPattern = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
    private static Pattern emailPattern = Pattern.compile("[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+");

    public static boolean isPhoneNum(String str) {
        return phoneNumPattern.matcher(str).matches();
    }

    public static boolean isEmail(String str) {
        return emailPattern.matcher(str).matches();
    }
}
