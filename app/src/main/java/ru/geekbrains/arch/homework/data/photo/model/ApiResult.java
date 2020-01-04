package ru.geekbrains.arch.homework.data.photo.model;

import com.google.gson.annotations.SerializedName;

public final class ApiResult {

    @SerializedName("photos")
    public ApiPhotoPage photos;

//    public ApiPhotoPage getPhotos() {
//        return photos;
//    }
}
