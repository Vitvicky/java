import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Main implements ActionListener 
{
	
	JFrame mainF;
	JPanel mainP, mainPup, mainPmidup, mainPmiddown, mainPdown;
	JTextField name, server_IP;
	JPasswordField password;
	JButton land, quit, register;
	JLabel nameLabel, passwordLabel, IPLabel;
	String playerName, score, playerPassword;
	String serverIP = "127.0.0.1";
	Socket socket;
	private Image mainmenu = null;
	
	boolean connected = false;
	
	DataInputStream dis = null;
	DataOutputStream dos = null;
	
	public void launch()//登陆界面
	{
		mainF = new JFrame("网络黑白棋	作者：张炀");
		mainPup = new JPanel();
		mainPmidup = new JPanel();
		mainPmiddown = new JPanel();
		mainPdown = new JPanel();
		mainP = new JPanel();
			
		
		name = new JTextField(15);
		password = new JPasswordField(15);
		
		land = new JButton("登录");
		register = new JButton("注册");
		quit = new JButton("退出");
		
		land.addActionListener(this);
		register.addActionListener(this);
		quit.addActionListener(this);
		name.addActionListener(this);
		password.addActionListener(this);
		
		nameLabel = new JLabel("用户名：");
		passwordLabel = new JLabel("密码：");
		
		mainP.setLayout(new GridLayout(4, 1));
		mainP.add(mainPup);
		mainP.add(mainPmidup);
		mainP.add(mainPmiddown);
		mainP.add(mainPdown);
		mainPup.add(nameLabel);
		mainPup.add(name);
		mainPmidup.add(passwordLabel);
		mainPmidup.add(password);
		mainPdown.add(land);
		mainPdown.add(register);
		mainPdown.add(quit);
		
		mainF.add(mainP);
		mainF.setBounds(200, 200, 300, 300);
		mainF.setResizable(false);
		mainF.setVisible(true);
		mainF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public void connect()
	{
		try
		{
			socket = new Socket(serverIP, 8888);
			connected = true;
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
		} catch(IOException e)
		{
			JOptionPane.showMessageDialog(null, "请确保服务器已开启");
			try
			{
				if (socket != null) socket.close();
				if (dos != null) dos.close();
				if (dis != null) dis.close();
				System.exit(0);
			} catch(IOException e1)
			{
				e1.printStackTrace();
			}
		}
		new Thread(new Receive()).start();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) //事件监听并处理
	{
		if (e.getActionCommand().equals("登录") || e.getSource().equals(name) || e.getSource().equals(password))//用户名秘密均正确，登陆
		{
			playerName = name.getText();
			playerPassword = new String(password.getText());
			
			if (playerName.equals("") || playerPassword.equals(""))
			{
				JOptionPane.showMessageDialog(null, "用户名和密码不能为空!");
				return;
			}
			else
			{
				int option0 = JOptionPane.showConfirmDialog(mainF, "是否观看上次的棋局？");
				if (option0 == JOptionPane.YES_OPTION)
				{
//JOptionPane.showMessageDialog(null, "观看成功");
					connect();
					try
					{
						dos.writeUTF("Search");
						dos.writeUTF(playerName);
						dos.writeUTF(playerPassword);
					} catch(IOException e1)
					{
						e1.printStackTrace();
					}
				}
				else
				{
					if (option0 == JOptionPane.NO_OPTION)
					{
						//new StartChess(playerName, serverIP).launchChess();
						connect();
						try
						{
							dos.writeUTF("确认");
							dos.writeUTF(playerName);
							dos.writeUTF(playerPassword);
						} catch(IOException e1)
						{
							e1.printStackTrace();
						}
					}
					if (option0 == JOptionPane.CANCEL_OPTION)
					{
						return;
					}
				}
			}
		}
		else if (e.getActionCommand().equals("登录") && ((!e.getSource().equals(name) || !e.getSource().equals(password))))//用户名或密码错误
			{
				JOptionPane.showMessageDialog(null, "用户名或密码错误！请检查");
				return;
			}
		
		if (e.getActionCommand().equals("注册"))//进入注册界面
		{
//JOptionPane.showMessageDialog(null, "注册成功");
System.out.println("注册");
			new Register().launch();
			mainF.setVisible(false);
		}
		
		if (e.getActionCommand().equals("退出"))//退出游戏
		{
			name.setText("");
			password.setText("");
			System.exit(0);
		}
		
	}
	
	public static void main(String[] args)//主函数
	{
		new Main().launch();
	}
	
	private class Receive implements Runnable
	{

		@Override
		public void run() 
		{
			String message;
			
			try
			{
				while(connected)
				{
					message = dis.readUTF();
					//message = "Information\n" + "wr";
					//message = "确认存在";
					
					if (message.equals("noInformation"))
					{
						JOptionPane.showMessageDialog(null, "没有记录，请注册");
						new Register().launch();
						mainF.setVisible(false);
					}
					
					if (message.equals("确认存在"))
					{
						mainF.setVisible(false);
						Thread t = new Thread(new Runnable()
						{
							public void run()
							{
								//new StartChess(playerName, serverIP).launchChess();
								new LobbyView(playerName).launch();
							}
						});
						
						t.start();
					}
					
					if (message.charAt(0) == 'I')
					{
						mainF.setVisible(false);
						JOptionPane.showMessageDialog(null, message);
						
						//new StartChessAgain(playerName).launchChess();
						
						Thread t = new Thread(new Runnable()
						{
							public void run()
							{
								new StartChessAgain(playerName).launchChess();
							}
						});
						
						t.start();
					}
					
					if (message.equals("WrongPassword"))
					{
						JOptionPane.showMessageDialog(null, "密码错误");
						System.exit(0);
					}
//connected = false;
				}
			} catch(Exception e)
			{
				JOptionPane.showMessageDialog(null, "服务器已关闭");
				System.exit(0);
			}
		}
	}
}
