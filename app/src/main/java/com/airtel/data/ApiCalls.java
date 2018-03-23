package com.airtel.data;

import com.airtel.data.pojo.ApiData;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by saransh on 23/03/18.
 */
public interface ApiCalls {

    @GET("https://wall.alphacoders.com/api2.0/get.php?auth=f775140bd17fbd6cdb994dfc106a4b38&method=newest&info_level=1")
    Observable<ApiData> fetchNewest(@Query("page") String key);

    @GET("https://wall.alphacoders.com/api2.0/get.php?auth=f775140bd17fbd6cdb994dfc106a4b38&method=highest_rated&info_level=1")
    Observable<ApiData> fetchHightestRated(@Query("page") String key);
}
