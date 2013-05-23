import javax.swing.*;
import java.awt.*;

public class TimeCounter implements Runnable 
{
	StartChess start;

	int time = 60;
	char temp = '黑';
	
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
			
			start.timeLabel.setText(start.turn + "方请在" + time + "秒内落子");
			
			if (time < 10)
			{
				start.timeLabel.setText("温馨提醒：" + start.turn + "方仅剩" + time + "秒！！！");
				//start.numberPane.setBackground(Color.red);
			}
			
			if (time == 0)
			{
				start.gameStart = false;
				JOptionPane.showMessageDialog(null, "很抱歉，您超时了！游戏结束！");
			}
		}
	}
	
}
