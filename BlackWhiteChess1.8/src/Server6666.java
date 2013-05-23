import java.io.*;
import java.util.*;
import java.net.*;
import javax.swing.*;

public class Server6666 
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
			ss = new ServerSocket(6666);
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
				System.out.println("连接6666");
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
				Object strmessage = null;
				Object message1 = null;
				Object message2 = null;
				
				while (isConnected)
				{
					strmessage = in.readObject();
					message1 = in.readObject();
					message2 = in.readObject();
					
					if (((String)strmessage).contains("请求开始"))
					{
System.out.println("服务器接收开始指令");
						if (kind < 2)
						{
							send("refuseBegin");
							send(new char[8][8]);
							send(new boolean[8][8]);
						}else
						{
							TransferThread ts = null;//游戏开始
							if (list.get(id).role.equals("1"))
							{
								for (int i = 0; i < list.size(); i++)
								{
									if (list.get(i).role.equals("2"))
									{
										ts = list.get(i);
									}
								}
							}else
							{
								for (int i = 0; i < list.size(); i++)
								{
									if (list.get(i).role.equals("1"))
									{
										ts = list.get(i);
									}
								}
							}
							ts.send(strmessage);
							ts.send(message1);
							ts.send(message2);
						}
					}
					
					if (((String)strmessage).contains("允许开始"))
					{
						message1List.add(message1);
						message2List.add(message2);
						turnList.add("" + ((String)strmessage).charAt(4));
						
						TransferThread ts = null;
						if (list.get(id).role.equals("1"))
						{
							for (int i = 0; i < list.size(); i++)
							{
								if (list.get(i).role.equals("2"))
								{
									ts = list.get(i);
								}
							}
						}else
						{
							for (int i = 0; i < list.size(); i++)
							{
								if (list.get(i).role.equals("1"))
								{
									ts = list.get(i);
								}
							}
						}
						ts.send(strmessage);
						ts.send(message1);
						ts.send(message2);
					}
					
					if (((String)strmessage).contains("落子"))
					{
//System.out.println("接收落子信息");
System.out.println(message2);
						message1List.add(message1);
						message2List.add(message2);					
						turnList.add("" + ((String)strmessage).charAt(2));
						for (int i = 0; i < list.size(); i++) 
						{
							if (i != id && list.get(i) != null)
							{
								list.get(i).send(strmessage);
								list.get(i).send(message1);
								list.get(i).send(message2);
							}
						}
					}
					
					if (((String)strmessage).contains("提前结束"))
					{
						TransferThread ts = null;
						if (list.get(id).role.equals("1"))
						{
							for (int i = 0; i < list.size(); i++)
							{
								if (list.get(i).role.equals("2"))
								{
									ts = list.get(i);
								}
							}
						}else
						{
							for (int i = 0; i < list.size(); i++)
							{
								if (list.get(i).role.equals("1"))
								{
									ts = list.get(i);
								}
							}
						}
						ts.send(strmessage);
						ts.send(message1);
						ts.send(message2);
					}
					
					if (((String)strmessage).contains("战胜"))
					{
						for (int i = 0; i < list.size(); i++)
						{
							TransferThread ts = list.get(i);
							if (i != id && ts != null)
							{
								list.get(i).send(strmessage);
								list.get(i).send(message1);
								list.get(i).send(message2);
							}
						}
					}
					
					if (((String)strmessage).equals("对手"))
					{
						kind++;
System.out.println("服务器接受下棋指令");
System.out.println("kind =" + kind);
						if (kind <= 2)
						{
							role = "" + kind;
							
							send("" + kind);
							send(message1);
							send(message2);
						}else
						{
							if (message1List.size() > 0 && (message1List.get(message1List.size() - 1)) != null)
							{
								send("退回" + turnList.get(turnList.size() - 1));
								send(message1List.get(message1List.size() - 1));
								send(message2List.get(message2List.size() - 1));
							}else
							{
								send("退回");
								send(message1);
								send(message2);
							}
						}
					}
					
					if (((String)strmessage).equals("观众"))
					{
						if (message1List.size() > 0 && (message1List.get(message1List.size() - 1)) != null)
						{
							send("观众" + turnList.get(turnList.size() - 1));
							send(message1List.get(message1List.size() - 1));
							send(message2List.get(message2List.size() - 1));
						}else
						{
							send("观众");
							send(message1);
							send(message2);
						}
					}
					
					if (((String)strmessage).contains("请求悔棋"))
					{
						TransferThread ts = null;
						if (list.get(id).role.equals("1"))
						{
							for (int i = 0; i < list.size(); i++)
							{
								if (list.get(i).role.equals("2"))
								{
									ts = list.get(i);
								}
							}
						}else
						{
							for (int i = 0; i < list.size(); i++)
							{
								if (list.get(i).role.equals("1"))
								{
									ts = list.get(i);
								}
							}
						}
						ts.send(strmessage);
						ts.send(message1);
						ts.send(message2);
					}
					
					if (((String)strmessage).contains("允许悔棋"))
					{
						message1List.add(message1);
						message2List.add(message2);
						turnList.add("" + ((String)strmessage).charAt(4));
						for (int i = 0; i < list.size(); i++)
						{
							if (i != id && list.get(i) != null)
							{
								list.get(i).send(strmessage);
								list.get(i).send(message1);
								list.get(i).send(message2);
							}
						}
					}
				}
			} catch(Exception e)
			{
				list.remove(list.get(id));
				try
				{
					if (out != null) out.close();
					if (in != null) in.close();
					if (s != null) s.close();
				} catch(IOException e1)
				{
					e1.printStackTrace();
				}
			}
		}
	}
}
