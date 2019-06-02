package com.example.swetakumari.myaesampleapp;

import java.util.ArrayList;
import java.util.List;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkSuggestion;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.BuildCompat;

public class WifiPrototypeActivity extends AppCompatActivity
{

    WifiManager wifiManager;

    private int networkId = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_prototype);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        Button enableNw = findViewById(R.id.enable_nw);
        enableNw.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                boolean enabled = wifiManager.enableNetwork(networkId, false);
                Log.i("sweta", "network enabled: " + enabled);
            }
        });

        Button newAddNw = findViewById(R.id.new_add_nw);
        newAddNw.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (BuildCompat.isAtLeastQ()) {
                    WifiNetworkSuggestion.Builder wifiNetworkSuggestionBuilder =
                        new WifiNetworkSuggestion.Builder();
                    wifiNetworkSuggestionBuilder.setSsid("AzuNet");
                    wifiNetworkSuggestionBuilder.setWpa2Passphrase("AzuNet4!$L");
                    wifiNetworkSuggestionBuilder.setPriority(0);
                    wifiNetworkSuggestionBuilder.setIsAppInteractionRequired();
                    WifiNetworkSuggestion suggestion1 =   wifiNetworkSuggestionBuilder.build();
                    WifiNetworkSuggestion suggestion2 = new WifiNetworkSuggestion.Builder()
                        .setSsid("CPH1610")
                        .setWpa2Passphrase("jalebi654")
                        .setPriority(0)
                        .setIsAppInteractionRequired()
                        .build();
                    List<WifiNetworkSuggestion> suggestionsList = new ArrayList<WifiNetworkSuggestion> ();
                    suggestionsList.add(suggestion1);
                    //suggestionsList.add(suggestion2);
                    int suggestionResult = wifiManager.addNetworkSuggestions(suggestionsList);
                    Log.i("sweta ", "add network suggestion result: " + suggestionResult);
                }
            }
        });

        Button newRemoveNw = findViewById(R.id.new_remove_nw);
        newRemoveNw.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (BuildCompat.isAtLeastQ()) {
                    WifiNetworkSuggestion.Builder wifiNetworkSuggestionBuilder =
                        new WifiNetworkSuggestion.Builder();
                    wifiNetworkSuggestionBuilder.setSsid("AzuNet");
                    wifiNetworkSuggestionBuilder.setWpa2Passphrase("AzuNet4!$L");
                    wifiNetworkSuggestionBuilder.setPriority(0);
                    wifiNetworkSuggestionBuilder.setIsAppInteractionRequired();
                    WifiNetworkSuggestion suggestion1 =   wifiNetworkSuggestionBuilder.build();
                    WifiNetworkSuggestion suggestion2 = new WifiNetworkSuggestion.Builder()
                        .setSsid("CPH1610")
                        .setWpa2Passphrase("jalebi654")
                        .setPriority(0)
                        .setIsAppInteractionRequired()
                        .build();
                    List<WifiNetworkSuggestion> suggestionsList = new ArrayList<WifiNetworkSuggestion> ();
                    suggestionsList.add(suggestion1);
                    //suggestionsList.add(suggestion2);
                    int suggestionResult = wifiManager.removeNetworkSuggestions(suggestionsList);
                    Log.i("sweta ", "remove network suggestion result: " + suggestionResult);
                }
            }
        });


        Button addNw = findViewById(R.id.add_nw);
        addNw.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String ssid = "CPH1613";
                String pass = "jalebi231";
                List<WifiConfiguration> configuredWifiProfiles = wifiManager.getConfiguredNetworks();
                WifiConfiguration wifiConfiguration = new WifiConfiguration();
                wifiConfiguration.SSID = "\"" + ssid + "\"";
                wifiConfiguration.preSharedKey = pass;
                networkId = wifiManager.addNetwork(wifiConfiguration);
                Log.i("sweta", " network id of added nw is " + networkId);
            }
        });

        Button removeNw = findViewById(R.id.remove_nw);
        removeNw.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                boolean removed = wifiManager.removeNetwork(networkId);
                Log.i("sweta ", "network removed: " + removed);
            }
        });

        Button updateNw = findViewById(R.id.update_nw);
        updateNw.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String ssid = "AzuNet";
                String pass = "AzuNet4!$L";
                WifiConfiguration wifiConfiguration = new WifiConfiguration();
                wifiConfiguration.SSID = "\"" + ssid + "\"";
                wifiConfiguration.preSharedKey = pass;
                wifiConfiguration.networkId = networkId;
                int networkid = wifiManager.updateNetwork(wifiConfiguration);
                Log.i("sweta ", "network updated with id: " + networkid);
            }
        });

        Button disconnectNw = findViewById(R.id.disconnect_nw);
        disconnectNw.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                boolean enabled = wifiManager.enableNetwork(networkId, false);
                boolean disconnected = wifiManager.reconnect();
                Log.i("sweta ", "disconnected with network id " + networkId + " : " + disconnected);
            }
        });

        Button reconnectNw = findViewById(R.id.reconnect_nw);
        reconnectNw.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                boolean enabled = wifiManager.enableNetwork(networkId, false);
                boolean reconnected = wifiManager.reconnect();
                Log.i("sweta ", "reconnected with network id " + networkId + " : " + reconnected);
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        // Optional (Wait for post connection broadcast to one of your suggestions)
        final IntentFilter intentFilter =
            new IntentFilter(WifiManager.ACTION_WIFI_NETWORK_SUGGESTION_POST_CONNECTION);

        final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (!intent.getAction().equals(
                    WifiManager.ACTION_WIFI_NETWORK_SUGGESTION_POST_CONNECTION)) {
                    Log.i("sweta ", "not connected yet");
                    return;
                }
                // do post connect processing here..
                Log.i("sweta ", "network suggestion connection received");
            }
        };
        getApplicationContext().registerReceiver(broadcastReceiver, intentFilter);
    }
}
