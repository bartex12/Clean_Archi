package ru.geekbrains.arch.homework.ui.main;

public interface MainPresenterNoRx {

    interface View {
        void showRateProposal();

        void showNumberLaunch();
        void showNumberNo();
    }

    void onStart();
    void onStop();

    void onRatePositive();
    void onRateNegative();
}
