package com.loftblog.loftmoney.screens.balance;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.loftblog.loftmoney.R;
import com.loftblog.loftmoney.common.diagram.DiagramView;
import com.loftblog.loftmoney.LoftApp;
import com.loftblog.loftmoney.screens.web.models.AuthResponse;

public class BalanceFragment extends Fragment implements BalanceViewState{

    public static BalanceFragment getInstance() {
        return new BalanceFragment();
    }

    private BalancePresenter balancePresenter;
    private AppCompatTextView balanceTextView;
    private AppCompatTextView incomeTextView;
    private AppCompatTextView expenseTextView;
    private CircularProgressView circularProgressView;
    private DiagramView  diagramView;
    private View divider1, divider2, divider3;
    private AppCompatTextView balanceTitle, incomeTitle, expenseTitle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_balance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        diagramView = view.findViewById(R.id.dvBalance);
        divider1 = view.findViewById(R.id.balanceDivider1);
        divider2 = view.findViewById(R.id.balanceDivider2);
        divider3 = view.findViewById(R.id.balanceDivider3);
        balanceTitle = view.findViewById(R.id.txtBalanceAvailableTitle);
        expenseTitle = view.findViewById(R.id.txtBalanceExpenseTitle);
        incomeTitle = view.findViewById(R.id.txtBalanceIncomesTitle);
        circularProgressView = view.findViewById(R.id.cpvBalance);
        balanceTextView = view.findViewById(R.id.txtBalanceAvailable);
        incomeTextView = view.findViewById(R.id.txtBalanceIncomes);
        expenseTextView = view.findViewById(R.id.txtBalanceExpense);

        balancePresenter = new BalancePresenter();
        balancePresenter.setBalanceViewState(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getContext() == null || getActivity() == null || getActivity().getApplication() == null) {
            return;
        }

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString(AuthResponse.AUTH_TOKEN_KEY, "");

        balancePresenter.fetchBalance(((LoftApp) getActivity().getApplication()).api(),
                authToken);
    }

    // Balance View State implementation
    @Override
    public void setState(Boolean isLoading) {
        circularProgressView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        expenseTextView.setVisibility(isLoading ? View.GONE : View.VISIBLE);
        incomeTextView.setVisibility(isLoading ? View.GONE : View.VISIBLE);
        balanceTextView.setVisibility(isLoading ? View.GONE : View.VISIBLE);
        divider1.setVisibility(isLoading ? View.GONE : View.VISIBLE);
        divider2.setVisibility(isLoading ? View.GONE : View.VISIBLE);
        divider3.setVisibility(isLoading ? View.GONE : View.VISIBLE);
        balanceTitle.setVisibility(isLoading ? View.GONE : View.VISIBLE);
        expenseTitle.setVisibility(isLoading ? View.GONE : View.VISIBLE);
        incomeTitle.setVisibility(isLoading ? View.GONE : View.VISIBLE);
        diagramView.setVisibility(isLoading ? View.GONE : View.VISIBLE);
    }

    @Override
    public void setBalance(String balance) {
        balanceTextView.setText(balance);
    }

    @Override
    public void setIncome(String income) {
        incomeTextView.setText(income);
    }

    @Override
    public void setExpense(String expense) {
        expenseTextView.setText(expense);
    }

    @Override
    public void setDiagram(int expense, int income) {
        diagramView.update(expense, income);
    }
}