/**
 * 版权所有(C)，LHWMS项目组，2017，所有权利保留。
 * 
 * 项目名：	netty1
 * 文件名：	MCCommandExtersions.java
 * 模块说明：	
 * 修改历史：
 * 2017年5月4日 - Jing - 创建。
 */
package com.hd123.zjlh.wcs.hoist;

/**
 * @author Jing
 *
 */
public class MCCommandExtersions {
   /***
    * 执行命令，并返回执行的结果。读写PLC单元的字符串型命令格式如下：
    * excute("D4001"); 读单一寄存器值
    * excute("D4001,2"); 读连续多个寄存器值
    * excute("D4005=01"); 为单一寄存器赋值
    * excute("D126,6=0"); 为连续寄存器赋值
    */
   public static MCResponseMessage excute(IMC plc, String cmdText) throws Exception {
        boolean isMulti = cmdText.contains(",");
        boolean isWrite = cmdText.contains("=");

        if (isWrite) {
            if (isMulti)
                return writeMulti(plc, cmdText);
            else
                return writeSingle(plc, cmdText);
        } else {
            if (isMulti)
                return readMulti(plc, cmdText);
            else
                return readSingle(plc, cmdText);
        }
    }

    private static MCResponseMessage readSingle(IMC plc, String cmdText) throws Exception {
        MCResponseMessage resp = plc.getByRegisterName(cmdText.toUpperCase());
        
        System.out.println(cmdText.toUpperCase() + "=" + resp.getData());
        if (0 < resp.getResultCode()) {
            System.out.println("ERROR:0x" + Integer.toHexString(resp.getResultCode()));
        }

        return resp;
    }

    private static MCResponseMessage readMulti(IMC plc, String cmdText) throws Exception {
        MCResponseMessage resp = new MCResponseMessage();
        String[] s = cmdText.split(",");
        if (s.length == 2) {
            int n = Integer.valueOf(s[1]);
            byte[] val = new byte[n];
            resp = plc.batchRead(s[0], val.length);
            if (0 < resp.getResultCode()) {
                System.out.println("ERROR:0x" + Integer.toHexString(resp.getResultCode()));
            } else {
                for (int i = 0; i < resp.getData().size(); ++i) {
                    System.out.println(cmdText+"["+i +"]" + "=" + resp.getData().get(i));
                }
            }
        }
        
        return resp;
    }

    private static MCResponseMessage writeSingle(IMC plc, String cmdText) throws Exception {
        String[] s = cmdText.split("=");
        MCResponseMessage resp = plc.setDevice(s[0], Integer.valueOf(s[1]));
        System.out.println(cmdText.toUpperCase());
        if (0 < resp.getResultCode()) {
            System.out.println("ERROR:0x" + Integer.toHexString(resp.getResultCode()));
        }
        return resp;
    }

    private static MCResponseMessage writeMulti(IMC plc, String cmdText) throws Exception {
        StringBuilder result = new StringBuilder();
        String[] s = cmdText.split(",|=");
        int n = Integer.valueOf(s[1]);

        int[] data = new int[n];
        byte v = Byte.valueOf(s[2]);
        for (int i = 0; i < data.length; ++i) {
            data[i] = v;
        }
        MCResponseMessage resp = plc.batchWrite(s[0], data.length, data);
        result.append(cmdText.toUpperCase());
        if (0 < resp.getResultCode()) {
            result.append("ERROR:0x" + Integer.toHexString(resp.getResultCode()));
        }

        return resp;
    }
}
