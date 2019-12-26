package ru.geekbrains.arch.homework.ui.main;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ru.geekbrains.arch.homework.interactor.main.MainInteractor;
import ru.geekbrains.arch.homework.util.logger.Logger;

class MainPresenterImpl implements MainPresenter {

    private static final String TAG = "Main";
    private final View view;
    private final MainInteractor mainInteractor;
    private Logger logger;
    private final CompositeDisposable compositeDisposable;

    MainPresenterImpl(View view, MainInteractor mainInteractor, Logger logger) {
        this.view = view;
        this.mainInteractor = mainInteractor;
        this.logger = logger;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onStart(int number) {

        if ((number == 2) || ((((number + 2)%4) == 0))){
            view.showNumberLaunch();
        }

        mainInteractor.shouldShowRateProposal().subscribe(new SingleObserver<Boolean>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                compositeDisposable.add(disposable);
            }

            @Override
            public void onSuccess(Boolean shouldShowRateProposal) {
                if (shouldShowRateProposal) {
                    view.showRateProposal();
                }
            }

            @Override
            public void onError(Throwable e) {
                logger.logException(TAG, "Should show rate error ", e);
            }
        });
    }

    @Override
    public void onStop() {
        compositeDisposable.dispose();
    }

    @Override
    public void onRatePositive() {
        // TODO: process user positive reaction
    }

    @Override
    public void onRateNegative() {
        // TODO: process user negative reaction
    }
}
