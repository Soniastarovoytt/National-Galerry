package com.example.nationalgallery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

        private ListView listView;
        private CustomAdapter adapter;
        private List<listitem> itemList;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            listView = findViewById(R.id.listView);
            itemList = new ArrayList<>();
            adapter = new CustomAdapter(this, R.layout.itemlist, itemList, false);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("MainActivity", "Item clicked at position: " + position);
                    listitem selectedItem = itemList.get(position);

                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("item_title", selectedItem.getTitle());
                    intent.putExtra("item_description", selectedItem.getDescription());
                    intent.putExtra("item_image", selectedItem.getImageUrl());
                    startActivity(intent);
                }
            });

            ImageButton savedItemsButton = findViewById(R.id.saved_items_button);
            savedItemsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, activity_saved_items.class);
                    startActivity(intent);
                }
            });

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("items");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    itemList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        listitem item = snapshot.getValue(listitem.class);
                        itemList.add(item);
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("MainActivity", "Database error: " + databaseError.getMessage());
                }
            });


    }
}