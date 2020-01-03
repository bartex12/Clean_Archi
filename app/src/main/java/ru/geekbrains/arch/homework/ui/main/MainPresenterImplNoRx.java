package ru.geekbrains.arch.homework.ui.main;

import android.util.Log;

import ru.geekbrains.arch.homework.interactor.main.MainInteractorNoRx;
import ru.geekbrains.arch.homework.util.logger.Logger;

public class MainPresenterImplNoRx implements MainPresenterNoRx {

    private final View view;
    private final MainInteractorNoRx mainInteractorNoRx;
    private Logger logger;
    private static final String TAG = "33333";

    MainPresenterImplNoRx(View view, MainInteractorNoRx mainInteractorNoRx, Logger logger) {
        this.view = view;
        this.mainInteractorNoRx = mainInteractorNoRx;
        this.logger = logger;
        //Log.d(TAG, "MainPresenterImplNoRx Конструктор");
    }

    @Override
    public void onStart() {
        Log.d(TAG, "MainPresenterImplNoRx onStart");
        if ( mainInteractorNoRx.shouldShowRateProposalNoRx()) {
            Log.d(TAG, "MainPresenterImplNoRx onStart true - показать диалог");
            view.showRateProposal();
        }
    }

    @Override
    public void onStop() {
        mainInteractorNoRx.setNewNumberOfLaunch();
    }

    @Override
    public void onRatePositive() {
        // TODO: process user positive reaction
        //показываем  номер и Оценить
        view.showNumberLaunch();
    }

    @Override
    public void onRateNegative() {
        // TODO: process user negative reaction
        //показываем звёздочки
        view.showNumberNo();
    }


}
