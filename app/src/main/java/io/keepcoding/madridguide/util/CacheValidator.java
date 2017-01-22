package io.keepcoding.madridguide.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

import io.keepcoding.madridguide.R;

public class CacheValidator {

    public static boolean isShopsCacheValid(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.CACHE_POLICY, Context.MODE_PRIVATE);
        long timeDifference = (new Date().getTime()) - sharedPreferences.getLong(Constants.CACHE_POLICY_SHOPS_TIME,0);
        return (timeDifference <= (Integer.parseInt(context.getString(R.string.cache_days_to_expire))*1000*24*60*60) ); // Days in miliseconds
    }

    public static boolean isActivitiesCacheValid(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.CACHE_POLICY, Context.MODE_PRIVATE);
        long timeDifference = (new Date().getTime()) - sharedPreferences.getLong(Constants.CACHE_POLICY_ACTIVITIES_TIME,0);
        return (timeDifference < (Integer.parseInt(context.getString(R.string.cache_days_to_expire))*1000*24*60*60) ); // Days in miliseconds
    }

    public static void resetShopsCacheTime(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.CACHE_POLICY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(Constants.CACHE_POLICY_SHOPS_TIME, (new Date().getTime()));
        editor.commit();
    }

    public static void resetActivitiesCacheTime(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.CACHE_POLICY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(Constants.CACHE_POLICY_ACTIVITIES_TIME, (new Date().getTime()));
        editor.commit();
    }

}
