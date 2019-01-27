package com.example.ibrahim.messages.Retrofit;

import com.example.ibrahim.messages.Model.SpreadSheet;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Ibrahim on 1/27/2019.
 */

public interface MessageRequest
{
    String url = "feeds/list/0Ai2EnLApq68edEVRNU0xdW9QX1BqQXhHRl9sWDNfQXc/od6/public/basic?alt=json";

    @GET(url)
    Call<SpreadSheet> getMessages();
}
