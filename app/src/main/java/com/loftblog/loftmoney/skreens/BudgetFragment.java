package com.loftblog.loftmoney.skreens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loftblog.loftmoney.R;
import com.loftblog.loftmoney.skreens.main.adapter.Item;
import com.loftblog.loftmoney.skreens.main.adapter.ItemAdapter;
import com.loftblog.loftmoney.skreens.second.SecondActivity;

public class BudgetFragment extends Fragment {

    private ItemAdapter itemAdapter = new ItemAdapter();
    static int ADD_ITEM_REQUEST = 1;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_budget, null);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerMain);
        recyclerView.setAdapter(itemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,
                false));

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

        if (requestCode == ADD_ITEM_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Item item = (Item) data.getSerializableExtra(Item.KEY_NAME);
            itemAdapter.addDataToTop(item);
        }
    }
}
