package com.iyxan23.reflection.test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {

    public static final int OPEN_JAR_FILE_REQUEST_CODE = 1;

    DexClassLoader loader;
    Class<Object> loadedClass;

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

                loader = new DexClassLoader(uri.getPath(), getCodeCacheDir().getAbsolutePath(), null, this.getClass().getClassLoader());

                ((TextView) findViewById(R.id.path_textview)).setText(uri.getPath());
            }
        }
    }

    public void loadJar(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Insert classpath to load");

        EditText classpath_edit = new EditText(this);

        builder.setView(classpath_edit);
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.setPositiveButton("Ok", (dialog, which) -> {
            try {
                loadedClass = (Class<Object>) loader.loadClass(classpath_edit.getText().toString());

                showClass();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error: Class not found: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Shows the class's methods to the screen, all of that view binding stuff
    private void showClass() {
        if (loadedClass == null)
            return;

        ArrayList<Attribute> attributes = new ArrayList<>();

        Field[] fields = loadedClass.getFields();

        for (Field field : fields) {
            attributes.add(new Attribute(
                            field.getType().getSimpleName() + " " + field.getName(),
                            Attribute.Type.FIELD
                    )
            );
        }

        Method[] methods = loadedClass.getMethods();

        for (Method method : methods) {
            attributes.add(new Attribute(
                        method.getReturnType().getSimpleName() + " " + method.getName() + "()",
                        Attribute.Type.METHOD
                    )
            );
        }

        RecyclerView rv = findViewById(R.id.recyclerView);
        AttributeRecyclerViewAdapter adapter = new AttributeRecyclerViewAdapter(attributes);
        
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }
}