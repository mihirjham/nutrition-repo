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
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

public class Graph extends Activity implements OnClickListener {
	Button MealTableGraph;
	Intent intent;
	String loggedInUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.graph);
		MealTableGraph = (Button) findViewById(R.id.mealTableButtonGraphWindow);
		MealTableGraph.setOnClickListener(this);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		Intent prevIntent = getIntent();
		if(prevIntent.getExtras() != null){
			loggedInUser = prevIntent.getExtras().getString("username");
		}
		
		new Thread() {
			public void run() {
				Socket toServer;
				PrintWriter printwriter;
				String queries[] = new String[31];
				try {
					toServer = new Socket("moore07.cs.purdue.edu", 3001);
					printwriter = new PrintWriter(toServer.getOutputStream(),true);
					// asks server to return only dates and calories to plot on
					// graph
					Date date = new Date();
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					int month = cal.get(Calendar.MONTH);
					month += 1;
					String command = new String("GET-GRAPH-INFO|root|password|"+loggedInUser+"|"+month);
					System.out.println(command);
					printwriter.println(command);
					BufferedReader in = new BufferedReader(new InputStreamReader(toServer.getInputStream()));
					String serverResponse;
					int k = 0;
					while((serverResponse = in.readLine()) != null){
						queries[k++] = serverResponse;
					}
					printwriter.close();
					toServer.close();
					in.close();
					
					
					//create graph
					final GraphViewData [] graphData = new GraphViewData[31];
					for(int i=0;i<31;i++){
						String [] dataPoint = queries[i].split("\\|");
						graphData[i] = new GraphViewData(Double.parseDouble(dataPoint[1]),Double.parseDouble(dataPoint[0]));
					}
					
					  Graph.this.runOnUiThread(new Runnable(){
						  @Override
						  public void run(){
							  try {
								  GraphViewSeries graphSeries = new GraphViewSeries(graphData);
								  GraphView graphView = new LineGraphView(Graph.this,"Monthly Calorie Tracker");
								  graphView.getGraphViewStyle().setVerticalLabelsColor(Color.BLACK);
								  graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.BLACK);
								  graphView.getGraphViewStyle().setGridColor(Color.BLACK);
								  graphView.addSeries(graphSeries);
								  
								  //calculate max, min, and avg to display
								  double max=0,min=0,avg=0;
								  int n;
								  for(n=0;n<graphData.length;n++){
									  if(graphData[n].getY()>max){
										  max = graphData[n].getY();
									  }
									  if(graphData[n].getY()<min){
										  min = graphData[n].getY();
									  }
									  avg += graphData[n].getY();
								  }
								  avg /= n;
								  TextView maximum = (TextView)findViewById(R.id.tvMax);
								  maximum.setText("Maximum Calories: "+(int)max);
								  TextView minimum = (TextView)findViewById(R.id.tvMin);
								  minimum.setText("Minimum Calories: "+(int)min);
								  TextView average = (TextView)findViewById(R.id.tvAvg);
								  average.setText("Average Calories: "+(int)avg);
								  LinearLayout linearLayout = (LinearLayout)findViewById(R.id.graphViewLayout);
								  linearLayout.addView(graphView);
							  } catch (Exception e) {
								  System.out.println(e.toString());
							  } 
						  } 
					  });
					
				} catch (IllegalArgumentException iae) {
				} catch (UnknownHostException uhe) {
				} catch (SecurityException se) {
				} catch (IOException ioe) {
				}
			}
		}.start();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:
	        NavUtils.navigateUpFromSameTask(this);
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		if (id == R.id.mealTableButtonGraphWindow) {
			intent = new Intent(v.getContext(), MealTable.class);
			intent.putExtra("username", loggedInUser);
			startActivityForResult(intent, 0);
		}

	}
}
