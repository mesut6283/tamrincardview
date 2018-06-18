package com.example.danesh.tamrincardview.Activity.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.danesh.tamrincardview.R;

public class testActivity extends AppCompatActivity {
ListView list1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        list1=(ListView)findViewById(R.id.listtest);
    }
}
