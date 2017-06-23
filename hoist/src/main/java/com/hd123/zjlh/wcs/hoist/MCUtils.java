/**
 * 版权所有(C)，LHWMS项目组，2017，所有权利保留。
 * 
 * 项目名：	netty1
 * 文件名：	MCUtils.java
 * 模块说明：	
 * 修改历史：
 * 2017年5月5日 - Jing - 创建。
 */
package com.hd123.zjlh.wcs.hoist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Jing
 *
 */
public class MCUtils {
    
    public static String toHexFixLengthString(int source, int length){
        if(length <= 0)
            return Integer.toHexString(source);
        
        String target = Integer.toHexString(source);
        return StringUtils.repeat("0", length - target.length()) + target;
    }
    
    public static String toFixLengthString(int source, int length){
        if(length <= 0)
            return Integer.toString(source);
        
        String target = Integer.toString(source);
        return StringUtils.repeat("0", length - target.length()) + target;
    }
    
    public static String ToUInt16(byte[] arrays){
        StringBuilder result = new StringBuilder();
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < arrays.length; i++) {
            int t = getUInt16(arrays[i]);
            list.add(Integer.toHexString(t));
        }
        
        Collections.reverse(list);
        for(String str : list){
            result.append(str);
        }
        
        return result.toString().toUpperCase();
    }
    
    public static byte[] hexStr2Bytes(String src) {
        int m = 0, n = 0;
        int l = src.length() / 2;
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            m = i * 2 + 1;
            n = m + 1;
            int s = Integer.decode("0x" + src.substring(i * 2, m) + src.substring(m, n));
            ret[i] = (byte) s;
        }
        return ret;
    }
    
    public static String byte2HexStr(byte[] b) {
        String stmp = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
        }
        return sb.toString().toUpperCase().trim();
    }
    
    private static int getUInt16(int i){
        return i & 0x0000ffff;
    }
}
