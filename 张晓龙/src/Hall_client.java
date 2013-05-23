import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.swing.*;



public class Hall_client extends JFrame{
	Socket s = null;
	DataOutputStream dos = null;
	DataInputStream dis = null;
	private boolean bConnected = false;
	private TextField yonghu = new TextField();
	private JPasswordField mima = new JPasswordField();
	String yonghuming;
	String mimakuang;
	Hall hall = null;
	String yonghumingzu="\n";
	int[] members = new int[9];
	
	
	public Hall_client(int port) {
		setTitle("黑白棋游戏大厅登录");
		setSize(300, 200);
		setIconImage(Toolkit.getDefaultToolkit().getImage("map01.jpg"));
		setLocationRelativeTo(null);
		for(int i=0;i<9;i++){
			members[i]=0;
		}
		
		
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(gridbag);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		c.fill=GridBagConstraints.BOTH;
		c.anchor=GridBagConstraints.CENTER;
		Container container = getContentPane();
		
		addComp(new JLabel("用户名： "),container,c,0,0,1,1,0,0);
		addComp(yonghu,container,c,0,1,1,2,5,0);
		addComp(new JLabel("密  码： "),container,c,1,0,1,1,0,0);
		addComp(mima,container,c,1,1,1,2,5,0);
		
		JButton denglubt = new JButton("登  录");
		denglubt.addActionListener(new Denglu());
		addComp(denglubt,container,c,2,1,1,1,1,0);
		
		JButton zhucebt = new JButton("注  册");
		zhucebt.addActionListener(new Zhuce());
		addComp(zhucebt,container,c,2,2,1,1,1,0);
		
		connect(port);
		new Thread(new RecvThread()).start();
	}
	
	
	public static void main(String[] args){
		Hall_client hall_client = new Hall_client(5000);
		hall_client.setVisible(true);
	}
	
	private void addComp(Component c,Container container,GridBagConstraints gbConstraints,int row,int column,int numberOfRows,int numberOfColumns,double weightx,double weighty){
		gbConstraints.gridx=column;
		gbConstraints.gridy=row;
		gbConstraints.gridwidth=numberOfColumns;
		gbConstraints.gridheight=numberOfRows;
		gbConstraints.weightx=weightx;
		gbConstraints.weighty=weighty;
		
		container.add(c,gbConstraints);
	}
	
	public void connect(int port) {
		try {
			s = new Socket("127.0.0.1", port);
			dos = new DataOutputStream(s.getOutputStream());
			dis = new DataInputStream(s.getInputStream());
			bConnected = true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (ConnectException e) {
			JOptionPane.showMessageDialog(null, "无法连接到服务器，请稍后再试");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	private class Zhuce implements ActionListener{
		public void actionPerformed(ActionEvent e){
			new Zhucechuangkou();
		}
	}
	
	private class Denglu implements ActionListener{
		public void actionPerformed(ActionEvent e){
			yonghuming=yonghu.getText().trim().toString();
			mimakuang=mima.getText().trim().toString();
			if(mimakuang.equals("")){
				JOptionPane.showMessageDialog(null, "密码不能为空");
			}else{
				try {
					dos.writeUTF("\t"+yonghuming+"\t"+mimakuang);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	public void trans(String str){
		str = str.substring(2,str.length());
		int a = str.indexOf("\t");
		
		for(int i=0;i<9;i++){
			members[i]=Integer.parseInt(str.substring(0,a));
			str=str.substring(a+1,str.length());
			a = str.indexOf("\t");
		}
		try{
			str=str.substring(1,str.length());
			a = str.indexOf("\t");
			String temp="";
			while(a!=-1){
				temp=temp+(str.substring(0,a))+"<br>";
				str=str.substring(a+1,str.length());
				a = str.indexOf("\t");
			}
			yonghumingzu=temp;
		}catch(StringIndexOutOfBoundsException e){
		}
	}
	
	private class Zhucechuangkou extends JFrame{
		TextField yonghuzhuce = new TextField();
		JPasswordField mimazhuce = new JPasswordField();
		JPasswordField mimazhuce2 = new JPasswordField();
		String yonghum;
		String mim1;
		String mim2;
		
		Zhucechuangkou(){
			setSize(300, 200);
			setLocationRelativeTo(null);
			GridBagLayout gridbag = new GridBagLayout();
			GridBagConstraints c = new GridBagConstraints();
			setLayout(gridbag);
			
			c.fill=GridBagConstraints.BOTH;
			c.anchor=GridBagConstraints.CENTER;
			Container container = getContentPane();
			
			addComp(yonghuzhuce,container,c,0,2,1,1,5,0);
			addComp(mimazhuce,container,c,1,2,1,1,5,0);
			addComp(mimazhuce2,container,c,2,2,1,1,5,0);
			addComp(new JLabel("用户名："),container,c,0,1,1,1,1,0);
			addComp(new JLabel("密  码："),container,c,1,1,1,1,1,0);
			addComp(new JLabel("确认密码："),container,c,2,1,1,1,1,0);
			
			JButton zhuce = new JButton("注册");
			zhuce.addActionListener(new Zhuce());
			addComp(zhuce,container,c,3,2,1,1,0,0);
			
			setVisible(true);
		}
		
		
		private class Zhuce implements ActionListener{
			public void actionPerformed(ActionEvent e){
				yonghum=yonghuzhuce.getText().trim().toString();
				mim1=mimazhuce.getText().trim().toString();
				mim2=mimazhuce2.getText().trim().toString();
				if(!mim1.equals(mim2)){
					JOptionPane.showMessageDialog(null, "两次输入的密码不同\n请重新输入");
				}
				
				try {
					dos.writeUTF("\tzhuce"+yonghum+"\t"+mim1);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	private class RecvThread implements Runnable {

		public void run() {
			try {
				while (bConnected) {
					String str = dis.readUTF();
					if (str.equals("\tzhuceok")) {
						JOptionPane.showMessageDialog(null, "注册成功\n请登录");
						
					}else{
						if (str.equals("\tdengluok")) {
							JOptionPane.showMessageDialog(null, "登录成功");
							setVisible(false);
							hall = new Hall();
							dos.writeUTF("refresh");
							hall.setVisible(true);
							hall.update.start();
						}else{
							if(str.equals("\tmimacuowu")){
								JOptionPane.showMessageDialog(null, "密码错误\n请重新输入");
							}else{
								if(str.equals("\tyonghumingcuowu")){
									JOptionPane.showMessageDialog(null, "用户名不存在");
								}else{
									if(str.equals("\tyonghumingcunzai")){
										JOptionPane.showMessageDialog(null, "用户名已存在");
									}else{
										if(str.substring(0,2).equals("\t\t")){
											trans(str);
											hall.drawfangjian();
										}else{
											if(str.equals("\tyidenglu")){
												JOptionPane.showMessageDialog(null, "用户已登陆,请勿二次登陆");
											}else{
												if(str.equals("\tcheck")){
													continue;
												}
											}
										}
									}
								}
							}
						}
					}
				}
			} catch (SocketException e) {
				System.out.println("退出了，bye!");
			} catch (EOFException e) {
				System.out.println("推出了，bye - bye!");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
	
	class Hall extends JFrame{
		Chess_client[] trychess = new Chess_client[9];
		JButton[] fangjian = new JButton[9];
		Thread update = new Thread(new UpdateThread());
		boolean canjoin = true;
		JLabel zaixian = new JLabel();
		
		
		Hall(){
			setTitle("黑白棋游戏大厅");
			setSize(700, 400);
			setIconImage(Toolkit.getDefaultToolkit().getImage("map01.jpg"));
			setLocationRelativeTo(null);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			
			GridBagLayout gridbag = new GridBagLayout();
			GridBagConstraints c = new GridBagConstraints();
			setLayout(gridbag);
			
			c.fill=GridBagConstraints.BOTH;
			c.anchor=GridBagConstraints.CENTER;
			Container container = getContentPane();
			
			JScrollPane scroll = new JScrollPane(zaixian);

			zaixian.setText("在线成员:		");
			addComp(scroll,container,c,0,2,2,2,10,10);
			c.insets = new Insets(10,10,10,10);
			addComp(Fangjianbt(),container,c,0,0,2,2,10,10);
			
		}
		
		public JPanel Fangjianbt(){
			fangjian[0] = new JButton();
			fangjian[0].addActionListener(new Fangjian1());
			fangjian[1] = new JButton();
			fangjian[1].addActionListener(new Fangjian2());
			fangjian[2] = new JButton();
			fangjian[2].addActionListener(new Fangjian3());
			fangjian[3] = new JButton();
			fangjian[3].addActionListener(new Fangjian4());
			fangjian[4] = new JButton();
			fangjian[4].addActionListener(new Fangjian5());
			fangjian[5] = new JButton();
			fangjian[5].addActionListener(new Fangjian6());
			fangjian[6] = new JButton();
			fangjian[6].addActionListener(new Fangjian7());
			fangjian[7] = new JButton();
			fangjian[7].addActionListener(new Fangjian8());
			fangjian[8] = new JButton();
			fangjian[8].addActionListener(new Fangjian9());
			
			for(int i=0;i<9;i++){
				fangjian[i].setBackground(new Color(81,114,159));
			}
			
			JPanel xx=new JPanel();
			xx.setLayout(new GridLayout(3,3,2,2));
			
			for(int i=0;i<9;i++){
				xx.add(fangjian[i]); 
			}
			return xx;
		}
		
		public void drawfangjian(){
			for(int i=0;i<9;i++){
				if(members[i]==0){
					fangjian[i].setIcon(new ImageIcon("fangjian1.gif"));
				}else{
					if(members[i]==1){
						fangjian[i].setIcon(new ImageIcon("fangjian2.gif"));
					}else{
						fangjian[i].setIcon(new ImageIcon("fangjian3.gif"));
					}
				}
			}
			zaixian.setText("<html>在线成员:		<br>"+yonghumingzu+"</html>");
		}
		
		public void send(String str) {
			try {
				dos.writeUTF(str);
			} catch (IOException e) {
				try {
					dis.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		private class UpdateThread implements Runnable {
			public void run() {
				while(true){
					try {
						dos.writeUTF("refresh");
						update.sleep(1000);
					} catch (IOException e) {
						//e.printStackTrace();
					} catch (InterruptedException e) {
						//e.printStackTrace();
					}
				}
			}
		}
		
		private class Fangjian1 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(canjoin==true){
					JOptionPane.showMessageDialog(null, "请勿关闭大厅");
					canjoin=false;
					trychess[0] = new Chess_client(5201,hall);
					trychess[0].yonghuming=yonghuming;
					trychess[0].room=1;
					trychess[0].setVisible(true);
					send("refresh");
				}
			}
		}
		
		private class Fangjian2 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(canjoin==true){
					JOptionPane.showMessageDialog(null, "请勿关闭大厅");
					canjoin=false;
					trychess[1] = new Chess_client(5202,hall);
					trychess[1].yonghuming=yonghuming;
					trychess[1].room=2;
					trychess[1].setVisible(true);
					send("refresh");
				}
			}
		}
		
		private class Fangjian3 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(canjoin==true){
					JOptionPane.showMessageDialog(null, "请勿关闭大厅");
					canjoin=false;
					trychess[2] = new Chess_client(5203,hall);
					trychess[2].yonghuming=yonghuming;
					trychess[2].room=3;
					trychess[2].setVisible(true);
					send("refresh");
				}
			}
		}
		
		private class Fangjian4 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(canjoin==true){
					JOptionPane.showMessageDialog(null, "请勿关闭大厅");
					canjoin=false;
					trychess[3] = new Chess_client(5204,hall);
					trychess[3].yonghuming=yonghuming;
					trychess[3].room=4;
					trychess[3].setVisible(true);
					send("refresh");
				}
			}
		}
		
		private class Fangjian5 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(canjoin==true){
					JOptionPane.showMessageDialog(null, "请勿关闭大厅");
					canjoin=false;
					trychess[4] = new Chess_client(5205,hall);
					trychess[4].yonghuming=yonghuming;
					trychess[4].room=5;
					trychess[4].setVisible(true);
					send("refresh");
				}
			}
		}
		
		private class Fangjian6 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(canjoin==true){
					JOptionPane.showMessageDialog(null, "请勿关闭大厅");
					canjoin=false;
					trychess[5] = new Chess_client(5206,hall);
					trychess[5].yonghuming=yonghuming;
					trychess[5].room=6;
					trychess[5].setVisible(true);
					send("refresh");
				}
			}
		}
		
		private class Fangjian7 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(canjoin==true){
					JOptionPane.showMessageDialog(null, "请勿关闭大厅");
					canjoin=false;
					trychess[6] = new Chess_client(5207,hall);
					trychess[6].yonghuming=yonghuming;
					trychess[6].room=7;
					trychess[6].setVisible(true);
					send("refresh");
				}
			}
		}
		
		private class Fangjian8 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(canjoin==true){
					JOptionPane.showMessageDialog(null, "请勿关闭大厅");
					canjoin=false;
					trychess[7] = new Chess_client(5208,hall);
					trychess[7].yonghuming=yonghuming;
					trychess[7].room=8;
					trychess[7].setVisible(true);
					send("refresh");
				}
			}
		}
		
		private class Fangjian9 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(canjoin==true){
					JOptionPane.showMessageDialog(null, "请勿关闭大厅");
					canjoin=false;
					trychess[8] = new Chess_client(5209,hall);
					trychess[8].yonghuming=yonghuming;
					trychess[8].room=9;
					trychess[8].setVisible(true);
					send("refresh");
				}
			}
		}
	}
}
