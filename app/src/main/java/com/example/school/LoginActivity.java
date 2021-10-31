package com.example.school;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.school.Helper.DbHelper;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    TextView registerlink, textView;
    private TextInputLayout mEmailInputLayout, mPasswordInputLayout;
    private DbHelper dbHelper;
    private Button Loginbtn;
    String email, password;
    ImageView imageView;
    MediaPlayer maplestory;
    public static final String MY_PREFS_NAME = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        email = prefs.getString("email", "");
        if (!TextUtils.isEmpty(email)) {
            Intent intent = new Intent(LoginActivity.this, LobbyActivity.class);
            intent.putExtra("Email", email);
            startActivity(intent);
            finish();
        }

        textView = findViewById(R.id.tv_login_status);
        imageView = findViewById(R.id.suss_img_view);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_file);

        textView.startAnimation(animation);

        AlphaAnimation fadeIn = new AlphaAnimation(0, 1);

        AlphaAnimation fadeOut = new AlphaAnimation(1, 0);


        final AnimationSet set = new AnimationSet(false);

        set.addAnimation(fadeIn);
        set.addAnimation(fadeOut);
        fadeOut.setStartOffset(2000);
        set.setDuration(2000);
        imageView.startAnimation(set);

        //FOR SUSS IMAGE
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                imageView.startAnimation(set);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }
        });

        maplestory = MediaPlayer.create(LoginActivity.this, R.raw.maplestory_wav);
        maplestory.start();

        mEmailInputLayout = findViewById(R.id.textinputlayout_email);
        mPasswordInputLayout = findViewById(R.id.textinputlayout_password);
        registerlink = findViewById(R.id.tv_registerlink);
        Loginbtn = findViewById(R.id.btn_login);

        dbHelper = new DbHelper(this);

        Loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateEmailAddress() | !validPassword()) {
                    return;
                }
                if (dbHelper.LoginUser(email, password, getApplicationContext(), mEmailInputLayout, mPasswordInputLayout)) {
                    Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, LobbyActivity.class);
                    intent.putExtra("Email", email);
                    Log.d("tag", "login successful..");
                    maplestory.release();
                    Log.e("Email", email);
                    SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("email", email);
                    editor.apply();


                    startActivity(intent);
                    finish();


                }
            }
        });

        registerlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public void onPause() {
        super.onPause();
        maplestory.release();
    }

    @Override
    public void onStop() {
        super.onStop();
        maplestory.release();
    }


    private boolean validateEmailAddress() {
        email = mEmailInputLayout.getEditText().getText().toString().trim();
        if (email.isEmpty())             //Using method isEmpty()
        {
            mEmailInputLayout.setError("Email is required. Can't be empty");                 //Setting up an error
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailInputLayout.setError("Invalid Email Address. Enter valid Email Address");
            return false;
        } else {
            mEmailInputLayout.setError(null);
            return true;
        }

    }

    private boolean validPassword() {
        password = mPasswordInputLayout.getEditText().getText().toString().trim();


        if (password.isEmpty()) {
            mPasswordInputLayout.setError("Password is required. Can't be empty");
            return false;
        } else {
            mPasswordInputLayout.setError(null);
            return true;
        }

    }
}