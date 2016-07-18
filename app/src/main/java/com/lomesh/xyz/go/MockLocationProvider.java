package com.lomesh.xyz.go;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Lomesh on 7/10/2016.
 */
public class MockLocationProvider {
    public String providerName;
    public Context ctx;

    public MockLocationProvider(String name, Context ctx) {
        this.providerName = name;
        this.ctx = ctx;

        LocationManager lm = (LocationManager) ctx.getSystemService(
                Context.LOCATION_SERVICE);
        try {
            lm.addTestProvider(providerName, false, false, false, false, false,
                    true, true, 0, 5);
            lm.setTestProviderEnabled(providerName, true);
        }
        catch(Exception ex){
            Toast.makeText(ctx,"Enable mock location in developer settings.", Toast.LENGTH_LONG);
        }

    }

    public void pushLocation(double lat, double lon,double alt,float accuracy) {
        LocationManager lm = (LocationManager) ctx.getSystemService(
                Context.LOCATION_SERVICE);

        Location mockLocation = new Location(providerName);
        mockLocation.setLatitude(lat);
        mockLocation.setLongitude(lon);
        mockLocation.setAltitude(alt);
        mockLocation.setTime(System.currentTimeMillis());
        mockLocation.setAccuracy(accuracy);
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mockLocation.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
        }
        Log.d("MLocationProvoder", "Setting location....");
        try {
            lm.setTestProviderLocation(providerName, mockLocation);
        }
        catch(Exception ex){
            Toast.makeText(ctx,"Enable mock location in developer settings.", Toast.LENGTH_LONG);
        }
        Log.d("MLocationProvoder", "Location set!");

    }

    public void shutdown() {
        LocationManager lm = (LocationManager) ctx.getSystemService(
                Context.LOCATION_SERVICE);
        Log.d("MLocationProvoder", "SHUTDOWN");
        lm.removeTestProvider(providerName);
    }
}