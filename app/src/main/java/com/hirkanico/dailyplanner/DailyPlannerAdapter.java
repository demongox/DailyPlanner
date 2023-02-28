package com.hirkanico.dailyplanner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hirkanico.dailyplanner.classes.ClickListener;
import com.hirkanico.dailyplanner.classes.DailyTask;

import java.util.ArrayList;
public class DailyPlannerAdapter extends RecyclerView.Adapter<DailyPlannerHolder> {

    int rowIndex = -1;

    ArrayList<DailyTask> taskNameList;
    ClickListener listener;
    Context context;

    public DailyPlannerAdapter(Context context, ArrayList<DailyTask> taskNameList, ClickListener listener) {

        this.taskNameList = taskNameList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DailyPlannerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the layout

        View taskView = inflater.inflate(R.layout.view_daily_plan_recycle_view, parent, false);

        return new DailyPlannerHolder(taskView);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyPlannerHolder holder, @SuppressLint("RecyclerView") int position) {
        final int index = holder.getAdapterPosition();

        //Log.v("taskName",taskNameList.get(position).taskName);
        //Log.v("taskDate",taskNameList.get(position).taskDate);
        //Log.v("taskTime",taskNameList.get(position).taskTime);
        //Log.v("txtPlanDuration",taskNameList.get(position).planDuration);

        holder.taskName.setText(taskNameList.get(position).taskName);
        //holder.taskDate.setText(taskNameList.get(position).taskDate);
        holder.taskTime.setText(taskNameList.get(position).taskTime);
        holder.txtPlanDuration.setText(taskNameList.get(position).planDuration);

        switch (taskNameList.get(position).planPriority){
            case "low":
                holder.imgPlanPriority.setImageResource(R.drawable.low_priority);
                break;
            case "medium":
                holder.imgPlanPriority.setImageResource(R.drawable.medium_priority);
                break;
            case "high":
                holder.imgPlanPriority.setImageResource(R.drawable.high_priority);
                break;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rowIndex = position;
                notifyDataSetChanged();
                listener.click(index);
            }
        });

        if (taskNameList.get(position).isDone.equals("true"))
            holder.taskIsDone.setImageResource(R.drawable.tick_icon_for_inside);

        if(rowIndex == position)
            holder.mainLayout.setBackgroundColor(Color.parseColor("#D6EEEE"));
        else
            holder.mainLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
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
