

import java.awt.*;

/**
 * 用户列表Panel。此Panel维护一个服务器的当前用户列表， 所有的用户名都将显示在列表中。
 */
public class userpad extends Panel {
    //用户列表
    public List userList = new List(10);List userList1 = new List(10);
    public userpad() {
        //设置布局管理器并添加信息
        setLayout(new GridLayout(2,1));
        //for (int i = 0; i < 30; i++) 
        {
            userList.add("当前暂无用户");
            userList1.add("当前暂无用户");
        }
        //添加到Panel
        add(userList);//, BorderLayout.CENTER);
        add(userList1);
    }
}
