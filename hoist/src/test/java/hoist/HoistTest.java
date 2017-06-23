/**
 * 版权所有(C)，LHWMS项目组，2017，所有权利保留。
 * 
 * 项目名：	hoist
 * 文件名：	HoistTest.java
 * 模块说明：	
 * 修改历史：
 * 2017年6月22日 - Jing - 创建。
 */
package hoist;

import com.hd123.zjlh.wcs.hoist.MCCommandExtersions;
import com.hd123.zjlh.wcs.hoist.MCProtocolTCP;
import com.hd123.zjlh.wcs.hoist.MCResponseMessage;

/**
 * @author Jing
 *
 */
public class HoistTest {
    
    public static void main(String[] args) throws Exception {
        MCProtocolTCP tcp = new MCProtocolTCP("127.0.0.1",8888);
//        MCResponseMessage message = MCCommandExtersions.excute(tcp,
//                "D126,2"); // d00000ff03ff00000400001234123
        
        MCResponseMessage message = MCCommandExtersions.excute(tcp,
                "D100,2");
        System.out.println(message.getResultCode());
    }
}
