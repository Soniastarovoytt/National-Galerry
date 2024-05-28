package com.example.nationalgallery;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        ImageView imageView = findViewById(R.id.image);
        TextView titleView = findViewById(R.id.textView);
        TextView descriptionView = findViewById(R.id.desc);

        String title = getIntent().getStringExtra("item_title");
        String description = getIntent().getStringExtra("item_description");
        String imageUrl = getIntent().getStringExtra("item_image");




        titleView.setText(title);
        descriptionView.setText(description);
        Glide.with(this).load(imageUrl).into(imageView);
    }
}