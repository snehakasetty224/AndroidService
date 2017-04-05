package com.example.sneha.androidservices;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.EditText;

import java.net.URL;

public class PDFDownloadActivity extends Activity {

    BoundService boundService;
    boolean isBound = false;
    Intent i;

    EditText editText1;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    EditText editText5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfdownload);
        i = new Intent(getBaseContext(), BoundService.class);

        editText1=(EditText)findViewById(R.id.editText);
        editText2=(EditText)findViewById(R.id.editText2);
        editText3=(EditText)findViewById(R.id.editText3);
        editText4=(EditText)findViewById(R.id.editText4);
        editText5=(EditText)findViewById(R.id.editText6);

//        intentFilter = new IntentFilter();
//        intentFilter.addAction("FILE_DOWNLOADED_ACTION");
//
//        registerReceiver(intentReceiver, intentFilter);

    }

//    public void clickThis(View view){
//        // Create an Explicit Intent
//        Intent intent = new Intent(this, StartedService.class);
//        // Set some data that the Service might require/use
//    try {
//        URL[] urls = new URL[]{
//        new URL("http://www.cisco.com/web/offer/emear/38586/images/Presentations/P3.pdf"),
//        new URL("http://www.cisco.com/c/dam/en_us/about/annual-report/2016-annual-report-full.pdf (Links to an external site"),
//        new URL("http://www.cisco.com/web/about/ac79/docs/innov/IoE_Economy.pdf"),
//        new URL("http://www.cisco.com/web/strategy/docs/gov/everything-for-cities.pdf"),
//        new URL("http://www.cisco.com/web/offer/gist_ty2_asset/Cisco_2014_ASR.pdf")};
//        intent.putExtra("urls", urls);
//    }catch(Exception e){
//
//    }
//        // Start the Service
//        startService(intent);
//    }
//
//
//    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            Toast.makeText(getBaseContext(), "File downloaded!",
//                    Toast.LENGTH_LONG).show();
//        }
//    };

    public void clickThis(View view){
        Intent intent = new Intent(this, BoundService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    public void clickStop(View view){
        stopService(i);
    }

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            BoundService.MyBinder binder = (BoundService.MyBinder) iBinder;
            boundService = binder.getService();
            isBound = true;
            try {
                URL[] urls = new URL[]{
                        new URL(editText1.getText().toString()),
                        new URL(editText2.getText().toString()),
                        new URL(editText3.getText().toString()),
                        new URL(editText4.getText().toString()),
                        new URL(editText5.getText().toString())
                };
                boundService.urls = urls;
            }catch(Exception e){

            }
            startService(i);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            boundService = null;
            isBound = false;
        }
    };
}
