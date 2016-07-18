package com.lomesh.xyz.go;

import wei.mark.standout.StandOutWindow;
import wei.mark.standout.constants.StandOutFlags;
import wei.mark.standout.ui.Window;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class Joystick extends StandOutWindow {

	public Thread t;
	public boolean running=true;

	public Button upButton,downButton,leftButton,rightButton;
	public double latitude=40.714191,longitude=-74.006293,del=0.00001;


	public static SharedPreferences allPrefs=null;


	@Override
	public String getPersistentNotificationMessage(int id) {
		return "Click to close.";
	}

	@Override
	public Intent getPersistentNotificationIntent(int id) {
		return StandOutWindow.getCloseIntent(this, Joystick.class, id);
	}

	@Override
	public String getAppName() {
		return "Joystick";
	}

	@Override
	public int getAppIcon() {
		return android.R.drawable.ic_menu_close_clear_cancel;
	}

	@Override
	public boolean onClose(int id, Window window){
		super.onClose(id,window);

		running=false;

		Toast.makeText(getApplicationContext(),"Joystick closed.",Toast.LENGTH_SHORT).show();

		return false;
	}

	@Override
	public void createAndAttachView(int id, FrameLayout frame) {
		// create a new layout from body.xml
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View view =inflater.inflate(R.layout.simple, frame, true);
		running=true;

		allPrefs = getApplication().getSharedPreferences("coordinates", Context.MODE_PRIVATE);


		String l=getPrefs().getString("latitude", "40.714191");
		String ln=getPrefs().getString("longitude", "-74.006293");
		String delta=getPrefs().getString("delta", "0.00001");



		try{
			latitude=Double.parseDouble(l);
			longitude=Double.parseDouble(ln);
			del=Double.parseDouble(delta);
		}
		catch (Exception e){

		}




		upButton=(Button)view.findViewById(R.id.upButton);
		upButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("Clickers","Up");
				latitude+=del;
				getPrefs().edit().putString("latitude",""+latitude).apply();
				getPrefs().edit().putString("longitude",""+longitude).apply();
			}
		});



		downButton=(Button)view.findViewById(R.id.downButton);
		downButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("Clickers","Down");
				latitude-=del;
				getPrefs().edit().putString("latitude",""+latitude).apply();
				getPrefs().edit().putString("longitude",""+longitude).apply();
			}
		});
		leftButton=(Button)view.findViewById(R.id.leftButton);
		leftButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("Clickers","Left");
				longitude-=del;
				getPrefs().edit().putString("latitude",""+latitude).apply();
				getPrefs().edit().putString("longitude",""+longitude).apply();
			}
		});
		rightButton=(Button)view.findViewById(R.id.rightButton);
		rightButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("Clickers","Right");
				longitude+=del;
				getPrefs().edit().putString("latitude",""+latitude).apply();
				getPrefs().edit().putString("longitude",""+longitude).apply();
			}
		});



		t=new Thread(new Runnable() {
			@Override
			public void run() {
				while(running){
					String l=getPrefs().getString("latitude", "40.714191");
					String ln=getPrefs().getString("longitude","-74.006293");
					double lt=40.714191,longt=-74.006293;
					try{
						lt=Double.parseDouble(l);
						longt=Double.parseDouble(ln);

					}
					catch (Exception e){

					}
					updateLocation(lt,longt);
					try {
						Thread.sleep(4000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});

		t.start();



	}

	public static synchronized SharedPreferences getPrefs(){

		return allPrefs;
	}


    public void updateLocation(double lat, double lon){
		Intent intent = new Intent();
		intent.putExtra("lat",String.valueOf(lat));
		intent.putExtra("lon",String.valueOf(lon));
		intent.setAction("com.xyz.go.updateLocation");
		sendBroadcast(intent);
	}

	// the window will be centered
	@Override
	public StandOutLayoutParams getParams(int id, Window window) {
		return new StandOutLayoutParams(id, 250, 300,
				StandOutLayoutParams.CENTER, StandOutLayoutParams.CENTER);
	}

	// move the window by dragging the view
	@Override
	public int getFlags(int id) {
		return super.getFlags(id) | StandOutFlags.FLAG_BODY_MOVE_ENABLE
				| StandOutFlags.FLAG_WINDOW_FOCUSABLE_DISABLE;
	}

}
