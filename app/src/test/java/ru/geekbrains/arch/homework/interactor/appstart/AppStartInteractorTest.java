package ru.geekbrains.arch.homework.interactor.appstart;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import ru.geekbrains.arch.homework.repository.LaunchCountRepository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AppStartInteractorTest {

    private AppStartInteractor appStartInteractor;

    @Mock
    private LaunchCountRepository launchCountRepository;

    @Before
    public void setUp() {
        appStartInteractor = new AppStartInteractorImpl(launchCountRepository);
    }

    @Test
    public void shouldSetFirstLaunchWhenWasZero() {
        testCountAppStart(0, 1);
    }

    @Test
    public void shouldSetSecondLaunchWhenWasFirst() {
        testCountAppStart(1, 2);
    }

    @Test
    public void shouldSetThirdLaunchWhenWasSecond() {
        testCountAppStart(2, 3);
    }

    private void testCountAppStart(int storedLaunchNumber, int expectedLaunchNumber) {
        when(launchCountRepository.getLaunchNumber()).thenReturn(Single.just(storedLaunchNumber));

        TestObserver<Void> testObserver = new TestObserver<>();

        appStartInteractor.countAppStart().subscribe(testObserver);

        verify(launchCountRepository).setLaunchNumber(expectedLaunchNumber);
    }

}