package com.hirkanico.dailyplanner;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class AllPlanHolder extends RecyclerView.ViewHolder {

    public ImageView imgEditIcon;
    public ImageView imgDeleteIcon;
    public ImageView imgPlanPriority;
    public TextView taskName;
    public TextView taskDate;
    public TextView taskTime;
    public TextView txtPlanDuration;
    public ConstraintLayout mainLayout;
    View view;

    public AllPlanHolder(@NonNull View itemView) {
        super(itemView);
        imgEditIcon = (ImageView) itemView.findViewById(R.id.imgEditIcon);
        imgDeleteIcon = (ImageView) itemView.findViewById(R.id.imgDeleteIcon);
        imgPlanPriority = (ImageView) itemView.findViewById(R.id.imgPlanPriority);
        taskName = (TextView) itemView.findViewById(R.id.txtPlanName);
        taskDate = (TextView) itemView.findViewById(R.id.txtPlanDate);
        taskTime = (TextView) itemView.findViewById(R.id.txtPlanTime);
        txtPlanDuration = (TextView) itemView.findViewById(R.id.txtPlanDuration);
        mainLayout = (ConstraintLayout) itemView.findViewById(R.id.planRowLayout);
        view = itemView;

    }
}
