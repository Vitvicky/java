import java.io.*;
import javax.swing.*;

import java.net.*;

public class Client9999 implements Runnable
{
	StartChess start;
	
	public Client9999(StartChess s)
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
			while(start.isConnected)
			{
				strMessage = (String) (start.in99.readObject());
				message1 = (char[][]) (start.in99.readObject());
				message2 = (boolean[][]) (start.in99.readObject());
				
				if (strMessage.charAt(0) == ' ')
				{
					start.jt2.append(strMessage);
				}
			}
		} catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "有一方客户退出");
		}
	}
	
	
}
