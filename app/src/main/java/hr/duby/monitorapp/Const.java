package hr.duby.monitorapp;

/**
 * Created by Duby on 10.11.2017..
 */

public class Const {

    //public static final String SERVICE_URL = "http://duby.ddns.net:2200";
    public static final String SERVICE_URL = "http://192.168.1.30:2200";            //debug mode -> local-servis
    public static final String FCM_REGISTER_URL = SERVICE_URL + "/updatepushtoken";

    //settings
    public static final String DATETIME_FORMAT  = "yyyy-MM-dd HH:mm:ss Z";
    public static final int NOTIFICATION_ID = 4455;
    public static long FCM_IGNORE_DELAY = 180;          //ignore push messages older than 3min
    public static final String USER_ID = "DY001";
    public static final String MSISDN = "0989088413";
    public static final String PLATFORM = "android";

    //KEY - for Intents & Bundle ARGS
    //**********************************************************************************************
    public static final String KEY_URL_LINK    = "KEY_URL_LINK";


}
