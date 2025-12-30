package com.example.Varsani.Staff.Trainer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.Varsani.R;
import com.example.Varsani.Staff.Trainer.Models.QuestionsListModel;

import java.util.List;

public class AdapterQuizList extends RecyclerView.Adapter<AdapterQuizList.OriginalViewHolder> {
    private List<QuestionsListModel> items;
    private Context ctx;
    private List<Integer> selectedQuestions;  // To keep track of selected questions

    public AdapterQuizList(Context context, List<QuestionsListModel> items, List<Integer> selectedQuestions) {
        this.items = items;
        this.ctx = context;
        this.selectedQuestions = selectedQuestions;
    }

    @Override
    public OriginalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item, parent, false);
        return new OriginalViewHolder(v);
    }

    @Override
    public void onBindViewHolder(OriginalViewHolder holder, final int position) {
        final QuestionsListModel data = items.get(position);

        // Set question text and multiple choices
        holder.textViewQuestion.setText(data.getQuestion());
        holder.textViewMultiple1.setText("Option 1: " + data.getMultiple_1());
        holder.textViewMultiple2.setText("Option 2: " + data.getMultiple_2());
        holder.textViewCorrectAnswer.setText("Correct Answer: " + data.getCorrectAnswer());

        // Handle the checkbox (select/unselect questions)
        holder.checkBoxSelect.setChecked(selectedQuestions.contains(data.getQuizId()));

        holder.checkBoxSelect.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Add the question to the selected list
                selectedQuestions.add(data.getQuizId());
            } else {
                // Remove from selected list
                selectedQuestions.remove((Integer) data.getQuizId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewQuestion, textViewMultiple1, textViewMultiple2, textViewCorrectAnswer;
        public CheckBox checkBoxSelect;

        public OriginalViewHolder(View v) {
            super(v);
            textViewQuestion = v.findViewById(R.id.textViewQuestion);
            textViewMultiple1 = v.findViewById(R.id.textViewMultiple1);
            textViewMultiple2 = v.findViewById(R.id.textViewMultiple2);
            textViewCorrectAnswer = v.findViewById(R.id.textViewCorrectAnswer);
            checkBoxSelect = v.findViewById(R.id.checkBoxSelect);
        }
    }
}
