package ru.geekbrains.arch.homework.ui.main;

import java.util.List;

import ru.geekbrains.arch.homework.domain.Photo;

public interface UserViewNoRx {
    void showRateProposal();
    void showNumberLaunch();
    void showNumberNo();
    void showPhotosResent(List<Photo> photos );
}
