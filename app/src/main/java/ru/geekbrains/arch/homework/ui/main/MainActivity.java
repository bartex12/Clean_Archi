package ru.geekbrains.arch.homework.ui.main;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.arch.homework.R;
import ru.geekbrains.arch.homework.data.launch.LaunchCountRepositoryImplNoRx;
import ru.geekbrains.arch.homework.data.photo.PhotoDataSource;
import ru.geekbrains.arch.homework.data.photo.PhotoDataSourceImpl;
import ru.geekbrains.arch.homework.data.photo.PhotosRepositoryImpl;
import ru.geekbrains.arch.homework.data.photo.model.PhotoResultMapper;
import ru.geekbrains.arch.homework.data.preference.PreferenceHelperNoRx;
import ru.geekbrains.arch.homework.domain.Photo;
import ru.geekbrains.arch.homework.interactor.main.MainInteractorImplNoRx;
import ru.geekbrains.arch.homework.interactor.main.MainInteractorNoRx;
import ru.geekbrains.arch.homework.network.ApiKeyProvider;
import ru.geekbrains.arch.homework.network.HostProvider;
import ru.geekbrains.arch.homework.network.flickr.FlickrApi;
import ru.geekbrains.arch.homework.network.flickr.FlickrApiKeyProvider;
import ru.geekbrains.arch.homework.network.flickr.FlickrHostProvider;
import ru.geekbrains.arch.homework.repository.LaunchCountRepositoryNoRx;
import ru.geekbrains.arch.homework.repository.PhotosRepository;
import ru.geekbrains.arch.homework.util.logger.Logger;
import ru.geekbrains.arch.homework.util.logger.LoggerImpl;
import ru.geekbrains.arch.homework.util.resources.ResourceManagerImpl;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class MainActivity extends AppCompatActivity implements MainPresenterNoRx.View {

    private static final String TAG = "33333";
    public static final String NUMBER_OF_LAUNCH = "NUMBER_OF_LAUNCH";
    private int number =1;
    private MainPresenterNoRx presenter;
    private TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView1 = findViewById(R.id.text1);

        SharedPreferences prefSetting = getDefaultSharedPreferences(this);
        number = prefSetting.getInt(NUMBER_OF_LAUNCH,1);
        Log.d(TAG, "MainActivity onCreate number = " + number);

        createPresenter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "MainActivity onStart");

        presenter.onStart();

        testGettingPhotos();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void showRateProposal() {
        createProposalDialog().show();
    }

    private void createPresenter() {

        // TODO: move to DI, make PreferenceHelper and LaunchCountRepository singletons
        PreferenceHelperNoRx preferenceHelperNoRx =
                new PreferenceHelperNoRx(this.getApplicationContext());
        LaunchCountRepositoryNoRx launchCountRepositoryNoRx =
                new LaunchCountRepositoryImplNoRx(preferenceHelperNoRx);
        MainInteractorNoRx mainInteractorNoRx = new MainInteractorImplNoRx(launchCountRepositoryNoRx);
        Logger logger = new LoggerImpl();
        presenter = new MainPresenterImplNoRx(this, mainInteractorNoRx, logger);

    }

    private AlertDialog createProposalDialog() {
        Log.d(TAG, "MainActivity AlertDialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.rate_proposal_message)
                .setPositiveButton(R.string.rate_proposal_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        presenter.onRatePositive();
                    }
                })
                .setNegativeButton(R.string.rate_proposal_no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        presenter.onRateNegative();
                    }
                });
        return builder.create();
    }

    // TODO: remove it
    private void testGettingPhotos() {
        ResourceManagerImpl resourceManager = new ResourceManagerImpl(getApplicationContext());
        ApiKeyProvider apiKeyProvider = new FlickrApiKeyProvider(resourceManager);
        HostProvider hostProvider = new FlickrHostProvider(resourceManager);
        FlickrApi flickrApi = new FlickrApi(hostProvider);
        PhotoResultMapper photoResultMapper = new PhotoResultMapper();
        PhotoDataSource photoDataSource = new PhotoDataSourceImpl(flickrApi.getService(), apiKeyProvider, photoResultMapper);
        PhotosRepository photosRepository = new PhotosRepositoryImpl(photoDataSource);
        photosRepository.getRecent(0, 100)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Photo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Photo> photos) {
                        Log.i(TAG, "Got photos: " + photos.size() + " " + photos);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "Error getting photos", e);
                    }
                });
    }

    @Override
    public void showNumberLaunch() {
        //
        textView1.setText("Launch = " + number + " Оценить.");
    }

    @Override
    public void showNumberNo() {
        //
        textView1.setText("*****");
    }

}
