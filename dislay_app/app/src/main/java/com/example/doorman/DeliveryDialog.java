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

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryDialog extends Dialog  {
    private final View.OnClickListener mLeftClickListener;

    String strDelivery = null;
    Delivery delivery;

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

        setContentView(R.layout.delivery_dialog);

        Button mLeftButton = findViewById(R.id.btnDeliveryClose);
        TextView tv_delivery_test = findViewById(R.id.textView16);



        @SuppressLint("HandlerLeak")
        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                tv_delivery_test.setText(strDelivery);

            }
        };

        new Thread() {
            public void run() {
                Call<Delivery> getDelivery = RetrofitClient.getApiDeliveryService().getDelivery();
                getDelivery.enqueue(new Callback<Delivery>() {

                    @Override
                    public void onResponse(Call<Delivery> call, Response<Delivery> response) {
                        if (response.isSuccessful()) {
                            delivery = response.body();
                            Log.d("Retrofit2 Success", "response.raw :" + response.raw());
                            strDelivery = delivery.getItem().getTrackingDetails().getTimeString();

                            Message msg = handler.obtainMessage();
                            handler.sendMessage(msg);
                        }
                    }

                    @Override
                    public void onFailure(Call<Delivery> call, Throwable t) {
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

    public DeliveryDialog(@NonNull Context context, View.OnClickListener singleListener) {
        super(context);
        this.mLeftClickListener = singleListener;
    }
}
