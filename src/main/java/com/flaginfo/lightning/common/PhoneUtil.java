package com.flaginfo.lightning.common;

import java.util.regex.Pattern;

/**
 * 手机号码工具类
 * @author zyong
 *
 */
public final class PhoneUtil {
    public static final String unicom_regex = "^(86|1)(3[0-2]|5[56]|8[56]|4[5]|7[1|5|6])\\d{8}$|^170[7|8|9]\\d{7}$|^10646\\d{8}$";
    public static final String mobile_regex = "^(86|1)(3[4-9]|5[0-2]|5[4]|5[7-9]|7[8]|8[2|3|4|7|8]|4[7])\\d{8}$|^170[3|5|6]\\d{7}$|^10648\\d{8}$";
    public static final String telecom_regex = "^(86|1)(3[3]|5[3]|7[3|7]|8[0]|8[1]|8[9])\\d{8}$|^170[0|1|2]\\d{7}$|^177\\d{8}$|^10649\\d{8}$";
    
    public static Pattern unicom = Pattern.compile(unicom_regex);
    public static Pattern mobile = Pattern.compile(mobile_regex);
    public static Pattern telecom = Pattern.compile(telecom_regex);

    /**
     * 判断手机号码运营商
     *      1:联通
     *      2:移动
     *      3:电信
     * @param mdn
     * @return
     */
    public static int getCarrier(String mdn) {
        if (unicom.matcher(mdn).matches()) {
            return 1;
        } else if (mobile.matcher(mdn).matches()) {
            return 2;
        } else if (telecom.matcher(mdn).matches()) {
            return 3;
        } else {
            return -1;
        }
    }
    
}
