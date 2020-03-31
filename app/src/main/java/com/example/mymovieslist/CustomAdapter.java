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
    private List<Movie> myList;
    private Context context;
    private OnItemListener myOnItemListener;


    CustomAdapter(Context context,List<Movie> list,OnItemListener onItemListener)
    {
        this.myOnItemListener=onItemListener;
        this.context=context;
       // myClickListener=listItemCLickListener;
        myList=list;
        if (list==null)
        {
            Toast.makeText(context, "list is null at constructor level", Toast.LENGTH_SHORT).show();
        }
        Log.i("list length checking","at custom class"+myList.size());
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


        //implement setOnclick
    }

    @Override
    public int getItemCount() {
       if (myList==null)
       {
           //Toast.makeText(context, "MoviesList is null", Toast.LENGTH_SHORT).show();
           return 0;
       }
       else {
           return myList.size();
       }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

       ImageView imageView;
       OnItemListener onItemListener;

       public MyViewHolder(@NonNull View itemView,OnItemListener onItemListener ) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            this.onItemListener=onItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemListener.onMovieClick(getAdapterPosition());
        }


        void bind(Movie movie)
        {
            String imageUrl=movie.getImageUrl();
            String path="http://image.tmdb.org/t/p/w185";
            String urlstring =path+imageUrl;
            Log.i("url checking","at bindview"+urlstring);
            Picasso.get().load(urlstring).into(imageView);
        }
    }
    public interface OnItemListener{
        void onMovieClick(int position);
    }

}
