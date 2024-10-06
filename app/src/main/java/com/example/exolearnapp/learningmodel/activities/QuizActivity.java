package com.example.exolearnapp.learningmodel.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.exolearnapp.learningmodel.model.Module;
import com.example.exolearnapp.learningmodel.model.QuizQuestion;
import com.example.exolearnapp.R;
import com.example.exolearnapp.utils.FirebaseUtils;

import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private Module module;
    private List<QuizQuestion> quizQuestions;
    private int currentQuestionIndex = 0;

    private TextView questionText;
    private Button optionA, optionB, optionC, optionD, submitButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Retrieve the module and quiz questions passed from LessonActivity
        if (getIntent().getSerializableExtra("module") != null) {
            module = (Module) getIntent().getSerializableExtra("module");
            quizQuestions = module.getQuizQuestions();
        }

        // Initialize UI components
        questionText = findViewById(R.id.question_text);
        optionA = findViewById(R.id.option_a);
        optionB = findViewById(R.id.option_b);
        optionC = findViewById(R.id.option_c);
        optionD = findViewById(R.id.option_d);
        submitButton = findViewById(R.id.submit_button);

        // Load the first question
        loadQuestion(currentQuestionIndex);

        // Set up the submit button click listener
        submitButton.setOnClickListener(v -> {
            // Check the selected answer
            checkAnswer();
        });
    }

    /**
     * Load a question based on the current question index.
     */
    private void loadQuestion(int index) {
        if (index < quizQuestions.size()) {
            QuizQuestion question = quizQuestions.get(index);
            questionText.setText(question.getQuestionText());
            optionA.setText(question.getOptions().get(0));
            optionB.setText(question.getOptions().get(1));
            optionC.setText(question.getOptions().get(2));
            optionD.setText(question.getOptions().get(3));
        } else {
            // Quiz is finished
            finishQuiz();
        }
    }

    /**
     * Check the selected answer and navigate to the next question.
     */
    private void checkAnswer() {
        // Logic to check selected answer (for now, we can log it)
        String selectedAnswer = ""; // Get selected answer from buttons (not shown)

        if (selectedAnswer.equals(quizQuestions.get(currentQuestionIndex).getCorrectAnswer())) {
            Log.d(TAG, "Correct answer!");
        } else {
            Log.d(TAG, "Incorrect answer.");
        }

        // Move to the next question
        currentQuestionIndex++;
        loadQuestion(currentQuestionIndex);
    }

    /**
     * Finish the quiz and save the results.
     */
    private void finishQuiz() {
        // Mark quiz as completed
        module.setQuizCompleted(true);
        FirebaseUtils.getInstance().saveModuleToFirestore(module); // Save to Firestore

        // Log completion
        Log.d(TAG, "Quiz completed for module: " + module.getModuleId());
        // Optionally, navigate back to RoadmapActivity or show results
    }
}

