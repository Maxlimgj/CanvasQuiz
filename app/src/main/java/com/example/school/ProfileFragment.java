package com.example.school;


import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.school.Helper.DbHelper;
import com.example.school.ModelClasses.ProfileModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


//


    public ProfileFragment() {
        // Required empty public constructor
    }
    public static final String MY_PREFS_NAME = "MyPrefs";
    TextView tv_name,tv_email,tv_phone,tv_dob;
    private DbHelper dbHelper;
    private ArrayList<ProfileModel> ProfileArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_profile, container, false);
        SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String sharedemail = prefs.getString("email", "No name defined");//"No name defined" is the default value.
        tv_name = view.findViewById(R.id.username);
        tv_email = view.findViewById(R.id.useremail);
        tv_phone = view.findViewById(R.id.userphone);
        tv_dob = view.findViewById(R.id.userdob);

        dbHelper = new DbHelper(getActivity());
        ProfileArrayList = new ArrayList<>();
        ProfileArrayList = dbHelper.getUsers(sharedemail);


        ProfileModel modal = ProfileArrayList.get(0);
        tv_email.setText("Email: "+modal.getEmail());
        tv_name.setText("Name: "+modal.getName());
        tv_phone.setText("Phone: "+modal.getPhone());
        tv_dob.setText("Date of Birth: "+modal.getDob());

        return view;
    }

}
