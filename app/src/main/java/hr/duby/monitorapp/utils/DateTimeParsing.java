package hr.duby.monitorapp.utils;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import hr.duby.monitorapp.Const;

/**
 * Created by dvrbancic on 04/10/16.
 */
public class DateTimeParsing {

    private Context mContext;

    public DateTimeParsing(){}

    public DateTimeParsing(Context context){
        mContext = context;
    }

    //**********************************************************************************************
    public long getTimeDiffInSeconds(String inputDTStr){

        long diffInSec;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(Const.DATETIME_FORMAT);
            Date inputDT = sdf.parse(inputDTStr);
            Date dateNow = new Date();

            long diff = dateNow.getTime() - inputDT.getTime();
            diffInSec = (long)(diff / (1000l));

        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
        return diffInSec;
    }

    //**********************************************************************************************
    // '-' -> future
    // '+' -> past
    public static long getTimeDiffInSeconds(long inputTSInSec){
        long diffInSec;
        try {
            Date dateNow = new Date();
            long nowTSSec = dateNow.getTime() / 1000l;   //in sec
            diffInSec = nowTSSec - inputTSInSec;
            //Log.d("DTag", "---> nowTSSec: " + nowTSSec + ", inputTSInSec: " + inputTSInSec + ", diffInSec: " + diffInSec);

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return diffInSec;
    }


    //**********************************************************************************************
    //test if send_on date is too fare in future -> if so than return current timestamp instate
    // (better than nothing)
    public static long validateSentOnDateValue(long sent_on){
        long sentOn = sent_on;
        if (sent_on > 0){  //not initial set
            long validValue = 60 * 10;  //10min
            long timeDiff = getTimeDiffInSeconds(sent_on);
            if (timeDiff < 0){  //if time is in future
                if (Math.abs(timeDiff) > validValue) { //if too far in future
                    long dateNow = new Date().getTime();
                    sentOn = dateNow / 1000l;  //in sec
                    Log.d("DTag", "DateTimeParsing: validateSentOnDateValue -> UNEXPECTED invalid date! ********************");
                }
            }
        }

        return sentOn;
    }

    //**********************************************************************************************
    public String getFormatedCurentDateTime(){
        Date currentTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return sdf.format(currentTime);
    }

    //**********************************************************************************************
    // long 1477293279000l (milli sec) -> "24.10.2016 09:14:39"
    // long unixDate = System.currentTimeMillis();
    public static String getDateFromUnixTime(long unixTime){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return sdf.format(unixTime);
    }

    //****************************************************************************************************************************
    // "23.05.2014 10:54:45"
    //           today: 10:54
    //    before today: 23/05/2014
    public String getPrettyDateTime2From(String inputDTStr){
        if (!(inputDTStr != null && inputDTStr.length() > 0)) {return null;}
        String result = null;
        Date inputDateTime = null;
        //Date now_DateTime = new Date();

        final SimpleDateFormat sdf_full = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault());
        final SimpleDateFormat sdf_date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        final SimpleDateFormat sdf_time = new SimpleDateFormat("HH:mm", Locale.getDefault());

        //1. Convert Input String to Date
        //*****************************************
        try {
            inputDateTime = sdf_full.parse(inputDTStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

        if (inputDateTime == null){return null;}

        boolean isToday = DateUtils.isToday(inputDateTime.getTime());
        if (isToday){
            result = sdf_time.format(inputDateTime);
        }else{
            result = sdf_date.format(inputDateTime);
        }

        return result;

    }

    // GET Pretty Formated DateTime - Relative to Now
    // "08.05.2014 10:54:45"  - returns -> "Danas, 10:54"...or ... "prije 15 min" ... or ...
    //****************************************************************************************************************************
    //****************************************************************************************************************************
    //****************************************************************************************************************************
    /*
    public String getPrettyDateTimeFrom(String date_str){
        if (!(date_str != null && date_str.length() > 0)) {return null;}

        String result = "";
        String res_holder;

        // SET Date Format
        //*****************************************
        final SimpleDateFormat sdf_full     = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault());
        final SimpleDateFormat sdf_onlyDate = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        final SimpleDateFormat sdf_onlyTime = new SimpleDateFormat("HH:mm", Locale.getDefault());

        Date temp_DateTime = null;
        Date now_DateTime = new Date();

        //1. Convert Input String to Date
        //*****************************************
        try {
            temp_DateTime = sdf_full.parse(date_str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        //Log.d(DTAG, "temp_DateTime : " + temp_DateTime);


        //2. GET Date Difference in sec
        //*****************************************
        //long timeDiff = ChatClient.getInstance().getTIME_DIFFERENCE();
        long timeDiff = 0l;
        long now_datetime_msec  = now_DateTime.getTime() - timeDiff;  //compensate timeDiff
        long temp_datetime_msec = temp_DateTime.getTime();
        int date_diference_sec = (int)(now_datetime_msec - temp_datetime_msec) / 1000;
        //Log.d(DTAG, "date_diference_sec: " + String.valueOf(date_diference_sec));


        //3. INIT DateTime
        //*****************************************
        Calendar temp_DateTime_cal = Calendar.getInstance();   // init
        temp_DateTime_cal.setTimeInMillis(temp_datetime_msec); // set to temp_datetime

        Calendar now_DateTime_cal = Calendar.getInstance();    // init with current DateTime
        //now_DateTime_cal.add(Calendar.SECOND, date_diference_sec);  //compensate timeDiff


        //4. Adjust Date format: Yesterday, Now,.....
        //*******************************************
        //IF TODAY
        //********************
        if(now_DateTime_cal.get(Calendar.DATE) == temp_DateTime_cal.get(Calendar.DATE) ){

            if (date_diference_sec < 0){									    // negative -> Future Time
                result = sdf_onlyTime.format(temp_DateTime);

            }else if (date_diference_sec <= 60 )  {                             // < min  -> upravo
                res_holder = mContext.getResources().getString(R.string.now);
                result =  res_holder;
                //result = "upravo";

            }else if (date_diference_sec > 60 && date_diference_sec < 120) {	    // 1 min	 -> pred minutu
                res_holder = mContext.getResources().getString(R.string.before_a_minute);
                result =  res_holder;
                //result = "pred minutu";


            }else if (date_diference_sec >= 120 && date_diference_sec < 3540){    // 2..59  -> 2..59 min
                res_holder = mContext.getResources().getString(R.string.before);
                long min = date_diference_sec / 60;
                if (min < 10)
                    result =  res_holder + "   " + min + " min";
                else
                    result =  res_holder + " " + min + " min";
                //result = "prije "  + date_diference_sec / 60 + " min";

            }else {																  // >60    -> Danas, 14:15
                res_holder = mContext.getResources().getString(R.string.today);
                result = res_holder + ", " + sdf_onlyTime.format(temp_DateTime);
                //result = "danas, " + sdf_onlyTime.format(temp_DateTime);

            }

            // IF YESTERDAY
            //********************
        }else if(now_DateTime_cal.get(Calendar.DATE) - temp_DateTime_cal.get(Calendar.DATE) == 1 ){
            res_holder = mContext.getResources().getString(R.string.yesterday);
            result = res_holder + ", " + sdf_onlyTime.format(temp_DateTime);
            //result = "jučer, " + sdf_onlyTime.format(temp_DateTime);

            // (BEFORE YESTERDAY) or (FUTURE DATE)
            //*************************************
        }else
            result = sdf_onlyDate.format(temp_DateTime);

        return result;
    }*/

    //****************************************************************************************************************************
    public String getPrettyDateTimeFrom(long unixDate_sec){
        long unix_ms = unixDate_sec * 1000l;
        String dateStr = getDateFromUnixTime(unix_ms);
        return getPrettyDateTime2From(dateStr);

    }

/*

    //****************************************************************************************************************************
    //****************************************************************************************************************************
    //****************************************************************************************************************************
    public static void test(Activity activity){
        Log.d(DTAG, "*%*%*%*%*%*%*%*%*%*%*%*%*%*%*%*%*%*%*%*%*%*%*%*%*%*%*%*%*%*%*%*%*");

        PrettyDateTime_dy prettyDateTime = new PrettyDateTime_dy(activity);

        //SIMULATION TEST                                // NOW = 14:00
        //****************************************************************
        String temp_DateTime_0 = "02.11.2000 20:40:44";
        String temp_DateTime_1 = "02.11.2014 20:40:44";
        String temp_DateTime_2 = "03.11.2014 11:10:44";  //
        String temp_DateTime_3 = "04.11.2014 14:30:44";  // jučer 14:30
        String temp_DateTime_4 = "05.11.2014 08:10:44";  // danas 08:10
        String temp_DateTime_5 = "05.11.2014 14:46:44";  // danas 14:47
        String temp_DateTime_6 = "05.11.2014 14:48:44";  // upravo
        String temp_DateTime_7 = "06.11.2014 08:50:44";  // Future DateTime

        String temp_str = prettyDateTime.getPrettyDateTimeFrom(temp_DateTime_0);
        Log.d(DTAG, "temp_DateTime_0: " +  temp_str + " \t" + temp_DateTime_0);

        temp_str = prettyDateTime.getPrettyDateTimeFrom(temp_DateTime_1);
        Log.d(DTAG, "temp_DateTime_1: " +  temp_str + " \t" + temp_DateTime_1);

        temp_str = prettyDateTime.getPrettyDateTimeFrom(temp_DateTime_2);
        Log.d(DTAG, "temp_DateTime_2: " +  temp_str + " \t" + temp_DateTime_2);

        temp_str = prettyDateTime.getPrettyDateTimeFrom(temp_DateTime_3);
        Log.d(DTAG, "temp_DateTime_3: " +  temp_str + " \t" + temp_DateTime_3);

        temp_str = prettyDateTime.getPrettyDateTimeFrom(temp_DateTime_4);
        Log.d(DTAG, "temp_DateTime_4: " +  temp_str + " \t" + temp_DateTime_4);

        temp_str = prettyDateTime.getPrettyDateTimeFrom(temp_DateTime_5);
        Log.d(DTAG, "temp_DateTime_5: " +  temp_str + " \t" + temp_DateTime_5);

        temp_str = prettyDateTime.getPrettyDateTimeFrom(temp_DateTime_6);
        Log.d(DTAG, "temp_DateTime_6: " +  temp_str + " \t" + temp_DateTime_6);

        temp_str = prettyDateTime.getPrettyDateTimeFrom(temp_DateTime_7);
        Log.d(DTAG, "temp_DateTime_7: " +  temp_str + " \t" + temp_DateTime_7);

    }

    */
}

