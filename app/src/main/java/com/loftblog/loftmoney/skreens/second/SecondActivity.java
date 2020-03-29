package com.loftblog.loftmoney.skreens.second;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.loftblog.loftmoney.R;
import com.loftblog.loftmoney.skreens.main.adapter.Item;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        final EditText textName = findViewById(R.id.textSecondName);
        final EditText textValue = findViewById(R.id.textSecondValue);
        Button buttonAdd = findViewById(R.id.btnSecondEnter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = textName.getText().toString();
                String value = textValue.getText().toString();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(value)) {return;}

                String valueString = value + " Р";
                Item item = new Item(name, valueString);

                Intent intent = new Intent();
                intent.putExtra(Item.KEY_NAME, item);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
