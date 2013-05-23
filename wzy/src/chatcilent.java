import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;
public class chatcilent extends JFrame {
	Socket socket;
	JButton j1;
	String name;
	JTextArea sendarea;
	JTextArea contentarea;
	JScrollPane j4;
	JScrollPane j5;
	public chatcilent(Socket socket,String name){
		this.socket=socket;
		this.name=name;
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.init();
		cilentThread t=new cilentThread(socket,contentarea);
		t.start();
	}
	public void init(){
		this.setTitle("我的聊天室");
		this.setSize(300,500);
		this.setResizable(false);
		contentarea=new JTextArea();
		contentarea.setLineWrap(false);
		contentarea.setEditable(false);
		//this.setLayout(new BorderLayout());
		j4=new JScrollPane(contentarea,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sendarea=new JTextArea();
		sendarea.setLineWrap(true);
		j5=new JScrollPane(sendarea,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//分割窗格
		JSplitPane jp=new JSplitPane(JSplitPane.VERTICAL_SPLIT,j4,j5);
		jp.setDividerLocation(250);
		this.add(jp,BorderLayout.CENTER);
		j1=new JButton("发送");
		JPanel qq=new JPanel();
		qq.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.add(qq,BorderLayout.SOUTH);
		
		JLabel jname=new JLabel("用户名:"+this.name+"  ");
		qq.add(jname);
		j1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			//获取发送内容
				String s=sendarea.getText();
				SimpleDateFormat f=new SimpleDateFormat("HH:mm:ss");
				String time=f.format(new Date());
				String send=name+" "+time + "说:"+s;
				//往服务器发
				PrintWriter out=null;
				try{
					out=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
					out.println(send);
					out.flush();}
					catch(IOException e1){
						e1.printStackTrace();
					}
				sendarea.setText("");
			}
		});
		qq.add(j1);
		
		}
	}
	//客户和服务器通信
	
		
class cilentThread extends Thread{
	private Socket socket;
	private JTextArea area;
	public cilentThread(Socket socket,JTextArea area){
		this.socket=socket;
		this.area=area;
	}
	
	

	public void run() {
		BufferedReader bf=null;
		try{
			
				bf=new BufferedReader(new InputStreamReader(socket.getInputStream()));//输入流读取数据添加到聊天记录区域
				String str=null;
				while((str=bf.readLine())!=null){
					area.append(str+"\n");
				}
				}
		catch(Exception e){
			e.printStackTrace();
		}	
		finally{
			if(bf!=null){
				try{
					bf.close();
				}
				catch(IOException e){
					
				}
			}
		}
	}	
}		
	
	
	
	