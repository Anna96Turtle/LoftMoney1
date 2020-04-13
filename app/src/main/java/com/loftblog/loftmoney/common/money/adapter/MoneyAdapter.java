package com.loftblog.loftmoney.common.money.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loftblog.loftmoney.R;

import java.util.ArrayList;
import java.util.List;

public class MoneyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MoneyModel> mDataList = new ArrayList<>();
    private final int VIEW_TYPE_CHARGE = 0;
    private final int VIEW_TYPE_RATE = 1;

    public void setNewData(List<MoneyModel> newData) {
        mDataList.clear();
        mDataList.addAll(newData);
        notifyDataSetChanged();
    }

    public void addDataToTop(MoneyModel model) {
        mDataList.add(0, model);
        mDataList.get(mDataList.size() - 1).setVisibility(View.GONE);
        notifyItemInserted(0);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ChargesViewHolder(layoutInflater.inflate(R.layout.cell_charge, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RateViewHolder) {
            ((RateViewHolder) holder).bind("We love you!");
        } else {
            ((ChargesViewHolder) holder).bind(mDataList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    static class RateViewHolder extends RecyclerView.ViewHolder {

        public RateViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(String message) {

        }
    }

    static class ChargesViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName = itemView.findViewById(R.id.txtItemName);
        private TextView txtValue = itemView.findViewById(R.id.txtItemValue);
        private View divider = itemView.findViewById(R.id.viewChargeDivider);

        ChargesViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(MoneyModel moneyModel) {
            txtName.setText(moneyModel.getName());
            txtValue.setText(moneyModel.getStringValue());
            divider.setVisibility(moneyModel.getVisibility());
        }
    }
}