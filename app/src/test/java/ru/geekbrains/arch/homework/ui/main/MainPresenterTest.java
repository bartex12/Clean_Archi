package ru.geekbrains.arch.homework.ui.main;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Single;
import ru.geekbrains.arch.homework.interactor.main.MainInteractor;
import ru.geekbrains.arch.homework.util.logger.Logger;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    private MainPresenter mainPresenter;

    @Mock
    private MainPresenter.View view;

    @Mock
    private MainInteractor mainInteractor;

    @Mock
    private Logger logger;


    @Before
    public void setUp() {
        mainPresenter = new MainPresenterImpl(view, mainInteractor, logger);
    }

    @Test
    public void shouldShowProposalOnStartIfInteractorSaysYes() {
        when(mainInteractor.shouldShowRateProposal()).thenReturn(Single.just(true));

        mainPresenter.onStart();

        verify(view).showRateProposal();
        verifyNoMoreInteractions(view);
    }

    @Test
    public void shouldNotShowProposalOnStartIfInteractorSaysNo() {
        when(mainInteractor.shouldShowRateProposal()).thenReturn(Single.just(false));

        mainPresenter.onStart();

        verifyNoMoreInteractions(view);
    }

    @Test
    public void shouldShowNothingOnStartIfInteractorError() {
        when(mainInteractor.shouldShowRateProposal()).thenReturn(Single.<Boolean>error(new Throwable()));

        mainPresenter.onStart();

        verifyNoMoreInteractions(view);
    }
}