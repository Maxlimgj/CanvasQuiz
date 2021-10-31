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

import com.example.school.Adapter.CommunityAdapter;
import com.example.school.Helper.DbHelper;
import com.example.school.ModelClasses.ProfileModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimetableFragment extends Fragment
{
    public TimetableFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.timetable, container, false);

        return view;
    }

}
