/*
 * Copyright 2009 Cedric Priscal
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.serialport;

import android.util.Log;


import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SerialPort {

    private static final boolean D = true;
    private static final String TAG = "SerialPort";

    /*
     * Do not remove or rename the field mFd: it is used by native method close();
     */
    private FileDescriptor mFd;
    private FileInputStream mFileInputStream;
    private FileOutputStream mFileOutputStream;

    // JNI
    private native static FileDescriptor open(String path, int baudrate);
    private native void close();

    static {
        System.loadLibrary("serialport");
    }


    public SerialPort() {
    }

    public void closeSerialPort() {
        try {
            if (mFileInputStream != null)
                mFileInputStream.close();
            if (mFileOutputStream != null)
                mFileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public boolean openSerialPort(File device, int baudrate) throws SecurityException, IOException {
        Log.d(TAG, "openSerialPort: path = " + device.getPath() + " baudrate = " + baudrate);
        /* Check access permission */
        if (!device.canRead() || !device.canWrite()) {
//            try {
//				/* Missing read/write permission, trying to chmod the file */
//                Process su;
//                su = Runtime.getRuntime().exec("/system/bin/su");
            String cmd = "chmod 777 " + device.getAbsolutePath() + "\n"
                    + "exit\n";
//				/*String cmd = "chmod 777 /dev/s3c_serial0" + "\n"
//				+ "exit\n";*/
//                su.getOutputStream().write(cmd.getBytes());
//                if ((su.waitFor() != 0) || !device.canRead()
//                        || !device.canWrite()) {
//                    throw new SecurityException();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                throw new SecurityException();
//            }
            ShellUtil.execCommand(cmd, false);
        }

        mFd = open(device.getAbsolutePath(), baudrate);
        if (mFd == null) {
            Log.e(TAG, "native open returns null");
            throw new IOException();
        }
        mFileInputStream = new FileInputStream(mFd);
        mFileOutputStream = new FileOutputStream(mFd);
        return true;
    }

    // Getters and setters
    public InputStream getInputStream() {
        return mFileInputStream;
    }

    public OutputStream getOutputStream() {
        return mFileOutputStream;
    }

    public void setmFileInputStream(FileInputStream mFileInputStream) {
        this.mFileInputStream = mFileInputStream;
    }

    public void setmFileOutputStream(FileOutputStream mFileOutputStream) {
        this.mFileOutputStream = mFileOutputStream;
    }

    /**
     * 从串口数据缓冲区读取数据
     *
     * @param recvBuf  接受到的数据
     * @param recvLen  数据长度
     * @param waitTime 超时时间
     * @return   0: 读取成功
     *          -1: 串口异常
     *          -2: 读数据超时
     * @throws IOException
     */
    public int read(byte recvBuf[], int recvLen, long waitTime) throws IOException {
        long lBeginTime = System.currentTimeMillis();// 更新当前秒计数
        long lCurrentTime = 0;
        int nRet = 0;
        int nReadedSize = 0;
        if (mFileInputStream == null) {
            return -1;
        }
        while (true) {
            if (mFileInputStream.available() > 0) {
                nRet = mFileInputStream.read(recvBuf, nReadedSize, (recvLen - nReadedSize));
                if (nRet > 0) {
                    nReadedSize += nRet;
                    // mCurrentSize += nReadedSize;
                    if (recvLen == nReadedSize) {
                        if(D)
                            Log.d(TAG, "接收:"+ StringUtil.byte2HexStr(recvBuf));
                        return 0;
                    }
                }
            }
            try {
                Thread.sleep(10);
                lCurrentTime = System.currentTimeMillis();
                if ((lCurrentTime - lBeginTime) > waitTime) {
                    return -2;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从串口数据缓冲区读取数据
     *
     * @param recvData 接受到的数据
     * @param waitTime 超时时间
     * @return   0: 读取成功
     *          -1: 串口异常
     *          -2: 读数据超时
     * @throws IOException
     */
    public int read(byte[] recvData, long waitTime) throws IOException {
        long lBeginTime = System.currentTimeMillis();// 更新当前秒计数
        long lCurrentTime = 0;
        int nRet = 0;
        int nReadedSize = 0;
        int recvLen = recvData.length;
        if (mFileInputStream == null) {
            return -1;
        }
        while (true) {
            if (mFileInputStream.available() > 0) {
                nRet = mFileInputStream.read(recvData, nReadedSize, (recvLen - nReadedSize));
                if (nRet > 0) {
                    nReadedSize += nRet;
                    // mCurrentSize += nReadedSize;
                    if (recvLen == nReadedSize) {
                        if(D)
                            Log.d(TAG, "接收:"+ StringUtil.byte2HexStr(recvData));
                        return 0;
                    }
                }
            }
            try {
                Thread.sleep(10);
                lCurrentTime = System.currentTimeMillis();
                if ((lCurrentTime - lBeginTime) > waitTime) {
                    return -2;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 向串口写数据
     * @param data 要发送的数据
     * @throws IOException
     */
    public void write(byte[] data) throws IOException {
        if (mFileInputStream != null) {
            while (mFileInputStream.available() > 0)
                mFileInputStream.read();
        }
        if (mFileOutputStream != null) {
            mFileOutputStream.write(data);
        }
        if (D)
            Log.d(TAG, "发送:" + StringUtil.byte2HexStr(data));
    }


}