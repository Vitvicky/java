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
	
	public void launch()//��½����
	{
		mainF = new JFrame("����ڰ���	���ߣ����");
		mainPup = new JPanel();
		mainPmidup = new JPanel();
		mainPmiddown = new JPanel();
		mainPdown = new JPanel();
		mainP = new JPanel();
			
		
		name = new JTextField(15);
		password = new JPasswordField(15);
		
		land = new JButton("��¼");
		register = new JButton("ע��");
		quit = new JButton("�˳�");
		
		land.addActionListener(this);
		register.addActionListener(this);
		quit.addActionListener(this);
		name.addActionListener(this);
		password.addActionListener(this);
		
		nameLabel = new JLabel("�û�����");
		passwordLabel = new JLabel("���룺");
		
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
			JOptionPane.showMessageDialog(null, "��ȷ���������ѿ���");
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
	public void actionPerformed(ActionEvent e) //�¼�����������
	{
		if (e.getActionCommand().equals("��¼") || e.getSource().equals(name) || e.getSource().equals(password))//�û������ܾ���ȷ����½
		{
			playerName = name.getText();
			playerPassword = new String(password.getText());
			
			if (playerName.equals("") || playerPassword.equals(""))
			{
				JOptionPane.showMessageDialog(null, "�û��������벻��Ϊ��!");
				return;
			}
			else
			{
				int option0 = JOptionPane.showConfirmDialog(mainF, "�Ƿ�ۿ��ϴε���֣�");
				if (option0 == JOptionPane.YES_OPTION)
				{
//JOptionPane.showMessageDialog(null, "�ۿ��ɹ�");
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
							dos.writeUTF("ȷ��");
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
		else if (e.getActionCommand().equals("��¼") && ((!e.getSource().equals(name) || !e.getSource().equals(password))))//�û������������
			{
				JOptionPane.showMessageDialog(null, "�û����������������");
				return;
			}
		
		if (e.getActionCommand().equals("ע��"))//����ע�����
		{
//JOptionPane.showMessageDialog(null, "ע��ɹ�");
System.out.println("ע��");
			new Register().launch();
			mainF.setVisible(false);
		}
		
		if (e.getActionCommand().equals("�˳�"))//�˳���Ϸ
		{
			name.setText("");
			password.setText("");
			System.exit(0);
		}
		
	}
	
	public static void main(String[] args)//������
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
					//message = "ȷ�ϴ���";
					
					if (message.equals("noInformation"))
					{
						JOptionPane.showMessageDialog(null, "û�м�¼����ע��");
						new Register().launch();
						mainF.setVisible(false);
					}
					
					if (message.equals("ȷ�ϴ���"))
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
						JOptionPane.showMessageDialog(null, "�������");
						System.exit(0);
					}
//connected = false;
				}
			} catch(Exception e)
			{
				JOptionPane.showMessageDialog(null, "�������ѹر�");
				System.exit(0);
			}
		}
	}
}
