import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.swing.border.*;


public class gameduan extends JFrame {
	WelcomeWindow a;
	//biaoti biaotilan;
	//public playinfo wanjia1,wanjia2;
	public qipanjiemian scr;
	public chatpad chat;
	public JPanel guanzhan;
	private MenuBar menu = new MenuBar();
	private Menu sysmenu;
	private MenuItem start;
	private MenuItem exit;
	//private MenuItem back;
	//public control contr;
	public kehuduan padpad;
	
	public gameduan(kehuduan padpad) throws Exception{
		super();
		this.padpad=padpad;
		setTitle("网络黑白棋---游戏端");
		setSize(getToolkit().getScreenSize().width-100,getToolkit().getScreenSize().height-25);
		//biaotilan=new biaoti();
		//wanjia1=new playinfo();wanjia2=new playinfo();
		scr=new qipanjiemian(this);
		Container cot=new JPanel(new BorderLayout()), 
		tan=new JPanel(new GridLayout(1,2)),
		son=new JPanel(new BorderLayout()),
		con=getContentPane();
		con.setLayout(new BorderLayout());
		//add(biaotilan,BorderLayout.NORTH);
		//add(wanjia1,BorderLayout.WEST);
		//add(wanjia2,BorderLayout.EAST);
		add(son,BorderLayout.CENTER);
		son.add(scr,BorderLayout.CENTER);
		son.setLayout(null);
		JLabel wanjia1=new JLabel("玩家一：");
		wanjia1.setForeground(Color.BLACK);
		wanjia1.setFont(new Font("",Font.BOLD,18));
		wanjia1.setBounds(515,10,120,30);
		son.add(wanjia1);
		JLabel xyy=new JLabel();
		ImageIcon qq=new ImageIcon("wzy.jpg");
		xyy.setIcon(qq);
		xyy.setBounds(515,40,180,180);
		son.add(xyy);
		JLabel wanjia2=new JLabel("玩家二:");
		wanjia2.setForeground(Color.BLACK);
		wanjia2.setFont(new Font("",Font.BOLD,18));
		wanjia2.setBounds(515,250,120,40);
		son.add(wanjia2);
		JLabel htl=new JLabel();
		ImageIcon ee=new ImageIcon("htl.jpg");
		htl.setIcon(ee);
		htl.setBounds(515,295,150,190);
		son.add(htl);
		JButton hq=new JButton("悔棋");
		hq.addActionListener(new huiqilistener());
		JButton cp=new JButton("存盘");
		hq.setBounds(30,520,120,40);
		cp.setBounds(240,520,120,40);
		son.add(hq);
		son.add(cp);
		JLabel mp3=new JLabel();
		ImageIcon qa=new ImageIcon("mp3.jpg");
		mp3.setIcon(qa);
		mp3.setBounds(800,10,300,225);
		son.add(mp3);
		JLabel mp4=new JLabel();
		ImageIcon xx=new ImageIcon("play.gif");
		mp4.setIcon(xx);
		mp4.setBounds(800,250,250,100);
		son.add(mp4);
		Mylistener m=new Mylistener();
		JButton play=new JButton("播放");
		play.addActionListener(m);
		JButton stop=new JButton("停止");
		stop.addActionListener(m);
		JButton next=new JButton("下一曲");
		JButton before=new JButton("上一曲");
		play.setBounds(830,370,120,30);
		stop.setBounds(970,370,120,30);
		next.setBounds(830,410,120,30);
		before.setBounds(970,410,120,30);
		son.add(play);
		son.add(stop);
		son.add(next);
		son.add(before);
		
		JLabel j1=new JLabel();
		ImageIcon qwe=new ImageIcon("meijing3.jpg");
		j1.setIcon(qwe);
		j1.setBounds(0,0,1280,800);
		son.add(j1);
		//cot.add(scr,BorderLayout.CENTER);
		//contr=new control();
		//cot.add(contr,BorderLayout.NORTH);
		add(tan,BorderLayout.SOUTH);//会话框面板
		chat=new chatpad(this);
		guanzhan=new JPanel(new GridLayout(3,2));
		tan.add(chat);tan.add(guanzhan);
		JLabel j2=new JLabel();
		ImageIcon q=new ImageIcon("mm.jpg");
		j2.setIcon(q);
		//tan.add(j2);
		// 菜单栏
		sysmenu = new Menu("系统");
		Menu enter = new Menu("音乐");
		MenuItem enter1 = new MenuItem("注册用户");
		
		enter.add(enter1);

		//enter1.addActionListener(m1);

		menu.add(sysmenu);
		sysmenu.add(enter);
		sysmenu.add(new MenuItem("-"));
		start = new MenuItem("开始");
		exit = new MenuItem("退出");
		//back = new MenuItem("悔棋");
		//back.addActionListener(new huiqilistener());
		sysmenu.add(start);
		sysmenu.add(exit);
		//sysmenu.add(back);
		Menu help = new Menu("帮助");
		//Mylistener m2 = new Mylistener();
		MenuItem about = new MenuItem("关于我");
		//about.addActionListener(m2);
		MenuItem how = new MenuItem("如何游戏");
		//Mylistener m3 = new Mylistener();
		//how.addActionListener(m3);
		help.add(about);
		help.add(how);
		setMenuBar(menu);
		menu.setHelpMenu(help);
		setVisible(true);                              
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		validate();
	}
	class huiqilistener implements ActionListener{
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("悔棋")){
			if(scr.getbai()+scr.gethei()==4) chat.chatarea.append("刚开始游戏，不准悔棋");
			else {padpad.sendMessage("/huiqi "+padpad.chessPeerName);
			for(int t=0;t<8;t++) for(int s=0;s<8;s++) scr.qi[t][s]=scr.x[t][s];scr.repaint();
		}}
		
			else if(!padpad.isMouseEnabled)  {
				padpad.sendMessage("/tuichu "+padpad.chessClientName);
			}
			padpad.isClient=padpad.isServer=false;
			//wanjia1.xingming.setText("");
			//wanjia1.dengji.setText("");
			//this.setVisible(false);
			padpad.setVisible(true);
		}

	
	}}
	class Mylistener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Container contentPane = getContentPane();
			if (e.getActionCommand().equals("注册用户"))
				//b = new registerWindows();
			if (e.getActionCommand().equals("如何游戏"))
				//c = new help();
			if (e.getActionCommand().equals("关于我"))
				//d = new me();
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

	
	






