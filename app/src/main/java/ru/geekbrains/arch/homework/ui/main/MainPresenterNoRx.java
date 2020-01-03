package ru.geekbrains.arch.homework.ui.main;

public interface MainPresenterNoRx {

    interface View {
        void showRateProposal();

        //добавлено для отображения 2-6-10... запуска
        void showNumberLaunch();
    }

    void onStart(int number); //для передачи номера запуска приложения
    void onStop();

    void onRatePositive();
    void onRateNegative();
}
