package hr.duby.monitorapp.network;

import org.json.JSONArray;

/**
 * Created by Duby on 10.11.2017..
 */

public interface AsyncHttpListenerArray extends AsyncHttpListener {

    public void onGetArrayDone(JSONArray array);

}
