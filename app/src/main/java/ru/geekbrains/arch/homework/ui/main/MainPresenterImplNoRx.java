package ru.geekbrains.arch.homework.ui.main;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.arch.homework.domain.Photo;
import ru.geekbrains.arch.homework.interactor.main.MainInteractorNoRx;
import ru.geekbrains.arch.homework.util.logger.Logger;

public class MainPresenterImplNoRx implements UserPresenterNoRx {

    private final UserViewNoRx view;
    private final MainInteractorNoRx mainInteractorNoRx;
    private Logger logger;
    private static final String TAG = "33333";
    private static final int PAGE_NUMBER = 1;
    private static final int PAGE_SIZE_RESENT = 33;
    private static final int PAGE_SIZE_SEARCHED = 100;

    private List<Photo> photos = new ArrayList<>();

    public MainPresenterImplNoRx(UserViewNoRx view, MainInteractorNoRx mainInteractorNoRx, Logger logger) {
        this.view = view;
        this.mainInteractorNoRx = mainInteractorNoRx;
        this.logger = logger;
    }

    @Override
    public void onStart() {
        Log.d(TAG, "MainPresenterImplNoRx onStart");
        if ( mainInteractorNoRx.shouldShowRateProposalNoRx()) {
            Log.d(TAG, "MainPresenterImplNoRx onStart true - показать диалог");
            view.showRateProposal();
        }

        photos = mainInteractorNoRx.getPhotos(PAGE_NUMBER,PAGE_SIZE_RESENT);
        Log.d(TAG, "*******Presenter ************ photos.size() =" + photos.size());
        view.showPhotosResent(photos);
    }

    @Override
    public void onStop() {
        //
        mainInteractorNoRx.setNewNumberOfLaunch();
    }

    @Override
    public void onRatePositive() {
        //показываем  номер и Оценить
        view.showNumberLaunch();
    }

    @Override
    public void onRateNegative() {
        //показываем звёздочки
        view.showNumberNo();
    }

    @Override
    public void onSearch(String searchText) {
        // TODO: process user  Search action
        Log.d(TAG, "MainPresenterImplNoRx onSearch searchText = " + searchText);
        photos = mainInteractorNoRx.getRecentSearched(PAGE_NUMBER,PAGE_SIZE_SEARCHED,searchText);
        Log.d(TAG, "******* Presenter ************ photos.size() =" + photos.size());
        view.showPhotosResent(photos);
    }
}
