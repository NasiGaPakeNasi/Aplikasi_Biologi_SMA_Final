package com.example.aplikasi_biologi_sma_final;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import java.util.Map;

public class LeaderboardActivity extends AppCompatActivity {

    private LinearLayout leaderboardContainer;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        leaderboardContainer = findViewById(R.id.leaderboardContainer);
        Button backToMenuButton = findViewById(R.id.backToMenuButton);

        loadLeaderboard();

        backToMenuButton.setOnClickListener(v -> {
            Intent intent = new Intent(LeaderboardActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void loadLeaderboard() {
        SharedPreferences preferences = getSharedPreferences("Leaderboard", MODE_PRIVATE);
        Map<String, ?> allEntries = preferences.getAll();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String key = entry.getKey();
            if (key.endsWith("_score")) {
                String playerName = key.substring(0, key.length() - 6); // remove "_score" suffix
                int score = preferences.getInt(playerName + "_score", 0);
                long totalTime = preferences.getLong(playerName + "_time", 0);
                long finishTime = preferences.getLong(playerName + "_finishTime", 0);

                // Konversi finishTime ke jam selesai dalam format yang lebih mudah dibaca
                @SuppressLint("SimpleDateFormat") java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss");
                String finishTimeStr = sdf.format(new java.util.Date(finishTime));

                TextView leaderboardEntry = new TextView(this);
                leaderboardEntry.setText(playerName + ": Skor " + score + ", Waktu " + totalTime + " detik, Selesai pada " + finishTimeStr);
                leaderboardEntry.setTextSize(18);
                leaderboardEntry.setPadding(8, 8, 8, 8);
                leaderboardEntry.setTextColor(getResources().getColor(R.color.white, getTheme()));
                leaderboardEntry.setTypeface(ResourcesCompat.getFont(this, R.font.mondaysans));

                // Set border background
                leaderboardEntry.setBackgroundResource(R.drawable.border);

                leaderboardContainer.addView(leaderboardEntry);
            }
        }
    }



}
