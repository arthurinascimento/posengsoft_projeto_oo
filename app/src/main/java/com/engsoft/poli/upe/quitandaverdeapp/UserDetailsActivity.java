package com.engsoft.poli.upe.quitandaverdeapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.engsoft.poli.upe.quitandaverdeapp.dao.UserDao;
import com.engsoft.poli.upe.quitandaverdeapp.models.User;

public class UserDetailsActivity extends AppCompatActivity {

    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        TextView txtName = (TextView) findViewById(R.id.userNameDetails);
        TextView txtEmail = (TextView) findViewById(R.id.userEmailDetails);

        userDao = new UserDao(getContentResolver());
        User user = userDao.getByEmail(getUserLoggedEmail());

        if(user != null) {
            txtName.setText(user.getName());
            txtEmail.setText(user.getEmail());
        }else{
            Toast.makeText(getApplicationContext(), R.string.account_error, Toast.LENGTH_SHORT).show();
        }
    }

    private String getUserLoggedEmail(){
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.preferences_file_key), Context.MODE_PRIVATE);
        String result = sharedPref.getString(getString(R.string.user_logged), "");
        return result;
    }
}
