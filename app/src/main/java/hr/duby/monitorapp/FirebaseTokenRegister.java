package hr.duby.monitorapp;

import org.json.JSONException;
import org.json.JSONObject;

import hr.duby.monitorapp.Const;
import hr.duby.monitorapp.MonitorClient;
import hr.duby.monitorapp.network.AsyncHttpClient;
import hr.duby.monitorapp.network.AsyncHttpListener;

/**
 * Created by Duby on 9.11.2017..
 */

public class FirebaseTokenRegister {

    public static void registerAppToken(String token) {
        if (token != null) {

            JSONObject json = new JSONObject();
            try {
                json.put("token", token);
                json.put("app", "monitor-app");

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
                }

                @Override
                public void onError(Exception e) {

                }
            });
        }
    }

}
