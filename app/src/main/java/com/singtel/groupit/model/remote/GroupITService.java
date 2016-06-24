package com.singtel.groupit.model.remote;


import com.singtel.groupit.model.TestResponse;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by lanna on 6/20/16.
 *
 */

public interface GroupITService {

    /*
        ENDPOINT
     */
    String ENDPOINT = " http://demo1023649.mockable.io"; // test
    byte READ_TIME_OUT_IN_SECOND = 5;
    byte CONNECTION_TIME_OUT_IN_SECOND = 5;


    /*
        Factory / Retrofit Setup
     */
    class Factory {
        public static GroupITService create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(GroupITService.ENDPOINT)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getRequestHeader())
                    .build();
            return retrofit.create(GroupITService.class);
        }

        private static OkHttpClient getRequestHeader() {
            return new OkHttpClient().newBuilder()
                    .readTimeout(READ_TIME_OUT_IN_SECOND, TimeUnit.SECONDS)
                    .connectTimeout(CONNECTION_TIME_OUT_IN_SECOND, TimeUnit.SECONDS)
                    .build();
        }
    }


    /*
        API Defines
     */

    /**
     * Return a list of articles.
     */
    @GET("/main")
    Observable<TestResponse> getTopStories();
}
