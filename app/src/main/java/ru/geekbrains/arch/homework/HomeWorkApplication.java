package ru.geekbrains.arch.homework;

import android.app.Application;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import ru.geekbrains.arch.homework.interactor.appstart.AppStartInteractor;
import ru.geekbrains.arch.homework.interactor.appstart.AppStartInteractorImpl;
import ru.geekbrains.arch.homework.util.logger.Logger;
import ru.geekbrains.arch.homework.util.logger.LoggerImpl;

public class HomeWorkApplication extends Application {
    private static final String TAG = "HomeWorkApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        processAppStart();
    }

    private void processAppStart() {
        // TODO: move to DI, make PreferenceHelper and LaunchCountRepository singletons
        final PreferenceHelper preferenceHelper = new PreferenceHelper(this);
        final LaunchCountRepositoryImpl launchCountRepository = new LaunchCountRepositoryImpl(preferenceHelper);
        final AppStartInteractor appStartInteractor = new AppStartInteractorImpl(launchCountRepository);
        final Logger logger = new LoggerImpl();

        appStartInteractor.countAppStart().subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {
                logger.logException(TAG, "Could not count app start", e);
            }
        });
    }
}
