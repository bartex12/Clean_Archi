package ru.geekbrains.arch.homework.ui.main;

public interface MainPresenter {

    interface View {
        void showRateProposal();

    }

    void onStart();
    void onStop();

    void onRatePositive();
    void onRateNegative();
}
