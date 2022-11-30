package com.example.weebly;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weebly.helpers.CacheHelper;
import com.example.weebly.placeholder.Content;

public class AnimeView extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Content.AnimeSched theAnime = (Content.AnimeSched) getIntent().getSerializableExtra("theAnime");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(theAnime.name);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_view);


        TextView ratings = findViewById(R.id.ratings);
        TextView synopsis = findViewById(R.id.synopsis);
        ImageView thumbnail = findViewById(R.id.thumbnail);
        TextView genres = findViewById(R.id.genres);
        TextView popularity = findViewById(R.id.popularity);
        Button malUrl = findViewById(R.id.malUrl);

        popularity.setText(theAnime.popularity);
        ratings.setText(theAnime.score);
        synopsis.setText(theAnime.synopsis);
        genres.setText(theAnime.genres);

        new CacheHelper.DownloadImageTask(thumbnail)
                .execute(theAnime.thumbnail);

        malUrl.setOnClickListener(view -> {
            Uri uri = Uri.parse(theAnime.malUrl);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
//            Toast.makeText(this, theAnime.malUrl, Toast.LENGTH_LONG).show();
        });

    }

}
