package com.example.aplikasi_biologi_sma_final;
//import android.annotation.SuppressLint;
import android.content.Intent;
//import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
//import android.widget.Switch;

import androidx.appcompat.app.AppCompatDelegate;



public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private final int[] images = {R.drawable.angiospermaebg, R.drawable.thallophytabg, R.drawable.jenislumutbg, R.drawable.spora, R.drawable.background_pohon};
    private int currentIndex = 0;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final int ANIMATION_DURATION = 1000; // Durasi animasi dalam milidetik
    private final int IMAGE_DISPLAY_DURATION = 5000; // Waktu tunggu sebelum gambar berganti

    //Ini adalah Bagian Untuk Layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main); //Kita bekerja di Layout ini

 //       AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);


//        // Mengatur night mode berdasarkan preferensi pengguna
//        SharedPreferences preferences = getSharedPreferences("Settings", MODE_PRIVATE);
//        boolean isNightMode = preferences.getBoolean("NightMode", false);
//        if (isNightMode) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//        } else {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        }
//
//        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch nightModeSwitch = findViewById(R.id.nightModeSwitch);
//        nightModeSwitch.setChecked(isNightMode);
//        nightModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            SharedPreferences.Editor editor = preferences.edit();
//            editor.putBoolean("NightMode", isChecked);
//            editor.apply();
//            AppCompatDelegate.setDefaultNightMode(isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
//        });

        imageView = findViewById(R.id.imageView);

        // Memulai siklus pergantian gambar
        startImageSwitching();

    //Exit Button
        Button exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(v -> {
            finishAffinity(); //Menutup semua aktivitas dan keluar dari aplikasi
        });


    }
    private void startImageSwitching() {
        Runnable imageSwitcher = new Runnable() {
            @Override
            public void run() {
                // Mengatur gambar berikutnya
                currentIndex = (currentIndex + 1) % images.length;
                int nextImage = images[currentIndex];

                // Animasi fade out
                Animation fadeOut = new AlphaAnimation(1, 0);
                fadeOut.setDuration(ANIMATION_DURATION);
                fadeOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // Mengubah gambar setelah animasi fade out selesai
                        imageView.setImageResource(nextImage);

                        // Animasi fade in
                        Animation fadeIn = new AlphaAnimation(0, 1);
                        fadeIn.setDuration(ANIMATION_DURATION);
                        imageView.startAnimation(fadeIn);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });

                imageView.startAnimation(fadeOut);

                // Mengatur ulang handler untuk menjalankan runnable lagi setelah IMAGE_DISPLAY_DURATION
                handler.postDelayed(this, IMAGE_DISPLAY_DURATION + ANIMATION_DURATION);
            }
        };

        // Menjalankan runnable untuk pertama kalinya
        handler.post(imageSwitcher);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Menghentikan handler ketika activity dihancurkan
        handler.removeCallbacksAndMessages(null);
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

    //Ini adalah function untuk pindah activity
    public void Tombol_Papan (View view) {
        Intent intent = new Intent(MainActivity.this, LeaderboardActivity.class);
        startActivity(intent);
    }





}

