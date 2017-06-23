/**
 * 版权所有(C)，LHWMS项目组，2017，所有权利保留。
 * 
 * 项目名：	hoist
 * 文件名：	HoistCommand.java
 * 模块说明：	
 * 修改历史：
 * 2017年5月16日 - Jing - 创建。
 */
package com.hd123.zjlh.wcs;

/**
 * @author Jing
 *
 */
public class HoistCommand {
    private String ipAddress;
    private String port;
    private String commandText;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getCommandText() {
        return commandText;
    }

    public void setCommandText(String commandText) {
        this.commandText = commandText;
    }
}
