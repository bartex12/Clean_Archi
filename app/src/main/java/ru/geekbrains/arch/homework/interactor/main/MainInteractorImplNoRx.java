package ru.geekbrains.arch.homework.interactor.main;

import android.util.Log;

import java.util.List;

import ru.geekbrains.arch.homework.domain.Photo;
import ru.geekbrains.arch.homework.repository.LaunchCountRepositoryNoRx;
import ru.geekbrains.arch.homework.repository.PhotosRepositoryNoRx;

public class MainInteractorImplNoRx implements MainInteractorNoRx {
    private LaunchCountRepositoryNoRx launchCountRepositoryNoRx;
    private PhotosRepositoryNoRx photosRepositoryNoRx;
    private static final String TAG = "33333";

    public MainInteractorImplNoRx(LaunchCountRepositoryNoRx launchCountRepositoryNoRx,
                                  PhotosRepositoryNoRx photosRepositoryNoRx) {
        this.launchCountRepositoryNoRx = launchCountRepositoryNoRx;
        this.photosRepositoryNoRx = photosRepositoryNoRx;
       // Log.d(TAG, "MainInteractorImplNoRx Конструктор");
    }

    @Override
    public boolean shouldShowRateProposalNoRx() {
        Log.d(TAG, "MainInteractorImplNoRx shouldShowRateProposalNoRx");
        int number = launchCountRepositoryNoRx.getLaunchNumberNoRx();
        Log.d(TAG, "MainInteractorImplNoRx shouldShowRateProposalNoRx число =" + number);
        return shouldShowRateProposal(number);
    }

    @Override
    public void setNewNumberOfLaunch() {
        //
        launchCountRepositoryNoRx.setLaunchNumberNoRx();
    }

    @Override
    public List<Photo> getPhotos(int pageNumber, int pageSize) {
        return photosRepositoryNoRx.getRecent(pageNumber,pageSize);
    }

    @Override
    public List<Photo> getRecentSearched(int pageNumber, int perPage, String textSearch) {
        return photosRepositoryNoRx.getRecentSearched(pageNumber,perPage, textSearch);
    }


    private Boolean shouldShowRateProposal(Integer launchNumber) {
        if (launchNumber == 2) {
            Log.d(TAG, "условие = " + true);
            return true;
        } else {
            Log.d(TAG, "условие = " + ((launchNumber - 2) % 4 == 0));
            return (launchNumber - 2) % 4 == 0;
        }
    }
}
