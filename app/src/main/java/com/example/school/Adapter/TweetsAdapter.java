package com.example.school.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.school.Helper.DbHelper;
import com.example.school.ModelClasses.TweetsModel;
import com.example.school.NotesDetailsActivity;
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

        if (!TextUtils.isEmpty(modal.getPhoto())) {
            holder.ivImage.setImageBitmap(getBitmapFromEncodedString(modal.getPhoto()));
            holder.ivImage.setVisibility(View.VISIBLE);
        } else {
            holder.ivImage.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return tweetModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        private TextView tweet, email, time, date;
        private RelativeLayout relDelete;
        AppCompatImageView ivImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            tweet = itemView.findViewById(R.id.showtweet);
            email = itemView.findViewById(R.id.showtweetemail);
            time = itemView.findViewById(R.id.showtweettime);
            date = itemView.findViewById(R.id.showtweetdate);
            ivImage = itemView.findViewById(R.id.ivImage);
            relDelete = itemView.findViewById(R.id.reldelete);

            relDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TweetsModel modal = tweetModalArrayList.get(getAdapterPosition());

                    Intent i = new Intent(context, NotesDetailsActivity.class);
                    i.putExtra("note", modal.getTweet());
                    i.putExtra("by", modal.getEmail());
                    i.putExtra("dateTime", modal.getDate() + " " + modal.getTime());
                    i.putExtra("photo", modal.getPhoto());
                    context.startActivity(i);
                }
            });


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

    private Bitmap getBitmapFromEncodedString(String encodedString) {

        byte[] arr = Base64.decode(encodedString, Base64.URL_SAFE);

        Bitmap img = BitmapFactory.decodeByteArray(arr, 0, arr.length);

        return img;

    }
}
