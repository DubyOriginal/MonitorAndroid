package hr.duby.monitorapp.activities;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hr.duby.monitorapp.R;
import hr.duby.monitorapp.data.SensorBean;

public class StatusActivity extends AppCompatActivity {

    private ListView lv_list;
    private List<SensorBean> mSensorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        lv_list = (ListView) findViewById(R.id.lvSensorStatus);

        mSensorList = new ArrayList<>();
        mSensorList.add(new SensorBean("CKP_CORE", "56.55", "10", "90"));
        mSensorList.add(new SensorBean("CKP_POL", "50.25", "10", "85"));
        mSensorList.add(new SensorBean("RAD_POL", "46.22", "10", "60"));

        MyListAdapter adapter = new MyListAdapter(this, R.layout.row_sensor_status, mSensorList);
        lv_list.setAdapter(adapter);
    }


public class MyListAdapter extends BaseAdapter {
    Context context;
    int layoutResourceId;
    List<SensorBean> rowItems = null;

    public MyListAdapter(Context context, int layoutResourceId, List<SensorBean> items) {
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.rowItems = items;
    }

    @Override
    //******************************************************************************************
    public int getCount() { return this.rowItems != null ? this.rowItems.size() : 0; }

    @Override
    public Object getItem(int position) {return (this.rowItems != null && (position >= 0) && (position < this.rowItems.size())) ? this.rowItems.get(position) : null;}

    @Override
    public long getItemId(int position) {return 0; }

    /* private view holder class */
    private class RowViewHolder {
        ProgressBar pbSensorStatus;
        TextView tvSensorName;
        TextView tvSensorValue;
        TextView tvAlarmMin;
        TextView tvAlarmMax;
    }

    @Override
    //******************************************************************************************
    public View getView(int position, View convertView, ViewGroup parent) {
        RowViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(this.layoutResourceId, null);
            holder = new RowViewHolder();
            holder.pbSensorStatus = (ProgressBar) convertView.findViewById(R.id.pbSensorStatus);
            holder.tvSensorName = (TextView) convertView.findViewById(R.id.tvSensorName);
            holder.tvSensorValue = (TextView) convertView.findViewById(R.id.tvSensorValue);
            holder.tvAlarmMin = (TextView) convertView.findViewById(R.id.tvAlarmMin);
            holder.tvAlarmMax = (TextView) convertView.findViewById(R.id.tvAlarmMax);
            convertView.setTag(holder);
        } else {
            holder = (RowViewHolder) convertView.getTag();
        }

        SensorBean rowItem = (SensorBean) getItem(position);
        int sensorValueInt = Math.round(Float.valueOf(rowItem.getSensorValue()));
        holder.pbSensorStatus.setProgress(sensorValueInt);
        holder.tvSensorName.setText(rowItem.getSensorName());
        holder.tvSensorValue.setText(rowItem.getSensorValue() + "°C");
        holder.tvAlarmMin.setText(rowItem.getAlarmMin() + "°C");
        holder.tvAlarmMax.setText(rowItem.getAlarmMax() + "°C");

        return convertView;
    }
}
}
