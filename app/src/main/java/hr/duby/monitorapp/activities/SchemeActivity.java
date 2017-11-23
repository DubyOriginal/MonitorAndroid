package hr.duby.monitorapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import hr.duby.monitorapp.MonitorClient;
import hr.duby.monitorapp.R;
import hr.duby.monitorapp.data.SensorBean;
import hr.duby.monitorapp.utils.Physics;

public class SchemeActivity extends AppCompatActivity {

    private TextView tvSensID_10, tvSensID_11, tvSensID_12, tvSensID_13, tvSensID_14, tvSensID_15, tvSensID_16, tvSensID_17, tvSensID_18, tvSensID_19, tvSensID_20;
    private TextView tvSensVal_10, tvSensVal_11, tvSensVal_12, tvSensVal_13, tvSensVal_14, tvSensVal_15, tvSensVal_16, tvSensVal_17, tvSensVal_18, tvSensVal_19, tvSensVal_20;
    private TextView tvPuffCapacityValue, tvPowerCKPValue, tvPowerRADValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheme);

        connectXML();

        MonitorClient.getInstance().request_GET_BASEMENT_LIST(new MonitorClient.OnGetBasementList() {
            @Override
            public void onGetBasementList(List<SensorBean> basementList) {
                prseData2View(basementList);
            };
        });
    }

    private  void connectXML(){
        tvPuffCapacityValue = (TextView) findViewById(R.id.tvPuffCapacityValue);
        tvPowerCKPValue = (TextView) findViewById(R.id.tvPowerCKPValue);
        tvPowerRADValue = (TextView) findViewById(R.id.tvPowerRADValue);

        tvSensID_10 = (TextView) findViewById(R.id.tvSensID_10);
        tvSensID_11 = (TextView) findViewById(R.id.tvSensID_11);
        tvSensID_12 = (TextView) findViewById(R.id.tvSensID_12);
        tvSensID_13 = (TextView) findViewById(R.id.tvSensID_13);
        tvSensID_14 = (TextView) findViewById(R.id.tvSensID_14);
        tvSensID_15 = (TextView) findViewById(R.id.tvSensID_15);
        tvSensID_16 = (TextView) findViewById(R.id.tvSensID_16);
        tvSensID_17 = (TextView) findViewById(R.id.tvSensID_17);
        tvSensID_18 = (TextView) findViewById(R.id.tvSensID_18);
        tvSensID_19 = (TextView) findViewById(R.id.tvSensID_19);
        tvSensID_20 = (TextView) findViewById(R.id.tvSensID_20);

        tvSensVal_10 = (TextView) findViewById(R.id.tvSensVal_10);
        tvSensVal_11 = (TextView) findViewById(R.id.tvSensVal_11);
        tvSensVal_12 = (TextView) findViewById(R.id.tvSensVal_12);
        tvSensVal_13 = (TextView) findViewById(R.id.tvSensVal_13);
        tvSensVal_14 = (TextView) findViewById(R.id.tvSensVal_14);
        tvSensVal_15 = (TextView) findViewById(R.id.tvSensVal_15);
        tvSensVal_16 = (TextView) findViewById(R.id.tvSensVal_16);
        tvSensVal_17 = (TextView) findViewById(R.id.tvSensVal_17);
        tvSensVal_18 = (TextView) findViewById(R.id.tvSensVal_18);
        tvSensVal_19 = (TextView) findViewById(R.id.tvSensVal_19);
        tvSensVal_20 = (TextView) findViewById(R.id.tvSensVal_20);
    }

    private void prseData2View(List<SensorBean> basementList){
        //SensorBean sensorBean = new SensorBean();
        SensorBean sensorBean_10 = SensorBean.getSensorForScreenID(10, basementList);
        tvSensVal_10.setText((sensorBean_10 != null && sensorBean_10.getSensorValue() != null) ? sensorBean_10.getSensorValue() : "-");
        tvSensID_10.setText((sensorBean_10 != null && sensorBean_10.getSensorID() != null) ? sensorBean_10.getSensorID(): "-");

        SensorBean sensorBean_11 = SensorBean.getSensorForScreenID(11, basementList);
        tvSensVal_11.setText((sensorBean_11 != null && sensorBean_11.getSensorValue() != null) ? sensorBean_11.getSensorValue() : "-");
        tvSensID_11.setText((sensorBean_11 != null && sensorBean_11.getSensorID() != null) ? sensorBean_11.getSensorID() : "-");

        SensorBean sensorBean_12 = SensorBean.getSensorForScreenID(12, basementList);
        tvSensVal_12.setText((sensorBean_12 != null && sensorBean_12.getSensorValue() != null) ? sensorBean_12.getSensorValue() : "-");
        tvSensID_12.setText((sensorBean_12 != null && sensorBean_12.getSensorID() != null) ? sensorBean_12.getSensorID() : "-");

        SensorBean sensorBean_13 = SensorBean.getSensorForScreenID(13, basementList);
        tvSensVal_13.setText((sensorBean_13 != null && sensorBean_13.getSensorValue() != null) ? sensorBean_13.getSensorValue() : "-");
        tvSensID_13.setText((sensorBean_13 != null && sensorBean_13.getSensorID() != null) ? sensorBean_13.getSensorID() : "-");

        SensorBean sensorBean_14 = SensorBean.getSensorForScreenID(14, basementList);
        tvSensVal_14.setText((sensorBean_14 != null && sensorBean_14.getSensorValue() != null) ? sensorBean_14.getSensorValue() : "-");
        tvSensID_14.setText((sensorBean_14 != null && sensorBean_14.getSensorID() != null) ? sensorBean_14.getSensorID() : "-");

        SensorBean sensorBean_15 = SensorBean.getSensorForScreenID(15, basementList);
        tvSensVal_15.setText((sensorBean_15 != null && sensorBean_15.getSensorValue() != null) ? sensorBean_15.getSensorValue() : "-");
        tvSensID_15.setText((sensorBean_15 != null && sensorBean_15.getSensorID() != null) ? sensorBean_15.getSensorID() : "-");

        SensorBean sensorBean_16 = SensorBean.getSensorForScreenID(16, basementList);
        tvSensVal_16.setText((sensorBean_16 != null && sensorBean_16.getSensorValue() != null) ? sensorBean_16.getSensorValue() : "-");
        tvSensID_16.setText((sensorBean_16 != null && sensorBean_16.getSensorID() != null) ? sensorBean_16.getSensorID() : "-");

        SensorBean sensorBean_17 = SensorBean.getSensorForScreenID(17, basementList);
        tvSensVal_17.setText((sensorBean_17 != null && sensorBean_17.getSensorValue() != null) ? sensorBean_17.getSensorValue() : "-");
        tvSensID_17.setText((sensorBean_17 != null && sensorBean_17.getSensorID() != null) ? sensorBean_17.getSensorID() : "-");

        SensorBean sensorBean_18 = SensorBean.getSensorForScreenID(18, basementList);
        tvSensVal_18.setText((sensorBean_18 != null && sensorBean_18.getSensorValue() != null) ? sensorBean_18.getSensorValue() : "-");
        tvSensID_18.setText((sensorBean_18 != null && sensorBean_18.getSensorID() != null) ? sensorBean_18.getSensorID() : "-");

        SensorBean sensorBean_19 = SensorBean.getSensorForScreenID(19, basementList);
        tvSensVal_19.setText((sensorBean_19 != null && sensorBean_19.getSensorValue() != null) ? sensorBean_19.getSensorValue() : "-");
        tvSensID_19.setText((sensorBean_19 != null && sensorBean_19.getSensorID() != null) ? sensorBean_19.getSensorID() : "-");

        SensorBean sensorBean_20 = SensorBean.getSensorForScreenID(20, basementList);
        tvSensVal_20.setText((sensorBean_20 != null && sensorBean_20.getSensorValue() != null) ? sensorBean_20.getSensorValue() : "-");
        tvSensID_20.setText((sensorBean_20 != null && sensorBean_20.getSensorID() != null) ? sensorBean_20.getSensorID() : "-");


        //------------------------------------------
        if (sensorBean_14 != null && sensorBean_15 != null && sensorBean_17 != null && sensorBean_18 != null){
            float tCold_ckp = Float.valueOf(sensorBean_15.getSensorValue());
            float tHot_ckp = Float.valueOf(sensorBean_14.getSensorValue());
            float tCold_rad = Float.valueOf(sensorBean_18.getSensorValue());
            float tHot_rad = Float.valueOf(sensorBean_17.getSensorValue());
            String powerCKPStr = String.valueOf(Physics.calculatePowerConsumation(Physics.FLOW_CKP, tCold_ckp, tHot_ckp));
            String powerRADStr = String.valueOf(Physics.calculatePowerConsumation(Physics.FLOW_RAD, tCold_rad, tHot_rad));
            tvPowerCKPValue.setText(powerCKPStr + "kW");
            tvPowerRADValue.setText(powerRADStr + "kW");
        }

        //------------------------------------------
        if (sensorBean_10 != null && sensorBean_11 != null && sensorBean_12 != null && sensorBean_13 != null) {
            float puff_10 = Float.valueOf(sensorBean_10.getSensorValue());
            float puff_11 = Float.valueOf(sensorBean_11.getSensorValue());
            float puff_12 = Float.valueOf(sensorBean_12.getSensorValue());
            float puff_13 = Float.valueOf(sensorBean_13.getSensorValue());
            float sum = puff_10 + puff_11 + puff_12 + puff_13;

            float averageTemp = sum / 4f;
            float rangeMin = 20f;  //from 20°C as 0%
            float rangeMax = 70f;  //from 70°C as 100%
            float percVal = 100f * (averageTemp - rangeMin) / (rangeMax - rangeMin);
            int percValInt = Math.round(percVal);

            tvPuffCapacityValue.setText("" + percValInt + "%");

        }
    }

    //**********************************************************************************************
    private void DLog(String msg) {
        String className = this.getClass().getSimpleName();
        Log.d("DTag", className + ": " + msg);
    }
}
