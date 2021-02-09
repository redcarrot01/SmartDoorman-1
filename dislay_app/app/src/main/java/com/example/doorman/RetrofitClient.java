package com.example.doorman;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://u8pw7xm0o9.execute-api.us-east-1.amazonaws.com/dev/";
    private static final String BASE_URL1 = "https://rqk2xly83a.execute-api.us-east-1.amazonaws.com/dev/";

    // DynamoDB의 날씨 데이터를 읽어주는 API Gateway 엔드포인트 호출
    public static TestService getApiService() {
        return getInstance().create(TestService.class);
    }

    private static Retrofit getInstance() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    // DynamoDB의 택배 데이터를 읽어주는 API Gateway 엔드포인트 호출
    public static TestService getApiDeliveryService() {
        return dynamoDelivery().create(TestService.class);
    }

    private static Retrofit dynamoDelivery() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL1)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
