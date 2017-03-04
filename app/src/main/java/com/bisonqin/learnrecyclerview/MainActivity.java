package com.bisonqin.learnrecyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bisonqin.learnrecyclerview.ui.activity.GridActivity;
import com.bisonqin.learnrecyclerview.ui.activity.LineActivity;
import com.bisonqin.learnrecyclerview.ui.activity.LineActivity2;
import com.bisonqin.learnrecyclerview.ui.activity.StaggeredActivity;

/**
 * Created by Bison on 2017/3/3.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.line).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LineActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.line2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LineActivity2.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.grid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GridActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.staggered).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StaggeredActivity.class);
                startActivity(intent);
            }
        });


    }
}
