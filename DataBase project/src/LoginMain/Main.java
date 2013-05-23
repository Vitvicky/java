package LoginMain;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.border.*;

import Check.CheckCertain;
import Choice.StudentChoice;
import Delete.DeleteCertain;
import Delete.StudentDelete;
import Register.RegisterCertain;
import Update.UpdateCertain;
public class Main extends JFrame {
	WelcomeWindow a;
	
	public JPanel guanzhan;
	Container cot=new JPanel(new BorderLayout()), 
	tan=new JPanel(new GridLayout(1,2)),
	son=new JPanel(new BorderLayout()),
	con=getContentPane();

	JLabel gif=new JLabel();
	public Main(){
		super();
		setTitle("教学管理系统客户端");
		//setSize(getToolkit().getScreenSize().width,getToolkit().getScreenSize().height);
		setBounds(0,0,900,600);
		add(son,BorderLayout.CENTER);
		//son.add(v,BorderLayout.CENTER);
		son.setLayout(null);
		
		
		ImageIcon i1=new ImageIcon("image/高达.jpg");
		gif.setIcon(i1);
		gif.setBounds(20,20,500,450);
		son.add(gif);
		JLabel xyy=new JLabel();
		ImageIcon qq=new ImageIcon("wzy.jpg");
		xyy.setIcon(qq);
		xyy.setBounds(515,40,180,180);
		son.add(xyy);
		
		JLabel htl=new JLabel();
		ImageIcon ee=new ImageIcon("htl.jpg");
		htl.setIcon(ee);
		htl.setBounds(515,295,150,190);
		son.add(htl);
		//JLabel mp4=new JLabel();
		//ImageIcon xx=new ImageIcon("image/play.gif");
		//mp4.setIcon(xx);
		//mp4.setBounds(700,200,300,150);
		//son.add(mp4);
		
		//Mylistener m=new Mylistener();
		listener l=new listener();
		//JButton play=new JButton("播放");
		//play.addActionListener(m);
		//JButton stop=new JButton("停止");
		//stop.addActionListener(m);
		//JButton next=new JButton("下一曲");
		//JButton before=new JButton("上一曲");
		//play.setBounds(650,370,120,30);
		//stop.setBounds(800,370,120,30);
		//next.setBounds(650,420,120,30);
		//before.setBounds(800,420,120,30);
		//son.add(play);
		//son.add(stop);
		//son.add(next);
		//son.add(before);
		JButton m1=new JButton("查看列表");
		m1.setBorder(BorderFactory.createEmptyBorder());
		m1.addActionListener(l);
		m1.setBounds(540,20,150,30);
		son.add(m1);
		JButton m2=new JButton("新信息录入");
		m2.setBorder(BorderFactory.createEmptyBorder());
		m2.addActionListener(l);
		m2.setBounds(540,60,150,30);
		son.add(m2);
		JButton m3=new JButton("查找信息");
		m3.setBorder(BorderFactory.createEmptyBorder());
		m3.addActionListener(l);
		m3.setBounds(710,20,150,30);
		son.add(m3);
		JButton m4=new JButton("删除记录");
		m4.setBorder(BorderFactory.createEmptyBorder());
		m4.addActionListener(l);
		m4.setBounds(710,60,150,30);
		son.add(m4);
		JButton m5=new JButton("更新记录");
		m5.setBorder(BorderFactory.createEmptyBorder());
		m5.addActionListener(l);
		m5.setBounds(540,100,150,30);
		son.add(m5);
		JButton m6=new JButton("学生选课");
		m6.setBorder(BorderFactory.createEmptyBorder());
		m6.addActionListener(l);
		m6.setBounds(710,100,150,30);
		son.add(m6);
		JLabel j1=new JLabel();
		ImageIcon qwe=new ImageIcon("image/高达2.jpg");
		j1.setIcon(qwe);
		j1.setBounds(0,0,950,550);
		son.add(j1);
		//cot.add(scr,BorderLayout.CENTER);
		//contr=new control();
		//cot.add(contr,BorderLayout.NORTH);
		add(tan,BorderLayout.SOUTH);//会话框面板
		
		guanzhan=new JPanel(new GridLayout(3,2));
		
		JLabel j2=new JLabel();
		ImageIcon q=new ImageIcon("image/mm.jpg");
		j2.setIcon(q);
		//tan.add(j2);
		
		
		setVisible(true);                              
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		validate();
	}
	class listener implements ActionListener{
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("查看列表")){
			System.out.println("1");
			View v=new View();
			gif.add(v);
			
		}
		if(e.getActionCommand().equals("新信息录入")){
			new RegisterCertain();
		}
		if(e.getActionCommand().equals("查找信息")){
			new CheckCertain();
		}
		if(e.getActionCommand().equals("删除记录")){
			new DeleteCertain();
		}
		if(e.getActionCommand().equals("更新记录")){
			new UpdateCertain();
		}
		if(e.getActionCommand().equals("学生选课")){
			new StudentChoice();
		}
	}}
	class Mylistener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			if (e.getActionCommand().equals("播放")) {
				
				
				WelcomeWindow.l.play();
			}
			if (e.getActionCommand().equals("停止")) {
				WelcomeWindow.l.stop();
			}

		}

		private Container getContentPane() {
			// TODO Auto-generated method stub
			return null;
		}
	}
public static void main(String []args){
	new Main();
}
}
	






