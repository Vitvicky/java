import java.awt.*;

import javax.swing.*;
public class me extends JFrame{
	JPanel j=new JPanel();
	JLabel j1=new JLabel();
	JLabel j2=new JLabel("作者:DepthCharge");
	JLabel j3=new JLabel("邮箱:wssg1992@163.com");
	JLabel j4=new JLabel("QQ:717185143");
	public me(){
		setTitle("关于我");
		setPreferredSize(new Dimension(340, 450));
		setBounds(100, 100, 200, 300);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		pack();
		add(j);
		setLocation(
				this.getToolkit().getScreenSize().width/2-this.getWidth()/2,
				this.getToolkit().getScreenSize().height/2-this.getHeight()/2);
		j.setLayout(null);
		j.setBackground(Color.YELLOW);
		ImageIcon t = new ImageIcon("DepthCharge.jpg");
		j1.setIcon(t);
		j1.setBounds(10,10,300,300);
		j.add(j1);
		j2.setBounds(10,340,120,25);
		j.add(j2);
		j3.setBounds(140,340,160,25);
		j.add(j3);
		j4.setBounds(90,380,100,25);
		j.add(j4);
		
	}
	
	public static void main(String[] args) {
		me q=new me();

	}

}
