package com.example.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InteractiveTabAdapter extends RecyclerView.Adapter<InteractiveTabAdapter.GradesViewHolder> {
    private List<GradesModel> mGradesList;
    private LayoutInflater mPump;

    public InteractiveTabAdapter(Activity context, List<GradesModel> gradesList) {
        mPump = context.getLayoutInflater();
        this.mGradesList = gradesList;
    }

    @NonNull
    @Override
    public GradesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View wiersz = mPump.inflate(R.layout.ocena_layout, viewGroup, false);
        return new GradesViewHolder(wiersz);
    }

    @Override
    public void onBindViewHolder(@NonNull GradesViewHolder gradesViewHolder, int rowNumber) {
        GradesModel md = mGradesList.get(rowNumber);
        gradesViewHolder.nameTextView.setText(md.getName());

        switch (md.getGrade()) {
            case 2:
                gradesViewHolder.radioGroup.check(R.id.radioButton1);
                break;
            case 3:
                gradesViewHolder.radioGroup.check(R.id.radioButton2);
                break;
            case 4:
                gradesViewHolder.radioGroup.check(R.id.radioButton3);
                break;
            case 5:
                gradesViewHolder.radioGroup.check(R.id.radioButton4);
                break;
            default:
                gradesViewHolder.radioGroup.clearCheck();
        }
    }

    @Override
    public int getItemCount() {
        return mGradesList.size();
    }

    public class GradesViewHolder extends RecyclerView.ViewHolder implements RadioGroup.OnCheckedChangeListener {
        TextView nameTextView;
        RadioGroup radioGroup;
        RadioButton radioButton1, radioButton2, radioButton3, radioButton4;

        public GradesViewHolder(@NonNull View mainRowElement) {
            super(mainRowElement);
            nameTextView = mainRowElement.findViewById(R.id.et);
            radioGroup = mainRowElement.findViewById(R.id.radioGroup);
            radioButton1 = mainRowElement.findViewById(R.id.radioButton1);
            radioButton2 = mainRowElement.findViewById(R.id.radioButton2);
            radioButton3 = mainRowElement.findViewById(R.id.radioButton3);
            radioButton4 = mainRowElement.findViewById(R.id.radioButton4);

            radioGroup.setOnCheckedChangeListener(this);
        }

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            int grade = 0;
            if (checkedId == R.id.radioButton1) {
                grade = 2;
            } else if (checkedId == R.id.radioButton2) {
                grade = 3;
            } else if (checkedId == R.id.radioButton3) {
                grade = 4;
            } else if (checkedId == R.id.radioButton4) {
                grade = 5;
            }
            mGradesList.get(getAdapterPosition()).setGrade(grade);
        }

    }
}
