package com.loftblog.loftmoney.common.money;

import android.os.Handler;

import com.loftblog.loftmoney.common.money.adapter.MoneyModel;
import com.loftblog.loftmoney.screens.web.GetApi;
import com.loftblog.loftmoney.screens.web.models.ItemRemote;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MoneyPresenter {

    private List<Disposable> disposables = new ArrayList();
    private MoneyViewState moneyViewState = null;

    public void setMoneyViewState(MoneyViewState moneyViewState) {
        this.moneyViewState = moneyViewState;
    }

    // Internal logic
    public void onDestroy() {
        for (Disposable disposable: disposables) {
            disposable.dispose();
        }
    }

    public void loadItems(String authToken, final String type, GetApi getItemsRequest) {
        moneyViewState.startLoading();
        Disposable response =  getItemsRequest.request(type, authToken)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ItemRemote>>() {
                    @Override
                    public void accept(List<ItemRemote> itemRemotes) throws Exception {
                        final List<MoneyModel> moneyModels = new ArrayList<>();
                        for (ItemRemote itemRemote: itemRemotes) {
                            moneyModels.add(new MoneyModel(itemRemote,type));
                        }
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (moneyModels.isEmpty()) {
                                    moneyViewState.setNoItems();
                                }  else {
                                    moneyViewState.setData(moneyModels);
                                }
                            }
                        }, 4000);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        moneyViewState.setError(throwable.getLocalizedMessage());
                    }
                });

        disposables.add(response);
    }
}