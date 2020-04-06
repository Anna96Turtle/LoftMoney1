package com.loftblog.loftmoney.skreens.web.models;

import java.util.List;

public class GetItemsResponseModel {
    private String status;
    private List<ItemRemote> data;

    public String getStatus() {
        return status;
    }

    public List<ItemRemote> getData() {
        return data;
    }
}
