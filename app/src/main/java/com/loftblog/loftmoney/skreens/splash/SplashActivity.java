package com.loftblog.loftmoney.skreens.splash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.loftblog.loftmoney.R;
import com.loftblog.loftmoney.skreens.authorisation.AuthActivity;
import com.loftblog.loftmoney.skreens.main.MainActivity;
import com.loftblog.loftmoney.skreens.web.models.AuthResponse;

public class SplashActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString(AuthResponse.AUTH_TOKEN_KEY, "");

        if (TextUtils.isEmpty(authToken)) {
            startActivity(new Intent(getApplicationContext(), AuthActivity.class));
        } else {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        finish();
    }
}
