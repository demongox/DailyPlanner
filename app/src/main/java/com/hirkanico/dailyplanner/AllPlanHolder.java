package com.hirkanico.dailyplanner;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class AllPlanHolder extends RecyclerView.ViewHolder {

    public ImageView imgIcon;
    public TextView taskName;
    public TextView taskDate;
    public TextView taskTime;
    public TextView txtPlanDuration;
    public ConstraintLayout mainLayout;
    View view;

    public AllPlanHolder(@NonNull View itemView) {
        super(itemView);
        imgIcon = (ImageView) itemView.findViewById(R.id.imgIcon);
        taskName = (TextView) itemView.findViewById(R.id.txtPlanTitle);
        taskDate = (TextView) itemView.findViewById(R.id.txtPlanDate);
        taskTime = (TextView) itemView.findViewById(R.id.txtPlanTime);
        txtPlanDuration = (TextView) itemView.findViewById(R.id.txtPlanDuration);
        mainLayout = (ConstraintLayout) itemView.findViewById(R.id.planRowLayout);
        view = itemView;

    }
}
