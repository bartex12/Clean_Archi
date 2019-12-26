package ru.geekbrains.arch.homework.data.photo.model;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.arch.homework.domain.Photo;

public class PhotoResultMapper {
    public List<Photo> map(ApiResult apiResult) {
        ApiPhotoPage photoPage = apiResult.getPhotos();

        ArrayList<Photo> photos = new ArrayList<>(photoPage.getPhotos().size());
        for (ApiPhoto apiPhoto : photoPage.getPhotos()) {
            photos.add(new Photo(apiPhoto.getUrl()));
        }

        return photos;
    }
}
