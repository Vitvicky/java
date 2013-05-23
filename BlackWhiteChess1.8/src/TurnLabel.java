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
				start.turnLabel.setText("由" + start.turn + "方开始下棋");
			}
			else 
			{
				start.turnLabel.setText("由黑方开始下棋");
			}
		}
	}
	
}
