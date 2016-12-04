package com.engsoft.poli.upe.quitandaverdeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.engsoft.poli.upe.quitandaverdeapp.dao.UserDao;
import com.engsoft.poli.upe.quitandaverdeapp.models.User;

public class RegisterActivity extends AppCompatActivity {

    private AutoCompleteTextView mEmailView;
    private AutoCompleteTextView mNameView;
    private EditText mPasswordView;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        mNameView = (AutoCompleteTextView) findViewById(R.id.userName);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.userEmail);
        mPasswordView = (EditText) findViewById(R.id.userPassword);
        userDao = new UserDao(getContentResolver());

        Button registerButton = (Button) findViewById(R.id.registerBtn);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = mNameView.getText().toString();
                String email = mEmailView.getText().toString();
                String password = mPasswordView.getText().toString();

                User user = new User(name, email, password);
                if(userDao.saveUser(user)) {
                    Toast.makeText(getApplicationContext(), R.string.register_success, Toast.LENGTH_LONG).show();
                    Intent intentLogin = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intentLogin);
                }else{
                    Toast.makeText(getApplicationContext(), R.string.register_invalid, Toast.LENGTH_LONG).show();
                }
            }
        });



    }
}
