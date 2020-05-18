package com.example.myapplication;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BluetoothActivity extends AppCompatActivity {
    private Button btnBluetoothCheck = null;
    private TextView txtBluetoothDevice = null;
    private ArrayList<String> bluetoothDeviceName = new ArrayList<String>();
    private ArrayList<String> bluetoothDeviceMAC = new ArrayList<String>();
    private BluetoothDevice device = null;

    private static final int REQUEST_ENABLE_BT = 3;
    public BluetoothAdapter mBluetoothAdapter = null;
    Set<BluetoothDevice> pairDevices;
    int mPairedDeviceCount;
    BluetoothDevice mRemoteDevice;
    BluetoothSocket mSocket;
    InputStream mInputStream;
    OutputStream mOutputStream;
    Thread mWorkerThread;
    int readBufferPositon;      //버퍼 내 수신 문자 저장 위치
    byte[] readBuffer;      //수신 버퍼
    byte mDelimiter = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        txtBluetoothDevice = (TextView)findViewById(R.id.bluetoothDevice);
        btnBluetoothCheck = (Button)findViewById(R.id.bluetoothSearch);
        btnBluetoothCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BluetoothAdapter mBlueToothAdapter = BluetoothAdapter.getDefaultAdapter();

                Set<BluetoothDevice> pairedDevices = mBlueToothAdapter.getBondedDevices();

                for(BluetoothDevice device : pairedDevices) {
                    Log.d("bluetooth", device.getName().toString() + "Device is  connected.");
                    Log.d("bluetooth", "MAC is : " + device.getAddress().toString());
                }

                bluetoothDeviceName.add(device.getName().toString());
                bluetoothDeviceMAC.add(device.getAddress().toString());

                txtBluetoothDevice.setText("Device Name : " + bluetoothDeviceName + "\n MAC : " + bluetoothDeviceMAC);
            }
        });
    }
}

