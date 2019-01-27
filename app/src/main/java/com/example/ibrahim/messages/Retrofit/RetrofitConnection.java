package com.example.ibrahim.messages.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ibrahim on 1/27/2019.
 */

public class RetrofitConnection
{

    private static Retrofit retrofit = null;

    public  static Retrofit getInstance()
    {
        if(retrofit == null)
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://spreadsheets.google.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        return retrofit;
    }


} // class of RetrofitConnection
