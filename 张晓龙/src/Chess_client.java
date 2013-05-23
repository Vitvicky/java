import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;


public class Chess_client extends JFrame{
	Socket s = null;
	DataOutputStream dos = null;
	DataInputStream dis = null;
	Date now = new Date();
	JLabel timerlb = new JLabel();
	private boolean bConnected = false;
	private TextArea taContent = new TextArea("",15,15);
	private TextField tf = new TextField("",25);//25
	Thread tRecv = new Thread(new RecvThread());
	Buttons bta = new Buttons();
	JLabel numsofb = new JLabel("    2");
	JLabel numsofw = new JLabel("    2");
	private int members_online=0;
	public String yonghuming;
	boolean yes=true;
	boolean canplay=false;
	Hall_client.Hall hall = null;
	public int room=0;
	
	
	public Chess_client(int port,Hall_client.Hall hall){
		this.hall=hall;
		setTitle("chess");
		setSize(800, 600);
		setIconImage(Toolkit.getDefaultToolkit().getImage("map01.jpg"));
		setLocationRelativeTo(null);
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(gridbag);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new InnerDestroyer());
		
		c.fill=GridBagConstraints.BOTH;
		c.anchor=GridBagConstraints.CENTER;
		
		Container container = getContentPane();
		JButton huiqibt = new JButton("悔棋");
		addComp(huiqibt,container,c,4,0,1,1,5,0);
		Huiqi huiqi = new Huiqi();
		huiqibt.addActionListener(huiqi);
		
		JButton tuichubt = new JButton("退出");
		addComp(tuichubt,container,c,4,1,1,1,5,0);
		Tuichu tuichu = new Tuichu();
		tuichubt.addActionListener(tuichu);
		
		JButton shuaxinbt = new JButton("刷新");
		addComp(shuaxinbt,container,c,4,2,1,2,0,0);
		Shuaxin shuaxin = new Shuaxin();
		shuaxinbt.addActionListener(shuaxin);
		
		addComp(time(),container,c,4,4,1,1,5,5);
		
		JButton fasongbt= new JButton("发送");
		addComp(fasongbt,container,c,3,5,1,1,0,0);
		Fasong fasong= new Fasong();
		fasongbt.addActionListener(fasong);
		
		JPanel num_o_b = new JPanel();
		num_o_b.setLayout(new BorderLayout());
		num_o_b.add(numsofb,BorderLayout.SOUTH);
		JLabel num_o_b2 = new JLabel();
		num_o_b2.setIcon(new ImageIcon("2.gif"));
		num_o_b.add(num_o_b2,BorderLayout.NORTH);
		num_o_b.setVisible(true);
		addComp(num_o_b,container,c,0,2,1,2,0,0);
		
		JPanel num_o_b3 = new JPanel();
		num_o_b3.setLayout(new BorderLayout());
		num_o_b3.add(numsofw,BorderLayout.SOUTH);
		JLabel num_o_b4 = new JLabel();
		num_o_b4.setIcon(new ImageIcon("1.gif"));
		num_o_b3.add(num_o_b4,BorderLayout.NORTH);
		num_o_b3.setVisible(true);
		addComp(num_o_b3,container,c,1,2,1,2,0,0);
		
		addComp(taContent,container,c,0,4,3,2,10,0);
		addComp(tf,container,c,3,4,1,1,0,0);
		c.insets = new Insets(10,10,10,10);
		addComp(qp(),container,c,0,0,3,2,0,0);
		pack();
		
		connect(port);
		tf.addActionListener(new TFListener());
		tRecv.start();
		try {
			dos.writeUTF("refresh");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		Chess_client trychess = new Chess_client(5757,null);
		trychess.setVisible(true);
		
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
	
	
	private JPanel qp(){
		JPanel xx=new JPanel();
		xx.setLayout(new GridLayout(8,8,2,2));
		
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				xx.add(bta.bt[i][j]); 
			}
		}
		return xx;
	}
	
	private JPanel time(){
		JPanel time = new JPanel();
		timerlb.setText("00:00:30");
		now.setHours(0);
		now.setMinutes(0);
		now.setSeconds(30);
		Timer timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(bta.w_or_b!=bta.w_or_b_online&&members_online!=1&&yes){
					now = new Date(now.getTime() - 1000);
					SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
					timerlb.setText(formatter.format(now));
					if(now.getSeconds()==0){
						if(bta.members==2){
							try {
								dos.writeUTF("\twhitetimeout");
								yes=false;
								canplay=false;
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
						if(bta.members==1){
							try {
								dos.writeUTF("\tblacktimeout");
								yes=false;
								canplay=false;
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					}
				}
			}
		});
		timer.start();
		time.add(timerlb);
		return time;
	}
	
	public void updateqp(int[][] chess){
		int a=0;
		int b=0;
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if(chess[i][j]==0){
					bta.bt[i][j].setIcon(null);
				}
				if(chess[i][j]==1){
					a++;
					bta.bt[i][j].setIcon(new ImageIcon("1.gif"));
				}
				if(chess[i][j]==2){
					b++;
					bta.bt[i][j].setIcon(new ImageIcon("2.gif"));
				}
				if(chess[i][j]==9){
					bta.bt[i][j].setIcon(new ImageIcon("3.gif"));
				}
			}
		}
		numsofb.setText("    "+b);
		numsofw.setText("    "+a);
		now.setSeconds(30);
	}
	
	
	public void send(String str) {
		try {
			if(bta.members==1||bta.members==2){
				if(bta.w_or_b!=bta.w_or_b_online&&members_online!=1&&canplay){
					dos.writeUTF(str);
				}
			}
		} catch (IOException e) {
			try {
				dis.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	
	public void transfer(int[][] chess,String qp){
		String temp=qp;
		String temp2="";
		int num=0;
		int num2=0;
		int tempb_or_w=0;
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				chess[i][j]=Integer.parseInt(temp.substring(0,1));
				temp=temp.substring(2,temp.length());
			}
			temp=temp.substring(1,temp.length());
		}
		tempb_or_w=Integer.parseInt(temp.substring(0,1));
		if(tempb_or_w==3){
			JOptionPane.showMessageDialog(null, "白子无棋可下，黑子继续一轮。");
			bta.w_or_b_online=1;
			tempb_or_w=1;
		}
		if(tempb_or_w==4){
			JOptionPane.showMessageDialog(null, "黑子无棋可下，白子继续一轮。");
			bta.w_or_b_online=2;
			tempb_or_w=2;
		}
		temp2=temp.substring(2,temp.length());
		temp=temp.substring(1,2);
		try{
			bta.w_or_b_online=tempb_or_w;
			num=Integer.parseInt(temp);
			this.members_online=num;
			if(num==1&&bta.w_or_b==99){
				bta.w_or_b=2;
				bta.members=num;
				canplay=true;
			}
			if(num==2&&bta.w_or_b==99){
				bta.w_or_b=1;
				bta.members=num;
				canplay=true;
			}
			if(num!=1&&num!=2){
				if(bta.members==0){
					bta.members=num;
				}
			}
			
			num2=Integer.parseInt(temp2);
			if(num2==2){
				JOptionPane.showMessageDialog(null, "游戏开始。");
			}
		}catch(NumberFormatException e){
			
		}
		try {
			dos.writeUTF("\n\n\n"+bta.members);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void connect(int port) {
		try {
			s = new Socket("127.0.0.1", port);
			dos = new DataOutputStream(s.getOutputStream());
			dis = new DataInputStream(s.getInputStream());
			bConnected = true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	
	private class InnerDestroyer extends WindowAdapter
    {
        public void windowClosing(WindowEvent e) 
        {
        	ConfirmWindow askWindow = new ConfirmWindow( );
            askWindow.setVisible(true);
        }
    }
	
	private class ConfirmWindow extends JFrame implements ActionListener{
		public static final int WIDTH = 200;
		public static final int HEIGHT = 100;

		public ConfirmWindow(){
			setSize(WIDTH, HEIGHT);
			setLocation(500,300);
			Container confirmContent = getContentPane( );
			confirmContent.setBackground(Color.WHITE);
			confirmContent.setLayout(new BorderLayout( ));

			JLabel msgLabel = new JLabel(
			"你确定要退出吗?");
			confirmContent.add(msgLabel, BorderLayout.CENTER);

			JPanel buttonPanel = new JPanel( );
			buttonPanel.setLayout(new FlowLayout( ));

			JButton exitButton = new JButton("Yes");
			exitButton.addActionListener(this);
			buttonPanel.add(exitButton);

			JButton cancelButton = new JButton("No");
			cancelButton.addActionListener(this);
			buttonPanel.add(cancelButton);

			confirmContent.add(buttonPanel, BorderLayout.SOUTH);
		}

		public void actionPerformed(ActionEvent e){
			if (e.getActionCommand( ).equals("Yes")){
				//System.exit(0);
				dispose();
				hall.trychess[room-1].dispose();
				hall.canjoin=true;
				try {
					dos.writeUTF("\tremove"+bta.members);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			else if (e.getActionCommand( ).equals("No"))
				dispose( );//Destroys only the ConfirmWindow.
			else
				System.out.println("Error in Confirm Window.");
		}
	}
	
	private class TFListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String str = tf.getText().trim();
			tf.setText("");

			try {
				dos.writeUTF(yonghuming+" : "+str);
				dos.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}

	}
	
	private class Fasong implements ActionListener{
		public void actionPerformed(ActionEvent e){
			String str = tf.getText().trim();
			tf.setText("");

			try {
				dos.writeUTF(str);
				dos.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private class Tuichu implements ActionListener{
		public void actionPerformed(ActionEvent e){
			ConfirmWindow askWindow = new ConfirmWindow( );
            askWindow.setVisible(true);
		}
	}
	
	private class Shuaxin implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try {
				dos.writeUTF("refresh");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private class Huiqi implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(bta.members==1||bta.members==2){
				if(canplay){
					try {
						dos.writeUTF("\thuiqi\t"+bta.w_or_b+"0");
					} catch (IOException f) {
						try {
							dis.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	private class RecvThread implements Runnable {
		public int[][] chess=new int[8][8];
		{
			for(int i=0;i<8;i++){
				for(int j=0;j<8;j++){
					chess[i][j]=0;
				}
			}
		}
		
		
		public void run() {
			try {
				while (bConnected) {
					String str = dis.readUTF();
					if (str.substring(1,2).equals("\t")) {
						transfer(chess, str);
						updateqp(chess);
					}else{
						if(str.equals("\thuiqi")){
							if(bta.members==1||bta.members==2){
								int answer=0;
								answer=JOptionPane.showConfirmDialog(null, "对方想悔棋，同意吗？","悔棋确认",0);
								if(answer==JOptionPane.YES_OPTION){
									dos.writeUTF("\thuiqi\t"+bta.w_or_b+"3");
								}
								if(answer==JOptionPane.NO_OPTION){
									dos.writeUTF("\thuiqi\t"+bta.w_or_b+"2");
								}
							}else{
								JOptionPane.showMessageDialog(null, "请求悔棋");
							}
						}else{
							if(str.equals("\thuiqi\tno")){
								JOptionPane.showMessageDialog(null, "对方拒绝悔棋。");
							}else{
								if(str.equals("\theisishengli")){
									JOptionPane.showMessageDialog(null, "黑棋胜利。");
									yes=false;
									canplay=false;
								}else{
									if(str.equals("\tbaisishengli")){
										JOptionPane.showMessageDialog(null, "白棋胜利。");
										yes=false;
										canplay=false;
									}else{
										if(str.equals("\theqi")){
											JOptionPane.showMessageDialog(null, "和棋。");
											yes=false;
											canplay=false;
										}else{
											if(str.equals("\tcheck")){
												continue;
											}else{
												if(str.equals("\t黑方悔棋")){
													JOptionPane.showMessageDialog(null, "黑方悔棋");
												}else{
													if(str.equals("\t白方悔棋")){
														JOptionPane.showMessageDialog(null, "白方悔棋");
													}else{
														if(str.equals("\tduifangtuichu")){
															JOptionPane.showMessageDialog(null, "对方退出");
															canplay=false;
															yes=false;
															
														}else{
															if(str.equals("\tyifangtuichu")){
																JOptionPane.showMessageDialog(null, "一方退出,游戏结束");
																canplay=false;
																yes=false;
															}else{
																if(str.equals("\n\n\nnums")){
																	dos.writeUTF("\n\n\n"+bta.members);
																}else{
																	taContent.setText(taContent.getText() + str + '\n');
																}
															}
														}
													}
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
	

	class Buttons {
		public JButton[][] bt = new JButton[8][8];
		public int w_or_b=99;
		public int members=0;
		public int w_or_b_online=0;
		
		public Buttons(){
			for(int i=0;i<64;i++){
			
			}
			for(int i=0;i<8;i++){
				for(int j=0;j<8;j++){
					bt[i][j]=new JButton("");
					//bt[i][j].setContentAreaFilled(true);
					bt[i][j].setBackground(new Color(187,160,133));
					//bt[i][j].setBackground(new Color(0,94,17));
				}
			}
			
			bt[3][3].setIcon(new ImageIcon("1.gif"));
			bt[3][4].setIcon(new ImageIcon("2.gif"));
			bt[4][3].setIcon(new ImageIcon("2.gif"));
			bt[4][4].setIcon(new ImageIcon("1.gif"));
				MyListener1 l1 = new MyListener1();
				MyListener2 l2 = new MyListener2();
				MyListener3 l3 = new MyListener3();
				MyListener4 l4 = new MyListener4();
				MyListener5 l5 = new MyListener5();
				MyListener6 l6 = new MyListener6();
				MyListener7 l7 = new MyListener7();
				MyListener8 l8 = new MyListener8();
				MyListener9 l9 = new MyListener9();
				MyListener10 l10 = new MyListener10();
				MyListener11 l11 = new MyListener11();
				MyListener12 l12 = new MyListener12();
				MyListener13 l13 = new MyListener13();
				MyListener14 l14 = new MyListener14();
				MyListener15 l15 = new MyListener15();
				MyListener16 l16 = new MyListener16();
				MyListener17 l17 = new MyListener17();
				MyListener18 l18 = new MyListener18();
				MyListener19 l19 = new MyListener19();
				MyListener20 l20 = new MyListener20();
				MyListener21 l21 = new MyListener21();
				MyListener22 l22 = new MyListener22();
				MyListener23 l23 = new MyListener23();
				MyListener24 l24 = new MyListener24();
				MyListener25 l25 = new MyListener25();
				MyListener26 l26 = new MyListener26();
				MyListener27 l27 = new MyListener27();
				MyListener28 l28 = new MyListener28();
				MyListener29 l29 = new MyListener29();
				MyListener30 l30 = new MyListener30();
				MyListener31 l31 = new MyListener31();
				MyListener32 l32 = new MyListener32();
				MyListener33 l33 = new MyListener33();
				MyListener34 l34 = new MyListener34();
				MyListener35 l35 = new MyListener35();
				MyListener36 l36 = new MyListener36();
				MyListener37 l37 = new MyListener37();
				MyListener38 l38 = new MyListener38();
				MyListener39 l39 = new MyListener39();
				MyListener40 l40 = new MyListener40();
				MyListener41 l41 = new MyListener41();
				MyListener42 l42 = new MyListener42();
				MyListener43 l43 = new MyListener43();
				MyListener44 l44 = new MyListener44();
				MyListener45 l45 = new MyListener45();
				MyListener46 l46 = new MyListener46();
				MyListener47 l47 = new MyListener47();
				MyListener48 l48 = new MyListener48();
				MyListener49 l49 = new MyListener49();
				MyListener50 l50 = new MyListener50();
				MyListener51 l51 = new MyListener51();
				MyListener52 l52 = new MyListener52();
				MyListener53 l53 = new MyListener53();
				MyListener54 l54 = new MyListener54();
				MyListener55 l55 = new MyListener55();
				MyListener56 l56 = new MyListener56();
				MyListener57 l57 = new MyListener57();
				MyListener58 l58 = new MyListener58();
				MyListener59 l59 = new MyListener59();
				MyListener60 l60 = new MyListener60();
				MyListener61 l61 = new MyListener61();
				MyListener62 l62 = new MyListener62();
				MyListener63 l63 = new MyListener63();
				MyListener64 l64 = new MyListener64();
				
				bt[0][0].addActionListener(l1);
				bt[0][1].addActionListener(l2);
				bt[0][2].addActionListener(l3);
				bt[0][3].addActionListener(l4);
				bt[0][4].addActionListener(l5);
				bt[0][5].addActionListener(l6);
				bt[0][6].addActionListener(l7);
				bt[0][7].addActionListener(l8);
				bt[1][0].addActionListener(l9);
				bt[1][1].addActionListener(l10);
				bt[1][2].addActionListener(l11);
				bt[1][3].addActionListener(l12);
				bt[1][4].addActionListener(l13);
				bt[1][5].addActionListener(l14);
				bt[1][6].addActionListener(l15);
				bt[1][7].addActionListener(l16);
				bt[2][0].addActionListener(l17);
				bt[2][1].addActionListener(l18);
				bt[2][2].addActionListener(l19);
				bt[2][3].addActionListener(l20);
				bt[2][4].addActionListener(l21);
				bt[2][5].addActionListener(l22);
				bt[2][6].addActionListener(l23);
				bt[2][7].addActionListener(l24);
				bt[3][0].addActionListener(l25);
				bt[3][1].addActionListener(l26);
				bt[3][2].addActionListener(l27);
				bt[3][3].addActionListener(l28);
				bt[3][4].addActionListener(l29);
				bt[3][5].addActionListener(l30);
				bt[3][6].addActionListener(l31);
				bt[3][7].addActionListener(l32);
				bt[4][0].addActionListener(l33);
				bt[4][1].addActionListener(l34);
				bt[4][2].addActionListener(l35);
				bt[4][3].addActionListener(l36);
				bt[4][4].addActionListener(l37);
				bt[4][5].addActionListener(l38);
				bt[4][6].addActionListener(l39);
				bt[4][7].addActionListener(l40);
				bt[5][0].addActionListener(l41);
				bt[5][1].addActionListener(l42);
				bt[5][2].addActionListener(l43);
				bt[5][3].addActionListener(l44);
				bt[5][4].addActionListener(l45);
				bt[5][5].addActionListener(l46);
				bt[5][6].addActionListener(l47);
				bt[5][7].addActionListener(l48);
				bt[6][0].addActionListener(l49);
				bt[6][1].addActionListener(l50);
				bt[6][2].addActionListener(l51);
				bt[6][3].addActionListener(l52);
				bt[6][4].addActionListener(l53);
				bt[6][5].addActionListener(l54);
				bt[6][6].addActionListener(l55);
				bt[6][7].addActionListener(l56);
				bt[7][0].addActionListener(l57);
				bt[7][1].addActionListener(l58);
				bt[7][2].addActionListener(l59);
				bt[7][3].addActionListener(l60);
				bt[7][4].addActionListener(l61);
				bt[7][5].addActionListener(l62);
				bt[7][6].addActionListener(l63);
				bt[7][7].addActionListener(l64);
			
			
		}
		
		
		
		
		
		class MyListener1 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t0\t0");
				}else{
					send("2\t0\t0");
				}
			}
		}
		class MyListener2 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t0\t1");
				}else{
					send("2\t0\t1");
				}
			}
		}
		class MyListener3 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t0\t2");
				}else{
					send("2\t0\t2");
				}
			}
		}
		class MyListener4 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t0\t3");
				}else{
					send("2\t0\t3");
				}
			}
		}
		class MyListener5 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t0\t4");
				}else{
					send("2\t0\t4");
				}
			}
		}
		class MyListener6 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t0\t5");
				}else{
					send("2\t0\t5");
				}
			}
		}
		class MyListener7 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t0\t6");
				}else{
					send("2\t0\t6");
				}
			}
		}
		class MyListener8 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t0\t7");
				}else{
					send("2\t0\t7");
				}
			}
		}
		class MyListener9 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t1\t0");
				}else{
					send("2\t1\t0");
				}
			}
		}
		class MyListener10 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t1\t1");
				}else{
					send("2\t1\t1");
				}
			}
		}
		class MyListener11 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t1\t2");
				}else{
					send("2\t1\t2");
				}
			}
		}
		class MyListener12 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t1\t3");
				}else{
					send("2\t1\t3");
				}
			}
		}
		class MyListener13 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t1\t4");
				}else{
					send("2\t1\t4");
				}
			}
		}
		class MyListener14 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t1\t5");
				}else{
					send("2\t1\t5");
				}
			}
		}
		class MyListener15 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t1\t6");
				}else{
					send("2\t1\t6");
				}
			}
		}
		class MyListener16 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t1\t7");
				}else{
					send("2\t1\t7");
				}
			}
		}
		class MyListener17 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t2\t0");
				}else{
					send("2\t2\t0");
				}
			}
		}
		class MyListener18 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t2\t1");
				}else{
					send("2\t2\t1");
				}
			}
		}
		class MyListener19 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t2\t2");
				}else{
					send("2\t2\t2");
				}
			}
		}
		class MyListener20 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t2\t3");
				}else{
					send("2\t2\t3");
				}
			}
		}
		class MyListener21 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t2\t4");
				}else{
					send("2\t2\t4");
				}
			}
		}
		class MyListener22 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t2\t5");
				}else{
					send("2\t2\t5");
				}
			}
		}
		class MyListener23 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t2\t6");
				}else{
					send("2\t2\t6");
				}
			}
		}
		class MyListener24 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t2\t7");
				}else{
					send("2\t2\t7");
				}
			}
		}
		class MyListener25 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t3\t0");
				}else{
					send("2\t3\t0");
				}
			}
		}
		class MyListener26 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t3\t1");
				}else{
					send("2\t3\t1");
				}
			}
		}
		class MyListener27 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t3\t2");
				}else{
					send("2\t3\t2");
				}
			}
		}
		class MyListener28 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t3\t3");
				}else{
					send("2\t3\t3");
				}
			}
		}
		class MyListener29 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t3\t4");
				}else{
					send("2\t3\t4");
				}
			}
		}
		class MyListener30 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t3\t5");
				}else{
					send("2\t3\t5");
				}
			}
		}
		class MyListener31 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t3\t6");
				}else{
					send("2\t3\t6");
				}
			}
		}
		class MyListener32 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t3\t7");
				}else{
					send("2\t3\t7");
				}
			}
		}
		class MyListener33 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t4\t0");
				}else{
					send("2\t4\t0");
				}
			}
		}
		class MyListener34 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t4\t1");
				}else{
					send("2\t4\t1");
				}
			}
		}
		class MyListener35 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t4\t2");
				}else{
					send("2\t4\t2");
				}
			}
		}
		class MyListener36 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t4\t3");
				}else{
					send("2\t4\t3");
				}
			}
		}
		class MyListener37 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t4\t4");
				}else{
					send("2\t4\t4");
				}
			}
		}
		class MyListener38 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t4\t5");
				}else{
					send("2\t4\t5");
				}
			}
		}
		class MyListener39 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t4\t6");
				}else{
					send("2\t4\t6");
				}
			}
		}
		class MyListener40 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t4\t7");
				}else{
					send("2\t4\t7");
				}
			}
		}
		class MyListener41 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t5\t0");
				}else{
					send("2\t5\t0");
				}
			}
		}
		class MyListener42 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t5\t1");
				}else{
					send("2\t5\t1");
				}
			}
		}
		class MyListener43 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t5\t2");
				}else{
					send("2\t5\t2");
				}
			}
		}
		class MyListener44 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t5\t3");
				}else{
					send("2\t5\t3");
				}
			}
		}
		class MyListener45 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t5\t4");
				}else{
					send("2\t5\t4");
				}
			}
		}
		class MyListener46 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t5\t5");
				}else{
					send("2\t5\t5");
				}
			}
		}
		class MyListener47 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t5\t6");
				}else{
					send("2\t5\t6");
				}
			}
		}
		class MyListener48 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t5\t7");
				}else{
					send("2\t5\t7");
				}
			}
		}
		class MyListener49 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t6\t0");
				}else{
					send("2\t6\t0");
				}
			}
		}
		class MyListener50 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t6\t1");
				}else{
					send("2\t6\t1");
				}
			}
		}
		class MyListener51 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t6\t2");
				}else{
					send("2\t6\t2");
				}
			}
		}
		class MyListener52 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t6\t3");
				}else{
					send("2\t6\t3");
				}
			}
		}
		class MyListener53 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t6\t4");
				}else{
					send("2\t6\t4");
				}
			}
		}
		class MyListener54 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t6\t5");
				}else{
					send("2\t6\t5");
				}
			}
		}
		class MyListener55 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t6\t6");
				}else{
					send("2\t6\t6");
				}
			}
		}
		class MyListener56 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t6\t7");
				}else{
					send("2\t6\t7");
				}
			}
		}
		class MyListener57 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t7\t0");
				}else{
					send("2\t7\t0");
				}
			}
		}
		class MyListener58 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t7\t1");
				}else{
					send("2\t7\t1");
				}
			}
		}
		class MyListener59 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t7\t2");
				}else{
					send("2\t7\t2");
				}
			}
		}
		class MyListener60 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t7\t3");
				}else{
					send("2\t7\t3");
				}
			}
		}
		class MyListener61 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t7\t4");
				}else{
					send("2\t7\t4");
				}
			}
		}
		class MyListener62 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t7\t5");
				}else{
					send("2\t7\t5");
				}
			}
		}
		class MyListener63 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t7\t6");
				}else{
					send("2\t7\t6");
				}
			}
		}
		class MyListener64 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(w_or_b==1){
					send("1\t7\t7");
				}else{
					send("2\t7\t7");
				}
			}
		}
	}

}
