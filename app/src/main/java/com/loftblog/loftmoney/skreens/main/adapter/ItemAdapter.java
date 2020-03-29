package com.loftblog.loftmoney.skreens.main.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loftblog.loftmoney.R;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private List<Item> mDataList = new ArrayList<>();

    public void setNewData(List<Item> newData) {
        mDataList.clear();
        mDataList.addAll(newData);
        notifyDataSetChanged();
    }

    public void addDataToTop(Item model) {
        mDataList.add(0, model);
        notifyItemInserted(0);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ItemViewHolder(layoutInflater.inflate(R.layout.cell_charge, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName = itemView.findViewById(R.id.txtItemName);
        private TextView txtValue = itemView.findViewById(R.id.txtItemValue);

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(Item item) {
            txtName.setText(item.getName());
            txtValue.setText(item.getValue());
        }
    }
}
