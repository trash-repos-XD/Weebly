package com.example.weebly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weebly.helpers.CacheHelper;
import com.example.weebly.placeholder.Content.AnimeSched;
import com.example.weebly.databinding.FragmentItemBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link AnimeSched}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<AnimeSched> mValues;
    private final Context mContext;

    public MyItemRecyclerViewAdapter(List<AnimeSched> items, Context ctx) {
        mValues = items;
        mContext = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        new CacheHelper.DownloadImageTask(holder.mImageView)
                .execute(mValues.get(position).thumbnail);
        holder.mNameView.setText(mValues.get(position).name);
        holder.mGenreView.setText(mValues.get(position).genres);

        String synopsisContent = mValues.get(position).synopsis;
        int synopsisLength = 150;
        if (synopsisContent.length() > synopsisLength) {
            synopsisContent = synopsisContent.substring(0, synopsisLength) + "...";
        }

        holder.mSynopsisView.setText(synopsisContent);
        holder.mViewAnimeButton.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, AnimeView.class);
            intent.putExtra("theAnime", mValues.get(position));
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mNameView;
        public final TextView mGenreView;
        public final ImageView mImageView;
        public final TextView mSynopsisView;
        public final TextView mViewAnimeButton;

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            mImageView = binding.thumbnail;
            mNameView = binding.name;
            mGenreView = binding.genres;
            mSynopsisView = binding.synopsis;
            mViewAnimeButton = binding.viewAnimeButton;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }

}