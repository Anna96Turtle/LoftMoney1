package com.loftblog.loftmoney.skreens.authorisation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.loftblog.loftmoney.R;
import com.loftblog.loftmoney.skreens.main.MainActivity;
import com.loftblog.loftmoney.skreens.web.LoftApp;
import com.loftblog.loftmoney.skreens.web.models.AuthResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AuthActivity extends AppCompatActivity {

    private List<Disposable> disposeBag = new ArrayList<>();
    private Button bthAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        bthAuth = findViewById(R.id.btfAuthSingIn);
        bthAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignIn();
            }
        });
    }

    @Override
    protected void onDestroy() {
        for (Disposable disposable : disposeBag) {
            disposable.dispose();
        }
        super.onDestroy();
    }

    // Internal logic
    private void startSignIn() {
        bthAuth.setVisibility(View.INVISIBLE);

        Disposable request = ((LoftApp) getApplication()).getAuthRequest().request("Anna96Turtle")
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AuthResponse>() {
                    @Override
                    public void accept(AuthResponse authResponse) throws Exception {
                        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_name),
                                Context.MODE_PRIVATE);
                        sharedPreferences.edit()
                                .putString(AuthResponse.AUTH_TOKEN_KEY, authResponse.getAuthToken())
                                .apply();

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        bthAuth.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "Error - " + throwable.getLocalizedMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
        disposeBag.add(request);
    }
}
