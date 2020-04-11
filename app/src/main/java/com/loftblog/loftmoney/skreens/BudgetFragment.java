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

public class BudgetFragment extends Fragment  {

    private static final int REQUEST_CODE = 100;
    private static final String COLOR_ID = "colorId";
    private static final String TYPE = "fragmentType";

//    private RecyclerView recyclerView;
//    private CircularProgressView cpvLoader;
//    private View notFound;
    private ItemAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Api mApi;
    private PostApi mpApi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.fragment_budget);
        mApi = ((LoftApp)getActivity().getApplication()).api();
//        cpvLoader = findViewById(R.id.cpvMain);
//        notFound = findViewById(R.id.llMainNoItem);

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
                loadItems();
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

//            SharedPreferences sharedPreferences = sharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
//            String authToken = sharedPreferences.getString(AuthResponse.AUTH_TOKEN_KEY, "");

            final String token = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(MainActivity.TOKEN, "");

            Call<Status> call = mpApi.addItem(new Item(name, getArguments().getString(TYPE), price), token);
            call.enqueue(new Callback<Status>() {

                @Override
                public void onResponse(
                        final Call<Status> call, final Response<Status> response
                ) {
                    if (response.body().getStatus().equals("success")) {
                        loadItems();
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
//        SharedPreferences sharedPreferences = sharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
//        String authToken = sharedPreferences.getString(AuthResponse.AUTH_TOKEN_KEY, "");

        final String token = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(MainActivity.TOKEN, "");

        Call<List<Item>> items = mApi.getItems(getArguments().getString(TYPE), token);
        items.enqueue(new Callback<List<Item>>() {

            @Override
            public void onResponse(
                    final Call<List<Item>> call, final Response<List<Item>> response
            ) {
                mSwipeRefreshLayout.setRefreshing(false);
                mAdapter.clearItem();
                List<Item> items = response.body();
                for (Item item : items) {
                    mAdapter.addItem(item);
                }
            }

            @Override
            public void onFailure(final Call<List<Item>> call, final Throwable t) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

//    //MainViewState implementation
//    @Override
//    public void startLoading() {
//    cpvLoader.setVisibility(View.VISIBLE);
//    recyclerView.setVisibility(View.GONE);
//    notFound.setVisibility(View.GONE);
//    }
//
//    @Override
//    public void setData(List<Item> items) {
//        cpvLoader.setVisibility(View.GONE);
//        recyclerView.setVisibility(View.VISIBLE);
//        notFound.setVisibility(View.GONE);
//
//        itemAdapter.setNewData (items);
//    }
//
//    @Override
//    public void setError(String error) {
//        cpvLoader.setVisibility(View.GONE);
//        recyclerView.setVisibility(View.GONE);
//        notFound.setVisibility(View.VISIBLE);
//
//        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public void setNoItems() {
//        cpvLoader.setVisibility(View.GONE);
//        recyclerView.setVisibility(View.GONE);
//        notFound.setVisibility(View.VISIBLE);
//
//    }
}