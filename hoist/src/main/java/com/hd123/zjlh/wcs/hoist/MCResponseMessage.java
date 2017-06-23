/**
 * 版权所有(C)，LHWMS项目组，2017，所有权利保留。
 * 
 * 项目名：	netty1
 * 文件名：	MCResponseMessage.java
 * 模块说明：	
 * 修改历史：
 * 2017年5月4日 - Jing - 创建。
 */
package com.hd123.zjlh.wcs.hoist;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jing
 *
 */
public class MCResponseMessage {
    public int resultCode;
    public List<String> data = new ArrayList<String>();

    public MCResponseMessage() {
        resultCode = MCConst.UNKNOWN;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public void assginSingleData(String str) {
        if (data == null)
            data = new ArrayList<String>();

        data.add(str);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String str : data) {
            sb.append(str + " ");
        }
        return sb.toString();
    }
}
