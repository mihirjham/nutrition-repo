package com.example.nutrition252;

import android.app.Activity;
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
			Intent openGetFit = new Intent(
					"com.example.nutrition252.MAINACTIVITY");
			startActivity(openGetFit);
		} else if (id == R.id.Register) {
			intent = new Intent(v.getContext(), Register.class);
			startActivityForResult(intent, 0);
		}

	}

}
