package com.example.nutrition252;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Register extends Activity implements OnClickListener {
	EditText newUsername;
	EditText newPassword;
	EditText newConfirmPassword;
	Button confirm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		newUsername = (EditText) findViewById(R.id.UsernameRegisterPage);
		newPassword = (EditText) findViewById(R.id.PasswordRegisterPage);
		newConfirmPassword = (EditText) findViewById(R.id.ConfirmEditTextPasswordRegisterPage);
		confirm = (Button) findViewById(R.id.ConfirmButtonRegisterPage);

		confirm.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		if (id == R.id.ConfirmButtonRegisterPage) {
			if(newPassword.getText().toString().equals(newConfirmPassword.getText().toString()))
			{
				new Thread() {
					public void run()
					{
						Socket outgoing;
						try
						{
							outgoing = new Socket("moore07.cs.purdue.edu", 3001);
							PrintWriter out = new PrintWriter(outgoing.getOutputStream(), true);
							BufferedReader in = new BufferedReader(new InputStreamReader(outgoing.getInputStream()));
						
							String command = "CREATE-USER|root|password|" + newUsername.getText().toString() + "|" + newPassword.getText().toString();
							out.println(command);
							String serverResponse = in.readLine();
							outgoing.close();
							
							if(serverResponse != null)
							{
								if(serverResponse.equals("Created user"))
								{
									Intent openGetFit = new Intent("com.example.nutrition252.MAINACTIVITY");
									openGetFit.putExtra("username", newUsername.getText().toString());								
									startActivity(openGetFit);
								}
							}
						}
						catch(Exception e)
						{
							System.out.println(e.toString());
						}
					}
				}.start();
			}
		}
	}

}
