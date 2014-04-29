package com.example.nutrition252;

import java.net.Socket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.Runnable;
import java.lang.Thread;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity implements OnClickListener {

	public EditText username; // = (EditText) findViewById(R.id.Username);
	public EditText password; // = (EditText) findViewById(R.id.Password);
	public Button login;// = (Button) findViewById(R.id.Login);
	public Button register;// = (Button) findViewById(R.id.Register);
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_page);

		username = (EditText) findViewById(R.id.Username);
		password = (EditText) findViewById(R.id.Password);
		login = (Button) findViewById(R.id.Login);
		register = (Button) findViewById(R.id.Register);

		login.setOnClickListener(this);
		register.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		if (id == R.id.Login) {
			
			new Thread() {
				public void run()
				{
					Socket outgoing;
					try
					{
						outgoing = new Socket("moore07.cs.purdue.edu", 3001);
						PrintWriter out = new PrintWriter(outgoing.getOutputStream(), true);
						BufferedReader in = new BufferedReader(new InputStreamReader(outgoing.getInputStream()));
					
						String command = "FIND-USER|root|password|" + username.getText().toString() + "|" + password.getText().toString();
						out.println(command);
						String serverResponse = in.readLine();
						outgoing.close();
						
						if(!serverResponse.equals(""))
						{
							System.out.println(serverResponse);
							String response[] = serverResponse.split("|");
							if(response[0].equals(username.getText().toString()) && response[1].equals(password.getText().toString()))
							{
								Intent openGetFit = new Intent("com.example.nutrition252.MAINACTIVITY");
								openGetFit.putExtra("username", username.getText().toString());								
								startActivity(openGetFit);
							}
							else
							{
								System.out.println("Login failed");
//								new AlertDialog.Builder(this)
//									.setTitle("Error")
//									.setMessage("Login failed")
//									.show();
							}
						}
					}
					catch(Exception e)
					{
						System.out.println(e.toString());
					}
				}
			}.start();

//			Intent openGetFit = new Intent(
//					"com.example.nutrition252.MAINACTIVITY");
//			startActivity(openGetFit);
		} else if (id == R.id.Register) {
			intent = new Intent(v.getContext(), Register.class);
			startActivityForResult(intent, 0);
		}

	}

}
