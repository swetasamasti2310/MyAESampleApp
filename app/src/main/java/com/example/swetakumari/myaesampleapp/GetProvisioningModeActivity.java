package com.example.swetakumari.myaesampleapp;

import android.app.admin.DevicePolicyManager;
import android.content.Intent;
import android.os.Build;
import android.os.PersistableBundle;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class GetProvisioningModeActivity extends AppCompatActivity
{
    private static final String loggerName = GetProvisioningModeActivity.class.getName();
    private static final String PROVISIONING_MODE = "provisioning_mode";
    private static final String PROFILE_OWNER = "profileOwner";
    private static final String DEVICE_OWNER = "deviceOwner";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_provisioning_mode);
        Log.i(loggerName, "Get Provisioning Mode");
        setManagementMode();

        Button getProvisioningData = findViewById(R.id.get_provisioning_data);
        getProvisioningData.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setManagementMode();
            }
        });
    }

    private void setManagementMode()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Intent provisioningIntent = getIntent();
            Intent resultIntent = new Intent();
            PersistableBundle adminExtraBundle = provisioningIntent
                    .getParcelableExtra(DevicePolicyManager.EXTRA_PROVISIONING_ADMIN_EXTRAS_BUNDLE);
            int provisioningMode;
            if (adminExtraBundle != null && adminExtraBundle.containsKey(PROVISIONING_MODE)) {
                provisioningMode = adminExtraBundle.getInt(PROVISIONING_MODE);
                resultIntent.putExtra("android.app.extra.PROVISIONING_MODE", provisioningMode);
                resultIntent.putExtra(DevicePolicyManager.EXTRA_PROVISIONING_ADMIN_EXTRAS_BUNDLE, adminExtraBundle);
            }

            setResult(RESULT_OK, resultIntent);
            finish();
        }
    }


}
