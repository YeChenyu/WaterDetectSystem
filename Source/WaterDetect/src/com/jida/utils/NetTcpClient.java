package com.jida.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.jida.waterdetect.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class NetTcpClient{

	private final static String TAG = "NetTcpClient";
	private boolean D = true;
	public final static int TCP_READ_SUCC = 1;
	public final static int TCP_READ_FAIL = 2;
	public final static int TCP_CONNECT_FAIL = 3;
	public final static int TCP_CONNECT_SUCC = 4;

	private int SERVER_PORT = 901;//端口号
	private String SERVER_IP = "192.168.1.132";//连接IP

	private SSLSocket s = null;

	private BufferedInputStream bis = null;
	private BufferedOutputStream bos = null;

	private byte[] readBuffer = new byte[1024];

	private String IP = null;
	private int NO = 0;
	private Context mContext;
	private int timeouts;

	public final static int certId = R.raw.ca;
	public static final String CLIENT_KET_PASSWORD = "123456";//私钥密码

	public interface TlsConnectingListener
	{
		public void onFinish(int result);
	}

	public interface TlsWriteListener
	{
		public void onFinish(int result);
	}

	public interface TlsReadListener
	{
		public int readProcess(byte[] recv, int len);
		public void onFinish(int result);
	}

	public NetTcpClient( Context context,String ip,int no){
		LogUtils.d( "NetTcpClientThread ");

		mContext = context;

		IP = ip;
		NO = no;
		SERVER_IP = IP;
		SERVER_PORT = NO;
	}


	public void netWrite(byte[] buf, TlsWriteListener listener)
	{
		new NetWriteThread( buf, listener).execute(new String[0]);
	}

	public class NetWriteThread extends AsyncTask<String, Integer, byte[]>{
		@SuppressWarnings("unused")
		byte[] buf;
		TlsWriteListener mTlsWriteListener;
		int result = 0;
		public NetWriteThread( byte[] buf, TlsWriteListener listener)
		{
			this.buf = buf;
			mTlsWriteListener = listener;
		}

		@Override
		protected byte[] doInBackground(String... params) {
			// TODO Auto-generated method stub
			if(s == null || buf == null)
			{
				result = -1;
				return null;
			}
			try {
				if(bos == null)
					bos = new BufferedOutputStream(s.getOutputStream());
				LogUtils.d(DataUtils.hexByte2HexStr(buf, 0, buf.length));
				bos.write(buf, 0, buf.length);
				bos.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				LogUtils.d("NetWriteThread",e);
				result = -2;
			}
			return null;
		}

		protected void onPostExecute(byte[] param)
		{
			super.onPostExecute(param);
			mTlsWriteListener.onFinish(result);
		}
		protected void onPreExecute()
		{
			super.onPreExecute();
		}

		protected void onProgressUpdate(Integer[] paramArrayOfInteger)
		{
			super.onProgressUpdate(paramArrayOfInteger);
		}

	}

	public  void netReadThread( TlsReadListener listener, int timeouts)
	{
		this.timeouts = timeouts;
		new NetReadThread(listener).execute(new String[0]);
	}

	boolean synRead = true;
	public void setSynRead()
	{
		synRead = true;
	}

	public boolean getSynRead()
	{
		return synRead;
	}

	public class NetReadThread   extends AsyncTask<String, Integer, byte[]>{
		@SuppressWarnings("unused")

		TlsReadListener mTlsReadListener;
		int result;
		public NetReadThread( TlsReadListener listener)
		{
			mTlsReadListener = listener;
		}


		@Override
		protected byte[] doInBackground(String... params) {

			while(true)
			{
				synchronized(this)
				{
					synRead = false;
					int len = 0;
					if(s == null)
					{
						result = -1;
						return null;
					}
					LogUtils.d( "start ssl read");

					try{
						s.setSoTimeout(timeouts*1000);
						if(bis == null)
							bis = new BufferedInputStream(s.getInputStream());
						len = bis.read(readBuffer);

					}catch (ConnectException e)
					{
						e.printStackTrace();
						LogUtils.d("NetReadThread",e);
						result = -2;
						return null;

					}catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						LogUtils.d("NetReadThread",e);
						result = -2;
						return null;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						LogUtils.d("NetReadThread",e);
						result = -2;
						return null;
					}

					if(len >0)
					{

						LogUtils.d(DataUtils.hexByte2HexStr(readBuffer, 0, len));
						if(mTlsReadListener.readProcess(readBuffer, len) > 0)
						{
							result = 0;
							return null;
						}

					}else
					{
						LogUtils.d(String.format("read errcode:%d", len));
						result = -3;
						return null;
					}
				}

			}
		}

		protected void onPostExecute(byte[] param)
		{
			super.onPostExecute(param);
			mTlsReadListener.onFinish(result);
		}
		protected void onPreExecute()
		{
			super.onPreExecute();
		}

		protected void onProgressUpdate(Integer[] paramArrayOfInteger)
		{
			super.onProgressUpdate(paramArrayOfInteger);
		}
	}


	public void closeSockt()
	{
		new NetColseThread().start();
	}

	public class NetColseThread extends Thread {
		@SuppressWarnings("unused")

		public void run() {

			try{

				if(bis!=null)bis.close();
				if(bos!=null)bos.close();
				if(s != null)
				{
					s.close();
				}

				bis = null;
				bos = null;
				s = null;

			}catch(IOException e)
			{
				e.printStackTrace();
				LogUtils.d("NetColseThread",e);
			}
		}

	}

	public void init(TlsConnectingListener listener){

		new NetInitThread(listener).execute(new String[0]);

	}

	public class NetInitThread  extends AsyncTask<String, Integer, byte[]>{
		@SuppressWarnings("unused")

		TlsConnectingListener mTlsConnectingListener;
		int result = 0;
		public NetInitThread( TlsConnectingListener listener)
		{
			mTlsConnectingListener = listener;
		}


		@Override
		protected byte[] doInBackground(String... params) {
			try {

				SSLSocketFactory mySSLSocketFactory = getFactory(mContext.getResources().openRawResource(certId), CLIENT_KET_PASSWORD);

				LogUtils.d( "start ssl connecting");

				//生成SSLSocket
				s = null;
				if(mySSLSocketFactory == null)
				{
					LogUtils.d("start ssl connecting fail");
					result = -1;
					return null;
				}

//		            s = (SSLSocket) mySSLSocketFactory.createSocket(SERVER_IP,SERVER_PORT);
				s = (SSLSocket)enableTLSOnSocket(mySSLSocketFactory.createSocket(SERVER_IP,SERVER_PORT));

				if( s!= null)
				{
					LogUtils.d( "sslconnected is" + s);
					return null;
				}

			} catch (Exception e) {
				e.printStackTrace();
				LogUtils.d("NetInitThread",e);
				result = -1;
			}
			return null;
		}
		protected void onPostExecute(byte[] param)
		{
			super.onPostExecute(param);
			mTlsConnectingListener.onFinish(result);
		}
		protected void onPreExecute()
		{
			super.onPreExecute();
		}

		protected void onProgressUpdate(Integer[] paramArrayOfInteger)
		{
			super.onProgressUpdate(paramArrayOfInteger);
		}

	}
	/**
	 * 获得ssl连接的验证工厂
	 *
	 * @param keyInput
	 * @param pKeyPassword
	 * @return
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	private SSLSocketFactory getFactory(InputStream keyInput,
										String pKeyPassword) throws GeneralSecurityException, IOException {

		// 使用p12格式的证书，也支持其他类型的，具体请看KeyStore的API
		KeyStore keyStore = KeyStore.getInstance("PKCS12");

		// j2se要使用SUNX509
		KeyManagerFactory keyManagerFactory = KeyManagerFactory
				.getInstance("X509");


		// InputStream keyInput = new FileInputStream(pKeyFile);
		keyStore.load(keyInput, pKeyPassword.toCharArray());
		keyInput.close();

		keyManagerFactory.init(keyStore, pKeyPassword.toCharArray());

		SSLContext context = SSLContext.getInstance("TLS");

		// init的第一个参数是验证客户端证书，第二个参数验证CA证书，由于私签的CA，所有绕过证书安全认证
		context.init(keyManagerFactory.getKeyManagers(),
				new TrustManager[] { new MyTrustManager() },null);

		if (keyInput != null) {
			keyInput.close();
		}
		else {
			System.out.println("keyInput is null");
		}
		return context.getSocketFactory();
	}

	private class MyTrustManager implements X509TrustManager{




		@Override
		public X509Certificate[] getAcceptedIssuers() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void checkClientTrusted(
				X509Certificate[] arg0, String arg1)
				throws java.security.cert.CertificateException {
			// TODO Auto-generated method stub

		}

		@Override
		public void checkServerTrusted(
				X509Certificate[] arg0, String arg1)
				throws java.security.cert.CertificateException {
			// TODO Auto-generated method stub

		}
	}

	public static Socket enableTLSOnSocket(Socket socket) {

		if(socket != null && (socket instanceof SSLSocket)) {

			String[] protocols = ((SSLSocket) socket).getEnabledProtocols();
			List<String> supports = new ArrayList<String>();
			if (protocols != null && protocols.length > 0) {
				supports.addAll(Arrays.asList(protocols));
			}

			Collections.addAll(supports, "TLSv1.1", "TLSv1.2");
			((SSLSocket) socket).setEnabledProtocols(supports.toArray(new String[supports.size()]));

			String[] cipherSuites = ((SSLSocket) socket).getEnabledCipherSuites();

			List<String> supportsCipher = new ArrayList<String>();
			if (cipherSuites != null && cipherSuites.length > 0) {
				supportsCipher.addAll(Arrays.asList(cipherSuites));
			}
			Collections.addAll(supportsCipher,
					"TLS_RSA_WITH_AES_256_CBC_SHA");
			((SSLSocket) socket).setEnabledCipherSuites(supportsCipher.toArray(new String[supportsCipher.size()]));
		}
		return socket;
	}

}
