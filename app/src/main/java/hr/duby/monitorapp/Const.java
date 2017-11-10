package hr.duby.monitorapp;

/**
 * Created by Duby on 10.11.2017..
 */

public class Const {

    public static final String SERVICE_URL = "duby.ddns.net:2200";
    public static final String FCM_REGISTER_URL = SERVICE_URL + "/fcmregister";

    public static final String DATETIME_FORMAT  = "yyyy-MM-dd HH:mm:ss Z";
    public static long FCM_IGNORE_DELAY = 180;          //ignore push messages older than 3min

    //KEY - for Intents & Bundle ARGS
    //**********************************************************************************************
    public static final String KEY_URL_LINK    = "KEY_URL_LINK";
}
