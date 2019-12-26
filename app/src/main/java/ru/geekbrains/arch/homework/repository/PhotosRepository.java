package ru.geekbrains.arch.homework.repository;

import java.util.List;

import io.reactivex.Single;
import ru.geekbrains.arch.homework.domain.Photo;

public interface PhotosRepository {
    Single<List<Photo>> getRecent(int pageNumber, int pageSize);
}
