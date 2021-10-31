package com.example.school.Helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.school.ModelClasses.ProfileModel;
import com.example.school.ModelClasses.TweetsModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "mydb";
    private static final int DB_VERSION = 1;
    private static final String TABLE_REGISTER = "Register";
    private static final String TABLE_TWEETS = "Tweets";
    private static final String TABLE_FOLLOWING = "Following";
    private static final String TABLE_FOLLOWER = "Follower";
    private static final String ID_COL = "id";
    private static final String NAME_COL = "name";
    private static final String DOB_COL = "dob";
    private static final String PHONE_COL = "phone";
    private static final String TWEET_COL = "tweet";
    private static final String TIME_COL = "time";
    private static final String DATE_COL = "date";
    private static final String FOLLOWING_COL = "following";
    private static final String FOLLOWER_COL = "follower";
    private static final String EMAIL_COL = "email";
    private static final String PASSWORD_COL = "password";
    String registeredEmail = "", registeredPassword = "", registereduser = "", registeredfollowing = "";


    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_REGISTER + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + DOB_COL + " TEXT,"
                + EMAIL_COL + " TEXT,"
                + PHONE_COL + " TEXT,"
                + PASSWORD_COL + " TEXT)";
        db.execSQL(query);

        String query1 = "CREATE TABLE " + TABLE_TWEETS + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EMAIL_COL + " TEXT,"
                + TWEET_COL + " TEXT,"
                + DATE_COL + " TEXT,"
                + TIME_COL + " TEXT)";

        db.execSQL(query1);

        String query2 = "CREATE TABLE " + TABLE_FOLLOWING + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EMAIL_COL + " TEXT,"
                + NAME_COL + " TEXT,"
                + PHONE_COL + " TEXT,"
                + FOLLOWING_COL + " TEXT)";

        db.execSQL(query2);

        String query3 = "CREATE TABLE " + TABLE_FOLLOWER + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EMAIL_COL + " TEXT ,"
                + NAME_COL + " TEXT ,"
                + PHONE_COL + " TEXT,"
                + FOLLOWER_COL + " TEXT )";

        db.execSQL(query3);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TWEETS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOLLOWING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOLLOWER);
        onCreate(db);
    }


    @SuppressLint("Range")
    public boolean registerUser(String userName, String Date, String phone, String email, Context mCtx, TextInputLayout mEmailInputLayout, String password) {

        SQLiteDatabase db = this.getWritableDatabase();


        Cursor c = db.rawQuery("SELECT email FROM " + TABLE_REGISTER
                + " WHERE email like '" + email + "'", null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    registeredEmail = c.getString(c.getColumnIndex("email"));
                    Toast.makeText(mCtx, registeredEmail, Toast.LENGTH_SHORT).show();
                } while (c.moveToNext());
            }
        }
        if (registeredEmail.equals(email)) {
            mEmailInputLayout.setError("Email Already in use");
            return false;

        } else {
            ContentValues values = new ContentValues();
            values.put(NAME_COL, userName);
            values.put(DOB_COL, Date);
            values.put(EMAIL_COL, email);
            values.put(PHONE_COL, phone);
            values.put(PASSWORD_COL, password);
            db.insert(TABLE_REGISTER, null, values);
            db.close();
            return true;

        }
    }

    @SuppressLint("Range")
    public boolean LoginUser(String email, String password, Context mCtx, TextInputLayout mEmailInputLayout, TextInputLayout mPasswordInputLayout) {
        SQLiteDatabase db = this.getWritableDatabase();


        Cursor c = db.rawQuery("SELECT email,password FROM " + TABLE_REGISTER
                + " WHERE email like '" + email + "'", null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    registeredEmail = c.getString(c.getColumnIndex("email"));
                    registeredPassword = c.getString(c.getColumnIndex("password"));

                } while (c.moveToNext());
            }
            if (registeredEmail.equals(email) && registeredPassword.equals(password)) {
                return true;
            }
            if (!registeredEmail.equals(email)) {
                mEmailInputLayout.setError("Email Not Registered yet");
                if (!registeredPassword.equals(password)) {
                    mPasswordInputLayout.setError("Wrong Password");
                }
                return false;
            }
            if (!registeredPassword.equals(password)) {
                mPasswordInputLayout.setError("Wrong Password");
                return false;
            }
        }
        return false;
    }

    public ArrayList<ProfileModel> getUsers(String email) {

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursorCourses = db.rawQuery("SELECT email,name,phone,dob FROM " + TABLE_REGISTER
                + " WHERE email like '" + email + "'", null);


        ArrayList<ProfileModel> courseModalArrayList = new ArrayList<>();


        if (cursorCourses.moveToFirst()) {
            do {

                courseModalArrayList.add(new ProfileModel(cursorCourses.getString(1),
                        cursorCourses.getString(0),
                        cursorCourses.getString(2),
                        cursorCourses.getString(3)));
            } while (cursorCourses.moveToNext());

        }

        cursorCourses.close();
        return courseModalArrayList;
    }

    @SuppressLint("Range")
    public boolean addTweet(String tweet, String email, String time, String date) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values1 = new ContentValues();

        values1.put(EMAIL_COL, email);
        values1.put(TWEET_COL, tweet);
        values1.put(DATE_COL, date);
        values1.put(TIME_COL, time);
        db.insert(TABLE_TWEETS, null, values1);
        db.close();
        return true;

    }

    public ArrayList<TweetsModel> getTweets() {

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursorCourses = db.rawQuery("SELECT email,tweet,time,date FROM " + TABLE_TWEETS, null);


        ArrayList<TweetsModel> tweetModalArrayList = new ArrayList<>();

        if (cursorCourses.moveToFirst()) {
            do {

                tweetModalArrayList.add(new TweetsModel(cursorCourses.getString(0),
                        cursorCourses.getString(1),
                        cursorCourses.getString(2),
                        cursorCourses.getString(3)));
            } while (cursorCourses.moveToNext());

        }

        cursorCourses.close();
        return tweetModalArrayList;
    }

    public ArrayList<ProfileModel> getCommunity(String email) {

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursorCourses = db.rawQuery("SELECT email,name,phone FROM " + TABLE_REGISTER
                + " WHERE email not like '" + email + "'", null);


        ArrayList<ProfileModel> communityModalArrayList = new ArrayList<>();


        if (cursorCourses.moveToFirst()) {
            do {

                communityModalArrayList.add(new ProfileModel(cursorCourses.getString(1),
                        cursorCourses.getString(0),
                        cursorCourses.getString(2)));
            } while (cursorCourses.moveToNext());

        }

        cursorCourses.close();
        return communityModalArrayList;
    }

    public ArrayList<ProfileModel> getFollowing(String email) {

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursorCourses = db.rawQuery("SELECT name,phone,following FROM " + TABLE_FOLLOWING
                + " WHERE email like '" + email + "'", null);


        ArrayList<ProfileModel> communityModalArrayList = new ArrayList<>();


        if (cursorCourses.moveToFirst()) {
            do {

                communityModalArrayList.add(new ProfileModel(cursorCourses.getString(0),
                        cursorCourses.getString(2),
                        cursorCourses.getString(1)));
            } while (cursorCourses.moveToNext());

        }

        cursorCourses.close();
        return communityModalArrayList;
    }

    public ArrayList<ProfileModel> getFollower(String email) {

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursorCourses = db.rawQuery("SELECT name,phone,follower FROM " + TABLE_FOLLOWER
                + " WHERE email like '" + email + "'", null);


        ArrayList<ProfileModel> communityModalArrayList = new ArrayList<>();


        if (cursorCourses.moveToFirst()) {
            do {

                communityModalArrayList.add(new ProfileModel(cursorCourses.getString(0),
                        cursorCourses.getString(2),
                        cursorCourses.getString(1)));
            } while (cursorCourses.moveToNext());

        }

        cursorCourses.close();
        return communityModalArrayList;
    }

    @SuppressLint("Range")
    public boolean addFollowing(String email, String friendemail, String friendname, String friendcontact) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT email,following FROM " + TABLE_FOLLOWING
                + " WHERE email like '" + email + "' and following like '" + friendemail + "'", null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    registereduser = c.getString(c.getColumnIndex("email"));
                    registeredfollowing = c.getString(c.getColumnIndex("following"));

                } while (c.moveToNext());
            }
            if (registereduser.equals(email) && registeredfollowing.equals(friendemail)) {
                return false;
            } else {
                ContentValues values1 = new ContentValues();
                values1.put(EMAIL_COL, email);
                values1.put(FOLLOWING_COL, friendemail);
                values1.put(NAME_COL, friendname);
                values1.put(PHONE_COL, friendcontact);
                db.insert(TABLE_FOLLOWING, null, values1);
                db.close();
                return true;
            }
        }
        return false;


    }

    @SuppressLint("Range")
    public boolean addFollower(String email, String friendemail, String friendname, String friendcontact) {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values1 = new ContentValues();

        values1.put(EMAIL_COL, email);
        values1.put(FOLLOWER_COL, friendemail);
        values1.put(NAME_COL, friendname);
        values1.put(PHONE_COL, friendcontact);
        db.insert(TABLE_FOLLOWER, null, values1);
        db.close();
        return true;

    }

    // below is the method for unfollowing.
    @SuppressLint("Range")
    public boolean unfollow(String email, String following) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT email,following FROM " + TABLE_FOLLOWING
                + " WHERE email like '" + email + "' and following like '" + following + "'", null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    registereduser = c.getString(c.getColumnIndex("email"));
                    registeredfollowing = c.getString(c.getColumnIndex("following"));

                } while (c.moveToNext());
            }
            if (registereduser.equals(email) && registeredfollowing.equals(following)) {
                db.delete(TABLE_FOLLOWING, "email=? and following=?", new String[]{registereduser, registeredfollowing});
                db.close();
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public void delTweet(String date, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TWEETS, "date='" + date + "' and time='" + time + "'", null);
    }
}
