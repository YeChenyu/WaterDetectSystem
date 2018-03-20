package com.jida.activity.test;

import android.util.Log;

import com.example.serialport.SerialPort;
import com.jida.utils.StringUtil;

import java.io.File;
import java.io.IOException;

/**
 */
public class Transfer extends SerialPort {

	private  HandleProtocol handleProtocol = new HandleProtocol();//报文处理类
	private static int packageId = 0;// 包序号取值为1~255，每次交互后增加1，超过255后重新从1开始计数
	
	private boolean isOpen = false;// 串口是否已打开
	@Deprecated
	private static boolean isPackageIdAutoUp = true;//仅供测试demo使用

	private static Transfer self;
	
	private Transfer() {
		super();
	}

	public static Transfer getInstance() {
		if (self == null) {
			self = new Transfer();
		}
		return self;
	}

	/**
	 * 
	 * <p>Title: open</p>    
	 * <p>Description: 打开串口</p>
	 * @return true打开成功;false打开失败
	 */
	public boolean open(String path, int baudrate) {
		try {
			if (isOpen) {
				return true;
			}
			boolean isSucc = openSerialPort(new File(path), baudrate);
			if (isSucc) {
				isOpen = true;
			} else {
				closeSerialPort();
				isOpen = false;
			}
			return isSucc;
		} catch (SecurityException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}


	
	/**
	 * 
	 * <p>Title: close</p>    
	 * <p>Description: 关闭串口</p>
	 */
	public void close() {
		closeSerialPort();
		isOpen = false;
		packageId = 0;
		self = null;
	}


	private static String PATH = "/dev/ttyHSL1";//串口地址
	private static final int BAUDRATE = 1500000;//串口波特率
	/**
	 * 连接K21安全模块
	 *
	 * @return
	 */
	public boolean connectK21(int baudrate) {
		try {
			boolean isSucc = open(PATH, baudrate);
			return isSucc;
		} catch (SecurityException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * <p>Title: isOpen</p>    
	 * <p>Description: 串口是否打开</p>
	 * @return
	 */
	public boolean isOpen() {
		return isOpen;
	}
	
	/**
	 * 发送请求报文
	 */
	public void write(byte[] data) throws IOException {
		super.write(data);
	}

	/**
	 * 读取响应报文
	 * @return 0成功 1无输入流2超时 
	 * @throws SDKException 
	 */
	public int read(byte[] recvBuf, int recvLen, long waitTime) throws IOException {
		return super.read(recvBuf, recvLen, waitTime);
	}

	private static final String TAG = "Transfer";
	
	/**
	 * 
	 * <p>Title: translate</p>    
	 * <p>Description: 与K21通讯</p>
	 * @param cmd 通讯指令
	 * @param params 通讯参数
	 * @param timeout 超时时间
	 * @return RespResult
	 * @throws SDKException
	 */
	public synchronized RespResult translate(byte[] cmd, byte[] params, int timeout) throws SDKException {
		if(isPackageIdAutoUp)//20170915测试重复包处理机制增加
			Transfer.packageId++;
		if (Transfer.packageId > 0xFF)
			Transfer.packageId = 1;
		int resendCnt = 3;
		RespResult result = new RespResult();
		int tempTimeOut = timeout;
		long start = System.currentTimeMillis();
		long end = 0;
		timeout = resendCnt*timeout;
		// 封装请求报文
		Log.i(TAG, "packProtocol packageId="+ packageId);
		byte[] requestData = handleProtocol.packProtocol((byte) packageId, cmd, params);
//		Log.d(TAG, "发送数据："+ HexUtil.toString(requestData));
		try {
			//重发
			for(int i = 0; i < resendCnt; i++){
				if(i != 0) Log.i(TAG, "发送异常，请求第"+ i+ "次重发");
				write(requestData);//发送请求报文
				byte[] STXBuff = new byte[1];// 取STX
				
				int stxRet = read(STXBuff, STXBuff.length, tempTimeOut);//读取响应报文STX
				end = System.currentTimeMillis();
				if (end - start >= timeout) {
					Log.d(TAG, "Timeout stxRet" + stxRet);
					throw new SDKException(SDKException.ERR_CODE_TIME_OUT);
				}
				if (stxRet != 0) {
					Log.d(TAG, "stxRet != 0, = " + stxRet);
					continue;
//					throw new SDKException(SDKException.ERR_CODE_COMMUNICATE_ERROR);
				}
				
				if (STXBuff[0] == 0x02) {
					//响应报文数据长度
					byte[] DataLenBuff = new byte[2];
					int dataLenRet = read(DataLenBuff, DataLenBuff.length, tempTimeOut);
					end = System.currentTimeMillis();
					if (end - start >= timeout) {
						Log.d(TAG, "Timeout dataLenRet" + dataLenRet);
						throw new SDKException(SDKException.ERR_CODE_TIME_OUT);
					}
					if (dataLenRet != 0) {
						Log.d(TAG, "dataLenRet != 0, = " + dataLenRet);
						continue;
//						throw new SDKException(SDKException.ERR_CODE_COMMUNICATE_ERROR);
					}
					
					//指示后跟随的指令号、指示位、序列号、可变数据的总长度
					int dataLen = Integer.valueOf(StringUtil.byte2HexStr(DataLenBuff));
					//数据长度+ETX+LRC
					byte[] dataBuff = new byte[dataLen + 2];
					int dataRet = read(dataBuff, dataBuff.length, tempTimeOut);
					end = System.currentTimeMillis();
					if (end - start >= timeout) {
						Log.d(TAG, "Timeout dataRet" + dataRet);
						throw new SDKException(SDKException.ERR_CODE_TIME_OUT);
					}
					if (dataRet != 0) {
						Log.d(TAG, "dataRet != 0, = " + dataRet);
						continue;
						//throw new SDKException(SDKException.ERR_CODE_COMMUNICATE_ERROR);
					}
					
					if (dataBuff[dataBuff.length - 2] == 0x03) {
						//解析响应报文
						result = handleProtocol.unpackageProtocol(dataBuff, cmd);
						if (result.getRespCode().equals(SDKException.ERR_STX)) {
							continue;
						}if(result.getRespCode().equals(SDKException.ERR_RESULT_NULL)){
							throw new SDKException(result.getRespCode());
						}
						return result;
					}else {
						Log.d(TAG, "dataBuff[dataBuff.length - 2] != 0x03, = " + dataBuff[dataBuff.length - 2]);
						continue;
						//throw new SDKException(SDKException.COMMUNICATE_ERROR_ETX_ERROR);
					}
				}else {
					Log.d(TAG, "STXBuff[0] != 0x02, = " + STXBuff[0]);
					continue;
					//throw new SDKException(SDKException.COMMUNICATE_ERROR_STX_ERROR);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new SDKException(SDKException.ERR_CODE_COMMUNICATE_ERROR);
		}

		Log.d(TAG, "return nothing");
		throw new SDKException(SDKException.ERR_CODE_COMMUNICATE_ERROR);
	}

	private boolean isSupport = false;


	/**
	 * 仅供测试demo使用
	 * @param autoUp
	 */
	@Deprecated
	public void setPackageIdAutoUp(boolean autoUp){
		isPackageIdAutoUp = autoUp;
	}
	/**
	 * 仅供测试demo使用
	 */
	@Deprecated
	public int getPackageId(){
		return packageId;
	}
	/**
	 * 仅供测试demo使用
	 */
	@Deprecated
	public void add1PackageId(){
		packageId++;
	}
	/**
	 * 仅供测试demo使用
	 */
	@Deprecated
	public void setPackageId(int id){
		packageId = id;
	}
}
