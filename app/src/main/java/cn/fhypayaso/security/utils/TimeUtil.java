package cn.fhypayaso.security.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.fhypayaso.security.common.CacheKey;
import cn.fhypayaso.security.common.Config;

public class TimeUtil {

    /**
     * 以模板类型返回当前时间点
     * @param formatStr 模板字符串类型
     * @return  返回当前的字符串
     */
    public static String getCurrentTime(String formatStr) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        String currentTime = format.format(date);
        return currentTime;
    }

    /**
     * 以默认类型返回当前时间点     "yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static String getCurrentTime() {
        return getCurrentTime("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 判断token是否过了有效期
     * @return
     */
    public static boolean isTokenValid() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date loginTime = null;
        String login = CacheUtil.getSP().getString(CacheKey.LOGIN_TIME, "");
        Date currentTime = null;
        try {
            loginTime = format.parse(login);
            currentTime = format.parse(getCurrentTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(loginTime);
        calendar.add(Calendar.DATE,Config.TOKEN_VALID_TIME);
        Date endTime = calendar.getTime();

        if(currentTime.before(endTime)) {
            return true;
        }else {
            return false;
        }
    }
}
