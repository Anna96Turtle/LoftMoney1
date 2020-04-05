package com.loftblog.loftmoney.skreens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.loftblog.loftmoney.R;
import com.loftblog.loftmoney.Status;
import com.loftblog.loftmoney.skreens.main.MainActivity;
import com.loftblog.loftmoney.skreens.main.adapter.Item;
import com.loftblog.loftmoney.skreens.main.adapter.ItemAdapter;
import com.loftblog.loftmoney.skreens.second.SecondActivity;
import com.loftblog.loftmoney.skreens.web.Api;
import com.loftblog.loftmoney.skreens.web.LoftApp;
import com.loftblog.loftmoney.skreens.web.PostApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BudgetFragment extends Fragment {

    private static final int REQUEST_CODE = 100;
    private static final String COLOR_ID = "colorId";
    private static final String TYPE = "fragmentType";

    private ItemAdapter mAdapter;

    private Api mApi;
    private PostApi mpApi;
    static int ADD_ITEM_REQUEST = 1;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApi = ((LoftApp)getActivity().getApplication()).api();
        loadItems();
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_budget, null);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });

        Button callAddButton = view.findViewById(R.id.btnSecondEnter);
        callAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                startActivityForResult(new Intent(getActivity(), SecondActivity.class),
                        REQUEST_CODE);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.recyclerMain);

        mAdapter = new ItemAdapter(getArguments().getInt(COLOR_ID));
        recyclerView.setAdapter(mAdapter);

        view.findViewById(R.id.fabMain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondIntent = new Intent(getActivity(), SecondActivity.class);
                startActivityForResult(secondIntent, ADD_ITEM_REQUEST);
            }
        });
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String price;
        try {
            price = String.valueOf(Integer.parseInt(data.getStringExtra("price")));
        } catch (NumberFormatException e) {
            price = String.valueOf(0);
        }
        final String realPrice = price;
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            final String name = data.getStringExtra("name");

            final String token = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(MainActivity.TOKEN, "");

            Call<Status> call = mpApi.addItem(new Item(name, getArguments().getString(TYPE), price), token);
            call.enqueue(new Callback<Status>() {

                @Override
                public void onResponse(
                        final Call<Status> call, final Response<Status> response
                ) {
                    if (response.body().getStatus().equals("success")) {
                        mAdapter.addItem(new Item(name, realPrice));
                    }
                }

                @Override
                public void onFailure(final Call<Status> call, final Throwable t) {
                    t.printStackTrace();
                }
            });

        }
    }

    public void loadItems() {
        final String token = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(MainActivity.TOKEN, "");

        Call<List<Item>> items = mApi.getItems(getArguments().getString(TYPE), token);
        items.enqueue(new Callback<List<Item>>() {

            @Override
            public void onResponse(
                    final Call<List<Item>> call, final Response<List<Item>> response
            ) {
                List<Item> items = response.body();
                for (Item item : items) {
                    mAdapter.addItem(item);
                }
            }

            @Override
            public void onFailure(final Call<List<Item>> call, final Throwable t) {

            }
        });
//        if (requestCode == ADD_ITEM_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
//            Item item = (Item) data.getSerializableExtra(Item.KEY_NAME);
//            mAdapter.addDataToTop(item);
//        }
    }
}