package ru.geekbrains.arch.homework.interactor.main;

import io.reactivex.Single;

public interface MainInteractor {
    Single<Boolean> shouldShowRateProposal();
}
