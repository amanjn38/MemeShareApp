package com.example.memeshareapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    private Button btnNext, btnShare;
    public ProgressBar progressBar;
    private String url1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnNext = findViewById(R.id.btnNext);
        btnShare = findViewById(R.id.btnShare);
        progressBar = findViewById(R.id.progressBar);
//        progressBar.setVisibility(View.VISIBLE);
        loadMeme();
        btnNext.setOnClickListener(view -> {
            loadMeme();
        });

        btnShare.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, "Hey check out this cool meme I found on reddit " + url1);
            intent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(intent, "Share this meme using...");
            startActivity(shareIntent);

        });
    }

    public void loadMeme() {

        String url = "https://meme-api.herokuapp.com/gimme";

// Request a string response from the provided URL.
        progressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                         url1 = response.getString("url");
                        System.out.println("links" + url1);
                        ImageView imageView = findViewById(R.id.image);
                        Glide.with(MainActivity.this).load(url1).listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                progressBar.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressBar.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(imageView);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            System.out.println("links");
        });
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }
}