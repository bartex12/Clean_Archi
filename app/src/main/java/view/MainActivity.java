package view;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;
import java.util.List;
import java.util.Locale;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.geekbrains.arch.homework.R;
import ru.geekbrains.arch.homework.domain.Photo;
import ru.geekbrains.arch.homework.ui.main.UserPresenterNoRx;
import ru.geekbrains.arch.homework.ui.main.UserViewNoRx;
import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class MainActivity extends AppCompatActivity implements UserViewNoRx {

    private static final String TAG = "33333";
    public static final String NUMBER_OF_LAUNCH = "NUMBER_OF_LAUNCH";
    public static final String PHOTO_URL = "PHOTO_URL";
    private int number =1;
    private UserPresenterNoRx presenter;
    private TextView textView1;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter; //адаптер для RecyclerView
    private EditText editTextSearch;
    private Button buttonSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefSetting = getDefaultSharedPreferences(this);
        number = prefSetting.getInt(NUMBER_OF_LAUNCH,1);
        Log.d(TAG, "MainActivity onCreate number = " + number);

        MainActivityInjector mainActivityInjector = new MainActivityInjector();
        mainActivityInjector.inject(this);

        initViews();
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

    //метод для определения презентера из вне MainActivity
    public void setPresenter(UserPresenterNoRx presenter){
        this.presenter = presenter;
    }

    private void initViews() {
        textView1 = findViewById(R.id.text1);
        recyclerView = findViewById(R.id.recycledViewUrl);
        editTextSearch = findViewById(R.id.editTextSearch);
        buttonSearch = findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = editTextSearch.getText().toString();
                if (search.trim().isEmpty()){
                    Snackbar.make(view, "Введите текст для поиска фото", Snackbar.LENGTH_SHORT)
                            .show();
                }else {
                    Log.d(TAG, "MainActivity initViews search = " + search);
                    presenter.onSearch(search);
                }
            }
        });
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

        //реализуем интерфейс адаптера, в  его методе onCityClick получим url картинки
        RecyclerViewAdapter.OnPhotoClickListener onPhotoClickListener =
                new RecyclerViewAdapter.OnPhotoClickListener() {
                    @Override
                    public void onPhotoClick(String url) {
                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putExtra(PHOTO_URL,url);
                        startActivity(intent);
                    }
                };
        //используем встроенный GridLayoutManager
        GridLayoutManager layoutManager = new GridLayoutManager(getBaseContext(), 3);
        recyclerViewAdapter = new RecyclerViewAdapter(photos);
        recyclerViewAdapter.setOnPhotoClickListener(onPhotoClickListener);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
    }
}
