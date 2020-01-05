package view;

import androidx.appcompat.app.AppCompatActivity;
import ru.geekbrains.arch.homework.R;

import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    ImageView imageViewDetail;
    public static final String PHOTO_URL = "PHOTO_URL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String url = Objects.requireNonNull(getIntent().getExtras()).getString(PHOTO_URL);
        imageViewDetail = findViewById(R.id.imageViewDetail);
        Picasso.get().load(url).into(imageViewDetail);
    }


}
