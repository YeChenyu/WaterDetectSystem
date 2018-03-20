package com.jida.common.manager;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import com.jida.common.config.AppConfig;
import com.jida.module.bean.AccessInfo;
import com.jida.utils.DataUtils;
import com.jida.utils.FileUtils;
import com.jida.utils.LogUtils;
import com.jida.waterdetect.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

//终端配置管理
public class TermInfoManager {

	public final static String CONF_APP_UNIQUEID = "APP_UNIQUEID";
	public final static String CONF_IS_FIRST_START = "isFirstStart";//是否第一次启动	false：不是第一次启动，不现实导航。
	public final static String CONF_UPDATE_GUIDE_IMAGE = "isUpdateGuideImage";//导航图片是否更新  true：已更新，要重新展示。
	public final static String CONF_ADVERTISMENT_NUM = "advertisment_num";//广告数
	public final static String CONF_ADVERTISMENT_KEY = "advertisment";//广告数
	public final static String CONF_COOKIE = "cookie";
	public final static String CONF_ACCESSTOKEN = "accessToken";
	public final static String CONF_ACCESSSECRET = "accessSecret";
	public final static String CONF_EXPIRESIN = "expiresIn";
	public final static String CONF_LOAD_IMAGE = "perf_loadimage";
	public final static String CONF_SCROLL = "perf_scroll";
	public final static String CONF_HTTPS_LOGIN = "perf_httpslogin";
	public final static String CONF_VOICE = "perf_voice";
	public final static String CONF_CHECKUP = "perf_checkup";

	private AccessInfo accessInfo = null;
	private Context mContext;
	private static TermInfoManager appConfig;

	public static TermInfoManager getAppConfig(Context context) {
		if (appConfig == null) {
			appConfig = new TermInfoManager();
			appConfig.mContext = context;
		}
		return appConfig;
	}

	public boolean isTheValueTrue(String key){
		String tmp = get(key);
		LogUtils.v("isTheValueTrue="+ tmp);
		if(tmp == null)
			return true;
		if(tmp.equals("true"))
			return true;
		else
			return false;
	}

	public void setTheBooleanValue(String key, boolean value){
		set(key, (value ? "true" : "false"));
	}

	public void setTheIntegerValue(String key, int value){
		set(key, String.valueOf(value));
	}

	public int getTheIntegerValue(String key){
		String tmp = get(key);
		if(tmp == null || tmp.equals("null"))
			return 0;
		else
			return Integer.parseInt(tmp);
	}
	public String getCookie() {
		return get(CONF_COOKIE);
	}

	public void setAccessToken(String accessToken) {
		set(CONF_ACCESSTOKEN, accessToken);
	}

	public String getAccessToken() {
		return get(CONF_ACCESSTOKEN);
	}

	public void setAccessSecret(String accessSecret) {
		set(CONF_ACCESSSECRET, accessSecret);
	}

	public String getAccessSecret() {
		return get(CONF_ACCESSSECRET);
	}

	public void setExpiresIn(long expiresIn) {
		set(CONF_EXPIRESIN, String.valueOf(expiresIn));
	}

	public long getExpiresIn() {
		return DataUtils.toLong(get(CONF_EXPIRESIN));
	}

	public void setAccessInfo(String accessToken, String accessSecret,
							  long expiresIn) {
		if (accessInfo == null)
			accessInfo = new AccessInfo();
		accessInfo.setAccessToken(accessToken);
		accessInfo.setAccessSecret(accessSecret);
		accessInfo.setExpiresIn(expiresIn);
		// 保存到配
		this.setAccessToken(accessToken);
		this.setAccessSecret(accessSecret);
		this.setExpiresIn(expiresIn);
	}

	public AccessInfo getAccessInfo() {
		if (accessInfo == null && !DataUtils.isEmpty(getAccessToken())
				&& !DataUtils.isEmpty(getAccessSecret())) {
			accessInfo = new AccessInfo();
			accessInfo.setAccessToken(getAccessToken());
			accessInfo.setAccessSecret(getAccessSecret());
			accessInfo.setExpiresIn(getExpiresIn());
		}
		return accessInfo;
	}

	public String get(String key) {
		Properties props = get();
		return (props != null) ? props.getProperty(key) : null;
	}

	public Properties get() {
		FileInputStream fis = null;
		Properties props = new Properties();
		try {

			// 读取files目录下的config
			// fis = activity.openFileInput(APP_CONFIG);

			// 读取app_config目录下的config
			//		            File dirConf = FileUtils.getDiskCacheDir(mContext);

			File conf =  AppConfig.getTermInfoFile();

			fis = new FileInputStream(conf);

			props.load(fis);

		} catch (Exception e)
		{

		} finally {
			try {
				fis.close();
			} catch (Exception e) {
			}
		}
		return props;
	}

	private void setProps(Properties p) {
		FileOutputStream fos = null;
		try {

			File conf = AppConfig.getTermInfoFile();

			fos = new FileOutputStream(conf);

			p.store(fos, null);
			fos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {

				fos.close();

			} catch (Exception e)
			{

			}

		}
	}

	public void set(Properties ps) {
		Properties props = get();
		props.putAll(ps);
		setProps(props);
	}

	public void set(String key, String value) {
		if(value == null)
			value = "";
		Properties props = get();
		props.setProperty(key, value);
		setProps(props);
	}

	public void remove(String... key) {
		Properties props = get();
		for (String k : key)
			props.remove(k);
		setProps(props);
	}

	public void clear()
	{
		FileUtils.deleteDirectoryAllFile(AppConfig.getTermInfoFile());
	}

	public String getAppVersion(){
		PackageManager packageManager = mContext.getPackageManager();
		PackageInfo packageInfo = null;
		String versionName = "";
		try {
			packageInfo = packageManager.getPackageInfo(
					mContext.getPackageName(), 0);
			if(packageInfo != null && packageInfo.versionName != null)
				versionName = packageInfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mContext.getString(R.string.app_name) + " V"+versionName;
	}
}
