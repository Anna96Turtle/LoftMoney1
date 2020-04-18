package com.loftblog.loftmoney.screens.main.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class MainPagerAdapter extends FragmentPagerAdapter {

    private List<MainFragmentModel> mainFragmentModels;

    public MainPagerAdapter(@NonNull FragmentManager fm, List<MainFragmentModel> mainFragmentModelList) {
        super(fm);
        this.mainFragmentModels = mainFragmentModelList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mainFragmentModels.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return mainFragmentModels.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mainFragmentModels.get(position).getTitle();
    }
}
