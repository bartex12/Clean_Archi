package ru.geekbrains.arch.homework.repository;


import io.reactivex.Completable;
import io.reactivex.Single;

public interface LaunchCountRepository {
    Completable setLaunchNumber(int launchNumber);
    Single<Integer> getLaunchNumber();
}
