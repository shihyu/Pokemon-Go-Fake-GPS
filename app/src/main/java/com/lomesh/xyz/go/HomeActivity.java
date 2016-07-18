package com.lomesh.xyz.go;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import wei.mark.standout.StandOutWindow;

public class HomeActivity extends Activity {


    public Button mStartButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            StandOutWindow.show(this, Joystick.class, StandOutWindow.DEFAULT_ID);
        }
        catch (Exception e){
            Toast.makeText(this,"ERROR 1000.",Toast.LENGTH_SHORT);
        }
        finish();
    }




}
