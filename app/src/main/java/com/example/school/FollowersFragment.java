package com.example.school;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.school.Adapter.FollowerAdapter;
import com.example.school.Helper.DbHelper;
import com.example.school.ModelClasses.ProfileModel;

import java.util.ArrayList;

public class FollowersFragment extends Fragment
{
    RecyclerView followerrecycler;
    private ArrayList<ProfileModel> followerModalArrayList;
    private DbHelper dbHelper;
    private FollowerAdapter followerAdapter;
    public static final String MY_PREFS_NAME = "MyPrefs";
    public FollowersFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_followers, container, false);
        followerrecycler = view.findViewById(R.id.followers_list);
        followerModalArrayList= new ArrayList<>();
        dbHelper = new DbHelper(getActivity());
        SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String sharedemail = prefs.getString("email", "No name defined");//"No name defined" is the default value.

        followerModalArrayList = dbHelper.getFollower(sharedemail);
        followerAdapter = new FollowerAdapter(followerModalArrayList, getActivity());


        // setting layout manager for our recycler view.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        followerrecycler.setLayoutManager(linearLayoutManager);

        // setting our adapter to recycler view.
        followerrecycler.setAdapter(followerAdapter);

        return view;
    }
}
