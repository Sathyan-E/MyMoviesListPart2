package com.example.mymovieslist;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemDecorator extends RecyclerView.ItemDecoration {
    //global variable for offset dimension
    private int myItemOffset;
    //constructor
    public ItemDecorator(int itemoffset)
    {
        myItemOffset=itemoffset;
    }
    //constructor
    public ItemDecorator(@NonNull Context context,@DimenRes int itemoffsetId)
    {
        this(context.getResources().getDimensionPixelSize(itemoffsetId));
    }
    //settting spaces between elements
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(myItemOffset,myItemOffset,myItemOffset,myItemOffset);
    }
}
