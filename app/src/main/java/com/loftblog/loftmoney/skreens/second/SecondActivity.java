package com.loftblog.loftmoney.skreens.second;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loftblog.loftmoney.R;
import com.loftblog.loftmoney.skreens.main.adapter.Item;
import com.loftblog.loftmoney.skreens.web.LoftApp;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SecondActivity extends AppCompatActivity {

    private EditText textName;
    private EditText textValue;
    private Button mButtonAdd;
    private String mName;
    private String mValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textName = findViewById(R.id.textSecondName);
        textName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    final CharSequence charSequence,
                    final int start,
                    final int count,
                    final int after
            ) {
            }

            @Override
            public void onTextChanged(
                    final CharSequence charSequence,
                    final int start,
                    final int before,
                    final int count
            ) {
            }

            @Override
            public void afterTextChanged(final Editable editable) {
                mName = editable.toString();
                checkEditTextHasText();
            }
        });

        textValue = findViewById(R.id.textSecondValue);
        textValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    final CharSequence charSequence,
                    final int start,
                    final int count,
                    final int after
            ) {
            }

            @Override
            public void onTextChanged(
                    final CharSequence charSequence,
                    final int start,
                    final int before,
                    final int count
            ) {
            }

            @Override
            public void afterTextChanged(final Editable editable) {
                mValue = editable.toString();
                checkEditTextHasText();
            }
        });

        mButtonAdd = findViewById(R.id.btnSecondEnter);
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(mName) || TextUtils.isEmpty(mValue)) {return;}
                sendNewExpense(Integer.valueOf(textValue.getText().toString()),
                        textName.getText().toString());
            }
        });
    }

    public void checkEditTextHasText() {
    mButtonAdd.setEnabled(!TextUtils.isEmpty(mName) && !TextUtils.isEmpty(mValue));
    }

    //Internal logic
    private void sendNewExpense(Integer price, String name) {
        Disposable disposables = LoftApp.getInstance().postApi().requect(price,name, "expence")
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        Toast.makeText(getApplicationContext(), getString(R.string.massage_success), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getApplicationContext(),throwable.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            List<Disposable> disposables1 = new ArrayList();
            disposables1.add(disposables);

    }
}
