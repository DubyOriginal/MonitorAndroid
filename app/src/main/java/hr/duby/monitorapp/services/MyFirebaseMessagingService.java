package hr.duby.monitorapp.services;

import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import hr.duby.monitorapp.Const;
import hr.duby.monitorapp.R;

/**
 * Created by Duby on 9.11.2017..
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        DLog("!!! PUSH MESSAGE RECEIVED !!!!");
        DLog("remoteMessage.getData() -> " + remoteMessage.getData());

        String msgTitle = null;
        String msgBody = null;
        JSONObject msgData = null;
        try {
            msgTitle = remoteMessage.getNotification().getTitle();
            msgBody = remoteMessage.getNotification().getBody();
            msgData = new JSONObject(remoteMessage.getData());  //data: {"action":"CRITICAL_TEMP"}

        } catch (Exception e) {
            e.printStackTrace();
            DLog("onMessageReceived: General Exception");
        }

        DLog("PUSH MSG CONTENT: \n\tmsgTitle: " + msgTitle + "\n\tmsgBody: " + msgBody + "\n\tmsgData: " + msgData);

        buildPushNotification(getBaseContext(), R.drawable.new_chat_msg, msgTitle, msgBody, false);

        handlePushNotification(msgTitle, msgBody, msgData);
        //String encoded = remoteMessage.getData().get("key_2");
        //String decoded = new String(Base64.decode(encoded, Base64.DEFAULT));

    }

    //**********************************************************************************************
    private void handlePushNotification(String title, String body, JSONObject msgData) {
        String action = null;
        try {
            action = msgData.getString("action");
        } catch (JSONException e) {
            DLog("handlePushNotification: parse json object exception");
        }
        if (action != null){
            if (action.contentEquals(Const.ACTION_CRITICAL_TEMP)){
                DLog("ACTION_CRITICAL_TEMP");
                //on notification click go to "CRITICAL TEMP VIEW" request REST and show critical sensors! todo
            }
        }

    }

    public void buildPushNotification(Context context, int icon, String msgTitle, CharSequence msgBody, boolean silent) {

        //Define Notification Manager
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //Define sound URI
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(icon)
                .setContentTitle(msgTitle)
                .setContentText(msgBody)
                .setSound(soundUri); //This sets the sound to play

        //Display notification
        notificationManager.notify(0, mBuilder.build());

        /*
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        /*
        Intent intent = new Intent(ctx, Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 1410, intent, PendingIntent.FLAG_ONE_SHOT);

        Bitmap bm = BitmapFactory.decodeResource(ctx.getResources(), icon); //large drawable);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(ctx)
                .setSmallIcon(icon)
                .setLargeIcon(bm)
                .setContentTitle(msgTitle)
                .setContentText(msgBody)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        if (!silent) {
            notificationBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        }
        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1410, notificationBuilder.build());
        */
    }


    //**********************************************************************************************
    private void DLog(String msg) {
        String className = this.getClass().getSimpleName();
        Log.d("DTag", className + ": " + msg);
    }

}
