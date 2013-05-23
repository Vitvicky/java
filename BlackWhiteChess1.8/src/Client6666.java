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
				
				if (strMessage.contains("请求开始"))
				{
					int option = JOptionPane.showConfirmDialog(null, "对方请求开始下棋，你同意吗？", "开始协议", JOptionPane.YES_NO_OPTION);
					if (option == JOptionPane.YES_OPTION)
					{
						start.Begin();
						if (start.kind == "黑")
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
							start.out66.writeObject("允许开始" + start.turn);
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
				
				if (strMessage.contains("请求悔棋"))
				{
					int option = JOptionPane.showConfirmDialog(null, "对方请求悔棋，你同意吗？", "开始协议", JOptionPane.YES_NO_OPTION);
					if (option == JOptionPane.YES_OPTION)
					{
						start.RegretChess();
						start.ShowChessNumber();
						try
						{
							start.out66.writeObject("允许悔棋" + start.turn);
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
				
				if (strMessage.contains("允许悔棋"))
				{
					JOptionPane.showMessageDialog(null, "对方同意悔棋");
					start.RegretChess();
					start.ShowChessNumber();
				}
				
				if (strMessage.contains("落子"))
				{
					start.turn = start.TakeTurn();
					for (int i = 0; i < 8; i++)
					for (int j = 0; j < 8; j++)
					{
						start.cell[i][j].state = message1[i][j];
						//start.cell[i][j].taken = message2[i][j];
						start.cell[i][j].repaint();
					}
//System.out.println("客户端接收后：" + start.cell[3][5].taken);
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
				
				if (strMessage.equals("提前结束"))
				{
					start.gameStart = false;
					JOptionPane.showMessageDialog(null, "对方已经退出");
					start.submit.setEnabled(false);
					try
					{
						start.out66.writeObject(start.myRole + "战胜");
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
				
				if (strMessage.equals("战胜"))
				{
					start.gameStart = false;
					JOptionPane.showMessageDialog(null, "游戏结束");
				}
				
				if (strMessage.equals("1")) 
				{
					start.kind = "黑";
					start.noticeLabel.setText("你是黑方");
					start.begin.setEnabled(true);
					start.save.setEnabled(true);
				}
				
				if (strMessage.equals("2")) 
				{
					start.kind = "白";
					start.noticeLabel.setText("你是白方");
					start.begin.setEnabled(true);
					start.save.setEnabled(true);
				}
				
				if (strMessage.contains("观众"))
				{
					start.noticeLabel.setText("你是一名观众");
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
				
				if (strMessage.contains("退回"))
				{
					JOptionPane.showMessageDialog(null, "已经有两位对手了，你只能作为观众");
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
