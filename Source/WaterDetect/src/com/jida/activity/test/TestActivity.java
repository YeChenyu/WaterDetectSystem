package com.jida.activity.test;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.serialport.SerialPort;
import com.jida.base.BaseActivity;
import com.jida.base.CallBack;
import com.jida.db.DBManager;
import com.jida.db.bean.Test;
import com.jida.db.bean.TestTeacher;
import com.jida.db.bean.TestUser;
import com.jida.utils.LengthUtil;
import com.jida.utils.StringUtil;
import com.jida.waterdetect.R;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class TestActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private Context mContext = TestActivity.this;
    private SerialPort mPort = new SerialPort();

    @Override
    protected void onClickEsc() {
        //网络请求测试
//        try {
//            HttpClientUtil.getInstance()
//                    .getSyncString("http://yecy.free.ngrok.cc/TestWeb/MainServlet.do");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        HttpClientUtil.getInstance()
//                .getAsyn("http://yecy.free.ngrok.cc/TestWeb/MainServlet.do",
//                        new OkHttpResultCallback<String>() {
//                            @Override
//                            public void onError(Request request, Exception e) {
//                                Log.e("TAG", "onClickEsc: "+ request.urlString());
//                                e.printStackTrace();
//                            }
//
//                            @Override
//                            public void onResponse(String response) {
//                                Log.d("TAG", "onClickEsc: "+ response);
//                            }
//                        });

        //Excel导入导出测试
//        ExcelManager.getInstance().exportExcelFile(mContext);
        //XML配置文件读写
//        ComPort port = new ComPort("COM1", 115200, 1, 0, 8);
//        Server servera = new Server("servera", "192.168.1.1", "123456", "8080");
//        Server serverb = new Server("serverb", "192.168.1.1", "123456", "8080");
//        Server serverc = new Server("serverc", "192.168.1.1", "123456", "8080");
//        NetWork netWork = new NetWork(1, "192.168.1.1", "", "", "", "", 1, 1, 1, 1,
//                servera, serverb, serverc);
//        ServerSite serverSite = new ServerSite("XXX", "1", "122.12", "32.11", "12332323", 1, 1);
//        System system = new System("1234567890", serverSite, netWork, port);
//        XMLUtil.toXML(system, "/mnt/test.xml");

        //对话框测试
//        displayDialogProgress("正在检测SD卡...", 3000);
//        View v = LayoutInflater.from(mContext).inflate(R.layout.setting_password, null);
//        displayDialogCustom("导入中...", v, "", new CallBack.OnBasicListener() {
//            @Override
//            public void onConfirm() {
//
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//        });
//        displayDialogSucc("导出成功！", new CallBack.OnCompleteListener() {
//            @Override
//            public void onComplete() {
//            }
//        });
    }

    @Override
    protected void addContentView() {
        setContentView(R.layout.test_activity);
//        setCustomViewEnable(true);
//        setTitle("Main Activity");
    }

    @Override
    protected void initView() {

    }

    /**
     * 数据库存储测试
     * @param v
     */
    public void onDBTestClick(View v){
        Log.d(TAG, "onDBTestClick: v.getid="+ v.getId());
        switch (v.getId()){
            case R.id.test1:
                DBManager.getInstance().getDaoSession().getTestDao().deleteAll();
                DBManager.getInstance().getDaoSession().getTestTeacherDao().deleteAll();
                DBManager.getInstance().getDaoSession().getTestUserDao().deleteAll();
                break;
            case R.id.test2:
                for (int i=0 ; i<5 ; i++){
                    Test test = new Test(null, true, 23+i, "test"+i);
                    DBManager.getInstance().getDaoSession().getTestDao().insert(test);
                }

                for (int i=0 ; i<5 ; i++){
                    TestUser user = new TestUser(null, "user"+i);
                    DBManager.getInstance().getDaoSession().getTestUserDao().insert(user);
                }
                List<TestUser> lstUser = DBManager.getInstance().getDaoSession().getTestUserDao()
                        .queryBuilder().build().list();
                for (TestUser list : lstUser){
                    Log.d(TAG, "onClickEsc: "+ list.toString());
                }
                TestTeacher teacher = new TestTeacher(null, false, 21, "teacher1", lstUser.get(0).getID());
                DBManager.getInstance().getDaoSession().getTestTeacherDao().insert(teacher);
                teacher = new TestTeacher(null, false, 22, "teacher2", lstUser.get(1).getID());
                DBManager.getInstance().getDaoSession().getTestTeacherDao().insert(teacher);
                List<TestTeacher> lstTest = DBManager.getInstance().getDaoSession().getTestTeacherDao()
                        .queryBuilder().build().list();
                for (TestTeacher list : lstTest){
                    Log.d(TAG, "onClickEsc: "+ list.toString());
                }
                break;

            case R.id.test4:
                try {
                    if(mPort.openSerialPort(new File("/dev/s3c2410_serial0"), 115200))
                        Toast.makeText(mContext, "串口打开成功", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(mContext, "串口打开失败", Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.test5:
                try {
                    mPort.write(StringUtil.hexStr2Bytes("020004F1012F0203DA"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                try {
//                    Transfer.getInstance().connectK21(1500000);
//                    getDeviceInfo();
//                } catch (SDKException e) {
//                    e.printStackTrace();
//                }
                break;
            case R.id.test6:
                byte[] data = new byte[256];
                int ret = 0;
                try {
                    ret = mPort.read(data, 10*1000);
                    if(ret != 0)
                        Log.e(TAG, "read data time out: "+ret);
                    Log.d(TAG, "opreaPort: receiver="+ StringUtil.byte2HexStr(data));
                    Toast.makeText(mContext, StringUtil.byte2HexStr(data), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.test7:
                mPort.closeSerialPort();
                break;

            case R.id.test:
                break;
            default:
                break;
        }
    }


    /**
     * 读取设备信息 </p>
     * <p>
     * Order: 0xF1 0x01
     * </p>
     * <p>
     * Description: 该命令用于获取设备基本信息和运行状态，可自由读取。
     * </p>
     *
     * @return DeviceInfo
     * @throws SDKException
     */
    /**
     *
     * <p>Title: 读取设备信息</p>
     * <p>Order: 0xF1 0x01</p>
     * <p>Description: 该命令用于获取设备基本信息和运行状态，可自由读取。</p>
     * @return DeviceInfo
     * @throws SDKException
     */
    public DeviceInfo getDeviceInfo() throws SDKException {
        byte[] cmd = new byte[] { (byte) 0xf1, 0x01 };
        RespResult respResult = Transfer.getInstance().translate(cmd, null, 3000);
        if (SDKException.CODE_SUCCESS.endsWith(respResult.getRespCode())) {
            DeviceInfo info = new DeviceInfo();
            byte[] result = respResult.getParams();
            int pos = 0;
            // 设备硬件编号（SN码）
            byte[] termSN = new byte[12];
            if (result.length < (pos + termSN.length))
            {
                return info;
            }

            System.arraycopy(result, pos, termSN, 0, termSN.length);
            pos += termSN.length;
            info.setTermSN(StringUtil.byteToStr(formatWithZero(termSN)));

            // 设备个人化状态
            if (result.length < (pos + 1))
            {
                return info;
            }
            info.setPrivatize(StringUtil.byte2HexStr(new byte[] { result[pos++] }));

            // 应用版本
            byte[] appVersion = new byte[16];
            if (result.length < (pos + appVersion.length))
            {
                return info;
            }
            System.arraycopy(result, pos, appVersion, 0, appVersion.length);
            pos += appVersion.length;
            info.setAppVersion(StringUtil.byteToStr(formatWithZero(appVersion)));

            // 保留
            byte[] retain = new byte[10];
            if (result.length < (pos + retain.length))
            {
                return info;
            }
            System.arraycopy(result, pos, retain, 0, retain.length);
            pos += retain.length;
            info.setRetain(StringUtil.byte2HexStr(retain));

            // 设备状态
            if (result.length < (pos + 1))
            {
                return info;
            }
            info.setDeviceStatus(result[pos++]);

            // 固件版本
            byte[] firmwareVersion = new byte[64];
            if (result.length < (pos + firmwareVersion.length))
            {
                return info;
            }
            System.arraycopy(result, pos, firmwareVersion, 0, firmwareVersion.length);
            pos += firmwareVersion.length;
            info.setFirmwareVersion(StringUtil.byteToGBK(formatWithZero(firmwareVersion)));

            // 客户序列号（CSN）
            if (result.length < (pos + 2))
            {
                return info;
            }
            int CSNLen = LengthUtil.llvar2Len( result[pos++], result[pos++] );
            if (CSNLen > 0) {
                byte[] CSN = new byte[CSNLen];
                if (result.length < (pos + CSN.length))
                {
                    return info;
                }
                System.arraycopy(result, pos, CSN, 0, CSN.length);
                pos += CSN.length;
                info.setCSN(StringUtil.byteToStr(CSN));
            }

            // 密钥序列号（KSN）
            if (result.length < (pos + 2))
            {
                return info;
            }
            int KSNLen = LengthUtil.llvar2Len( result[pos++], result[pos++] );
            if (KSNLen > 0) {
                byte[] KSN = new byte[KSNLen];
                if (result.length < (pos + KSN.length))
                {
                    return info;
                }
                System.arraycopy(result, pos, KSN, 0, KSN.length);
                pos += KSN.length;
                info.setKSN(StringUtil.byteToStr(KSN));
            }

            // 产品ID
            byte[] productID = new byte[2];
            if (result.length < (pos + productID.length))
            {
                return info;
            }
            System.arraycopy(result, pos, productID, 0, productID.length);
            pos += productID.length;
            info.setProductID(StringUtil.byte2HexStr(productID));

            // 厂商ID
            byte[] menufactery = new byte[2];
            if (result.length < (pos + menufactery.length))
            {
                return info;
            }
            System.arraycopy(result, pos, menufactery, 0, menufactery.length);
            pos += menufactery.length;
            info.setManufacturerID(StringUtil.byte2HexStr(menufactery));

            // 生产SN号
            if (result.length < (pos + 2))
            {
                return info;
            }
            int produceLen = LengthUtil.llvar2Len( result[pos++], result[pos++] );
            if (produceLen > 0) {
                byte[] produceSN = new byte[produceLen];
                if (result.length < (pos + produceSN.length))
                {
                    return info;
                }
                System.arraycopy(result, pos, produceSN, 0, produceSN.length);
                pos += produceSN.length;
                info.setProduceSN(StringUtil.byteToStr(produceSN));
            }

            // Boot版本
            if (result.length < (pos + 2))
            {
                return info;
            }
            int bootLen = LengthUtil.llvar2Len( result[pos++], result[pos++] );
            if (bootLen > 0) {
                byte[] bootVersion = new byte[bootLen];
                if (result.length < (pos + bootVersion.length))
                {
                    return info;
                }
                System.arraycopy(result, pos, bootVersion, 0, bootVersion.length);
                info.setBootVersion(StringUtil.byteToStr(bootVersion));
                pos += bootVersion.length;
            }

            // 蓝牙名称
            if (result.length < (pos + 2))
            {
                return info;
            }
            int bluetoothNameLen = LengthUtil.llvar2Len( result[pos++], result[pos++] );
            if (bluetoothNameLen > 0) {
                byte[] bootVersion = new byte[bluetoothNameLen];
                if (result.length < (pos + bootVersion.length))
                {
                    return info;
                }
                System.arraycopy(result, pos, bootVersion, 0, bootVersion.length);
                info.setDeviceBlueName(StringUtil.byte2HexStr(bootVersion));
                pos += bootVersion.length;
            }

            // 蓝牙地址
            if (result.length < (pos + 2))
            {
                return info;
            }

            int bluetoothAddrLen = LengthUtil.llvar2Len( result[pos++], result[pos++] );
            if (bootLen > 0) {
                byte[] bootAddr = new byte[bluetoothAddrLen];
                if (result.length < (pos + bootAddr.length))
                {
                    return info;
                }
                System.arraycopy(result, pos, bootAddr, 0, bootAddr.length);
                info.setDeviceBlueAddress(StringUtil.byte2HexStr(bootAddr));
                pos += bootAddr.length;
            }

            // 硬件版本号
            if (result.length >= (pos+4)) {
                byte[] hardwareVersion = new byte[4];
                if (result.length < (pos + hardwareVersion.length))
                {
                    return info;
                }
                System.arraycopy(result, pos, hardwareVersion, 0, hardwareVersion.length);
                info.setHardwareVersion(StringUtil.byte2HexStr(hardwareVersion));
            }

            return info;
        } else {
            throw new SDKException(respResult.getRespCode());
        }
    }

    private String changeDate(String inputDate) {
        StringBuffer dateBuffer = new StringBuffer();
        int pos = 1;
        String month = inputDate.substring(pos, pos + 3);
        pos += 3;
        String day = inputDate.substring(pos + 1, pos + 1 + 2);
        day = day.trim();
        int dayNum = Integer.valueOf(day);
        day = String.format(Locale.US,"%02d", dayNum);
        pos += 3;
        String year = inputDate.substring(pos + 1, pos + 1 + 4);
        pos += 5;
        String hour = inputDate.substring(pos + 1, pos + 1 + 2);
        pos += 3;
        String minute = inputDate.substring(pos + 1, pos + 1 + 2);
        dateBuffer.append(year);
        dateBuffer.append(changeMonth(month));
        dateBuffer.append(day);
        dateBuffer.append(hour);
        dateBuffer.append(minute);
        return dateBuffer.toString();
    }

    private String changeMonth(String month) {
        String res = "00";
        switch (month) {
            case "Jan":
                res = "01";
                break;
            case "Feb":
                res = "02";
                break;
            case "Mar":
                res = "03";
                break;
            case "Apr":
                res = "04";
                break;
            case "May":
                res = "05";
                break;
            case "Jun":
                res = "06";
                break;
            case "Jul":
                res = "07";
                break;
            case "Aug":
                res = "08";
                break;
            case "Sep":
                res = "09";
                break;
            case "Oct":
                res = "10";
                break;
            case "Nov":
                res = "11";
                break;
            case "Dec":
                res = "12";
                break;
        }
        return res;
    }
    private byte[] formatWithZero(byte[] data) {
        int mark = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i] == 0x00) {
                mark = i;
                break;
            }
        }
        if (mark == 0) {
            mark = data.length;
        }
        byte[] result = new byte[mark];
        System.arraycopy(data, 0, result, 0, mark);
        return result;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPort.closeSerialPort();
    }
}
