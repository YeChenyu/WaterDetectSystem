package com.example.serialport;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jida.db.bean.ComPort;
import com.jida.db.bean.NetWork;
import com.jida.db.bean.Server;
import com.jida.db.bean.ServerSite;
import com.jida.db.bean.System;
import com.jida.db.xml.XMLUtil;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends Activity {

    private SerialPort mPort = new SerialPort();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void opreaPort(View v){
        switch (v.getId()){
            case R.id.open:
                try {
                    mPort.openSerialPort(new File("/dev/ttyHSL1"), 1500000);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.write:
                //XML配置文件读写
                ComPort port = new ComPort("COM1", 115200, 1, 0, 8);
                Server servera = new Server("servera", "192.168.1.1", "123456", "8080");
                Server serverb = new Server("serverb", "192.168.1.1", "123456", "8080");
                Server serverc = new Server("serverc", "192.168.1.1", "123456", "8080");
                NetWork netWork = new NetWork(1, "192.168.1.1", "", "", "", "", 1, 1, 1, 1,
                        servera, serverb, serverc);
                ServerSite serverSite = new ServerSite("XXX", "1", "122.12", "32.11", "12332323", 1, 1);
                System system = new System("1234567890", serverSite, netWork, port);
                XMLUtil.toXML(system, "/mnt/sdcard/test.xml");
                XMLUtil.toEntity("/mnt/sdcard/test.xml");
//                try {
//                    mPort.write(StringUtil.hexStr2Bytes("020004F1012F0103D9"));
//                    byte[] data = new byte[256];
//                    int ret = mPort.read(data, 5000);
//                    Log.d("TAG", "opreaPort: receiver="+ StringUtil.byte2HexStr(data));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPort.closeSerialPort();
    }
}
