import java.util.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;

public class StartChessAgain extends MouseAdapter implements ActionListener
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
	JButton previous, next;
	
	ChessBoard[][] cell = new ChessBoard[8][8];
	ArrayList<ChessBoard> list = new ArrayList<ChessBoard>();
	int black, white, time;
	char turn = '��';
	boolean can;
	boolean canPut = false;
	boolean gameStart = false, controlThread = true, controlFirst = true;
	boolean isConnected = false;
	Thread t;
	ArrayList<char[][]> stateList = new ArrayList<char[][]>();
	ArrayList<boolean[][]> takenList = new ArrayList<boolean[][]>();
	String myRole;
	String file;
	ObjectOutputStream out = null;
	ObjectInputStream in = null;
	FileInputStream fin = null;
	static int number;
	
	public StartChessAgain(String myr)
	{
		myRole = myr;
	}
	
	public void launchChess()
	{
		mainWindow = new JFrame("����ڰ���	���ߣ����");
		jpWest = new JPanel();
		jpEast = new JPanel();
		jpNorth = new JPanel();
		jpSouth = new JPanel();
		jpCenter = new JPanel();
		
		turnLabel = new JLabel("�ڰ��帴��");
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
		previous = new JButton("<<previous");
		next = new JButton(">>next");
		previous.addActionListener(this);
		next.addActionListener(this);
		jpNorth.setLayout(new BorderLayout());
		jpNorth.add(turnLabel, BorderLayout.NORTH);
		jpNorth.add(jpCenter, BorderLayout.CENTER);
		jpSouth.add(previous);
		jpSouth.add(next);
		jpEast.setLayout(new GridLayout(3, 1));
		jpEast.add(numberPane);
		jpEast.add(readyPane);
		
		numberPane.add(blackNumberLabel);
		numberPane.add(whiteNumberLabel);
		numberPane.add(timeLabel);
		jpCenter.setSize(320, 320);
		
		mainWindow.setBounds(80, 80, 600, 600);
		mainWindow.setResizable(false);
		
		jsLeft = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, jpNorth, jpSouth);
		jsBase = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, jsLeft, jpEast);
		mainWindow.add(jsBase);
		mainWindow.setVisible(true);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jsBase.setDividerLocation(0.6);
		jsLeft.setDividerLocation(0.7);
		
		jpCenter.setLayout(new GridLayout(8, 8, 0, 0));
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
System.out.println(myRole);
		file = "D:\\" + myRole;
System.out.println(file);
		try
		{
			fin = new FileInputStream(file);
			in = new ObjectInputStream(fin);
System.out.println("ok");
			stateList = (ArrayList<char[][]>)(in.readObject());
			number = stateList.size() - 1;
		} catch(Exception e1)
		{
			JOptionPane.showMessageDialog(null, "û�����ļ�¼");
			try
			{
				if (fin != null) fin.close();
				if (in != null) in.close();
				System.exit(0);
			} catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		
		for (int i = 0; i < 8; i++)
		for (int j = 0; j < 8; j++)
		{
			cell[i][j].state = stateList.get(number)[i][j];
			cell[i][j].repaint();
		}
		ShowChessNumber();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == previous)
		{
			number--;
			if (number == 0)
			{
				previous.setEnabled(false);
			}
			if (number < stateList.size() - 1)
			{
				next.setEnabled(true);
			}
			for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
			{
				cell[i][j].state = stateList.get(number)[i][j];
				cell[i][j].repaint();
			}
			ShowChessNumber();
		}
		
		if (e.getSource() == next)
		{
			number++;
			if (number == stateList.size() - 1)
			{
				next.setEnabled(false);
			}
			if (number > 0)
			{
				previous.setEnabled(true);
			}
			for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
			{
				cell[i][j].state = stateList.get(number)[i][j];
				cell[i][j].repaint();
			}
			ShowChessNumber();
		}
	}
	
	public char TakeTurn()//��������˳��
	{
		if (turn == '��')
			return '��';
		else return '��';
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
	
	
}
