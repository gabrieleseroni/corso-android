package it.elbuild.corso.wifibroadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WifiManager mWifiManager;
    private ListView listView;
    private ProgressBar progressBar;
    private ArrayAdapter<String> adapter;
    private final BroadcastReceiver mWifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent intent) {
            if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                List<ScanResult> mScanResults = mWifiManager.getScanResults();
                for (ScanResult sr : mScanResults)
                {
                    Log.d("CLEVIRIA", sr.SSID);
                    adapter.add(sr.SSID);
                }
                progressBar.setVisibility(View.GONE);
                unregisterReceiver(mWifiScanReceiver);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(getBaseContext(),android.R.layout.simple_list_item_1,new ArrayList());
        listView.setAdapter(adapter);
        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

    }

    public void scan(View v){
        adapter.clear();
        progressBar.setVisibility(View.VISIBLE);
        registerReceiver(mWifiScanReceiver,
                new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        mWifiManager.startScan();
    }
}
