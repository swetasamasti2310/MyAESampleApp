package com.example.swetakumari.myaesampleapp;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

public class WorkProfileReceiver extends DeviceAdminReceiver
{
    private static final String loggerName = WorkProfileReceiver.class.getName();
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(loggerName, "received");
    }

    @Override
    public void onEnabled(Context context, Intent intent) {
        showToast(context, "Device admin enabled");
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        showToast(context, "Device admin disabled");
    }

    @Override
    public void onProfileProvisioningComplete(Context context, Intent intent) {
        Log.i(loggerName, "profile provisioning complete");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
            ComponentName deviceAdmin = new ComponentName(context.getApplicationContext(), WorkProfileReceiver.class);
            devicePolicyManager.setProfileName(deviceAdmin, "My New Work Profile");
            devicePolicyManager.setProfileEnabled(deviceAdmin);
        }
    }

    void showToast(Context context, CharSequence msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
