package com.example.school;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

public class NotesDetailsActivity extends AppCompatActivity {

    AppCompatImageView ivImage;
    TextView tvNote, tvDateTime, tvTweetBy;
    String photo, note, dateTime, tweetBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_details);
        ivImage = findViewById(R.id.ivImage);
        tvNote = findViewById(R.id.tvNote);
        tvDateTime = findViewById(R.id.tvDateTime);
        tvTweetBy = findViewById(R.id.tvTweetBy);

        photo = getIntent().getStringExtra("photo");
        note = getIntent().getStringExtra("note");
        dateTime = getIntent().getStringExtra("dateTime");
        tweetBy = getIntent().getStringExtra("by");

        tvNote.setText(note);
        tvTweetBy.setText(tweetBy);
        tvDateTime.setText(dateTime);


        if (!TextUtils.isEmpty(photo)) {
            ivImage.setImageBitmap(getBitmapFromEncodedString(photo));

        }

    }
    private Bitmap getBitmapFromEncodedString(String encodedString) {

        byte[] arr = Base64.decode(encodedString, Base64.URL_SAFE);

        Bitmap img = BitmapFactory.decodeByteArray(arr, 0, arr.length);

        return img;

    }
}
