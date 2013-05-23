import java.util.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;

public class StartChess extends MouseAdapter implements ActionListener
{

	JFrame mainWindow;//������
	JPanel jpNorth, jpSouth, jpCenter, jpEast, jpWest;
	JPanel numberPane, readyPane;
	JSplitPane jsBase, jsLeft; 
	JLabel turnLabel, blackNumberLabel, whiteNumberLabel, timeLabel, idLabel, noticeLabel;
	JTextArea jt2;
	JTextField jt1;
	JScrollPane js;
	JButton send, cancel, regret;
	JButton submit, begin, save;
	
	JRadioButton fighter, audience, AIPlayer;
	ButtonGroup group;
	JMenuBar jmb;
	JMenu document, edit, insert, help;
	
	ChessBoard[][] cell = new ChessBoard[8][8];
	ArrayList<ChessBoard> list = new ArrayList<ChessBoard>();
	int black = 2, white = 2, time;
	char turn = '��';
	boolean can;
	boolean canPut = false;
	boolean gameStart = false, controlThread = true, controlFirst = true;
	boolean isConnected = false;
	
	String kind = "";
	String myRole = "";
	String serverIP;
	
	Socket socket66;
	ObjectOutputStream out66 = null;
	ObjectInputStream in66 = null;
	Socket socket99;
	ObjectOutputStream out99 = null;
	ObjectInputStream in99 = null;
	Socket socket88;
	DataOutputStream out88 = null;
	
	ArrayList<char[][]> stateList = new ArrayList<char[][]>();
	ArrayList<boolean[][]> takenList = new ArrayList<boolean[][]>();
	
	File file;
	ObjectOutputStream out = null;
	FileOutputStream fout = null;
	
	public StartChess(String myr, String IP)
	{
		myRole = myr;
		serverIP = IP;
	}
	
	public void launchChess()
	{
		
		mainWindow = new JFrame("����ڰ���	���ߣ����");
		jpWest = new JPanel();
		jpEast = new JPanel();
		jpNorth = new JPanel();
		jpSouth = new JPanel();
		jpCenter = new JPanel();
		
		turnLabel = new JLabel();
		blackNumberLabel = new JLabel("���壺\n" + black + "		");
		whiteNumberLabel = new JLabel("���壺\n" + white + "		");
		timeLabel = new JLabel("ʱ�䣺  " + time + "		s");
		noticeLabel = new JLabel();
		
		noticeLabel = new JLabel("��ѡ��");
		numberPane = new JPanel();
		readyPane = new JPanel();
		jt1 = new JTextField(18);//������Ϣ��
		jt2 = new JTextArea(3, 30);//��ʾ��Ϣ��
		js = new JScrollPane(jt2);
		jt2.setLineWrap(true);
		jt2.setEditable(false);
//		jt1.setLineWrap(true);
		
		send = new JButton("����");
		cancel = new JButton("ȡ��");
		regret = new JButton("����");
		submit = new JButton("�˳�");
		begin = new JButton("��ʼ");
		save = new JButton("����");
		
		save.setEnabled(true);
		begin.setEnabled(true);
		
		fighter = new JRadioButton("����");
		audience = new JRadioButton("�ۿ�");
		group = new ButtonGroup();
		group.add(audience);
		group.add(fighter);
		group.add(AIPlayer);
		
		submit.addActionListener(this);
		begin.addActionListener(this);
		save.addActionListener(this);
		send.addActionListener(this);
		cancel.addActionListener(this);
		regret.addActionListener(this);
		fighter.addActionListener(this);
		audience.addActionListener(this);
		
		jpNorth.setLayout(new BorderLayout());
		jpNorth.add(turnLabel, BorderLayout.NORTH);
		jpNorth.add(jpCenter, BorderLayout.CENTER);
		jpSouth.add(js);
		jpSouth.add(jt1);
		jpSouth.add(send);
		jpSouth.add(cancel);
		jpEast.setLayout(new GridLayout(3, 1));
		submit.setBackground(new Color(130, 251, 241));
		panel x = new panel();
		jpEast.add(x);
		jpEast.add(numberPane);
		jpEast.add(readyPane);
		
		numberPane.add(blackNumberLabel);
		numberPane.add(whiteNumberLabel);
		numberPane.add(timeLabel);
		numberPane.add(submit);
		numberPane.add(begin);
		numberPane.add(save);
		numberPane.add(regret);
		
		readyPane.add(noticeLabel);
		readyPane.add(fighter);
		readyPane.add(audience);
//		readyPane.add(save);
//		readyPane.add(regret);
		
		jpCenter.setSize(400, 400);
		jmb = new JMenuBar();
		document = new JMenu("��Ϸ		");
		edit = new JMenu("����		");
		insert = new JMenu("����			");
		help = new JMenu("����		");
		jmb.add(document);
		jmb.add(edit);
		jmb.add(insert);
		jmb.add(help);
		//mainWindow.setJMenuBar(jmb);
		
		mainWindow.setBounds(80, 80, 600, 600);
		mainWindow.setResizable(false);
		
		jsLeft = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, jpNorth, jpSouth);
		jsBase = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, jsLeft, jpEast);
		
		mainWindow.add(jsBase);
		mainWindow.setVisible(true);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		jsBase.setDividerLocation(0.7);
		jsLeft.setDividerLocation(0.78);
		
		jpCenter.setLayout(new GridLayout(8, 8, 0, 0));
		ImageIcon key = new ImageIcon("chess1.jpg");
		for (int i = 0; i < cell.length; i++)
			for (int j = 0; j < cell.length; j++)
			{
				cell[i][j] = new ChessBoard(i, j);
				cell[i][j].addMouseListener(this);
				jpCenter.add(cell[i][j]);
			}
		
		cell[3][3].state = cell[4][4].state = '��';
		cell[4][3].state = cell[3][4].state = '��';
		cell[3][3].taken = cell[4][4].taken = true;
		cell[4][3].taken = cell[3][4].taken = true;

		RememberState();
		
		new Thread(new TurnLabel(this)).start();
		
		file = new File("D:/" + myRole);
		try
		{
			fout=new FileOutputStream(file);
			out = new ObjectOutputStream(fout);
		} catch(IOException e1)
		{
			e1.printStackTrace();
		}
		
		Connect();
		
		try
		{
			out99 = new ObjectOutputStream(socket99.getOutputStream());
			in99 = new ObjectInputStream(socket99.getInputStream());
			
			out66 = new ObjectOutputStream(socket66.getOutputStream());
			in66 = new ObjectInputStream(socket66.getInputStream());
			
			out88 = new DataOutputStream(socket88.getOutputStream());
		} catch(IOException e)
		{
			e.printStackTrace();
		}
		
		new Thread(new Client9999(this)).start();
		new Thread(new Client6666(this)).start();
	}
	
	public class panel extends JPanel
	{
		public void paintComponent(Graphics page)
		{
			super.paintComponent(page);
			
			Image icon = this.getToolkit().getImage("chess1.jpg");
			page.drawImage(icon, -40, 20, this);
			
			setOpaque(false);
		}
	}

	public void addScore()
	{
		try
		{
			out88.writeUTF("�ӷ�");
			out88.flush();
			out88.writeUTF("");
		} catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void cutScore(String whom)
	{
		try
		{
			out88.writeUTF("����");
			out88.flush();
			out88.writeUTF("");
		} catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void Connect()
	{
		try
		{
System.out.println("ok");
			socket99 = new Socket(serverIP, 9999);
			socket66 = new Socket(serverIP, 6666);
			socket88 = new Socket(serverIP, 8888);
			isConnected = true;
		} catch(UnknownHostException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getActionCommand().equals("����"))
		{
			audience.setEnabled(false);
			fighter.setEnabled(false);
			begin.setEnabled(true);
//			JOptionPane.showMessageDialog(null, "����");
System.out.println("����");
			try
			{
System.out.println("�ͻ��˷�������ָ��");
				out66.writeObject("����");
				out66.flush();
				out66.writeObject(new char[0][0]);
				out66.flush();
				out66.writeObject(new boolean[0][0]);
				out66.flush();
			} catch(IOException e1)
			{
				e1.printStackTrace();
			}
		}
		if (e.getActionCommand().equals("�ۿ�"))
		{
			submit.setEnabled(false);
			regret.setEnabled(false);
			audience.setEnabled(false);
			fighter.setEnabled(false);
//			JOptionPane.showMessageDialog(null, "�ۿ�");
System.out.println("�ۿ�");
			try
			{
				out66.writeObject("����");
				out66.flush();
				out66.writeObject(stateList.get(stateList.size() - 1));
				out66.flush();
				out66.writeObject(takenList.get(takenList.size() - 1));
				out66.flush();
			} catch(IOException e1)
			{
				e1.printStackTrace();
			}
		}
		/*if (e.getActionCommand().equals("�˻�����"))
		{
			audience.setEnabled(false);
			fighter.setEnabled(false);
			AIPlayer.setEnabled(false);
			begin.setEnabled(true);
			JOptionPane.showMessageDialog(null, "�˻�����");
		}*/
		if (e.getActionCommand().equals("����"))
		{
//			JOptionPane.showMessageDialog(null, "����");
System.out.println("����");
			String str = myRole + ": " + " " + jt1.getText() + "\n";
			try
			{
				out99.writeObject(" " + str);
				out99.flush();
				out99.writeObject(new char[8][8]);
				out99.flush();
				out99.writeObject(new boolean[8][8]);
				out99.flush();
			} catch(IOException e1)
			{
				e1.printStackTrace();
			}
			jt2.append(str);
			jt1.setText("");
		}
		if (e.getActionCommand().equals("ȡ��"))
		{
//			JOptionPane.showMessageDialog(null, "ȡ��");
System.out.println("ȡ��");
			jt1.setText("");
		}
		if (e.getActionCommand().equals("����"))
		{
//			JOptionPane.showMessageDialog(null, "����");
System.out.println("����");
			try
			{
				out66.writeObject("�������");
				out66.flush();
				out66.writeObject(new char[8][8]);
				out66.flush();
				out66.writeObject(new boolean[8][8]);
				out66.flush();
			}catch(IOException e1)
			{
				e1.printStackTrace();
			}
			//RegretChess();
			//ShowChessNumber();
		}
		if (e.getActionCommand().equals("�˳�"))
		{
			int quit = JOptionPane.showConfirmDialog(null, "��ȷ��Ҫǿ���˳���", "��ȷ������ѡ��", JOptionPane.YES_NO_OPTION);
			if (quit == JOptionPane.YES_OPTION)
			{
				JOptionPane.showMessageDialog(null, "��ǿ���˳�");
				System.exit(0);
			}
			else return;
		}
		if (e.getActionCommand().equals("��ʼ"))
		{
			begin.setEnabled(false);
System.out.println("�ͻ��˷��Ϳ�ʼָ��");
//			JOptionPane.showMessageDialog(null, "��ʼ");
			try
			{
				out66.writeObject("����ʼ");
				out66.flush();
				out66.writeObject(stateList.get(stateList.size() - 1));
				out66.flush();
				out66.writeObject(takenList.get(takenList.size() - 1));
				out66.flush();
			} catch(IOException e1)
			{
				e1.printStackTrace();
			}
			Begin();
			if (kind == "��")
			{
				for (int i = 0; i < 8; i++)
				for (int j = 0; j < 8; j++)
				if (cell[i][j].taken == false)
				{
					CheckPlace(cell[i][j]);
					if (canPut) 
					{
						cell[i][j].ChangeBackground();
						canPut = false;
					}
				}
			}
		}
		if (e.getActionCommand().equals("����"))
		{
//			JOptionPane.showMessageDialog(null, "����");
System.out.println("����");
			try
			{
System.out.println();
				out.writeObject(stateList);
				out.flush();
				out.close();
			} catch(IOException e1)
			{
				e1.printStackTrace();
			}
		}
	}
	
	public void RememberState()//��¼����
	{
		char[][] states = new char[8][8];
		boolean[][] takens = new boolean[8][8];
		
		for (int i = 0; i < 8; i++)
		for (int j = 0; j < 8; j++)
		{
			states[i][j] = cell[i][j].state;
			takens[i][j] = cell[i][j].taken;
		}
		stateList.add(states);
		takenList.add(takens);
	}
	
	public void RegretChess()
	{
		for (int i = 0; i < 8; i++)
		for (int j = 0; j < 8; j++)
		if (cell[i][j].changed == true)
		{
			cell[i][j].ChangeBack();
		}
		turn = TakeTurn();
		try
		{
			stateList.remove(stateList.size() - 1);
			takenList.remove(takenList.size() - 1);
			
			char[][] states = stateList.get(stateList.size() - 1);
			boolean[][] takens = takenList.get(takenList.size() - 1);
			for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
			{
				cell[i][j].state = states[i][j];
				cell[i][j].taken = takens[i][j];
				cell[i][j].repaint();
			}
			
			for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
			if (cell[i][j].taken == false)
			{
				CheckPlace(cell[i][j]);
				if (canPut) 
				{
					cell[i][j].ChangeBackground();
					canPut = false;
				}
			}
			
		} catch(ArrayIndexOutOfBoundsException e1)
		{
			JOptionPane.showMessageDialog(null, "�޷�����");
		}
		if (stateList.size() == 0)
		{
			RememberState();
		}
	}
	
	public void CheckAtTheEnd()//�����ӿ���ʱ�ж�ʤ��
	{
		ShowChessNumber();
		gameStart = false;
		
		for (int i = 0; i < 8; i++)
		for (int j = 0; j < 8; j++)
			cell[i][j].taken = true;
		
		String winner = "";
		
		if (white > black)
		{
			controlThread = false;
			JOptionPane.showMessageDialog(null, "�׷�ʤ��" + white + ":" + black);
			winner = "��";
		}
		else if (white < black)
		{
			controlThread = false;
			JOptionPane.showMessageDialog(null, "�ڷ�ʤ!" + black + ":" + white);
			winner = "��";
		}
		else if (white == black)
		{
			controlThread = false;
			JOptionPane.showMessageDialog(null, "�;֣�");
			winner = "";
		}
	//	JOptionPane.showMessageDialog(null, "��Ϸ����!��ʱ" + time + "��");
	}
	
	public boolean CheckAll()//������������Ƿ�������
	{
		for (int i = 0; i < 8; i++)
		for (int j = 0; j < 8; j++)
		{
			if (cell[i][j].taken == false)
			{
				JustCheck(cell[i][j]);
			}
			if (can) break;
		}
		return can;
	}
	
	public void JustCheck(ChessBoard cel)
	{
		int r = cel.getRow();
		int c = cel.getColumn();
		
		if (r - 1 >= 0 && cell[r - 1][c].state != ' ' && cell[r - 1][c].state != turn) 
		{
			for (int i = r - 2; i >= 0; i--) 
			{
				if (cell[i][c].state == turn) 
				{
					can = true;
					return;
				} 
				else 
				{
					if (cell[i][c].state == ' ')
						break;
					else continue;
				}
			}
		}

		if (r + 1 < 8 && cell[r + 1][c].state != ' ' && cell[r + 1][c].state != turn) 
		{
			for (int i = r + 2; i < 8; i++) 
			{
				if (cell[i][c].state == turn) 
				{
					can = true;
					return;
				} 
				else {
					if (cell[i][c].state == ' ')
						break;
					else continue;
				}
			}
		}

		if (c - 1 >= 0 && r - 1 >= 0 && cell[r - 1][c - 1].state != ' ' && cell[r - 1][c - 1].state != turn) 
		{
			for (int i = 2; (r - i) >= 0 && (c - i) >= 0; i++) 
			{
				if (cell[r - i][c - i].state == turn) 
				{
					can = true;
					return;
				} 
				else 
				{
					if (cell[r - i][c - i].state == ' ')
						break;
					else continue;
				}
			}
		}

		if (r - 1 >= 0 && c + 1 < 8 && cell[r - 1][c + 1].state != ' ' && cell[r - 1][c + 1].state != turn) 
		{
			for (int i = 2; (r - 2) >= 0 && (c + i) < 8; i++) 
			{
				if (r - i >= 0 && c + i < 8 && cell[r - i][c + i].state == turn) 
				{
					can = true;
					return;
				} 
				else 
				{
					if (r - i >= 0 && c + i < 8 && cell[r - i][c + i].state == ' ')
						break;
					else continue;
				}
			}
		}

		if (r + 1 < 8 && c - 1 >= 0 && cell[r + 1][c - 1].state != ' ' && cell[r + 1][c - 1].state != turn) 
		{
			for (int i = 2; (r + i) < 8 && (c - i) >= 0; i++) 
			{
				if (r + i < 8 && c - i >= 0 && cell[r + i][c - i].state == turn) 
				{
					can = true;
					return;
				} 
				else 
				{
					if (cell[r + i][c - i].state == ' ')
						break;
					else continue;
				}
			}
		}

		if (r + 1 < 8 && c + 1 < 8 && cell[r + 1][c + 1].state != ' ' && cell[r + 1][c + 1].state != turn) 
		{
			for (int i = 2; (i + r) < 8 && (i + c) < 8; i++) 
			{
				if (r + i < 8 && c + i < 8 && cell[i + r][c + i].state == turn) 
				{
					can = true;
					return;
				} 
				else 
				{
					if (cell[r + i][c + i].state == ' ')
						break;
					else continue;
				}
			}
		}

		if (c - 1 >= 0 && cell[r][c - 1].state != ' ' && cell[r][c - 1].state != turn) 
		{
			for (int i = c - 2; i >= 0; i--) 
			{
				if (cell[r][i].state == turn) 
				{
					can = true;
					return;
				} 
				else 
				{
					if (cell[r][i].state == ' ') break;
					else continue;
				}
			}
		}

		if (c + 1 < 8 && cell[r][c + 1].state != ' ' && cell[r][c + 1].state != turn) 
		{
			for (int i = c + 2; i < 8; i++) 
			{
				if (cell[r][i].state == turn) 
				{
					can = true;
					return;
				} 
				else 
				{
					if (cell[r][i].state == ' ')
						break;
					else continue;
				}
			}
		}
	}
	
	public void CheckPlace(ChessBoard cel)
	{
		int r = cel.getRow();
		int c = cel.getColumn();
		if (r - 1 >= 0 && cell[r - 1][c].state != ' ' && cell[r - 1][c].state != turn) 
		{
			for (int i = r - 2; i >= 0; i--) 
			{
				if (cell[i][c].state == turn) 
				{
					canPut = true;
					return;
				} 
				else 
				{
					if (cell[i][c].state == ' ')
						break;
					else continue;
				}
			}
		}
		
		if (r + 1 < 8 && cell[r + 1][c].state != ' ' && cell[r + 1][c].state != turn) 
		{
			for (int i = r + 2; i < 8; i++) 
			{
				if (cell[i][c].state == turn) 
				{
					canPut = true;
					return;
				} 
				else 
				{
					if (cell[i][c].state == ' ')
						break;
					else continue;
				}
			}
		}
		
		if (c - 1 >= 0 && cell[r][c - 1].state != ' ' && cell[r][c - 1].state != turn) 
		{
			for (int i = c - 2; i >= 0; i--) 
			{
				if (cell[r][i].state == turn) 
				{
					canPut = true;
					return;
				} 
				else 
				{
					if (cell[r][i].state == ' ')
						break;
					else continue;
				}
			}
		}
		
		if (c + 1 < 8 && cell[r][c + 1].state != ' ' && cell[r][c + 1].state != turn) 
		{
			for (int i = c + 2; i < 8; i++) 
			{
				if (cell[r][i].state == turn) 
				{
					canPut = true;
					return;
				} 
				else 
				{
					if (cell[r][i].state == ' ')
						break;
					else continue;
				}
			}
		}
		
		if (c - 1 >= 0 && r - 1 >= 0 && cell[r - 1][c - 1].state != ' ' && cell[r - 1][c - 1].state != turn) 
		{
			for (int i = 2; (r - i) >= 0 && (c - i) >= 0; i++) 
			{
				if (cell[r - i][c - i].state == turn) 
				{
					canPut = true;
					return;
				} 
				else 
				{
					if (cell[r - i][c - i].state == ' ')
						break;
					else continue;
				}
			}
		}
		
		if (r + 1 < 8 && c - 1 >= 0 && cell[r + 1][c - 1].state != ' ' && cell[r + 1][c - 1].state != turn) 
		{
			for (int i = 2; (r + i) < 8 && (c - i) >= 0; i++) 
			{
				if (r + i < 8 && c - i >= 0 && cell[r + i][c - i].state == turn) 
				{
					canPut = true;
					return;
				} 
				else 
				{
					if (cell[r + i][c - i].state == ' ')
						break;
					else continue;
				}
			}
		}
		
		if (r - 1 >= 0 && c + 1 < 8 && cell[r - 1][c + 1].state != ' ' && cell[r - 1][c + 1].state != turn) 
		{
			for (int i = 2; (r - 2) >= 0 && (c + i) < 8; i++) 
			{
				if (r - i >= 0 && c + i < 8 && cell[r - i][c + i].state == turn) 
				{
					canPut = true;
					return;
				} 
				else 
				{
					if (r - i >= 0 && c + i < 8
							&& cell[r - i][c + i].state == ' ')
						break;
					else continue;
				}
			}
		}
		
		if (r + 1 < 8 && c + 1 < 8 && cell[r + 1][c + 1].state != ' ' && cell[r + 1][c + 1].state != turn) 
		{
			for (int i = 2; (i + r) < 8 && (i + c) < 8; i++) 
			{
				if (r + i < 8 && c + i < 8 && cell[i + r][c + i].state == turn) 
				{
					canPut = true;
					return;
				} 
				else 
				{
					if (cell[r + i][c + i].state == ' ')
						break;
					else continue;
				}
			}
		}
		
	}
	
	public void Check(ChessBoard cel)//����Ƿ��������
	{
		CheckUp(cel);
		CheckDown(cel);
		CheckRight(cel);
		CheckLeft(cel);
		CheckLeftUp(cel);
		CheckLeftDown(cel);
		CheckRightUp(cel);
		CheckRightDown(cel);
	}
	
	public void CheckUp(ChessBoard cel)
	{
		int r = cel.getRow();
		int c = cel.getColumn();
		if (r - 1 >= 0 && cell[r - 1][c].state != ' ' && cell[r - 1][c].state != turn) 
		{
			for (int i = r - 2; i >= 0; i--) 
			{
				if (cell[i][c].state == turn) 
				{
					can = true;
					for (int j = r; j > i; j--) 
					{
						cell[j][c].state = cel.state = turn;
						cell[j][c].repaint();
					}
					return;
				} 
				else 
				{
					if (cell[i][c].state == ' ')
						break;
					else continue;
				}
			}
		}
	}
	
	public void CheckDown(ChessBoard cel)
	{
		int r = cel.getRow();
		int c = cel.getColumn();
		if (r + 1 < 8 && cell[r + 1][c].state != ' ' && cell[r + 1][c].state != turn) 
		{
			for (int i = r + 2; i < 8; i++) 
			{
				if (cell[i][c].state == turn) 
				{
					can = true;
					for (int j = r; j < i; j++) 
					{
						cell[j][c].state = cel.state = turn;
						cell[j][c].repaint();
					}
					return;
				} 
				else 
				{
					if (cell[i][c].state == ' ')
						break;
					else continue;
				}
			}
		}
	}
	
	public void CheckLeft(ChessBoard cel)
	{
		int r = cel.getRow();
		int c = cel.getColumn();
		if (c - 1 >= 0 && cell[r][c - 1].state != ' ' && cell[r][c - 1].state != turn) 
		{
			for (int i = c - 2; i >= 0; i--) 
			{
				if (cell[r][i].state == turn) 
				{
					can = true;
					for (int j = i + 1; j <= c; j++) 
					{
						cell[r][j].state = cel.state = turn;
						cell[r][j].repaint();
					}
					return;
				} 
				else 
				{
					if (cell[r][i].state == ' ')
						break;
					else continue;
				}
			}
		}
	}
	
	public void CheckRight(ChessBoard cel)
	{
		int r = cel.getRow();
		int c = cel.getColumn();
		if (c + 1 < 8 && cell[r][c + 1].state != ' ' && cell[r][c + 1].state != turn) 
		{
			for (int i = c + 2; i < 8; i++) 
			{
				if (cell[r][i].state == turn) 
				{
					can = true;
					for (int j = c; j < i; j++) 
					{
						cell[r][j].state = cel.state = turn;
						cell[r][j].repaint();
					}
					return;
				} 
				else 
				{
					if (cell[r][i].state == ' ')
						break;
					else continue;
				}
			}
		}
	}
	
	public void CheckLeftUp(ChessBoard cel)
	{
		int r = cel.getRow();
		int c = cel.getColumn();
		if (c - 1 >= 0 && r - 1 >= 0 && cell[r - 1][c - 1].state != ' ' && cell[r - 1][c - 1].state != turn) 
		{
			for (int i = 2; (r - i) >= 0 && (c - i) >= 0; i++) 
			{
				if (cell[r - i][c - i].state == turn) 
				{
					can = true;
					for (int j = 0; j < i; j++) 
					{
						cell[r - j][c - j].state = cel.state = turn;
						cell[r - j][c - j].repaint();
					}
					return;
				} 
				else 
				{
					if (cell[r - i][c - i].state == ' ')
						break;
					else continue;
				}
			}
		}
	}
	
	public void CheckLeftDown(ChessBoard cel)
	{
		int r = cel.getRow();
		int c = cel.getColumn();
		if (r + 1 < 8 && c - 1 >= 0 && cell[r + 1][c - 1].state != ' ' && cell[r + 1][c - 1].state != turn) 
		{
			for (int i = 2; (r + i) < 8 && (c - i) >= 0; i++) 
			{
				if (r + i < 8 && c - i >= 0 && cell[r + i][c - i].state == turn) 
				{
					can = true;
					for (int j = 0; j < i; j++) 
					{
						cell[r + j][c - j].state = cel.state = turn;
						cell[r + j][c - j].repaint();
					}
					return;
				} 
				else 
				{
					if (cell[r + i][c - i].state == ' ')
						break;
					else continue;
				}
			}
		}
	}
	
	public void CheckRightUp(ChessBoard cel)
	{
		int r = cel.getRow();
		int c = cel.getColumn();
		if (r - 1 >= 0 && c + 1 < 8 && cell[r - 1][c + 1].state != ' ' && cell[r - 1][c + 1].state != turn) 
		{
			for (int i = 2; (r - 2) >= 0 && (c + i) < 8; i++) 
			{
				if (r - i >= 0 && c + i < 8 && cell[r - i][c + i].state == turn) 
				{
					can = true;
					for (int j = 0; j < i; j++) 
					{
						cell[r - j][c + j].state = cel.state = turn;
						cell[r - j][c + j].repaint();
					}
					return;
				} 
				else 
				{
					if (r - i >= 0 && c + i < 8
							&& cell[r - i][c + i].state == ' ')
						break;
					else continue;
				}
			}
		}
	}
	
	public void CheckRightDown(ChessBoard cel)
	{
		int r = cel.getRow();
		int c = cel.getColumn();
		if (r + 1 < 8 && c + 1 < 8 && cell[r + 1][c + 1].state != ' ' && cell[r + 1][c + 1].state != turn) 
		{
			for (int i = 2; (i + r) < 8 && (i + c) < 8; i++) 
			{
				if (r + i < 8 && c + i < 8 && cell[i + r][c + i].state == turn) 
				{
					can = true;
					for (int j = 0; j < i; j++) 
					{
						cell[r + j][c + j].state = cel.state = turn;
						cell[r + j][c + j].repaint();
					}
					return;
				} 
				else 
				{
					if (cell[r + i][c + i].state == ' ')
						break;
					else continue;
				}
			}
		}
	}
	
	public void Begin()//��ʼ����
	{
		gameStart = true;
		begin.setEnabled(false);
		new Thread(new TimeCounter(this)).start();
		/*for (int i = 0; i < 8; i++)
		for (int j = 0; j < 8; j++)
		if (cell[i][j].taken == false)
		{
			CheckPlace(cell[i][j]);
			if (canPut) 
			{
				cell[i][j].ChangeBackground();
				canPut = false;
			}
		}*/
	}
	
	public char TakeTurn()//��������˳��
	{
		if (turn == '��')
			return '��';
		else return '��';
	}

	public void JudgeWhoIsWinner()//�ж�ʤ��
	{
		String winner = "";
		
		if (white == 0)
		{
			JOptionPane.showMessageDialog(null, "�ڷ�ʤ��" + black + ":" + white);
//			JOptionPane.showMessageDialog(null, "��Ϸ��������ʱ" + time + "��");
			submit.setEnabled(false);
			winner = "��";
		}
		if (black == 0)
		{
			JOptionPane.showMessageDialog(null, "�׷�ʤ��" + white + ":" + black);
//			JOptionPane.showMessageDialog(null, "��Ϸ��������ʱ" + time + "��");
			submit.setEnabled(false);
			winner = "��";
		}
		if (black + white == 64)
		{
			if (white > black)
			{
				JOptionPane.showMessageDialog(null, "�׷�ʤ��" + white + ":" + black);
//				JOptionPane.showMessageDialog(null, "��Ϸ��������ʱ" + time + "��");
				submit.setEnabled(false);
				winner = "��";
			}
			else if (black > white)
			{
				JOptionPane.showMessageDialog(null, "�ڷ�ʤ��" + black + ":" + white);
//				JOptionPane.showMessageDialog(null, "��Ϸ��������ʱ" + time + "��");
				submit.setEnabled(false);
				winner = "��";
			}
			else if (black == white)
			{
				JOptionPane.showMessageDialog(null, "�;֣�");
//				JOptionPane.showMessageDialog(null, "��Ϸ����!��ʱ" + time + "��");
				winner = "";
			}
		}
	}

	public void Count()//�Ժ��Ӱ��Ӽ���
	{
		for (int i = 0; i < cell.length; i++)
		for (int j = 0; j < cell.length; j++)
		{
			if (cell[i][j].state == '��')
			{
				white++;
			}
			if (cell[i][j].state == '��')
			{
				black++;
			}
		}
	}
	
	public void ShowChessNumber()//��ʾ���Ӱ�����Ŀ
	{
		white = 0;
		black = 0;
		Count();
		blackNumberLabel.setText("��������" + black + "		");
		whiteNumberLabel.setText("��������" + white + "		");
	}
	
	public int Clicked(ChessBoard cel)
	{	
		int judge = 0;
		int r = cel.getRow();
		int c = cel.getColumn();
System.out.println("����ǰ��" + cell[3][5].taken);
		
		if (gameStart)
		{	
			if (cel.taken == false)
			{
				Check(cel);
			}
System.out.println(cell[3][5].taken);
System.out.println(can);
			if (can && cel.taken == false)
			{
				/*for (int i = 0; i < 8; i++)
				for (int j = 0; j < 8; j++)
				if (cell[i][j].changed == true)
				{
					cell[i][j].ChangeBack();
				}*/
				RememberState();
				ShowChessNumber();
				list.add(cel);
				JudgeWhoIsWinner();
				turn = TakeTurn();
				cel.taken = true;
System.out.println("���Ӻ�" + cell[3][5].taken);
				can = false;
				judge = 1;
				for (int i = 0; i < 8; i++)
				for (int j = 0; j < 8; j++)
				if (cell[i][j].changed)
				{
					cell[i][j].ChangeBack();
				}
				
				boolean flag = CheckAll();
				if (!flag && white + black < 64) CheckAtTheEnd();
				/*else
				{
					for (int i = 0; i < 8; i++)
					for (int j = 0; j < 8; j++)
					if (cell[i][j].taken == false)
					{
						CheckPlace(cell[i][j]);
						if (canPut) 
						{
							cell[i][j].ChangeBackground();
							canPut = false;
						}
					}
				}*/
			}
			else
			{
				JOptionPane.showMessageDialog(null, "�޷��ڸ�λ������");
				judge = 0;
				System.out.println(cell[3][5].taken);
			}
			return judge;
		}
		else
		{
			JOptionPane.showMessageDialog(null, "��Ϸ��δ��ʼ���ѽ���");
			return 0;
		}
	}
	
	public void mouseClicked(MouseEvent e)
	{
		/*can = false;
		boolean f = true;
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			if (cell[i][j] == e.getSource())
			{
				int judege = Clicked(cell[i][j]);
				f = false;
				break;
			}
			if (!f) break;
		}*/
		
		boolean flage = CheckAll();
		if (flage)
		{
			can = false;
			if (kind.equals("" + turn))
			{
				ChessBoard cel = (ChessBoard)(e.getSource());
				int judge = Clicked(cel);
				if (judge == 1)
				{
					try
					{
System.out.println("����ǰ��" + cell[3][5].taken);
						out66.writeObject("����" + turn);
						out66.flush();
						out66.writeObject(stateList.get(stateList.size() - 1));
						out66.flush();
						out66.writeObject(takenList.get(takenList.size() - 1));
						out66.flush();
					} catch(IOException e1)
					{
						e1.printStackTrace();
					}
				}
			}else
			{
				JOptionPane.showMessageDialog(null, "��ȷ��������ݣ�����ʱ��������");
			}
		}
		else CheckAtTheEnd();
	}
}
