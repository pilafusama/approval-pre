package com.tenpay.wxwork.approval.presvr.common.utils;

public class HexConvert {

    /** 
     * 字节转16进制string
     */     
    public static String byte2HexStr(byte[] b)
    {
        String stmp="";
        StringBuilder sb = new StringBuilder("");
        for (int n=0;n<b.length;n++)
        {
            stmp = Integer.toHexString(b[n] & 0xFF);
            sb.append((stmp.length()==1)? "0"+stmp : stmp);
        }
        return sb.toString().toUpperCase().trim();
    }
    
    /**将16进制转换为二进制 
     * @param hexStr 
     * @return 
     */  
    public static byte[] hexStr2Byte(String hexStr)
    {  
        if (hexStr.length() < 1)
        {
        	return null; 
        }
        
        byte[] result = new byte[hexStr.length()/2];  
        
        for(int i = 0;i< hexStr.length()/2; i++)
        {  
                int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
                int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
                result[i] = (byte) (high * 16 + low);  
        }  
        return result;  
    }

    public static String strToUnicode(String strText) throws Exception
    {
        char c;
        StringBuilder str = new StringBuilder();
        int intAsc;
        String strHex;
        for (int i = 0; i < strText.length(); i++)
        {
            c = strText.charAt(i);
            intAsc = (int) c;
            strHex = Integer.toHexString(intAsc);
            if (intAsc > 128)
            {
            	str.append("\\u" + strHex);
            }
            else // 低位在前面补00
            {
            	str.append("\\u00" + strHex);
            }
        }
        return str.toString();
    }

    public static String unicodeToString(String hex)
    {
        int t = hex.length() / 6;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < t; i++)
        {
            String s = hex.substring(i * 6, (i + 1) * 6);
            // 高位需要补上00再转
            String s1 = s.substring(2, 4) + "00";
            // 低位直接转
            String s2 = s.substring(4);
            // 将16进制的string转为int
            int n = Integer.valueOf(s1, 16) + Integer.valueOf(s2, 16);
            // 将int转换为字符
            char[] chars = Character.toChars(n);
            str.append(new String(chars));
        }
        return str.toString();
    }
}
