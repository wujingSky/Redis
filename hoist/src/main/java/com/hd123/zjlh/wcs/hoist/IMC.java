/**
 * 版权所有(C)，LHWMS项目组，2017，所有权利保留。
 * 
 * 项目名：	netty1
 * 文件名：	IMC.java
 * 模块说明：	
 * 修改历史：
 * 2017年5月4日 - Jing - 创建。
 */
package com.hd123.zjlh.wcs.hoist;

/**
 * @author Jing
 *
 */
public interface IMC {
    MCResponseMessage batchWrite(String iDeviceName, int iSize, int[] iData) throws Exception ;
    
    MCResponseMessage batchRead(String iDeviceName, int iSize) throws Exception ;
    
    MCResponseMessage setDevice(String iDeviceName, int iData) throws Exception ;
    
    MCResponseMessage getByRegisterName(String iDeviceName) throws Exception ;
}
