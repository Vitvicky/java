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
		setTitle("����ڰ���---��Ϸ��");
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
		JLabel wanjia1=new JLabel("���һ��");
		wanjia1.setForeground(Color.BLACK);
		wanjia1.setFont(new Font("",Font.BOLD,18));
		wanjia1.setBounds(515,10,120,30);
		son.add(wanjia1);
		JLabel xyy=new JLabel();
		ImageIcon qq=new ImageIcon("wzy.jpg");
		xyy.setIcon(qq);
		xyy.setBounds(515,40,180,180);
		son.add(xyy);
		JLabel wanjia2=new JLabel("��Ҷ�:");
		wanjia2.setForeground(Color.BLACK);
		wanjia2.setFont(new Font("",Font.BOLD,18));
		wanjia2.setBounds(515,250,120,40);
		son.add(wanjia2);
		JLabel htl=new JLabel();
		ImageIcon ee=new ImageIcon("htl.jpg");
		htl.setIcon(ee);
		htl.setBounds(515,295,150,190);
		son.add(htl);
		JButton hq=new JButton("����");
		hq.addActionListener(new huiqilistener());
		JButton cp=new JButton("����");
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
		JButton play=new JButton("����");
		play.addActionListener(m);
		JButton stop=new JButton("ֹͣ");
		stop.addActionListener(m);
		JButton next=new JButton("��һ��");
		JButton before=new JButton("��һ��");
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
		add(tan,BorderLayout.SOUTH);//�Ự�����
		chat=new chatpad(this);
		guanzhan=new JPanel(new GridLayout(3,2));
		tan.add(chat);tan.add(guanzhan);
		JLabel j2=new JLabel();
		ImageIcon q=new ImageIcon("mm.jpg");
		j2.setIcon(q);
		//tan.add(j2);
		// �˵���
		sysmenu = new Menu("ϵͳ");
		Menu enter = new Menu("����");
		MenuItem enter1 = new MenuItem("ע���û�");
		
		enter.add(enter1);

		//enter1.addActionListener(m1);

		menu.add(sysmenu);
		sysmenu.add(enter);
		sysmenu.add(new MenuItem("-"));
		start = new MenuItem("��ʼ");
		exit = new MenuItem("�˳�");
		//back = new MenuItem("����");
		//back.addActionListener(new huiqilistener());
		sysmenu.add(start);
		sysmenu.add(exit);
		//sysmenu.add(back);
		Menu help = new Menu("����");
		//Mylistener m2 = new Mylistener();
		MenuItem about = new MenuItem("������");
		//about.addActionListener(m2);
		MenuItem how = new MenuItem("�����Ϸ");
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
		
		if(e.getActionCommand().equals("����")){
			if(scr.getbai()+scr.gethei()==4) chat.chatarea.append("�տ�ʼ��Ϸ����׼����");
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
			if (e.getActionCommand().equals("ע���û�"))
				//b = new registerWindows();
			if (e.getActionCommand().equals("�����Ϸ"))
				//c = new help();
			if (e.getActionCommand().equals("������"))
				//d = new me();
			if (e.getActionCommand().equals("����")) {
				WelcomeWindow.l.play();
			}
			if (e.getActionCommand().equals("ֹͣ")) {
				WelcomeWindow.l.stop();
			}

		}

		private Container getContentPane() {
			// TODO Auto-generated method stub
			return null;
		}
	}

	
	






