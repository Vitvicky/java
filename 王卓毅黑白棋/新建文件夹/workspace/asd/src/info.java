import javax.swing.*;
import java.awt.*;

public class info extends JPanel{
JLabel tupian=new JLabel();
JLabel xingming,dengji,ying,shu,shenglv;
JPanel x,y,z;
	public info(){
		xingming=new JLabel("�û�����");
		//dengji=new JLabel("�ȼ���");
		ying=new JLabel("Ӯ��");
		shu=new JLabel("�䣺");
		//shenglv=new JLabel("ʤ�ʣ�");
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
