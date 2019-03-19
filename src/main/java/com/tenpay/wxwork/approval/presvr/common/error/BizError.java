package com.tenpay.wxwork.approval.presvr.common.error;

/**
 * 质押贷错误枚举类
 * 银行通用错误码：统一使用8843开头，主要用于银行错误码的转换，如前置机错误码转换配置文件（BankErrorCode.xml、BankRules.xml、CommRules.xml），不使用在Java代码中
 * 银行个性错误码：主要以8842、288开头，属于某个银行的特性，为更精确的引导客户而出现，弥补银行通用错误码覆盖不了的部分错误，使用在错误码转换配置文件中，不建议使用在Java代码中
 * 快捷平台错误码：主要以8841开头，属于fastpay-core层及具体银行代码的错误，该类错误大都为前置机内部的业务判定或服务异常
 * 前置平台错误码：主要以355开头，属于框架层错误，涉及到基础参数校验，基础IO等，建议在bap中使用
 * 证书服务错误码：以8128（模块ID）开头，证书服务错误码
 * 质押贷服务通用错误码：以各模块ID开头，系统繁忙为9999
 * 质押贷服务错误码：系统错误码以（模块ID）1开头；业务错误码以（模块ID）2开头；银行错误码以（模块ID）3开头
 * <p>
 */
public enum BizError {

    // 缺省错误码:系统繁忙
    DEFAULT_ERROR(9999, "system busy."),
    
    // -------------系统错误码-----------------------
    // 银行渠道配置异常或调整
    INVALID_CONFIG(0, "invalid config."),
    NO_FIND_ERROR(1, "没有找到错误信息"),
    // 调用relay失败
    RELAY_CALL_ERROR(2, "调用relay失败"), 
    // 调用middle失败
    MIDDLE_CALL_ERROR(3, "调用middle失败"),
    //bean转换为map失败
    TRANSFER_BEAN2MAP_ERROR(4, "Failed to transfer bean to map."),
    //http请求协议参数错误
    CLIENT_PROTOCOL_ERROR(5, "Http client protocol error."),
    //http请求网络错误
    HTTP_IO_ERROR(6, "Http io error."),
    // 发送http请求失败
    HTTP_CLIENT_ERROR(7, "发送http请求失败"),
    //请求放款方异常
    REQUEST_LOANEE_ERROR(8, "request loanee error."),
    //请求转换json失败
    OBJECT2STR_CONVERT_ERROR(9, "Object converted to json error."),
    //http请求协议参数错误
    ILLEGAL_HTTARG_ERROR(10, "illegal http(s) argument."),
    //请求银行的url非法
    ILLEGAL_LOANURL_ERROR(11, "loan bank url illegal."), 
    //银行返回失败
    GET_BANK_RESPONSE_FAILED(12, "Get Bank response failed."), 
    // 银行信息不存在
    BANK_INFO_NOT_EXIST(13, "Bank info not exist."),
    // --------前置机与bank_proxy及后台交互产生的错误及基本的业务错误码--------------
    //不支持的银行类型
    UNSUPPORTED_BANK(1000, "不支持的银行类型."), 
    // 不支持的请求类型
    UNSUPPORT_REQUEST(1001, "不支持的请求类型"),
    // 参数未输入
    PARAM_NOT_EXIST(1002, "参数未输入"),
    // 银行类型不存在
    BANK_TYPE_NOT_EXIST(1003, "银行类型不存在"),
    // 非法域
    ILLEGAL_FIELDS(1004, "非法域"),
    //返回报文没有签名字段
    LACK_SIGN_ERROR(1005, "lack sign field."),
    //获取私钥失败
    GET_PRIKEY_ERROR(1006, "Failed to get private key."),
    //签名源串为空
    SIGNSRC_EMPTY_ERROR(1007, "Sign src is empty."),
    //返回信息关键信息不一致
    KEYMSG_CONSISTENCY_ERROR(1008, "请求报文与接收报文的关键信息不一致."),
    //AES加密
    AES_ENCRYPT_ERROR(1009, "AES加密失败."),
    //AES解密
    AES_DECRYPT_ERROR(1010, "AES解密失败."),
    //不支持的签名算法
    SIGN_TYPE_ERROR(1011, "签名算法错误."),
    //不支持的padding方式
    PADDING_TYPE_ERROR(1012, "padding方式错误."),
    //key非法
    INVALID_KEY_ERROR(1013, "加密或解密的key非法"),
    //key非法
    ILLEGAL_BLOCK_ERROR(1014, "加密块非法"), 
    //不支持的padding方式
    BAD_PADDING_ERROR(1015, "明文数据已破坏."),
    //验证签名失败
    VERIFY_SIGN_ERROR(1016, "验证银行签名失败"),
    //不支持的padding方式
    UNSUPPORTED_ENCODING_ERROR(1017, "不支持的编码方式."),
    //生成签名失败
    GEN_SIGN_ERROR(1018, "生成签名失败"),
    //生成签名失败
    ENCODE_SIGN_ERROR(1019, "签名base64编码失败"),
    //请求转换json失败
    JSON_CONVERT_ERROR(1020, "json转换失败"),
    //报文时间戳过期
    PACKAGE_TIMESTAMP_ERROR(1021, "报文时间戳过期."),   
    //缺少bank_name
    LACK_BANKNAME_ERROR(1022, "lack of bank name."),
    //获取银行证书失败
    GET_BANK_CERT_ERROR(1023, "Failed to get bank cert."),
    //银行返回信息异常
    INVALID_BANK_RETURN(1024, "银行返回信息异常"),
    // 银行返回报文字段解析失败
    MISSING_REQUIRED_FIELD(1025, "银行返回报文字段缺失"),
    // 不支持的签名算法
    UNSUPPORT_SIGN_TYPE(1026, "不支持的签名算法"),
    //获取公钥失败
    GET_PUBLIC_ERROR(1027, "Failed to get public key."),
    // --------银行返回的业务错误码--------------
    //1)程序类错误
    // 报文格式错误
    PACKAGE_FORMAT_ERROR(3001, "银行报错报文格式错误"),
    // 必填域缺失
    LACK_REQURIED_FILED(3002, "银行报错报文格式错误"),
    // 验证码错误
    VERIFYCODE_ERROR(3003, "验证码错误"),
    //验证签名失败
    BANK_VERIFY_SIGN_ERROR(3004, "银行验证签名失败"),
    //通讯公钥异常
    CERT_ABNORMAL(3005, "通讯公钥异常"),
    //文件格式不正确
    FILE_FORMAT_ERROR(3006, "文件格式不正确"),
    //业务日期不正确
    BIZ_DATE_ERROR(3007, "业务日期不正确"),  
    //文件已锁定
    FILE_LOCKED(3008, "文件已锁定"),
    //文件不存在
    FILE_NOT_EXIST(3009, "文件不存在"),
    //文件无法解密
    FILE_DECRYPT_ERROR(3010, "文件无法解密"),
    //文件已处理
    FILE_ALREADY_HANDLED(3011, "文件已处理"),
    //文件无法解压缩
    FILE_CANNT_DECOMPRESS(3012, "文件无法解压缩"),
    //文件无法删除
    FILE_CANNT_DELETE(3013, "文件无法删除"),
    //文件摘要不正确
    FILE_ABSTRACT_ERROR(3014, "文件摘要不正确"),
    //文件摘要不正确
    FILE_HANDLE_ERROR(3015, "文件读取处理失败"),  
    //无接口码
    INVALID_INTERFACE_CODE(3016, "无接口码"),  

    RSA_DECRYPT_ERROR(3017, "RSA解密失败"),  
    RSA_ENCRYPT_ERROR(3018, "RSA加密失败"),

    //业务错误
    UNSUPPORTED_APPROVAL_TYPE_ERROR(3301, "Unsupported approval type."); 
    
    private int errorCode;

    private String errMsg;

    BizError(int errorCode, String errMsg) {
        this.errorCode = errorCode;
        this.errMsg = errMsg;
    }

    public int errorCode() {
        return errorCode;
    }

    public String errorMsg() {
        return errMsg;
    }

    public String toString() {
        return String.valueOf(this.errorCode + ":" + this.errMsg);
    }
}
