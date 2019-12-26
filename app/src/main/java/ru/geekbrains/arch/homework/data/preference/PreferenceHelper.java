package ru.geekbrains.arch.homework.data.preference;

import android.content.Context;
import android.preference.PreferenceManager;

import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.functions.Action;


public class PreferenceHelper {
    private final Context context;

    public PreferenceHelper(Context context) {
        this.context = context;
    }

    public Completable put(final String key, final int value) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() {
                PreferenceManager.getDefaultSharedPreferences(context)
                        .edit()
                        .putInt(key, value)
                        .apply();
            }
        });
    }

    public Single<Integer> getInt(final String key, final int defaultValue) {
        return Single.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() {
                return PreferenceManager.getDefaultSharedPreferences(context)
                        .getInt(key, defaultValue);
            }
        });
    }

}
