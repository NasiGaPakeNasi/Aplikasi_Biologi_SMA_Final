package com.example.aplikasi_biologi_sma_final;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
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
    private ImageView questionImageView;
    private ImageView celebrationImageView, encouragementImageView;
    private RadioGroup answersRadioGroup;
    private RadioButton answer1RadioButton, answer2RadioButton, answer3RadioButton, answer4RadioButton;
    private Button submitAnswerButton;
    private TextView scoreTextView;
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
        questionImageView = findViewById(R.id.questionImageView);
        celebrationImageView = findViewById(R.id.celebrationImageView);
        encouragementImageView = findViewById(R.id.encouragementImageView);
        answersRadioGroup = findViewById(R.id.answersRadioGroup);
        answer1RadioButton = findViewById(R.id.answer1RadioButton);
        answer2RadioButton = findViewById(R.id.answer2RadioButton);
        answer3RadioButton = findViewById(R.id.answer3RadioButton);
        answer4RadioButton = findViewById(R.id.answer4RadioButton);
        submitAnswerButton = findViewById(R.id.submitAnswerButton);
        scoreTextView = findViewById(R.id.scoreTextView);

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
            initializeQuiz();
            loadNewQuestion();
            startTime = System.currentTimeMillis();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.cancel();
            finish();
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
        Collections.shuffle(questionsList);
    }

    private void loadNewQuestion() {
        if (questionIndex < questionsList.size()) {
            Question currentQuestion = questionsList.get(questionIndex);

            questionTextView.setText(currentQuestion.getQuestion());
            questionImageView.setImageResource(currentQuestion.getImageResId());

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
        int selectedRadioButtonId = answersRadioGroup.getCheckedRadioButtonId();
        if (selectedRadioButtonId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
            String selectedAnswer = selectedRadioButton.getText().toString();
            Question currentQuestion = questionsList.get(questionIndex - 1);

            if (currentQuestion.getCorrectAnswer().equals(selectedAnswer)) {
                score++;
                Toast.makeText(this, "Jawaban benar! Skor: " + score, Toast.LENGTH_SHORT).show();
                scoreTextView.setText(String.valueOf(score));
            } else {
                Toast.makeText(this, "Jawaban salah", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Pilih jawaban terlebih dahulu", Toast.LENGTH_SHORT).show();
        }
    }

    private void endQuiz() {
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        String finalMessage = "Selamat " + playerName + "! Anda mendapatkan skor: " + score +
                "\nWaktu penyelesaian: " + (totalTime / 1000) + " detik";
        Toast.makeText(this, finalMessage, Toast.LENGTH_LONG).show();

        saveToLeaderboard(playerName, score, totalTime / 1000);

        submitAnswerButton.setEnabled(false);

        // Show celebration or encouragement based on the score
        if (score == questionsList.size()) {
            showCelebration();
        } else if (score == 0) {
            showEncouragement();
        } else {
            showPartialSuccess();
        }
    }

    private void showCelebration() {
        celebrationImageView.setImageResource(R.drawable.celebration_image);
        celebrationImageView.setVisibility(View.VISIBLE);
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.windah);
        mediaPlayer.start();
        // Hide celebration image and stop music after a delay
        new Handler().postDelayed(() -> {
            celebrationImageView.setVisibility(View.GONE);
            mediaPlayer.stop();
            mediaPlayer.release();
            returnToMainActivity();
        }, 60_000); // 5 seconds delay
    }

    private void showEncouragement() {
        encouragementImageView.setVisibility(View.VISIBLE);
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.kerjabagus);
        mediaPlayer.start();
        // Hide encouragement image and stop music after a delay
        new Handler().postDelayed(() -> {
            encouragementImageView.setVisibility(View.GONE);
            mediaPlayer.stop();
            mediaPlayer.release();
            returnToMainActivity();
        }, 5000); // 5 seconds delay
    }

    private void showPartialSuccess() {
        encouragementImageView.setImageResource(R.drawable.encouragement_image);
        encouragementImageView.setVisibility(View.VISIBLE);
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.tepuktangan);
        mediaPlayer.start();
        // Hide partial success image and stop music after a delay
        new Handler().postDelayed(() -> {
            encouragementImageView.setVisibility(View.GONE);
            mediaPlayer.stop();
            mediaPlayer.release();
            returnToMainActivity();
        }, 5000); // 5 seconds delay
    }

    private void returnToMainActivity() {
        Intent intent = new Intent(QuizFauna.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void saveToLeaderboard(String playerName, int score, long totalTime) {
        SharedPreferences preferences = getSharedPreferences("Leaderboard", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        long finishTime = System.currentTimeMillis();
        editor.putInt(playerName + "_score", score);
        editor.putLong(playerName + "_time", totalTime);
        editor.putLong(playerName + "_finishTime", finishTime);
        editor.apply();
    }



}
