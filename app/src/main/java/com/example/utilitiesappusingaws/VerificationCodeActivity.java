package com.example.utilitiesappusingaws;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;

public class VerificationCodeActivity extends AppCompatActivity {

    private EditText etVerificationCode;
    private EditText etUserName;
    private Button btnVerificationCode;
    private String strUserName;
    private String strVerificationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);

        etVerificationCode = findViewById(R.id.etVerficationCode);
        etUserName = findViewById(R.id.etUsername);
        btnVerificationCode = findViewById(R.id.btnVerificationCode);


        btnVerificationCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strUserName = etUserName.getText().toString();
                strVerificationCode = etVerificationCode.getText().toString();
                new ConfirmTask().execute(strUserName, strVerificationCode);
            }
        });
    }

    private class ConfirmTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            final String[] result = new String[1];

            final GenericHandler confirmationCallback = new GenericHandler() {
                @Override
                public void onSuccess() {
                    result[0] = "Success";
                    Log.i("Success", "Successfully Verified");
                }

                @Override
                public void onFailure(Exception exception) {
                    result[1] = "Failed";
                    Log.i("Failure", "Failure. The reason is:" + exception.getMessage());
                }
            };

            CognitoSettings cognitoSettings = new CognitoSettings(VerificationCodeActivity.this);
            CognitoUser thisUser = cognitoSettings.getUserPool().getUser(strUserName);

            thisUser.confirmSignUp(strVerificationCode, false, confirmationCallback);
            return result[0];
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equalsIgnoreCase("Success")) {
                Toast.makeText(getApplicationContext(), "Successfully verified!!! Please sign in.", Toast.LENGTH_LONG);
                VerificationCodeActivity.this.finish();
                Intent intent = new Intent(VerificationCodeActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Failed error code. Please retry with correct code", Toast.LENGTH_LONG);
            }
        }
    }

}