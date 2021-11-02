package com.example.school;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.school.Adapter.AchievementAdapter;
import com.example.school.ModelClasses.Achievement;
import com.example.school.data.UserDatabase;

import java.util.ArrayList;
import java.util.List;

public class AchievementFragment extends Fragment {


    private RecyclerView recyclerView;
    private List<Achievement> achievementList;
    private UserDatabase userDatabase;
    MediaPlayer celebrate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_achievement, container, false);

        recyclerView = view.findViewById(R.id.recycler);

        celebrate = MediaPlayer.create(getContext(), R.raw.celebrationeffect);
        celebrate.start();

        userDatabase = new UserDatabase(getContext());

        achievementList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        achievementList = userDatabase.getAchievement();
        AchievementAdapter adapter = new AchievementAdapter(achievementList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
