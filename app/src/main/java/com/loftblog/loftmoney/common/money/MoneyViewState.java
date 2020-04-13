package com.loftblog.loftmoney.common.money;

import com.loftblog.loftmoney.common.money.adapter.MoneyModel;

import java.util.List;

public interface MoneyViewState {
    void startLoading();
    void setData(List<MoneyModel> items);
    void setError (String error);
    void setNoItems();
}