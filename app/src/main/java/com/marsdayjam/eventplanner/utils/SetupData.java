package com.marsdayjam.eventplanner.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by soropeza on 21/07/17.
 */

public class SetupData {

    private SharedPreferences sharedPreferences;
    private static SetupData setupData = null;

    private SetupData(Context context){
        sharedPreferences =  context.getSharedPreferences("ComusCalendar_Setup", Context.MODE_PRIVATE);
    }

    public static SetupData getInstance(Context context){
        if (setupData==null){
            setupData = new SetupData(context);
        }
        return setupData;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

}
