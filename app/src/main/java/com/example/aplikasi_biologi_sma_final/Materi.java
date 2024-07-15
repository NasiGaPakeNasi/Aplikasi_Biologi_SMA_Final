package com.example.aplikasi_biologi_sma_final;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Materi extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_materi);
    }

    //Ini adalah function untuk pindah activity
    public void Tombol_Tumbuhan (View view) {
        Intent intent = new Intent(Materi.this, Flora.class);
        startActivity(intent);
    }

    //Ini adalah function untuk pindah activity
    public void Tombol_Fauna (View view) {
        Intent intent = new Intent(Materi.this, Fauna.class);
        startActivity(intent);
    }

    //Ini adalah function untuk pindah activity
    public void Tombol_Kembali (View view) {
        Intent intent = new Intent(Materi.this, MainActivity.class);
        startActivity(intent);
    }
}