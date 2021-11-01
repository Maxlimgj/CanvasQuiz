package com.example.school.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.school.ModelClasses.Achievement;

import java.util.ArrayList;


public class UserDatabase extends SQLiteOpenHelper {

    private static final String DB_NAME = "student";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "attempt_result";

    private static final String ID_COL = "id";
    private static final String EMAIL_COL = "email";
    private static final String DATE_COL = "dateAttempt";
    private static final String SUBJECT_COL = "subject";
    private static final String CORRECT_COL = "correct";
    private static final String INCORRECT_COL = "incorrect";
    private static final String EARNED_COL = "earned";
    private static final String POINTS_COL = "overallPoints";

    public UserDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EMAIL_COL + " TEXT,"
                + DATE_COL + " TEXT,"
                + SUBJECT_COL + " TEXT,"
                + CORRECT_COL + " TEXT,"
                + INCORRECT_COL + " TEXT,"
                + EARNED_COL + " TEXT,"
                + POINTS_COL + " TEXT)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public void addAttemptData(String email, String dateA, String subject, String correct, String incorrect, String earned, String points) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EMAIL_COL, email);
        values.put(DATE_COL, dateA);
        values.put(SUBJECT_COL, subject);
        values.put(CORRECT_COL, correct);
        values.put(INCORRECT_COL, incorrect);
        values.put(EARNED_COL, earned);
        values.put(POINTS_COL, points);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<UserAttempt> getAttemptData(String email) {
        ArrayList<UserAttempt> userAttempts = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("Select * from " + TABLE_NAME + " where email='" + email + "'", null);

        if (cursor.moveToFirst()) {
            do {

                UserAttempt userAttempt = new UserAttempt(cursor.getInt(0), cursor.getString(1), cursor.getString(3), cursor.getString(5),
                        cursor.getString(4),
                        cursor.getString(2)
//                        ,cursor.getString(7)

                );

                userAttempts.add(userAttempt);

            } while (cursor.moveToNext());
        }
        cursor.close();

        return userAttempts;
    }

    public int getOverallPoints(String email) {
        int totalPoints = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select sum(earned) from " + TABLE_NAME + " where email='" + email + "'", null);
        if (c.moveToFirst())
            totalPoints = c.getInt(0);

        c.close();
        return totalPoints;
    }

    public int getTotalQuestions(String email) {
        int totalQ = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select count(*) from " + TABLE_NAME + " where email='" + email + "'", null);
        if (c.moveToFirst())
            totalQ = c.getInt(0) * 5;

        c.close();
        return totalQ;
    }

    public ArrayList<Achievement> getAchievement() {
        ArrayList<Achievement> achievements = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("Select email,sum(earned) as score from " + TABLE_NAME + " group by email order by score desc", null);

        if (cursor.moveToFirst()) {
            do {

                Achievement achievement = new Achievement(cursor.getString(0), cursor.getInt(1));

                achievements.add(achievement);

            } while (cursor.moveToNext());
        }
        cursor.close();

        return achievements;
    }
}