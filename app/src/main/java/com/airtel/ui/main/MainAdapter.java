package com.airtel.ui.main;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.airtel.R;
import com.airtel.data.pojo.ApiData;
import com.airtel.utils.GlideApp;
import com.airtel.utils.RatioImageView;
import com.airtel.utils.Utility;
import com.airtel.utils.ViewClickListener;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;

/**
 * Created by saransh on 23/03/18.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.viewHolder> {

    ApiData apiData;
    private Context context;
    private ViewClickListener mListener;


    public MainAdapter(ApiData apiData, Context context, final ViewClickListener listener) {

        this.apiData = apiData;
        this.context = context;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        itemView.setOnClickListener(mListener);


        return new viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, int position) {
        int width_new = Utility.getScreenWidth(context);
        int width = Integer.parseInt(apiData.getWallpapers()[position].getWidth());
        int height = Integer.parseInt(apiData.getWallpapers()[position].getHeight());
        int height_new = (int) (((double) height / (double) width) * (double) width_new);

        if (position == apiData.getWallpapers().length - 2)
            mListener.loadMoreData();           //loads more data from api

        GlideApp.with(context)
                .load(apiData.getWallpapers()[position].getUrl_thumb())
                .centerCrop()
                .override(width_new, height_new)
                .signature(new ObjectKey(apiData.getWallpapers()[position].getUrl_thumb()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.images.setBackground(null);

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(holder.images);

    }

@Override
public int getItemCount(){
        return apiData.getWallpapers().length;
        }

class viewHolder extends RecyclerView.ViewHolder {
    RatioImageView images;

    viewHolder(View itemView) {
        super(itemView);
        images = (RatioImageView) itemView.findViewById(R.id.list_image);
    }
}

}
