package com.example.aplikasi_biologi_sma_final;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private final int[] defaultImages = {
            R.drawable.angiospermaebg, R.drawable.thallophytabg,
            R.drawable.jenislumutbg, R.drawable.spora,
            R.drawable.background_pohon
    };
    private ArrayList<Bitmap> customImages = new ArrayList<>();
    private int currentIndex = 0;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final int ANIMATION_DURATION = 1000;
    private final int IMAGE_DISPLAY_DURATION = 5000;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        startImageSwitching();

        Button exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(v -> {
            finishAffinity();
        });

        Button addImageButton = findViewById(R.id.addImageButton);
        addImageButton.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), PICK_IMAGE_REQUEST);
        });
    }

    private void startImageSwitching() {
        Runnable imageSwitcher = new Runnable() {
            @Override
            public void run() {
                currentIndex = (currentIndex + 1) % (defaultImages.length + customImages.size());
                if (currentIndex < defaultImages.length) {
                    int nextImage = defaultImages[currentIndex];
                    animateImageSwitch(nextImage, null);
                } else {
                    int customImageIndex = currentIndex - defaultImages.length;
                    Bitmap nextImage = customImages.get(customImageIndex);
                    animateImageSwitch(0, nextImage);
                }
                handler.postDelayed(this, IMAGE_DISPLAY_DURATION + ANIMATION_DURATION);
            }
        };
        handler.post(imageSwitcher);
    }

    private void animateImageSwitch(int resId, Bitmap bitmap) {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setDuration(ANIMATION_DURATION);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                if (bitmap == null) {
                    imageView.setImageResource(resId);
                } else {
                    imageView.setImageBitmap(bitmap);
                }
                Animation fadeIn = new AlphaAnimation(0, 1);
                fadeIn.setDuration(ANIMATION_DURATION);
                imageView.startAnimation(fadeIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        imageView.startAnimation(fadeOut);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    public void Tombol_Mulai(View view) {
        Intent intent = new Intent(MainActivity.this, Materi.class);
        startActivity(intent);
    }

    public void Tombol_About(View view) {
        Intent intent = new Intent(MainActivity.this, About.class);
        startActivity(intent);
    }

    public void Tombol_Papan(View view) {
        Intent intent = new Intent(MainActivity.this, LeaderboardActivity.class);
        startActivity(intent);
    }

    public void Tombol_ApaYangBaru(View view) {
        Intent intent = new Intent(MainActivity.this, ApaYangBaru.class);
        startActivity(intent);
    }

    public void Tombol_AddImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                customImages.add(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
