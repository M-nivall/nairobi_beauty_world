package com.example.Varsani.Clients.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Varsani.Clients.Models.ExamQuizModel;
import com.example.Varsani.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterExamQuiz extends RecyclerView.Adapter<AdapterExamQuiz.ExamQuizViewHolder> {

    private final Context context;
    private final List<ExamQuizModel> quizList;
    private final Map<Integer, String> selectedAnswers = new HashMap<>();

    public AdapterExamQuiz(Context context, List<ExamQuizModel> quizList) {
        this.context = context;
        this.quizList = quizList;
    }

    @NonNull
    @Override
    public ExamQuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_exam_quiz, parent, false);
        return new ExamQuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExamQuizViewHolder holder, int position) {
        ExamQuizModel quiz = quizList.get(position);

        holder.questionTextView.setText(quiz.getQuestion());
        holder.option1.setText(quiz.getMultiple_1());
        holder.option2.setText(quiz.getMultiple_2());

        holder.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedOption = holder.itemView.findViewById(checkedId);
            String selectedAnswer = selectedOption.getText().toString();
            selectedAnswers.put(quiz.getQuizId(), selectedAnswer);
        });
    }

    @Override
    public int getItemCount() {
        return quizList.size();
    }

    public Map<Integer, String> getSelectedAnswers() {
        return selectedAnswers;
    }

    static class ExamQuizViewHolder extends RecyclerView.ViewHolder {
        final TextView questionTextView;
        final RadioGroup radioGroup;
        final RadioButton option1;
        final RadioButton option2;

        ExamQuizViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.question_text);
            radioGroup = itemView.findViewById(R.id.radio_group);
            option1 = itemView.findViewById(R.id.option1);
            option2 = itemView.findViewById(R.id.option2);
        }
    }
}
