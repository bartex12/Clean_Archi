package view;

import ru.geekbrains.arch.homework.data.photo.PhotoDataSourceImplNoRx;
import ru.geekbrains.arch.homework.data.photo.PhotoDataSourceNoRx;
import ru.geekbrains.arch.homework.data.photo.PhotosRepositoryImplNoRx;
import ru.geekbrains.arch.homework.data.preference.LaunchCountRepositoryImplNoRx;
import ru.geekbrains.arch.homework.data.preference.PreferenceHelperNoRx;
import ru.geekbrains.arch.homework.interactor.main.MainInteractorImplNoRx;
import ru.geekbrains.arch.homework.interactor.main.MainInteractorNoRx;
import ru.geekbrains.arch.homework.network.ApiKeyProvider;
import ru.geekbrains.arch.homework.network.HostProvider;
import ru.geekbrains.arch.homework.network.flickr.FlickrApiKeyProvider;
import ru.geekbrains.arch.homework.network.flickr.FlickrApiNoRx;
import ru.geekbrains.arch.homework.network.flickr.FlickrHostProvider;
import ru.geekbrains.arch.homework.repository.LaunchCountRepositoryNoRx;
import ru.geekbrains.arch.homework.repository.PhotosRepositoryNoRx;
import ru.geekbrains.arch.homework.ui.main.MainPresenterImplNoRx;
import ru.geekbrains.arch.homework.ui.main.UserPresenterNoRx;
import ru.geekbrains.arch.homework.util.logger.Logger;
import ru.geekbrains.arch.homework.util.logger.LoggerImpl;
import ru.geekbrains.arch.homework.util.resources.ResourceManager;
import ru.geekbrains.arch.homework.util.resources.ResourceManagerImpl;

public class MainActivityInjector {

    //метод создаёт презентер со всеми зависимостями и устанавливает его в поле активити
    public void inject(MainActivity mainActivity) {

        // TODO: move to DI, make PreferenceHelper and LaunchCountRepository singletons
        PreferenceHelperNoRx preferenceHelperNoRx =
                new PreferenceHelperNoRx(mainActivity);
        LaunchCountRepositoryNoRx launchCountRepositoryNoRx =
                new LaunchCountRepositoryImplNoRx(preferenceHelperNoRx);
        Logger logger = new LoggerImpl();

        ResourceManager resourceManager = new ResourceManagerImpl(mainActivity);
        HostProvider hostProvider = new FlickrHostProvider(resourceManager);
        FlickrApiNoRx flickrApiNoRx = new FlickrApiNoRx(hostProvider);
        ApiKeyProvider apiKeyProvider = new FlickrApiKeyProvider(resourceManager);
        PhotoDataSourceNoRx photoDataSourceNoRx =
                new PhotoDataSourceImplNoRx(flickrApiNoRx,apiKeyProvider);
        PhotosRepositoryNoRx photosRepositoryNoRx =
                new PhotosRepositoryImplNoRx(photoDataSourceNoRx);
        MainInteractorNoRx mainInteractorNoRx =
                new MainInteractorImplNoRx(launchCountRepositoryNoRx, photosRepositoryNoRx);
        UserPresenterNoRx presenter = new MainPresenterImplNoRx(mainActivity, mainInteractorNoRx, logger);

        // инъекция зависимостей через метод-сеттер
        mainActivity.setPresenter(presenter);
    }
}
