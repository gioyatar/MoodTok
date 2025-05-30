package com.example.moodtok;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private ArrayList<Task> tasks;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Task task);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description, deadline;

        public TaskViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewTitle);
            description = itemView.findViewById(R.id.textViewDescription);
            deadline = itemView.findViewById(R.id.textViewDeadline);
        }

        public void bind(Task task, OnItemClickListener listener) {
            itemView.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onItemClick(task);
                }
            });
        }
    }

    public TaskAdapter(ArrayList<Task> taskList) {
        this.tasks = taskList;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.todoitem, parent, false);
        return new TaskViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task current = tasks.get(position);
        holder.title.setText(current.getTitle());
        holder.description.setText(current.getDescription());
        holder.deadline.setText(current.getDeadline());
        holder.bind(current, listener);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
