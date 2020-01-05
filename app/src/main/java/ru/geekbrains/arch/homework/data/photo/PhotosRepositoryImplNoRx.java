package ru.geekbrains.arch.homework.data.photo;

import java.util.List;
import ru.geekbrains.arch.homework.domain.Photo;
import ru.geekbrains.arch.homework.repository.PhotosRepositoryNoRx;

public class PhotosRepositoryImplNoRx implements PhotosRepositoryNoRx {

    private final PhotoDataSourceNoRx photoDataSourceNoRx;

    public PhotosRepositoryImplNoRx(PhotoDataSourceNoRx photoDataSourceNoRx) {
        this.photoDataSourceNoRx = photoDataSourceNoRx;
    }

    @Override
    public List<Photo> getRecent(int pageNumber, int pageSize) {
        return photoDataSourceNoRx.getRecent(pageNumber, pageSize);
    }

    @Override
    public List<Photo> getRecentSearched(int pageNumber, int perPage, String textSearch) {
        return photoDataSourceNoRx.getRecentSearched(pageNumber, perPage,textSearch);
    }
}
