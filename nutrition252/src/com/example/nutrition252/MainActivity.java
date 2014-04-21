package com.example.nutrition252;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements OnItemSelectedListener {

	Spinner spinner;
	TextView username;
	Button postIt;
	Button graph;
	Button mealTable;
	EditText manualCalories;
	Intent intent;
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
		
		postIt.setOnClickListener((OnClickListener) this);
		graph.setOnClickListener((OnClickListener) this);
		mealTable.setOnClickListener((OnClickListener) this);
		
	}
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.bPostIt) {
			/*intent = new Intent(v.getContext(), .class);
			startActivityForResult(intent, 0);*/
		} else if (id == R.id.bGraph) {
			/*intent = new Intent(v.getContext(), .class);
			startActivityForResult(intent, 0);*/
		} else if (id == R.id.bMealTable) {
			/*intent = new Intent(v.getContext(), .class);
			startActivityForResult(intent, 0);*/
		} 
	}
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		TextView myText = (TextView) view;
		Toast.makeText(this, "You Selected"+myText.getText(), Toast.LENGTH_SHORT).show();
	}
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

}
