import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Register implements ActionListener 
{
	JFrame jf;
	JPanel jpBase, jp1, jp2, jp3, jp4, jp5;
	TextField name, password, makesure, server_IP;
	JLabel namel, passl, makesurel, IPl;
	JButton ok, cancel;
	String playerName, playerPassword, serverIP = "127.0.0.1";
	Socket socket;
	boolean connected;
	DataInputStream dis = null;
	DataOutputStream dos = null;
	
	public void launch()
	{
		jf = new JFrame("注册");
		jpBase = new JPanel();//注册框主面板
		jp1 = new JPanel(); //账号
		jp2 = new JPanel();//密码
		jp3 = new JPanel();//密码确认
		//jp4 = new JPanel();//IP面板
		jp5 = new JPanel();/////////提交和取消的面板
		name = new TextField(20);
		password = new TextField(20);
		makesure = new TextField(20);
		password.setEchoChar('*');//密码显示为**********
		makesure.setEchoChar('*');
		
		namel = new JLabel("账号 ：");
		passl = new JLabel("密码：");
		makesurel = new JLabel("密码确认：");
		
		ok = new JButton("提交");
		cancel = new JButton("取消");
		
		name.addActionListener(this);
		password.addActionListener(this);
		makesure.addActionListener(this);
		//server_IP.addActionListener(this);
		ok.addActionListener(this);
		cancel.addActionListener(this);
		
		jp1.add(namel);
		jp1.add(name);
		jp2.add(passl);
		jp2.add(password);
		jp3.add(makesurel);
		jp3.add(makesure);
		//jp4.add(IPl);
		//jp4.add(server_IP);
		jp5.add(ok);
		jp5.add(cancel);
		jpBase.add(jp1);
		jpBase.add(jp2);
		jpBase.add(jp3);
		//jpBase.add(jp4);
		jpBase.add(jp5);
		
		jf.add(jpBase);
		jf.setBounds(200, 200, 300, 220);
		jf.setResizable(false);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
	}
	
	public void connect()
	{
		try
		{
System.out.println("连接8888前");
			socket = new Socket(serverIP, 8888);
			connected = true;
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
		} catch(IOException e)
		{
			JOptionPane.showMessageDialog(null, "请确认服务器已运行");
			try
			{
				if (socket != null) socket.close();
				if (dis != null) dis.close();
				if (dos != null) dos.close();
				System.exit(0);
			} catch(IOException e1)
			{
				e1.printStackTrace();
			}
		}
		
		new Thread(new Receive()).start();
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == name || e.getSource() == password || e.getSource() == makesure || e.getSource() == ok)
		{
			if (name.getText().equals("") || password.getText().equals("") || makesure.getText().equals(""))
			{
				JOptionPane.showMessageDialog(null, "任何信息不能为空");
				return;
			}else
			{
				if (!password.getText().equals(makesure.getText()))
				{
					JOptionPane.showMessageDialog(null, "密码确认不一样，请确认");
					return;
				}else
				{
					playerName = name.getText();
					playerPassword = password.getText();
					jf.setVisible(false);
					int option = JOptionPane.showConfirmDialog(jf, " 你的账号" + playerName + "\n 你的密码" + playerPassword);
					if (option == JOptionPane.YES_OPTION)
					{
						connect();
						try
						{
							dos.writeUTF("增加");
							dos.writeUTF(playerName);
							dos.writeUTF(playerPassword);
						} catch(IOException e1)
						{
							e1.printStackTrace();
						}
					}
					if (option == JOptionPane.NO_OPTION)
					{
						System.exit(0);
					}
				}
			}
		}
		if (e.getSource() == cancel)
		{
			System.exit(0);
		}
	}
	
	private class Receive implements Runnable
	{
		public void run()
		{
			String message;
			
			try
			{
				while (connected)
				{
					message = dis.readUTF();
					
					if (message.equals("注册成功"))
					{
						JOptionPane.showMessageDialog(null, "注册成功");
						Thread t = new Thread(new Runnable()
						{
							public void run()
							{
								new LobbyView(playerName).launch();
								//new StartChess(playerName, serverIP).launchChess();
							}
						});
						t.start();
					}
					
					if (message.equals("已存在"))
					{
						JOptionPane.showMessageDialog(null, "账号已存在");
						System.exit(0);
					}
				}
			} catch(Exception e)
			{
				JOptionPane.showMessageDialog(null, "服务器已关闭");
				System.exit(0);
			}
		}
	}
}
