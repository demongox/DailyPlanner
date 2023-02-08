package com.hirkanico.dailyplanner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
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

        View taskView = inflater.inflate(R.layout.view_daily_plane_recycle_view, parent, false);

        return new DailyPlannerHolder(taskView);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyPlannerHolder holder, @SuppressLint("RecyclerView") int position) {
        final int index = holder.getAdapterPosition();
        Log.v("position",taskNameList.get(position).taskName);
        holder.taskName.setText(taskNameList.get(position).taskName);
        holder.taskDate.setText(taskNameList.get(position).taskDate);
        holder.taskDate.setText(taskNameList.get(position).taskTime);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rowIndex = position;
                notifyDataSetChanged();
                listener.click(index);
            }
        });

        if(rowIndex == position)
            holder.mainLayout.setBackgroundColor(Color.parseColor("#DC746C"));
        else
            holder.mainLayout.setBackgroundColor(Color.parseColor("#E49B83"));
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
