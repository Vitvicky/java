import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;

import javax.swing.*;


public class ServerDataBase 
{
	LinkedHashMap<Integer, TransferThread> list = null;
	ArrayList message1List = new ArrayList();
	ArrayList message2List = new ArrayList();
	ArrayList<String> turnList = new ArrayList<String>();
	ServerSocket ss;
	Socket s;
	static int kind = 0;
	Connection connection;
	Statement statement;
	
	public void launch() throws InstantiationException, IllegalAccessException
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch(ClassNotFoundException e)
		{
			System.out.println();
			e.printStackTrace();
		}
		
		try
		{
			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/game", "root", "1991412");
			statement = connection.createStatement();
		} catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		list = new LinkedHashMap<Integer, TransferThread>();
		try
		{
			ss = new ServerSocket(8888);
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
				System.out.println("连接8888");
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
		DataInputStream in = null;
		DataOutputStream out = null;
		
		public TransferThread(int id)
		{
			this.id = id;
			isConnected = true;
			try
			{
				in = new DataInputStream(s.getInputStream());
				out = new DataOutputStream(s.getOutputStream());
			} catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		
		public void send(String message)
		{
			try
			{
				out.writeUTF(message);
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
				String strmessage;
				String message1 = null;
				String message2 = null;
				
				while (isConnected)
				{
					strmessage = in.readUTF();
					message1 = in.readUTF();
					message2 = in.readUTF();
					
System.out.println(strmessage);
					if (strmessage.contains("增加"))
					{
System.out.println("增加");		
						String name = null;
						ResultSet result = statement.executeQuery("SELECT * FROM people where name='"+ message1 + "'");
System.out.println(result);
						if (result.next())
						{
							name = result.getString(1);
						}
System.out.println(name);
						if (name == null || name.equals(""))
						{
System.out.println("注册成功");
							statement.execute("INSERT INTO people  VALUES('"+ message1+ "','"+ message2+ "',100)");
							send("注册成功");
						}else
						{
							send("已经存在");
						}
					}
					
					if (strmessage.contains("确认"))
					{
						String name = null;
						String password = null;
						ResultSet result = statement.executeQuery("SELECT * FROM people where name='"+ message1 + "'");
						if (result.next())
						{
							name = result.getString(1);
							password = result.getString(2);
						}
						if (name == null || name.equals(""))
						{
							send("noInformation");
						}else
						{
							if (!password.equals(message2))
								send("WrongPassword");
							else send("确认存在");
						}
					}
					
					if (strmessage.equals("Search"))
					{
						String name = null;
						String password = null;
						String score = null;
						String SearchResult = null;
						ResultSet result = statement.executeQuery("SELECT * FROM people where name='"+ message1 + "'");
						if (result.next())
						{
							name = result.getString(1);
							password = result.getString(2);
							score = result.getString(3);
						}
						if (name == null || name.equals(""))
						{
							send("noInformation");
						}else
						{
							if (!password.equals(message2))
								send("WrongPassword");
							else
							{
								SearchResult = "Your userName is:" + name + "\n your password is:" + password + "\n your score is:" + score;
								send("Information：" + "\n" + SearchResult);
							}
						}
					}
					
					if (strmessage.contains("加分"))
					{
						String winner = message1;
						statement.execute("UPDATE people set score=score+10 where name='" + winner + "'");
					}
					
					if (strmessage.contains("减分"))
					{
						String loser = message1;
						statement.execute("UPDATE people set score=score+10 where name='" + loser + "'");
					}
				}
			} catch(Exception e)
			{
				list.remove(list.get(id));
				try
				{
					if (in != null) in.close();
					if (out != null) out.close();
					if (s != null) s.close();
				} catch(IOException e1)
				{
					System.out.println();
				}
			}
		}
	}
}
