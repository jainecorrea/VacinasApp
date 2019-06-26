package com.example.jaine.vacinacaoapp.activity.activity;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jaine.vacinacaoapp.R;

public class MainActivity extends AppCompatActivity {

    TextView alias1, alias2, alias3, alias4, alias5, alias6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alias1 = findViewById(R.id.textView50);
        alias2 = findViewById(R.id.textView51);
        alias3 = findViewById(R.id.textView52);
        alias4 = findViewById(R.id.textView53);
        alias5 = findViewById(R.id.textView54);
        alias6 = findViewById(R.id.textView55);
    }

}
