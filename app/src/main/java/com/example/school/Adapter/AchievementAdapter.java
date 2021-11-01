package com.example.school.Adapter;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.school.ModelClasses.Achievement;
import com.example.school.R;

import java.util.List;

public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.AchievementViewHolder> {

    private final List<Achievement> achievementList;

    public AchievementAdapter(List<Achievement> achievementList) {
        this.achievementList = achievementList;
    }

    @NonNull
    @Override
    public AchievementAdapter.AchievementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_achievement, parent, false);
        return new AchievementAdapter.AchievementViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull AchievementAdapter.AchievementViewHolder holder, int position) {

        Achievement item = achievementList.get(position);

        holder.tvEmail.setText(String.valueOf(item.getEmail()));
        holder.tvPoints.setText(String.valueOf(item.getPoints()));


    }

    @Override
    public int getItemCount() {
        return achievementList.size();
    }

    public static class AchievementViewHolder extends RecyclerView.ViewHolder {

        public TextView tvEmail, tvPoints;


        public AchievementViewHolder(View v) {
            super(v);
            tvEmail = v.findViewById(R.id.tvEmail);
            tvPoints = v.findViewById(R.id.tvPoints);


        }
    }

}