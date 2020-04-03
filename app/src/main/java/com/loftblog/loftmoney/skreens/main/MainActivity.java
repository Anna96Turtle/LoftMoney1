package com.loftblog.loftmoney.skreens.main;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.loftblog.loftmoney.R;
import com.loftblog.loftmoney.skreens.BudgetFragment;
import com.loftblog.loftmoney.skreens.main.adapter.Item;
import com.loftblog.loftmoney.skreens.main.adapter.ItemAdapter;
import com.loftblog.loftmoney.skreens.second.SecondActivity;
import com.loftblog.loftmoney.skreens.web.LoftApp;
import com.loftblog.loftmoney.skreens.web.models.GetItemsResponseModel;
import com.loftblog.loftmoney.skreens.web.models.ItemRemote;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private ItemAdapter itemAdapter = new ItemAdapter();
    private List<Disposable> disposables = new ArrayList();
    static int ADD_ITEM_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView  = findViewById(R.id.recyclerMain);
        recyclerView.setAdapter(itemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,
                false));
        findViewById(R.id.fabMain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondIntent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivityForResult(secondIntent, ADD_ITEM_REQUEST);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        loadItems();
    }

    @Override
    protected void onStop(){
        for (Disposable disposable: disposables){
            disposable.dispose();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //Internal logic
    private void loadItems() {
        Disposable response = LoftApp.getInstance().api().request("expense")
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GetItemsResponseModel>() {
                    @Override
                    public void accept(GetItemsResponseModel getItemsResponseModel) throws Exception {
                        List<Item> items = new ArrayList<>();
                        for (ItemRemote itemRemote : getItemsResponseModel.getData()) {
                            items.add(new Item(itemRemote));
                        }
                        itemAdapter.setNewData(items);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getApplicationContext(),throwable.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
        disposables.add(response);
    }
}
