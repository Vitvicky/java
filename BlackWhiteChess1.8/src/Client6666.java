import java.io.*;
import javax.swing.*;

public class Client6666 implements Runnable 
{
	StartChess start;
	
	public Client6666(StartChess s)
	{
		this.start = s;
	}

	@Override
	public void run() 
	{
		String strMessage;
		char[][] message1;
		boolean[][] message2;
		
		try
		{
			while (start.isConnected)
			{
				strMessage = (String) (start.in66.readObject());
				message1 = (char[][]) (start.in66.readObject());
				message2 = (boolean[][]) (start.in66.readObject());
				
				if (strMessage.contains("����ʼ"))
				{
					int option = JOptionPane.showConfirmDialog(null, "�Է�����ʼ���壬��ͬ����", "��ʼЭ��", JOptionPane.YES_NO_OPTION);
					if (option == JOptionPane.YES_OPTION)
					{
						start.Begin();
						if (start.kind == "��")
						{
							for (int i = 0; i < 8; i++)
							for (int j = 0; j < 8; j++)
							if (start.cell[i][j].taken == false)
							{
								start.CheckPlace(start.cell[i][j]);
								if (start.canPut) 
								{
									start.cell[i][j].ChangeBackground();
									start.canPut = false;
								}
							}
						}
						try
						{
							start.out66.writeObject("����ʼ" + start.turn);
							start.out66.flush();
							start.out66.writeObject(start.stateList.get(start.stateList.size() - 1));
							start.out66.flush();
							start.out66.writeObject(start.takenList.get(start.takenList.size() - 1));
							start.out66.flush();
						} catch(IOException e1)
						{
							e1.printStackTrace();
						}
					}
				}
				
				if (strMessage.contains("�������"))
				{
					int option = JOptionPane.showConfirmDialog(null, "�Է�������壬��ͬ����", "��ʼЭ��", JOptionPane.YES_NO_OPTION);
					if (option == JOptionPane.YES_OPTION)
					{
						start.RegretChess();
						start.ShowChessNumber();
						try
						{
							start.out66.writeObject("�������" + start.turn);
							start.out66.flush();
							start.out66.writeObject(start.stateList.get(start.stateList.size() - 1));
							start.out66.flush();
							start.out66.writeObject(start.takenList.get(start.takenList.size() - 1));
							start.out66.flush();
							for (int i = 0; i < 8; i++)
							for (int j = 0; j < 8; j++)
							if (start.cell[i][j].changed == true)
							{
								start.cell[i][j].ChangeBack();
							}
						} catch(IOException e1)
						{
							e1.printStackTrace();
						}
					}
				}
				
				if (strMessage.contains("�������"))
				{
					JOptionPane.showMessageDialog(null, "�Է�ͬ�����");
					start.RegretChess();
					start.ShowChessNumber();
				}
				
				if (strMessage.contains("����"))
				{
					start.turn = start.TakeTurn();
					for (int i = 0; i < 8; i++)
					for (int j = 0; j < 8; j++)
					{
						start.cell[i][j].state = message1[i][j];
						//start.cell[i][j].taken = message2[i][j];
						start.cell[i][j].repaint();
					}
//System.out.println("�ͻ��˽��պ�" + start.cell[3][5].taken);
					start.RememberState();
					start.ShowChessNumber();
					for (int i = 0; i < 8; i++)
					for (int j = 0; j < 8; j++)
					if (start.cell[i][j].taken == false)
					{
						start.CheckPlace(start.cell[i][j]);
						if (start.canPut) 
						{
							start.cell[i][j].ChangeBackground();
							start.canPut = false;
						}
					}
				}
				
				if (strMessage.equals("��ǰ����"))
				{
					start.gameStart = false;
					JOptionPane.showMessageDialog(null, "�Է��Ѿ��˳�");
					start.submit.setEnabled(false);
					try
					{
						start.out66.writeObject(start.myRole + "սʤ");
						start.out66.flush();
						start.out66.writeObject(new char[8][8]);
						start.out66.flush();
						start.out66.writeObject(new boolean[8][8]);
						start.out66.flush();
					} catch(IOException e1)
					{
						e1.printStackTrace();
					}
				}
				
				if (strMessage.equals("սʤ"))
				{
					start.gameStart = false;
					JOptionPane.showMessageDialog(null, "��Ϸ����");
				}
				
				if (strMessage.equals("1")) 
				{
					start.kind = "��";
					start.noticeLabel.setText("���Ǻڷ�");
					start.begin.setEnabled(true);
					start.save.setEnabled(true);
				}
				
				if (strMessage.equals("2")) 
				{
					start.kind = "��";
					start.noticeLabel.setText("���ǰ׷�");
					start.begin.setEnabled(true);
					start.save.setEnabled(true);
				}
				
				if (strMessage.contains("����"))
				{
					start.noticeLabel.setText("����һ������");
					start.Begin();
					if (strMessage.length() > 2)
						start.turn = strMessage.charAt(2);
					for (int i = 0; i < 8; i++)
					for (int j = 0; j < 8; j++)
					{
						start.cell[i][j].state = message1[i][j];
						start.cell[i][j].taken = message2[i][j];
						start.cell[i][j].repaint();
					}
				}
				start.ShowChessNumber();
				
				if (strMessage.contains("�˻�"))
				{
					JOptionPane.showMessageDialog(null, "�Ѿ�����λ�����ˣ���ֻ����Ϊ����");
					start.audience.setEnabled(true);
					start.regret.setEnabled(false);
					start.Begin();
					if (strMessage.length() > 2)
					{
						start.turn = strMessage.charAt(2);
					}
					for (int i = 0; i < 8; i++)
					for (int j = 0; j < 8; j++)
					{
						start.cell[i][j].state = message1[i][j];
						start.cell[i][j].taken = message2[i][j];
						start.cell[i][j].repaint();
					}
					start.ShowChessNumber();
				}
			}
		} catch(Exception e)
		{
			System.out.println();
		}
	}
	
	
}
