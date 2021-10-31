package com.example.school.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.school.ModelClasses.ProfileModel;
import com.example.school.R;

import java.util.ArrayList;

public class FollowerAdapter extends RecyclerView.Adapter<FollowerAdapter.FollowerHolder> {
    private ArrayList<ProfileModel> followerModalArrayList;
    private Context context;

    public FollowerAdapter (ArrayList<ProfileModel> followerModalArrayList, Context context) {
        this.followerModalArrayList =followerModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public FollowerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.followerlayout, parent, false);
        return new FollowerAdapter.FollowerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowerHolder holder, int position) {
        ProfileModel modal = followerModalArrayList.get(position);
        holder.name.setText(modal.getName());
        holder.email.setText(modal.getEmail());
        holder.phone.setText(modal.getPhone());
    }

    @Override
    public int getItemCount() {
        return followerModalArrayList.size();
    }


    public class FollowerHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        private TextView name, email, phone;
        public FollowerHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            name = itemView.findViewById(R.id.followerName);
            email = itemView.findViewById(R.id.followerEmail);
            phone = itemView.findViewById(R.id.followerPhone);
        }
    }
}
