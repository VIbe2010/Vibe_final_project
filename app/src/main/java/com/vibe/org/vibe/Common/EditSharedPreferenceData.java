package com.vibe.org.vibe.Common;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by Aman on 04/12/2017.
 */

public class EditSharedPreferenceData {

    public static void saveUserID(int userId){
        SharedPreferences sharedPref = EditMyAppContext.getAppContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("id", userId);
        editor.commit();
    }

    public static int getUserId(){
        SharedPreferences sharedPref = EditMyAppContext.getAppContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        int id = sharedPref.getInt("id",0);
        return id;
    }

    public static void saveIsPlace(boolean isPlace){
        SharedPreferences sharedPref = EditMyAppContext.getAppContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("isPlace", isPlace);
        editor.commit();
    }

    public static boolean isPlace(){
        SharedPreferences sharedPref = EditMyAppContext.getAppContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        boolean isPlace= sharedPref.getBoolean("isPlace",true);
        return isPlace;
    }


    public static void savePlaceMood(String placeMood){
        SharedPreferences sharedPref = EditMyAppContext.getAppContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("placeMood", placeMood);
        editor.commit();
    }

    public static String getPlaceMood(){
        SharedPreferences sharedPref = EditMyAppContext.getAppContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String placeMood= sharedPref.getString("placeMood","");
        return placeMood;
    }

}
