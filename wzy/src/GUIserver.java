import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
public class GUIserver extends JFrame implements ActionListener,Runnable{
	InetAddress add;//声明数据通信类对象
	ServerSocket s;
	Socket ss;
	DataInputStream in;
	DataOutputStream out;
	JButton j1;
	JTextField j2;
	TextArea j3=new TextArea();
	JScrollPane j4;
	public GUIserver()throws Exception{
		j1=new JButton("发送");
		j2=new JTextField(15);
		j1.addActionListener(this);
		this.setLayout(new BorderLayout());
		
		JPanel qq=new JPanel();
		
		qq.add(j1);
		qq.add(j2);
		
		//滚动条面板
		j4=new JScrollPane();
		j4.setViewportView(j3);
		
		this.setTitle("服务器端");
		this.add(BorderLayout.SOUTH,qq);
		this.add(j4);
		this.setSize(300,500);
		this.setVisible(true);
		add=InetAddress.getByName("wzy-PC");//创建ServerSocket开始监听
		s=new ServerSocket(10000);
		System.out.println("服务器启动...");
		j3.append("服务器启动..."+"\n");	
	}
	
	public static void main(String[] args) throws Exception{
		//将当前类作为线程启动
		Thread t=new Thread(new GUIserver());
		t.start();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		try{//向客户端发送消息
			
			if(arg0.getSource()==j1){
			
			out.writeUTF(j2.getText());
			out.flush();
			j3.append("me:"+j2.getText());
			j3.append("\n");
			j2.setText("");
			}
		}
		catch(Exception ee){
			System.out.println(ee.getMessage());
		}
	}

	@Override
	public void run() {
		//接受客户端消息
		try{
			while(true){
				ss=s.accept();
				System.out.println("有客户端连接...");
				j3.append("有客户端连接..."+"\n");
				//创建数据流
				in=new DataInputStream(ss.getInputStream());
				out=new DataOutputStream(ss.getOutputStream());
				while(true)//读进来时要用循环
				j3.append("client:"+in.readUTF()+"\n");
				
			}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

}
