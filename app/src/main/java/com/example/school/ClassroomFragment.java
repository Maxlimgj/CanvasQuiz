package com.example.school;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.school.Adapter.CommunityAdapter;
import com.example.school.Helper.DbHelper;
import com.example.school.ModelClasses.ProfileModel;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClassroomFragment extends Fragment
{
    private View TestView;
    RecyclerView communityrecycler;
    private ArrayList<ProfileModel> communityModalArrayList;
    private DbHelper dbHelper;
    private CommunityAdapter communityAdapter;
    public static final String MY_PREFS_NAME = "MyPrefs";

    public ClassroomFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        TestView =  inflater.inflate(R.layout.fragment_test, container, false);
        communityrecycler = TestView.findViewById(R.id.community_list);
        communityModalArrayList = new ArrayList<>();
        dbHelper = new DbHelper(getActivity());

        SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String sharedemail = prefs.getString("email", "No name defined");//"No name defined" is the default value.

        communityModalArrayList = dbHelper.getCommunity(sharedemail);
        communityAdapter = new CommunityAdapter(communityModalArrayList, getActivity());


        // setting layout manager for our recycler view.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        communityrecycler.setLayoutManager(linearLayoutManager);

        // setting our adapter to recycler view.
        communityrecycler.setAdapter(communityAdapter);
        return TestView;
    }
}
