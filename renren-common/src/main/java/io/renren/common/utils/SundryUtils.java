package io.renren.common.utils;

/**
 * 乱七八糟的工具类
 */
public class SundryUtils {


    /**
     * 获取银行卡手机号后四位
     * @param cardNum
     * @return
     */
    public static String getCardTailNum(String cardNum){
        StringBuffer tailNum = new StringBuffer();
        if (cardNum != null) {
            int len = cardNum.length();
            for (int i = len - 1; i >= len - 4; i--) {
                tailNum.append(cardNum.charAt(i));
            }
            tailNum.reverse();
        }
        return tailNum.toString();
    }
}
