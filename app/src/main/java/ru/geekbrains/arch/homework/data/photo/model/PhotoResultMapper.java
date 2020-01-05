package ru.geekbrains.arch.homework.data.photo.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Response;
import ru.geekbrains.arch.homework.domain.Photo;

public class PhotoResultMapper {

    private static final String TAG = "33333";

    public List<Photo> map(Response<ApiResult> response) {

        List<Photo> photos =  new ArrayList<>();
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

        return photos;
    }
}
