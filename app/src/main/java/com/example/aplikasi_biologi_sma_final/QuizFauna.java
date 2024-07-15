package com.example.aplikasi_biologi_sma_final;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizFauna extends AppCompatActivity {

    private TextView questionTextView;
    private ImageView questionImageView; // ImageView for question image
    private RadioGroup answersRadioGroup;
    private RadioButton answer1RadioButton, answer2RadioButton, answer3RadioButton, answer4RadioButton;
    private Button submitAnswerButton;
    private TextView highScoreTextView;
    private List<Question> questionsList;
    private int score;
    private int questionIndex = 0;
    private String playerName;
    private long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_fauna);

        questionTextView = findViewById(R.id.questionTextView);
        questionImageView = findViewById(R.id.questionImageView); // Initialize ImageView
        answersRadioGroup = findViewById(R.id.answersRadioGroup);
        answer1RadioButton = findViewById(R.id.answer1RadioButton);
        answer2RadioButton = findViewById(R.id.answer2RadioButton);
        answer3RadioButton = findViewById(R.id.answer3RadioButton);
        answer4RadioButton = findViewById(R.id.answer4RadioButton);
        submitAnswerButton = findViewById(R.id.submitAnswerButton);
        findViewById(R.id.scoreTextView);
        highScoreTextView = findViewById(R.id.highScoreTextView);

        showNameInputDialog();

        submitAnswerButton.setOnClickListener(v -> {
            if (answersRadioGroup.getCheckedRadioButtonId() == -1) {
                Toast.makeText(QuizFauna.this, "Pilih jawaban terlebih dahulu", Toast.LENGTH_SHORT).show();
            } else {
                checkAnswer();
                loadNewQuestion();
            }
        });
    }

    private void showNameInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Masukkan Nama Anda");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            playerName = input.getText().toString();
            initializeQuiz(); // Inisialisasi kuis setelah nama dimasukkan
            loadNewQuestion(); // Muat pertanyaan pertama
            startTime = System.currentTimeMillis(); // Catat waktu mulai
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.cancel();
            finish(); // Tutup aktivitas jika dibatalkan
        });

        builder.show();
    }

    private void initializeQuiz() {
        questionsList = new ArrayList<>();
        questionsList.add(new Question("Hewan apa yang memiliki kemampuan ekolokasi?",
                "Lumba-lumba", "Gajah", "Harimau", "Kelelawar", "Kelelawar", R.drawable.ekolokasi));

        questionsList.add(new Question("Apa yang dimaksud dengan hibernasi?",
                "Migrasi hewan", "Reproduksi hewan", "Keadaan tidak aktif pada hewan berdarah dingin selama musim dingin",
                "Adaptasi hewan terhadap lingkungan", "Keadaan tidak aktif pada hewan berdarah dingin selama musim dingin", R.drawable.hibernasi));

        questionsList.add(new Question("Jelaskan ciri-ciri unik dari platypus.",
                "Mamalia berkantung", "Memiliki bulu tebal", "Mamalia bertelur, memiliki kaki berselaput, dan memiliki paruh seperti bebek",
                "Bisa terbang", "Mamalia bertelur, memiliki kaki berselaput, dan memiliki paruh seperti bebek", R.drawable.platypus));

        questionsList.add(new Question("Apa yang dimaksud dengan simbiosis mutualisme?",
                "Hubungan interaksi antar makhluk hidup yang saling merugikan", "Hubungan interaksi antar makhluk hidup yang saling menguntungkan",
                "Hubungan interaksi antar makhluk hidup yang tidak saling mempengaruhi",
                "Hubungan interaksi antar makhluk hidup yang satu diuntungkan dan satu dirugikan",
                "Hubungan interaksi antar makhluk hidup yang saling menguntungkan", R.drawable.mutualisme));

        score = 0;
        questionIndex = 0;
        loadHighScore();
        Collections.shuffle(questionsList);
    }

    private void loadNewQuestion() {
        if (questionIndex < questionsList.size()) {
            Question currentQuestion = questionsList.get(questionIndex);

            questionTextView.setText(currentQuestion.getQuestion());
            questionImageView.setImageResource(currentQuestion.getImageResId()); // Set the image resource

            answer1RadioButton.setText(currentQuestion.getOption1());
            answer2RadioButton.setText(currentQuestion.getOption2());
            answer3RadioButton.setText(currentQuestion.getOption3());
            answer4RadioButton.setText(currentQuestion.getOption4());

            answersRadioGroup.clearCheck();
            questionIndex++;
        } else {
            endQuiz();
        }
    }

    private void checkAnswer() {
        RadioButton selectedRadioButton = findViewById(answersRadioGroup.getCheckedRadioButtonId());
        String selectedAnswer = selectedRadioButton.getText().toString();
        Question currentQuestion = questionsList.get(questionIndex - 1); // get current question

        if (currentQuestion.getCorrectAnswer().equals(selectedAnswer)) {
            score++;
        }
    }

    private void endQuiz() {
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        String finalMessage = "Selamat " + playerName + "! Anda mendapatkan skor: " + score +
                "\nWaktu penyelesaian: " + (totalTime / 1000) + " detik";
        Toast.makeText(this, finalMessage, Toast.LENGTH_LONG).show();

        saveToLeaderboard(playerName, score, totalTime / 1000); // Simpan ke leaderboard

        submitAnswerButton.setEnabled(false);
    }

    private void saveToLeaderboard(String name, int score, long time) {
        SharedPreferences preferences = getSharedPreferences("Leaderboard", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        // Format: "Nama|Skor|Waktu"
        String leaderboardEntry = name + "|" + score + "|" + time;
        editor.putString("leaderboard_" + System.currentTimeMillis(), leaderboardEntry);
        editor.apply();
    }

    @SuppressLint("SetTextI18n")
    private void loadHighScore() {
        SharedPreferences preferences = getSharedPreferences("QuizHighScore", MODE_PRIVATE);
        int highScore = preferences.getInt("highScore", 0);
        highScoreTextView.setText("High Score: " + highScore);
    }

    // Other methods like checking answers, updating score, etc.
}
