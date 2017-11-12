package hr.duby.monitorapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import hr.duby.monitorapp.activities.MainActivity;
import hr.duby.monitorapp.network.AsyncHttpClient;
import hr.duby.monitorapp.network.AsyncHttpListener;

/**
 * Created by Duby on 10.11.2017..
 */

public class MonitorClient {

    private static MonitorClient _this;
    private static Object lock = new Object();


    public static MonitorClient getInstance() {
        synchronized (lock) {
            if (_this == null) {
                _this = new MonitorClient();
            }
        }
        return _this;
    }

    //**********************************************************************************************
    public void registerAppToken(String token) {
        DLog("registerAppToken: " + token);

        if (token != null) {
            JSONObject json = new JSONObject();
            try {
                json.put("id", Const.USER_ID);
                json.put("msisdn", Const.MSISDN);
                json.put("token", token);
                json.put("platform", Const.PLATFORM);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Log.d("DTag", "FirebaseTokenRegister: josn: " + json.toString());
            new AsyncHttpClient().post(Const.FCM_REGISTER_URL, json, new AsyncHttpListener() {
                @Override
                public void onGetDone(JSONObject object) {
                }

                @Override
                public void onPostDone(JSONObject object) {
                    if (object != null){
                        DLog("registerAppToken: onPostDone -> " + object);
                    } else{
                        DLog("registerAppToken: onPostDone -> NULL object");
                    }

                }

                @Override
                public void onError(Exception e) {
                    DLog("registerAppToken: onError: " + e.toString());
                }
            });
        }
    }


    private void ____________MESSAGES_HANDLING____________() {}
    //*****************************************************************************************************************************************
    //*****************************************************************************************************************************************

    //**********************************************************************************************
    private void showNotificationNewMsg(Context context, int unreadCnt, int convCnt) {
        int drawableID = R.drawable.new_chat_msg;
        //String title = context.getResources().getString(R.string.basic_general_error);
        //String message = context.getResources().getString(R.string.basic_general_error);

        Uri alertSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        String infoMsg = "CENTELNO GRIJANJE";

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSound(alertSound)
                .setSmallIcon(drawableID)
                .setAutoCancel(true)
                .setContentTitle("mTel Chat")
                .setContentText(infoMsg);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, MainActivity.class);

        // The stack builder object will contain an artificial back stack for thestarted Activity.
        // This ensures that navigating backward from the Activity leads out of your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Adds the back stack for the Intent (but not the Intent itself) stackBuilder.addParentStack((MContactsActivity) context);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(Const.NOTIFICATION_ID, mBuilder.build());
    }

    //**********************************************************************************************
    private void DLog(String msg) {
        String className = this.getClass().getSimpleName();
        Log.d("DTag", className + ": " + msg);    //className + " " + hashCode() + ":  " + msg
    }
}
