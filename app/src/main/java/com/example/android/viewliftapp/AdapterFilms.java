package com.example.android.viewliftapp;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

public class AdapterFilms  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private LayoutInflater inflater;
    List<DataFilms> data = Collections.emptyList();
    int currentPos=0;

    public AdapterFilms (Context context, List<DataFilms> data) {
        this.context = context;
        inflater = LayoutInflater.from( context );
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate( R.layout.container_films, parent, false );
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        DataFilms current = data.get(position);
        myHolder.textFilmName.setText(current.filmsImage);

        Glide.with( context ).load( "http://www.snagfilms.com/apis/films.json?limit=10" )
//                .placeholder( R.drawable.ic_img_error )
//                .error( R.drawable.ic_img_error )
                .into( myHolder.ivFilms );
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView textFilmName;
        ImageView ivFilms;

        public MyHolder(View itemView) {
            super( itemView );

            textFilmName= (TextView) itemView.findViewById(R.id.textFilmName);
            ivFilms= (ImageView) itemView.findViewById(R.id.ivFilms);

        }
    }
}
