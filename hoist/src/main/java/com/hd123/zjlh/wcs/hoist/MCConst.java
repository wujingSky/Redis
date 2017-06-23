/**
 * 版权所有(C)，LHWMS项目组，2017，所有权利保留。
 * 
 * 项目名：	netty1
 * 文件名：	MCConst.java
 * 模块说明：	
 * 修改历史：
 * 2017年5月4日 - Jing - 创建。
 */
package com.hd123.zjlh.wcs.hoist;

/**
 * @author Jing
 *
 */
public interface MCConst {
    public static int UNDEFINE = 0;
    public static int READ = 0x0403;        // <随机>读
    public static int BATCHREAD = 0x0401;   // 成批读
    public static int WRITE = 0x1402;       // <随机>写
    public static int BATCHWRITE = 0x1401;  // 成批写
    public static int SUBCOMMAND = 0x0000;  // 子命令
    
    /** readSingle */
    public static int UNKNOWN = -1;
    public static int SUCCESS  = 0;
    public static int ERROR = 0xC051;
    
    public static int MC3E = 0x01;
    public static int MC4E = 0x02;
    public static int ASCII_FLAG = 0x80;
    public static int MC3E_BINARY = MC3E;
    public static int MC3E_ASCII = MC3E | ASCII_FLAG;
    
    public static int RCNOTSETTTING = 0;    // 未设定
    public static int RCSUCCESS = 0x06;     // 成功
    public static int RCFAILT = 0x15;       // 失败
    public static int RCTIMEOUT = 0x55;
    
    /**PlcDeviceType*/
    public static int D = 0xA8;
}
