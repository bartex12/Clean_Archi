package ru.geekbrains.arch.homework.data.preference;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

public class PreferenceHelperNoRx {

    private final Context context;
    private static final String TAG = "33333";

    public PreferenceHelperNoRx(Context context) {
        this.context = context;
        //Log.d(TAG, "PreferenceHelperNoRx Конструктор");
    }

    public  void putNoRx(String key){

        int number = getIntNoRx(key,0);

        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(key, ++number)
                .apply();

        Log.d(TAG, "PreferenceHelperNoRx getIntNoRx запись полученного числа" );
    }

    public int getIntNoRx(String key, int defaultValue) {
        int number = PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(key, defaultValue);
        Log.d(TAG, "PreferenceHelperNoRx getIntNoRx получение числа = " + number );
        return number;
    }
}
