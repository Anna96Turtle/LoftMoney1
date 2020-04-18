package com.loftblog.loftmoney.screens.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.loftblog.loftmoney.R;
import com.loftblog.loftmoney.screens.balance.BalanceFragment;
import com.loftblog.loftmoney.screens.expenses.MoneyFragment;
import com.loftblog.loftmoney.screens.main.adapter.MainFragmentModel;
import com.loftblog.loftmoney.screens.main.adapter.MainPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

//    @Override
//    public void onActionModeStarted(final ActionMode mode) {
//        super.onActionModeStarted(mode);
//        mTabLayout.setBackgroundColor(ContextCompat.getColor(this,R.color.dark_gray_blue));
//        mToolbar.setBackgroundColor(ContextCompat.getColor(this,R.color.dark_gray_blue));
//    }
//
//    @Override
//    public void onActionModeFinished(final ActionMode mode) {
//        super.onActionModeFinished(mode);
//        mTabLayout.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary));
//        mToolbar.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary));
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<MainFragmentModel> mainFragmentModels = new ArrayList<>(3);
        mainFragmentModels.add(new MainFragmentModel(MoneyFragment.getInstance("expense"),
                getString(R.string.expenses)));
        mainFragmentModels.add(new MainFragmentModel(MoneyFragment.getInstance("income"),
                getString(R.string.income)));
        mainFragmentModels.add(new MainFragmentModel(BalanceFragment.getInstance(),
                getString(R.string.balance)));

        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(),
                mainFragmentModels);

        ViewPager mainViewPager = findViewById(R.id.vpMain);
        mainViewPager.setOffscreenPageLimit(3);
        mainViewPager.setAdapter(mainPagerAdapter);
    }
}