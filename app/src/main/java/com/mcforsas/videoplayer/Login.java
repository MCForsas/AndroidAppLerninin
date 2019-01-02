package com.mcforsas.videoplayer;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText userNameInput, passwordInput;
    TextView credentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userNameInput = findViewById(R.id.textViewName);
        passwordInput = findViewById(R.id.textViewPassword);
        credentials = findViewById(R.id.textViewCredentials);
    }

    public void saveInfo(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo",Context.MODE_PRIVATE);

        SharedPreferences.Editor e = sharedPreferences.edit();
        e.putString("userName", userNameInput.getText().toString());
        e.putString("password", passwordInput.getText().toString());
        e.apply();

        Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();

    }

    public void loadInfo(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        String name = sharedPreferences.getString("userName","");
        String password = sharedPreferences.getString("password","");

        credentials.setText(String.format("N:%s\nP:%s", name, password));
    }
}
