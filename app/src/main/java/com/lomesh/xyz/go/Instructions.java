package com.lomesh.xyz.go;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Instructions extends Activity {

    Button gotIt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        SharedPreferences prefs = getApplication().getSharedPreferences("coordinates", Context.MODE_PRIVATE);
        prefs.edit().putBoolean("firstTime",false).apply();

        gotIt=(Button)findViewById(R.id.gotIt);

        gotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(), "ERROR 1001.", Toast.LENGTH_SHORT);
                }
                try {
                    startActivity(new Intent(android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION));
                    finish();
                }
                catch (Exception ex){
                    Toast.makeText(getApplicationContext(),"ERROR 1002.",Toast.LENGTH_SHORT);
                }
            }
        });


    }
}
