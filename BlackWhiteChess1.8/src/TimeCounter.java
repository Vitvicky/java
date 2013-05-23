import javax.swing.*;
import java.awt.*;

public class TimeCounter implements Runnable 
{
	StartChess start;

	int time = 60;
	char temp = '��';
	
	public TimeCounter(StartChess s)
	{
		this.start = s;
	}
	
	@Override
	public void run() 
	{
		while (start.gameStart)
		{
			if (start.turn != temp)
			{
				temp = start.turn;
				time = 60;
			}
			try
			{
				Thread.sleep(1000);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			time--;
			
			start.timeLabel.setText(start.turn + "������" + time + "��������");
			
			if (time < 10)
			{
				start.timeLabel.setText("��ܰ���ѣ�" + start.turn + "����ʣ" + time + "�룡����");
				//start.numberPane.setBackground(Color.red);
			}
			
			if (time == 0)
			{
				start.gameStart = false;
				JOptionPane.showMessageDialog(null, "�ܱ�Ǹ������ʱ�ˣ���Ϸ������");
			}
		}
	}
	
}
