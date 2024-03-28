package com.example.utilitiesappusingaws;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.core.Amplify;

public class MainActivity extends AppCompatActivity {

    private EditText usernameLogin;
    private EditText passwordLogin;

    private Button btnLogin;
    private Button btnSignUpLogin;

    private final String CREDENTIAL_SHARED_PREF = "login_shared_pref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Amplify.Auth.fetchAuthSession(
                result -> Log.i("AmplifyQuickstart", result.toString()),
                error -> Log.e("AmplifyQuickstart", error.toString())
        );

        usernameLogin = findViewById(R.id.usernameLogin);
        passwordLogin = findViewById(R.id.passwordLogin);

        btnLogin = findViewById(R.id.Login);
        btnSignUpLogin = findViewById(R.id.SignUp);

        btnSignUpLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Mode private, so other apps cannot access our shared pref
                SharedPreferences credentials = getSharedPreferences(CREDENTIAL_SHARED_PREF, Context.MODE_PRIVATE);
                String strUsername = credentials.getString("Username", null);
                String strPassword = credentials.getString("Password", null);

                String username_from_login = usernameLogin.getText().toString();
                String password_from_login = passwordLogin.getText().toString();

                if (strUsername != null && username_from_login != null && strUsername.equalsIgnoreCase(username_from_login)) {
                    if (strPassword != null && password_from_login != null && strPassword.equalsIgnoreCase(password_from_login)) {
                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, ReminderActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}