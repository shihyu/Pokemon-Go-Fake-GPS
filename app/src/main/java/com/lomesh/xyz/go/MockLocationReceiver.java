package com.lomesh.xyz.go;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;

/**
 * Created by Lomesh on 7/10/2016.
 */
public class MockLocationReceiver  extends BroadcastReceiver {
    public MockLocationReceiver() {
    }

    public MockLocationProvider mockGPS;
    public MockLocationProvider mockWifi;
    public String logTag="GPS mock MyReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {



        if (intent.getAction().equals("com.xyz.go.stopMock")) {
            if (mockGPS!=null) {
                mockGPS.shutdown();
            }
            if (mockWifi!=null) {
                mockWifi.shutdown();
            }
        }
        else {
            mockGPS = new MockLocationProvider(LocationManager.GPS_PROVIDER, context);
            mockWifi = new MockLocationProvider(LocationManager.NETWORK_PROVIDER, context);

            double lat, lon, alt;
            float accurate;

            lat = Double.parseDouble(intent.getStringExtra("lat") != null ? intent.getStringExtra("lat") : "0");
            lon = Double.parseDouble(intent.getStringExtra("lon") != null ? intent.getStringExtra("lon") : "0");
            alt = Double.parseDouble(intent.getStringExtra("alt") != null ? intent.getStringExtra("alt") : "0");
            accurate = Float.parseFloat(intent.getStringExtra("accurate") != null ? intent.getStringExtra("accurate") : "0");
            Log.i(logTag, String.format("setting mock to Latitude=%f, Longitude=%f Altitude=%f Accuracy=%f", lat, lon, alt, accurate));
            mockGPS.pushLocation(lat, lon, alt, accurate);
            mockWifi.pushLocation(lat, lon, alt, accurate);

        }
    }
}
