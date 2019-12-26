package ru.geekbrains.arch.homework.interactor.main;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import ru.geekbrains.arch.homework.repository.LaunchCountRepository;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainInteractorTest {

    private MainInteractor mainInteractor;

    @Mock
    private
    LaunchCountRepository launchCountRepository;


    @Before
    public void setUp() {
        mainInteractor = new MainInteractorImpl(launchCountRepository);
    }

    @Test
    public void showNotShowProposalOnZeroLaunch() {
        testShowRateProposal(0, false);
    }

    @Test
    public void showNotShowProposalOnFirstLaunch() {
        testShowRateProposal(1, false);
    }

    @Test
    public void showProposalOnSecondLaunch() {
        testShowRateProposal(2, true);
    }

    @Test
    public void showNotShowProposalOnThirdLaunch() {
        testShowRateProposal(3, false);
    }

    @Test
    public void showNotShowProposalOnForthLaunch() {
        testShowRateProposal(4, false);
    }

    @Test
    public void showNotShowProposalOnFifthLaunch() {
        testShowRateProposal(5, false);
    }

    @Test
    public void showShowProposalOnSixthLaunch() {
        testShowRateProposal(6, true);
    }

    @Test
    public void showNotShowProposalOnSeventhLaunch() {
        testShowRateProposal(7, false);
    }

    @Test
    public void showNotShowProposalOnEighthLaunch() {
        testShowRateProposal(8, false);
    }

    @Test
    public void showNotShowProposalOnNinthLaunch() {
        testShowRateProposal(9, false);
    }

    @Test
    public void showShowProposalOnTenthLaunch() {
        testShowRateProposal(10, true);
    }

    private void testShowRateProposal(int launchNumber, boolean isExpectedToShowProposal) {
        when(launchCountRepository.getLaunchNumber()).thenReturn(Single.just(launchNumber));

        TestObserver<Boolean> subscriber = new TestObserver<>();
        mainInteractor.shouldShowRateProposal().subscribe(subscriber);

        subscriber.assertValue(isExpectedToShowProposal);
    }

}