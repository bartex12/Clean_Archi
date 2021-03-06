package ru.geekbrains.arch.homework.network.flickr;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.geekbrains.arch.homework.data.photo.model.ApiResult;

public interface FlickrPhotoApiService {

    @GET("services/rest")
    Single<ApiResult> getRecent(
            @Query("method") String method,
            @Query("api_key") String apiKey,
            @Query("format") String format,
            @Query("nojsoncallback") String noJsonCallback,
            @Query("per_page") int perPage,
            @Query("page") int pageNumber,
            @Query("extras") String extras);

}
