package com.jida;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.jida.base.AppException;
import com.jida.common.manager.TermInfoManager;
import com.jida.db.DBManager;
import com.jida.utils.DataUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Properties;

public class MainApplication extends Application{

    private static final String TAG = MainApplication.class.getSimpleName();

    public static final int NETTYPE_WIFI = 0x01;
    
    public static final int NETTYPE_CMWAP = 0x02;
    
    public static final int NETTYPE_CMNET = 0x03;

    public static final int PAGE_SIZE = 20;//默认分页大小
    
    private static final int CACHE_TIME = 60*60000;//缓存失效时间
    
    private boolean login = false;  //登录状态 

    private Hashtable<String, Object> memCacheRegion = new Hashtable<String, Object>();
        
    private static Context context; 
    


	 @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        
        init();
    }

    /**
     * 初始 */
    private void init(){
        //异常捕获配置初始化
        AppException.getInstance().init(context);
        //GreenDao数据库初始化
        DBManager.getInstance().initGreenDao(context);
    }


    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        Log.d(TAG, "onTerminate: executed");
        super.onTerminate();
    }
    
    @Override
    public void onLowMemory() {
        // 低内存的时候执行
        Log.d(TAG, "onLowMemory: executed");
        super.onLowMemory();
    }
    
    @Override
    public void onTrimMemory(int level) {
        // 程序在内存清理的时候执行
        Log.d(TAG, "onTrimMemory: executed");
        super.onTrimMemory(level);
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d(TAG, "onConfigurationChanged: executed");
        super.onConfigurationChanged(newConfig);
    }
	     	 
	public static Context getContextObject(){  
		return context;  
	}   


	/**
     * 检测当前系统声音是否为正常模式
     * @return
     */
    public boolean isAudioNormal() {
        AudioManager mAudioManager = (AudioManager)getSystemService(AUDIO_SERVICE); 
        return mAudioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL;
    }

    /**
     * 是否发出提示
     * @return
     */
    public boolean isVoice() {
        String perf_voice = getProperty(TermInfoManager.CONF_VOICE);
        //默认是开启提示声
        if(DataUtils.isEmpty(perf_voice))
            return true;
        else
            return DataUtils.toBool(perf_voice);
    }

    /**
     * 应用程序是否发出提示
     * @return
     */
    public boolean isAppSound() {
        return isAudioNormal() && isVoice();
    }
    
    /**
     * 检测网络是否可用
     * @return
     */
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    /**
     * 获取当前网络类型
     * @return 0：没有网络  1：WIFI网络   2：WAP网络    3：NET网络
     */
    public int getNetworkType() {
    	
        int netType = 0;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }       
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = networkInfo.getExtraInfo();
            if(!DataUtils.isEmpty(extraInfo)){
                if (extraInfo.toLowerCase().equals("cmnet")) {
                    netType = NETTYPE_CMNET;
                } else {
                    netType = NETTYPE_CMWAP;
                }
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = NETTYPE_WIFI;
        }
        return netType;
    }
    
    /**
     * 判断当前版本是否兼容目标版本的
     * @param VersionCode
     * @return
     */
    public static boolean isMethodsCompat(int VersionCode) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }
    
    /**
     * 获取App安装包信息
     * @return
     */
    public PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try { 
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (NameNotFoundException e) {    
            e.printStackTrace(System.err);
        } 
        if(info == null) info = new PackageInfo();
        return info;
    }

    /**
     * 设置是否发出提示
     * @param b
     */
    public void setConfigVoice(boolean b) {
        setProperty(TermInfoManager.CONF_VOICE, String.valueOf(b));
    }
    
    /**
     * 是否启动检查更
     * @return
     */
    public boolean isCheckUp()
    {
        String perf_checkup = getProperty(TermInfoManager.CONF_CHECKUP);
        //默认是开       
        if(DataUtils.isEmpty(perf_checkup))
            return true;
        else
            return DataUtils.toBool(perf_checkup);
    }
    
    /**
     * 设置启动检查更
     *     * @param b
     */
    public void setConfigCheckUp(boolean b)
    {
        setProperty(TermInfoManager.CONF_CHECKUP, String.valueOf(b));
    }
    
    /**
     * 是否左右滑动
     * @return
     */
    public boolean isScroll()
    {
        String perf_scroll = getProperty(TermInfoManager.CONF_SCROLL);
        //默认是关闭左右滑
        if(DataUtils.isEmpty(perf_scroll))
            return false;
        else
            return DataUtils.toBool(perf_scroll);
    }
    
    /**
     * 设置是否左右滑动
     * @param b
     */
    public void setConfigScroll(boolean b)
    {
        setProperty(TermInfoManager.CONF_SCROLL, String.valueOf(b));
    }
    
    /**
     * 是否Https登录
     * @return
     */
    public boolean isHttpsLogin()
    {
        String perf_httpslogin = getProperty(TermInfoManager.CONF_HTTPS_LOGIN);
        //默认是http
        if(DataUtils.isEmpty(perf_httpslogin))
            return false;
        else
            return DataUtils.toBool(perf_httpslogin);
    }
    
    /**
     * 设置是是否Https登录
     * @param b
     */
    public void setConfigHttpsLogin(boolean b)
    {
        setProperty(TermInfoManager.CONF_HTTPS_LOGIN, String.valueOf(b));
    }
    
    /**
     * 清除保存的缓 */
    public void cleanCookie()
    {
        removeProperty(TermInfoManager.CONF_COOKIE);
    }
    
    /**
     * 判断缓存数据是否可读
     * @param cachefile
     * @return
     */
    private boolean isReadDataCache(String cachefile)
    {
        return readObject(cachefile) != null;
    }
    
    /**
     * 判断缓存是否存在
     * @param cachefile
     * @return
     */
    private boolean isExistDataCache(String cachefile)
    {
        boolean exist = false;
        File data = getFileStreamPath(cachefile);
        if(data.exists())
            exist = true;
        return exist;
    }
    
    /**
     * 判断缓存是否失效
     * @param cachefile
     * @return
     */
    public boolean isCacheDataFailure(String cachefile)
    {
        boolean failure = false;
        File data = getFileStreamPath(cachefile);
        if(data.exists() && (System.currentTimeMillis() - data.lastModified()) > CACHE_TIME)
            failure = true;
        else if(!data.exists())
            failure = true;
        return failure;
    }
    
    /**
     * 清除app缓存
     */
    public void clearAppCache()
    {
        //清除webview缓存
//        File file = CacheManager.getCacheFileBaseDir();
//        if (file != null && file.exists() && file.isDirectory()) {
//            for (File item : file.listFiles()) {
//                item.delete();
//            }
//            file.delete();
//        }
        deleteDatabase("webview.db");  
        deleteDatabase("webview.db-shm");  
        deleteDatabase("webview.db-wal");  
        deleteDatabase("webviewCache.db");  
        deleteDatabase("webviewCache.db-shm");  
        deleteDatabase("webviewCache.db-wal");  
        
        //清除数据缓存
        clearCacheFolder(getFilesDir(),System.currentTimeMillis());
        clearCacheFolder(getCacheDir(),System.currentTimeMillis());
        
        //2.2版本才有将应用缓存转移到sd卡的功能
//        if(isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)){
//            clearCacheFolder(MethodsCompat.getExternalCacheDir(this),System.currentTimeMillis());
//        }
//        
        
        //清除编辑器保存的临时内容
        Properties props = getProperties();
        for(Object key : props.keySet()) {
            String _key = key.toString();
            if(_key.startsWith("temp"))
                removeProperty(_key);
        }
        
    }   
    
    
    /**
     * 清除缓存目录
     * @param dir 目录
     * @param curTime 当前系统时间
     * @return
     */
    private int clearCacheFolder(File dir, long curTime) {          
        int deletedFiles = 0;         
        if (dir!= null && dir.isDirectory()) {             
            try {                
                for (File child:dir.listFiles()) {    
                    if (child.isDirectory()) {              
                        deletedFiles += clearCacheFolder(child, curTime);          
                    }  
                    if (child.lastModified() < curTime) {     
                        if (child.delete()) {                   
                            deletedFiles++;           
                        }    
                    }    
                }             
            } catch(Exception e) {       
                e.printStackTrace();    
            }     
        }       
        return deletedFiles;     
    }
    
    /**
     * 将对象保存到内存缓存
     * @param key
     * @param value
     */
    public void setMemCache(String key, Object value) {
        memCacheRegion.put(key, value);
    }
    
    /**
     * 从内存缓存中获取对象
     * @param key
     * @return
     */
    public Object getMemCache(String key){
        return memCacheRegion.get(key);
    }
    
    public void removeMemCache(String key){
        memCacheRegion.remove(key);
    }
    
    public void clearMemCache(){
    	memCacheRegion.clear();
    }
    
    /**
     * 保存磁盘缓存
     * @param key
     * @param value
     * @throws IOException
     */
    public void setDiskCache(String key, String value) throws IOException {
        FileOutputStream fos = null;
        try{
            fos = openFileOutput("cache_"+key+".data", Context.MODE_PRIVATE);
            fos.write(value.getBytes());
            fos.flush();
        }finally{
            try {
                fos.close();
            } catch (Exception e) {}
        }
    }
    
    /**
     * 获取磁盘缓存数据
     * @param key
     * @return
     * @throws IOException
     */
    public String getDiskCache(String key) throws IOException {
        FileInputStream fis = null;
        try{
            fis = openFileInput("cache_"+key+".data");
            byte[] datas = new byte[fis.available()];
            fis.read(datas);
            return new String(datas);
        }finally{
            try {
                fis.close();
            } catch (Exception e) {}
        }
    }
    
    /**
     * 保存对象
     * @param ser
     * @param file
     * @throws IOException
     */
    public boolean saveObject(Serializable ser, String file) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try{
            fos = openFileOutput(file, MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(ser);
            oos.flush();
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }finally{
            try {
                oos.close();
            } catch (Exception e) {}
            try {
                fos.close();
            } catch (Exception e) {}
        }
    }
    
    /**
     * 读取对象
     * @param file
     * @return
     * @throws IOException
     */
    public Serializable readObject(String file){
        if(!isExistDataCache(file))
            return null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try{
            fis = openFileInput(file);
            ois = new ObjectInputStream(fis);
            return (Serializable)ois.readObject();
        }catch(FileNotFoundException e){
        }catch(Exception e){
            e.printStackTrace();
            //反序列化失败 - 删除缓存文件
            if(e instanceof InvalidClassException){
                File data = getFileStreamPath(file);
                data.delete();
            }
        }finally{
            try {
                ois.close();
            } catch (Exception e) {}
            try {
                fis.close();
            } catch (Exception e) {}
        }
        return null;
    }

    /********** 设置、获取参数配置*****************************************************************************************/
    private boolean containsProperty(String key){
    	Properties props = getProperties();
    	return props.containsKey(key);
    }
    private void setProperties(Properties ps){
    	TermInfoManager.getAppConfig(this).set(ps);
    }
    private Properties getProperties(){
        return TermInfoManager.getAppConfig(this).get();
    }
    private void setProperty(String key,String value){
    	TermInfoManager.getAppConfig(this).set(key, value);
    }
    private String getProperty(String key){
        return TermInfoManager.getAppConfig(this).get(key);
    }
    private void removeProperty(String...key){
    	TermInfoManager.getAppConfig(this).remove(key);
    }

}
	    