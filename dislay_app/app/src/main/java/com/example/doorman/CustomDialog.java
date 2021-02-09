package com.example.doorman;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomDialog extends Dialog {
    private final View.OnClickListener mLeftClickListener;

    String str_weather = null;
    String str_cur_temp = null;
    String str_max_temp = null;
    String str_min_tempo = null;

    Weather weather;

    Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        lpWindow.width = WindowManager.LayoutParams.MATCH_PARENT;
        lpWindow.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.custom_dialog);

        Button mLeftButton = findViewById(R.id.btnDustClose);
        TextView weather = findViewById(R.id.textView);
        TextView cur_temp = findViewById(R.id.textView6);
        TextView max_temp = findViewById(R.id.textView10);
        TextView min_tempo = findViewById(R.id.textView11);

        @SuppressLint("HandlerLeak")
        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                weather.setText(str_weather);
                cur_temp.setText(str_cur_temp);
                max_temp.setText(str_max_temp);
                min_tempo.setText(str_min_tempo);

            }
        };

        new Thread() {
            public void run() {
                Call<Weather> getTest = RetrofitClient.getApiService().getTest();
                getTest.enqueue(new Callback<Weather>() {

                    @Override
                    public void onResponse(Call<Weather> call, Response<Weather> response) {
                        if (response.isSuccessful()) {
                            CustomDialog.this.weather = response.body();
                            Log.d("Retrofit2 Success", "response.raw :" + response.raw());
                            str_weather = CustomDialog.this.weather.getItem().getArea();
                            str_cur_temp = CustomDialog.this.weather.getItem().getCur_temp();
                            str_max_temp = CustomDialog.this.weather.getItem().getMax_temp();
                            str_min_tempo = CustomDialog.this.weather.getItem().getMin_temp();

                            Message msg = handler.obtainMessage();
                            handler.sendMessage(msg);
                        }
                    }

                    @Override
                    public void onFailure(Call<Weather> call, Throwable t) {
                        Log.e("Retrofit2 Error","정보 불러오기 실패 :" + t.getMessage() );
                        Log.e("Retrofit2 Error","요청 메시지 :" + call.request());
                    }
                });
            }
        }.start();


        // 클릭 이벤트 셋팅
        if (mLeftClickListener != null) {
            mLeftButton.setOnClickListener(mLeftClickListener);
        }
    }

    // 클릭버튼이 하나일때 생성자 함수로 클릭이벤트를 받는다.
    public CustomDialog(Context context, View.OnClickListener singleListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mLeftClickListener = singleListener;
    }
}
