package com.example.swetakumari.myaesampleapp;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.UserManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class PolicyComplianceActivity extends AppCompatActivity
{
    private static final String loggerName = PolicyComplianceActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy_compliance);
        Log.i(loggerName, "onCreate Policy Compliance");

        Log.i(loggerName, "profile provisioning complete");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) getApplicationContext().getSystemService(Context.DEVICE_POLICY_SERVICE);
            ComponentName deviceAdmin = new ComponentName(getApplicationContext(), WorkProfileReceiver.class);
            devicePolicyManager.setProfileName(deviceAdmin, "My New Work Profile");
            devicePolicyManager.setProfileEnabled(deviceAdmin);
            devicePolicyManager.clearUserRestriction(deviceAdmin, UserManager.DISALLOW_DEBUGGING_FEATURES);
        }

        Intent intent = getIntent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
