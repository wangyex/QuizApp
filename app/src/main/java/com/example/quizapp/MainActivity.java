package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.quizapp.databinding.ActivityMainBinding;
import com.example.quizapp.model.Question;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private int currentQuestionIndex = 0;
    private Button translateToGuess;
    //create question bank array
    private Question[] questionBank = new Question[]{
            //create/instantiate question objects
            new Question(R.string.question_amendments,false),
            new Question(R.string.question_constitution,true),
            new Question(R.string.question_declaration,true),
            new Question(R.string.question_independence_rights,true),
            new Question(R.string.question_religion,true),
            new Question(R.string.question_government,false),
            new Question(R.string.question_government_feds,false),
            new Question(R.string.question_government_senators,true),


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);

        binding.questionTextView.setText(questionBank[currentQuestionIndex].getAnswerResId());

        translateToGuess = findViewById(R.id.button_nxtview);
        translateToGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShowGuess.class);
                startActivity(intent);
                intent.putExtra("guess","Hello there!");


            }
        });


        binding.nextButton.setOnClickListener(view -> {

            currentQuestionIndex = (currentQuestionIndex + 1) % questionBank.length;
            updateQuestion();


        });
        binding.prevButton.setOnClickListener(view -> {
            if (currentQuestionIndex > 0) {
            currentQuestionIndex = (currentQuestionIndex - 1) % questionBank.length;
            updateQuestion();}


        });

        binding.trueButton.setOnClickListener(view ->
        {
            checkAnswer(true);

        });
        binding.falseButton.setOnClickListener(view ->
        {
            checkAnswer(false);
        });

    }

    private void checkAnswer(boolean userChoseCorrect) {
        boolean answerIsCorrect = questionBank[currentQuestionIndex].isAnswerTrue();

        int messageId;
        if (answerIsCorrect == userChoseCorrect) {
            messageId = R.string.correct_answer;
        }else{
            messageId = R.string.wrong_answer;
        }

        Snackbar.make(binding.imageView,messageId,Snackbar.LENGTH_SHORT).show();

    }


    private void updateQuestion() {
        binding.questionTextView.setText(questionBank[currentQuestionIndex].getAnswerResId());

    }
}