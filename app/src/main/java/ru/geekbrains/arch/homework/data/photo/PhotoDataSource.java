package ru.geekbrains.arch.homework.data.photo;

import java.util.List;

import io.reactivex.Single;
import ru.geekbrains.arch.homework.domain.Photo;

public interface PhotoDataSource {
    Single<List<Photo>> getRecent(int pageNumber, int perPage);
}
