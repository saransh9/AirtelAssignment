package com.airtel.ui.fullscreenimage;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.airtel.R;
import com.airtel.databinding.ActivityFullscreenimageBinding;
import com.airtel.ui.base.BaseActivity;
import com.airtel.utils.GlideApp;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;

import javax.inject.Inject;

/**
 * Created by saransh on 23/03/18.
 */

public class FullScreenImageView extends BaseActivity implements FullScreenImageViewContract {

    @Inject
    FullScreenImagePresenter presenter;
    ActivityFullscreenimageBinding binding;
    String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fullscreenimage);
        getActivityComponent().inject(this);
        presenter.setView(this);
        Bundle b = getIntent().getExtras();

        if (b != null) {
            url = b.getString("image_url");
        }

        loadImage();

    }

    void loadImage() {
        GlideApp.with(this)
                .load(url)
                .centerCrop()
                .signature(new ObjectKey(url))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        binding.fullImage.setBackground(null);

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(binding.fullImage);
    }
}
