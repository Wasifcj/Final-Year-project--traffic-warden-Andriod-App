package com.cjz.wasif.trafficwarden;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    private TextView driver_i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        String dirverid=getIntent().getStringExtra("Driver ID");

        driver_i.setText("Driver ID: "+dirverid);

      //  Log.i("DriverID",dirverid);
    }
}
