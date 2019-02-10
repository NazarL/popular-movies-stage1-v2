package com.example.popular_movies_v2;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapterRView extends RecyclerView.Adapter<MovieAdapterRView.ViewHolder> {
    private Context context;
    private static ArrayList<Movie> mMovies;

    void setData(ArrayList<Movie> movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }

    public MovieAdapterRView(Context context, ArrayList<Movie> movies) {
        this.context = context;
        mMovies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater
                        .from(context)
                        .inflate(R.layout.list_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Movie currentMovie = mMovies.get(position);

        if (currentMovie != null) {
            Picasso
                    .get()
                    .load(currentMovie.getPosterPath())
                    .error(R.drawable.ic_android_black_24dp)
                    .into(holder.posterIMV);
        }

        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra(Constants.SELECTED_MOVIE, currentMovie);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView posterIMV;
        private View parentView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.parentView = itemView;
            posterIMV = itemView.findViewById(R.id.imv_grid_poster);
        }
    }
}
