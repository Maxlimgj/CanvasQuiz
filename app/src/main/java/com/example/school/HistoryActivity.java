package com.example.school;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.school.Adapter.HistoryAdapter;
import com.example.school.data.Attempt;
import com.example.school.data.UserAttempt;
import com.example.school.data.UserDatabase;
import com.example.school.data.UserWithAttempts;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView rvHistory;
    private List<UserWithAttempts> userWithAttempts;
    private TextView tvTotalPoints, tvTotalQuestions;
    public static final String MY_PREFS_NAME = "MyPrefs";
    private UserDatabase userDatabase;
    ArrayList<UserAttempt> userAttempts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        rvHistory = findViewById(R.id.rvHistory);
        tvTotalQuestions = findViewById(R.id.tvTotalQuestionsHistory);
        tvTotalPoints = findViewById(R.id.tvOverAllPointsHistory);

        findViewById(R.id.imageViewHistory).setOnClickListener(view -> finish());

        userDatabase = new UserDatabase(HistoryActivity.this);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String email = prefs.getString("email", "");

        userAttempts = new ArrayList<>();

        userAttempts = userDatabase.getAttemptData(email);
        HistoryAdapter adapter = new HistoryAdapter(userAttempts);
        rvHistory.setAdapter(adapter);

     /*   GetAllUserAttemptTask getAllUserAttemptTask = new GetAllUserAttemptTask(email);
        getAllUserAttemptTask.execute();*/
        ArrayList<Attempt> attempts = new ArrayList<>();

        tvTotalQuestions.setText(String.valueOf(userDatabase.getTotalQuestions(email)));
        tvTotalPoints.setText(String.valueOf(userDatabase.getOverallPoints(email)));

    }





  /*  class GetAllUserAttemptTask extends AsyncTask<Void, Void, Void> {

        private final String email;
        ArrayList<Attempt> attempts = new ArrayList<>();

        public GetAllUserAttemptTask(String email) {
            this.email = email;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            UserDatabase databaseClient = UserDatabaseClient.getInstance(getApplicationContext());
            attempts = (ArrayList<Attempt>) databaseClient.userDao().getUserAndAttemptsWithSameEmail(email);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            int overallPoints = 0;

            for (Attempt userWithAttempts : attempts) {
                overallPoints += userWithAttempts.getEarned();
            }

            tvTotalQuestions.setText(String.valueOf(attempts.size()));
            tvTotalPoints.setText(String.valueOf(overallPoints));

            Collections.sort(attempts, new AttemptCreatedTimeComparator());

            HistoryAdapter adapter = new HistoryAdapter(attempts);
            rvHistory.setAdapter(adapter);


        }
    }

    public class AttemptCreatedTimeComparator implements Comparator<Attempt> {

        @Override
        public int compare(Attempt attempt, Attempt t1) {
            return String.valueOf(t1.getCreatedTime()).compareTo(String.valueOf(attempt.getCreatedTime()));
        }
    }*/


}