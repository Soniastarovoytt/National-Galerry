package com.example.nationalgallery;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<listitem> {
    private Context context;
    private int resource;
    private List<listitem> items;
    private boolean isSavedList;  // Новое поле для определения типа списка

    public CustomAdapter(Context context, int resource, List<listitem> items, boolean isSavedList) {
        super(context, resource, items);
        this.context = context;
        this.resource = resource;
        this.items = items;
        this.isSavedList = isSavedList;  // Инициализация нового поля
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        listitem listItem = items.get(position);

        ImageView imageView = convertView.findViewById(R.id.item_image);
        TextView titleView = convertView.findViewById(R.id.item_title);
        ImageButton saveButton = convertView.findViewById(R.id.save_button);
        Button deleteButton = convertView.findViewById(R.id.delete_button);
        TextView descriptionView = convertView.findViewById(R.id.desc);

        titleView.setText(listItem.getTitle());
        descriptionView.setText(listItem.getDescription());
        Glide.with(context).load(listItem.getImageUrl()).into(imageView);

        if (isSavedList) {
            saveButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeItem(position);
                }
            });
        } else {
            saveButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.GONE);
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveItem(listItem);
                }
            });
        }

        return convertView;
    }

    private void saveItem(listitem item) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("saved_items", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = sharedPreferences.getString("items", null);
        Type type = new TypeToken<ArrayList<listitem>>() {}.getType();
        List<listitem> itemList = json == null ? new ArrayList<listitem>() : gson.fromJson(json, type);

        itemList.add(item);
        json = gson.toJson(itemList);
        editor.putString("items", json);
        editor.apply();
    }

    private void removeItem(int position) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("saved_items", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = sharedPreferences.getString("items", null);
        Type type = new TypeToken<ArrayList<listitem>>() {}.getType();
        List<listitem> itemList = json == null ? new ArrayList<listitem>() : gson.fromJson(json, type);

        itemList.remove(position);
        json = gson.toJson(itemList);
        editor.putString("items", json);
        editor.apply();

        // Удаляем элемент из списка и уведомляем адаптер об изменениях
        items.remove(position);
        notifyDataSetChanged();
    }
}
