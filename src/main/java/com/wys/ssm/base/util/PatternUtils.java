package com.wys.ssm.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class Name : PatternUtils<br>
 * 
 * Description : 正则表达式util<br>
 * 
 * @author yangwb
 * @version $Revision$
 * @see
 *
 */
public class PatternUtils {
    // 只能为数字汉字字母
    public static final String textReg = "^[a-zA-Z0-9\u4E00-\u9FA5]+$";
    // 数字字母下划线
    public static final String textReg1 = "^[a-zA-Z0-9_]+$";
    //数字正则
    public static final String isNum = "^[0-9]*$";

    // // 大写英文
    // public static final String upperChar = "^[A-Z]+$";
    // // 小写英文
    // public static final String lowerChar = "^[a-z]+$";
   
    // public static final String isNum = "^[0-9]*$";
    // // 特殊字符
    // public static final String isSerialChar =
    // "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

    // 密码强度验证 正则
    public static final String passwordReg = "^(?:(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])|(?=.*[A-Z])(?=.*[a-z])(?=.*[^A-Za-z0-9])|(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9])|(?=.*[a-z])(?=.*[0-9])(?=.*[^A-Za-z0-9])).{6,}";

    // 密码强度验证 正则 8-16位
    public static final String passwordRegAgent = "^(?:(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])|(?=.*[A-Z])(?=.*[a-z])(?=.*[^A-Za-z0-9])|(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9])|(?=.*[a-z])(?=.*[0-9])(?=.*[^A-Za-z0-9])).{8,16}";

    /**
     * @param regex 正则表达式字符串
     * @param str 要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    public static boolean matchText(String str) {
        Pattern pattern = Pattern.compile(PatternUtils.textReg);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * Description ：密码强度验证<br>
     * 
     * yangwb
     * 
     * @param str
     * @return
     * @since
     *
     */
    public static boolean matchPassword(String str) {
        Pattern pattern = Pattern.compile(PatternUtils.passwordReg);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * Description ：密码强度验证 8-16位 包含三种强度<br>
     * 
     * yangwb
     * 
     * @param str
     * @return
     * @since
     *
     */
    public static boolean matchPasswordAgent(String str) {
        Pattern pattern = Pattern.compile(PatternUtils.passwordRegAgent);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * Description ：<br>
     * 
     * yangwb
     * 
     * @param str
     * @return
     * @since
     *
     */
    public static boolean matchText1(String str) {
        Pattern pattern = Pattern.compile(PatternUtils.textReg1);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
	/**
	 * 判断字符串是否全是数字
	* @Title: matchTextIsNum
	* @return boolean
	* @throws 
	* @author zhangtao
	 */
    public static boolean matchTextIsNum(String str) {
        Pattern pattern = Pattern.compile(PatternUtils.isNum);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
    
    public static void main(String[] args) {
        // int num = 0;
        // num = Pattern.compile("[0-9]").matcher("Ao-2$oo").find() ? num + 1 :
        // num;
        // num = Pattern.compile("[a-z]").matcher("Ao212$oo").find() ? num + 1 :
        // num;
        // num = Pattern.compile("[A-Z]").matcher("Ao212$oo").find() ? num + 1 :
        // num;
        // num =
        // Pattern.compile("[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]").matcher("Ao212$o+=o").find()
        // ? num + 1 : num;
        // //
        // System.out.println(Pattern.compile(upperChar).matcher("SSSss").find());
        // System.out.println(num);

//        String[] array = { "A2wee", "###123", "!!isok!!23424545", "ijk#A", "QWERtyuiop" };
//        for (int i = 0; i < array.length; i++) {
//            System.out.print(PatternUtils.matchPasswordAgent(array[i]) ? "YES" : "No");
//            System.out.println(":" + array[i]);
//        }
        // String str = "sdfsd123_!87";
        // System.out.println(PatternUtils.matchText1(str));
    	
    	System.out.println(PatternUtils.matchTextIsNum("12321"));
    }
}
