package com.lomesh.xyz.go;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import wei.mark.standout.StandOutWindow;


public class MainActivity extends Activity {

    public String logTag = "PokemonGo";

    public String l,ln,loc;

    public EditText latText,longText,placeText;
    public Button searchButton,goButton;
    public double latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setContentView(R.layout.activity_main);
        try {
            SharedPreferences prefs = getApplication().getSharedPreferences("coordinates", Context.MODE_PRIVATE);


            boolean first = prefs.getBoolean("firstTime", true);

            if (first) {
                Intent inte = new Intent(this, Instructions.class);
                startActivity(inte);
                finish();
            }


            latText = (EditText) findViewById(R.id.latText);
            longText = (EditText) findViewById(R.id.longText);


            l = prefs.getString("latitude", "40.714191");
            ln = prefs.getString("longitude", "-74.006293");
            loc = prefs.getString("location", "New York");
            final String del = prefs.getString("delta", "0.00001");

            latText.setText(l);
            longText.setText(ln);

            placeText = (EditText) findViewById(R.id.placeText);
            placeText.setText(loc);


            goButton = (Button) findViewById(R.id.goButton);
            searchButton = (Button) findViewById(R.id.searchButton);


            StandOutWindow.closeAll(getApplicationContext(), Joystick.class);

            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    setCoordinates();
                }
            });

            goButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    RadioGroup speedGroup = (RadioGroup) findViewById(R.id.speedGroup);

                    int selectedSpeed = speedGroup.getCheckedRadioButtonId();
                    String delta = "0.00001";
                    switch (selectedSpeed) {
                        case R.id.slow:
                            delta = "0.00001";
                            break;
                        case R.id.fast:
                            delta = "0.000025";
                            break;
                        case R.id.fastest:
                            delta = "0.00004";
                            break;
                    }


                    if (!latText.getText().toString().isEmpty() && !longText.getText().toString().isEmpty()) {
                        l = latText.getText().toString();
                        ln = longText.getText().toString();
                    }


                    SharedPreferences prefs = getApplication().getSharedPreferences("coordinates", Context.MODE_PRIVATE);
                    prefs.edit().putString("latitude", l).apply();
                    prefs.edit().putString("longitude", "" + ln).apply();
                    prefs.edit().putString("location", placeText.getText().toString()).apply();
                    prefs.edit().putString("delta", delta).apply();


                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(i);
                    finish();

                }
            });
        }
        catch (Exception ex){
            Toast.makeText(this, "ERROR 1004.", Toast.LENGTH_SHORT);
        }

    }




    public void setCoordinates(){

        String myLocation="";

        myLocation+=placeText.getText().toString();

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocationName(myLocation, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(addresses.size()>0) {
            Address address = addresses.get(0);
             longitude = address.getLongitude();
             latitude = address.getLatitude();



            latText.setText("" + latitude);
            longText.setText("" + longitude);

            l=latText.getText().toString();
            ln=longText.getText().toString();
        }
        else{
            latText.setText("40.714191");
            longText.setText("-74.006293");
            placeText.setText("New York");

            l=latText.getText().toString();
            ln=longText.getText().toString();
        }
    }



}
