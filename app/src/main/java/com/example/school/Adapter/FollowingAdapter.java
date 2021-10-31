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

public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.FollowingHolder>
{
    private ArrayList<ProfileModel> followingModalArrayList;
    private Context context;

    public FollowingAdapter (ArrayList<ProfileModel> followingModalArrayList, Context context) {
        this.followingModalArrayList = followingModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public FollowingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.followinglayout, parent, false);
        return new FollowingAdapter.FollowingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowingHolder holder, int position) {
        ProfileModel modal = followingModalArrayList.get(position);
        holder.name.setText(modal.getName());
        holder.email.setText(modal.getEmail());
        holder.phone.setText(modal.getPhone());
    }

    @Override
    public int getItemCount() {
        return followingModalArrayList.size();
    }

    public class FollowingHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        private TextView name, email, phone;
        public FollowingHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            name = itemView.findViewById(R.id.followingName);
            email = itemView.findViewById(R.id.followingEmail);
            phone = itemView.findViewById(R.id.followingPhone);
        }
    }
}
