package com.loftblog.loftmoney.common.money.adapter;

import android.view.View;

import com.loftblog.loftmoney.screens.web.models.ItemRemote;

import java.io.Serializable;

public class MoneyModel implements Serializable {

    public static String KEY_NAME = MoneyModel.class.getName();

    private String id;
    private String name;
    private int value;
    private String type;
    private int visibility;


    public MoneyModel(ItemRemote itemRemote,String name, int value, String type) {
        this.id = "1";
        this.name = name;
        this.value = value;
        this.type = type;
        this.visibility = View.VISIBLE;
    }

    public MoneyModel(ItemRemote itemRemote,String type) {
        this.id = itemRemote.getId();
        this.name = itemRemote.getName();
        this.value = itemRemote.getPrice();
        this.type = type;
        this.visibility = View.VISIBLE;
    }

    public  String getType() {
        return type;
    }

    public String getId() { return id; }

    public void setVisibility(int  visibility){ this.visibility = visibility;}

    public int getVisibility() { return visibility; }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public String getStringValue() {
        return String.valueOf(value) + "₽";
    }
}