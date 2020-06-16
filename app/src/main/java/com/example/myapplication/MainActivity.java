package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    private final String fileName = "items.list" ;
    private ListView listview ;
    private ArrayAdapter adapter ;
    private ArrayList<String> items = new ArrayList<String>() ;




public class MainActivity extends AppCompatActivity {

    // 05.18. 01:45 기준
    @Override

    protected void onCreate(Bundle savedInstanceState) {
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
        }) ;



    }



    private void saveItemsToFile() {
        File file = new File(getFilesDir(), fileName) ;
        FileWriter fw = null ;
        BufferedWriter bufwr = null ;

        try {
            // open file.
            fw = new FileWriter(file) ;
            bufwr = new BufferedWriter(fw) ;

            for (String str : items) {
                bufwr.write(str) ;
                bufwr.newLine() ;
            }

            // write data to the file.
            bufwr.flush() ;

        } catch (Exception e) {
            e.printStackTrace() ;
        }

        try {
            // close file.
            if (bufwr != null) {
                bufwr.close();
            }

            if (fw != null) {
                fw.close();
            }
        } catch (Exception e) {
            e.printStackTrace() ;
        }
    }


    private void loadItemsFromFile() {
        File file = new File(getFilesDir(), fileName) ;
        FileReader fr = null ;
        BufferedReader bufrd = null ;
        String str ;

        if (file.exists()) {
            try {
                // open file.
                fr = new FileReader(file) ;
                bufrd = new BufferedReader(fr) ;

                while ((str = bufrd.readLine()) != null) {
                    items.add(str) ;
                }

                bufrd.close() ;
                fr.close() ;
            } catch (Exception e) {
                e.printStackTrace() ;
            }
        }
    }


}
