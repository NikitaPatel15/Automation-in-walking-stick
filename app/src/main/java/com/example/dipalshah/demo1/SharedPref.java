package com.example.dipalshah.demo1;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by feni.kadivar on 27-12-2017.
 */

public class SharedPref {
    private static ArrayList<String> MY_STRING_PREF ;
    private static ArrayList<String> MY_STRING_PREF_NUMBER ;

    public static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences("App_setting", context.MODE_PRIVATE);
    }

    public static ArrayList<String> getMyStringPref(Context context) {
        MY_STRING_PREF=new ArrayList<>();
        Set<String> set = getPrefs(context).getStringSet("Contact_name", null);
        MY_STRING_PREF.addAll(set);
//        set.addAll(MY_STRING_PREF);
        return MY_STRING_PREF;
    }



    public static void setMyStringPref(Context context, ArrayList<String> value) {
        // perform validation etc..
        Set<String> set = new HashSet<String>();
        set.addAll(value);
        getPrefs(context).edit().putStringSet("Contact_name", set).commit();
    }
    public static ArrayList<String> getMyStringPrefNo(Context context) {
        MY_STRING_PREF_NUMBER=new ArrayList<>();
        Set<String> set = getPrefs(context).getStringSet("Contact_no", null);
        MY_STRING_PREF_NUMBER.addAll(set);
//        set.addAll(MY_STRING_PREF_NUMBER);
        return MY_STRING_PREF_NUMBER;
    }



    public static void setMyStringPrefNo(Context context, ArrayList<String> value) {
        // perform validation etc..
        Set<String> set = new HashSet<String>();
        set.addAll(value);
        getPrefs(context).edit().putStringSet("Contact_no", set).commit();
    }

}
