package ru.geekbrains.arch.homework.interactor.main;

import java.util.List;

import ru.geekbrains.arch.homework.domain.Photo;

public interface MainInteractorNoRx {
   boolean shouldShowRateProposalNoRx();
   void setNewNumberOfLaunch();
    List<Photo> getPhotos(int pageNumber, int pageSize);
    List<Photo> getRecentSearched(int pageNumber, int perPage, String textSearch);
}
