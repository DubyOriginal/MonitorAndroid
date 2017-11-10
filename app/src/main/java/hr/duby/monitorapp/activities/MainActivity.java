package hr.duby.monitorapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import hr.duby.monitorapp.Const;
import hr.duby.monitorapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DLog("onCreate");

        gotoWebBrowserActivity("http://" + Const.SERVICE_URL);
    }

    private void gotoWebBrowserActivity(String linkUrl) {
        DLog("gotoWebBrowserActivity");
        Intent intent = new Intent(getBaseContext(), WebBrowserActivity.class);
        intent.putExtra(Const.KEY_URL_LINK, linkUrl);
        startActivity(intent);
    }


    //**********************************************************************************************
    private void DLog(String msg) {
        String className = this.getClass().getSimpleName();
        Log.d("DTag", className + ": " + msg);
    }
}
