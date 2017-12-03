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

import hr.duby.monitorapp.Const;
import hr.duby.monitorapp.R;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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


    //**********************************************************************************************
    private void DLog(String msg) {
        String className = this.getClass().getSimpleName();
        Log.d("DTag", className + ": " + msg);
    }
}
