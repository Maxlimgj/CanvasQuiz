package com.example.school.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.school.Helper.DbHelper;
import com.example.school.ModelClasses.ProfileModel;
import com.example.school.R;


import java.util.ArrayList;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.CommunityHolder>
{
    private ArrayList<ProfileModel> communityModalArrayList;
    private ArrayList<ProfileModel> ProfileArrayList;
    private Context context;
    public static final String MY_PREFS_NAME = "MyPrefs";
    private DbHelper dbHelper;

    public CommunityAdapter (ArrayList<ProfileModel> communityModalArrayList, Context context) {
        this.communityModalArrayList = communityModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CommunityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.communitylayout, parent, false);
        return new CommunityAdapter.CommunityHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityHolder holder, int position) {
        ProfileModel modal = communityModalArrayList.get(position);
        dbHelper = new DbHelper(context);
        ProfileArrayList = new ArrayList<>();

        holder.name.setText(modal.getName());
        holder.email.setText(modal.getEmail());
        holder.phone.setText(modal.getPhone());
        holder.btnfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                String sharedemail = prefs.getString("email", "No name defined");//"No name defined" is the default value.
                ProfileArrayList = dbHelper.getUsers(sharedemail);
                ProfileModel profilemodal = ProfileArrayList.get(0);
                if(dbHelper.addFollowing(sharedemail, modal.getEmail(),modal.getName(),modal.getPhone()) &&
                        dbHelper.addFollower(modal.getEmail(),sharedemail,profilemodal.getName(),profilemodal.getPhone())){
                    Toast.makeText(context, "You Started Following "+modal.getName(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "You already Following "+modal.getName(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.btnunfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                String sharedemail = prefs.getString("email", "No name defined");//"No name defined" is the default value.

                if(dbHelper.unfollow(sharedemail,modal.getEmail()))
                {
                    Toast.makeText(context, "You Successfully Unfollowed "+modal.getName(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "You are not following "+modal.getName(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return communityModalArrayList.size();
    }

    public class CommunityHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        private TextView name, email, phone;
        private Button btnfollow,btnunfollow;
        public CommunityHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            name = itemView.findViewById(R.id.commName);
            email = itemView.findViewById(R.id.commEmail);
            phone = itemView.findViewById(R.id.commPhone);
            btnfollow = itemView.findViewById(R.id.commbtnfollow);
            btnunfollow = itemView.findViewById(R.id.commbtnunfollow);
        }
    }

}
