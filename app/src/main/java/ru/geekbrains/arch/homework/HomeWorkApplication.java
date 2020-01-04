package ru.geekbrains.arch.homework;

import android.app.Application;

public class HomeWorkApplication extends Application {
    private static final String TAG = "HomeWorkApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        //processAppStart();
    }

    private void processAppStart() {
//        // TODO: move to DI, make PreferenceHelper and LaunchCountRepository singletons
//        final PreferenceHelperNoRx preferenceHelperNoRx = new PreferenceHelperNoRx(this);
//        final LaunchCountRepositoryImplNoRx launchCountRepository =
//                new LaunchCountRepositoryImplNoRx(preferenceHelperNoRx);
//        final AppStartInteractor appStartInteractor = new AppStartInteractorImpl(launchCountRepository);
//        final Logger logger = new LoggerImpl();
//
//        appStartInteractor.countAppStart().subscribe(new CompletableObserver() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                logger.logException(TAG, "Could not count app start", e);
//            }
//        });
//    }
    }
}