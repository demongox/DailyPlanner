package com.hirkanico.dailyplanner;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

public class DailyPlannerHolder extends RecyclerView.ViewHolder {

    public CheckBox taskIsDone;
    public TextView taskName;
    public TextView taskDate;
    public TextView taskTime;
    public ConstraintLayout mainLayout;
    View view;

    public DailyPlannerHolder(@NonNull View itemView) {
        super(itemView);
        taskIsDone = (CheckBox) itemView.findViewById(R.id.checkBoxDone);
        taskName = (TextView) itemView.findViewById(R.id.txtPlanTitle);
        taskDate = (TextView) itemView.findViewById(R.id.txtPlanDate);
        taskTime = (TextView) itemView.findViewById(R.id.txtPlanTime);
        mainLayout = (ConstraintLayout) itemView.findViewById(R.id.planRowLayout);
        view = itemView;

    }
}
