package com.example.school;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class QuizFragment extends Fragment {

    public QuizFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        CardView cvStartQuiz = view.findViewById(R.id.cvStartQuiz);
        CardView cvRule = view.findViewById(R.id.cvRule);
        CardView cvHistory = view.findViewById(R.id.cvHistory);


        cvStartQuiz.setOnClickListener(view13 -> startActivity(new Intent(getContext(), QuizOptionActivity.class)));

        cvRule.setOnClickListener(view1 -> startActivity(new Intent(getContext(), RuleActivity.class)));

        cvHistory.setOnClickListener(view12 -> startActivity(new Intent(getContext(), HistoryActivity.class)));


        return view;
    }
}
