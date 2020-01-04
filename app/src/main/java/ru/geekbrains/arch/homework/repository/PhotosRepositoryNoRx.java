package ru.geekbrains.arch.homework.repository;

import java.util.List;

import ru.geekbrains.arch.homework.domain.Photo;

public interface PhotosRepositoryNoRx {
    List<Photo> getRecent(int pageNumber, int pageSize);
}
