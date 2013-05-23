
import java.applet.AudioClip;
import java.awt.*;

import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

import javax.swing.*;



//import chatdengluqi.actionAdapter;

public class dengluqi extends JFrame implements ActionListener,TextListener{
	public static final JLabel name= new JLabel("用户名:"); 
	public static final JLabel mima= new JLabel("密码:"); 
	JLabel tishi=new JLabel("");
	JButton zhuce=new JButton("还没注册？点击这里吧");
	registerWindows b;
	JLabel picture = new JLabel();//贴入图片
	TextField zhanghao1=new TextField();//用户名输入
	JPasswordField mima1=new JPasswordField();//密码输入框
	static AudioClip l;
public  Socket socket;
public  DataInputStream inda;
public DataOutputStream outda;
public String host;
public int port;
	//JLabel banquan=new JLabel("All Rights Reserved");
	
	public kehuduan userpad;
	public boolean connectserver(String ip,int por)throws Exception{
		try{
			//socket=new Socket("121.250.221.172",3333);
			socket=new Socket("127.0.0.1",3333);
			inda=new DataInputStream(socket.getInputStream());
			outda=new DataOutputStream(socket.getOutputStream());
			
			return true;
		}catch(IOException ex){
			tishi.setText("连接错误！");
		}return false;
	}
	public dengluqi(){
		super();
		setTitle("登陆界面");
		setPreferredSize(new Dimension(350, 330));
		setBounds(100, 100, 200, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		pack();
		setLocation(
				this.getToolkit().getScreenSize().width/2-this.getWidth()/2,
				this.getToolkit().getScreenSize().height/2-this.getHeight()/2);
		JPanel a = new JPanel();
	    a.setOpaque(true);
		a.setLayout(null);
		//提示框
		tishi.setBounds(70,230,200,30);
		a.add(tishi);
		Mylistener qq=new Mylistener();
		zhuce.setBounds(0,260,350,30);
		a.add(zhuce);
		zhuce.addActionListener(qq);
		ImageIcon t = new ImageIcon("动感黑白棋.jpg");
		picture.setIcon(t);
		
		picture.setBounds(0, 0, 350, 100);
		 add(a);
		 //用户名的输入
		 name.setForeground(Color.BLACK);
		 name.setFont(new Font("",Font.BOLD,15));
		 name.setBounds(10, 120, 60, 25);
		 a.add(name);
		 a.add(picture);
		 zhanghao1.setBounds(100, 120, 160, 22);
		 a.add(zhanghao1);
		 //密码的输入
			mima.setForeground(Color.BLACK);
			mima.setFont(new Font("",Font.BOLD,15));
			mima.setBounds(10, 150, 70, 25);
			a.add(mima);
			mima1.setBounds(100, 150, 140, 22);
			a.add(mima1);
			final JButton a1=new JButton("确定");//确定按钮
			a1.setBounds(70,200,70,22);
			//a1.addActionListener(new actionAdapter());
			a.add(a1);
			JButton a2=new JButton("取消");
			//a2.addActionListener(new actionAdapter());
			a2.setBounds(180,200,70,22);
			a.add(a2);
			JLabel c=new JLabel();
			ImageIcon r=new ImageIcon("qqq");
			c.setIcon(r);
			c.setBounds(0,0,350,350);
			a.add(c);
	    Font z=new Font("黑体",Font.BOLD,35);
	    Font x1=new Font("",Font.BOLD,16);
	    Font x=new Font("黑体",Font.ITALIC+Font.BOLD,25);
	    Font y=new Font("",Font.BOLD,18);
	    Font y1=new Font("黑体",Font.BOLD,21);
	    
	    
		
		

		
		
	   
		//add(zhanghao1);zhanghao1.setBounds(590,110,150,25);
		zhanghao1.addTextListener(this);zhanghao1.setFont(y1);
		//add(mima);mima.setBounds(525,145,60,25);mima.setFont(y);
		//add(mima1);mima1.setBounds(590,145,150,25);
		//mima1.addTextListener(this);mima1.setFont(y1);
	
	
	
	a1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			String x=new String(mima1.getPassword());
			if(zhanghao1.getText().trim().equals("")||x.equals(""))
			{tishi.setText("账号或密码错误，请重新登录！");return;}
		try {
			if(connectserver("127.0.0.1",3333))
			{ outda.writeUTF("/yanzheng"+" " +zhanghao1.getText()+" "+x);//writeUTF();
				a1.setEnabled(false);	
				int t=inda.read();
				if(t==1)
				{	dispose();
					userpad=new kehuduan(socket,inda,outda);
					
					
					
					String str=inda.readUTF();
				  	 StringTokenizer usertaken = new StringTokenizer(str);
	               	 String xx=usertaken.nextToken(),y=usertaken.nextToken();
				userpad.user.xingming.setText(userpad.user.xingming.getText().trim()+zhanghao1.getText());
				userpad.user.ying.setText(userpad.user.ying.getText().trim()+xx);
				userpad.user.shu.setText(userpad.user.shu.getText().trim()+y);
				userpad.user.shenglv.setText(userpad.user.shenglv.getText().trim()+((double)Integer.parseInt(xx))/Integer.parseInt(y));
				userpad.setVisible(true);
				}else if(t==0){
					a1.setEnabled(true);tishi.setText("账号或密码错误，请重新登录！");
					inda.close();outda.close();socket.close();}}
			else{
				tishi.setText("登陆超时！请检查网络连接。");
			}}
		catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	});
				
	
	}
	public void actionPerformed(ActionEvent e){
		
	}
		
	public void textValueChanged(TextEvent e){
		
	}
	class Mylistener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("还没注册？点击这里吧"))
				b = new registerWindows();
		}
	}
	

	
	/*public static void main(String[] args) throws MalformedURLException {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		try  {
			SubstanceImageWatermark watermark  =   new  SubstanceImageWatermark(othello. class .getResourceAsStream( "111.jpg " ));
			//UIManager.setLookAndFeel( new SubstanceOfficeBlue2007LookAndFeel()); 
			UIManager.setLookAndFeel( new SubstanceGreenMagicLookAndFeel()); 
	       
	    }  catch  (UnsupportedLookAndFeelException ex) {
	       
	    } 
         new othello(); 
         {
     		File music= new File("E:\\wzy java programe\\wzy\\夜的钢琴曲 - 五.wav"); 
     		URL url=music.toURL();
     		l = java.applet.Applet.newAudioClip(url);
     		l.loop();
     	}
	}*/

}
//SubstanceAutumnLookAndFeel
//SubstanceEmeraldDuskLookAndFeel
//SubstanceFieldOfWheatLookAndFeel
//SubstanceGreenMagicLookAndFeel
//SubstanceMangoLookAndFeel
