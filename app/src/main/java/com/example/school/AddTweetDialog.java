package com.example.school;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.DialogFragment;

import com.example.school.Helper.DbHelper;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class AddTweetDialog extends DialogFragment {
    private static final String TAG = "TweetDialog";
    public static final String MY_PREFS_NAME = "MyPrefs";
    private TextInputLayout mTweetInputLayout;
    String tweet, currentTime, sharedemail, currentdate;
    private DbHelper dbHelper;
    private Button btn;
    AppCompatImageView ivImage;

    private static final int CAMERA_REQUEST = 1888;

    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    //Bitmap photo;
    String photo = "";
    Bitmap theImage;
    MediaPlayer mp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.addtweetdialog, container, false);
        mTweetInputLayout = view.findViewById(R.id.textinputlayout_tweet);
        btn = view.findViewById(R.id.btnaddtweet);
        ivImage = view.findViewById(R.id.ivImage);
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
                    if (dbHelper.addTweet(tweet, sharedemail, currentTime, currentdate, photo)) {
                        mp = MediaPlayer.create(getContext(), R.raw.jump);
                        mp.start();
                        getDialog(                ).dismiss();
                        Toast.makeText(getContext(), "Notes added Successfully", Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new TweetsFragment())
                                .commit();
                    }
                } else if (tweet.length() == 0) {
                    Toast.makeText(getContext(), "Kindly write some post", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ivImage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });


        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(getActivity(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Start an activity for result
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            theImage = (Bitmap) data.getExtras().get("data");
            ivImage.setImageBitmap(theImage);
            photo = getEncodedString(theImage);

        }
    }

    private String getEncodedString(Bitmap bitmap) {

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);

      /* or use below if you want 32 bit images

       bitmap.compress(Bitmap.CompressFormat.PNG, (0–100 compression), os);*/
        byte[] imageArr = os.toByteArray();
        return Base64.encodeToString(imageArr, Base64.URL_SAFE);

    }
}
