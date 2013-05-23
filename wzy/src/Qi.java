import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.*;

public class Qi extends JFrame implements MouseListener{
	
	private MenuBar menu = new MenuBar();
	private Menu sysmenu;
	private MenuItem start;
	private MenuItem exit;
	private MenuItem back;
	
	public DataOutputStream outDate;
	public DataInputStream   inDate;
	
	TextArea infoText = new TextArea("", 25, 40,
			TextArea.SCROLLBARS_VERTICAL_ONLY);

	// 加入会话框
	TextArea message = new TextArea("", 18, 25,
			TextArea.SCROLLBARS_VERTICAL_ONLY); // 加入信息框
	// MainQipan a;
	public int blackcount=2,whitecount=2,blackwin=0,whitewin=0;
	int chesscolor=1;
	boolean isMouseEnabled=true;
	help c;
	registerWindows b;
	me d;
	public int next = -1, n;
	int cwhite, cblack;
	String str = "";
	private int timer=20;  //倒计时时间
	private JLabel time;
	Timer t=new Timer(n);
	// chatcilent a1;
	// 建立网络连接
	Socket socket;
	String name;
	qipanThread ww;
	int[][] qi = { { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, }, { 0, 0, 0, 1, -1, 0, 0, 0 },
			{ 0, 0, 0, -1, 1, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, },
			{ 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 } };// 囊括棋盘的二维数组

	public Qi(Socket socket, String name) throws UnknownHostException,
			IOException {
		this.socket = socket;
		//this.outDate=outDate;
		//this.inDate=inDate;
		outDate=new DataOutputStream(socket.getOutputStream());
		inDate=new DataInputStream(socket.getInputStream());
		
	    this.name = name;
		pack();
		setVisible(true);
		
		
		// 网络的东东
		this.init();
		liaotianxiancheng  t = new liaotianxiancheng(socket, infoText);
		t.start();
	}

	

	public void init() throws UnknownHostException, IOException {
		setTitle("网络黑白棋  ");
		setLocation(400, 0);
		setPreferredSize(new Dimension(1280, 800));
		JPanel p = new JPanel();
		add(p);
		p.setBounds(0, 0, 1280, 800);
		setBounds(0, 0, 1280, 800);
		//p.setBackground(new Color(75, 169, 120));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		p.setLayout(null);
		// p.add(new MainQipan());

		Mylistener m1 = new Mylistener();

		
		JButton play = new JButton("播放音乐");
		JButton stop = new JButton("停止音乐");

		JLabel bai = new JLabel("白棋玩家:");
		bai.setBounds(5, 140, 100, 30);
		JLabel b1 = new JLabel();
		ImageIcon t = new ImageIcon("xyy.jpg");
		b1.setIcon(t);
		b1.setBounds(0, 180, 120, 190);
		JLabel hei = new JLabel("黑棋玩家:");
		hei.setBounds(630, 140, 100, 30);
		JLabel b3 = new JLabel();
		ImageIcon t2 = new ImageIcon("htl.jpg");
		b3.setIcon(t2);
		b3.setBounds(630, 175, 150, 190);
		p.add(hei);
		p.add(bai);
		p.add(b1);
		p.add(b3);

		play.setBounds(200, 590, 90, 25);
		stop.setBounds(450, 590, 90, 25);
		p.add(play);
		p.add(stop);
		play.addActionListener(m1);
		stop.addActionListener(m1);

		// 菜单栏
		sysmenu = new Menu("系统");
		Menu enter = new Menu("音乐");
		MenuItem enter1 = new MenuItem("注册用户");
		
		enter.add(enter1);

		enter1.addActionListener(m1);

		menu.add(sysmenu);
		sysmenu.add(enter);
		sysmenu.add(new MenuItem("-"));
		start = new MenuItem("开始");
		exit = new MenuItem("退出");
		back = new MenuItem("悔棋");
		sysmenu.add(start);
		sysmenu.add(exit);
		sysmenu.add(back);
		Menu help = new Menu("帮助");
		Mylistener m2 = new Mylistener();
		MenuItem about = new MenuItem("关于我");
		about.addActionListener(m2);
		MenuItem how = new MenuItem("如何游戏");
		Mylistener m3 = new Mylistener();
		how.addActionListener(m3);
		help.add(about);
		help.add(how);
		setMenuBar(menu);
		menu.setHelpMenu(help);

		infoText.setEditable(false);

		JScrollPane jsp = new JScrollPane(infoText);
		JScrollPane jsq = new JScrollPane(message);
		p.add(jsp);
		p.add(jsq);
		JLabel say = new JLabel("会话内容:");
		say.setBounds(880, 200, 80, 25);
		p.add(say);
		JLabel mysay = new JLabel("发送消息:");
		mysay.setBounds(880, 530, 80, 25);
		p.add(mysay);
		JLabel jname = new JLabel("用户名:" + this.name + "  ");
		jname.setBounds(980, 690, 120, 50);
		p.add(jname);
		
		jsp.setBounds(950, 0, 300, 430);
		jsq.setBounds(950, 435, 300, 250);
		JButton sendMessage = new JButton("发送");
		sendMessage.setBounds(1080, 700, 70, 25);
		p.add(sendMessage);
		
		int baiqi=getWhite();
		int heiqi=getBlack();
		JLabel j1 = new JLabel("白棋子数:" + baiqi);// 统计棋子数
		j1.setBounds(180, 520, 150, 50);
		p.add(j1);
		JLabel j2 = new JLabel("黑棋子数:" + heiqi);
		j2.setBounds(450, 520, 150, 50);
		p.add(j2);

		JLabel ww = new JLabel();
		ImageIcon t1 = new ImageIcon("meijing3.jpg");
		ww.setIcon(t1);
		ww.setBounds(0, 0, 1600, 800);
		p.add(ww);
		sendMessage.addActionListener(new bListener());
	}

	class Mylistener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Container contentPane = getContentPane();
			if (e.getActionCommand().equals("注册用户"))
				b = new registerWindows();
			if (e.getActionCommand().equals("如何游戏"))
				c = new help();
			if (e.getActionCommand().equals("关于我"))
				d = new me();
			if (e.getActionCommand().equals("播放音乐")) {
				//chatdengluqi.l.play();
			}
			if (e.getActionCommand().equals("停止音乐")) {
				//chatdengluqi.l.stop();
			}

		}
	}

	class bListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (e.getActionCommand().equals("发送"))
				;
			{
				if (message.getText().equals("")) {
					infoText.append("不能发送空消息！" + "\n");
				} else {
					String s1 = message.getText();
					// String s2=infoText.getText();
					SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss");
					// s1.setText("");
					String time = f.format(new Date());
					String send = name + " " + time + "说:" + s1;
					// if(message.getText().equals("")){infoText.append("不能发送空消息！"
					// + "\n");}
					// else{infoText.append(s1+"\n");
					// }
					// message.setText("");
					PrintWriter out = null;

					try {
						out = new PrintWriter(new OutputStreamWriter(socket
								.getOutputStream()));
						out.println(send);
						out.flush();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					message.setText("");
				}
			}
		}
	}
	public void chessvictory(int chesscolor){

		for(int t=0;t<8;t++)for(int s=0;s<8;s++)
			qi[t][s]=0;
		qi[4][4]=qi[5][5]=-1;qi[4][6]=qi[5][4]=1;
		blackcount=2;
		whitecount=2;
		if(chesscolor==1){
			blackwin++;
			//zhu.chat.chatarea.append(""+blackwin+":"+whitewin+",");
		}
		else if(chesscolor==-1){
			whitewin++;
		//zhu.chat.chatarea.append(""+blackwin+":"+whitewin+",");
		}
		
	}
	public void paint(Graphics page) {// 画棋盘

		super.paintComponents(page);

		// draw qipan
		Graphics2D g2 = (Graphics2D) page;
		Composite c=g2.getComposite();
		Color color=g2.getColor();
		g2.setComposite(AlphaComposite.SrcOver.derive(0.8f));
		
		g2.setColor(new Color(227, 133, 28));
		//g2.setColor(Color.BLACK);
		g2.fill3DRect(135, 75, 500, 500, true);
		g2.setComposite(c);
		g2.setColor(color);
		//g2.drawRect(135, 75, 495, 495);
		g2.setColor(Color.BLACK);

		for (int a = 140; a <= 620; a = a + 60)
			g2.drawLine(a, 80, a, 560);
		for (int b = 80; b <= 560; b = b + 60)
			g2.drawLine(140, b, 620, b);

		cwhite = 0;
		cblack = 0;
		for (int a = 0; a < 8; a++)
			// 画子
			for (int b = 0; b < 8; b++) {// 黑棋为1，白棋为-1
				if (qi[a][b] == -1) {
					cwhite++;

					page.setColor(Color.WHITE);
					page.fillOval(140 + a * 60, 80 + b * 60, 60, 60);

				}
				if (qi[a][b] == 1) {
					cblack++;
					page.setColor(Color.BLACK);
					page.fillOval(140 + a * 60, 80 + b * 60, 60, 60);

				}
			}
		addMouseListener(this);}
	
	public int getBlack(){//统计黑棋子数
		int count=0;
		for(int i=0;i<8;i++){
			for(int t=0;t<8;t++){
				if(qi[i][t]==1)
					count++;
	}
		}
		return count;
	}
	
	public int getWhite(){//统计白棋子数
		int count=0;
		for(int i=0;i<8;i++){
			for(int t=0;t<8;t++){
				if(qi[i][t]==-1)
					count++;
	}
		}
		return count;
	}
	
	public boolean checkWin(){
		int x=getBlack();
		int y=getWhite();
		if(x+y==64){
		return true;}
		else
		return false;
		
	}
		public void chesspaint(int a,int b,int color){//向服务器发棋子信息
				if(color==1&&isMouseEnabled){//黑子落子时记下位置
				if(checkWin()==false){
					//没有获胜，向对方发送信息，绘制棋子
					ww.send("/fight"+a+" "+b+" "+color);
					qi[a][b]=color;
					repaint();
					isMouseEnabled=false;
					//状态文本框显示信息
					//statusText.setText("黑(第"+chessblackcount+"步)"+a+" "+b+",请白棋下子");
				}else{
				//如果获胜，直接调用c	hessvictory完成后续工作
					ww.send("/fight"+a+" "+b+" "+color);
					qi[a][b]=color;
					repaint();
					chessvictory(1);
					isMouseEnabled=false;
				}}
				else
					if(color==-1&&isMouseEnabled){
						if(checkWin()==false)
						{ww.send("/fight"+a+" "+b+" "+color);
						//bian(a,b,-1);
						qi[a][b]=color;
						repaint();
						//zhu.chat.chatarea.append("("+getbai()+""+a+" "+b+",");
					isMouseEnabled=false;}
						

					else
					{ww.send("/chess"+a+" "+b+" "+color);
					//bian(a,b,-1);
					qi[a][b]=color;
					repaint();
					chessvictory(-1);
					isMouseEnabled=false;
						
					}}
					}
			
		
		
		public void netchesspaint(int a,int b,int color){//与chesspaint区别：一个画自己的棋，一个画对手的棋
			if(color==1){
				if(checkWin()==false){
					qi[a][b]=color;
					//zhu.chat.chatarea.append("("+getbai()+",");
					isMouseEnabled=true;
				}else{
					qi[a][b]=color;
					chessvictory(1);
					isMouseEnabled=true;
				}
			}else if(color==-1){
				if(checkWin()==false){
					qi[a][b]=color;//zhu.chat.chatarea.append("("+gethei()+",");
					isMouseEnabled=true;
				}else{
					qi[a][b]=color;
					chessvictory(-1);
					isMouseEnabled=true;
				}
			}
		}
			
	boolean f = true;

	boolean flag = true;

	public boolean fanqi(int x, int y) {//走棋的算法
		boolean a = false;
		if (qi[x][y] == 0) {

			if (flag) {// 黑吃白
				if (y - 1 > 0 && qi[x][y - 1] == -1) {
					for (int i = 2; y - i >= 0; i++) {
						if (qi[x][y - i] == 1) {
							for (int n = 1; n < i; n++) {
								qi[x][y - n] = 1;

							}
							a = true;
							if (qi[x][y - i] == 0)
								i = 9;
						}

					}
				}

				if (y + 1 < 8 && qi[x][y + 1] == -1) {
					for (int i = 2; y + i < 8; i++) {
						if (qi[x][y + i] == 1) {
							for (int n = 1; n < i; n++) {
								qi[x][y + n] = 1;

							}
							a = true;
							if (qi[x][y + i] == 0)
								i = 9;
						}
					}
				}
				if (x + 1 < 8 && qi[x + 1][y] == -1) {
					for (int i = 2; x + i < 8; i++) {
						if (qi[x + i][y] == 1) {
							for (int n = 1; n < i; n++) {
								qi[x + n][y] = 1;

							}
							a = true;
							if (qi[x + i][y] == 0)
								i = 9;
						}
					}
				}
				if (x - 1 >= 0 && qi[x - 1][y] == -1) {
					for (int i = 2; x - i >= 0; i++) {
						if (qi[x - i][y] == 1) {
							for (int n = 1; n < i; n++) {
								qi[x - n][y] = 1;

							}
							a = true;
							if (qi[x - i][y] == 0)
								i = 9;
						}
					}
				}
				if (x - 1 >= 0 && y - 1 >= 0 && qi[x - 1][y - 1] == -1) {
					for (int i = 2; x - i >= 0 && y - i >= 0; i++) {
						if (qi[x - i][y - i] == 1) {
							for (int n = 1; n < i; n++) {
								qi[x - n][y - n] = 1;

							}
							a = true;
							if (qi[x - i][y - i] == 0)
								i = 9;
						}
					}
				}
				if (x + 1 < 8 && y - 1 >= 0 && qi[x + 1][y - 1] == -1) {
					for (int i = 2; x + i < 8 && y - i >= 0; i++) {
						if (qi[x + i][y - i] == 1) {
							for (int n = 1; n < i; n++) {
								qi[x + n][y - n] = 1;

							}
							a = true;
							if (qi[x + i][y - i] == 0)
								i = 9;
						}
					}
				}
				if (x - 1 >= 0 && y + 1 < 8 && qi[x - 1][y + 1] == -1) {
					for (int i = 2; x - i >= 0 && y + i < 8; i++) {
						if (qi[x - i][y + i] == 1) {
							for (int n = 1; n < i; n++) {
								qi[x - n][y + n] = 1;

							}
							a = true;
							if (qi[x - i][y + i] == 0)
								i = 9;
						}
					}
				}
				if (x + 1 < 8 && y + 1 < 8 && qi[x + 1][y + 1] == -1) {
					for (int i = 2; x + i < 8 && y + i < 8; i++) {
						if (qi[x + i][y + i] == 1) {
							for (int n = 1; n < i; n++) {
								qi[x + n][y + n] = 1;

							}
							a = true;
							if (qi[x + i][y + i] == 0)
								i = 9;
						}
					}
				}

			}

			else {// 白吃黑
				if (y - 1 > 0 && qi[x][y - 1] == 1) {
					for (int i = 2; y - i >= 0; i++) {
						if (qi[x][y - i] == -1) {
							for (int n = 1; n < i; n++) {
								qi[x][y - n] = -1;

							}
							a = true;
							if (qi[x][y - i] == 0)
								i = 9;
						}

					}
				}

				if (y + 1 < 8 && qi[x][y + 1] == 1) {
					for (int i = 2; y + i < 8; i++) {
						if (qi[x][y + i] == -1) {
							for (int n = 1; n < i; n++) {
								qi[x][y + n] = -1;

							}
							a = true;
							if (qi[x][y + i] == 0)
								i = 9;
						}
					}
				}
				if (x + 1 < 8 && qi[x + 1][y] == 1) {
					for (int i = 2; x + i < 8; i++) {
						if (qi[x + i][y] == -1) {
							for (int n = 1; n < i; n++) {
								qi[x + n][y] = -1;

							}
							a = true;
							if (qi[x + i][y] == 0)
								i = 9;
						}
					}
				}
				if (x - 1 >= 0 && qi[x - 1][y] == 1) {
					for (int i = 2; x - i >= 0; i++) {
						if (qi[x - i][y] == -1) {
							for (int n = 1; n < i; n++) {
								qi[x - n][y] = -1;

							}
							a = true;
							if (qi[x - i][y] == 0)
								i = 9;
						}
					}
				}
				if (x - 1 >= 0 && y - 1 >= 0 && qi[x - 1][y - 1] == 1) {
					for (int i = 2; x - i >= 0 && y - i >= 0; i++) {
						if (qi[x - i][y - i] == -1) {
							for (int n = 1; n < i; n++) {
								qi[x - n][y - n] = -1;

							}
							a = true;
							if (qi[x - i][y - i] == 0)
								i = 9;
						}
					}
				}
				if (x + 1 < 8 && y - 1 >= 0 && qi[x + 1][y - 1] == 1) {
					for (int i = 2; x + i < 8 && y - i >= 0; i++) {
						if (qi[x + i][y - i] == -1) {
							for (int n = 1; n < i; n++) {
								qi[x + n][y - n] = -1;

							}
							a = true;
							if (qi[x + i][y - i] == 0)
								i = 9;
						}
					}
				}
				if (x - 1 >= 0 && y + 1 < 8 && qi[x - 1][y + 1] == 1) {
					for (int i = 2; x - i >= 0 && y + i < 8; i++) {
						if (qi[x - i][y + i] == -1) {
							for (int n = 1; n < i; n++) {
								qi[x - n][y + n] = -1;

							}
							a = true;
							if (qi[x - i][y + i] == 0)
								i = 9;
						}
					}
				}
				if (x + 1 < 8 && y + 1 < 8 && qi[x + 1][y + 1] == 1) {
					for (int i = 2; x + i < 8 && y + i < 8; i++) {
						if (qi[x + i][y + i] == -1) {
							for (int n = 1; n < i; n++) {
								qi[x + n][y + n] = -1;

							}
							a = true;
							if (qi[x + i][y + i] == 0)
								i = 9;
						}
					}
				}

			}

		}
		if (a)
			return true;
		else
			return false;

	}
	 private class Timer extends Thread{ //倒计时牌
		 int n;
		 public Timer(int nn){
		 n=nn;
		 }
		 public void run(){
		 timer=20;
		 for(int a=1;a<=21;a++){
		 time.setText("倒计时：  "+timer);
		 timer--;
		 if (timer==-1){     //超时判负
		 cwhite=0;
		 cblack=0;
		    for (int i=0;i<=qi.length-1;i++){     //计算棋子数
		 for (int j=0;j<=qi[i].length-1;j++){
		 if (qi[i][j]==1){
		 cwhite++;
		 }
		 else{
		 if (qi[i][j]==-1){
		 cblack++;
		 }
		 }
		 }
		 }    
		    if (n==-1) {
		     str = "白方胜    白方：黑方=" + cwhite+ ":" + cblack;
		        } 
		     else {
		     if(n==1)
		     str = "黑方胜    黑方：白方=" + cblack + ":" + cwhite;
		        }
		     JOptionPane.showConfirmDialog(null, str, "Game Over",JOptionPane.CLOSED_OPTION);
		     break;
		 }
		 try { 
		                Thread.sleep(1000); 
		            } catch (InterruptedException e) { 
		                e.printStackTrace(); 
		            } 
		 }
		 }
		 }

	public void judge() {//判断胜负
		int white = 0;
		int black = 0;
		for (int i = 0; i <= 7; i++) {
			for (int j = 0; j <= 7; j++) {
				if (qi[i][j] == 2) {
					white++;
				} else if (qi[i][j] == 1) {
					black++;
				}
			}

		}
		if (white + black < 64) {
			if (white == 0) {
				new blackwin();
			} else {
				if (black == 0) {
					new whitewin();
				}

			}
		}
		if (white + black == 64 || white == 0 || black == 0) {
			if (white > black || black == 0) {
				// str = "白棋胜    白方：黑方=" + cwhite + ":" + cblack;
				new whitewin();
			} else if (black > white || white == 0) {
				// str="黑棋胜   黑方：白方="+black+":"+white;
				new blackwin();
			} else
				str = "双方战平";
		}
		// JOptionPane.showConfirmDialog(this, str,
		// "Game Over",JOptionPane.CLOSED_OPTION); //显示胜负信息
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		// System.out.println("lllll");
		// add(e.getPoint());
		int x = (int) e.getX();
		int y = (int) e.getY();
		int X = (x - 140) / 60;
		int Y = (y - 80) / 60;

		if (fanqi(X, Y)) {
			if (flag) {
				qi[X][Y] = 1;
				flag = false;
				
			}

			else {
				qi[X][Y] = -1;// 黑棋是1
				flag = true;
				
			}
			chesspaint(X,Y,chesscolor);
			//repaint();
			
			//System.out.println("mmmmmmmmm");
		}
		if (true) {

		}
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {
	}

	public static void main(String[] args) throws UnknownHostException, IOException   {
		
			 Qi q=new Qi(null,null);
		

	

}

class liaotianxiancheng extends Thread {
	private Socket socket;
	private TextArea area;

	public liaotianxiancheng(Socket socket, TextArea area) {
		this.socket = socket;
		this.area = area;
	}

	public void run() {
		BufferedReader bf = null;
		try {

			bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));// 输入流读取数据添加到聊天记录区域
			String str = null;
			while ((str = bf.readLine()) != null) {
				area.append(str + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bf != null) {
				try {
					bf.close();
				} catch (IOException e) {

				}
			}
		}
	}
}}
