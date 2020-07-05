package com.energia.electra.geolocation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    //send information to other view
    private Button _mButtonLogin;

    //one user can put there the day to check
    private EditText _textUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        _mButtonLogin = (Button) findViewById(R.id.ButtonLogin);
        _mButtonLogin.setOnClickListener(this);
        _textUser = (EditText) findViewById(R.id.editTextUsuario);
    }
    @Override
    public void onClick(View v) {
        if (v == _mButtonLogin)
        {
            //without text
            if (_textUser.getText().toString().length() <= 0)
            {
                _textUser.setError(getString(R.string.errorVacio));
            }
            else {

                Intent intent = new Intent(LoginActivity.this, MapsActivity.class);

                startActivity(intent);
               // }
            }
        }
    }
}
