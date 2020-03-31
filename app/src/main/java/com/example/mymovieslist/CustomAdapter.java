package com.example.mymovieslist;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;
import com.example.mymovieslist.CustomAdapter.MyViewHolder;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    final static String BASIC_PATH_IMAGE="http://image.tmdb.org/t/p/w185";
    //global variables
    private List<Movie> myList;
    private Context context;
    private OnItemListener myOnItemListener;
    //Constructor
    CustomAdapter(Context context,List<Movie> list,OnItemListener onItemListener)
    {
        this.myOnItemListener=onItemListener;
        this.context=context;
        myList=list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate the itme layout
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout,parent,false);
        //set the view's size ,margins, paddings and layout parameters.
        MyViewHolder vh=  new MyViewHolder(v,myOnItemListener);
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    //set data in items
        Movie currentMovie = myList.get(position);
        holder.bind(currentMovie);
    }

    @Override
    public int getItemCount() {
       if (myList==null)
       {

           return 0;
       }
       else {
           return myList.size();
       }
    }
    //inner class MyViewHolder class
    public class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        //global variables
       ImageView imageView;
       OnItemListener onItemListener;
       //constructor
       public MyViewHolder(@NonNull View itemView,OnItemListener onItemListener ) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            this.onItemListener=onItemListener;
            itemView.setOnClickListener(this);
        }
        //method for onclick
        @Override
        public void onClick(View v) {
            onItemListener.onMovieClick(getAdapterPosition());
        }
        //method for binding image onto Imageview
        void bind(Movie movie)
        {
            String imageUrl=movie.getImageUrl();
            String urlstring =BASIC_PATH_IMAGE+imageUrl;
            Picasso.get().load(urlstring).into(imageView);
        }
    }
    //interface
    public interface OnItemListener{
        void onMovieClick(int position);
    }

}
