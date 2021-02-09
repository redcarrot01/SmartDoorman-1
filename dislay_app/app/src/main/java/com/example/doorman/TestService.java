package com.example.doorman;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TestService {
    @GET("items")
    Call<Weather> getTest();

    @GET("delivery")
    Call<Delivery> getDelivery();
}
