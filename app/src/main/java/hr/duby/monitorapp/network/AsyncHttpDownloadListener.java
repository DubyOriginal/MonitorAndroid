package hr.duby.monitorapp.network;

import org.json.JSONObject;

/**
 * Created by Duby on 10.11.2017..
 */

public interface AsyncHttpDownloadListener {

    public void onDownloadDone(String filePath);

    public void onDownloadDone(JSONObject object);

    public void onDownloadProgress(int progress);

    public void onError(Exception e);
}
