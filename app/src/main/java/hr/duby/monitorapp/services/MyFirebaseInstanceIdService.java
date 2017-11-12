package hr.duby.monitorapp.services;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import hr.duby.monitorapp.MonitorClient;

/**
 * Created by Duby on 9.11.2017..
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        //Log.d("DTag", "MyFirebaseInstanceIdService: Refreshed token: " + refreshedToken);
        MonitorClient.getInstance().registerAppToken(refreshedToken);
    }

}