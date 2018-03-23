package com.airtel.ui.fullscreenimage;

import javax.inject.Inject;

/**
 * Created by saransh on 23/03/18.
 */

public class FullScreenImagePresenter implements FullScreenImagePresenterContract {


    FullScreenImageView view;

    @Inject
    public FullScreenImagePresenter() {

    }

    public void setView(FullScreenImageView view) {
        this.view = view;
    }
}
