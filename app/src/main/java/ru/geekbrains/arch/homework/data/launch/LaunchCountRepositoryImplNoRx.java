package ru.geekbrains.arch.homework.data.launch;

import android.util.Log;

import ru.geekbrains.arch.homework.data.preference.PreferenceHelperNoRx;
import ru.geekbrains.arch.homework.repository.LaunchCountRepositoryNoRx;

import static ru.geekbrains.arch.homework.ui.main.MainActivity.NUMBER_OF_LAUNCH;

public class LaunchCountRepositoryImplNoRx implements LaunchCountRepositoryNoRx {
    private static final String KEY_APP_LAUNCH_NUMBER = "app_launch_number";
    private static final String TAG = "33333";

    private PreferenceHelperNoRx preferenceHelperNoRx;

    public LaunchCountRepositoryImplNoRx(PreferenceHelperNoRx preferenceHelperNoRx) {
        this.preferenceHelperNoRx = preferenceHelperNoRx;
        //Log.d(TAG, "LaunchCountRepositoryImplNoRx Конструктор");
    }

    @Override
    public void setLaunchNumberNoRx() {
        preferenceHelperNoRx.putNoRx(NUMBER_OF_LAUNCH);
    }

    @Override
    public int getLaunchNumberNoRx() {
        Log.d(TAG, "LaunchCountRepositoryImplNoRx getLaunchNumberNoRx до получения числа");
        return preferenceHelperNoRx.getIntNoRx(NUMBER_OF_LAUNCH, 0);
    }

}
