package com.example.nutrition252;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ExpandableListView;

public class MealTable extends Activity {

	ListAdapter_Expandable listAdapterExapnd;
	ExpandableListView listViewExpand;
	List<String> listHeaderData;
	HashMap<String, List<String>> listChildData;
	String loggedInUser;
	Intent prevIntent = getIntent();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meal_table);
		
		if(prevIntent.getExtras() != null){
			loggedInUser = prevIntent.getExtras().getString("username");
		}

		// get the listview
		listViewExpand = (ExpandableListView) findViewById(R.id.listViewMain);

		// preparing list data
		prepareListData();

		listAdapterExapnd = new ListAdapter_Expandable(this, listHeaderData,
				listChildData);

		// setting list adapter
		listViewExpand.setAdapter(listAdapterExapnd);
		new Thread() {
			public void run() {
				Socket toServer;
				char [] requestedInfo = new char[1024];
				try {
					toServer = new Socket("sslab01.cs.purdue.edu", 5555);
					PrintWriter printwriter = new PrintWriter(
							toServer.getOutputStream(), true);
					// asks server for all info
					String command = new String("GET-USER-INFO-CURRENT|root|password|"+loggedInUser);
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

	/*
	 * Preparing the list data
	 */
	private void prepareListData() {
		listHeaderData = new ArrayList<String>();
		listChildData = new HashMap<String, List<String>>();

		// Adding child data
		listHeaderData.add("Monday 23/04/2104");
		listHeaderData.add("Tuesday 24/04/2014");
		listHeaderData.add("Wednesday 25/04/2014");

		// Adding child data
		List<String> dayOne = new ArrayList<String>();
		dayOne.add("Chicekn - 200 calories");
		dayOne.add("Chicekn - 200 calories");
		dayOne.add("Chicekn - 200 calories");
		dayOne.add("Chicekn - 200 calories");
		dayOne.add("Chicekn - 200 calories");

		List<String> dayTwo = new ArrayList<String>();
		dayTwo.add("Chicekn - 200 calories");
		dayTwo.add("Chicekn - 200 calories");
		dayTwo.add("Chicekn - 200 calories");
		dayTwo.add("Chicekn - 200 calories");
		dayTwo.add("Chicekn - 200 calories");

		List<String> dayThree = new ArrayList<String>();
		dayThree.add("Chicekn - 200 calories");
		dayThree.add("Chicekn - 200 calories");
		dayThree.add("Chicekn - 200 calories");
		dayThree.add("Chicekn - 200 calories");
		dayThree.add("Chicekn - 200 calories");

		listChildData.put(listHeaderData.get(0), dayOne); // Header, Child data
		listChildData.put(listHeaderData.get(1), dayTwo);
		listChildData.put(listHeaderData.get(2), dayThree);
	}
}
