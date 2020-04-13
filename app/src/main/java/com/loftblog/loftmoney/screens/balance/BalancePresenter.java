package com.loftblog.loftmoney.screens.balance;

import android.annotation.SuppressLint;

import com.loftblog.loftmoney.common.money.adapter.MoneyModel;
import com.loftblog.loftmoney.screens.web.GetApi;
import com.loftblog.loftmoney.screens.web.models.ItemRemote;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BalancePresenter {

    private BalanceViewState balanceViewState;

    public void setBalanceViewState(BalanceViewState balanceViewState) {
        this.balanceViewState = balanceViewState;
    }

    @SuppressLint("CheckResult")
    void fetchBalance(GetApi getItemsRequest, String authToken) {
        Single<List<ItemRemote>> expenseRequest = getItemsRequest.request("expense", authToken);
        Single<List<ItemRemote>> incomeRequest = getItemsRequest.request("income", authToken);

        balanceViewState.setState(true);
        Single.zip(expenseRequest, incomeRequest, new BiFunction<List<ItemRemote>, List<ItemRemote>, List<MoneyModel>>() {
            @Override
            public List<MoneyModel> apply(List<ItemRemote> expenses, List<ItemRemote> incomes) throws Exception {
                List<MoneyModel> totalModels = new ArrayList<>(expenses.size() + incomes.size());

                for (ItemRemote expense : expenses) {
                    totalModels.add(new MoneyModel(expense, "expense"));
                }

                for (ItemRemote income : incomes) {
                    totalModels.add(new MoneyModel(income, "income"));
                }

                return totalModels;
            }
        })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<MoneyModel>>() {
                    @Override
                    public void accept(List<MoneyModel> moneyModels) throws Exception {
                        int balance = 0;
                        int income = 0;
                        int expense = 0;

                        for (MoneyModel moneyModel: moneyModels) {
                            if (moneyModel.getType().equals("expense")) {
                                expense += moneyModel.getValue();
                                balance -= moneyModel.getValue();
                            }

                            if (moneyModel.getType().equals("income")) {
                                income += moneyModel.getValue();
                                balance += moneyModel.getValue();
                            }
                        }

                        balanceViewState.setState(false);
                        balanceViewState.setBalance(balance + "₽");
                        balanceViewState.setIncome(income + "₽");
                        balanceViewState.setExpense(expense + "₽");
                        balanceViewState.setDiagram(expense, income);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }
}