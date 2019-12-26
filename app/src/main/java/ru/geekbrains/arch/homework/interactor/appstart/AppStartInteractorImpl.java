package ru.geekbrains.arch.homework.interactor.appstart;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.functions.Function;
import ru.geekbrains.arch.homework.repository.LaunchCountRepository;

public class AppStartInteractorImpl implements AppStartInteractor {

    private final LaunchCountRepository launchCountRepository;

    public AppStartInteractorImpl(LaunchCountRepository launchCountRepository) {
        this.launchCountRepository = launchCountRepository;
    }

    @Override
    public Completable countAppStart() {
        return launchCountRepository.getLaunchNumber()
                .flatMapCompletable(new Function<Integer, CompletableSource>() {
                    @Override
                    public CompletableSource apply(Integer launchNumber) {
                        return launchCountRepository.setLaunchNumber(launchNumber + 1);
                    }
                });
    }
}
