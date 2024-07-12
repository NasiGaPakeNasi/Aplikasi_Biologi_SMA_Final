package com.example.aplikasi_biologi_sma_final;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizFlora extends AppCompatActivity {

    private TextView questionTextView;
    private RadioGroup answersRadioGroup;
    private RadioButton answer1RadioButton, answer2RadioButton, answer3RadioButton, answer4RadioButton;
    private Button submitAnswerButton;
    private TextView scoreTextView, highScoreTextView;
    private List<Question> questionsList;
    private Question currentQuestion;
    private int score;
    private int highScore;
    private int questionIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_flora);

        questionTextView = findViewById(R.id.questionTextView);
        answersRadioGroup = findViewById(R.id.answersRadioGroup);
        answer1RadioButton = findViewById(R.id.answer1RadioButton);
        answer2RadioButton = findViewById(R.id.answer2RadioButton);
        answer3RadioButton = findViewById(R.id.answer3RadioButton);
        answer4RadioButton = findViewById(R.id.answer4RadioButton);
        submitAnswerButton = findViewById(R.id.submitAnswerButton);
        scoreTextView = findViewById(R.id.scoreTextView);
        highScoreTextView = findViewById(R.id.highScoreTextView);

        // Inisialisasi kuis
        initializeQuiz();

        // Muat pertanyaan pertama
        loadNewQuestion();

        // Event Listener untuk tombol submit
        submitAnswerButton.setOnClickListener(v -> checkAnswer());
    }

    private void initializeQuiz() {
        questionsList = new ArrayList<>();

        questionsList.add(new Question("Apa fungsi klorofil dalam fotosintesis?",
                "Menyerap cahaya matahari",
                "Mengangkut air",
                "Menyerap karbon dioksida",
                "Menghasilkan oksigen",
                "Menyerap cahaya matahari"));

        questionsList.add(new Question("Apa yang dimaksud dengan mitosis?",
                "Pembelahan sel somatik",
                "Pembelahan sel gamet",
                "Pembelahan inti sel",
                "Pembentukan RNA",
                "Pembelahan sel somatik"));

        questionsList.add(new Question("Apa yang dimaksud dengan ATP?",
                "Molekul penyimpan energi",
                "Asam nukleat",
                "Protein struktural",
                "Enzim pencernaan",
                "Molekul penyimpan energi"));

        questionsList.add(new Question("Dimana proses respirasi sel terjadi?",
                "Mitokondria",
                "Ribosom",
                "Nukleus",
                "Kloroplas",
                "Mitokondria"));

        score = 0;
        questionIndex = 0;
        loadHighScore();
        Collections.shuffle(questionsList);
    }

    private void loadNewQuestion() {
        if (questionIndex < questionsList.size()) {
            currentQuestion = questionsList.get(questionIndex);

            questionTextView.setText(currentQuestion.getQuestion());
            List<String> answers = new ArrayList<>();
            answers.add(currentQuestion.getOption1());
            answers.add(currentQuestion.getOption2());
            answers.add(currentQuestion.getOption3());
            answers.add(currentQuestion.getOption4());
            Collections.shuffle(answers);

            answer1RadioButton.setText(answers.get(0));
            answer2RadioButton.setText(answers.get(1));
            answer3RadioButton.setText(answers.get(2));
            answer4RadioButton.setText(answers.get(3));

            answersRadioGroup.clearCheck();
        } else {
            endQuiz();
        }
    }

    @SuppressLint("SetTextI18n")
    private void checkAnswer() {
        int selectedId = answersRadioGroup.getCheckedRadioButtonId();

        if (selectedId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedId);
            String selectedAnswer = selectedRadioButton.getText().toString();

            if (selectedAnswer.equals(currentQuestion.getCorrectAnswer())) {
                score++;
                scoreTextView.setText("Score: " + score);
                if (score > highScore) {
                    highScore = score;
                    saveHighScore();
                }
            }

            questionIndex++;
            loadNewQuestion();
        } else {
            Toast.makeText(this, "Silakan pilih jawaban terlebih dahulu", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void saveHighScore() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("highScore", highScore);
        editor.apply();
        highScoreTextView.setText("High Score: " + highScore);
    }

    @SuppressLint("SetTextI18n")
    private void loadHighScore() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        highScore = preferences.getInt("highScore", 0);
        highScoreTextView.setText("High Score: " + highScore);
    }

    private void endQuiz() {
        Toast.makeText(this, "Kuis selesai! Skor akhir: " + score, Toast.LENGTH_LONG).show();
        submitAnswerButton.setEnabled(false);
    }
}