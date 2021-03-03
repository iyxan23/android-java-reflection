package com.iyxan23.reflection.test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final int OPEN_JAR_FILE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void pickJar(View view) {
        Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        i   .addCategory(Intent.CATEGORY_OPENABLE)
            .setType("application/java-archive");

        startActivityForResult(i, OPEN_JAR_FILE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OPEN_JAR_FILE_REQUEST_CODE) {
            if (data != null) {
                Uri uri = data.getData();

                // TODO
            }
        }
    }
}