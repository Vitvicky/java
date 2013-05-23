import javax.swing.*;
import java.awt.*;

public class info extends JPanel{
JLabel tupian=new JLabel();
JLabel xingming,dengji,ying,shu,shenglv;
JPanel x,y,z;
	public info(){
		xingming=new JLabel("用户名：");
		//dengji=new JLabel("等级：");
		ying=new JLabel("赢：");
		shu=new JLabel("输：");
		//shenglv=new JLabel("胜率：");
		x=new JPanel();
		y=new JPanel(new GridLayout(3,1));
		//z=new JPanel();
		ImageIcon q=new ImageIcon("yyy.jpg");
		tupian.setIcon(q);
		x.add(tupian);
		y.add(xingming);
		//y.add(dengji);
		y.add(ying);
		y.add(shu);
		//y.add(shenglv);
		setLayout(new GridLayout(2,1));
		add(x);add(y);
		//add(z);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
