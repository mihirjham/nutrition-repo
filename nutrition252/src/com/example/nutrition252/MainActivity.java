package com.example.nutrition252;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements
		AdapterView.OnItemSelectedListener, OnClickListener {

	private Spinner spinner;
	private TextView username;
	private Button postIt;
	private Button graph;
	private Button mealTable;
	private EditText manualCalories;
	private Intent intent;
	private Button logout;
	String left, right, text,loggedInUser;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Intent prevIntent = getIntent();
		if(prevIntent.getExtras() != null){
			loggedInUser = prevIntent.getExtras().getString("username");
		}

		logout = (Button) findViewById(R.id.bLogout);
		username = (TextView) findViewById(R.id.tvUserName);
		username.setText(loggedInUser);
		postIt = (Button) findViewById(R.id.bPostIt);
		graph = (Button) findViewById(R.id.bGraph);
		mealTable = (Button) findViewById(R.id.bMealTable);
		manualCalories = (EditText) findViewById(R.id.etCalories);
		spinner = (Spinner) findViewById(R.id.spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.calories,
				android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
		
		postIt.setOnClickListener(this);
		graph.setOnClickListener(this);
		mealTable.setOnClickListener(this);
		logout.setOnClickListener(this);


	}

	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.bPostIt) {
			// Send data entered to server
			new Thread() {
				public void run() {
					Socket toServer;
					try {
						toServer = new Socket("moore07.cs.purdue.edu", 3001);
						PrintWriter printwriter = new PrintWriter(
								toServer.getOutputStream(), true);
						sendToServer(printwriter);
						toServer.close();			
					} catch (IllegalArgumentException iae) {
					} catch (UnknownHostException uhe) {
					} catch (SecurityException se) {
					} catch (IOException ioe) {
					}
				}
			}.start();
			
			Intent newIntent = new Intent(this, MainActivity.class);
			newIntent.putExtra("username", loggedInUser);
			startActivityForResult(newIntent, 0);
			
		} else if (id == R.id.bGraph) {
			intent = new Intent(v.getContext(), Graph.class);
			intent.putExtra("username", loggedInUser);
			startActivityForResult(intent, 0);
		} else if (id == R.id.bMealTable) {
			intent = new Intent(v.getContext(), MealTable.class);
			intent.putExtra("username", loggedInUser);
			startActivityForResult(intent, 0);
		}else if(id == R.id.bLogout){
			intent = new Intent(v.getContext(), Login.class);
			startActivityForResult(intent, 0);
	
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		int indexPosition = spinner.getSelectedItemPosition();
		// Log.d("Position", "Selected position at: " + indexPosition);

		if (position >= 1) {
			text = spinner.getSelectedItem().toString();
			// Log.d("Selected",text);
			int counter = text.indexOf('-');
			if (counter >= 0) {
				left = text.substring(0, counter);
				right = text.substring(counter + 1);
				// Log.d("First",left);
				// Log.d("Second",right);
				manualCalories.setText(right, TextView.BufferType.EDITABLE);
			} else {
				Log.d("Error", "There is no -");
			}
		} else if (position == 0) {
			manualCalories.setText(" ");
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

	// sends info to server and closes printwriter
	public void sendToServer(PrintWriter printwriter) {
		String command = "INSERT-MEAL|" + "root|password|" + loggedInUser +"|";
		// this
		String selectedItem = spinner.getSelectedItem().toString();
		selectedItem = selectedItem.replace('-', '|');// separate the food item
														// and calorie number
		command = command + selectedItem;
		printwriter.print(command);
		printwriter.close();
	}

	@Override
	public void onBackPressed() {
		return;
	}

}
