package ru.geekbrains.arch.homework.network.flickr;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.geekbrains.arch.homework.network.HostProvider;

public class FlickrApiNoRx {
    private final HostProvider hostProvider;

    public FlickrApiNoRx(HostProvider hostProvider) {
        this.hostProvider = hostProvider;
    }

    public FlickrPhotoApiServiceNoRx getService() {
        Gson gson = new GsonBuilder().create();

//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
// .client(client)

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(hostProvider.getHostUrl())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(FlickrPhotoApiServiceNoRx.class);
    }
}
