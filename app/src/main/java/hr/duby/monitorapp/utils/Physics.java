package hr.duby.monitorapp.utils;

import android.renderscript.Sampler;

/**
 * Created by dvrbancic on 23/11/2017.
 */

public class Physics {

    //CONSTANTS
    public static final float FLOW_CKP = 2.2f;
    public static final float FLOW_RAD = 1.7f;

    //CALCULATIONS
    public static int calculatePowerConsumation(float flow, float tCold, float tHot){
        //P = Q * Cp * ro * dT;     Q[m3/s], , ro[kg/m3], dT[T2-T1]
        float Q = flow / 60f / 60f;     //flow -> 1.7 m3/h
        float Cp = 4200f;               //heat capacity of water        Cp[J/kg°C]
        float ro = 1000f;               //water density                 ro[kg/m3]
        float dT = tHot - tCold;        //dT[T2-T1]

        float power = Q * Cp * ro * dT / 1000f;
        return Math.round(power);

    }

}
