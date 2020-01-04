package ru.geekbrains.arch.homework.data.photo;

import java.util.List;
import ru.geekbrains.arch.homework.data.photo.model.ApiResult;
import ru.geekbrains.arch.homework.domain.Photo;


public interface PhotoDataSourceNoRx {
    List<Photo> getRecent(int pageNumber, int perPage);
}
