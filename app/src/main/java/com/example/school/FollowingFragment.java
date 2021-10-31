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

import com.example.school.Adapter.FollowingAdapter;
import com.example.school.Helper.DbHelper;
import com.example.school.ModelClasses.ProfileModel;

import java.util.ArrayList;

public class FollowingFragment extends Fragment
{
    RecyclerView followingrecycler;
    private ArrayList<ProfileModel> followingModalArrayList;
    private DbHelper dbHelper;
    private FollowingAdapter followingAdapter;
    public static final String MY_PREFS_NAME = "MyPrefs";
    public FollowingFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_following, container, false);

        followingrecycler = view.findViewById(R.id.following_list);
        followingModalArrayList= new ArrayList<>();
        dbHelper = new DbHelper(getActivity());
        SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String sharedemail = prefs.getString("email", "No name defined");//"No name defined" is the default value.

        followingModalArrayList = dbHelper.getFollowing(sharedemail);
        followingAdapter = new FollowingAdapter(followingModalArrayList, getActivity());


        // setting layout manager for our recycler view.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        followingrecycler.setLayoutManager(linearLayoutManager);

        // setting our adapter to recycler view.
        followingrecycler.setAdapter(followingAdapter);
        return view;
    }
}
