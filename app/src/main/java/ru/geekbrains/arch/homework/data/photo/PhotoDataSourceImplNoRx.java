package ru.geekbrains.arch.homework.data.photo;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

import retrofit2.Response;
import ru.geekbrains.arch.homework.data.photo.model.ApiPhoto;
import ru.geekbrains.arch.homework.data.photo.model.ApiResult;
import ru.geekbrains.arch.homework.data.photo.model.PhotoResultMapper;
import ru.geekbrains.arch.homework.domain.Photo;
import ru.geekbrains.arch.homework.network.ApiKeyProvider;
import ru.geekbrains.arch.homework.network.flickr.FlickrApiNoRx;
import ru.geekbrains.arch.homework.network.flickr.FlickrPhotoApiServiceNoRx;

public class PhotoDataSourceImplNoRx implements PhotoDataSourceNoRx {

    private static final String TAG = "33333";
    private static final String FLICKR_PHOTOS_GET_RECENT = "flickr.photos.getRecent";
    private static final String JSON = "json";
    private static final String NO_JSON_CALLBACK = "1";
    private static final String URL_S = "url_s";

    //private final FlickrPhotoApiService service;
    private FlickrApiNoRx flickrApiNoRx;
    private ApiKeyProvider apiKeyProvider;
    private PhotoResultMapper photoResultMapper;

    public PhotoDataSourceImplNoRx(FlickrApiNoRx flickrApiNoRx,
                               ApiKeyProvider apiKeyProvider,
                               PhotoResultMapper photoResultMapper) {
        this.flickrApiNoRx = flickrApiNoRx;
        this.apiKeyProvider = apiKeyProvider;
        this.photoResultMapper = photoResultMapper;
    }


    @Override
    public List<Photo> getRecent(final int pageNumber, final int perPage) {

        Log.i(TAG, "PhotoDataSourceImplNoRx List<ApiResult> getRecent()");
        final List<Photo> photos =  new ArrayList<>();

        //TODO *************синхронизация нужна другая******************
        //возможно, нужно вместо execute - в том же потоке , сделать enqueue() - в своём потоке
        //это описано в видеоуроке по ретрофиту Андроид2 урок 5
        //для синхронизации потоков - чтобы основной ждал пока закончится поиск фото на сервере
        final CountDownLatch startSignal = new CountDownLatch(1);
        //final List<Photo> photos =  new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //создаём запрос с помощью ретрофита
                    FlickrPhotoApiServiceNoRx iService = flickrApiNoRx.getService();
                    Log.i(TAG, "PhotoDataSourceImplNoRx  iService = " + iService);

                    //выполняем запрос @GET("services/rest") к серверу
                    Response<ApiResult> response =iService.getRecentPhotos(
                            FLICKR_PHOTOS_GET_RECENT,
                            apiKeyProvider.getApiKey(),
                            JSON,
                            NO_JSON_CALLBACK,
                            perPage,
                            pageNumber,
                            URL_S).execute();

                    //получаем модель данных как она есть на сервере
                    ApiResult apiResults = response.body();
                    Log.i(TAG, "PhotoDataSourceImplNoRx  Всего  = " +
                            Objects.requireNonNull(apiResults).photos.getTotal());
                    //переходим к списку фото на сервере  - photos сделан public для разнообразия
                    List<ApiPhoto> fotoList = apiResults.photos.getPhotos();
                    //теперь надо перейти к списку фоток с единственным полем - URL
                    for (int i=0; i<fotoList.size(); i++) {
                        photos.add(new Photo(fotoList.get(i).getUrl()));
                    }
                    Log.i(TAG, "PhotoDataSourceImplNoRx  1 photos.size() = " + photos.size());
                    //разрешаем работу основного потока для возврата данных после окончания этого
                    startSignal.countDown();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        try {
            //заставляем основной поток ждать, пока не закончит работу другой поток - startSignal.countDown();
            startSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "PhotoDataSourceImplNoRx  2 photos.size() = " + photos.size());
        return photos;
    }
}
//    //TODO *************синхронизация нужна другая******************

//В этом варианте всё грузит нормально, но при уходе на активити детализации и
// последующем возврате ничего не грузится уже в отличие от предыдущего варианта с execute()

//    //создадим  запрос с помощью ретрофита
//    FlickrPhotoApiServiceNoRx iService = flickrApiNoRx.getService();
//        Log.i(TAG, "PhotoDataSourceImplNoRx  iService = " + iService);
//                //выполняем запрос @GET("services/rest") в отдельном потоке - т к enqueue, а не execute
//                iService.getRecentPhotos(
//                FLICKR_PHOTOS_GET_RECENT,
//                apiKeyProvider.getApiKey(),
//                JSON,
//                NO_JSON_CALLBACK,
//                perPage,
//                pageNumber,
//                URL_S).enqueue(new Callback<ApiResult>() {
//@Override
//public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
//        if (response.body() != null && response.isSuccessful()) {
//        //получаем модель данных как она есть на сервере
//        ApiResult apiResults = response.body();
//        Log.i(TAG, "PhotoDataSourceImplNoRx  Всего  = " +
//        Objects.requireNonNull(apiResults).photos.getTotal());
//        //переходим к списку фото на сервере  - photos сделан public для разнообразия
//        List<ApiPhoto> fotoList = apiResults.photos.getPhotos();
//        //теперь надо перейти к списку фото с единственным полем - URL
//        for (int i=0; i<fotoList.size(); i++) {
//        photos.add(new Photo(fotoList.get(i).getUrl()));
//        }
//        Log.i(TAG, "PhotoDataSourceImplNoRx  1 photos.size() = " + photos.size());
//        }
//        }
//
//@Override
//public void onFailure(Call<ApiResult> call, Throwable t) {
//
//        }
//        });
