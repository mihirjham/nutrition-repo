package com.example.nutrition252;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Graph extends Activity implements OnClickListener {
	Button MealTableGraph;
	Intent intent, prevIntent = getIntent();
	String loggedInUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.graph);
		MealTableGraph = (Button) findViewById(R.id.mealTableButtonGraphWindow);
		MealTableGraph.setOnClickListener(this);
		
		if(prevIntent.getExtras() != null){
			loggedInUser = prevIntent.getExtras().getString("username");
		}
		
		new Thread() {
			public void run() {
				Socket toServer;
				PrintWriter printwriter;
				char [] requestedInfo = new char[1024];
				try {
					toServer = new Socket("sslab01.cs.purdue.edu", 5555);
					printwriter = new PrintWriter(toServer.getOutputStream(),
							true);
					// asks server to return only dates and calories to plot on
					// graph
					Date date = new Date();
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					int month = cal.get(Calendar.MONTH);
					month += 1;
					String command = new String("GET-GRAPH-INFO|root|password|"+loggedInUser+"|"+month);
					printwriter.print(command);
					BufferedReader in = new BufferedReader(new InputStreamReader(toServer.getInputStream()));
					in.read(requestedInfo);
					// need to add code to display the info obtained from server
					printwriter.close();
					toServer.close();
					in.close();
				} catch (IllegalArgumentException iae) {
				} catch (UnknownHostException uhe) {
				} catch (SecurityException se) {
				} catch (IOException ioe) {
				}
			}
		}.start();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		if (id == R.id.mealTableButtonGraphWindow) {
			intent = new Intent(v.getContext(), MealTable.class);
			startActivityForResult(intent, 0);
		}

	}
}
