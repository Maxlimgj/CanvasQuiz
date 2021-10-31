package com.example.school.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.school.Helper.DbHelper;
import com.example.school.ModelClasses.TweetsModel;
import com.example.school.R;

import java.util.ArrayList;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {
    private ArrayList<TweetsModel> tweetModalArrayList;
    private Context context;

    // constructor
    public TweetsAdapter(ArrayList<TweetsModel> tweetModalArrayList, Context context) {
        this.tweetModalArrayList = tweetModalArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweetlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TweetsModel modal = tweetModalArrayList.get(position);
        holder.tweet.setText(modal.getTweet());
        holder.email.setText(modal.getEmail());
        holder.time.setText(modal.getTime());
        holder.date.setText(modal.getDate());


    }

    @Override
    public int getItemCount() {
        return tweetModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        private TextView tweet, email, time, date;
        private RelativeLayout relDelete;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            tweet = itemView.findViewById(R.id.showtweet);
            email = itemView.findViewById(R.id.showtweetemail);
            time = itemView.findViewById(R.id.showtweettime);
            date = itemView.findViewById(R.id.showtweetdate);
            relDelete = itemView.findViewById(R.id.reldelete);

            relDelete.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    TweetsModel modal = tweetModalArrayList.get(getAdapterPosition());
                    DbHelper dbHelper = new DbHelper(context);
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    builder.setMessage("Delete: " + modal.getTweet());

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dbHelper.delTweet(modal.getDate(), modal.getTime());

                            dialog.dismiss();
                            tweetModalArrayList.remove(getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());



                            Toast.makeText(context, "Notes Removed Successfully!!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();

                    return true;
                }
            });
        }
    }
}
