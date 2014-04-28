package com.example.nutrition252;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class Graph extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.graph);
		
		new Thread(){
			public void run(){
				Socket toServer;
				PrintWriter printwriter;
				ObjectInputStream tableInfo;
				try{
					toServer = new Socket("sslab01.cs.purdue.edu",5555);
					printwriter = new PrintWriter(toServer.getOutputStream(),true);
					//asks server to return only dates and calories to plot on graph
					String command = new String("GET-GRAPH-INFO|root|password");//need to add username
					printwriter.print(command);
					tableInfo = new ObjectInputStream(toServer.getInputStream());
					Vector<String> info = (Vector<String>)tableInfo.readObject();
					//need to add code to display the info obtained from server
					printwriter.close();
					toServer.close();
					tableInfo.close();
				}
				catch(IllegalArgumentException iae){
				}
				catch(UnknownHostException uhe){
				}
				catch(SecurityException se){
				}
				catch(IOException ioe){
				}
				catch(ClassNotFoundException cnfe){
				}
			}
		}.start();
	}
}
