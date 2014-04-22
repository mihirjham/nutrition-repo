package com.example.nutrition252;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class Graph extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.graph);
		
		Socket toServer;
		try{
			toServer = new Socket("sslab01.cs.purdue.edu",5555);
			PrintWriter printwriter = new PrintWriter(toServer.getOutputStream(),true);
			String command = new String("GET-GRAPH-INFO");//asks server to return only dates and calories to plot on graph
			printwriter.print(command);
			//need to add code to accept info from server after sending this request
			printwriter.close();
			toServer.close();
		}
		catch(IOException ioe){
			Toast.makeText(this, "Couldn't Connect to Host", Toast.LENGTH_SHORT).show();
		}
	}
}
