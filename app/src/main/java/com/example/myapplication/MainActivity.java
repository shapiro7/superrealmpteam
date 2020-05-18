package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // 05.18. 01:45 기준
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // '네트워크 정보'버튼 터치 시 네트워크 메뉴로 이동
        Button NetworkAdd = (Button) findViewById(R.id.network);
        NetworkAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent StartNetworkMenu = new Intent(MainActivity.this, WifiActivity.class);
                startActivity(StartNetworkMenu);
            }
        });

        // '블루투스 정보'버튼 터치 시 블루투스 메뉴로 이동
        Button BluetoothAdd = (Button) findViewById(R.id.bluetoothAdd);
        BluetoothAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent StartBluetoothMenu = new Intent(MainActivity.this, BluetoothActivity.class);
                startActivity(StartBluetoothMenu);
            }
        });

        // '메모 추가'버튼 터치 시 메모 추가 메뉴로 이동
        Button MemoAdd = (Button) findViewById(R.id.MemoAdd);
        MemoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent StartMemoMenu = new Intent(MainActivity.this, MemoActivity.class);
                startActivity(StartMemoMenu);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_dev, menu);
        return true;
    }
}

