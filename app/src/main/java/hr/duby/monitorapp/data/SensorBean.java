package hr.duby.monitorapp.data;

import java.util.List;

/**
 * Created by dvrbancic on 23/11/2017.
 */

public class SensorBean {

    private String sensorID;
    private String sensorReadTS;
    private int screenID;
    private String sensorType;
    private String sensorMID;
    private String sensorName;
    private String sensorValue;

    //constructor
    //**********************************************************************************************
    public SensorBean() {
    }

    public SensorBean(String sensorID, String sensorReadTS, int screenID, String sensorType, String sensorMID, String sensorName, String sensorValue) {
        this.sensorID = sensorID;
        this.sensorReadTS = sensorReadTS;
        this.screenID = screenID;
        this.sensorType = sensorType;
        this.sensorMID = sensorMID;
        this.sensorName = sensorName;
        this.sensorValue = sensorValue;
    }

    //getter && setters
    //**********************************************************************************************
    public String getSensorID() {
        return sensorID;
    }

    public void setSensorID(String sensorID) {
        this.sensorID = sensorID;
    }

    public String getSensorReadTS() {
        return sensorReadTS;
    }

    public void setSensorReadTS(String sensorReadTS) {
        this.sensorReadTS = sensorReadTS;
    }

    public int getScreenID() {
        return screenID;
    }

    public void setScreenID(int screenID) {
        this.screenID = screenID;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public String getSensorMID() {
        return sensorMID;
    }

    public void setSensorMID(String sensorMID) {
        this.sensorMID = sensorMID;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getSensorValue() {
        return sensorValue;
    }

    public void setSensorValue(String sensorValue) {
        this.sensorValue = sensorValue;
    }


    //**********************************************************************************************
    public static SensorBean getSensorForScreenID(int screenID, List<SensorBean> sensorBeanList){
        for (SensorBean sb : sensorBeanList) {
            if (sb.getScreenID() == screenID){
                return sb;
            }
        }
        return null;
    }

    //**********************************************************************************************
    public String toString(){
        return "SensorBean: ID: " + sensorID + ", TS: " + sensorReadTS + ", sensorName: " + sensorName + ", sensorValue: " + sensorValue + ", screenID: " + screenID;
    }
}
