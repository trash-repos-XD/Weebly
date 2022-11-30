package com.example.weebly;

import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weebly.placeholder.Content.AnimeSched;
import com.example.weebly.databinding.FragmentItemBinding;

import java.io.InputStream;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link AnimeSched}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<AnimeSched> mValues;

    public MyItemRecyclerViewAdapter(List<AnimeSched> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        new DownloadImageTask(holder.mImageView)
                .execute(mValues.get(position).thumbnail);
        holder.mNameView.setText(mValues.get(position).name);
        holder.mGenreView.setText(mValues.get(position).genres);
        holder.mSynopsisView.setText(mValues.get(position).synopsis);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mNameView;
        public final TextView mGenreView;
        public final ImageView mImageView;
        public final TextView mSynopsisView;

        public AnimeSched mItem;

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            mImageView = binding.thumbnail;
            mNameView = binding.name;
            mGenreView = binding.genres;
            mSynopsisView = binding.synopsis;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        //        TODO: cache the images
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}