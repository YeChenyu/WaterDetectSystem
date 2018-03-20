package com.jida.activity.test;

import android.util.Log;

import com.jida.utils.BCDDecode;
import com.jida.utils.LrcUtil;
import com.jida.utils.StringUtil;

import java.util.Locale;


/**
 * 报文处理类，负责封装请求报文以及解析请求报文
 */
public class HandleProtocol {
	public static final int EXECUTE_ID_SUCCESS = 0;

	public static final int EXECUTE_ID_LRC_ERROR = -2;

	public static final int EXECUTE_ID_CMD_ERROR = -1;

	public static final String TAG = HandleProtocol.class.getSimpleName();

//	private static final boolean D = false;
	//private Logger logger = Logger.getLogger(HandleProtocol.class);

	/**
	 * 组装报文
	 *
	 * @param packageId
	 * @param cmd
	 * @param params
	 * @return
	 */
	public byte[] packProtocol(byte packageId, byte[] cmd, byte[] params) {

		int len = 9;// 参数除外的字节数
		int paramLen = 0;// 参数的字节数

		if (params != null && params.length > 0)
			paramLen = params.length;
		int pos = 0;
		byte[] requestData = new byte[len + paramLen];
		// 包起止符2字符x
		requestData[pos++] = 0x02;// STX

		// 包长度,参数和指令的字节数，高字节优先输出
		String dataLenStr = String.format(Locale.US,"%04d", paramLen + 4);
		byte[] dataLen = BCDDecode.str2Bcd(dataLenStr);
		requestData[pos++] = dataLen[0];
		requestData[pos++] = dataLen[1];

		requestData[pos++] = cmd[0];// 包指令
		requestData[pos++] = cmd[1];

		requestData[pos++] = 0x2f;
		requestData[pos++] = packageId;// 包序号
		if (paramLen > 0) {
			System.arraycopy(params, 0, requestData, pos, paramLen);
			pos += paramLen;
		}
		requestData[pos++] = 0x03;// ETX
		byte[] lrc_data = new byte[requestData.length - 2];
		System.arraycopy(requestData, 1, lrc_data, 0, requestData.length - 2);
		requestData[pos] = LrcUtil.lrc_check(lrc_data);// LRC
		//logger.debug("发送数据:"+StringUtil.byte2HexStr(requestData));
		logSend(cmd, params);
		return requestData;
	}

	/**
	 * 解析报文
	 *
	 * @param src
	 * @param cmd
	 * @return
	 */
	public RespResult unpackageProtocol(byte[] src, byte[] cmd) {
		//logger.debug("接收数据:"+StringUtil.byte2HexStr(src));
		// 指令号 指示位 序列号 响应吗 可变数据 ETX LRC
		// 2字节 1字节 1字节 2字节 可变 1字节 1字节
		RespResult respResult = new RespResult();
		int pos = 0;

		byte[] src_cmd = new byte[2];
		System.arraycopy(src, pos, src_cmd, 0, 2);
		pos += 2;
		// 比对指令
		String cmdStr = StringUtil.byte2HexStr(cmd);
		String srccmdStr = StringUtil.byte2HexStr(src_cmd);
		//Log.d("","cmdStr ="+cmdStr +" srccmdStr="+srccmdStr);
		if (!cmdStr.equalsIgnoreCase(srccmdStr)) {
			respResult.setExecuteId(EXECUTE_ID_CMD_ERROR);
			return respResult;
		}
		byte dir_cmd = src[2];// 指示位, 0x2F, 请求/响应报文指示 ， 0x3F，为终端主动发送的报文
		pos++;// 指示位
		pos++;// 序列号
		if ((byte) 0x2F == dir_cmd) {
			byte[] src_response = new byte[2];
			System.arraycopy(src, pos, src_response, 0, 2);
			pos += 2;
			// 响应码
			respResult.setRespCode(StringUtil.hexStr2Str(StringUtil.byte2HexStr(src_response)));
		}

		int paramLen = src.length - pos - 2;
		if (paramLen > 0) {
			byte[] params = new byte[paramLen];
			System.arraycopy(src, pos, params, 0, paramLen);
			pos += paramLen;
			respResult.setParams(params);
			logRecv(src_cmd, src,respResult.getRespCode());
		} else {
			logRecv(src_cmd, null,respResult.getRespCode());
		}
		pos++;// ETX
		byte lrc = src[pos];
		Log.d(TAG, "lrc:"+lrc);

		String dataLenStr = String.format(Locale.US,"%04d", src.length - 2);
//		Log.d(TAG, "len:"+(src.length - 2));
//		Log.d(TAG, "dataLenStr:"+dataLenStr);
		byte[] dataLen = BCDDecode.str2Bcd(dataLenStr);

		byte[] lrcData = new byte[src.length + 1];
		System.arraycopy(dataLen, 0, lrcData, 0, 2);
		System.arraycopy(src, 0, lrcData, 2, src.length - 1);
		byte calLrc = LrcUtil.lrc_check(lrcData);// LRC
//		 Log.d(TAG, "caldata:"+StringUtil.byte2HexStr(lrcData));
//		 Log.d(TAG, "calLrc:"+calLrc);
//		 Log.d(TAG, "返回数据:"+StringUtil.byte2HexStr(respResult.getParams()));
//		logger.debug("返回数据:"+StringUtil.byte2HexStr(respResult.getParams()));
		if (lrc != calLrc) {
			respResult.setExecuteId(EXECUTE_ID_LRC_ERROR);
		} else {
			respResult.setExecuteId(EXECUTE_ID_SUCCESS);
		}

		return respResult;
	}

	/**
	 * pboc包发送包组装，0x3f
	 *
	 * @param packageId
	 * @param cmd
	 * @param params
	 * @return
	 */
	public byte[] packProtocolPBOC(byte packageId, byte[] cmd, byte[] params) {

		int len = 9;// 参数除外的字节数
		int paramLen = 0;// 参数的字节数

		if (params != null && params.length > 0)
			paramLen = params.length;
		int pos = 0;
		byte[] requestData = new byte[len + paramLen];
		// 包起止符2字符x
		requestData[pos++] = 0x02;// STX

		// 包长度,参数和指令的字节数，高字节优先输出
		String dataLenStr = String.format(Locale.US,"%04d", paramLen + 4);
		byte[] dataLen = BCDDecode.str2Bcd(dataLenStr);
		requestData[pos++] = dataLen[0];
		requestData[pos++] = dataLen[1];

		requestData[pos++] = cmd[0];// 包指令
		requestData[pos++] = cmd[1];
		requestData[pos++] = 0x3f;
		requestData[pos++] = packageId;// 包序号
		if (paramLen > 0) {
			System.arraycopy(params, 0, requestData, pos, paramLen);
			pos += paramLen;
		}
		requestData[pos++] = 0x03;// ETX
		byte[] lrc_data = new byte[requestData.length - 2];
		System.arraycopy(requestData, 1, lrc_data, 0, requestData.length - 2);
		requestData[pos] = LrcUtil.lrc_check(lrc_data);// LRC
		// Log.d(TAG, "caldata:"+StringUtil.byte2HexStr(lrc_data));
//		String str = StringUtil.byte2HexStr(requestData);
		logSend(cmd, params);
		return requestData;
	}

	/**
	 * pboc类的报文返回的数据比较特殊，没有返回码
	 *
	 * @param src
	 * @return
	 */
	public RespResult unpackageProtocolPBOC(byte[] src) {
		// 指令号 指示位 序列号 可变数据 ETX LRC
		// 2字节 1字节 1字节 可变 1字节 1字节
		RespResult respResult = new RespResult();
		int pos = 0;

		byte[] cmd = new byte[2];
		System.arraycopy(src, pos, cmd, 0, 2);

		byte src_cmd = src[1];// 取第二个字节作为指令区分
		respResult.setRespCmd(src_cmd);
		pos += 2;

		byte dir_cmd = src[2];// 指示位, 0x2F, 请求/响应报文指示 ， 0x3F，为终端主动发送的报文
		byte[] arr = { dir_cmd };
		StringUtil.byte2HexStr(arr);
		pos++;// 指示位
		pos++;// 序列号
		if ((byte) 0x2F == dir_cmd) {
			byte[] src_response = new byte[2];
			System.arraycopy(src, pos, src_response, 0, 2);
			pos += 2;
			// 响应码
			respResult.setRespCode(StringUtil.hexStr2Str(StringUtil.byte2HexStr(src_response)));
		}

		int paramLen = src.length - pos - 2;
		if (paramLen > 0) {
			byte[] params = new byte[paramLen];
			System.arraycopy(src, pos, params, 0, paramLen);
			pos += paramLen;
			respResult.setParams(params);
			logRecv(cmd, src,respResult.getRespCode());
		} else {
			logRecv(cmd, null,respResult.getRespCode());
		}

		pos++;// ETX
		byte lrc = src[pos];
		// Log.d(TAG, "lrc:"+lrc);
		String dataLenStr = String.format(Locale.US,"%04d", src.length - 2);
		byte[] dataLen = BCDDecode.str2Bcd(dataLenStr);

		byte[] lrcData = new byte[src.length + 1];
		System.arraycopy(dataLen, 0, lrcData, 0, 2);
		System.arraycopy(src, 0, lrcData, 2, src.length - 1);
		byte calLrc = LrcUtil.lrc_check(lrcData);// LRC
		// Log.d(TAG, "caldata:"+StringUtil.byte2HexStr(lrcData));
		// Log.d(TAG, "calLrc:"+calLrc);
		if (lrc != calLrc) {
			respResult.setExecuteId(-2);
		} else {
			respResult.setExecuteId(0);
		}
		return respResult;
	}

	private void logSend(byte[] cmd, byte[] params) {
		StringBuilder send = new StringBuilder();
		// send.append("==============发送 Start===============\n");
		String cmdStr = StringUtil.byte2HexStr(cmd);
		send.append("发送 >>> 指令:[" + cmdStr +"]"+"\n");
		for (String[] cmdPair : ALL_CMD) {
			if (cmdPair[0].equalsIgnoreCase(cmdStr)) {
				send.append("[" + cmdPair[1] + "]\n");
				break;
			}
		}
		send.append("发送 >>> 内容:[" + StringUtil.byte2HexStr(params) + "]\n\n");
		// send.append("==============发送 End===============\n");
		Log.d(TAG, send.toString());
//		logger.debug(send.toString());
	}

	private void logRecv(byte[] cmd, byte[] params,String respCode) {
		StringBuilder recv = new StringBuilder();
		// recv.append("==============接收 Start===============\n");
		String cmdStr = StringUtil.byte2HexStr(cmd);
		for (String[] cmdPair : ALL_CMD) {
			if (cmdPair[0].equalsIgnoreCase(cmdStr)) {
				recv.append("接收 <<< 指令:[" + cmdStr + " " + cmdPair[1] + "]\n");
				break;
			}
		}
		recv.append("接收 <<< 响应码:["+respCode+"] 内容:[" + StringUtil.byte2HexStr(params) + "]\n\n");
		// recv.append("==============接收 End===============\n");
		Log.d(TAG, recv.toString());
//		logger.debug(recv.toString());
	}

	public static final String[] CMD_OpenMagcardReader = new String[] { "D101", "开启读卡器" };
	public static final String[] CMD_CloseMagcardReader = new String[] { "D102", "关闭读卡器" };
	public static final String[] CMD_ReadPlainTrackData = new String[] { "D104", "读磁卡(明文)" };
	public static final String[] CMD_ReadEncryptedTrackData = new String[] { "D105", "读磁卡(密文)" };
	public static final String[] CMD_SetLRC_Check = new String[] { "D106", "设置LRC校验" };
	public static final String[] CMD_OpenMagchardDevice = new String[] { "D107", "非阻塞开启关闭磁头" };
	public static final String[] CMD_GetSwipeStatus = new String[] { "D108", "非阻塞获取刷卡状态" };
	public static final String[] CMD_GetIC_Status = new String[] { "D109", "非阻塞检测IC卡状态" };
	public static final String[] CMD_GetRF_Status = new String[] { "D10A", "非阻塞检测RF卡状态" };

	public static final String[] CMD_IC_PowerOn = new String[] { "E103", "集成电路卡上电" };
	public static final String[] CMD_IC_DETECTION = new String[] { "E101", "集成电路卡检测" };
	public static final String[] CMD_IC_PowerOff = new String[] { "E104", "集成电路卡下电" };
	public static final String[] CMD_IC_ExchangeApdu = new String[] { "E105", "集成电路卡通讯" };

	public static final String[] CMD_GetDeviceInfo = new String[] { "F101", "读取设备信息" };
	public static final String[] CMD_GetRandom = new String[] { "F102", "获取随机数" };
	public static final String[] CMD_IsOpenSecurity = new String[] { "F104", "是否开启系统安全" };
	public static final String[] CMD_GetSecureStatus = new String[] { "F106", "获取系统安全状态" };
	public static final String[] CMD_GetCertKeyPair = new String[] { "F108", "生成认证公私钥对" };
	public static final String[] CMD_CertPriKeyCalculate = new String[] { "F109", "认证私钥运算" };
	public static final String[] CMD_CertReadWrite = new String[] { "F10A", "证书读写" };
	public static final String[] CMD_GetCertPubKey = new String[] { "F10C", "获取认证公钥" };
	public static final String[] CMD_DeleteCertPriKey = new String[] { "F10D", "删除认证私钥" };

	public static final String[] CMD_LoadMasterKey = new String[] { "1A02", "装载主密钥" };
	public static final String[] CMD_CalculateData = new String[] { "1A03", "数据加密/解密" };
	public static final String[] CMD_CalculateMAC = new String[] { "1A04", "MAC计算" };
	public static final String[] CMD_LoadWorkKey = new String[] { "1A05", "装载工作密钥" };
	public static final String[] CMD_EncryptPin = new String[] { "1A1A", "密码输入（设备无键盘输入模式）" };
	public static final String[] CMD_LoadDesPlainKey = new String[] { "1A1B", "装载明文DES密钥" };
	public static final String[] CMD_CalculateCheckValue = new String[] { "1A1C", "计算密钥KCV" };
	public static final String[] CMD_CalculateDES = new String[] { "1A1D", "DES计算" };
	public static final String[] CMD_DeleteMasterKey = new String[] { "1A1E", "删除主密钥" };
	public static final String[] CMD_FormatAllKey = new String[] { "1A1F", "格式化密钥区" };
	public static final String[] CMD_PinpadGetRandom = new String[] { "1A2A", "获取随机数" };
	public static final String[] CMD_PinpadGetCoords = new String[] { "1A2C", "获取密码键盘坐标" };
	public static final String[] CMD_PinpadGetInputState = new String[] { "1A2D", "获取密码键盘状态" };
	public static final String[] CMD_PinpadManage = new String[] { "1A2E", "接管/释放触摸屏" };
	public static final String[] CMD_IntptPinpadManage = new String[] { "1A2F", "中断接管/释放触摸屏" };

	public static final String[] CMD_GetPrinterStatus = new String[] { "1B47", "获取打印机状态" };
	public static final String[] CMD_InitPrinter = new String[] { "1B49", "初始化指令" };
	public static final String[] CMD_Print = new String[] { "1B50", "打印指令" };
	public static final String[] CMD_SetPrinterParams = new String[] { "1B51", "设置打印属性" };

	public static final String[] CMD_SetTerminalParams = new String[] { "1C11", "设置终端参数" };
	public static final String[] CMD_SetAID = new String[] { "1C12", "设置AID" };
	public static final String[] CMD_SetRID = new String[] { "1C13", "设置公钥" };
	public static final String[] CMD_PBOC_Start = new String[] { "1C14", "PBOC开始执行流程" };
	public static final String[] CMD_PBOC_SelectAID = new String[] { "1C15", "PBOC选择AID" };
	public static final String[] CMD_PBOC_Confirm_CardNo = new String[] { "1C16", "PBOC卡号确认" };
	public static final String[] CMD_PBOC_Input_Pin = new String[] { "1C17", "PBOC输入PIN" };
	public static final String[] CMD_PBOC_Keeper_Confirm = new String[] { "1C18", "PBOC持卡人证件确认" };
	public static final String[] CMD_PBOC_Danger_Manage = new String[] { "1C19", "PBOC风险管理" };
	public static final String[] CMD_PBOC_First_Check = new String[] { "1C20", "PBOC卡片第一次验证" };
	public static final String[] CMD_PBOC_Get_Tlv = new String[] { "1C21", "PBOC获取TLV数据" };
	public static final String[] CMD_PBOC_Second_Check = new String[] { "1C22", "PBOC联机结果（二次授权)" };
	public static final String[] CMD_PBOC_GetEC_Banlance = new String[] { "1C23", "PBOC获取电子现金余额" };
	public static final String[] CMD_PBOC_Get_Card_Log = new String[] { "1C24", "PBOC开始获取卡片日志" };
	public static final String[] CMD_PBOC_Set_Get_Tlv = new String[] { "1C25", "PBOC设置/获取TLV数据" };
	public static final String[] CMD_PBOC_Get_Card_Log_Callback = new String[] { "1C26", "PBOC回调函数：获取卡片交易日志" };
	public static final String[] CMD_PBOC_ClearEMV_Log = new String[] { "1C27", "PBOC清除EMV日志" };

	public static final String[] CMD_SYSTEM_BEEPER = new String[] { "1D01", "蜂鸣器" };
	public static final String[] CMD_SYSTEM_PilotLamp = new String[] { "1D02", "指示灯" };
	public static final String[] CMD_SYSTEM_RESET = new String[] { "1D08", "取消复位操作" };
	public static final String[] CMD_SYSTEM_SET_BaudRate = new String[] { "1D12", "设置波特率" };

//	public static final String[] CMD_PRODUCE_SET_PARAMS = new String[] { "FF01", "设置SN/PN/CSN/KSN" };
//	public static final String[] CMD_PRODUCE_GET_PARAMS = new String[] { "FF02", "获取SN/PN/CSN/KSN" };

	public final static String[][] ALL_CMD = { CMD_IC_DETECTION,CMD_OpenMagcardReader, CMD_CloseMagcardReader, CMD_ReadPlainTrackData,
			CMD_ReadEncryptedTrackData, CMD_SetLRC_Check, CMD_OpenMagchardDevice, CMD_GetSwipeStatus, CMD_GetIC_Status,
			CMD_GetRF_Status, CMD_IC_PowerOn, CMD_IC_PowerOff, CMD_IC_ExchangeApdu, CMD_GetDeviceInfo, CMD_GetRandom,
			CMD_GetCertKeyPair, CMD_CertPriKeyCalculate, CMD_CertReadWrite, CMD_GetCertPubKey, CMD_DeleteCertPriKey,
			CMD_IsOpenSecurity,
			CMD_LoadMasterKey, CMD_CalculateData, CMD_CalculateMAC, CMD_LoadWorkKey, CMD_EncryptPin,
			CMD_LoadDesPlainKey, CMD_CalculateCheckValue, CMD_CalculateDES, CMD_DeleteMasterKey, CMD_FormatAllKey,
			CMD_PinpadGetRandom, CMD_PinpadGetCoords, CMD_PinpadGetCoords, CMD_PinpadGetInputState,
			CMD_PinpadManage, CMD_IntptPinpadManage,
			CMD_GetPrinterStatus, CMD_InitPrinter, CMD_Print, CMD_SetPrinterParams,
			CMD_SetTerminalParams, CMD_SetAID, CMD_SetRID, CMD_PBOC_Start, CMD_PBOC_SelectAID, CMD_PBOC_Confirm_CardNo,
			CMD_PBOC_Input_Pin, CMD_PBOC_Keeper_Confirm, CMD_PBOC_Danger_Manage, CMD_PBOC_First_Check, CMD_PBOC_Get_Tlv,
			CMD_PBOC_Second_Check, CMD_PBOC_GetEC_Banlance, CMD_PBOC_Get_Card_Log, CMD_PBOC_Set_Get_Tlv,
			CMD_PBOC_Get_Card_Log_Callback, CMD_PBOC_ClearEMV_Log, CMD_SYSTEM_BEEPER, CMD_SYSTEM_PilotLamp,
			CMD_SYSTEM_RESET, CMD_SYSTEM_SET_BaudRate };

}
