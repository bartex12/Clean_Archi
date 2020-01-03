package ru.geekbrains.arch.homework.ui.main;

public interface MainPresenter {

   interface View{
       void showNumberLaunch();
       void showNumberNo();
    }


    void onStart();
    void onStop();
}
