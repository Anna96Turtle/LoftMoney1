package com.loftblog.loftmoney.screens.main.adapter;

import androidx.fragment.app.Fragment;

public class MainFragmentModel {

    public MainFragmentModel(Fragment fragment, String title) {
        this.fragment = fragment;
        this.title = title;
    }

    private Fragment fragment;
    private String title;

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}