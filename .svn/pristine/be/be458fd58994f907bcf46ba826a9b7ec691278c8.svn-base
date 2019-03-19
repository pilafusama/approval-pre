package com.tenpay.wxwork.approval.presvr.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 民生银行信用卡快捷支付报文工具类
 */
public class MessageUtils {
    /**
     * 与银行通讯消息的ContentType
     */
    public static final String MESSAGE_CONTENT = "application/json; charset=GBK";
    /**
     * 与银行通讯的消息版本号
     */
    public static final String Mesage_VERSION = "1.0.1";
    /**
     * 缺省货币代号(人民币)
     */
    public static final String DEFAULT_CURRENCY = "156";
    /**
     * 无效的证书ID
     */
    public static final String INVALID_CERTID = "0000000000000000";
    /**
     * 无效的机构标识
     */
    public static final String INVALID_INSTID = "XXXXXX";
    /**
     * 错误消息的Element name
     */
    public static final String ERROR_PACKET_NAME = "Error";

    public static final String PayUrl = "PayUrl";
    public static final String SingRefundUrl = "SingRefundUrl";


    /**
     * 把财付通证件类型转化为银行证件类型
     * 财付通证件类型：
     * 1 身份证
     * 2 护照
     * 3 军官证
     * 4 士兵证
     * 5 回乡证
     * 6 临时身份证
     * 7 户口簿
     * 8 警官证
     * 9 台胞证
     * 10 营业执照
     * 11 其它证件
     * <p>
     * <p>
     * 民生银行的证件类型
     * 01 18位身份证
     * 02 15位身份证
     * 03 军官证
     * 04 台胞证
     * 05 05－回乡证
     * 06 06－港澳通行证
     * 10 护照
     * 11 户口簿
     * 12 中国护照
     * 13 外国护照
     * 14 香港通行证
     * 15 澳门通行证
     * 16 台湾通行
     *
     * @param creType
     * @return
     */
    public static String creTypeTrans(int creType, String certNo) {
        if (StringUtils.isBlank(certNo)) {
            return "-1";
        }
        String typeStr = "";
        switch (creType) {
            case 1:              // 身份证
                if (certNo.trim().length() == 18) {
                    typeStr = "01";
                } else if (certNo.trim().length() == 15) {
                    typeStr = "02";
                } else {
                    typeStr = "-1";
                }
                break;
            case 2:              //外国护照
                typeStr = "13";
                break;
            case 5:              // 回乡证
                typeStr = "05";
                break;
            case 9:              // 台胞证
                typeStr = "04";
                break;
            default:
                typeStr = "-1";
        }
        return typeStr;
    }

}
