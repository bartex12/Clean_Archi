package ru.geekbrains.arch.homework.data.launch;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.geekbrains.arch.homework.data.preference.PreferenceHelper;
import ru.geekbrains.arch.homework.repository.LaunchCountRepository;

public class LaunchCountRepositoryImpl implements LaunchCountRepository {

    private static final String KEY_APP_LAUNCH_NUMBER = "app_launch_number";

    private final PreferenceHelper preferenceHelper;

    public LaunchCountRepositoryImpl(PreferenceHelper preferenceHelper) {
        this.preferenceHelper = preferenceHelper;
    }

    @Override
    public Completable setLaunchNumber(int launchNumber) {
        return preferenceHelper.put(KEY_APP_LAUNCH_NUMBER, launchNumber);
    }

    @Override
    public Single<Integer> getLaunchNumber() {
        return preferenceHelper.getInt(KEY_APP_LAUNCH_NUMBER, 0);
    }
}
