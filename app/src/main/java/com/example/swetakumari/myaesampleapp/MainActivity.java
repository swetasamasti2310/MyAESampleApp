package com.example.swetakumari.myaesampleapp;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PersistableBundle;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.app.admin.DevicePolicyManager.ACTION_PROVISION_MANAGED_PROFILE;
import static android.app.admin.DevicePolicyManager.EXTRA_PROVISIONING_ADMIN_EXTRAS_BUNDLE;
import static android.app.admin.DevicePolicyManager.EXTRA_PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{

    private static final int REQUEST_PROVISION_MANAGED_PROFILE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button createPo = findViewById(R.id.create_po);
        createPo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                createWorkProfile();
            }
        });

        Button openZt = findViewById(R.id.open_zt);
        openZt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), GetProvisioningModeActivity.class);
                startActivity(intent);
            }
        });

        Button wifiPrototype = findViewById(R.id.wifi_prototype);
        wifiPrototype.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), WifiPrototypeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void createWorkProfile()
    {
        // creating PO
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String intentAction = ACTION_PROVISION_MANAGED_PROFILE;
            Intent intent = new Intent(intentAction);
            DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) getApplicationContext().getSystemService(Context.DEVICE_POLICY_SERVICE);
            ComponentName adminComponent = new ComponentName(getApplication(), WorkProfileReceiver.class);
            intent.putExtra(EXTRA_PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME, adminComponent);

            PersistableBundle adminExtras = new PersistableBundle();
            if (adminExtras.size() > 0) {
                intent.putExtra(EXTRA_PROVISIONING_ADMIN_EXTRAS_BUNDLE, adminExtras);
            }

            if (intent.resolveActivity(this.getPackageManager()) != null) {
                startActivityForResult(intent, REQUEST_PROVISION_MANAGED_PROFILE);
                finish();
            } else {
                Toast.makeText(this, "Provisioning not supported", Toast.LENGTH_SHORT)
                    .show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check if this is the result of the provisioning activity
        if (requestCode == REQUEST_PROVISION_MANAGED_PROFILE) {
            // If provisioning was successful, the result code is
            // Activity.RESULT_OK
            if (resultCode == Activity.RESULT_OK) {
                // Work profile created and provisioned.
            } else {
                // Provisioning failed.
            }
            return;
        } else {
            // This is the result of some other activity. Call the superclass.
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
