package com.example.utilitiesappusingaws;

import android.content.Context;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.regions.Regions;

public class CognitoSettings {

    private String userPoolID = "us-east-1_Cd9ZTtsMd";
    private String clientId = "5q0aig699lek1sbmn3prpob39r";
    private String clientSecret = "";
    private Regions cognitoRegion = Regions.US_EAST_1;

    private Context context;

    public CognitoSettings(Context context) {
        this.context = context;
    }

    public String getUserPoolID() {
        return userPoolID;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public Regions getCognitoRegion() {
        return cognitoRegion;
    }

    public Context getContext() {
        return context;
    }

    public CognitoUserPool getUserPool() {
        return new CognitoUserPool(context, userPoolID, clientId, clientSecret, cognitoRegion);
    }


}
