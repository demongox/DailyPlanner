package com.hirkanico.dailyplanner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hirkanico.dailyplanner.classes.ClickListener;
import com.hirkanico.dailyplanner.classes.ClickListenerForEditDelete;
import com.hirkanico.dailyplanner.classes.DailyTask;

import java.util.ArrayList;

public class AllPlanAdapter extends RecyclerView.Adapter<AllPlanHolder> {

    int rowIndex = -1;

    ArrayList<DailyTask> taskNameList;
    ClickListenerForEditDelete listener;
    Context context;

    public AllPlanAdapter(Context context, ArrayList<DailyTask> taskNameList, ClickListenerForEditDelete listener) {

        this.taskNameList = taskNameList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AllPlanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the layout
        View taskView = inflater.inflate(R.layout.view_all_plan_recycle_view, parent, false);

        return new AllPlanHolder(taskView);
    }

    @Override
    public void onBindViewHolder(@NonNull AllPlanHolder holder, @SuppressLint("RecyclerView") int position) {
        final int index = holder.getAdapterPosition();

        holder.taskName.setText(taskNameList.get(position).taskName);
        holder.taskTime.setText(taskNameList.get(position).taskTime);
        holder.txtPlanDuration.setText(taskNameList.get(position).planDuration);
        holder.taskDate.setText(taskNameList.get(position).taskDate);
        holder.imgDeleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rowIndex = position;
                listener.click(index, "delete");
            }
        });

        holder.imgEditIcon.setOnClickListener(view -> {
            rowIndex = position;
            listener.click(index, "edit");
        });
/*
        if(rowIndex == position)
            holder.mainLayout.setBackgroundColor(Color.parseColor("#D6EEEE"));
        else
            holder.mainLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));

 */
    }

    @Override
    public int getItemCount() {
        return taskNameList.size();
        //return 0;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
