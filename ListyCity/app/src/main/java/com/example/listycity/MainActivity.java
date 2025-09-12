package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    int selectedPos = -1;         // track the selected item
    String selectedCity = null;   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addButton = findViewById(R.id.addButton);
        Button deleteButton = findViewById(R.id.deleteButton);
        EditText editList = findViewById(R.id.editList);

        cityList = findViewById(R.id.city_list);

        String []cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        deleteButton.setEnabled(false);
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedPos = position;
            selectedCity = dataList.get(position);
            deleteButton.setEnabled(true);
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newItem = editList.getText().toString().trim();
                if (!newItem.isEmpty()) {
                    dataList.add(newItem);
                    cityAdapter.notifyDataSetChanged(); // Notify adapter of data change
                    editList.setText(""); // Clear the EditText
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPos >= 0 && selectedPos < dataList.size()) {
                    // in case there are duplicate names
                    dataList.remove(selectedPos);
                    cityAdapter.notifyDataSetChanged(); // refresh the list UI
                    // reset selection so don't accidentally delete  without tap
                    selectedPos = -1;
                    selectedCity = null;
                    deleteButton.setEnabled(false);
                } else {
                    // nothing selected — ignore click
                }
            }
        });
    }
}
