package com.example.aplikasi_biologi_sma_final;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class QuizFlora extends AppCompatActivity {

    private TextView questionTextView;
    private RadioGroup optionsRadioGroup;
    private Button submitButton;
    private TextView scoreTextView;

    private List<Question> questionList;
    private int currentQuestionIndex;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_flora);

        questionTextView = findViewById(R.id.questionTextView);
        optionsRadioGroup = findViewById(R.id.optionsRadioGroup);
        submitButton = findViewById(R.id.submitButton);
        scoreTextView = findViewById(R.id.scoreTextView);

        questionList = createQuestionList();
        currentQuestionIndex = 0;
        score = 0;

        loadNextQuestion();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });
    }

    private List<Question> createQuestionList() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Apa itu fotosintesis?", new String[]{"Proses respirasi", "Proses memasak", "Proses penyerapan air", "Proses pembuatan makanan pada tumbuhan"}, 3));
        questions.add(new Question("Apa fungsi mitokondria?", new String[]{"Menghasilkan energi", "Mengatur osmosis", "Menyimpan cadangan makanan", "Sintesis protein"}, 0));
        questions.add(new Question("Bagian dari sel yang mengandung DNA adalah?", new String[]{"Mitokondria", "Inti sel", "Membran sel", "Sitoplasma"}, 1));
        questions.add(new Question("Organ yang bertanggung jawab untuk memompa darah adalah?", new String[]{"Hati", "Ginjal", "Paru-paru", "Jantung"}, 3));
        return questions;
    }

    private void loadNextQuestion() {
        if (currentQuestionIndex < questionList.size()) {
            Question currentQuestion = questionList.get(currentQuestionIndex);
            questionTextView.setText(currentQuestion.getQuestion());
            ((RadioButton) optionsRadioGroup.getChildAt(0)).setText(currentQuestion.getOptions()[0]);
            ((RadioButton) optionsRadioGroup.getChildAt(1)).setText(currentQuestion.getOptions()[1]);
            ((RadioButton) optionsRadioGroup.getChildAt(2)).setText(currentQuestion.getOptions()[2]);
            ((RadioButton) optionsRadioGroup.getChildAt(3)).setText(currentQuestion.getOptions()[3]);
            optionsRadioGroup.clearCheck();
        } else {
            Toast.makeText(this, "Kuis selesai! Skor Anda: " + score, Toast.LENGTH_LONG).show();
            submitButton.setEnabled(false);
        }
    }

    private void checkAnswer() {
        int selectedOptionIndex = optionsRadioGroup.indexOfChild(findViewById(optionsRadioGroup.getCheckedRadioButtonId()));
        if (selectedOptionIndex == questionList.get(currentQuestionIndex).getCorrectAnswerIndex()) {
            score++;
        }
        scoreTextView.setText("Score: " + score);
        currentQuestionIndex++;
        loadNextQuestion();
    }
}
