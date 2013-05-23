import java.io.*;
import java.util.*;
import java.net.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class Server9999 
{
	LinkedHashMap<Integer, TransferThread> list = null;
	ArrayList message1List = new ArrayList();
	ArrayList message2List = new ArrayList();
	ArrayList<String> turnList = new ArrayList<String>();
	ServerSocket ss;
	Socket s;
	static int kind = 0;
	
	public void launch()
	{
		list = new LinkedHashMap<Integer, TransferThread>();
		try
		{
			ss = new ServerSocket(9999);
		} catch (BindException e) 
		{
			System.out.println("端口使用中....");
			System.out.println("请关掉相关程序并重新运行服务器！");
			System.exit(0);
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		Integer id = 0;
		
		try
		{
			while (true)
			{
				s = ss.accept();
				System.out.println("连接9999");
				TransferThread transfer = new TransferThread(id);
				list.put(id, transfer);
				new Thread(transfer).start();
				id++;
			}
		} catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private class TransferThread implements Runnable
	{

		int id;
		String role = "";
		boolean isConnected = false;
		ObjectInputStream in = null;
		ObjectOutputStream out = null;
		
		public TransferThread(int id)
		{
			this.id = id;
			isConnected = true;
			
			try
			{
				in = new ObjectInputStream(s.getInputStream());
				out = new ObjectOutputStream(s.getOutputStream());
			} catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		
		public void send(Object message)
		{
			try
			{
				out.writeObject(message);
				out.flush();
			} catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() 
		{
			try
			{
				Object strmessage;
				Object message1 = null;
				Object message2 = null;
				
				while (isConnected)
				{
					strmessage = in.readObject();
					message1 = in.readObject();
					message2 = in.readObject();
					
					if (((String) strmessage).charAt(0) == ' ')
					{
						for (int i = 0; i < list.size(); i++)
						if (i != id && list.get(i) != null)
						{
							list.get(i).send(strmessage);
							list.get(i).send(message1);
							list.get(i).send(message2);
						}
					}
				}
			} catch(Exception e)
			{
				JOptionPane.showMessageDialog(null, "第" + id + "号用户退出");
				list.remove(list.get(id));
				try
				{
					if (out != null) out.close();
					if (in != null) in.close();
					if (s != null) s.close();
				} catch(IOException e1)
				{
					System.out.println();
				}
			}
		}
		
	}
}
