package hr.duby.monitorapp.network;

import org.json.JSONObject;

/**
 * Created by Duby on 10.11.2017..
 */
public interface AsyncHttpListener {

    public void onGetDone(JSONObject object);

    public void onPostDone(JSONObject object);

    public void onError(Exception e);
}
