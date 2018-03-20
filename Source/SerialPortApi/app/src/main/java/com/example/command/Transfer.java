package com.example.command;

import android.util.Log;

import com.example.serialport.SerialPort;

import java.io.File;
import java.io.IOException;

/**
 * Created by chenyuye on 17/11/24.
 */

public class Transfer {

    private static final String TAG = "Transfer";
    private static Transfer mTransfer = null;

    private final String PORT_PATH = "/dev/";
    private final int BAURDRATE = 1500000;
    private SerialPort mPort = null;

    private boolean isPortOpened = false;
    public static Transfer getInstance(){
        if(mTransfer == null)
            mTransfer = new Transfer();
        return mTransfer;
    }

    public boolean initialize(){
        Log.d(TAG, "initialize: executed");
        if(!isPortOpened){
            mPort = new SerialPort();
            try {
                isPortOpened = mPort.openSerialPort(new File(PORT_PATH), BAURDRATE);
                return isPortOpened;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }else
            return true;
    }


}
