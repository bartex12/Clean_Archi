package ru.geekbrains.arch.homework.data.photo;

import java.util.List;

import io.reactivex.Single;
import ru.geekbrains.arch.homework.domain.Photo;
import ru.geekbrains.arch.homework.repository.PhotosRepository;

public class PhotosRepositoryImpl implements PhotosRepository {

    private final PhotoDataSource photoDataSource;

    public PhotosRepositoryImpl(PhotoDataSource photoDataSource) {
        this.photoDataSource = photoDataSource;
    }

    @Override
    public Single<List<Photo>> getRecent(int pageNumber, int pageSize) {
        return photoDataSource.getRecent(pageNumber, pageSize);
    }
}
