package ru.geekbrains.arch.homework.data.photo;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import ru.geekbrains.arch.homework.data.photo.model.ApiResult;
import ru.geekbrains.arch.homework.data.photo.model.PhotoResultMapper;
import ru.geekbrains.arch.homework.domain.Photo;
import ru.geekbrains.arch.homework.network.ApiKeyProvider;
import ru.geekbrains.arch.homework.network.flickr.FlickrPhotoApiService;

public class PhotoDataSourceImpl implements PhotoDataSource {

    private static final String FLICKR_PHOTOS_GET_RECENT = "flickr.photos.getRecent";
    private static final String JSON = "json";
    private static final String NO_JSON_CALLBACK = "1";
    private static final String URL_S = "url_s";

    private final FlickrPhotoApiService service;
    private ApiKeyProvider apiKeyProvider;
    private PhotoResultMapper photoResultMapper;

    public PhotoDataSourceImpl(FlickrPhotoApiService service,
                               ApiKeyProvider apiKeyProvider,
                               PhotoResultMapper photoResultMapper) {
        this.service = service;
        this.apiKeyProvider = apiKeyProvider;
        this.photoResultMapper = photoResultMapper;
    }

    @Override
    public Single<List<Photo>> getRecent(int pageNumber, int perPage) {
        return service.getRecent(
                        FLICKR_PHOTOS_GET_RECENT,
                        apiKeyProvider.getApiKey(),
                        JSON,
                        NO_JSON_CALLBACK,
                        perPage,
                        pageNumber,
                        URL_S)
                .map(new Function<ApiResult, List<Photo>>() {
                    @Override
                    public List<Photo> apply(ApiResult apiResult) {
                        return photoResultMapper.map(apiResult);
                    }
                });
    }

}
