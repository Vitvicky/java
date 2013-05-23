import java.net.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.io.*;
public class GUIclient extends JFrame implements ActionListener,Runnable{
	Socket ss;
	DataInputStream in;
	DataOutputStream out;
	JButton j1;
	JTextField j2;
	JTextArea j3;
	JScrollPane j4;
	public GUIclient()throws Exception{
		j1=new JButton("发送");
		j2=new JTextField(15);
		j1.addActionListener(this);
		this.setLayout(new BorderLayout());
		j3=new JTextArea();
		JPanel ww=new JPanel();
		ww.add(j1);
		ww.add(j2);
		j4=new JScrollPane();
		j4.setViewportView(j3);
		this.setTitle("聊天窗口");
		this.add(BorderLayout.SOUTH,ww);
		this.add(j4);
		this.setSize(300,500);
		this.setVisible(true);
		ss=new Socket("211.87.237.132",10000);//创建客户端Socket并创建输入出流
		in=new DataInputStream(ss.getInputStream());
		out=new DataOutputStream(ss.getOutputStream());
	
	}
	public static void main(String[] args)throws Exception {
		Thread t=new Thread(new GUIclient());
		t.start();
		

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try{
			
			if(arg0.getSource()==j1)
			{
			out.writeUTF(j2.getText());//向服务器发送消息
			out.flush();
			j3.append("me:"+j2.getText()+"\n");
			j2.setText("");
			}
		}
		catch(Exception ee){
			System.out.println(ee.getMessage());
		}
	}

	@Override
	public void run() {
		//接受服务器返回的消息
		try{
			while(true){
				
				j3.append("server:"+in.readUTF());
				j3.append("\n");
			}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

}
