package exercise.android.arrk.starwars.utils;

import android.text.TextUtils;

import java.util.Collection;

public class AppUtil {

    //Check Collection for Empty
    public static boolean isCollectionEmpty(Collection<? extends Object> collection)
    {
        if(collection == null || collection.isEmpty())
        {
            return true;
        }

        return false;
    }

    //Convert centimeter value to meter. In case of format error returning blank string
    public static String convertCmToMeter(String value) {
        double output;
        String valueInMeter = "";

        try {
            if( !TextUtils.isEmpty(value) ) {
                output = Double.valueOf(value) / 100;
                valueInMeter = String.format("%.2f",output);
            }
        } catch (NumberFormatException ex) {
        }

        return valueInMeter;
    }

    //get Date
    public static String getDateString(String value) {
        String date = "";
        if( !TextUtils.isEmpty(value)
                && value.length() > 9) {
            date = value.substring(0, 10);
        }

        return date;
    }

    //get Time
    public static String getTimeString(String value) {

        String time = "";
        if( !TextUtils.isEmpty(value)
                && value.length() > 18) {
            time = value.substring(11, 19);
        }

        return time;
    }
}
