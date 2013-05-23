
import java.awt.*;

import javax.swing.*;
public class kongzhi extends JPanel{
public JButton connectbutton=new JButton("连接主机"),
creatbutton=new JButton("建立游戏"),
joinbutton=new JButton("加入游戏"),
cancelbutton=new JButton("放弃游戏"),
exitbutton=new JButton("关闭程序");


	public kongzhi(){
		setLayout(new FlowLayout());
		//add(connectbutton);
		add(creatbutton);
		add(joinbutton);
		//add(cancelbutton);
		add(exitbutton);
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
