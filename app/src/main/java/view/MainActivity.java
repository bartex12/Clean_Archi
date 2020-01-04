package view;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.arch.homework.R;
import ru.geekbrains.arch.homework.data.photo.PhotoDataSourceImplNoRx;
import ru.geekbrains.arch.homework.data.photo.PhotoDataSourceNoRx;
import ru.geekbrains.arch.homework.data.photo.PhotosRepositoryImplNoRx;
import ru.geekbrains.arch.homework.data.preference.LaunchCountRepositoryImplNoRx;
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
import ru.geekbrains.arch.homework.network.flickr.FlickrApiNoRx;
import ru.geekbrains.arch.homework.network.flickr.FlickrHostProvider;
import ru.geekbrains.arch.homework.repository.LaunchCountRepositoryNoRx;
import ru.geekbrains.arch.homework.repository.PhotosRepository;
import ru.geekbrains.arch.homework.repository.PhotosRepositoryNoRx;
import ru.geekbrains.arch.homework.ui.main.MainPresenterImplNoRx;
import ru.geekbrains.arch.homework.ui.main.UserPresenterNoRx;
import ru.geekbrains.arch.homework.ui.main.UserViewNoRx;
import ru.geekbrains.arch.homework.util.logger.Logger;
import ru.geekbrains.arch.homework.util.logger.LoggerImpl;
import ru.geekbrains.arch.homework.util.resources.ResourceManager;
import ru.geekbrains.arch.homework.util.resources.ResourceManagerImpl;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class MainActivity extends AppCompatActivity implements UserViewNoRx {

    private static final String TAG = "33333";
    public static final String NUMBER_OF_LAUNCH = "NUMBER_OF_LAUNCH";
    private int number =1;
    private UserPresenterNoRx presenter;
    private TextView textView1;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter; //адаптер для RecyclerView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView1 = findViewById(R.id.text1);

        SharedPreferences prefSetting = getDefaultSharedPreferences(this);
        number = prefSetting.getInt(NUMBER_OF_LAUNCH,1);
        Log.d(TAG, "MainActivity onCreate number = " + number);

        createPresenter();

        initRecycler();
    }

    private void initRecycler() {
        recyclerView = findViewById(R.id.recycledViewUrl);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "MainActivity onStart");

        presenter.onStart();

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
        Logger logger = new LoggerImpl();

        ResourceManager resourceManager = new ResourceManagerImpl(this);
        HostProvider hostProvider = new FlickrHostProvider(resourceManager);
        FlickrApiNoRx flickrApiNoRx = new FlickrApiNoRx(hostProvider);
        ApiKeyProvider apiKeyProvider = new FlickrApiKeyProvider (resourceManager);
        PhotoResultMapper photoResultMapper = new PhotoResultMapper();
        PhotoDataSourceNoRx photoDataSourceNoRx =
                new PhotoDataSourceImplNoRx(flickrApiNoRx,apiKeyProvider,photoResultMapper);
        PhotosRepositoryNoRx photosRepositoryNoRx =
                new PhotosRepositoryImplNoRx(photoDataSourceNoRx);
        MainInteractorNoRx mainInteractorNoRx =
                new MainInteractorImplNoRx(launchCountRepositoryNoRx, photosRepositoryNoRx);

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

    @Override
    public void showNumberLaunch() {
        //textView1.setText(String.format(Locale.ENGLISH,"Запуск № %d", number));
        textView1.setText(String.format(Locale.ENGLISH,
                "%s%d", getResources().getString(R.string.launch_), number));
    }

    @Override
    public void showNumberNo() {
        //
        textView1.setText(getResources().getString(R.string.stars));
    }

    @Override
    public void showPhotosResent(List<Photo> photos) {
        Log.d(TAG, "MainActivity showPhotosResent photos.size() = "+ photos.size());
        //используем встроенный LinearLayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewAdapter = new RecyclerViewAdapter(photos);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);

    }

}
