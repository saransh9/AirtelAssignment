package com.airtel.utils;

import android.view.View;

/**
 * Created by saransh on 23/03/18.
 */

public interface ViewClickListener extends View.OnClickListener {
    @Override
    void onClick(View v);
    void loadMoreData();
}
