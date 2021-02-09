package com.example.doorman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttManager;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttQos;
import com.amazonaws.regions.Regions;

import org.json.JSONException;

import java.util.UUID;


public class MainActivity extends AppCompatActivity {
    private CustomDialog dialog;
    private DustDialog dialog1;
    private DeliveryDialog dialog2;

    private int dialog_flag = 0;

    ToggleButton toggleButton;
    ToggleButton toggleButton2;
    ToggleButton toggleButton3;

    static final String LOG_TAG = MainActivity.class.getCanonicalName();

    // --- Constants to modify per your configuration ---

    // Customer specific IoT endpoint
    // AWS Iot CLI describe-endpoint call returns: XXXXXXXXXX.iot.<region>.amazonaws.com,
    private static final String CUSTOMER_SPECIFIC_ENDPOINT = "a30haofaipwzvk-ats.iot.us-east-1.amazonaws.com";

    // Cognito pool ID. For this app, pool needs to be unauthenticated pool with
    // AWS IoT permissions.
    private static final String COGNITO_POOL_ID = "us-east-1:21e3bfd3-f625-40de-a353-ca6dca4b5401";

    // Region of AWS IoT
    private static final Regions MY_REGION = Regions.US_EAST_1;

    TextView tvStatus;

    AWSIotMqttManager mqttManager;
    String clientId;

    CognitoCachingCredentialsProvider credentialsProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button weather = findViewById(R.id.button);
        Button btn_dust = findViewById(R.id.button2);
        Button btn_delivery = findViewById(R.id.button3);

        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_flag = 1;
                Dialog();
            }
        });

        btn_dust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_flag = 2;
                Dialog();
            }
        });

        btn_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_flag = 3;
                Dialog();
            }
        });

        // Camera Fragment 실행
        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new Camera2BasicFragment())
                    .commit();
        }

        toggleButton = findViewById(R.id.tb);
        toggleButton2 = findViewById(R.id.tb_2);
        toggleButton3 = findViewById(R.id.tb_3);



        // MQTT client IDs are required to be unique per AWS IoT account.
        // This UUID is "practically unique" but does not _guarantee_
        // uniqueness.
        clientId = UUID.randomUUID().toString();

        // Initialize the AWS Cognito credentials provider
        credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(), // context
                COGNITO_POOL_ID, // Identity Pool ID
                MY_REGION // Region
        );

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    try {
                        mqttManager.publishString("off", "room1/led", AWSIotMqttQos.QOS0);
                    } catch (Exception e) {
                        Log.e(LOG_TAG, "Publish error.", e);
                    }
                }else{
                    try {
                        mqttManager.publishString("on", "room1/led", AWSIotMqttQos.QOS0);
                    } catch (Exception e) {
                        Log.e(LOG_TAG, "Publish error.", e);
                    }
                }
            }
        });
        toggleButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    try {
                        mqttManager.publishString("off", "room2/led", AWSIotMqttQos.QOS0);
                    } catch (Exception e) {
                        Log.e(LOG_TAG, "Publish error.", e);
                    }
                }else{
                    try {
                        mqttManager.publishString("on", "room2/led", AWSIotMqttQos.QOS0);
                    } catch (Exception e) {
                        Log.e(LOG_TAG, "Publish error.", e);
                    }
                }
            }
        });
        toggleButton3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    try {
                        mqttManager.publishString("off", "room3/led", AWSIotMqttQos.QOS0);
                    } catch (Exception e) {
                        Log.e(LOG_TAG, "Publish error.", e);
                    }
                }else{
                    try {
                        mqttManager.publishString("on", "room3/led", AWSIotMqttQos.QOS0);
                    } catch (Exception e) {
                        Log.e(LOG_TAG, "Publish error.", e);
                    }
                }
            }
        });

        // MQTT Client
        mqttManager = new AWSIotMqttManager(clientId, CUSTOMER_SPECIFIC_ENDPOINT);

        // The following block uses a Cognito credentials provider for authentication with AWS IoT.
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        btnConnect.setEnabled(true);
                    }
                });
            }
        }).start();
        connectMethod connectMethod = new connectMethod();
        connectMethod.method();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if(dialog_flag == 1)
//            dialog.dismiss();
//        else if(dialog_flag == 2)
//            dialog1.dismiss();
//        else
//            dialog2.dismiss();
    }

    //    더보기 창
    public void Dialog() {
        if(dialog_flag == 1) {
            dialog = new CustomDialog(MainActivity.this, leftListener); // 왼쪽 버튼 이벤트
            dialog.setCancelable(true);
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.show();
        }
        else if(dialog_flag == 2){
            dialog1 = new DustDialog(MainActivity.this, leftListener); // 왼쪽 버튼 이벤트
            dialog1.setCancelable(true);
            dialog1.getWindow().setGravity(Gravity.CENTER);
            dialog1.show();
        }
        else {
            dialog2 = new DeliveryDialog(MainActivity.this, leftListener); // 왼쪽 버튼 이벤트
            dialog2.setCancelable(true);
            dialog2.getWindow().setGravity(Gravity.CENTER);
            dialog2.show();
        }
    }
    //다이얼로그 클릭이벤트
    private final View.OnClickListener leftListener = new View.OnClickListener() {
        public void onClick(View v) {

            if(dialog_flag == 1)
                dialog.dismiss();
            else if(dialog_flag == 2)
                dialog1.dismiss();
            else
                dialog2.dismiss();
        }
    };

    public class connectMethod{
        @SuppressLint("SetTextI18n")
        public void method(){
            Log.d("Connecting", "Connecting");
            try {
                mqttManager.connect(credentialsProvider, new AWSIotMqttClientStatusCallback() {
                    @Override
                    public void onStatusChanged(final AWSIotMqttClientStatus status, final Throwable throwable) {
                        Log.d(LOG_TAG, "Status = " + String.valueOf(status));

                    }
                });
            } catch (final Exception e) {
                Log.e(LOG_TAG, "Connection error.", e);
                tvStatus.setText("Error! " + e.getMessage());
            }
        }
    }

    public static class DataEvent {

        public final String helloEventBus;

        public DataEvent(String helloEventBus) {
            this.helloEventBus = helloEventBus;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

}

