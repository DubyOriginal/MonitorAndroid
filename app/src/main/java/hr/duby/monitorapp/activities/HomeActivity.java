package hr.duby.monitorapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import hr.duby.monitorapp.Const;
import hr.duby.monitorapp.MonitorClient;
import hr.duby.monitorapp.R;
import hr.duby.monitorapp.data.SensorBean;
import hr.duby.monitorapp.utils.Physics;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView tvValSOBA, tvValCKP_CORE, tvValCapacity, tvValPOW_RAD, tvValRAD_POL, tvValDATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        connectXML();

        MonitorClient.getInstance().request_GET_BASEMENT_LIST(new MonitorClient.OnGetBasementList() {
            @Override
            public void onGetBasementList(List<SensorBean> basementList) {
                prseData2View(basementList);
            };
        });

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            //selClass = HomeActivity.class;
        } else if (id == R.id.nav_scheme) {
            gotoSchemeActivity();
        } else if (id == R.id.nav_events) {
            gotoEventsActivity();
        } else if (id == R.id.nav_tools) {
            gotoToolsActivity();
        } else if (id == R.id.nav_browser) {
            gotoWebBrowserActivity(Const.SERVICE_URL);
        } else if (id == R.id.nav_about) {
            gotoAboutActivity();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void gotoSchemeActivity() {
        DLog("gotoSchemeActivity");
        Intent intent = new Intent(getBaseContext(), SchemeActivity.class);
        startActivity(intent);
    }

    private void gotoEventsActivity() {
        DLog("gotoEventsActivity");
        Intent intent = new Intent(getBaseContext(), EventsActivity.class);
        startActivity(intent);
    }

    private void gotoToolsActivity() {
        DLog("gotoToolsActivity");
        Intent intent = new Intent(getBaseContext(), ToolsActivity.class);
        startActivity(intent);
    }

    private void gotoWebBrowserActivity(String linkUrl) {
        DLog("gotoWebBrowserActivity");
        Intent intent = new Intent(getBaseContext(), WebBrowserActivity.class);
        intent.putExtra(Const.KEY_URL_LINK, linkUrl);
        startActivity(intent);
    }

    private void gotoAboutActivity() {
        DLog("gotoAboutActivity");
        Intent intent = new Intent(getBaseContext(), AboutActivity.class);
        startActivity(intent);
    }


    private void connectXML(){
        tvValDATE = (TextView) findViewById(R.id.tvValDATE);
        tvValSOBA = (TextView) findViewById(R.id.tvValSOBA);
        tvValCKP_CORE = (TextView) findViewById(R.id.tvValCKP_CORE);
        tvValCapacity = (TextView) findViewById(R.id.tvValCapacity);
        tvValRAD_POL = (TextView) findViewById(R.id.tvValRAD_POL);
        tvValPOW_RAD = (TextView) findViewById(R.id.tvValPOW_RAD);

    }

    private void prseData2View(List<SensorBean> basementList){

        //SENSOR       CKP_CORE   CKP_POL   CKP_POV   PUFF_1   PUFF_2   PUFF_3   PUFF_4   RAD_POL   RAD_POV   SOBA   VANI   POW_CKP   POW_RAD   PUFF_AVERAGE
        //SENSOR_ID    104        105       107       108      106      102      100      101       103       109    110    -         -         -
        //SCREEN_ID    16         14        15        10       11       12       13       17        18        19     20     -

        SensorBean sBean_CKP_CORE = SensorBean.getSensorForScreenID(16, basementList);
        SensorBean sBean_CKP_POL  = SensorBean.getSensorForScreenID(14, basementList);
        SensorBean sBean_CKP_POV  = SensorBean.getSensorForScreenID(15, basementList);
        SensorBean sBean_PUFF_1   = SensorBean.getSensorForScreenID(10, basementList);
        SensorBean sBean_PUFF_2   = SensorBean.getSensorForScreenID(11, basementList);
        SensorBean sBean_PUFF_3   = SensorBean.getSensorForScreenID(12, basementList);
        SensorBean sBean_PUFF_4   = SensorBean.getSensorForScreenID(13, basementList);
        SensorBean sBean_RAD_POL  = SensorBean.getSensorForScreenID(17, basementList);
        SensorBean sBean_RAD_POV  = SensorBean.getSensorForScreenID(18, basementList);
        SensorBean sBean_SOBA     = SensorBean.getSensorForScreenID(19, basementList);
        SensorBean sBean_VANI     = SensorBean.getSensorForScreenID(20, basementList);

        tvValDATE.setText((sBean_SOBA != null && sBean_SOBA.getSensorReadTS() != null) ? sBean_SOBA.getSensorReadTS() : "-");
        tvValCKP_CORE.setText((sBean_CKP_CORE != null && sBean_CKP_CORE.getSensorValue() != null) ? sBean_CKP_CORE.getSensorValue() : "-");
        tvValSOBA.setText((sBean_SOBA != null && sBean_SOBA.getSensorValue() != null) ? sBean_SOBA.getSensorValue() : "-");
        tvValRAD_POL.setText((sBean_RAD_POL != null && sBean_RAD_POL.getSensorValue() != null) ? sBean_RAD_POL.getSensorValue() : "-");


        //------------------------------------------

        if (sBean_CKP_POL != null && sBean_CKP_POV != null && sBean_RAD_POL != null && sBean_RAD_POV != null){
            float tCKP_POV = Float.valueOf(sBean_CKP_POV.getSensorValue());
            float tCKP_POL = Float.valueOf(sBean_CKP_POL.getSensorValue());
            float tRAD_POV = Float.valueOf(sBean_RAD_POV.getSensorValue());
            float tRAD_POL = Float.valueOf(sBean_RAD_POL.getSensorValue());
            String powerCKPStr = String.valueOf(Physics.calculatePowerConsumation(Physics.FLOW_CKP, tCKP_POV, tCKP_POL));
            String powerRADStr = String.valueOf(Physics.calculatePowerConsumation(Physics.FLOW_RAD, tRAD_POV, tRAD_POL));
            //tvPowerCKPValue.setText(powerCKPStr + "kW");
            tvValPOW_RAD.setText(powerRADStr + "kW");
        }

        //------------------------------------------
        if (sBean_PUFF_1 != null && sBean_PUFF_2 != null && sBean_PUFF_3 != null && sBean_PUFF_4 != null) {
            float tPUFF_1 = Float.valueOf(sBean_PUFF_1.getSensorValue());
            float tPUFF_2 = Float.valueOf(sBean_PUFF_2.getSensorValue());
            float tPUFF_3 = Float.valueOf(sBean_PUFF_3.getSensorValue());
            float tPUFF_4 = Float.valueOf(sBean_PUFF_4.getSensorValue());
            float sum = tPUFF_1 + tPUFF_2 + tPUFF_3 + tPUFF_4;

            float averageTemp = sum / 4f;
            float rangeMin = 20f;  //from 20°C as 0%
            float rangeMax = 70f;  //from 70°C as 100%
            float percVal = 100f * (averageTemp - rangeMin) / (rangeMax - rangeMin);
            int percValInt = Math.round(percVal);

            tvValCapacity.setText("" + percValInt + "%");

        }

    }



    //**********************************************************************************************
    private void DLog(String msg) {
        String className = this.getClass().getSimpleName();
        Log.d("DTag", className + ": " + msg);
    }
}
