package com.example.school;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.school.Adapter.TweetsAdapter;
import com.example.school.Helper.DbHelper;
import com.example.school.ModelClasses.TweetsModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TweetsFragment extends Fragment {
    RecyclerView tweetrecycler;
    FloatingActionButton floatingActionButton;
    private ArrayList<TweetsModel> tweetsModalArrayList;
    private DbHelper dbHelper;
    private TweetsAdapter tweetsAdapter;


    public TweetsFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tweet_fragment, container, false);

        tweetrecycler = view.findViewById(R.id.tweetrecycler);
        floatingActionButton = view.findViewById(R.id.floating);


        tweetsModalArrayList = new ArrayList<>();
        dbHelper = new DbHelper(getActivity());

        // getting our tweet array
        // list from db helper class.
        tweetsModalArrayList = dbHelper.getTweets();

        // on below line passing our array lost to our adapter class.
        tweetsAdapter = new TweetsAdapter(tweetsModalArrayList, getActivity());


        // setting layout manager for our recycler view.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        tweetrecycler.setLayoutManager(linearLayoutManager);

        // setting our adapter to recycler view.
        tweetrecycler.setAdapter(tweetsAdapter);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddTweetDialog addTweetDialog = new AddTweetDialog();
                addTweetDialog.show(getActivity().getSupportFragmentManager(), "TweetDialog");
            }
        });

        return view;
    }

    public void getData() {
        tweetsModalArrayList.clear();
        tweetsModalArrayList = dbHelper.getTweets();

        // on below line passing our array lost to our adapter class.
        tweetsAdapter = new TweetsAdapter(tweetsModalArrayList, getActivity());


        // setting layout manager for our recycler view.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        tweetrecycler.setLayoutManager(linearLayoutManager);

        // setting our adapter to recycler view.
        tweetrecycler.setAdapter(tweetsAdapter);

    }
}
