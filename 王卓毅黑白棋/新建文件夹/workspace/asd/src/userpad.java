

import java.awt.*;

/**
 * �û��б�Panel����Panelά��һ���������ĵ�ǰ�û��б� ���е��û���������ʾ���б��С�
 */
public class userpad extends Panel {
    //�û��б�
    public List userList = new List(10);List userList1 = new List(10);
    public userpad() {
        //���ò��ֹ������������Ϣ
        setLayout(new GridLayout(2,1));
        //for (int i = 0; i < 30; i++) 
        {
            userList.add("��ǰ�����û�");
            userList1.add("��ǰ�����û�");
        }
        //��ӵ�Panel
        add(userList);//, BorderLayout.CENTER);
        add(userList1);
    }
}
