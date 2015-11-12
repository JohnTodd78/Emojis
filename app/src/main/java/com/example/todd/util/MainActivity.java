package com.example.todd.util;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.todd.connector.EmojisConnector;
import com.example.todd.emojis.R;

public class MainActivity extends AppCompatActivity {

    EmojisConnector emojisHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emojisHelper = new EmojisConnector(this);
        emojisHelper.execute();
    }
}
