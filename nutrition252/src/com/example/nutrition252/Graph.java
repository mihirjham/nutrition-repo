package com.example.nutrition252;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Graph extends Activity implements OnClickListener {
	Button MealTableGraph;
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.graph);
		MealTableGraph = (Button) findViewById(R.id.mealTableButtonGraphWindow);
		MealTableGraph.setOnClickListener(this);

		new Thread() {
			public void run() {
				Socket toServer;
				PrintWriter printwriter;
				ObjectInputStream tableInfo;
				try {
					toServer = new Socket("sslab01.cs.purdue.edu", 5555);
					printwriter = new PrintWriter(toServer.getOutputStream(),
							true);
					// asks server to return only dates and calories to plot on
					// graph
					String command = new String("GET-GRAPH-INFO|root|password");// need
																				// to
																				// add
																				// username
					printwriter.print(command);
					tableInfo = new ObjectInputStream(toServer.getInputStream());
					Vector<String> info = (Vector<String>) tableInfo
							.readObject();
					// need to add code to display the info obtained from server
					printwriter.close();
					toServer.close();
					tableInfo.close();
				} catch (IllegalArgumentException iae) {
				} catch (UnknownHostException uhe) {
				} catch (SecurityException se) {
				} catch (IOException ioe) {
				} catch (ClassNotFoundException cnfe) {
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
