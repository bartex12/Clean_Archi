package ru.geekbrains.arch.homework.interactor.main;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import ru.geekbrains.arch.homework.repository.LaunchCountRepository;

public class MainInteractorImpl implements MainInteractor {

    private final LaunchCountRepository launchCountRepository;

    public MainInteractorImpl(LaunchCountRepository launchCountRepository) {
        this.launchCountRepository = launchCountRepository;
    }

    @Override
    public Single<Boolean> shouldShowRateProposal() {
        return launchCountRepository.getLaunchNumber()
                .map(new Function<Integer, Boolean>() {
                    @Override
                    public Boolean apply(Integer launchNumber) {
                        return shouldShowRateProposal(launchNumber);
                    }
                });
    }

    private Boolean shouldShowRateProposal(Integer launchNumber) {
        if (launchNumber == 2) {
            return true;
        } else {
            return (launchNumber - 2) % 4 == 0;
        }
    }
}
