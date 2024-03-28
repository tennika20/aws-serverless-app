package com.example.utilitiesappusingaws;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.amazonaws.services.cognitoidentityprovider.model.SignUpResult;

public class SignUpActivity extends AppCompatActivity {

    private EditText usernameSignup;
    private EditText passwordSignUp;
    private EditText emailAddress;
    private EditText confirmPasswordSignUp;
    private EditText etName;
    private Button signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usernameSignup = findViewById(R.id.etUsername);
        passwordSignUp = findViewById(R.id.etPassword);
        confirmPasswordSignUp = findViewById(R.id.etConfirmpassword);
        signupBtn = findViewById(R.id.SignUpPageBtn);
        etName = findViewById(R.id.etName);
        emailAddress = findViewById(R.id.etEmailAddress);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUsername = usernameSignup.getText().toString();
                String strPassword = passwordSignUp.getText().toString();
                String strConfirmPassword = confirmPasswordSignUp.getText().toString();
                String strEmailAddress = emailAddress.getText().toString();
                String strName = etName.getText().toString();

                if (strPassword != null && strPassword.equalsIgnoreCase(strConfirmPassword)) {

                    final CognitoUserAttributes cognitoUserAttributes = new CognitoUserAttributes();
                    cognitoUserAttributes.addAttribute("given_name", strName);
                    cognitoUserAttributes.addAttribute("phone_number", "+11111111111");
                    cognitoUserAttributes.addAttribute("email", strEmailAddress);

                    CognitoSettings cognitoSettings = new CognitoSettings(SignUpActivity.this);

                    final SignUpHandler signUpHandler = new SignUpHandler() {
                        @Override
                        public void onSuccess(CognitoUser user, SignUpResult signUpResult) {
                            Log.i("Sign Up", "Sign Up Success: " + signUpResult.getCodeDeliveryDetails());
                            SignUpActivity.this.finish();
                            Intent intent = new Intent(SignUpActivity.this, VerificationCodeActivity.class);
                            startActivity(intent);
                        }

                        @SuppressLint("ShowToast")
                        @Override
                        public void onFailure(Exception exception) {
                            Log.i("Sign Up", "Sign Up Failure: " + exception.getLocalizedMessage());
                            if (exception.getLocalizedMessage().contains("Error Code: UsernameExistsException")) {
                                Toast.makeText(getApplicationContext(), "User Name already exist. Please try a different username.", Toast.LENGTH_LONG);
                            }

                        }
                    };

                    cognitoSettings.getUserPool().signUpInBackground(strUsername, strPassword, cognitoUserAttributes, null, signUpHandler);

                }
            }
        });
    }
}
