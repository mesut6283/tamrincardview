package com.example.danesh.tamrincardview.Activity.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danesh.tamrincardview.R;

public class MainActivity extends AppCompatActivity {
    CardView c1;
    TextView tx1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        c1 = (CardView) findViewById(R.id.cd_1);
        tx1 = (TextView) findViewById(R.id.textView);
        tx1.getText().toString();
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), tx1.getText().toString(), Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("myText", tx1.getText().toString());

                startActivity(intent);
            }
        });
    }
}
