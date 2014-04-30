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
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ExpandableListView;

public class MealTable extends Activity {

	ListAdapter_Expandable listAdapterExapnd;
	ExpandableListView listViewExpand;
	List<String> listHeaderData = new ArrayList<String>();
	HashMap<String, List<String>> listChildData = new HashMap<String, List<String>>();
	Vector<String> queries = new Vector<String>();
	String loggedInUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meal_table);
		
		Intent prevIntent = getIntent();
		if(prevIntent.getExtras() != null){
			loggedInUser = prevIntent.getExtras().getString("username");
		}
		
		new Thread() {
			public void run() {
				Socket toServer;
				try {
					toServer = new Socket("moore07.cs.purdue.edu", 3001);
					PrintWriter printwriter = new PrintWriter(
							toServer.getOutputStream(), true);
					// asks server for all info
					String command = new String("GET-USER-INFO|root|password|"+loggedInUser);
					printwriter.println(command);
					BufferedReader in = new BufferedReader(new InputStreamReader(toServer.getInputStream()));
					
					String serverResponse;
					while((serverResponse = in.readLine()) != null)
					{
						queries.add(serverResponse);
					}

					for(int i = 0; i < queries.size(); i++)
					{
						String query = queries.get(i);
						String queryArray[] = query.split("\\|");

						if(listChildData.get(queryArray[3]) != null)
						{

							List<String> list = listChildData.get(queryArray[3]);
							list.add(queryArray[1] + "-" + queryArray[2]);
							listChildData.put(queryArray[3], list);
						}

						else
						{

							List<String> list = new ArrayList<String>();
							list.add(queryArray[1] + "-" + queryArray[2]);
							listChildData.put(queryArray[3], list);
						}
					}
					
					for(String key : listChildData.keySet())
					{
						listHeaderData.add(key);
					}



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


		// get the listview
		listViewExpand = (ExpandableListView) findViewById(R.id.listViewMain);

		listAdapterExapnd = new ListAdapter_Expandable(this, listHeaderData,
				listChildData);

		// setting list adapter
		listViewExpand.setAdapter(listAdapterExapnd);
	}
}
