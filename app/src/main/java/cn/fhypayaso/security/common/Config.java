package cn.fhypayaso.security.common;

/**
 * @author FanHongyu.
 * @since 18/4/18 17:31.
 * email fanhongyu@hrsoft.net.
 */

public class Config {


    /**
     * 当前app版本号
     */
    public static final String APP_VERSION = "";

    /**
     * token持续时间（天）
     */
    public static final int TOKEN_VALID_TIME = 15;

    /**
     * 网络请求BaseURL
     */
    public static final String APP_SERVER_BASE_URL = "http://neuqsecurity.lyzwhh.top/";


    /**
     * 是否为测试版本
     */
    public static final boolean DEBUG = false;


    /**
     * 网络请求连接超时时间，单位：s
     */
    public static final int APP_SERVER_CONNECT_TIME_OUT = 15;


    /**
     * 正确返回码
     */
    public static final int[] NET_CORRECT_CODE = {0};

    /**
     * 扫描二维码跳转Activity RequestCode
     */
    public static final int REQUEST_CODE = 111;

}
