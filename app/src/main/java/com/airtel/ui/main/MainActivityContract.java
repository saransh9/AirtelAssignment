package com.airtel.ui.main;

import com.airtel.data.ApiCalls;
import com.airtel.data.pojo.ApiData;

/**
 * Created by saransh on 23/03/18.
 */

public interface MainActivityContract {

    void showLoader();
    void hideLoader();

    void dataFetched(Boolean isErrorFound, ApiData data);
}
