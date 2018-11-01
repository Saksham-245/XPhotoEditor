package com.sakshammathur25web.photoeditor;

import android.support.multidex.MultiDexApplication;

import com.adobe.creativesdk.foundation.AdobeCSDKFoundation;
import com.adobe.creativesdk.foundation.auth.IAdobeAuthClientCredentials;

public class MainApplication extends MultiDexApplication implements IAdobeAuthClientCredentials {
    private static final String CREATIVE_SDK_CLENT_ID = "9e1b599bb4f74b2985c0e6a080de929f";
    private static final String CREATIVE_SDK_CLIENT_SECRET = "f857bbe5-0b66-4769-9e18-53c37e9c610f";
    private static final String CREATIVE_SDK_CLIENT_REDIRECT_URI = "ams+7bae0506b089eaa7483833f1477f23fa089f9bc2://adobeid/9e1b599bb4f74b2985c0e6a080de929f";
    private static final String[] CREATIVE_SDK_SCOPES = new String[]{"email", "profile", "address"};
    @Override
    public void onCreate() {
        super.onCreate();
        AdobeCSDKFoundation.initializeCSDKFoundation(getApplicationContext());
    }

    @Override
    public String getClientID() {
        return CREATIVE_SDK_CLENT_ID;
    }

    @Override
    public String getClientSecret() {
        return CREATIVE_SDK_CLIENT_SECRET;
    }

    @Override
    public String[] getAdditionalScopesList() {
        return CREATIVE_SDK_SCOPES;
    }

    @Override
    public String getRedirectURI() {
        return CREATIVE_SDK_CLIENT_REDIRECT_URI;
    }


}