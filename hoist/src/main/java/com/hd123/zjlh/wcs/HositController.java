/**
 * 版权所有(C)，LHWMS项目组，2017，所有权利保留。
 * 
 * 项目名：	hoist
 * 文件名：	HositController.java
 * 模块说明：	
 * 修改历史：
 * 2017年5月16日 - Jing - 创建。
 */
package com.hd123.zjlh.wcs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hd123.zjlh.wcs.hoist.MCCommandExtersions;
import com.hd123.zjlh.wcs.hoist.MCProtocolTCP;
import com.hd123.zjlh.wcs.hoist.MCResponseMessage;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @author Jing
 *
 */
@RestController
public class HositController {
    protected static Logger logger = LoggerFactory.getLogger(HositController.class);
    
    @ApiOperation(value = "发送指令", notes = "发送提升机指令")
    @ApiImplicitParam(name = "hoistCommand", value = "提升机命令", required = true, dataType = "HoistCommand")
    @RequestMapping(value = "/hoist", method = RequestMethod.POST)
    public @ResponseBody String sendHoistCommand(@RequestBody HoistCommand hoistCommand) {
        String result = null;
        MCProtocolTCP tcp = new MCProtocolTCP(hoistCommand.getIpAddress(),
                Integer.valueOf(hoistCommand.getPort()));
        try {
            MCResponseMessage message = MCCommandExtersions.excute(tcp,
                    hoistCommand.getCommandText());
            result = message.toString();
        } catch (Exception e) {
            result = e.getMessage();
            e.printStackTrace();
        }finally{
            tcp.close();
        }
        return result;
    }
}
