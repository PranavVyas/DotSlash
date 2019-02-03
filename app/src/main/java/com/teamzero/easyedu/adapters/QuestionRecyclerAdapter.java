package com.teamzero.easyedu.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.teamzero.easyedu.R;
import com.teamzero.easyedu.models.QuestionModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class QuestionRecyclerAdapter extends RecyclerView.Adapter<QuestionRecyclerAdapter.QuestionHolder> {

    private List<QuestionModel> questions;
    private List<Boolean> marksBoolean = new ArrayList<>();

    @NonNull
    @Override
    public QuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new QuestionHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_holder_questions, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return (questions == null) ? 0 : questions.size();
    }

    public void setQuestions(List<QuestionModel> questions) {
        this.questions = questions;
        notifyDataSetChanged();
    }

    class QuestionHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_que_no)
        TextView tvNo;
        @BindView(R.id.tv_que_name)
        TextView tvName;

        public QuestionHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnCheckedChanged(R.id.group)
        void onOptionsChanged(CompoundButton button, boolean checked) {
            int id = button.getId();
            switch (id) {
                case R.id.btn_1:
                    if (questions.get(getAdapterPosition()).getOp1().equals(questions.get(getAdapterPosition()).getCorrect())) {
                        marksBoolean.set(getAdapterPosition(), true);
                    } else {
                        marksBoolean.set(getAdapterPosition(), false);
                    }
                    break;

                case R.id.btn_2:
                    if (questions.get(getAdapterPosition()).getOp1().equals(questions.get(getAdapterPosition()).getCorrect())) {
                        marksBoolean.set(getAdapterPosition(), true);
                    } else {
                        marksBoolean.set(getAdapterPosition(), false);
                    }
                    break;

                case R.id.btn_3:
                    if (questions.get(getAdapterPosition()).getOp1().equals(questions.get(getAdapterPosition()).getCorrect())) {
                        marksBoolean.set(getAdapterPosition(), true);
                    } else {
                        marksBoolean.set(getAdapterPosition(), false);
                    }
                    break;

                case R.id.btn_4:
                    if (questions.get(getAdapterPosition()).getOp1().equals(questions.get(getAdapterPosition()).getCorrect())) {
                        marksBoolean.set(getAdapterPosition(), true);
                    } else {
                        marksBoolean.set(getAdapterPosition(), false);
                    }
                    break;
            }
        }
    }

}
