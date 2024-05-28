package com.example.nationalgallery;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class activity_saved_items extends AppCompatActivity {
    private ListView listView;
    private CustomAdapter adapter;
    private List<listitem> savedItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_items);

        listView = findViewById(R.id.listView);
        savedItemList = loadSavedItems();
        adapter = new CustomAdapter(this, R.layout.itemlist, savedItemList, true);
        listView.setAdapter(adapter);
    }

    private List<listitem> loadSavedItems() {
        SharedPreferences sharedPreferences = getSharedPreferences("saved_items", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("items", null);
        Type type = new TypeToken<ArrayList<listitem>>() {}.getType();
        return json == null ? new ArrayList<listitem>() : gson.fromJson(json, type);
    }
}