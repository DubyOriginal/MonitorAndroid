package hr.duby.monitorapp.services;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import hr.duby.monitorapp.Const;
import hr.duby.monitorapp.utils.DateTimeParsing;

/**
 * Created by Duby on 9.11.2017..
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Check if message contains a data payload.
        //if (remoteMessage.getData().size() > 0) {
        //    DLog("Message data payload: " + remoteMessage.getData());
        //}

        DLog("!!! PUSH MESSAGE RECEIVED !!!! -> " + remoteMessage.getData());

        String origin = remoteMessage.getData().get("origin"); // mtelvoice.436677... ili mtelchat.4366777
        String encoded = remoteMessage.getData().get("message");
        String decoded = new String(Base64.decode(encoded, Base64.DEFAULT));

        DLog("onMessageReceived -> From: " + remoteMessage.getFrom() + ", origin: " + origin + ", DECODED message: " + decoded);

        if (origin.contains("")) {
            DLog("PUSH NOTIFICATION -> VOICE");
            handlePushNotification_VOIP(remoteMessage, decoded);

        }

    }

    //**********************************************************************************************
    private void handlePushNotification_VOIP(RemoteMessage remoteMessage, String decoded) {
        String timestamp = remoteMessage.getData().get("timestamp");
        if (timestamp != null && timestamp.length() > 0) {
            long diffInSec = new DateTimeParsing().getTimeDiffInSeconds(timestamp);
            if (diffInSec > Const.FCM_IGNORE_DELAY) {
                //insertMissedCall to DB
                JSONObject json = null;
                try {
                    json = new JSONObject(decoded);
                } catch (JSONException e) {
                    e.printStackTrace();
                    DLog("ERR-6732");
                }

            } else {
                DLog("PUSH NOTIFICATION -> INVALID_TIMESTAMP: " + timestamp);
            }

        }
    }


    //**********************************************************************************************
    private void DLog(String msg) {
        String className = this.getClass().getSimpleName();
        Log.d("DTag", className + ": " + msg);
    }

}
