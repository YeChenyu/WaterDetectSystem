package com.jida.activity.test;

/**
 * 
 * ClassName: SDKException 
 * <p>Description: SDK自定义异常类</p>
 * @author 宋来忠 lzsoong@gmail.com
 * <p>Date: 2016-4-16</p>
 */
public class SDKException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 通讯异常
     */
    public final static String ERR_CODE_COMMUNICATE_ERROR = "99";// 通讯异常

    /**
     * IO异常
     */
    public final static String ERR_CODE_IO_ERROR = "98";// IO异常
    /**
     * 参数错误
     */
    public final static String SDK_ERR_CODE_PARAM_ERROR = "92";//   参数错误
    
    /**
     * 成功
     */
    public final static String CODE_SUCCESS = "00";// 成功
    
    /**
     * 指令码不支持
     */
    public final static String ERR_CODE_CMD_NONSUPPORT = "01";// 指令码不支持
    
    /**
     * 参数错误
     */
    public final static String ERR_CODE_PARAM_ERROR = "02";// 参数错误
    
    /**
     * 可变数据域长度错误
     */
    public final static String ERR_CODE_VARIABLE_LENGHT = "03"; // 可变数据域长度错误
    
    /**
     * 帧格式错误
     */
    public final static String ERR_CODE_FRAME_ERROR = "04"; // 帧格式错误
    
    /**
     * LRC交易失败
     */
    public final static String ERR_CODE_LRC_ERROR = "05"; // LRC交易失败
    
    /**
     * 其他
     */
    public final static String ERR_CODE_OTHER = "06"; // 其他
    
    /**
     * 超时
     */
    public final static String ERR_CODE_TIME_OUT = "07"; // 超时
    
    /**
     * 返回当前状态
     */
    public final static String ERR_CODE_CUS_STATUS = "08"; // 返回当前状态
    
    /**
     * 设备认证失败
     */
    public final static String ERR_DEVICE_AUTHENTICATE = "09"; //  设备认证失败
    
    /**
     * 外部认证失败
     */
    public final static String ERR_EXTERN_AUTHENTICATE = "0A"; // 外部认证失败
    
    /**
     * 公钥灌装失败
     */
    public final static String ERR_PUBLIC_KEY = "0B"; // 公钥灌装失败
    
    /**
     * 生成密钥对失败
     */
    public final static String ERR_GENERATE_KEYPAIR = "0C"; // 生成密钥对失败

    /**
     * 用户取消
     */
    public final static String ERR_CODE_USER_CANCEL = "10"; // 用户取消
    
    /**
     * 算法不支持
     */
    public final static String ERR_UNSUPPORT_ALG = "11"; // 算法不支持
    
    /**
     * 长度不匹配
     */
    public final static String ERR_LEN_NO_MATCH = "12"; // 长度不匹配
    
    /**
     * 参数ID错误
     */
    public final static String ERR_INVALID_ID = "13"; // 参数ID错误
    
    /**
     * INVALID参数错误
     */
    public final static String ERR_INVALID_PARAM = "14"; // INVALID参数错误
    
    /**
     * STX参数ID错误
     */
    public final static String ERR_STX = "15"; // STX参数ID错误
    
    /**
     * ETX参数错误
     */
    public final static String ERR_ETX = "16"; // ETX参数错误
    
    /**
     * 二磁道错误
     */
    public final static String ERR_TRACK2 = "17"; // 二磁道错误
    
    /**
     * 内存错误
     */
    public final static String ERR_MEM = "18"; // 内存错误
    
    /**
     * 获取解析密钥失败
     */
    public final static String ERR_GET_DECODE_KEY = "20"; // 获取解析密钥失败
    
    /**
     * 返回数据为空, 数据包ID重复
     */
    public final static String ERR_RESULT_NULL = "21"; // 返回数据为空，数据包ID重复
    
    /**
     * 图片宽度大于384
     */
    public final static String ERR_IMG_WIDTH = "22"; // 图片宽度大于384
    
    /**
     * 身份证图片解析错误
     */
    public final static String ERR_IMG_DECODE  = "23"; // 身份证图片解析错误
    /**
     * 身份证图片解析文件不存在
     */
    public final static String ERR_IMG_DECODE_FILE_NOT_FOUND  = "24"; // 身份证图片解析文件不存在
    
    /**
     * 通讯异常_STX位错误
     */
    public final static String COMMUNICATE_ERROR_STX_ERROR = "31";// 通讯异常_STX位错误
	
    /**
     * 通讯异常_ETX位错误
     */
    public final static String COMMUNICATE_ERROR_ETX_ERROR = "32";// 通讯异常_ETX位错误
	
    /**
     * 通讯异常
     */
    public final static String COMMUNICATE_ERROR_TIMEOUT = "33";// 通讯异常
	
    /**
     * 通讯异常IO异常
     */
    public final static String COMMUNICATE_ERROR_IO_ERROR = "34";// 通讯异常IO异常
	
    /**
     * 返回数据指令号不同
     */
    public final static String COMMUNICATE_ERROR_BACK_CMD_ERROR = "35";// 返回数据指令号不同
	
    /**
     * 返回数据LRC校验失败
     */
    public final static String COMMUNICATE_ERROR_LRC_ERROR = "36";// 返回数据LRC校验失败
	
    /**
     * 其他通讯异常
     */
    public final static String COMMUNICATE_ERROR_OTHER = "37";// 其他通讯异常
	
    /**
     * 指令停止
     */
    public final static String COMMUNICATE_ERROR_STOP = "38";// 指令停止
    
    /**
     * 错误码
     */
    private String errCode;

    /**
     * 
     * <p>Title: SDKException</p>    
     * <p>Description: SDKException错误码实例化方法</p>    
     * @param errCode 错误码
     */
    public SDKException(String errCode) {
        super(setErrMsg(errCode));
        this.errCode = errCode;
    }
    
    /**
     * 
     * <p>Title: SDKException</p>    
     * <p>Description: SDKException带错误信息和错误码实例化方法</p>    
     * @param errCode 错误码
     * @param msg 错误信息
     */
    public SDKException(String errCode, String msg) {
        super(msg);
        this.errCode = errCode;
    }

    /**
     * 
     * <p>Title: setErrMsg</p>    
     * <p>Description: 设置错误信息</p>
     * @param errCode 错误码
     * @return String
     */
    private static String setErrMsg(String errCode) {
        String errMsg = "";
        switch (errCode) {
        case "00":
            errMsg = "成功";
            break;
        case "01":
            errMsg = "指令码不支持";
            break;
        case "02":
            errMsg = "参数错误";
            break;
        case "03":
            errMsg = "可变数据域长度错误";
            break;
        case "04":
            errMsg = "帧格式错误";
            break;
        case "05":
            errMsg = "LRC校验失败";
            break;
        case "06":
            errMsg = "其他";
            break;
        case "07":
            errMsg = "超时";
            break;
        case "08":
            errMsg = "返回当前状态";
            break;
        case "09":
            errMsg = "设备认证失败";
        case "10":
            errMsg = "用户取消";
            break;
        case "11":
        	errMsg = "算法不支持";
        	break;
        case "12":
        	errMsg = "长度不匹配";
        	break;
        case "13":
        	errMsg = "参数ID错误";
        	break;
        case "14":
        	errMsg = "参数错误";
        	break;
        case "15":
        	errMsg = "参数ID错误";
        	break;
        case "16":
        	errMsg = "参数错误";
        	break;
        case "17":
        	errMsg = "二磁道错误";
        	break;
        case "18":
        	errMsg = "内存错误";
        	break;
        case "20":
            errMsg = "获取解析密钥失败";
            break;
        case "21":
        	errMsg = "返回数据为空";
        	break;
        case "22":
        	errMsg = "图片宽度大于384";
        	break;
        case "23":
        	errMsg = "身份证图片解析错误";
        	break;
        case "24":
        	errMsg = "身份证图片解析文件不存在";
        	break;
        case "31":
        	errMsg = "通讯异常STX错误";
        	break;
        case "32":
        	errMsg = "通讯异常EXT错误";
        	break;
        case "33":
        	errMsg = "通讯异常超时";
        	break;
        case "34":
        	errMsg = "通讯异常IO异常";
        	break;
        case "35":
        	errMsg = "通讯异常指令号不匹配";
        	break;
        case "36":
        	errMsg = "通讯异常LRC校验失败";
        	break;
        case "37":
        	errMsg = "通讯异常其他";
        	break;
        case "38":
        	errMsg = "指令停止";
        	break;
        case "92":
            errMsg = "参数错误";
            break;
        case "98":
        	errMsg = "IO异常";
        	break;
        case "99":
            errMsg = "通讯异常";
            break;
        case "0A":
        	errMsg = "外部认证失败";
        	break;
        case "0B":
        	errMsg = "公钥灌装失败";
        	break;
        case "0C":
        	errMsg = "生成密钥对失败";
            break;
        default:
            break;
        }

        return errCode+":"+errMsg;
    }

    /**
     * 
     * <p>Title: getErrCode</p>    
     * <p>Description: 获取错误码</p>
     * @return String
     */
    public String getErrCode() {
        return errCode;
    }

}
