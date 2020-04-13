package com.loftblog.loftmoney.screens.expenses;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.loftblog.loftmoney.R;
import com.loftblog.loftmoney.common.money.MoneyPresenter;
import com.loftblog.loftmoney.common.money.MoneyViewState;
import com.loftblog.loftmoney.common.money.adapter.MoneyModel;
import com.loftblog.loftmoney.common.money.adapter.MoneyAdapter;
import com.loftblog.loftmoney.screens.second.SecondActivity;
import com.loftblog.loftmoney.screens.web.GetApi;
import com.loftblog.loftmoney.LoftApp;
import com.loftblog.loftmoney.screens.web.models.AuthResponse;

import java.util.List;

public class MoneyFragment extends Fragment implements MoneyViewState {

    public static String TYPE_KEY = "Type";
    private MoneyAdapter moneyAdapter = new MoneyAdapter();
    private MoneyPresenter moneyPresenter = new MoneyPresenter();
    private RecyclerView recyclerView;
    private CircularProgressView cpvLoader;
    private View notFound;
    private static String type = "expense";

    public static MoneyFragment getInstance(String type) {
        MoneyFragment moneyFragment = new MoneyFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TYPE_KEY, type);
        moneyFragment.setArguments(bundle);
        return moneyFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null) {
            return;
        }

        type = getArguments().getString(TYPE_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_expenses, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cpvLoader = view.findViewById(R.id.cpvMain);
        notFound = view.findViewById(R.id.llMainNoItems);
        recyclerView = view.findViewById(R.id.recyclerMain);
        recyclerView.setAdapter(moneyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,
                false));

        view.findViewById(R.id.fabMain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondIntent = new Intent(getContext(), SecondActivity.class);
                secondIntent.putExtra(TYPE_KEY, type);
                startActivity(secondIntent);
            }
        });
        moneyPresenter.setMoneyViewState(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getContext() == null || getActivity() == null) {
            return;
        }

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString(AuthResponse.AUTH_TOKEN_KEY, "");

        GetApi getApi = ((LoftApp) getActivity().getApplication()).api();

        moneyPresenter.loadItems(authToken, type, getApi);
    }

    @Override
    public void onDestroy() {
        moneyPresenter.onDestroy();
        super.onDestroy();
    }

    //MoneyViewState implementation
    @Override
    public void startLoading() {
    cpvLoader.setVisibility(View.VISIBLE);
    recyclerView.setVisibility(View.GONE);
    notFound.setVisibility(View.GONE);
    }

    @Override
    public void setData(List<MoneyModel> moneyModels) {
        cpvLoader.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        notFound.setVisibility(View.GONE);

        moneyAdapter.setNewData (moneyModels);
    }

    @Override
    public void setError(String error) {
        cpvLoader.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        notFound.setVisibility(View.VISIBLE);

        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setNoItems() {
        cpvLoader.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        notFound.setVisibility(View.VISIBLE);
    }
}