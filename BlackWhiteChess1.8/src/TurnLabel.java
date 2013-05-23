import javax.swing.*;
import java.awt.*;

public class TurnLabel implements Runnable
{
	StartChess start;
	
	public TurnLabel(StartChess s)
	{
		this.start = s;
	}
	
	@Override
	public void run() 
	{
		while (true)
		{
			if (start.gameStart)
			{
				start.turnLabel.setText("��" + start.turn + "����ʼ����");
			}
			else 
			{
				start.turnLabel.setText("�ɺڷ���ʼ����");
			}
		}
	}
	
}
