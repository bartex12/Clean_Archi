package ru.geekbrains.arch.homework.ui.main;

public interface UserPresenterNoRx {

    void onStart();
    void onStop();

    void onRatePositive();
    void onRateNegative();

    void onSearch(String search);
}
