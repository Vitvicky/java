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
		jf = new JFrame("ע��");
		jpBase = new JPanel();//ע��������
		jp1 = new JPanel(); //�˺�
		jp2 = new JPanel();//����
		jp3 = new JPanel();//����ȷ��
		//jp4 = new JPanel();//IP���
		jp5 = new JPanel();/////////�ύ��ȡ�������
		name = new TextField(20);
		password = new TextField(20);
		makesure = new TextField(20);
		password.setEchoChar('*');//������ʾΪ**********
		makesure.setEchoChar('*');
		
		namel = new JLabel("�˺� ��");
		passl = new JLabel("���룺");
		makesurel = new JLabel("����ȷ�ϣ�");
		
		ok = new JButton("�ύ");
		cancel = new JButton("ȡ��");
		
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
System.out.println("����8888ǰ");
			socket = new Socket(serverIP, 8888);
			connected = true;
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
		} catch(IOException e)
		{
			JOptionPane.showMessageDialog(null, "��ȷ�Ϸ�����������");
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
				JOptionPane.showMessageDialog(null, "�κ���Ϣ����Ϊ��");
				return;
			}else
			{
				if (!password.getText().equals(makesure.getText()))
				{
					JOptionPane.showMessageDialog(null, "����ȷ�ϲ�һ������ȷ��");
					return;
				}else
				{
					playerName = name.getText();
					playerPassword = password.getText();
					jf.setVisible(false);
					int option = JOptionPane.showConfirmDialog(jf, " ����˺�" + playerName + "\n �������" + playerPassword);
					if (option == JOptionPane.YES_OPTION)
					{
						connect();
						try
						{
							dos.writeUTF("����");
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
					
					if (message.equals("ע��ɹ�"))
					{
						JOptionPane.showMessageDialog(null, "ע��ɹ�");
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
					
					if (message.equals("�Ѵ���"))
					{
						JOptionPane.showMessageDialog(null, "�˺��Ѵ���");
						System.exit(0);
					}
				}
			} catch(Exception e)
			{
				JOptionPane.showMessageDialog(null, "�������ѹر�");
				System.exit(0);
			}
		}
	}
}
