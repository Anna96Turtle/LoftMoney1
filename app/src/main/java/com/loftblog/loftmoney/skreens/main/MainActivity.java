package com.loftblog.loftmoney.skreens.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.loftblog.loftmoney.R;
import com.loftblog.loftmoney.skreens.second.SecondActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    private List<Disposable> disposables = new ArrayList<>();
    static int ADD_ITEM_REQUEST = 1;
    public static final String TOKEN = "token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        RecyclerView recyclerView = findViewById(R.id.recyclerMain);
//        recyclerView.setAdapter(itemAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL));

        FloatingActionButton floatingActionButton = findViewById(R.id.fabMain);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondIntent = new Intent(MainActivity.this, SecondActivity.class);
                startActivityForResult(secondIntent, ADD_ITEM_REQUEST);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
