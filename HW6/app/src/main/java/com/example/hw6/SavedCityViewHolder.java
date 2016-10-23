package com.example.hw6;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SavedCityViewHolder extends RecyclerView.ViewHolder
{
    public TextView textLocation, textTemp, textUpdatedOn;
    public ImageView ivFavIcon;

    public SavedCityViewHolder(View itemView)
    {
        super(itemView);

        textLocation  = (TextView)  itemView.findViewById(R.id.textViewLocation);
        textTemp      = (TextView)  itemView.findViewById(R.id.textViewCityTemp);
        textUpdatedOn = (TextView)  itemView.findViewById(R.id.textViewUpdatedOn);
        ivFavIcon     = (ImageView) itemView.findViewById(R.id.imageViewFavorite);
    }
}
