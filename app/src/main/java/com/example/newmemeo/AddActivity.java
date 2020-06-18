package com.example.newmemeo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.EditText;


import android.widget.Toast;


import java.util.ArrayList;


import java.util.Set;

public class AddActivity extends AppCompatActivity {

    EditText editText;
    EditText editWhere;

    String WifiDivice = "";
    String BlueToothDivice = "";

    public BluetoothAdapter mBluetoothAdapter = null;
    Set<BluetoothDevice> mDevices;
    int mPairedDeviceCount;

    final ArrayList<String> listItems = new ArrayList<String>();

    WifiManager wifiManager;

    public void getWifiInfo(){
        wifiManager =(WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        WifiInfo info = wifiManager.getConnectionInfo();

        WifiDivice = info.getSSID();
        Toast.makeText(AddActivity.this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
    }

    private void DeviceList() {
        mDevices = mBluetoothAdapter.getBondedDevices();
        mPairedDeviceCount = mDevices.size();
        AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
        builder.setTitle("Device List");


        // 페어링 된 블루투스 장치의 이름 목록 작성
        if(listItems.size() == 0) {
                for (BluetoothDevice device : mDevices) {
                    listItems.add(device.getName());
                }
            }

            if(listItems.size() == 0){
                //no bonded device => searching
                Log.d("Bluetooth", "No bonded device");
            }else{
                Log.d("Bluetooth", "Find bonded device");
                // 취소 항목 추가
                if(listItems.contains("저장") == false) {
                    listItems.add("저장");
                }

            final CharSequence[] items = listItems.toArray(new CharSequence[listItems.size()]);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                //각 아이템의 click에 따른 listener를 설정
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Dialog dialog_ = (Dialog) dialog;
                    // 연결할 장치를 선택하지 않고 '뒤로가기'를 누른 경우
                    if (which == listItems.size()-1) {
                        Toast.makeText(dialog_.getContext(), "취소되었습니다.", Toast.LENGTH_SHORT).show();
                    }else{
                         BlueToothDivice=items[which].toString();
                        Toast.makeText(dialog_.getContext(), "저장되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            AlertDialog alert = builder.create();
            alert.show();   //alert 시작
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        findViewById(R.id.btnBluetooth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (mBluetoothAdapter == null) {
                    // 장치가 블루투스 지원하지 않는 경우
                    Toast.makeText(getApplicationContext(), "Bluetooth no available", Toast.LENGTH_SHORT).show();
                } else {
                    // 장치가 블루투스 지원하는 경우
                    if (!mBluetoothAdapter.isEnabled()) {
                        // 블루투스를 지원하지만 비활성 상태인 경우
                        // 블루투스를 활성 상태로 바꾸기 위해 사용자 동의 요첨
                        Intent enableBtIntent = new Intent( BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBtIntent, 3);
                    } else {
                        DeviceList();
                    }
                }
            }
        });

        editText = findViewById(R.id.edtMemo);
        editWhere =findViewById(R.id.edtWhere);

        findViewById(R.id.btnDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = editText.getText().toString();
                String strwhere = editWhere.getText().toString() +"   "+ BlueToothDivice + WifiDivice;

                if(str.length()>0){

                    Intent intent = new Intent();
                    intent.putExtra("main",str);
                    intent.putExtra("sub",strwhere);
                    setResult(RESULT_OK,intent);

                    finish();

                    Toast.makeText(AddActivity.this,str+","+strwhere,Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.btnNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editWhere.setText(null);
                editText.setText(null);
            }
        });


        findViewById(R.id.btnConnect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWifiInfo();
            }
        });

    }

}
