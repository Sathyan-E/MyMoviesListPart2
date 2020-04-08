package com.example.mymovieslist;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.TaskViewHolder> {

   private Context mcontext;
   private List<Movie> favMovieList;
   final private favMovieClickListener mymovieClickListner;


   public FavoriteMovieAdapter(Context  context,favMovieClickListener mymovieClickListner)
   {
       this.mcontext=context;
       this.mymovieClickListner=mymovieClickListner;
   }


   @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mcontext).inflate(R.layout.fav_movie_list_item,parent,false);

        return new TaskViewHolder(view);
    }



    @Override
    public void  onBindViewHolder(TaskViewHolder holder,int postion)
    {
        if (favMovieList!=null) {
            Movie favmovie = favMovieList.get(postion);
            String title = favmovie.getMovieTitle();
            int id=favmovie.getId();

            holder.favoritemovie.setText(title);
            holder.favMoiveID.setText(String.valueOf(id));
        }
        else
        {
            holder.favoritemovie.setText("NO movies");
        }
    }

    @Override
    public int getItemCount() {
        if (favMovieList==null)
        {
            return 0;
        }
        return favMovieList.size();
    }

    public List<Movie>  getFavMovieList(){
       return favMovieList;
    }

    public void setTasks(List<Movie> movielist)
    {
        favMovieList=movielist;
        notifyDataSetChanged();
    }

   public interface favMovieClickListener{
       void onMovieClickListener(int itemId);
   }



   class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

      TextView favoritemovie;
      TextView favMoiveID;
       public TaskViewHolder(View itemView)
       {
           super(itemView);
            favoritemovie =itemView.findViewById(R.id.fav_movie_textview);
            favMoiveID=itemView.findViewById(R.id.favmovie_id);
            itemView.setOnClickListener(this);

       }
        //i have to fill this
       @Override
        public void onClick(View v) {


        }
    }
}
