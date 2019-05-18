package com.example.alquilervehiculos.Views;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alquilervehiculos.DAO.UserDAO;
import com.example.alquilervehiculos.DTO.LoginUserDTO;
import com.example.alquilervehiculos.R;

public class ActivitySignUp extends AppCompatActivity {
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userDAO = new UserDAO(getApplicationContext());

        bindEvents();
    }

    private void bindEvents() {
        findViewById(R.id.btnCancel).setOnClickListener(
                (View v) -> cancelOnClickEvent()
        );

        findViewById(R.id.btnSignUp).setOnClickListener(
                (View v) -> signupOnClickEvent()
        );
    }

    public void cancelOnClickEvent() {
        this.finish();
    }

    public void signupOnClickEvent() {
        EditText txtUser = findViewById(R.id.txtUsername);
        EditText txtPassword = findViewById(R.id.txtPassword);
        EditText txtRepeatPassword = findViewById(R.id.txtRepeatPassword);

        if (!txtUser.getText().toString().equals("") && !txtPassword.getText().toString().equals("") && !txtRepeatPassword.getText().toString().equals("")) {
            if (txtPassword.getText().toString().equals(txtRepeatPassword.getText().toString())) {
                new MyTask().execute(txtUser.getText().toString(), txtPassword.getText().toString());
            }
            else {
                Toast t = Toast.makeText(ActivitySignUp.this, "Passwords not matching", Toast.LENGTH_SHORT);
                t.show();
            }
        }
        else {
            Toast t = Toast.makeText(this, "You must fulfill the fields above", Toast.LENGTH_SHORT);
            t.show();
        }
    }

    private class MyTask extends AsyncTask<String, Void, LoginUserDTO> {
        @Override
        protected LoginUserDTO doInBackground(String... strings) {
            return userDAO.saveUser(strings[0], strings[1]);
        }

        @Override
        protected void onPostExecute(LoginUserDTO u) {
            Toast t = Toast.makeText(ActivitySignUp.this, "User created successfully", Toast.LENGTH_SHORT);
            t.show();
            ActivitySignUp.this.finish();
        }
    }
}
