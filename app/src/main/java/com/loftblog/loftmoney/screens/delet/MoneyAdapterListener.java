package com.loftblog.loftmoney.screens.delet;

import com.loftblog.loftmoney.common.money.adapter.MoneyModel;

public interface MoneyAdapterListener {
    public void onItemClick(MoneyModel item, int position);

    public void onItemLongClick(MoneyModel item, int position);


}