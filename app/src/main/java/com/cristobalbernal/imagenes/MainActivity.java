package com.cristobalbernal.imagenes;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityResultLauncher<PickVisualMediaRequest> pickImage;

    private ImageView img;
    private ListView listView;
    private TextView cantidad;
    private List<String> base = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btN = findViewById(R.id.btn);
        cantidad = findViewById(R.id.cantidad);
        listView = findViewById(R.id.list);
        img = findViewById(R.id.imageView);
        pickImage = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            if (uri != null) {
                try {
                    String base64 = getBase64FromUri(uri);
                    cantidad.setText(String.valueOf(base64.length()));
                    for (int i = 0; i <base64.length() ; i++) {
                        base.add(base64);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,base);
                    listView.setAdapter(adapter);
                    img.setImageBitmap(EncodingImg.decode(base64));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        btN.setOnClickListener(v -> {
            pickImage.launch(new PickVisualMediaRequest());
        });
    }
    //Converti la imagen en Base64 para el servidor!!!!!
    private String getBase64FromUri(Uri uri) throws IOException {
        @SuppressLint("Recycle") InputStream inputStream = getContentResolver().openInputStream(uri);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[100000];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }

        byte[] imageBytes = outputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }
}