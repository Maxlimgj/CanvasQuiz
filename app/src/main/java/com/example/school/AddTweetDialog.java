package com.example.school;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.school.Helper.DbHelper;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddTweetDialog extends DialogFragment {
    private static final String TAG = "TweetDialog";
    public static final String MY_PREFS_NAME = "MyPrefs";
    private TextInputLayout mTweetInputLayout;
    String tweet, currentTime, sharedemail, currentdate;
    private DbHelper dbHelper;
    private Button btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.addtweetdialog, container, false);
        mTweetInputLayout = view.findViewById(R.id.textinputlayout_tweet);
        btn = view.findViewById(R.id.btnaddtweet);
        SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        dbHelper = new DbHelper(getActivity());


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tweet = mTweetInputLayout.getEditText().getText().toString().trim();
                sharedemail = prefs.getString("email", "No name defined");//"No name defined" is the default value.
                currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                currentdate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                if (tweet.length() > 0) {
                    if (dbHelper.addTweet(tweet, sharedemail, currentTime, currentdate)) {
                        getDialog().dismiss();
                        Toast.makeText(getContext(), "Tweet added Successfully", Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new TweetsFragment())
                                .commit();
                    }
                } else if (tweet.length() == 0) {
                    Toast.makeText(getContext(), "Kindly write some post", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }
}
