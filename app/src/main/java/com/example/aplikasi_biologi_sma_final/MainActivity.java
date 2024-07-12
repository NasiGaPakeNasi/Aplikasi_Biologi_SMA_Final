package com.example.aplikasi_biologi_sma_final;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;

public class MainActivity extends AppCompatActivity {

    //Ini adalah Bagian Untuk Layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main); //Kita bekerja di Layout ini

    //Exit Button
        Button exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(v -> {
            finishAffinity(); //Menutup semua aktivitas dan keluar dari aplikasi
        });
    }

    //Ini adalah Bagian untuk Activity
    //Ini adalah function untuk pindah activity
    public void Tombol_Mulai(View view) {
        Intent intent = new Intent(MainActivity.this, Materi.class);
        startActivity(intent);
    }

    //Ini adalah function untuk pindah activity
    public void Tombol_About(View view) {
        Intent intent = new Intent(MainActivity.this, About.class);
        startActivity(intent);
    }

}

