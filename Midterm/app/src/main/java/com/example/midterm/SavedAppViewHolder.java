/*
Chase Schelthoff
 */

package com.example.midterm;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Chases on 10/24/2016.
 */

public class SavedAppViewHolder extends RecyclerView.ViewHolder
{
    public ImageView imageIcon, imagePrice, iconDelete;
    public TextView textName, textPrice;

    public SavedAppViewHolder(View itemView)
    {
        super(itemView);

        imageIcon = (ImageView) itemView.findViewById(R.id.imageViewSavedIcon);
        imagePrice = (ImageView) itemView.findViewById(R.id.imageViewSavedPrice);
        iconDelete = (ImageView) itemView.findViewById(R.id.imageViewSavedDeleteIcon);
        textName = (TextView) itemView.findViewById(R.id.textViewSavedName);
        textPrice = (TextView) itemView.findViewById(R.id.textViewSavedPrice);
    }
}
