package com.hirkanico.dailyplanner;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class DailyPlannerHolder extends RecyclerView.ViewHolder {

    public ImageView taskIsDone;
    public TextView taskName;
    //public TextView taskDate;
    public TextView taskTime;
    public TextView txtPlanDuration;
    public ImageView imgPlanPriority;
    public ConstraintLayout mainLayout;
    View view;

    public DailyPlannerHolder(@NonNull View itemView) {
        super(itemView);
        taskIsDone = (ImageView) itemView.findViewById(R.id.imgIcon);
        taskName = (TextView) itemView.findViewById(R.id.txtPlanTitle);
        //taskDate = (TextView) itemView.findViewById(R.id.txtPlanDate);
        taskTime = (TextView) itemView.findViewById(R.id.txtPlanTime);
        txtPlanDuration = (TextView) itemView.findViewById(R.id.txtPlanDuration);
        imgPlanPriority = (ImageView) itemView.findViewById(R.id.imgPlanPriority);
        mainLayout = (ConstraintLayout) itemView.findViewById(R.id.planRowLayout);
        view = itemView;

    }
}
