/**
 * 版权所有(C)，LHWMS项目组，2017，所有权利保留。
 * 
 * 项目名：	netty1
 * 文件名：	MCProtocolTCP.java
 * 模块说明：	
 * 修改历史：
 * 2017年5月5日 - Jing - 创建。
 */
package com.hd123.zjlh.wcs.hoist;

/**
 * @author Jing
 *
 */
public class MCProtocolTCP extends MCProtocolApp{
    private HoistClient client;
    
    private MCProtocolTCP(){}
    public MCProtocolTCP(String ipAddress,int port){
        this();
        client = HoistClient.getInstance(ipAddress, port);
        client.start();
    }
    
    public void close(){
        client.close();
    }
    
    @Override
    public byte[] execute(String iCommand) {
        return MCUtils.hexStr2Bytes(client.sendData(iCommand));
//        return (client.sendData(iCommand)).getBytes();

//        String data = "1234";
//        StringBuilder ret = new StringBuilder();
//        ret.append(MCUtils.toHexFixLengthString(0xd000, 4));
//        ret.append(MCUtils.toHexFixLengthString(0x00, 2));
//        ret.append(MCUtils.toHexFixLengthString(0xff, 2));
//        ret.append(MCUtils.toHexFixLengthString(0x03ff, 4));
//        ret.append(MCUtils.toHexFixLengthString(0x00, 2));
//        ret.append(MCUtils.toHexFixLengthString(data.length(), 4));
//        ret.append(MCUtils.toHexFixLengthString(0x0000, 4));
//        ret.append(data);
//        
//        return MCUtils.hexStr2Bytes(ret.toString());
    }
}
