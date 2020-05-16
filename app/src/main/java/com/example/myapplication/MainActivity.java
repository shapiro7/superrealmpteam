package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    final String TAG = "AndroidDEV_UI";
    public Button m_btnNetworkInfo = null;
    public TextView m_txtNetworkInfo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_btnNetworkInfo = (Button)findViewById(R.id.NetworkInfo);
        m_txtNetworkInfo = (TextView)findViewById(R.id.NetworkInfo2);

        m_btnNetworkInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                String m_strName = "";
                String m_strNetType;

                int m_iNetworkType = activeNetwork.getType();
                if ( m_iNetworkType == cm.TYPE_WIFI )
                {
                    //Wi_Fi 사용 시 AP의 SSID를 가져온다.
                    m_strName = wifiManager.getConnectionInfo().getSSID();
                    m_strNetType = "Wi-Fi";
                }
                else if ( m_iNetworkType == cm.TYPE_MOBILE )
                {
                    //모바일 네트워크 사용 시 네트워크 정보를 가져온다.
                    m_strName = activeNetwork.getExtraInfo();
                    m_strNetType = "Mobile";
                }
                else
                {
                    m_strNetType = "None";
                }

                m_txtNetworkInfo.setText("Name : "+m_strName+" \n NetworkType : "+m_strNetType);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_dev, menu);
        return true;
    }
}

