package com.example.mymovieslist;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class UserReviewAdapter extends ArrayAdapter<UserReview> {
    Activity context;
    List<UserReview> reviewList;
    //Constructor
    public UserReviewAdapter(Activity context, List<UserReview> reviews)
    {
        super(context,0,reviews);
        this.context=context;
        this.reviewList=reviews;
    }
    //method for inflating the view
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView== null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.user_review_list_item,parent,false);
        }

        UserReview currentUserReview=getItem(position);
        TextView reviewTextView=(TextView)convertView.findViewById(R.id.userreview_textview);
        reviewTextView.setText(currentUserReview.getContent());
        return convertView;
    }
}
