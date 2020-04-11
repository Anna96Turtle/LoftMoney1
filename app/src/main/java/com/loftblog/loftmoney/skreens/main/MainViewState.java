package com.loftblog.loftmoney.skreens.main;

import com.loftblog.loftmoney.skreens.main.adapter.Item;

import java.util.List;

public interface MainViewState {
    void startLoading();
    void setData(List<Item> items);
    void setError (String error);
    void setNoItems();
}
