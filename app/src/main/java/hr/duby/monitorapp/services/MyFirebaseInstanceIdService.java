package hr.duby.monitorapp.services;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import hr.duby.monitorapp.FirebaseTokenRegister;

/**
 * Created by Duby on 9.11.2017..
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Log.d("DTag", "MyFirebaseInstanceIdService: Refreshed token: " + refreshedToken);
        FirebaseTokenRegister.registerAppToken(refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(refreshedToken);
    }


}