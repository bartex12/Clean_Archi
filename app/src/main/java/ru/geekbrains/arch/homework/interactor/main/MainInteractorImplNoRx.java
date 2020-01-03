package ru.geekbrains.arch.homework.interactor.main;

import android.util.Log;

import ru.geekbrains.arch.homework.repository.LaunchCountRepositoryNoRx;

public class MainInteractorImplNoRx implements MainInteractorNoRx {
    private LaunchCountRepositoryNoRx launchCountRepositoryNoRx;
    private static final String TAG = "33333";

    public MainInteractorImplNoRx(LaunchCountRepositoryNoRx launchCountRepositoryNoRx) {
        this.launchCountRepositoryNoRx = launchCountRepositoryNoRx;
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
        launchCountRepositoryNoRx.setLaunchNumberNoRx();
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
