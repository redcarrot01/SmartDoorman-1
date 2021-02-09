package com.example.doorman;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DustDialog extends Dialog {
    private final View.OnClickListener mLeftClickListener;

    String dustStr = null;
    String ultraDustStr = null;
    Weather weather;

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

        setContentView(R.layout.dust_dialog);

        Button mLeftButton = findViewById(R.id.btnDustClose);
        TextView dust = findViewById(R.id.textView14);
        TextView ultraDust = findViewById(R.id.textView15);


        @SuppressLint("HandlerLeak")
        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                dust.setText(dustStr);
                ultraDust.setText(ultraDustStr);
            }
        };

        new Thread() {
            public void run() {
                Call<Weather> getTest = RetrofitClient.getApiService().getTest();
                getTest.enqueue(new Callback<Weather>() {

                    @Override
                    public void onResponse(Call<Weather> call, Response<Weather> response) {
                        if (response.isSuccessful()) {
                            weather = response.body();
                            Log.d("Retrofit2 Success", "response.raw :" + response.raw());
                            dustStr = weather.getItem().getDust();
                            ultraDustStr = weather.getItem().getUltra_dust();

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

        if (mLeftClickListener != null) {
            mLeftButton.setOnClickListener(mLeftClickListener);
        }
    }

    // 클릭버튼이 하나일때 생성자 함수로 클릭이벤트를 받는다.
    public DustDialog(Context context, View.OnClickListener singleListener)  {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mLeftClickListener = singleListener;
    }
}
