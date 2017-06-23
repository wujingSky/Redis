/**
 * 版权所有(C)，LHWMS项目组，2017，所有权利保留。
 * 
 * 项目名：	netty1
 * 文件名：	MCCommand.java
 * 模块说明：	
 * 修改历史：
 * 2017年5月4日 - Jing - 创建。
 */
package com.hd123.zjlh.wcs.hoist;

import java.util.Arrays;

/**
 * @author Jing
 *
 */
public class MCCommand {
    private int NetwrokNumber = 0x00; // 网络编号
    private int PcNumber = 0xff; // PC编号/PLC编号
    private int IoNumber = 0x03FF; // 请求目标模块IO编号
    private int ChannelNumber = 0x00; // 请求目标模块站编号
    private int CpuTimer = 0x0010; // CPU监视定时器
    private int resultCode = 0xcccc; // 返回代码（如果没有返回，则为0xcccc）
    public byte[] response;

    public String setCommand(int iMainCommand, int iSubCommand, String data) {
        int dataLength = data.length() + 12;

        StringBuilder ret = new StringBuilder();
        ret.append("5000");
        ret.append(MCUtils.toHexFixLengthString(NetwrokNumber, 2));
        ret.append(MCUtils.toHexFixLengthString(PcNumber, 2));
        ret.append(MCUtils.toHexFixLengthString(IoNumber, 4));
        ret.append(MCUtils.toHexFixLengthString(ChannelNumber, 2));
        ret.append(MCUtils.toHexFixLengthString(dataLength, 4));
        ret.append(MCUtils.toHexFixLengthString(CpuTimer, 4));
        ret.append(MCUtils.toHexFixLengthString(iMainCommand, 4));
        ret.append(MCUtils.toHexFixLengthString(iSubCommand, 4));
        ret.append(data);

        System.out.println(ret.toString());
        return ret.toString();
    }

    public int setResponse(byte[] responseBytes, int mcFrame) {
        if (mcFrame == MCConst.ASCII_FLAG)
            return setResponse(String.valueOf(responseBytes));

        int min = 11;
        if (min <= responseBytes.length) {
            byte[] btCount = new byte[] {
                    responseBytes[min - 3], responseBytes[min - 4] };
            byte[] btCode = new byte[] {
                    responseBytes[min - 1], responseBytes[min - 2] };
            int rsCount = Integer.valueOf(MCUtils.ToUInt16(btCount));
            if (mcFrame == MCConst.ASCII_FLAG)
                rsCount = rsCount / 2;

            resultCode = Integer.valueOf(MCUtils.ToUInt16(btCode));
            response = new byte[rsCount - 2];
            response = Arrays.copyOfRange(responseBytes, min, min + response.length);
        }
        return resultCode;
    }

    public int setResponse(String responseText) {
        byte[] buffer = new byte[responseText.length() / 2];
        for (int i = 0; i < buffer.length; i++) {
            String str = responseText.substring(i * 2, (i * 2) + 2);
            String hexStr = Integer.toHexString(Integer.valueOf(str));
            buffer[i] = Byte.valueOf(hexStr);
        }
        return setResponse(buffer, MCConst.MC3E);
    }

    public boolean isIncorrectResponse(byte[] iResponse) {
        if (iResponse == null || iResponse.length == 0)
            return false;

        int min = 11;
        if (iResponse.length >= min) {
            byte[] btCount = new byte[] {
                    iResponse[min - 3], iResponse[min - 4] };
            byte[] btCode = new byte[] {
                    iResponse[min - 1], iResponse[min - 2] };
            int rsCount = Integer.valueOf(MCUtils.ToUInt16(btCount)) - 2;
            int rsCode = Integer.valueOf(MCUtils.ToUInt16(btCode));
            return (rsCode == 0 && rsCount != (iResponse.length - min));
        }
        return false;
    }
}
