package com.example.nutrition252;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

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


public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener, OnClickListener {

	private Spinner spinner;
	private TextView username;
	private Button postIt;
	private Button graph;
	private Button mealTable;
	private EditText manualCalories;
	private Intent intent;
	String left,right,text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		username = (TextView) findViewById(R.id.tvUserName);
		postIt = (Button) findViewById(R.id.bPostIt);
		graph = (Button) findViewById(R.id.bGraph);
		mealTable = (Button) findViewById(R.id.bMealTable);
		manualCalories = (EditText) findViewById(R.id.etCalories);
		spinner = (Spinner) findViewById(R.id.spinner);
		ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.calories, android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);;
		
		postIt.setOnClickListener( this);
		graph.setOnClickListener(this);
		mealTable.setOnClickListener(this);
		
	}
	
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.bPostIt) {
			//Send data entered to server
			Socket toServer;
			try{
				toServer = new Socket("sslab01.cs.purdue.edu",5555);
				PrintWriter printwriter = new PrintWriter(toServer.getOutputStream(),true);
				sendToServer(printwriter);
				toServer.close();
			}
			catch(IOException ioe){
				Toast.makeText(this, "Couldn't Connect to Host", Toast.LENGTH_SHORT).show();
			}
		} else if (id == R.id.bGraph) {
			intent = new Intent(v.getContext(), Graph.class);
			startActivityForResult(intent, 0);
		} else if (id == R.id.bMealTable) {
			intent = new Intent(v.getContext(), MealTable.class);
			startActivityForResult(intent, 0);
		} 
	}
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		TextView myText = (TextView) view;
		Toast.makeText(this, "You Selected "+myText.getText(), Toast.LENGTH_SHORT).show();
		text = spinner.getSelectedItem().toString();
		Log.d("Selected",text);
		int counter = text.indexOf('-');
		if (counter >= 0) {
		    left = text.substring(0, counter);
		    right = text.substring(counter + 1);
		    Log.d("First",left);
			Log.d("Second",right);
		} else {
		  Log.d("Error", "There is no -");
		}
		manualCalories.setText(right, TextView.BufferType.EDITABLE);
		
	}
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	
	//sends info to server and closes printwriter
	public void sendToServer(PrintWriter printwriter){
		String command = new String("STORE|");
		command.concat(username.getText() + "|");
		String selectedItem = spinner.getSelectedItem().toString();
		selectedItem = selectedItem.replace('-','|');// separate the food item and calorie number
		command.concat(selectedItem);
		printwriter.print(command);
		printwriter.close();
	}

}
