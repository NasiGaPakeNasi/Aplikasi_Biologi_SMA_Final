package com.example.aplikasi_biologi_sma_final;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Map;

public class LeaderboardActivity extends AppCompatActivity {

    private LinearLayout leaderboardContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        leaderboardContainer = findViewById(R.id.leaderboardContainer);

        loadLeaderboard();
    }

    private void loadLeaderboard() {
        SharedPreferences preferences = getSharedPreferences("Leaderboard", MODE_PRIVATE);
        Map<String, ?> allEntries = preferences.getAll();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String[] parts = entry.getValue().toString().split("\\|");
            String name = parts[0];
            String score = parts[1];
            String time = parts[2];

            TextView textView = new TextView(this);
            textView.setText(name + " - Skor: " + score + " - Waktu: " + time + " detik");
            leaderboardContainer.addView(textView);
        }
    }
}
