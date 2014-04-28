package com.example.nutrition252;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Register extends Activity implements OnClickListener{
	EditText newUsername;
	EditText newPassword;
	EditText newConfirmPassowrd;
	Button confirm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		newUsername = (EditText) findViewById(R.id.UsernameRegisterPage);
		newPassword = (EditText) findViewById(R.id.PasswordRegisterPage);
		newConfirmPassowrd= (EditText) findViewById(R.id.ConfirmEditTextPasswordRegisterPage);
		confirm = (Button) findViewById(R.id.ConfirmButtonRegisterPage);
		
		confirm.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		if (id == R.id.ConfirmButtonRegisterPage) {
			Intent openGetFit = new Intent("com.example.nutrition252.MAINACTIVITY");
			startActivity(openGetFit);
		}
	}

}
