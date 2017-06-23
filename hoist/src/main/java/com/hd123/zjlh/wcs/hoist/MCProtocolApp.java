/**
 * 版权所有(C)，LHWMS项目组，2017，所有权利保留。
 * 
 * 项目名：	netty1
 * 文件名：	MCProtocolApp.java
 * 模块说明：	
 * 修改历史：
 * 2017年5月5日 - Jing - 创建。
 */
package com.hd123.zjlh.wcs.hoist;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jing
 *
 */
public abstract class MCProtocolApp implements IMC {
    public int commandFrame = MCConst.MC3E_ASCII;
    private static String PLCDeviceType = "D*";
    private MCCommand command = new MCCommand();

    public abstract byte[] execute(String iCommand);

    public MCResponseMessage batchWrite(String deviceName, int iSize, int[] iData) throws Exception {
        StringBuilder data = new StringBuilder(PLCDeviceType);
        data.append(MCUtils.toHexFixLengthString(getAddressByRegister(deviceName), 6));
        data.append(MCUtils.toHexFixLengthString(iSize, 4));
        for (int i = 0; i < iData.length; i++) {
            data.append(MCUtils.toHexFixLengthString(iData[i], 4));
        }

        String sdCommand = command.setCommand(0x1401, 0x0000, data.toString());
        byte[] rtResponse = tryExecution(sdCommand);
        int rtCode = command.setResponse(rtResponse, commandFrame);
        
        int oData = 0;
        if (rtCode <= 0) {
            byte[] rtData = command.response;
            oData = Integer.valueOf(MCUtils.ToUInt16(rtData));
        }
        MCResponseMessage result = new MCResponseMessage();
        result.setResultCode(rtCode);
        result.assginSingleData(String.valueOf(oData));
        return result;
    }

    public MCResponseMessage batchRead(String deviceName, int iSize) throws Exception {
        StringBuilder data = new StringBuilder(PLCDeviceType);
        data.append(MCUtils.toHexFixLengthString(getAddressByRegister(deviceName), 6));
        data.append(MCUtils.toHexFixLengthString(iSize, 4));
        String sdCommand = command.setCommand(0x0401, 0x0000, data.toString());
        byte[] rtResponse = tryExecution(sdCommand);
        int rtCode = command.setResponse(rtResponse, commandFrame);
        List<String> datas = new ArrayList<String>();

        byte[] rtData = command.response;
        for (int i = 0; i < iSize; i++) {            
            if (rtCode > 0) {
                datas.add(String.valueOf(0));
                continue;
            }
//            byte[] btCount = new byte[] {
//                    rtData[i * 2], rtData[i * 2 + 1] };
            byte[] btCount = new byte[] {rtData[i] };
            datas.add(MCUtils.ToUInt16(btCount));
        }

        MCResponseMessage result = new MCResponseMessage();
        result.setResultCode(rtCode);
        result.setData(datas);
        return result;
    }

    public MCResponseMessage setDevice(String deviceName, int iData) throws Exception {
        StringBuilder data = new StringBuilder(PLCDeviceType);
        data.append(MCUtils.toHexFixLengthString(getAddressByRegister(deviceName), 6));
        data.append(MCUtils.toHexFixLengthString(1, 4));
        data.append(MCUtils.toHexFixLengthString(iData, 4));
        String sdCommand = command.setCommand(MCConst.WRITE, MCConst.SUBCOMMAND, data.toString());
        byte[] rtResponse = tryExecution(sdCommand);
        int rtCode = command.setResponse(rtResponse, commandFrame);

        MCResponseMessage result = new MCResponseMessage();
        result.setResultCode(rtCode);
        result.assginSingleData(String.valueOf(rtResponse));
        return result;
    }

    public MCResponseMessage getByRegisterName(String deviceName) throws Exception {
        StringBuilder data = new StringBuilder(PLCDeviceType);
        data.append(MCUtils.toHexFixLengthString(getAddressByRegister(deviceName), 6));
        data.append(MCUtils.toHexFixLengthString(1, 4));
        String sdCommand = command.setCommand(MCConst.READ, MCConst.SUBCOMMAND, data.toString());
        byte[] rtResponse = tryExecution(sdCommand);
        int rtCode = command.setResponse(rtResponse, commandFrame);
        int oData = 0;
        if (rtCode <= 0) {
            byte[] rtData = command.response;
            oData = Integer.valueOf(MCUtils.ToUInt16(rtData));
        }

        MCResponseMessage result = new MCResponseMessage();
        result.assginSingleData(String.valueOf(oData));
        result.setResultCode(rtCode);
        return result;
    }

    private int getAddressByRegister(String registerName) {
        return Integer.valueOf(registerName.substring(1, registerName.length()));
    }

    private byte[] tryExecution(String iCommand) throws Exception {
        byte[] rtResponse;
        int tCount = 10;
        do {
            rtResponse = execute(iCommand);
            --tCount;
            if (tCount < 0) {
                throw new Exception("PLC超时无响应.");
            }
        } while (command.isIncorrectResponse(rtResponse));
        return rtResponse;
    }
}