package com.example.alquilervehiculos.Views;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alquilervehiculos.DAO.UserDAO;
import com.example.alquilervehiculos.DTO.LoginUserDTO;
import com.example.alquilervehiculos.R;

public class MainActivity extends AppCompatActivity {
    private UserDAO userDAO;


    EditText txtUsername;
    EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userDAO = new UserDAO(getApplicationContext());

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);

        bindEvents();
    }

    private void bindEvents() {
        findViewById(R.id.btnLogIn).setOnClickListener(
                (View v) -> loginOnClickEvent()
        );

        findViewById(R.id.btnSignUp).setOnClickListener(
                (View v) -> signupOnClickEvent()
        );
    }

    public void loginOnClickEvent() {
        if (!txtUsername.getText().toString().equals("") && !txtPassword.getText().toString().equals("")) {
            new MyTask().execute(txtUsername.getText().toString());

        } else {
            if (txtUsername.getText().toString().equals(""))
                txtUsername.setError("This field must not be empty");

            if (txtPassword.getText().toString().equals(""))
                txtPassword.setError("This field must not be empty");
        }

    }

    public void signupOnClickEvent() {
        Intent i = new Intent(this, ActivitySignUp.class);
        startActivity(i);

        txtUsername.setText(null);
        txtPassword.setText(null);
    }

    private class MyTask extends AsyncTask<String, Void, LoginUserDTO>{
        @Override
        protected LoginUserDTO doInBackground(String... strings) {
            return userDAO.retrieveUser(strings[0]);
        }

        @Override
        protected void onPostExecute(LoginUserDTO u) {
            if (u.getPassword() != null ) {
                if (u.getPassword().equals(txtPassword.getText().toString())) {
                    Intent i = new Intent(MainActivity.this, MainViewActivity.class);
                    startActivity(i);

                    txtUsername.setText(null);
                    txtPassword.setText(null);
                } else {
                    Toast.makeText(MainActivity.this, "Incorrect user/password", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Incorrect user/password", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
