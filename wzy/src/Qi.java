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

	// ����Ự��
	TextArea message = new TextArea("", 18, 25,
			TextArea.SCROLLBARS_VERTICAL_ONLY); // ������Ϣ��
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
	private int timer=20;  //����ʱʱ��
	private JLabel time;
	Timer t=new Timer(n);
	// chatcilent a1;
	// ������������
	Socket socket;
	String name;
	qipanThread ww;
	int[][] qi = { { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, }, { 0, 0, 0, 1, -1, 0, 0, 0 },
			{ 0, 0, 0, -1, 1, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, },
			{ 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 } };// �������̵Ķ�ά����

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
		
		
		// ����Ķ���
		this.init();
		liaotianxiancheng  t = new liaotianxiancheng(socket, infoText);
		t.start();
	}

	

	public void init() throws UnknownHostException, IOException {
		setTitle("����ڰ���  ");
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

		
		JButton play = new JButton("��������");
		JButton stop = new JButton("ֹͣ����");

		JLabel bai = new JLabel("�������:");
		bai.setBounds(5, 140, 100, 30);
		JLabel b1 = new JLabel();
		ImageIcon t = new ImageIcon("xyy.jpg");
		b1.setIcon(t);
		b1.setBounds(0, 180, 120, 190);
		JLabel hei = new JLabel("�������:");
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

		// �˵���
		sysmenu = new Menu("ϵͳ");
		Menu enter = new Menu("����");
		MenuItem enter1 = new MenuItem("ע���û�");
		
		enter.add(enter1);

		enter1.addActionListener(m1);

		menu.add(sysmenu);
		sysmenu.add(enter);
		sysmenu.add(new MenuItem("-"));
		start = new MenuItem("��ʼ");
		exit = new MenuItem("�˳�");
		back = new MenuItem("����");
		sysmenu.add(start);
		sysmenu.add(exit);
		sysmenu.add(back);
		Menu help = new Menu("����");
		Mylistener m2 = new Mylistener();
		MenuItem about = new MenuItem("������");
		about.addActionListener(m2);
		MenuItem how = new MenuItem("�����Ϸ");
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
		JLabel say = new JLabel("�Ự����:");
		say.setBounds(880, 200, 80, 25);
		p.add(say);
		JLabel mysay = new JLabel("������Ϣ:");
		mysay.setBounds(880, 530, 80, 25);
		p.add(mysay);
		JLabel jname = new JLabel("�û���:" + this.name + "  ");
		jname.setBounds(980, 690, 120, 50);
		p.add(jname);
		
		jsp.setBounds(950, 0, 300, 430);
		jsq.setBounds(950, 435, 300, 250);
		JButton sendMessage = new JButton("����");
		sendMessage.setBounds(1080, 700, 70, 25);
		p.add(sendMessage);
		
		int baiqi=getWhite();
		int heiqi=getBlack();
		JLabel j1 = new JLabel("��������:" + baiqi);// ͳ��������
		j1.setBounds(180, 520, 150, 50);
		p.add(j1);
		JLabel j2 = new JLabel("��������:" + heiqi);
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
			if (e.getActionCommand().equals("ע���û�"))
				b = new registerWindows();
			if (e.getActionCommand().equals("�����Ϸ"))
				c = new help();
			if (e.getActionCommand().equals("������"))
				d = new me();
			if (e.getActionCommand().equals("��������")) {
				//chatdengluqi.l.play();
			}
			if (e.getActionCommand().equals("ֹͣ����")) {
				//chatdengluqi.l.stop();
			}

		}
	}

	class bListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (e.getActionCommand().equals("����"))
				;
			{
				if (message.getText().equals("")) {
					infoText.append("���ܷ��Ϳ���Ϣ��" + "\n");
				} else {
					String s1 = message.getText();
					// String s2=infoText.getText();
					SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss");
					// s1.setText("");
					String time = f.format(new Date());
					String send = name + " " + time + "˵:" + s1;
					// if(message.getText().equals("")){infoText.append("���ܷ��Ϳ���Ϣ��"
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
	public void paint(Graphics page) {// ������

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
			// ����
			for (int b = 0; b < 8; b++) {// ����Ϊ1������Ϊ-1
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
	
	public int getBlack(){//ͳ�ƺ�������
		int count=0;
		for(int i=0;i<8;i++){
			for(int t=0;t<8;t++){
				if(qi[i][t]==1)
					count++;
	}
		}
		return count;
	}
	
	public int getWhite(){//ͳ�ư�������
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
		public void chesspaint(int a,int b,int color){//���������������Ϣ
				if(color==1&&isMouseEnabled){//��������ʱ����λ��
				if(checkWin()==false){
					//û�л�ʤ����Է�������Ϣ����������
					ww.send("/fight"+a+" "+b+" "+color);
					qi[a][b]=color;
					repaint();
					isMouseEnabled=false;
					//״̬�ı�����ʾ��Ϣ
					//statusText.setText("��(��"+chessblackcount+"��)"+a+" "+b+",���������");
				}else{
				//�����ʤ��ֱ�ӵ���c	hessvictory��ɺ�������
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
			
		
		
		public void netchesspaint(int a,int b,int color){//��chesspaint����һ�����Լ����壬һ�������ֵ���
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

	public boolean fanqi(int x, int y) {//������㷨
		boolean a = false;
		if (qi[x][y] == 0) {

			if (flag) {// �ڳ԰�
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

			else {// �׳Ժ�
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
	 private class Timer extends Thread{ //����ʱ��
		 int n;
		 public Timer(int nn){
		 n=nn;
		 }
		 public void run(){
		 timer=20;
		 for(int a=1;a<=21;a++){
		 time.setText("����ʱ��  "+timer);
		 timer--;
		 if (timer==-1){     //��ʱ�и�
		 cwhite=0;
		 cblack=0;
		    for (int i=0;i<=qi.length-1;i++){     //����������
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
		     str = "�׷�ʤ    �׷����ڷ�=" + cwhite+ ":" + cblack;
		        } 
		     else {
		     if(n==1)
		     str = "�ڷ�ʤ    �ڷ����׷�=" + cblack + ":" + cwhite;
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

	public void judge() {//�ж�ʤ��
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
				// str = "����ʤ    �׷����ڷ�=" + cwhite + ":" + cblack;
				new whitewin();
			} else if (black > white || white == 0) {
				// str="����ʤ   �ڷ����׷�="+black+":"+white;
				new blackwin();
			} else
				str = "˫��սƽ";
		}
		// JOptionPane.showConfirmDialog(this, str,
		// "Game Over",JOptionPane.CLOSED_OPTION); //��ʾʤ����Ϣ
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
				qi[X][Y] = -1;// ������1
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

			bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));// ��������ȡ������ӵ������¼����
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
